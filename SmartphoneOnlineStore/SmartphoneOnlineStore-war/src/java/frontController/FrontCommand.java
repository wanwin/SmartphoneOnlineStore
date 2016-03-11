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
    protected static final String CART_JNDI_URL = "java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/Cart!userBeans.CartLocal";
    protected static final String CART_PATH = "/cart.jsp";
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

    protected CartLocal initCart(CartLocal cart, HttpSession session) throws NamingException {
        if (cart == null) {
            cart = InitialContext.doLookup(AddToCartCommand.CART_JNDI_URL);
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
