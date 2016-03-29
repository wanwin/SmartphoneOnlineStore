package userBeans;

import javax.ejb.Local;

@Local
public interface StatisticsLocal {

    public void incrementNumberOfUsersConnected();
    public int getNumberOfUsersConnected();
}
