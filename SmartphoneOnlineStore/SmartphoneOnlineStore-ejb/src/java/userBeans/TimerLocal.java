package userBeans;

import javax.ejb.Local;

@Local
public interface TimerLocal {
    public void setDiscount();    
    public void unsetDiscount();    
}