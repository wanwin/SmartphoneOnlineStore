package frontController;

import controller.ProductFacadeLocal;
import entity.Product;
import static frontController.FrontCommand.CART_PATH;
import java.io.IOException;
import static java.lang.System.out;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import userBeans.CartLocal;

public class DelFromCartCommand extends FrontCommand{

    private static final String PRODUCT_JNDI_URL = "java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/ProductFacade!controller.ProductFacadeLocal";
    @Override
    public void process(){
        try {
            HttpSession session = getSession(request);
            CartLocal cart = (CartLocal) session.getAttribute("cart");
            cart = initCart(cart, session);
            String productId = request.getParameter("productId");
            ProductFacadeLocal productFacade = (ProductFacadeLocal) InitialContext.doLookup(PRODUCT_JNDI_URL);
            Product product = productFacade.find(Integer.parseInt(productId));
            if (cart.getProductList().contains(product)){
                cart.delFromCart(product);
                /*product.setQuantityOnHand(product.getQuantityOnHand() - 1);
                productFacade.edit(product);*/
            }
            try {
                forward(CART_PATH);
            } catch (ServletException | IOException ex) {
            }
        } catch (NamingException ex) {
        }
    }

    private static boolean isAvailable(Product product) {
        return product.getQuantityOnHand() > 0;
    }
}