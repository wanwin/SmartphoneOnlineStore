package frontController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePageCommand extends FrontCommand{

    @Override
    public void process() {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()){
            out.println("Command1");
        } catch (IOException ex) {
            Logger.getLogger(HomePageCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
