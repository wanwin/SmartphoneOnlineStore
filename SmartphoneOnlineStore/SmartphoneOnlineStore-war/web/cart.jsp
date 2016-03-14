<%@page import="java.util.List"%>
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
        <div w3-include-HTML="library/navbar.html"></div>
        <%
            CartLocal cart = (CartLocal) session.getAttribute("cart");
            if (cart == null || cart.getProductList().isEmpty()){
                out.println("<div class=\"text-center bg-warning alert-warning\"><h1>El carrito se encuentra vacío.</h1></div>");
                out.println("<form class=\"text-center\" action=\"FrontControllerServlet\">");
                out.println("<input type=\"hidden\" name=\"command\" value=\"FindProductCommand\">");
                out.println("<input class=\"btn btn-continueShopping\" type=\"submit\" value=\"Seguir Comprando\">");
                out.println("</form>");
            }
            else{
                List<Product> products = cart.getProductList();
                out.println("<div class=\"container\">");
                    out.println("<h2>Productos añadidos al carrito</h2>");
                    out.println("<table class=\"table table-hover\">");
                        out.println("<thead>");
                            out.println("<tr>");
                                out.println("<th>IMAGEN</th>");
                                out.println("<th>PRODUCTO</th>");
                                out.println("<th>PRECIO</th>");
                                out.println("<th>¿ELIMINAR?</th>");
                            out.println("</tr>");
                        out.println("</thead>");
                        out.println("<tbody>");
                for (Product product: products){
                            out.println("<tr>");
                                out.println("<td><img src=\"http://www.entrecomics.com/wp-content/uploads/2007/08/cellphone.gif\" width=\"50\" height=\"50\"></td>");
                                out.println("<td>" + product.getDescription() + "</td>");
                                out.println("<td>" + product.getPurchaseCost() + "</td>");
                                out.println("<td>");
                                    out.println("<form action=\"FrontControllerServlet\">");
                                    out.println("<input type=\"hidden\" name=\"command\" value=\"DelFromCartCommand\">");
                                    out.println("<input type=\"hidden\" name=\"productId\" value=" + product.getProductId() + ">");
                                    out.println("<input class=\"btn btn-delFromCart\" type=\"submit\" value=\"Eliminar del carrito\">");
                                    out.println("</form>");
                                out.println("</td>");
                            out.println("</tr>");
                    
                }
                            out.println("<thead>");
                            out.println("<tr>");
                                out.println("<th></th>");
                                out.println("<th></th>");
                                out.println("<th></th>");
                                out.println("<th class=\"info\">TOTAL: " + cart.calculateTotal() +" €</th>");
                            out.println("</tr>");
                        out.println("</thead>");
                        out.println("<tbody>");
                            out.println("<tr>");
                                out.println("<td colspan=\"4\" style=\"text-align:center\">");
                                    out.println("<form action=\"FrontControllerServlet\">");
                                    out.println("<input type=\"hidden\" name=\"command\" value=\"FindProductCommand\">");
                                    out.println("<input class=\"btn btn-continueShopping\" type=\"submit\" value=\"Seguir Comprando\">");
                                    out.println("</form>");
                                out.println("</td>");
                            out.println("</tr>");
                        out.println("</tbody>");
                        out.println("</tbody>");
                    out.println("</table>");
                out.println("</div>");
            }
        %>    
        <div w3-include-HTML="library/footer.html"></div>
        <script src="http://code.jquery.com/jquery.js"></script> 
        <script src="resources/js/bootstrap.js"></script>
        <script src="resources/js/w3-include-HTML.js"></script>
    </body>
</html>
