package frontController;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import controller.ProductFacadeLocal;
import entity.Product;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import userBeans.CartLocal;
public class PurchaseCommand extends FrontCommand{
    Document document = new Document();
    
    @Override
    public void process() {
        response.setContentType("text/html;charset=UTF-8");
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
            openPDF();
            createCompanyHead("../../web/resources/img/logo_horizontal.png", 
                    "Movilazos SL", "B35.258.951", "Las Palmas de Gran Canaria", "928 928 928");
            createClientHead("NombreCliente", "4585726", "C/La Que Sea", "928 456 654");
            createTable(3, products);
            closePDF();
            products.clear();
            out.println("he llegado");
        } catch (IOException | NamingException ex) {
        } catch (DocumentException ex) {
            Logger.getLogger(PurchaseCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        FileOutputStream pdf = new FileOutputStream("/home/evelin/Escritorio/Factura.pdf");
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

    private double printTableOfProducts(ConcurrentHashMap<Product, Integer> products, PdfPTable productsTable, double subtotal) {
        for (Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            productsTable.addCell(product.getDescription());
            productsTable.addCell("" + product.getPurchaseCost().floatValue() + "");
            productsTable.addCell("" + quantity.intValue() + "");
            if (quantity.intValue() > 1){
                subtotal += product.getPurchaseCost().floatValue() * quantity.intValue();
            }else subtotal += product.getPurchaseCost().floatValue();
        }
        return subtotal;
    }

    private void printHeadOfTableOfProducts(PdfPTable productsTable) {
        productsTable.addCell("Producto");
        productsTable.addCell("Precio");
        productsTable.addCell("Unidades");
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
