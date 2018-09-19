package health.app.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.R;
import health.app.Response.SignUpResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.HealthApp;
import health.app.Utilities.LocationActivity;
import health.app.Utilities.LocationUpdater;
import health.app.Utilities.Preferences;
import health.app.Utilities.ValidationUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterTwoActivity extends LocationActivity implements LocationUpdater {

    public static EditText etBirthday;
    MultipartBody.Part profileImgBody;
    File file;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
        final Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins-SemiBold.ttf");
        btSubmit.setTypeface(font);
        Typeface typeface_temp = inputPassword.getTypeface();
        inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPassword.setTypeface(typeface_temp);
        passwordLayout.setTypeface(typeface_temp);
        Typeface typeface_temp1 = inputConPassword.getTypeface();
        inputConPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputConPassword.setTypeface(typeface_temp1);
        conpasswordLayout.setTypeface(typeface_temp1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Client Sign Up");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.mipmap.backicon2x));
        toolbar.setNavigationIcon(drawable);
        etBirthday=(EditText)findViewById(R.id.input_birthday);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (RegisterOneActivity.signUp.getcGender().equalsIgnoreCase("Male")){
            inputlayoutHip.setVisibility(View.GONE);
            inputHip.setVisibility(View.GONE);
        }
        else if (RegisterOneActivity.signUp.getcGender().equalsIgnoreCase("Female")){
            inputlayoutHip.setVisibility(View.VISIBLE);
            inputHip.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onCurrentLocationAvailaible(LatLng latLng) {
        double latitude=latLng.latitude;
        double longitude=latLng.longitude;
        Preferences.getInstance().setLongitude(String.valueOf(longitude));
        Preferences.getInstance().setLatitude(String.valueOf(latitude));
        Log.d("LatLng","LatLng"+latitude+"v"+longitude);
        HealthApp.getInstance().setCurrentLatng(latLng);
    }

    public RequestBody requestBody(String name) {
        return RequestBody.create(MediaType.parse("text/plain"), name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void locationUpdate(LatLng latLng) {
        double latitude=latLng.latitude;
        double longitude=latLng.longitude;
        Preferences.getInstance().setLongitude(String.valueOf(longitude));
        Preferences.getInstance().setLatitude(String.valueOf(latitude));
        HealthApp.getInstance().setCurrentLatng(latLng);
        if ((HealthApp.getInstance()).getAppLatLng() == null)
            (HealthApp.getInstance()).setAppLatLng(latLng);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return  dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            etBirthday.setText(new StringBuilder().append(day).append("/").append(month+1).append("/").append(year));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void signUp() {
        if (RegisterOneActivity.signUp.getcImagePath() != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), RegisterOneActivity.signUp.getcImagePath());
            profileImgBody = MultipartBody.Part.createFormData("profile_image", RegisterOneActivity.signUp.getcImagePath().getName(), requestBody);}
            final Map<String, RequestBody> field = new HashMap<String, RequestBody>();
            field.put("first_name", requestBody(RegisterOneActivity.signUp.getcFirstName()));
            field.put("last_name", requestBody(RegisterOneActivity.signUp.getcLastName()));
            field.put("gender", requestBody(RegisterOneActivity.signUp.getcGender()));
            field.put("phone", requestBody(RegisterOneActivity.signUp.getcPhoneNo()));
            field.put("age", requestBody(RegisterOneActivity.signUp.getcAge()));
            field.put("weight", requestBody(RegisterOneActivity.signUp.getcWeight()));
            field.put("height", requestBody(RegisterOneActivity.signUp.getcHeight()));
            field.put("Waist", requestBody(inputWaist.getText().toString()));
            field.put("Calf", requestBody(inputCalf.getText().toString()));
            field.put("Thigh", requestBody(inputThigh.getText().toString()));
            field.put("Chest", requestBody(inputChest.getText().toString()));
            field.put("Arm", requestBody(inputArm.getText().toString()));
            if (RegisterOneActivity.signUp.getcGender().equalsIgnoreCase("Female")){
            field.put("Hips", requestBody(inputHip.getText().toString()));}
            field.put("fat", requestBody(inputFat.getText().toString()));
            field.put("email", requestBody(inputEmail.getText().toString()));
            field.put("password", requestBody(inputPassword.getText().toString()));
            if(Preferences.getInstance().getDevice_token()!=null) {
                field.put("token", requestBody(Preferences.getInstance().getDevice_token()));
            }
            field.put("token_type", requestBody("android_token"));
            if(Preferences.getInstance().getLongitude()!=null) {
               field.put("longitude", requestBody(Preferences.getInstance().getLongitude()));
            }
            if(Preferences.getInstance().getLatitude()!=null) {
            field.put("latitude", requestBody(Preferences.getInstance().getLatitude()));
            }
            field.put("user_type", requestBody("Customer"));
            final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
            showProgressbar("Loading", "Sending request...");
            healthAppService.signUpCustomer(profileImgBody, field).
                    enqueue(new Callback<SignUpResponse>() {
                        @Override
                        public void onResponse(Call<SignUpResponse> call, final Response<SignUpResponse> response) {
                            alertDialog.dismiss();
                            if (response.body() != null) {
                                if (response.body().isStatus()){
                                    setPreferences(response.body().getData());
                                    startActivity(new Intent(getApplicationContext(), CustomerDashboardActivity.class));
                                    finish();
                                }
                                else {
                                    alertUser(response.body().getMsg());
                                }
                                hideProgressBar();
                            }
                            else {
                              alertUser(response.body().getMsg());
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
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(RegisterTwoActivity.this);
            alertDialogBuilder.setTitle(getString(R.string.app_name));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ptlogo);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void setPreferences(SignUpResponse.SignUpData data) {
        Preferences.getInstance().setLogIn(true);
        Preferences.getInstance().setUserId(data.getUser().getId());
        Preferences.getInstance().setLongitude(data.getUser().getLongitude());
        Preferences.getInstance().setLatitude(data.getUser().getLatitude());
        Preferences.getInstance().setFirstName(data.getUser().getFirst_name());
        Preferences.getInstance().setLastName(data.getUser().getLast_name());
        Preferences.getInstance().setMobile(data.getUser().getPhone());
        Preferences.getInstance().setEmail(data.getUser().getEmail());
        Preferences.getInstance().setGender(data.getUser().getGender());
        Preferences.getInstance().setAge(data.getUser().getAge());
        Preferences.getInstance().setWeight(data.getUser().getWeight());
        Preferences.getInstance().setHeight(data.getUser().getHeight());
        Preferences.getInstance().setBodyFat(data.getUser().getFat());
        Preferences.getInstance().setUserPhotoPath(data.getUser().getProfile_image_path());
        Preferences.getInstance().setUserType(data.getUser().getUser_type());
        Preferences.getInstance().setTrainingType(data.getUser().getTraining_type());
        Preferences.getInstance().setBioData(data.getUser().getShort_bio());
    }

    public boolean validation(){
        if (inputEmail.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (!ValidationUtils.isValidEmail(inputEmail.getText())) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Valid Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputPassword.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputPassword.getText().toString().trim().length()<6) {
            Snackbar snackbar = Snackbar.make(container, "Your password must be 6 characters long.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputConPassword.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Confirm Password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
//        else if (inputConPassword.getText().toString().trim().length()<6) {
//            Snackbar snackbar = Snackbar.make(container, "Your password must be 6 characters long.", Snackbar.LENGTH_LONG);
//            snackbar.getView().setBackgroundColor(Color.RED);
//            snackbar.show();
//            return false;
//        }
        else if(!inputPassword.getText().toString().trim().matches(inputConPassword.getText().toString().trim())){
            Snackbar snackbar = Snackbar.make(container, "Your confirm password not match with new password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.input_birthday)
    void dob(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_submit)
    void submit(){
        if (validation()) {
            signUp();
        }
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bt_submit)
    Button btSubmit;

    @BindView(R.id.input_email)
    EditText inputEmail;

    @BindView(R.id.input_birthday)
    EditText inputBirthday;

    @BindView(R.id.input_daily_activity)
    EditText inputDailyActivity;

    @BindView(R.id.input_short_bio)
    EditText inputShortBio;

    @BindView(R.id.input_layout_password)
    TextInputLayout passwordLayout;

    @BindView(R.id.input_layout_con_password)
    TextInputLayout conpasswordLayout;

    @BindView(R.id.input_password)
    EditText inputPassword;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.input_con_password)
    EditText inputConPassword;

    @BindView(R.id.input_fat)
    EditText inputFat;


    @BindView(R.id.input_calf)
    EditText inputCalf;


    @BindView(R.id.input_thigh)
    EditText inputThigh;


    @BindView(R.id.input_chest)
    EditText inputChest;


    @BindView(R.id.input_arm)
    EditText inputArm;

    @BindView(R.id.input_waist)
    EditText inputWaist;

    @BindView(R.id.input_layout_hip)
    TextInputLayout inputlayoutHip;

    @BindView(R.id.input_hip)
    EditText inputHip;
}


