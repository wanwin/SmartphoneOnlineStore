package frontController;

import controller.CustomerFacadeLocal;
import controller.PurchaseOrderFacadeLocal;
import entity.Customer;
import entity.PurchaseOrder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

public class SearchPreviousPurchasesCommand extends FrontCommand{

    @Override
    public void process() {
        try {
            PurchaseOrderFacadeLocal purchaseOrderFacade = InitialContext.doLookup(PURCHASE_ORDER_JNDI_URL);
            CustomerFacadeLocal customerFacade = InitialContext.doLookup(CUSTOMER_JNDI_URL);
            String dni = request.getParameter("dni");
            Customer customer = customerFacade.find(dni);
            if (customer != null){ 
                List<PurchaseOrder> purchaseOrders = purchaseOrderFacade.findAll();
                List<PurchaseOrder> previousPurchaseOrders = new ArrayList<PurchaseOrder>();
                for (PurchaseOrder purchaseOrder : purchaseOrders) {
                    if (purchaseOrder.getCustomerId().equals(customer)){
                        previousPurchaseOrders.add(purchaseOrder);
                    } 
                    request.setAttribute("purchases", previousPurchaseOrders);
                }
                forward(PREVIOUS_PURCHASES_PATH);
            }    
        } 
        catch (NamingException | ServletException | IOException ex) {
        }
    }
}
