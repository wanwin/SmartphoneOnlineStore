package userBeans;

import entity.Product;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.Local;
import javax.servlet.http.HttpSession;

@Local
public interface CartLocal {
    public float calculateTotal();
    public void addToCart(Product product);
    public void delFromCart(Product product);
    public void finishPurchase(HttpSession session);
    public ConcurrentHashMap<Product,Integer> getProducts();
    public ConcurrentHashMap<Product,Integer> getProducts2();
    public int getTotalOfProducts();
}


