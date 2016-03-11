package userBeans;

import entity.Product;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpSession;

@Local
public interface CartLocal {
    public float calculateTotal();
    public void addToCart(Product product);
    public void delFromCart(Product product);
    public void finishPurchase(HttpSession session);
    public List<Product> getProductList();
}


