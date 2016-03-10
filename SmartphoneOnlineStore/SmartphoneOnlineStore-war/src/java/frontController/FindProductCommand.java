/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import controller.ProductFacadeLocal;
import entity.Product;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

/**
 *
 * @author Darwin
 */
public class FindProductCommand extends FrontCommand{

    @Override
    public void process(){
        RequestDispatcher dispatcher = context.getRequestDispatcher("/products.jsp");
        try {
            ProductFacadeLocal productFacade = (ProductFacadeLocal) InitialContext.doLookup("java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/ProductFacade!controller.ProductFacadeLocal");
            List<Product> products = productFacade.findAll();
            request.setAttribute("Products", products);
            try {
                dispatcher.forward(request, response);
            } catch (ServletException ex) {
            } catch (IOException ex) {
            }
        } catch (NamingException ex) {
        }
    }
}
