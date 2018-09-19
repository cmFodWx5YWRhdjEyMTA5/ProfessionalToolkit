package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.R;
import health.app.Response.LoginResponse;
import health.app.Response.SignUpResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.HealthApp;
import health.app.Utilities.LocationActivity;
import health.app.Utilities.LocationUpdater;
import health.app.Utilities.Preferences;
import health.app.Utilities.ValidationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CommonLoginActivity extends LocationActivity implements LocationUpdater {

    String deviceToken;
    TextView title;
    String userType;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_login);
        ButterKnife.bind(this);
        Typeface typeface_temp = etPassword.getTypeface();
        final Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins-SemiBold.ttf");
        final Typeface font1 = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins-Regular.ttf");
        btLogin.setTypeface(font);
        tv1.setTypeface(font);
        tv2.setTypeface(font);
        tvforgetpsd.setTypeface(font1);
        customer.setTypeface(font1);
        trainer.setTypeface(font1);
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etPassword.setTypeface(typeface_temp);
        passwordLayout.setTypeface(typeface_temp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        if (deviceToken==null) {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
        }
        Preferences.getInstance().setDevice_token(deviceToken);
        title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Client Login");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       // Drawable drawable = (getResources().getDrawable(R.mipmap.backicon2x));
       // toolbar.setNavigationIcon(drawable);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        checkCheckBox();
        trainer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
         if (trainer.isChecked())
         {
             title.setText("Trainer Login");
             customer.setChecked(false);
         }
         else {
             title.setText("Client Login");
             customer.setChecked(true);
         }
     }
 });
        customer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (customer.isChecked())
                {
                    title.setText("Client Login");
                    trainer.setChecked(false);
                }
                else {
                    title.setText("Trainer Login");
                    trainer.setChecked(true);
                }
            }
        });
    }

//    private void checkCheckBox() {
//        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer"))
//        {
//            trainer.setChecked(true);
//            customer.setChecked(false);
//            title.setText("Trainer Login");
//        }
//        else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer"))
//        {
//            customer.setChecked(true);
//            trainer.setChecked(false);
//            title.setText("Customer Login");
//        }
//    }
    private void checkCheckBox() {
     customer.setChecked(true);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void customerLogin(){
        if (trainer.isChecked())
        {
             userType="Trainer";
        }
        else if (customer.isChecked()){
             userType="Customer";
        }
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Sending request...");
        healthAppService.customerLogin(String.valueOf(etEmail.getText()), String.valueOf(etPassword.getText()),Preferences.getInstance().getDevice_token(), Preferences.getInstance().getLongitude(),Preferences.getInstance().getLatitude(),"android_token",userType).
                enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        alertDialog.dismiss();
                        if (response.body().isStatus()) {
                            if (response.body().getData() != null) {
                                setPreferences(response.body().getData());
                                if (response.body().getData().getUser().getUser_type().equals("Trainer")){
                                    startActivity(new Intent(getApplicationContext(), TrainerDashboardActivity.class));
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                }
                                else if (response.body().getData().getUser().getUser_type().equals("Customer")) {
                                    startActivity(new Intent(getApplicationContext(), CustomerDashboardActivity.class));
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                }
                                //Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                alertUser(response.body().getMsg());
                            }
                        }
                        else {
                            alertUser(response.body().getMsg());
                        }

                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void alertUser(String msg) {
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(CommonLoginActivity.this);
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
        Preferences.getInstance().setUniqueId(data.getUser().getUniqueId());
    }

    public boolean validation() {
        if (etEmail.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        } else if (!ValidationUtils.isValidEmail(etEmail.getText())) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Valid Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (etPassword.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Preferences.getInstance().setDevice_token(deviceToken);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bt_login)
    Button btLogin;

    @BindView(R.id.input_email)
    EditText etEmail;

    @BindView(R.id.cb_trainer)
    CheckBox trainer;

    @BindView(R.id.tv_not_menber)
    TextView tv1;

    @BindView(R.id.tv_register)
    TextView tv2;

    @BindView(R.id.cb_customer)
    CheckBox customer;

    @BindView(R.id.input_password)
    EditText etPassword;

    @BindView(R.id.input_layout_password)
    TextInputLayout passwordLayout;

    @BindView(R.id.tv_forget_psd)
    TextView tvforgetpsd;

    @BindView(R.id.container)
    LinearLayout container;

    @OnClick(R.id.tv_forget_psd)
    void forgetPwd(){
        startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
    }

    @OnClick(R.id.tv_register)
    void registerUser(){
        if (trainer.isChecked())
        {
            startActivity(new Intent(getApplicationContext(), TrainerSignUpActivity.class));
        }
        else if (customer.isChecked()){
            startActivity(new Intent(getApplicationContext(), RegisterOneActivity.class));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_login)
    void loginUser(){
        if (validation()) {
            customerLogin();
        }
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
}