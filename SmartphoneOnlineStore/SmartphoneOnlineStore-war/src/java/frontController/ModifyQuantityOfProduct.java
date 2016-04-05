package frontController;

import entity.Product;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.naming.NamingException;
import javax.servlet.ServletException;

public class ModifyQuantityOfProduct extends FrontCommand{

    @Override
    public void process() {
        try {
            HashMap<Product,Integer> products = initCart().getProducts();
            String requestedProductId = request.getParameter("productId");
            if (request.getParameter("incrementButton") != null){
                for (Entry<Product,Integer> entry : products.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();
                    if (product.getProductId() == Integer.parseInt(requestedProductId)){
                        products.put(product, quantity + 1);
                    }
                }
            }
            if (request.getParameter("decrementButton") != null){
                for (Entry<Product,Integer> entry : products.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();
                    if (product.getProductId() == Integer.parseInt(requestedProductId) && (quantity - 1) >= 0){
                        products.put(product, quantity - 1);
                    }
                }    
            }    
            try {
                forward(CART_PATH);
            } catch (ServletException | IOException ex) {
            }
        } catch (NamingException ex) {
        }
    }
}
