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
        <table class="table table-hover">
            <thead>
                <tr>
                    <th><p>Datos de Facturación</p></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="col-sm-6 form-group">
                        <form action="FrontControllerServlet" role="form">
                            <input type="hidden" name="command" value="PurchaseCommand">
                            <div class="form-group">
                                <label for="name">Nombre:
                                <input type="text" class="form-control" id="name"></label>
                            </div>
                            <div class="form-group">
                                <label for="surname">Apellidos:
                                <input type="text" class="form-control" id="surname"></label>
                            </div>
                            <div class="form-group">
                                <label for="dni">DNI:
                                <input type="text" class="form-control" id="dni"></label>
                            </div>
                            <div class="bg-info info"><p>Se aplicará el IGIC (7%) al total de la factura.</p></div>
                            <input type="submit" class="btn btn-purchase" value="Seguir">
                        </form><br>
                        <form action="FrontControllerServlet">
                            <input type="hidden" name="command" value="ViewCartCommand">
                            <input type="submit" class="btn btn-danger" value="Cancelar">
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
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
