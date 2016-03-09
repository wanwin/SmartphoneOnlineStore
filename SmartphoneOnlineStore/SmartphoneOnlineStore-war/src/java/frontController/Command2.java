package frontController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Command2 extends FrontCommand{

    @Override
    public void process() {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()){
            out.println("Command2");
        } catch (IOException ex) {
            Logger.getLogger(Command2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
