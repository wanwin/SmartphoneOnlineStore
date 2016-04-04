<%@page import="userBeans.StatisticsLocal"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="java.util.Iterator"%>
<%@page import="entity.Manufacturer"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.HashSet"%>
<%@page import="entity.Product"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="controller.ProductFacade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="resources/css/bootstrap.css" rel="stylesheet" media="screen">
        <link href="resources/stylesheet.css" rel="stylesheet" media="screen">
        <title>Productos</title>
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
        %>
        <div w3-include-HTML="library/navbar.jsp"></div>
        <%!
            private void insertBrandsInDropdownList(List<Manufacturer> manufacturers, JspWriter out) throws IOException{
                out.println("<li>");
                        out.println("<form action=\"FrontControllerServlet\">");
                            out.println("<input type=\"hidden\" name=\"command\" value=\"FindProductCommand\">");
                            out.println("<input class=\"dropdownButton\" type=\"submit\" value=\"Todas\">");
                        out.println("</form>");
                    out.println("</li>");
                for (Manufacturer manufacturer: manufacturers){
                    //out.println("<li><a href=\"#\">" + manufacturer.getName() + "</a></li>");
                    out.println("<li>");
                        out.println("<form action=\"FrontControllerServlet\">");
                            out.println("<input type=\"hidden\" name=\"command\" value=\"FindProductCommand\">");
                            out.println("<input type=\"hidden\" name=\"manufacturer\" value=" + manufacturer.getManufacturerId() + ">");
                            out.println("<input class=\"dropdownButton\" type=\"submit\" value=" + manufacturer.getName() + ">");
                        out.println("</form>");
                    out.println("</li>");
                }
            }
            
            private String obtainCssClassOfAddToCartButton(Product product){
                if (product.getQuantityOnHand() > 0){
                    return "<input class=\"btn btn-lg\" type=\"submit\" value=\"Añadir al carrito\">";
                }else{
                    return "<input class=\"btn disabled\" value=\"Añadir al carrito\">";
                }
            }
        %>
        
        <%
            List<Manufacturer> manufacturers = (List<Manufacturer>)request.getAttribute("Manufacturers");
            List<Product> products = (List<Product>)request.getAttribute("Products");
            int numberOfProductsInThisRow = 0;
            if (products != null){
                out.println("<div class=\"container-fluid\">");
                    out.println("<br>");
                    out.println("<div class=\"dropdown \">");
                        out.println("<button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Filtrar por marca");
                        out.println("<span class=\"caret\"></span></button>");
                        out.println("<ul class=\"dropdown-menu scrollable-menu\">");
                        insertBrandsInDropdownList(manufacturers, out);
                        out.println("</ul>");
                     out.println("</div>");
                out.println("</div>");
                out.println("<br>");
                Iterator productsIterator = products.iterator();
                while (productsIterator.hasNext()){
                    Product product = (Product) productsIterator.next();
                    if (numberOfProductsInThisRow % 3 == 0){
                        out.println("<div class=\"row\">");
                    }    
                            out.println("<div class=\"col-md-4\">");
                                out.println("<div class=\"panel panel-default text-center\">");
                                    out.println("<div class=\"panel-heading\">");
                                        out.println("<h3>" + product.getManufacturerId().getName() + "<br>" + product.getDescription() + "</h3>");
                                    out.println("</div>");
                                    out.println("<div class=\"panel-body\">");
                                        out.println("<img src=\"http://www.entrecomics.com/wp-content/uploads/2007/08/cellphone.gif\" width=\"100\" height=\"100\" alt=\"Móvil_img\">");
                                    out.println("</div>");
                                    out.println("<div class=\"panel-footer\">");
                                        out.println("<h3>" + product.getPurchaseCost() + "€</h3>");
                                        out.println("<h5>Stock: " + product.getQuantityOnHand() + "</h5>");
                                        out.println("<form action=\"FrontControllerServlet\">");
                                        out.println("<input type=\"hidden\" name=\"command\" value=\"AddToCartCommand\">");
                                        out.println("<input type=\"hidden\" name=\"productId\" value=" + product.getProductId() + ">");
                                        out.println(obtainCssClassOfAddToCartButton(product));
                                        out.println("</form>");
                                    out.println("</div>");
                                out.println("</div>");
                            out.println("</div>");
                    numberOfProductsInThisRow++;
                    if (numberOfProductsInThisRow % 3 == 0 || !productsIterator.hasNext()){
                        out.println("</div>");
                    }  
                }
            }
        %>
        <div class="text-center">
            <ul class="pagination">
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
            </ul>
        </div>
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