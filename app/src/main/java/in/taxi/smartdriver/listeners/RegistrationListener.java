package in.taxi.smartdriver.listeners;

import in.taxi.smartdriver.model.AuthBean;

/**
 * Created by Jemsheer K D on 24 April, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface RegistrationListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);
}
