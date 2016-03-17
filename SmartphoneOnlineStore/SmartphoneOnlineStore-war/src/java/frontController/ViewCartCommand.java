package frontController;

import java.io.IOException;
import javax.naming.NamingException;
import javax.servlet.ServletException;

public class ViewCartCommand extends FrontCommand{

    @Override
    public void process(){
        try {
            initCart();
            forward(CART_PATH);
        } catch (NamingException ex) {
        } catch (ServletException | IOException ex) {
        }
    }
}
