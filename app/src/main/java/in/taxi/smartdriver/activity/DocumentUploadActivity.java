package in.taxi.smartdriver.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import in.taxi.smartdriver.R;
import in.taxi.smartdriver.app.App;
import in.taxi.smartdriver.config.Config;
import in.taxi.smartdriver.listeners.BasicListener;
import in.taxi.smartdriver.model.BasicBean;
import in.taxi.smartdriver.net.DataManager;
import in.taxi.smartdriver.util.AppConstants;
import in.taxi.smartdriver.util.FileOp;

public class DocumentUploadActivity extends BaseAppCompatNoDrawerActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int IMAGE_PICKER_SELECT = 2;
    private static final String TAG = "DocUpA";
    private int type = AppConstants.DOCUMENT_TYPE_DRIVER_LICENCE;
    private String imagePath = "";
    private String documentPath;
    private TextView txtTitle;
    private ImageView ivDocumentPreview;
    private Button btnRetake;
    private Button btnSave;
    private ViewFlipper viewFlipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_upload);


        if (getIntent().hasExtra("type"))
            type = getIntent().getIntExtra("type", AppConstants.DOCUMENT_TYPE_DRIVER_LICENCE);

        initViews();

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            documentPath = imagePath;
            //    setBannerPic(tempImagePath);
            setDocumentImage(imagePath);

            Log.i(TAG, "onActivityResult: IMAGE PATH : " + imagePath);
        }
    }

    private void initViews() {

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper_document_upload);

        txtTitle = (TextView) findViewById(R.id.txt_document_upload_title);
        ivDocumentPreview = (ImageView) findViewById(R.id.iv_document_upload_preview);

        btnRetake = (Button) findViewById(R.id.btn_document_upload_retake);
        btnSave = (Button) findViewById(R.id.btn_document_upload_save);

        btnRetake.setTypeface(typeface);
        btnSave.setTypeface(typeface);

        txtTitle.setText(getDocumentTitle(type));

    }

    private String getDocumentTitle(int type) {

        switch (type) {

            case AppConstants.DOCUMENT_TYPE_DRIVER_LICENCE:
                return getString(R.string.label_driver_license);

            case AppConstants.DOCUMENT_TYPE_POLICE_CLEARANCE_CERTIFICATE:
                return getString(R.string.label_police_clearance_certificate);

            case AppConstants.DOCUMENT_TYPE_FITNESS_CERTIFICATE:
                return getString(R.string.label_fitness_certificate);

            case AppConstants.DOCUMENT_TYPE_VEHICLE_REGISTRATION:
                return getString(R.string.label_vehicle_registration);

            case AppConstants.DOCUMENT_TYPE_VEHICLE_PERMIT:
                return getString(R.string.label_vehicle_permit);

            case AppConstants.DOCUMENT_TYPE_COMMERCIAL_INSURANCE:
                return getString(R.string.label_commercial_insurance);

            case AppConstants.DOCUMENT_TYPE_TAX_RECEIPT:
                return getString(R.string.label_tax_receipt);

            case AppConstants.DOCUMENT_TYPE_PASS_BOOK:
                return getString(R.string.label_pass_book);

            case AppConstants.DOCUMENT_TYPE_DRIVER_LICENCE_WITH_BADGE_NUMBER:
                return getString(R.string.label_driver_licence_with_badge_number);

            case AppConstants.DOCUMENT_TYPE_BACKGROUND_CHECK_CONSENT_FORM:
                return getString(R.string.label_background_check_consent_form);

            case AppConstants.DOCUMENT_TYPE_PAN_CARD:
                return getString(R.string.label_pan_card);

            case AppConstants.DOCUMENT_TYPE_NO_OBJECTION_CERTIFICATE:
                return getString(R.string.label_no_objection_certificate);

            default:
                return getString(R.string.label_error);
        }

    }


    private void setDocumentImage(String imagePath) {

        viewFlipper.setDisplayedChild(1);
        Glide.with(getApplicationContext())
                .load(imagePath)
                .apply(new RequestOptions()
                        .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                        .centerCrop())
                .into(ivDocumentPreview);

//        ibClearDisplayPic.setVisibility(View.VISIBLE);

    }

    public void onDocumentUploadTakePhotoClick(View view) {

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
//                    imagePath = image.getAbsolutePath();
                    photoFile = App.createImageFile(App.getFileName(type)).getAbsoluteFile();
                    imagePath = photoFile.getAbsolutePath();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                if (photoFile != null) {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                getApplicationContext().getPackageName() + ".provider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    }

                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }


    public void onDocumentUploadRetakeClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        onDocumentUploadTakePhotoClick(view);
    }

    public void onDocumentUploadSaveClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        performDocumentUploadSave();
    }

    public void performDocumentUploadSave() {

        swipeView.setRefreshing(true);
        JSONObject postData = getDocumentUploadSaveJSObj();

        ArrayList<String> fileList = getFileList();

        DataManager.performDocumentUpload(postData, fileList, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);

//                App.saveToken(getApplicationContext(), driverDetailsBean);

                Intent intent = new Intent();
                intent.putExtra("type", type);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

                 /* To Be Removed....*/
                if (App.getInstance().isDemo()) {
                    Intent intent = new Intent();
                    intent.putExtra("type", type);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private ArrayList<String> getFileList() {
        ArrayList<String> fileList = new ArrayList<>();

        if (documentPath != null && !documentPath.equals("")) {
            String tempPath = FileOp.getDocumentPhotoPath(getDocumentTitle(type));
            FileOp.writeBitmapToFile(documentPath, tempPath);
            fileList.add(tempPath);
            Log.i(TAG, "getFileList: Temp : " + tempPath);
            Log.i(TAG, "getFileList: Original " + documentPath);
        }

        return fileList;
    }

    private JSONObject getDocumentUploadSaveJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("auth_token", Config.getInstance().getAuthToken());
            postData.put("type", type);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }
}
