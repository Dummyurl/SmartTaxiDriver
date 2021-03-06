package in.taxi.smartdriver.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.ArrayList;

import in.taxi.smartdriver.model.BasicBean;
import in.taxi.smartdriver.net.invokers.ProfilePhotoUploadInvoker;

public class ProfilePhotoUploadTask extends AsyncTask<String, Integer, BasicBean> {

    private final ArrayList<String> fileList;
    private JSONObject postData;

    private ProfilePhotoUploadTaskListener profilePhotoUploadTaskListener;

    public ProfilePhotoUploadTask(JSONObject postData, ArrayList<String> fileList) {
        super();

        this.postData = postData;
        this.fileList = fileList;
    }

    @Override
    protected BasicBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        ProfilePhotoUploadInvoker profilePhotoSaveInvoker = new ProfilePhotoUploadInvoker(null, postData);
        return profilePhotoSaveInvoker.invokeProfilePhotoUploadWS(fileList);
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);

        if (result != null)
            profilePhotoUploadTaskListener.dataDownloadedSuccessfully(result);
        else
            profilePhotoUploadTaskListener.dataDownloadFailed();
    }

    public interface ProfilePhotoUploadTaskListener {

        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public ProfilePhotoUploadTaskListener getProfilePhotoUploadTaskListener() {
        return profilePhotoUploadTaskListener;
    }

    public void setProfilePhotoUploadTaskListener(ProfilePhotoUploadTaskListener profilePhotoUploadTaskListener) {
        this.profilePhotoUploadTaskListener = profilePhotoUploadTaskListener;
    }
}
