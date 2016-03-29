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
                            <input type="submit" class="btn btn-default" value="Siguiente">
                         </form>
                    </td>
                </tr>
            </tbody>
        </table>
        <div w3-include-HTML="library/footer.html"></div>
        <script src="http://code.jquery.com/jquery.js"></script> 
        <script src="resources/js/bootstrap.js"></script>
        <script src="resources/js/w3-include-HTML.js"></script>
    </body>
</html>
