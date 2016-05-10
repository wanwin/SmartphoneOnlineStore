package userBeans;

import entity.Product;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.servlet.http.HttpSession;

@Stateful
public class Cart implements CartLocal {

    private final ConcurrentHashMap<Product,Integer> products = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Product,Integer> products2 = new ConcurrentHashMap<>();
    
    @Override
    public ConcurrentHashMap<Product,Integer> getProducts() {
        return products;
    }
    
    public ConcurrentHashMap<Product, Integer> getProducts2() {
        return products2;
    }
    
    @Override
    public void addToCart(Product product) {
        if (products.containsKey(product)){
            boolean isProductWithDiscountInCart = false;
            for (Entry<Product,Integer> entry : products.entrySet()) {
                Product productInCart = entry.getKey();
                if (productInCart.equals(product)){
                    if (productInCart.getDiscount() != product.getDiscount()){
                        if (products2.containsKey(product)){
                            products2.put(product, products2.get(product) + 1);        
                        }
                        else{
                            products2.put(product, 1);    
                        }
                        isProductWithDiscountInCart = true;
                    }    
                }
            }
            if (isProductWithDiscountInCart){
                products.put(product, products.get(product) + 1);
            }
        }
        else{
            products.put(product, 1);
        }
    }
    
    @Override
    public float calculateTotal() {
        return calculateTotal(products) + calculateTotal(products2);
    }

    private float calculateTotal(ConcurrentHashMap<Product,Integer> products) {
        float sum = 0;
        for (Entry<Product,Integer> entry: products.entrySet()){
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            Float productPrice = product.getPurchaseCost().floatValue();
            Integer productDiscount = product.getDiscount();
            if (productDiscount > 0){
                sum += (productPrice - productPrice * productDiscount / 100) * quantity;     
            }
            else{
                sum += productPrice * quantity;
            }
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
