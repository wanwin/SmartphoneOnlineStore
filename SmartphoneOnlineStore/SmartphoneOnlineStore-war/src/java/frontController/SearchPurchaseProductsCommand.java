package frontController;

import controller.PurchaseOrderFacadeLocal;
import entity.Product;
import entity.PurchaseOrder;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

public class SearchPurchaseProductsCommand extends FrontCommand{

    @Override
    public void process() {
        try {
            Integer orderNum = Integer.parseInt(request.getParameter("orderNum"));
            PurchaseOrderFacadeLocal purchaseOrderFacade = InitialContext.doLookup(PURCHASE_ORDER_JNDI_URL);
            PurchaseOrder purchaseOrder = purchaseOrderFacade.find(orderNum);
            ConcurrentHashMap<Product,Integer> products = (ConcurrentHashMap<Product,Integer>) purchaseOrder.getProducts();
            request.setAttribute("purchaseProducts", products);
            forward(PURCHASE_PRODUCTS_PATH);
        } 
        catch (NamingException | IOException | ServletException ex) {
        }
    }
}
