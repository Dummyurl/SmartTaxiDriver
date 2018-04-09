package in.taxi.smartdriver.listeners;


import in.taxi.smartdriver.model.AuthBean;

public interface PhoneRegistrationListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);
}
