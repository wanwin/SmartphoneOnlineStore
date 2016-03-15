package frontController;

import controller.ProductFacadeLocal;
import entity.Product;
import java.io.IOException;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

public class FindProductCommand extends FrontCommand{

    @Override
    public void process(){
        try {
            ProductFacadeLocal productFacade = (ProductFacadeLocal) InitialContext.doLookup(PRODUCT_JNDI_URL);
            List<Product> products = productFacade.findAll();
            request.setAttribute("Products", products);
            try {
                forward(PRODUCTS_PATH);
            } catch (ServletException | IOException ex) {
            }
        } catch (NamingException ex) {
        }
    }
}
