package in.taxi.smartdriver.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import in.taxi.smartdriver.model.BasicBean;
import in.taxi.smartdriver.net.invokers.ForgotPasswordInvoker;


/**
 * Created by Jemsheer K D on 03 May, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class ForgotPasswordTask extends AsyncTask<String, Integer, BasicBean> {

    private ForgotPasswordTaskListener forgotPasswordTaskListener;

    private JSONObject postData;

    public ForgotPasswordTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        ForgotPasswordInvoker forgotPasswordInvoker = new ForgotPasswordInvoker(null, postData);
        return forgotPasswordInvoker.invokeForgotPasswordWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            forgotPasswordTaskListener.dataDownloadedSuccessfully(result);
        else
            forgotPasswordTaskListener.dataDownloadFailed();
    }

    public static interface ForgotPasswordTaskListener {
        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public ForgotPasswordTaskListener getForgotPasswordTaskListener() {
        return forgotPasswordTaskListener;
    }

    public void setForgotPasswordTaskListener(ForgotPasswordTaskListener forgotPasswordTaskListener) {
        this.forgotPasswordTaskListener = forgotPasswordTaskListener;
    }
}
