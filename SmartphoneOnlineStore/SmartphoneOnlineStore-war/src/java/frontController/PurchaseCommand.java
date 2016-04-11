package frontController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import controller.ProductFacadeLocal;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import userBeans.CartLocal;
public class PurchaseCommand extends FrontCommand{

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
            products.clear();
            
        } catch (IOException | NamingException ex) {
        }
    }
}
