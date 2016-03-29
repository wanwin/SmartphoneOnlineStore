package userBeans;

import javax.ejb.Singleton;

@Singleton
public class Statistics implements StatisticsLocal {
    
    int numberOfUsers = 0;

    @Override
    public void incrementNumberOfUsersConnected() {
        numberOfUsers++;        
    }
    
    @Override
    public int getNumberOfUsersConnected() {
        return numberOfUsers;
    }
}
