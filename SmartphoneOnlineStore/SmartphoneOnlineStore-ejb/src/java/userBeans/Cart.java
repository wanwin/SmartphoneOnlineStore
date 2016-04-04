package userBeans;

import entity.Product;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.servlet.http.HttpSession;

@Stateful
public class Cart implements CartLocal {

    private final HashMap<Product,Integer> products = new HashMap<>();
    
    @Override
    public HashMap<Product,Integer> getProducts() {
        return products;
    }
    
    @Override
    public void addToCart(Product product) {
        if (products.containsKey(product)){
            products.put(product, products.get(product) + 1);
        }
        else{
            products.put(product, 1);
        }
    }
    
    @Override
    public float calculateTotal() {
        float sum = 0;
        for (Entry<Product,Integer> entry: products.entrySet()){
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            sum += product.getPurchaseCost().floatValue() * quantity;
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
        products.remove(product);
    }
    
}
