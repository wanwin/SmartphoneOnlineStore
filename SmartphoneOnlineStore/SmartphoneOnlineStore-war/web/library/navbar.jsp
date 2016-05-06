<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="entity.Product"%>
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
                        <input type="hidden" name="pagination" value="1">
                        <input class="transparentButton" type="submit" value="PRODUCTOS">
                    </form></li>
                <li><a href="previousPurchasesForm.jsp">VER COMPRAS ANTERIORES</a></li>
                <li><a href="contact.jsp">CONTACTAR</a></li>
                <li><form action="FrontControllerServlet">
                        <input type="hidden" name="command" value="ViewCartCommand">
                        <%
                            CartLocal cart = (CartLocal) session.getAttribute("cart");
                            if (cart == null){
                                out.println("<input id=\"cartButton0\" type=\"submit\" value=\"\">");
                            }else{
                                ConcurrentHashMap<Product,Integer> products = cart.getProducts();
                                Integer numberOfProductsInCart = 0; 
                                for (Entry<Product,Integer> entry: products.entrySet()){
                                    Integer quantity = entry.getValue();
                                    numberOfProductsInCart += quantity;
                                }
                                if (numberOfProductsInCart <= 10){
                                    out.println("<input id=\"cartButton" + numberOfProductsInCart + "\" type=\"submit\" value=\"\">");
                                }else out.println("<input id=\"cartButton11\" type=\"submit\" value=\"\">");
                            }
                        %>
                </form></li>
            </ul>
        </div>
    </div>
</nav>