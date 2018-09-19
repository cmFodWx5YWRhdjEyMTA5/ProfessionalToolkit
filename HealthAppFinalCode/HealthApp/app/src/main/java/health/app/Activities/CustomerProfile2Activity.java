package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
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

public class CustomerProfile2Activity extends BaseActivity {
    MultipartBody.Part profileImgBody2;
    String ageC,weight,height,fat,calf,waist,thigh,chest,arm,hip;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile2);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        ageC=getIntent().getStringExtra("age");
        weight=getIntent().getStringExtra("weight");
        height=getIntent().getStringExtra("height");
        fat=getIntent().getStringExtra("fat");
        calf=getIntent().getStringExtra("calf");
        waist=getIntent().getStringExtra("waist");
        thigh=getIntent().getStringExtra("thigh");
        chest=getIntent().getStringExtra("chest");
        arm=getIntent().getStringExtra("arm");
        hip=getIntent().getStringExtra("hip");
        Log.d("Measurement","Measurement"+calf+waist+thigh+chest+arm+hip);
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("My Profile");
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
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer"))
        {
            title.setText("View Profile");
            age.setClickable(false);
            age.setFocusable(false);
            inputWeight.setClickable(false);
            inputWeight.setFocusable(false);
            inputHeight.setClickable(false);
            inputHeight.setFocusable(false);
            inputFat.setClickable(false);
            inputFat.setFocusable(false);
            inputCalf.setClickable(false);
            inputCalf.setFocusable(false);
            inputThigh.setClickable(false);
            inputThigh.setFocusable(false);
            inputChest.setClickable(false);
            inputChest.setFocusable(false);
            inputArm.setClickable(false);
            inputArm.setFocusable(false);
            inputWaist.setClickable(false);
            inputWaist.setFocusable(false);
            inputHip.setClickable(false);
            inputHip.setFocusable(false);
            submitBtn.setVisibility(View.GONE);
            setValues2();
        }
        else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer"))
        {
            title.setText("My Profile");
            if (CustomerProfile1Activity.cprofile.getCgender().equalsIgnoreCase("Male")){
                inputHip.setVisibility(View.GONE);
                inputlayoutHip.setVisibility(View.GONE);
            }
            else if (CustomerProfile1Activity.cprofile.getCgender().equalsIgnoreCase("Female"))
            {
                inputHip.setVisibility(View.VISIBLE);
                inputlayoutHip.setVisibility(View.GONE);
            }
            setValues2();
        }

    }

    public void setValues2() {
        if (ageC.equalsIgnoreCase("")) {
            age.setText("N/A");
        }
        else {
            age.setText(ageC);

        }
        if (weight.equalsIgnoreCase("")) {
            inputWeight.setText("N/A");
        }
        else {
            inputWeight.setText(weight);
        }
        if (height.equalsIgnoreCase("")) {
            inputHeight.setText("N/A");
        }
        else {
            inputHeight.setText(height);
        }
        if (fat.equalsIgnoreCase("")) {
            inputFat.setText("N/A");
        }
        else {
            inputFat.setText(fat);
        }
        if (calf==null||calf.equalsIgnoreCase("")||calf.equalsIgnoreCase("0.00")) {
            inputCalf.setText("N/A");
        }
        else {
            inputCalf.setText(calf);
        }
        if (thigh==null||thigh.equalsIgnoreCase("")||thigh.equalsIgnoreCase("0.00")) {
            inputThigh.setText("N/A");
        }
        else {
            inputThigh.setText(thigh);
        }
        if (chest==null||chest.equalsIgnoreCase("")||chest.equalsIgnoreCase("0.00")) {
            inputChest.setText("N/A");
        }
        else {
            inputChest.setText(chest);
        }
        if (arm==null||arm.equalsIgnoreCase("")||arm.equalsIgnoreCase("0.00")) {
            inputArm.setText("N/A");
        }
        else {
            inputArm.setText(arm);
        }

        if (hip==null||hip.equalsIgnoreCase("")||hip.equalsIgnoreCase("0.00"))
        {
            inputHip.setText("N/A");
        }
        else {
            inputHip.setText(hip);
        }
        if (waist==null||waist.equalsIgnoreCase("")||waist.equalsIgnoreCase("0.00"))
        {
            inputWaist.setText("N/A");
        }
        else {
            inputWaist.setText(waist);
        }

        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")) {
            inputWeight.setEnabled(false);
            inputHeight.setEnabled(false);
            inputFat.setEnabled(false);
            if (CustomerProfile1Activity.cprofile.getCgender().equalsIgnoreCase("Female")) {
                inputHip.setEnabled(false);
            }
            inputCalf.setEnabled(false);
            inputThigh.setEnabled(false);
            inputChest.setEnabled(false);
            inputArm.setEnabled(false);
            inputWaist.setEnabled(false);
        }
        else {
            inputWeight.setEnabled(true);
            inputHeight.setEnabled(true);
            inputFat.setEnabled(true);
        }
    }

    public RequestBody requestBody(String name) {
        return RequestBody.create(MediaType.parse("text/plain"), name);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void editCustomerProfile(){
        if (CustomerProfile1Activity.cprofile.getCtrainerImage()!=null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), CustomerProfile1Activity.cprofile.getCtrainerImage());
            profileImgBody2 = MultipartBody.Part.createFormData("profile_image", CustomerProfile1Activity.cprofile.getCtrainerImage().getName(), requestBody);
        }
        final Map<String, RequestBody> field = new HashMap<String, RequestBody>();
        field.put("first_name", requestBody(CustomerProfile1Activity.cprofile.getCfName().trim()));
        field.put("last_name", requestBody(CustomerProfile1Activity.cprofile.getClName().trim()));
        field.put("email", requestBody(CustomerProfile1Activity.cprofile.getCemail().trim()));
        field.put("gender", requestBody(CustomerProfile1Activity.cprofile.getCgender().trim()));
        field.put("phone", requestBody(CustomerProfile1Activity.cprofile.getCphoneNum().trim()));
        field.put("age", requestBody((age.getText().toString().trim())));
        field.put("weight", requestBody(inputWeight.getText().toString().trim()));
        field.put("height", requestBody(inputHeight.getText().toString().trim()));
        field.put("UserId", requestBody(Preferences.getInstance().getUserId()));
        field.put("Waist", requestBody(inputWaist.getText().toString()));
        field.put("Calf", requestBody(inputCalf.getText().toString()));
        field.put("Thigh", requestBody(inputThigh.getText().toString()));
        field.put("Chest", requestBody(inputChest.getText().toString()));
        field.put("Arm", requestBody(inputArm.getText().toString()));
        if (CustomerProfile1Activity.cprofile.getCgender().equalsIgnoreCase("Female")){
            field.put("Hips", requestBody(inputHip.getText().toString()));
        }
        field.put("fat",requestBody(inputFat.getText().toString().trim()));
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.editCustomerProfile(profileImgBody2,field).
                enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(Call<SignUpResponse> call, final Response<SignUpResponse> response) {
                        if (response.body() != null) {
                            if(response.body().isStatus()){
                                alertUser(response.body().getMsg());
                        }
                        else {
                                alertUser(response.body().getMsg());
                        }
                        hideProgressBar();

                    }
                    else {
                            Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    }

                    @Override
                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void alertUser(String msg) {
        if (msg!=null) {
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(CustomerProfile2Activity.this);
            alertDialogBuilder.setTitle(getString(R.string.app_name));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ptlogo);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), CustomerDashboardActivity.class));
                    finish();
                }
            });
            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public boolean validation(){
        if (age.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Age.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputWeight.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Weight.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputHeight.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Height.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputFat.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Body Fat Percentage.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }

    @BindView(R.id.bt_submit_now)
    Button submitBtn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_submit_now)
    void submit(){
        if (validation()){
            editCustomerProfile();
        }
    }

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.input_age)
    EditText age;

    @BindView(R.id.input_height)
    EditText inputHeight;

    @BindView(R.id.input_weight)
    EditText inputWeight;

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

    @BindView(R.id.input_layout_hip)
    TextInputLayout inputlayoutHip;

    @BindView(R.id.input_hip)
    EditText inputHip;

    @BindView(R.id.input_waist)
    EditText inputWaist;

}
