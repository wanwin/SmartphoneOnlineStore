package userBeans;

import entity.Product;
import java.util.List;
import javax.ejb.Stateful;

@Stateful
public class Cart implements CartLocal {

    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }
    
    @Override
    public int calculateTotal() {
        int sum = 0;
        for (Product product : productList) {
            sum += product.getPurchaseCost().intValue();
        }
        return sum;
    }
    
}
