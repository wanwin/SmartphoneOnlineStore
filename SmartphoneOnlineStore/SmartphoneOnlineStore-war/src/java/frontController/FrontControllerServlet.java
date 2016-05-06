package frontController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/FrontControllerServlet"})
public class FrontControllerServlet extends HttpServlet {

    private static final String PACKAGE_NAME = "frontController.";
    private static final String PARAMETER_NAME = "command";
    private static final String PARAMETER_PAGINATION = "pagination";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String commandPath = PACKAGE_NAME + request.getParameter(PARAMETER_NAME);
            try {
                FrontCommand action1 = (FrontCommand) createCommandInstance(commandPath);
                if (request.getParameter("pagination") != null){
                    action1.modifyStringPaginationNumber(request.getParameter(PARAMETER_PAGINATION));
                }
                action1.init(getServletContext(), request, response);
                action1.process();
            } catch (InstantiationException | IllegalAccessException ex) {
            }
        } catch (ClassNotFoundException ex) {
        }
    }

    private static Object createCommandInstance(String command) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return Class.forName(command).newInstance();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
