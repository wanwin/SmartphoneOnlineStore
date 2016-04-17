package frontController;

import entity.Product;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import userBeans.CartLocal;

public class DelFromCartCommand extends FrontCommand{

    @Override
    public void process(){
        try {
            String requestedProductId = request.getParameter("productId");
            CartLocal cart = initCart();
            ConcurrentHashMap<Product,Integer> products = cart.getProducts();
            for (Map.Entry<Product,Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                Integer productId = Integer.parseInt(requestedProductId);
                if (product.getProductId().equals(productId)){
                    cart.delFromCart(product);
                    forward(CART_PATH);
                }
            }
        } 
        catch (NamingException | ServletException | IOException ex) {
        } 
    }
}