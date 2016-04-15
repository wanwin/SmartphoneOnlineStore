package frontController;

import controller.CustomerFacadeLocal;
import controller.PurchaseOrderFacadeLocal;
import entity.Customer;
import entity.Product;
import entity.PurchaseOrder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

public class ShowPreviousPurchasesCommand extends FrontCommand{

    @Override
    public void process() {
        try {
            PurchaseOrderFacadeLocal purchaseOrderFacade = InitialContext.doLookup(PURCHASE_ORDER_JNDI_URL);
            CustomerFacadeLocal customerFacade = InitialContext.doLookup(CUSTOMER_JNDI_URL);
            String dni = request.getParameter("dni");
            Customer customer = customerFacade.find(dni);
            if (customer != null){ 
                List<PurchaseOrder> purchaseOrders = purchaseOrderFacade.findAll();
                List<ConcurrentHashMap<Product,Integer>> productsOfPurchases = new ArrayList<ConcurrentHashMap<Product,Integer>>();
                for (PurchaseOrder purchaseOrder : purchaseOrders) {
                    ConcurrentHashMap<Product, Integer> products;
                    if (purchaseOrder.getCustomerId().equals(customer)){
                        FileInputStream fileInputStream = new FileInputStream("products.ser");
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                        products = (ConcurrentHashMap<Product,Integer>) objectInputStream.readObject();
                        objectInputStream.close();   
                        productsOfPurchases.add(products);
                    } 
                    request.setAttribute("purchases", productsOfPurchases);
                }
                forward(PREVIOUS_PURCHASES_PATH);
            }    
        } catch (NamingException | FileNotFoundException ex) {
        } catch (IOException | ClassNotFoundException | ServletException ex) {
        }
    }
}
