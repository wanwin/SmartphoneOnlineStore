<%-- 
    Document   : products
    Created on : 09-mar-2016, 0:33:52
    Author     : evelin
--%>

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
        <title>Movilazos.es</title>
    </head>
    <body>
        <div w3-include-HTML="library/navbar.html"></div>
        <%
            List<Product> products = (List<Product>)request.getAttribute("Products");
            int numberOfProductsInThisRow = 0;
            if (products != null){
                out.println("<div class=\"container\">");
                for (Product product: products){
                    if (numberOfProductsInThisRow % 3 == 0){
                        out.println("<div class=\"row\">");
                    }    
                    out.println("<div class=\"col-md-1 col-md-offset-2\">");
                    out.println("<img class=\"img-responsive\" src=\"http://www.entrecomics.com/wp-content/uploads/2007/08/cellphone.gif\">");
                    out.println("<p class=\"text-center\">" + product.getDescription() + "<br>" + product.getPurchaseCost()+ " €</p>");
                    out.println("<form action=\"FrontControllerServlet\">");
                    out.println("<input type=\"hidden\" name=\"command\" value=\"AddToCart\">");
                    out.println("<input class=\"btn btn-default center-block\" type=\"submit\" value=\"Añadir al carrito\">");
                    out.println("</form>");
                    out.println("</div>");
                    numberOfProductsInThisRow++;
                    if (numberOfProductsInThisRow % 3 == 0){
                        out.println("</div>");
                    }  
                }
                out.println("</div>");
            }
        %>
        <div w3-include-HTML="library/footer.html"></div>
        <script src="http://code.jquery.com/jquery.js"></script> 
        <script src="resources/js/bootstrap.js"></script>
        <script src="resources/js/w3-include-HTML.js"></script>
    </body>
</html>
