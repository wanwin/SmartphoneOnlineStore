<%@page import="java.io.IOException"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="userBeans.StatisticsLocal"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="entity.Product"%>
<%@page import="userBeans.CartLocal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="resources/css/bootstrap.css" rel="stylesheet" media="screen">
        <link href="resources/stylesheet.css" rel="stylesheet" media="screen">
        <title>Carrito</title>
    </head>
    <body>
        <%!
            public StatisticsLocal getStatisticsSingleton(HttpSession session, HttpServletRequest request) throws NamingException{
                StatisticsLocal statistics = (StatisticsLocal )session.getAttribute("Statistics");
                if (session.getAttribute("Statistics") == null){
                    statistics = InitialContext.doLookup("java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/Statistics!userBeans.StatisticsLocal");
                    session.setAttribute("Statistics", statistics);
                }    
                return statistics;
            }
            
            public Float calculatePriceWithDiscount(Product product){
                Float productPrice = product.getPurchaseCost().floatValue();
                Integer productDiscount = product.getDiscount();
                return productPrice - productPrice * productDiscount / 100;
            }
            
            public void printProducts(ConcurrentHashMap<Product,Integer> products, JspWriter out) throws IOException{
                for (Entry<Product,Integer> entry: products.entrySet()){
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();
                            out.println("<tr>");
                                out.println("<td><a href=" + product.getImage() + "><img src=" + product.getImage() + " width=\"100\" height=\"100\" alt=\"Móvil_img\" title=\"Ampliar imagen\"></a></td>");
                                out.println("<td>" + product.getManufacturerId().getName() + " " + product.getDescription() + "</td>");
                                if (product.getDiscount() > 0){
                                    out.println("<td><strike>" + product.getPurchaseCost().floatValue() + "</strike> <p class=\"lead\">" + calculatePriceWithDiscount(product) + "</p></td>");
                                }
                                else{
                                    out.println("<td>" + product.getPurchaseCost() + "</td>");
                                }
                                out.println("<td class=\"text-center\">");
                                    out.println("<p>" + quantity + "</p>");
                                    out.println("<form action=\"FrontControllerServlet\">");
                                    out.println("<input type=\"hidden\" name=\"command\" value=\"ModifyQuantityOfProduct\">");
                                    out.println("<input type=\"hidden\" name=\"productId\" value=" + product.getProductId() + ">");
                                    out.println("<input class=\"btn btn-incrementProduct position-relative\" name=\"incrementButton\" value=\"\" type=\"submit\">");
                                    out.println("</form>");
                                    out.println("<form action=\"FrontControllerServlet\">");
                                    out.println("<input type=\"hidden\" name=\"command\" value=\"ModifyQuantityOfProduct\">");
                                    out.println("<input type=\"hidden\" name=\"productId\" value=" + product.getProductId() + ">");
                                    out.println("<input class=\"btn btn-decrementProduct position-relative\" name=\"decrementButton\" value=\"\" type=\"submit\">");
                                    out.println("</form>");
                                out.println("</td>");
                                
                                out.println("<td>");
                                    out.println("<form action=\"FrontControllerServlet\">");
                                    out.println("<input type=\"hidden\" name=\"command\" value=\"DelFromCartCommand\">");
                                    out.println("<input type=\"hidden\" name=\"productId\" value=" + product.getProductId() + ">");
                                    out.println("<input class=\"btn btn-delFromCart\" type=\"submit\" value=\"Eliminar del carrito\">");
                                    out.println("</form>");
                                out.println("</td>");
                            out.println("</tr>");
                }    
            }
        %> 
        <div w3-include-HTML="library/navbar.jsp"></div>
        <%
            CartLocal cart = (CartLocal) session.getAttribute("cart");
            DecimalFormat df = new DecimalFormat("0.00");
            boolean areThereAnyErrors = false;
            if (cart == null || cart.getProducts().isEmpty()){
                out.println("<div class=\"text-center bg-warning alert-warning\"><h1>El carrito se encuentra vacío.</h1></div>");
                out.println("<form class=\"text-center\" action=\"FrontControllerServlet\">");
                out.println("<input type=\"hidden\" name=\"command\" value=\"FindProductCommand\">");
                out.println("<input type=\"hidden\" name=\"pagination\" value=\"1\">");
                out.println("<input class=\"btn btn-continueShopping\" type=\"submit\" value=\"Seguir Comprando\">");
                out.println("</form><br>");
            }
            else{
                ConcurrentHashMap<Product,Integer> products = cart.getProducts();
                ConcurrentHashMap<Product,Integer> products2 = cart.getProducts2();
                out.println("<div class=\"container\">");
                    out.println("<h2>Productos añadidos al carrito</h2>");
                    for (Entry<Product,Integer> entry: products.entrySet()){
                        Product product = entry.getKey();
                        Integer quantity = entry.getValue();
                        if(quantity > product.getQuantityOnHand()){
                            areThereAnyErrors = true;
                            out.println("<div class=\"text-center bg-danger alert-danger\"><h2>No hay unidades suficientes para el producto " + product.getManufacturerId().getName()+ " " + product.getDescription() + "</h2></div>");
                        }
                    }
                    out.println("<table class=\"table table-hover table-vcenter table-striped text-center table-condensed\">");
                        out.println("<thead>");
                            out.println("<tr>");
                                out.println("<th>IMAGEN</th>");
                                out.println("<th>PRODUCTO</th>");
                                out.println("<th>PRECIO (€)</th>");
                                out.println("<th colspan=\"\">CANTIDAD</th>");
                                out.println("<th>¿ELIMINAR?</th>");
                            out.println("</tr>");
                        out.println("</thead>");
                        out.println("<tbody>");
                printProducts(products, out);
                printProducts(products2, out);
                            out.println("<thead>");
                            out.println("<tr>");
                                out.println("<th></th>");
                                out.println("<th></th>");
                                out.println("<th></th>");
                                out.println("<th class=\"info\">TOTAL: " + df.format(cart.calculateTotal()) +" €</th>");
                            out.println("</tr>");
                        out.println("</thead>");
                        out.println("<tbody>");
                            out.println("<tr>");
                                out.println("<td colspan=\"3\" style=\"text-align:center\">");
                                    out.println("<form action=\"FrontControllerServlet\">");
                                    out.println("<input type=\"hidden\" name=\"command\" value=\"FindProductCommand\">");
                                    out.println("<input type=\"hidden\" name=\"pagination\" value=\"1\">");
                                    out.println("<input class=\"btn btn-continueShopping\" type=\"submit\" value=\"Seguir Comprando\">");
                                    out.println("</form>");
                                out.println("</td>");
                                out.println("<td colspan=\"1\" style=\"text-align:center\">");
                                    out.println("<form action=\"purchase.jsp\">");
                                    if (areThereAnyErrors){
                                        out.println("<input class=\"btn disabled\" value=\"Finalizar compra\">");
                                    }
                                    else{
                                        out.println("<input class=\"btn btn-purchase\" type=\"submit\" value=\"Tramitar Pedido\">");
                                    }
                                    out.println("</form>");
                                out.println("</td>");
                            out.println("</tr>");
                        out.println("</tbody>");
                    out.println("</table>");
                out.println("</div>");
            }
        %>    
        <div w3-include-HTML="library/footer.html"></div>
        <%
            StatisticsLocal statistics = getStatisticsSingleton(session, request);
            if (session.isNew()){
                statistics.incrementNumberOfUsersConnected();
            }
            out.println("<div class=\"text-center\">");
            out.println("<p>Usuarios conectados: " + statistics.getNumberOfUsersConnected() + "</p>");
            out.println("</div>");
        %> 
        <script src="http://code.jquery.com/jquery.js"></script> 
        <script src="resources/js/bootstrap.js"></script>
        <script src="resources/js/w3-include-HTML.js"></script>
    </body>
</html>
