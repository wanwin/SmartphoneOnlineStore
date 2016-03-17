<%@page import="userBeans.CartLocal"%>
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
                <li><form action="FrontControllerServlet">
                        <input type="hidden" name="command" value="FindProductCommand">
                        <input id="transparentButton" type="submit" value="PRODUCTOS">
                    </form></li>
                <li><a href="contact.jsp">CONTACTAR</a></li>
                <li><form action="FrontControllerServlet">
                        <input type="hidden" name="command" value="ViewCartCommand">
                        <input id="cartButton" type="submit" value="">
                </form></li>
                <li>
                    <% 
                        CartLocal cart = (CartLocal) session.getAttribute("cart");
                        if (cart == null){
                            out.println("<p>0</p>");
                        }
                        else{
                            out.println("<p>" + cart.getProductList().size() + "</p>");
                        }
                    %>
                </li>
            </ul>
        </div>
    </div>
</nav>