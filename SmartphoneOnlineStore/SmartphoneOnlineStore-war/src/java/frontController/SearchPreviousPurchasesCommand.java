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
            PurchaseOrderFacadeLocal purchaseOrderFacade = InitialContext.doLookup("java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/PurchaseOrderFacade!controller.PurchaseOrderFacadeLocal");
            CustomerFacadeLocal customerFacade = InitialContext.doLookup("java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/CustomerFacade!controller.CustomerFacadeLocal");
            String dni = request.getParameter("dni");
            Customer customer = customerFacade.find(dni);
            if (customer != null){ 
                List<PurchaseOrder> purchaseOrders = purchaseOrderFacade.findAll();// problem here
                List<PurchaseOrder> previousPurchaseOrders = new ArrayList<PurchaseOrder>();
                for (PurchaseOrder purchaseOrder : purchaseOrders) {
                    if (purchaseOrder.getCustomerId().equals(customer)){
                        previousPurchaseOrders.add(purchaseOrder);
                    } 
                    request.setAttribute("purchases", previousPurchaseOrders);
                }
            }
            else{
                request.setAttribute("purchases", null);    
            }
            forward(PREVIOUS_PURCHASES_PATH);
        } 
        catch (NamingException | ServletException | IOException ex) {
        }
    }
}
