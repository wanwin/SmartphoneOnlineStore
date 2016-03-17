<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="resources/css/bootstrap.css" rel="stylesheet" media="screen">
        <link href="resources/stylesheet.css" rel="stylesheet" media="screen">
        <title>Contactar</title>
    </head>
    <body>
        <div w3-include-HTML="library/navbar.jsp"></div>
        <div class="container-fluid bg-grey">
            <h2 class="text-center">CONTACTAR</h2>
            <div class="row">
                <div class="col-sm-5">
                    <p>Contacta ahora con nosotros y te atenderemos sin compromiso.</p>
                    <p><span class="glyphicon glyphicon-map-marker"></span> Campus Universitario de Tafira - ULPGC</p>
                    <p><span class="glyphicon glyphicon-phone"></span> 928 928 928</p>
                    <p><span class="glyphicon glyphicon-envelope"></span> info@movilazos.es</p>
                </div>
                <div class="col-sm-7">
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <input class="form-control" id="name" name="name" placeholder="Nombre" type="text" required>
                        </div>
                        <div class="col-sm-6 form-group">
                            <input class="form-control" id="email" name="email" placeholder="Email" type="email" required>
                        </div>
                    </div>
                    <textarea class="form-control" id="comments" name="comments" placeholder="Comentario" rows="5"></textarea><br>
                    <div class="row">
                        <div class="col-sm-12 form-group">
                            <button class="btn btn-default pull-right" type="submit">Enviar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Set height and width with CSS -->
        <div id="googleMap" style="height:400px;width:100%;"></div>

        <!-- Add Google Maps -->
        <script src="http://maps.googleapis.com/maps/api/js"></script>
        <script>
        var myCenter = new google.maps.LatLng(28.07330359659391, -15.451401634484913);

        function initialize() {
        var mapProp = {
        center:myCenter,
        zoom:18,
        scrollwheel:true,
        draggable:true,
        mapTypeId:google.maps.MapTypeId.ROADMAP
        };

        var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);

        var marker = new google.maps.Marker({
        position:myCenter
        });

        marker.setMap(map);
        }

        google.maps.event.addDomListener(window, 'load', initialize);
        </script>
        <div w3-include-HTML="library/footer.html"></div>
        <script src="http://code.jquery.com/jquery.js"></script> 
        <script src="resources/js/bootstrap.js"></script>
        <script src="resources/js/w3-include-HTML.js"></script>
    </body>
</html>
