package userBeans;

import controller.ProductFacadeLocal;
import entity.Product;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Singleton
public class Timer implements TimerLocal {
    
    boolean thereIsDiscount = false;
    
    @Schedule(hour = "*", minute = "*", second = "*/15")
    @Override
    public void establishDiscount(){
        try {
            int discount;
            if (thereIsDiscount){
                discount = 30;
            }
            else{
                discount = 0;
            }
            setProductDiscount(discount);   
            thereIsDiscount = !thereIsDiscount; 
        } 
        catch (NamingException ex) {
        }
    }

    private void setProductDiscount(int discount) throws NamingException {
        ProductFacadeLocal productFacade = InitialContext.doLookup("java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/ProductFacade!controller.ProductFacadeLocal");
        List<Product> products = productFacade.findAll();
        for (Product product : products) {
            product.setDiscount(discount);
            productFacade.edit(product);
        }
    }
}
