package userBeans;

import entity.Product;
import java.util.HashMap;
import javax.ejb.Local;
import javax.servlet.http.HttpSession;

@Local
public interface CartLocal {
    public float calculateTotal();
    public void addToCart(Product product);
    public void delFromCart(Product product);
    public void finishPurchase(HttpSession session);
    public HashMap<Product,Integer> getProducts();
}


