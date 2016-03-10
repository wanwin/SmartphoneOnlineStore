package frontController;

import controller.ProductFacadeLocal;
import entity.Product;
import java.io.IOException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

public class AddToCartCommand extends FrontCommand{

    private static final String PRODUCT_JNDI_URL = "java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/ProductFacade!controller.ProductFacadeLocal";
    private static final String CART_PATH = "/cart.jsp";
    @Override
    public void process(){
        try {
            String productId = request.getParameter("productId");
            ProductFacadeLocal productFacade = (ProductFacadeLocal) InitialContext.doLookup(PRODUCT_JNDI_URL);
            Product product = productFacade.find(Integer.parseInt(productId));
            if (isAvailable(product)){
                product.setQuantityOnHand(product.getQuantityOnHand() - 1);
                productFacade.edit(product);
            }
            else{
                
            }
            try {
                forward(CART_PATH);
            } catch (ServletException | IOException ex) {
            }
        } catch (NamingException ex) {
        }
    }

    private static boolean isAvailable(Product product) {
        return product.getQuantityOnHand() > 1;
    }
}
