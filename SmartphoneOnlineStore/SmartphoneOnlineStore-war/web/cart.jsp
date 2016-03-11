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
                out.println("<p>El carrito se encuentra vacío</p>");
            }
            else{
                List<Product> products = cart.getProductList();
                out.println("<div class=\"container\">");
                for (Product product: products){
                    out.println("<div class=\"row\">");
                    out.println("<div class=\"col-md-1\">");
                    out.println("<p>" + product.getDescription() + "</p>");
                    out.println("</div>");
                    out.println("<div class=\"col-md-1\">");
                    out.println("<p>" + product.getPurchaseCost()+ "</p>");
                    out.println("</div>");
                    out.println("</div>");
                }
                out.println("<div class=\"col-md-1\">");
                    out.println("<p>TOTAL: " + cart.calculateTotal() +" €</p>");
                    out.println("</div>");
                out.println("</div>");
            }
        %>    
        <div w3-include-HTML="library/footer.html"></div>
        <script src="http://code.jquery.com/jquery.js"></script> 
        <script src="resources/js/bootstrap.js"></script>
        <script src="resources/js/w3-include-HTML.js"></script>
    </body>
</html>
