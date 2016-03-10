package userBeans;

import javax.ejb.Local;

@Local
public interface CartLocal {
    public int calculateTotal();
}
