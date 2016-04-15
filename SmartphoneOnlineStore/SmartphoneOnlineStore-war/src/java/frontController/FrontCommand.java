package frontController;

import java.io.IOException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import userBeans.CartLocal;

abstract class FrontCommand {
    protected static final String PRODUCT_JNDI_URL = "java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/ProductFacade!controller.ProductFacadeLocal";
    protected static final String CART_JNDI_URL = "java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/Cart!userBeans.CartLocal";
    protected static final String MANUFACTURER_JNDI_URL = "java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/ManufacturerFacade!controller.ManufacturerFacadeLocal";
    protected static final String PURCHASE_ORDER_JNDI_URL = "java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/PurchaseOrderFacade!controller.PurchaseOrderFacadeLocal";
    protected static final String CUSTOMER_JNDI_URL = "java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/CustomerFacade!controller.CustomerFacadeLocal";
    protected static final String PRODUCTS_PATH = "/products.jsp";
    protected static final String CART_PATH = "/cart.jsp";
    protected static final String PURCHASE_FINISHED_PATH = "/purchaseFinished.jsp";
    protected static final String PREVIOUS_PURCHASES_PATH = "/previousPurchases.jsp";
    
    
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    
    public void init(ServletContext context, HttpServletRequest request, 
            HttpServletResponse response){
        this.context = context;
        this.request = request;
        this.response = response;
    }
    
    abstract public void process();
    
    public void forward(String target) throws ServletException, IOException{
        RequestDispatcher requestDispatcher = context.getRequestDispatcher(target);
        requestDispatcher.forward(request, response);
    }

    protected HttpSession getSession(HttpServletRequest request) {
        return request.getSession(true);
    }

    protected CartLocal initCart() throws NamingException {
        HttpSession session = getSession(request);
        CartLocal cart = (CartLocal) session.getAttribute("cart");
        if (cart == null) {
            cart = InitialContext.doLookup(CART_JNDI_URL);
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
