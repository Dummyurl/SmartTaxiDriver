package in.taxi.smartdriver.listeners;


import in.taxi.smartdriver.model.BasicBean;

public interface BasicListener {

    void onLoadCompleted(BasicBean basicBean);

    void onLoadFailed(String error);
}
