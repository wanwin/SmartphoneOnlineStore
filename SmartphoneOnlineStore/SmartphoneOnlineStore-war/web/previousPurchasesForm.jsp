<%@page import="javax.naming.InitialContext"%>
<%@page import="userBeans.StatisticsLocal"%>
<%@page import="javax.naming.NamingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Movilazos.es</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="resources/css/bootstrap.css" rel="stylesheet" media="screen">
        <link href="resources/stylesheet.css" rel="stylesheet" media="screen">
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
        <div class="container">
            <form action="FrontControllerServlet" role="form">
                <input type="hidden" name="command" value="ShowPreviousPurchasesCommand">
                <div class="text-center">
                    <label for="dni">Introduzca su DNI para ver compras anteriores:
                    <input type="text" class="form-control" name="dni" id="dni" required/></label>
                </div>
                <div class="text-center">
                <input type="submit" class="btn btn-purchase" value="Ver compras anteriores">
                </div>
            </form><br>
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
