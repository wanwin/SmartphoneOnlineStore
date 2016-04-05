package frontController;

import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.jsp.JspWriter;

public class PurchaseCommand extends FrontCommand{

    @Override
    public void process() {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()){
            HashMap<Product, Integer> products = initCart().getProducts();
            out.println("<!DOCTYPE html>\n" +
"<html>\n" +
"    <head>\n" +
"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
"        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
"        <link href=\"resources/css/bootstrap.css\" rel=\"stylesheet\" media=\"screen\">\n" +
"        <link href=\"resources/stylesheet.css\" rel=\"stylesheet\" media=\"screen\">\n" +
"        <title>Movilazos.es</title>\n" +
"    </head>\n" +
"    <body>\n" +
"        \n" +
"        <div w3-include-HTML=\"library/navbar.jsp\"></div>\n" +
"        <div class=\"text-center bg-success success\"><h1>Ha realizado su compra correctamente</h1></div>\n" +
"        <div w3-include-HTML=\"library/footer.html\"></div>\n" +
"        <script src=\"http://code.jquery.com/jquery.js\"></script> \n" +
"        <script src=\"resources/js/bootstrap.js\"></script>\n" +
"        <script src=\"resources/js/w3-include-HTML.js\"></script>\n" +
"    </body>\n" +
"</html>");
        } catch (IOException ex) {
        } catch (NamingException ex) {
            Logger.getLogger(PurchaseCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
