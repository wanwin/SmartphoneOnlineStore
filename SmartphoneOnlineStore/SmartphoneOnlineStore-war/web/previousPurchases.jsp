<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="entity.PurchaseOrder"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.List"%>
<%@page import="entity.Product"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="userBeans.StatisticsLocal"%>
<%@page import="javax.naming.NamingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="resources/css/bootstrap.css" rel="stylesheet" media="screen">
        <link href="resources/stylesheet.css" rel="stylesheet" media="screen">
        <title>Historial de compras</title>
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
        <%
            List<PurchaseOrder> purchases = (List<PurchaseOrder>)request.getAttribute("purchases");
            if (purchases == null){
                out.println("<h1 class=\"bg-warning warning text-center\">No se ha encontrado " +
                            "un usuario con ese DNI.</h1>");
            }
            else{
                out.println("<table class=\"table table-hover\">");
                    out.println("<thead>");
                        out.println("<tr>");
                            out.println("<th>Fecha del pedido</th>");
                            out.println("<th>Acción</th>");
                        out.println("</tr>");
                    out.println("</thead>");
                    out.println("<tbody>");
                for (PurchaseOrder purchase: purchases) {
                    out.println("<tr>");
                        out.println("<td>" + purchase.getDate() + "</td>");
                        out.println("<td>");
                        out.println("<form action=\"FrontControllerServlet\">" +
                                        "<input type=\"hidden\" name=\"command\" value=\"SearchPurchaseProductsCommand\">" +
                                        "<input type=\"hidden\" name=\"orderNum\" value=" + purchase.getOrderNum() + ">" +
                                        "<input type=\"submit\" value=\"Ver productos\">" +
                                
                                    "</form>");
                        out.println("</td>");
                    out.println("</tr>");
                }
                    out.println("</tbody>");
                out.println("</table>");
            }
        %>
        <div w3-include-HTML="library/navbar.jsp"></div>
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
