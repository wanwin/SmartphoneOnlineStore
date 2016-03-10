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
                    <button type="button" class="navbar-toggle"     data-toggle="collapse"  data-target="#myNavbar">
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
                        <li><form action="FrontControllerServlet"><input type="hidden" name="command" value="FindProductCommand"><input type="hidden" name="command" value="FindProductCommand"><input id="transparentButton" type="submit" value="PRODUCTOS"></form></li>
                        <li><a href="">CONTACTO</a></li>
                        <li><a href="">INICIAR SESIÓN</a></li>
                        <li><a href=""><img src="resources/img/carrito.png" 
                                            class="glyphicon-shopping-cart"></a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div id="myCarousel" class="carousel slide" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">
                <div class="item active">
                    <img src="resources/img/image1.jpg" alt="Image">
                    <div class="carousel-caption">
                        <h3>"La vida es aquello que ocurre mientras estamos ocupados haciendo planes"</h3>
                        <p style="font-style: italic">(John Lennon)</p>
                    </div>      
                </div>

                <div class="item">
                    <img src="resources/img/image2.jpg" alt="Image">
                    <div class="carousel-caption">
                        <h3>"La vida es aquello que ocurre mientras estamos ocupados haciendo planes"</h3>
                        <p style="font-style: italic">(John Lennon)</p>
                    </div>      
                </div>
            </div>

            <!-- Left and right controls -->
            <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
        <div class="breadcrumb">
            <p style="appearance: desktop;color: #000">Estamos en mantenimiento, disculpe las molestias...</p>
        </div>
        <footer class="container-fluid text-center">
            <p style="font-style: italic;color: #000;font-size: small">Copyright 2016 - Darwin Hamad y Evelin Rodrguez</p>
        </footer>
        <script src="http://code.jquery.com/jquery.js"></script> 
        <script src="resources/js/bootstrap.js"></script>
    </body>
</html>
