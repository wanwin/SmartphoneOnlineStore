package frontController;

import java.io.IOException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import userBeans.CartLocal;

public class ViewCartCommand extends FrontCommand{

    @Override
    public void process(){
        try {
            HttpSession session = getSession(request);
            CartLocal cart = (CartLocal) session.getAttribute("cart");
            initCart(cart, session);
            forward(CART_PATH);
        } catch (NamingException ex) {
        } catch (ServletException | IOException ex) {
        }
    }
}
