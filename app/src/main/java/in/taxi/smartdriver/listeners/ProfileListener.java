package in.taxi.smartdriver.listeners;


import in.taxi.smartdriver.model.ProfileBean;


public interface ProfileListener {

    void onLoadCompleted(ProfileBean profileBean);

    void onLoadFailed(String error);
}
