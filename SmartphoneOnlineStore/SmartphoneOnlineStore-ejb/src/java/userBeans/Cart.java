package userBeans;

import entity.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.servlet.http.HttpSession;

@Stateful
public class Cart implements CartLocal {

    private List<Product> productList = new ArrayList<>();
    
    public List<Product> getProductList() {
        return productList;
    }
    
    @Override
    public void addToCart(Product product) {
        productList.add(product);
    }
    
    @Override
    public float calculateTotal() {
        float sum = 0;
        for (Product product : productList) {
            sum += product.getPurchaseCost().floatValue();
        }
        return sum;
    }


    @Override
    @Remove
    public void finishPurchase(HttpSession session) {
        session.invalidate();
    }

    @Override
    public void delFromCart(Product product) {
        productList.remove(product);
    }
    
}
