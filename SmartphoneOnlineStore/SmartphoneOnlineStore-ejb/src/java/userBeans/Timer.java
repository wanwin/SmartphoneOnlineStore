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
    
    @Schedule(dayOfWeek = "Sun")
    @Override
    public void setDiscount(){
        try {
            ProductFacadeLocal productFacade = InitialContext.doLookup("java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/ProductFacade!controller.ProductFacadeLocal");
            List<Product> products = productFacade.findAll();
            for (Product product : products) {
                product.setDiscount(30);
                productFacade.edit(product);
            }
        } catch (NamingException ex) {
        }
    }
    
    @Schedule(dayOfWeek = "Tue")
    @Override
    public void unsetDiscount(){
        try {
            ProductFacadeLocal productFacade = InitialContext.doLookup("java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/ProductFacade!controller.ProductFacadeLocal");
            List<Product> products = productFacade.findAll();
            for (Product product : products) {
                product.setDiscount(0);
                productFacade.edit(product);
            }
        } catch (NamingException ex) {
        }
    }
}
