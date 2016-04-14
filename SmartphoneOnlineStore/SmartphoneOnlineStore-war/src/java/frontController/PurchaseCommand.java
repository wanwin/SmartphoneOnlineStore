package frontController;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import controller.CustomerFacadeLocal;
import controller.ProductFacadeLocal;
import controller.PurchaseOrderFacadeLocal;
import entity.Customer;
import entity.Product;
import entity.PurchaseOrder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import userBeans.CartLocal;
public class PurchaseCommand extends FrontCommand{
    
    Document document = new Document();
    
    @Override
    public void process() {
        response.setContentType("text/html;charset=UTF-8");
        String dni = request.getParameter("dni");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        try(PrintWriter out = response.getWriter()){
            CartLocal cart = initCart();
            ConcurrentHashMap<Product, Integer> products = cart.getProducts();
            ProductFacadeLocal productFacade = (ProductFacadeLocal) InitialContext.doLookup(PRODUCT_JNDI_URL);
            for (Entry<Product,Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                Integer quantity = entry.getValue();
                product.setQuantityOnHand(product.getQuantityOnHand() - quantity);
                productFacade.edit(product);
            }
            CustomerFacadeLocal customerFacade = InitialContext.doLookup(CUSTOMER_JNDI_URL);
            PurchaseOrderFacadeLocal purchaseOrderFacade = InitialContext.doLookup(PURCHASE_ORDER_JNDI_URL);
            Customer customer = customerFacade.find(dni);
            if (isCustomerInDB(customer)){
                customerFacade.create(createCustomer(dni, name, surname));
            }
            createPurchaseOrder(cart, customer, purchaseOrderFacade);
            openPDF();
            createCompanyHead("../../web/resources/img/logo_horizontal.png", 
                    "Movilazos SL", "B35.258.951", "Las Palmas de Gran Canaria", "928 928 928");
            createClientHead(customer.getName(), customer.getCustomerId(), "C/Desengaño 21", "928 456 654");
            createTable(3, products);
            products.clear();
            closePDF();
            out.println("he llegado");
        } catch (IOException | NamingException | DocumentException ex) {
        }
    }

    private boolean isCustomerInDB(Customer customer) {
        return customer == null;
    }
    
    private Customer createCustomer(String dni, String name, String surname) throws NumberFormatException {
        Customer customer = new Customer(dni);
        customer.setName(name);
        customer.setSurname(surname);
        return customer;
    }
    
    private void createPurchaseOrder(CartLocal cart, Customer customer, PurchaseOrderFacadeLocal purchaseOrderFacade) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        Float purchaseCost = cart.calculateTotal();
        purchaseOrder.setCustomerId(customer);
        purchaseOrder.setPurchaseCost(purchaseCost.doubleValue());
        purchaseOrderFacade.create(purchaseOrder);
    }
    
    public void createCompanyHead(String logo, String name, String cif, String location, String phone) throws DocumentException{
        // "resources/img/logo_horizontal.png"
        // "B35.258.951"
        addLogoToPDF(logo);
        addParagraph(name, Chunk.ALIGN_LEFT);
        addParagraph(cif, Chunk.ALIGN_LEFT);
        addParagraph(location, Chunk.ALIGN_LEFT);
        addParagraph(phone, Chunk.ALIGN_LEFT);
    }
    
    
    public void createClientHead(String name, String dni, String direction, String phone) throws DocumentException{
        addParagraph(name, Chunk.ALIGN_RIGHT);
        addParagraph(dni, Chunk.ALIGN_RIGHT);
        addParagraph(direction, Chunk.ALIGN_RIGHT);
        addParagraph(phone, Chunk.ALIGN_RIGHT);
        addParagraph("-", Chunk.ALIGN_RIGHT);
        
    }
    
    public void openPDF() throws FileNotFoundException, DocumentException{
        //FileOutputStream pdf = new FileOutputStream("/home/evelin/Escritorio/Factura.pdf");
        FileOutputStream pdf = new FileOutputStream("C:\\Users\\Darwin\\Desktop\\Factura.pdf");
        PdfWriter.getInstance(document,pdf).setInitialLeading(20);
        document.open();
    }
    
    public void addLogoToPDF(String path){
        try{
            Image picture = Image.getInstance(path);
            picture.scaleToFit(100, 100);
            picture.setAlignment(Chunk.ALIGN_LEFT);
            document.add(picture);
        }catch (Exception e){}
    }
    
    public void addParagraph(String text, int alignment) throws DocumentException{
        Paragraph paragraph = new Paragraph(text,
                FontFactory.getFont("arial",15,Font.ITALIC,BaseColor.BLACK));
        paragraph.setAlignment(alignment);
        document.add(paragraph);
    }
    
    public void createTable(int column, ConcurrentHashMap<Product, Integer> products) throws DocumentException{
        DecimalFormat df = new DecimalFormat("0.00");
        PdfPTable productsTable = new PdfPTable(column);
        PdfPTable finalTable = new PdfPTable(2);
        double subtotal = 0, total = 0; 
        printHeadOfTableOfProducts(productsTable);
        subtotal = printTableOfProducts(products, productsTable, subtotal);
        document.add(productsTable);
        addParagraph("--", Chunk.ALIGN_CENTER);
        total = subtotal + subtotal * 0.07;
        printFinalTable(finalTable, subtotal, df, total);
        document.add(finalTable);
    }

    private void printHeadOfTableOfProducts(PdfPTable productsTable) {
        productsTable.addCell("Producto");
        productsTable.addCell("Precio");
        productsTable.addCell("Unidades");
    }
    
    private double printTableOfProducts(ConcurrentHashMap<Product, Integer> products, PdfPTable productsTable, double subtotal) {
        for (Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            productsTable.addCell(product.getManufacturerId().getName() + " " + product.getDescription());
            productsTable.addCell("" + product.getPurchaseCost().floatValue() + "");
            productsTable.addCell("" + quantity + "");
            if (quantity > 1){
                subtotal += product.getPurchaseCost().floatValue() * quantity;
            }else subtotal += product.getPurchaseCost().floatValue();
        }
        return subtotal;
    }


    private void printFinalTable(PdfPTable finalTable, double subtotal, DecimalFormat df, double total) {
        finalTable.addCell("Subtotal");
        finalTable.addCell("" + subtotal + "");
        finalTable.addCell("IGIC");
        finalTable.addCell("" + df.format(subtotal * 0.07) + "");
        finalTable.addCell("Total");
        finalTable.addCell("" + total + "");
    }
    
    public void closePDF(){
        document.close();
    }
}
