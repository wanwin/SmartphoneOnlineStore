package frontController;

import entity.Product;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import userBeans.CartLocal;

public class ModifyQuantityOfProduct extends FrontCommand{

    @Override
    public void process() {
        try {
            CartLocal cart = initCart();
            ConcurrentHashMap<Product,Integer> products = cart.getProducts();
            String requestedProductId = request.getParameter("productId");
            if (request.getParameter("incrementButton") != null){
                for (Entry<Product,Integer> entry : products.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();
                    if (product.getProductId() == Integer.parseInt(requestedProductId)){
                        products.put(product, quantity + 1);
                        break;
                    }
                }
            }
            if (request.getParameter("decrementButton") != null){
                for (Entry<Product,Integer> entry : products.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();
                    if(product.getProductId() == Integer.parseInt(requestedProductId) && (quantity - 1) == 0){
                        cart.delFromCart(product);
                        break;
                    }
                    else if (product.getProductId() == Integer.parseInt(requestedProductId) && (quantity - 1) >= 0){
                        products.put(product, quantity - 1);
                        break;
                    }
                }    
            }    
            forward(CART_PATH);
        } 
        catch (NamingException | ServletException | IOException ex) {
        }
    }
}
