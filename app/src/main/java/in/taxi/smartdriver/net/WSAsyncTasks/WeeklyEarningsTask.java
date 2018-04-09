package in.taxi.smartdriver.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import in.taxi.smartdriver.model.WeeklyEarningsBean;
import in.taxi.smartdriver.net.invokers.WeeklyEarningsInvoker;

/**
 * Created by Jemsheer K D on 16 May, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class WeeklyEarningsTask extends AsyncTask<String, Integer, WeeklyEarningsBean> {

    private WeeklyEarningsTaskListener weeklyEarningsTaskListener;

    private HashMap<String, String> urlParams;

    public WeeklyEarningsTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected WeeklyEarningsBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        WeeklyEarningsInvoker weeklyEarningsInvoker = new WeeklyEarningsInvoker(urlParams, null);
        return weeklyEarningsInvoker.invokeWeeklyEarningsWS();
    }

    @Override
    protected void onPostExecute(WeeklyEarningsBean result) {
        super.onPostExecute(result);
        if (result != null)
            weeklyEarningsTaskListener.dataDownloadedSuccessfully(result);
        else
            weeklyEarningsTaskListener.dataDownloadFailed();
    }

    public static interface WeeklyEarningsTaskListener {
        void dataDownloadedSuccessfully(WeeklyEarningsBean weeklyEarningsBean);

        void dataDownloadFailed();
    }

    public WeeklyEarningsTaskListener getWeeklyEarningsTaskListener() {
        return weeklyEarningsTaskListener;
    }

    public void setWeeklyEarningsTaskListener(WeeklyEarningsTaskListener weeklyEarningsTaskListener) {
        this.weeklyEarningsTaskListener = weeklyEarningsTaskListener;
    }
}
