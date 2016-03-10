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
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="index.jsp">
                        <img src="resources/img/logo_horizontal.png" class="img-responsive"></a>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="index.jsp">INICIO</a></li>
                        <li><a href="products.jsp">PRODUCTOS</a></li>
                        <li><a href="">CONTACTO</a></li>
                        <li><a href="">INICIAR SESIÓN</a></li>
                        <li><a href=""><img src="resources/img/carrito.png" 
                                            class="glyphicon-shopping-cart"></a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <%
            List<Product> products = (List<Product>)request.getAttribute("Products");
            if (products != null){
                out.println("<div class=\"container-fluid\">");
                for (Product product: products){
                    out.println("<p>ID: " + product.getProductId() + ", Description: " + product.getDescription() + "</p>");
                }
                out.println("</div>");
            }
        %>
        <footer class="container-fluid text-center">
            <p style="font-style: italic;color: #000;font-size: small">Copyright 2016 - Darwin Hamad y Evelin Rodrguez</p>
        </footer>
        <script src="http://code.jquery.com/jquery.js"></script> 
        <script src="resources/js/bootstrap.js"></script>
    </body>
</html>
