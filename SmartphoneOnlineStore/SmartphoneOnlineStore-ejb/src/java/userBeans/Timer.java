package userBeans;

import controller.ProductFacadeLocal;
import entity.Product;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Singleton
public class Timer implements TimerLocal {
    
    @Override
    public void initTimer(){
        Calendar calendar = new GregorianCalendar();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek){
            case 3: setDiscount(); // los martes hay descuento
                    break;
            case 1: case 2: case 4: case 5: case 6: case 7: unsetDiscount();
                    break;
            default: break;
        }
    }
    
    @Schedule(dayOfWeek = "Tue")
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

    @Schedule(dayOfWeek = "San,Mon,Wed,Thu,Fri,Sat")
    public void unsetDiscount() {
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
