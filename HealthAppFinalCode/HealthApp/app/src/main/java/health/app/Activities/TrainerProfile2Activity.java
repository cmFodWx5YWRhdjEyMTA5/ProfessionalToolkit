package health.app.Activities;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.R;
import health.app.Response.SignUpResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TrainerProfile2Activity extends BaseActivity {
    String firstName,lastName,email,phoneNo,trainingType;
    String biop,agep,training;
    CircleProgressBar mCustomProgressBar5;
    MultipartBody.Part profileImgBody2;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile2);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        agep=getIntent().getStringExtra("age");
        biop=getIntent().getStringExtra("bio");
        training=getIntent().getStringExtra("trainingType");
        Log.d("trainingType","trainingType"+training.length());
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        mCustomProgressBar5 = (CircleProgressBar) toolbar.findViewById(R.id.custom_progress5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.mipmap.backicon2x));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer"))
        {
            title.setText("View Profile");
            age.setClickable(false);
            age.setFocusable(false);
            bioData.setClickable(false);
            bioData.setFocusable(false);
            submitBtn.setVisibility(View.GONE);
            addEntry();
        }
        else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer"))
        {
            title.setText("My Profile");
            addEntry();
            setValues();
        }
//        addEntry();
        simulateProgress();
        bioData.setMaxLines(5);
        bioData.setVerticalScrollBarEnabled(true);
    }

    private void simulateProgress() {
        if (age.getText().length()==0 && bioData.getText().length()==0 && training.length()==0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 70);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (age.getText().length()!=0 && bioData.getText().length()!=0 && training.length()!=0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 100);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (age.getText().length()!=0 && bioData.getText().length()==0 && training.length()==0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 80);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (age.getText().length()==0 && bioData.getText().length()==0 && training.length()!=0)
        {
            ValueAnimator animator = ValueAnimator.ofInt(0, 80);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (age.getText().length()==0 && bioData.getText().length()!=0 && training.length()==0)
        {
            ValueAnimator animator = ValueAnimator.ofInt(0, 80);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (age.getText().length()!=0 && bioData.getText().length()!=0)
        {
            ValueAnimator animator = ValueAnimator.ofInt(0, 90);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (age.getText().length()!=0||bioData.getText().length()!=0){
            ValueAnimator animator = ValueAnimator.ofInt(0, 90);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
    }

    public void addEntry(){
        age.setText(agep);
        bioData.setText(biop);
    }


    public void setValues(){
        firstName= TrainerProfile1Activity.profile.getTfirstName();
        lastName=TrainerProfile1Activity.profile.getTlastName();
        email=TrainerProfile1Activity.profile.getTemail();
        phoneNo=TrainerProfile1Activity.profile.getTphoneNum();
        trainingType=TrainerProfile1Activity.profile.getTtrainingType();
    }

    public boolean validation(){
        if (age.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Age.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (bioData.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Bio.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }

    public RequestBody requestBody(String name) {
        return RequestBody.create(MediaType.parse("text/plain"), name);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void editTrainerProfile(){
        if (TrainerProfile1Activity.profile.getTtrainerImage()!=null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), TrainerProfile1Activity.profile.getTtrainerImage());
            profileImgBody2 = MultipartBody.Part.createFormData("profile_image", TrainerProfile1Activity.profile.getTtrainerImage().getName(), requestBody);
        }
        final Map<String, RequestBody> field = new HashMap<String, RequestBody>();
        field.put("first_name", requestBody(firstName));
        field.put("last_name", requestBody(lastName));
        field.put("email", requestBody(email));
        field.put("phone", requestBody(phoneNo));
        field.put("training_type", requestBody(trainingType));
        field.put("age", requestBody(age.getText().toString()));
        field.put("short_bio", requestBody(bioData.getText().toString()));
        field.put("UserId", requestBody(Preferences.getInstance().getUserId()));
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.editTrainerProfile(profileImgBody2,field).
                enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(Call<SignUpResponse> call, final Response<SignUpResponse> response) {
                        if (response.body() != null) {
                            if (response.body().isStatus()){
                                alertUser(response.body().getMsg());
                            }
                            else {
                                alertUser(response.body().getMsg());
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        hideProgressBar();

                    }

                    @Override
                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void alertUser(String msg) {
        if (msg!=null){
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(TrainerProfile2Activity.this);
            alertDialogBuilder.setTitle(getString(R.string.app_name));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ptlogo);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    TrainerProfile1Activity.fa.finish();
                    Intent intent=new Intent(getApplicationContext(),TrainerProfile1Activity.class);
                    startActivity(intent);
                    finish();
                }
            });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_submit_now)
    void submit(){
       //if (validation()){
            editTrainerProfile();
       // }
    }

    @BindView(R.id.bt_submit_now)
    Button submitBtn;
    
    @BindView(R.id.input_age)
    EditText age;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.input_bio)
    EditText bioData;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
}
