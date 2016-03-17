package frontController;

import controller.ProductFacadeLocal;
import entity.Product;
import static frontController.FrontCommand.CART_PATH;
import java.io.IOException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import userBeans.CartLocal;

public class DelFromCartCommand extends FrontCommand{

    @Override
    public void process(){
        try {
            CartLocal cart = initCart();
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
}