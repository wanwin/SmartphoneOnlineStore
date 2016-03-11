package frontController;

import controller.ProductFacadeLocal;
import entity.Product;
import java.io.IOException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import userBeans.CartLocal;

public class AddToCartCommand extends FrontCommand{

    private static final String PRODUCT_JNDI_URL = "java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/ProductFacade!controller.ProductFacadeLocal";
    private static final String CART_JNDI_URL = "java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/Cart!userBeans.CartLocal";
    private static final String CART_PATH = "/cart.jsp";
    @Override
    public void process(){
        try {
            HttpSession session = getSession(request);
            CartLocal cart = (CartLocal) session.getAttribute("cart");
            cart = initCart(cart, session);
            String productId = request.getParameter("productId");
            ProductFacadeLocal productFacade = (ProductFacadeLocal) InitialContext.doLookup(PRODUCT_JNDI_URL);
            Product product = productFacade.find(Integer.parseInt(productId));
            if (isAvailable(product)){
                cart.addToCart(product);
                /*product.setQuantityOnHand(product.getQuantityOnHand() - 1);
                productFacade.edit(product);*/
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

    private HttpSession getSession(HttpServletRequest request) {
        return request.getSession(true);
    }
    
    private CartLocal initCart(CartLocal cart, HttpSession session) throws NamingException {
        if(cart == null){
            cart = InitialContext.doLookup(CART_JNDI_URL);
            session.setAttribute("cart", cart);
        }
        return cart;
    }
    
    private static boolean isAvailable(Product product) {
        return product.getQuantityOnHand() > 0;
    }
}
