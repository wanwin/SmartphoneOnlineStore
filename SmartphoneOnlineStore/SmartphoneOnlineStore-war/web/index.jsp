<%@page import="userBeans.TimerLocal"%>
<%@page import="userBeans.StatisticsLocal"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="javax.naming.InitialContext"%>
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
        <div w3-include-HTML="library/carousel.html"></div>
        <div w3-include-HTML="library/breadcrumb.html"></div>
        <div w3-include-HTML="library/footer.html"></div>
        <%
            TimerLocal timer = (TimerLocal) InitialContext.doLookup("java:global/SmartphoneOnlineStore/SmartphoneOnlineStore-ejb/Timer!userBeans.TimerLocal");
            timer.setDiscount();
            timer.unsetDiscount();
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
