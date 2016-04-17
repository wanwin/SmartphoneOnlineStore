<%@page import="javax.naming.InitialContext"%>
<%@page import="userBeans.StatisticsLocal"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="entity.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="resources/css/bootstrap.css" rel="stylesheet" media="screen">
        <link href="resources/stylesheet.css" rel="stylesheet" media="screen">
        <title>Productos de la compra</title>
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
        <%
            ConcurrentHashMap<Product,Integer> products = (ConcurrentHashMap<Product,Integer>) request.getAttribute("purchaseProducts");
            out.println("<table class=\"table table-hover\">");
                    out.println("<thead>");
                        out.println("<tr>");
                            out.println("<th>PRODUCTO</th>");
                            out.println("<th>PRECIO</th>");
                            out.println("<th colspan=\"3\">CANTIDAD</th>");
                            out.println("</tr>");
                    out.println("</thead>");
                    out.println("<tbody>");
                for (Entry<Product,Integer> entry: products.entrySet()) {
                        Product product = entry.getKey();
                        Integer quantity = entry.getValue();
                        out.println("<tr>");
                            out.println("<td>" + product.getManufacturerId().getName() + " " + product.getDescription() + "</td>");
                            out.println("<td>" + product.getPurchaseCost() + "</td>");   
                            out.println("<td>" + quantity + "</td>");   
                        out.println("</tr>");
                }
                    out.println("</tbody>");
            out.println("</table>");
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
