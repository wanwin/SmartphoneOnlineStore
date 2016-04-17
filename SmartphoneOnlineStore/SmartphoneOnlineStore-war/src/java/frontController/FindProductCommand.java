package frontController;

import controller.ManufacturerFacadeLocal;
import controller.ProductFacadeLocal;
import entity.Manufacturer;
import entity.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

public class FindProductCommand extends FrontCommand{

    @Override
    public void process(){
        try {
            ManufacturerFacadeLocal manufacturerFacade = (ManufacturerFacadeLocal) InitialContext.doLookup(MANUFACTURER_JNDI_URL);
            ProductFacadeLocal productFacade = (ProductFacadeLocal) InitialContext.doLookup(PRODUCT_JNDI_URL);
            String manufacturerID = request.getParameter("manufacturer");
            List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
            List<Product> products = new ArrayList<Product>();
            if (manufacturerID != null){
                ArrayList<Product> productsToRemove = new ArrayList<>();
                manufacturers.add(manufacturerFacade.find(Integer.parseInt(manufacturerID)));
                products.addAll(productFacade.findAll());
                for (Product product : products) {
                    if (product.getManufacturerId().getManufacturerId() != Integer.parseInt(manufacturerID)){
                        productsToRemove.add(product);
                    }
                }
                products.removeAll(productsToRemove);
            }
            else{
                manufacturers.addAll(manufacturerFacade.findAll());
                products.addAll(productFacade.findAll());
            }
            request.setAttribute("Manufacturers", manufacturers);
            request.setAttribute("Products", products);
            forward(PRODUCTS_PATH);
        } 
        catch (NamingException | ServletException | IOException ex) {
        }
    }
}
