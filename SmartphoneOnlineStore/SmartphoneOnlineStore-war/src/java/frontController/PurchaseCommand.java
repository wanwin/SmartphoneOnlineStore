package frontController;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import controller.CustomerFacadeLocal;
import controller.ProductFacadeLocal;
import controller.PurchaseOrderFacadeLocal;
import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;
import entity.Customer;
import entity.Product;
import entity.PurchaseOrder;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import userBeans.CartLocal;

public class PurchaseCommand extends FrontCommand{
    
    Document document = new Document();
    ByteArrayOutputStream pdf;
    
    @Override
    public void process() {
        try {
            String name = encodeStringToUTF8(request.getParameter("name"));
            String surname = encodeStringToUTF8(request.getParameter("surname"));
            String dni = request.getParameter("dni");
            String tel = request.getParameter("tel");
            String address = encodeStringToUTF8(request.getParameter("address"));
            CartLocal cart = initCart();
            ConcurrentHashMap<Product, Integer> products = cart.getProducts();
            ConcurrentHashMap<Product, Integer> products2 = cart.getProducts2();
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
            if (!isCustomerInDB(customer)){
                customer = createCustomer(name, surname, dni, tel, address);
                customerFacade.create(customer);
            }
            createPurchaseOrder(cart, customer, purchaseOrderFacade);
            openPDF();
            createCompanyHead("../../web/resources/img/logo_horizontal.png", 
                    "Movilazos SL", "B35.258.951", "Las Palmas de Gran Canaria", "928 928 928");
            createClientHead(name, surname, dni, tel, address);
            createTable(3, products, products2);
            products.clear();
            products2.clear();
            closePDF();
            createPDFHeader();
            BrowserLauncher browserLauncher = new BrowserLauncher();
            browserLauncher.openURLinBrowser("http://localhost:8080/SmartphoneOnlineStore-war/purchaseFinished.jsp");
        } 
        catch (NamingException | IOException | DocumentException | BrowserLaunchingInitializingException | UnsupportedOperatingSystemException ex) {
        }
    }

    private void createPDFHeader() throws IOException {
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("content-disposition", "attachment; filename=\"Factura.pdf\"");
        response.setContentType("application/pdf");
        response.setContentLength(pdf.size());
        OutputStream os = response.getOutputStream();
        pdf.writeTo(os);
        os.flush();
        os.close();
    }

    private String encodeStringToUTF8(String str) {
        byte string[] = str.getBytes(ISO_8859_1);
        return new String(string, UTF_8);
    }

    private boolean isCustomerInDB(Customer customer) {
        return customer != null;
    }
    
    private Customer createCustomer(String name, String surname, String dni, String tel, String address) throws NumberFormatException {
        Customer customer = new Customer(dni);
        customer.setName(name);
        customer.setSurname(surname);
        customer.setPhone(tel);
        customer.setAddressline1(address);
        return customer;
    }
    
    private void createPurchaseOrder(CartLocal cart, Customer customer, PurchaseOrderFacadeLocal purchaseOrderFacade) throws FileNotFoundException, IOException{
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        ConcurrentHashMap<Product, Integer> products = cart.getProducts();
        Float purchaseCost = cart.calculateTotal();
        purchaseOrder.setCustomerId(customer);
        purchaseOrder.setPurchaseCost(purchaseCost.doubleValue());
        purchaseOrder.setProducts(products);
        purchaseOrder.setDate(constructDate());
        purchaseOrderFacade.create(purchaseOrder);
    }
    
    private String constructDate() {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return simpleDateFormat.format(calendar.getTime());
    }
    
    public void openPDF() throws FileNotFoundException, DocumentException{
        pdf = new ByteArrayOutputStream();
        PdfWriter.getInstance(document,pdf).setInitialLeading(20);
        document.open();
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
    
    
    private void createClientHead(String name, String surname, String dni, String tel, String address) throws DocumentException{
        addParagraph(name, Chunk.ALIGN_RIGHT);
        addParagraph(surname, Chunk.ALIGN_RIGHT);
        addParagraph(dni, Chunk.ALIGN_RIGHT);
        addParagraph(tel, Chunk.ALIGN_RIGHT);
        addParagraph(address, Chunk.ALIGN_RIGHT);
        addParagraph("-", Chunk.ALIGN_RIGHT);
        
    }
    
    private void createTable(int column, ConcurrentHashMap<Product, Integer> products, ConcurrentHashMap<Product, Integer> products2) 
            throws DocumentException{
        DecimalFormat df = new DecimalFormat("0.00");
        PdfPTable productsTable = new PdfPTable(column);
        PdfPTable finalTable = new PdfPTable(2);
        double subtotal = 0, total = 0; 
        printHeadOfTableOfProducts(productsTable);
        subtotal = printTableOfProducts(products, products2, productsTable, subtotal);
        document.add(productsTable);
        addParagraph("--", Chunk.ALIGN_CENTER);
        total = subtotal + subtotal * 0.07;
        printFinalTable(finalTable, subtotal, df, total);
        document.add(finalTable);
    }
    
    private void closePDF(){
        document.close();
    }
    
    private void addLogoToPDF(String path){
        try{
            Image picture = Image.getInstance(path);
            picture.scaleToFit(100, 100);
            picture.setAlignment(Chunk.ALIGN_LEFT);
            document.add(picture);
        }
        catch (Exception e){
        }
    }
    
    private void addParagraph(String text, int alignment) throws DocumentException{
        Paragraph paragraph = new Paragraph(text,
                FontFactory.getFont("arial",15,Font.ITALIC,BaseColor.BLACK));
        paragraph.setAlignment(alignment);
        document.add(paragraph);
    }
    

    private void printHeadOfTableOfProducts(PdfPTable productsTable) {
        productsTable.addCell("Producto");
        productsTable.addCell("Precio");
        productsTable.addCell("Unidades");
    }
    
    private double printTableOfProducts(ConcurrentHashMap<Product, Integer> products, ConcurrentHashMap<Product, Integer> products2, 
            PdfPTable productsTable, double subtotal) {
        for (Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            Integer productDiscount = product.getDiscount();
            Float productPrice = product.getPurchaseCost().floatValue() * quantity;  
            if (productDiscount > 0){
                productPrice = (productPrice - productPrice * productDiscount / 100) * quantity;
            }
            productsTable.addCell(product.getManufacturerId().getName() + " " + product.getDescription());
            productsTable.addCell("" + productPrice + "");
            productsTable.addCell("" + quantity + "");
            subtotal += productPrice;
        }
        for (Entry<Product, Integer> entry : products2.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            Integer productDiscount = product.getDiscount();
            Float productPrice = product.getPurchaseCost().floatValue() * quantity;  
            if (productDiscount > 0){
                productPrice = (productPrice - productPrice * productDiscount / 100) * quantity;
            }
            productsTable.addCell(product.getManufacturerId().getName() + " " + product.getDescription());
            productsTable.addCell("" + productPrice + "");
            productsTable.addCell("" + quantity + "");
            subtotal += productPrice;
        }
        return subtotal;
    }


    private void printFinalTable(PdfPTable finalTable, double subtotal, DecimalFormat df, double total) {
        finalTable.addCell("Subtotal");
        finalTable.addCell("" + df.format(subtotal) + "");
        finalTable.addCell("IGIC");
        finalTable.addCell("" + df.format(subtotal * 0.07) + "");
        finalTable.addCell("Total");
        finalTable.addCell("" + df.format(total) + "");
    }
}
