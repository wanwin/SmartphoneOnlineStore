package frontController;

import entity.Product;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import userBeans.CartLocal;

public class ModifyQuantityOfProduct extends FrontCommand{

    @Override
    public void process() {
        try {
            CartLocal cart = initCart();
            ConcurrentHashMap<Product,Integer> products = cart.getProducts();
            ConcurrentHashMap<Product,Integer> products2 = cart.getProducts2();
            String requestedProductId = request.getParameter("productId");
            int requestedProductDiscount = Integer.parseInt(request.getParameter("productDiscount"));
            if (request.getParameter("incrementButton") != null){
                for (Entry<Product,Integer> entry : products.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();
                    if (product.getProductId() == Integer.parseInt(requestedProductId) && product.getDiscount() == requestedProductDiscount){
                        products.put(product, quantity + 1);
                        break;
                    }
                }
                for (Entry<Product,Integer> entry : products2.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();
                    if (product.getProductId() == Integer.parseInt(requestedProductId) && product.getDiscount() == requestedProductDiscount){
                        products2.put(product, quantity + 1);
                        break;
                    }
                }
            }
            if (request.getParameter("decrementButton") != null){
                for (Entry<Product,Integer> entry : products.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();
                    if (product.getProductId() == Integer.parseInt(requestedProductId) && product.getDiscount() == requestedProductDiscount 
                        && (quantity - 1) == 0){
                        cart.delFromCart(product, products);
                        break;
                    }
                    else if (product.getProductId() == Integer.parseInt(requestedProductId) && product.getDiscount() == requestedProductDiscount 
                            && (quantity - 1) >= 0){
                        products.put(product, quantity - 1);
                        break;
                    }
                }
                for (Entry<Product,Integer> entry : products2.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();
                    if (product.getProductId() == Integer.parseInt(requestedProductId) && product.getDiscount() == requestedProductDiscount 
                        && (quantity - 1) == 0){
                        cart.delFromCart(product, products2);
                        break;
                    }
                    else if (product.getProductId() == Integer.parseInt(requestedProductId) && product.getDiscount() == requestedProductDiscount 
                            && (quantity - 1) >= 0){
                        products2.put(product, quantity - 1);
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
