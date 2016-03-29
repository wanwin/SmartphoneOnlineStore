package frontController;

import controller.ProductFacadeLocal;
import entity.Product;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import userBeans.CartLocal;

public class AddToCartCommand extends FrontCommand{

    @Override
    public void process(){
        try {
            CartLocal cart = initCart();
            String productId = request.getParameter("productId");
            ProductFacadeLocal productFacade = (ProductFacadeLocal) InitialContext.doLookup(PRODUCT_JNDI_URL);
            Product product = productFacade.find(Integer.parseInt(productId));
            if (isAvailable(product)){
                cart.addToCart(product);
            }
            FindProductCommand findProductCommand = new FindProductCommand();
            findProductCommand.context = this.context;
            findProductCommand.request = this.request;
            findProductCommand.response = this.response;
            findProductCommand.process();
        } catch (NamingException ex) {
        }
    }

    private static boolean isAvailable(Product product) {
        return product.getQuantityOnHand() > 0;
    }
}
