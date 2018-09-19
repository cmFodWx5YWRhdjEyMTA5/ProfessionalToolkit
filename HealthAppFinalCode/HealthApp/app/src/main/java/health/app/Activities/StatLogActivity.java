package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.R;
import health.app.Response.GetUserInfoResponse;
import health.app.Response.MeasurementResponse;
import health.app.Response.TrainerDashboardListResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StatLogActivity extends BaseActivity {
    double waist,neck,hip,weight,height,arm,chest,thigh,calf;
    String todayString;
    TrainerDashboardListResponse.ApprovedData trainerData;
    public static final String STAT = "stat";
    String customerGender,clientId;
    String initialHeight, initialWeight,initialFat,initialNeck,initialWaist,initialHip,initialArm,initialCalf,initialThigh,initialChest;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_log);
        ButterKnife.bind(this);
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")){
            for (int i=0;i<CustomerMeasurmentActivity.allmeasurementList.size();i++) {
                bHeight.setText("Body Height " + "\"" + CustomerMeasurmentActivity.allmeasurementList.get(i).getHeight() + " cm" + "\"");
                bWeight.setText("Body Weight " + "\"" + CustomerMeasurmentActivity.allmeasurementList.get(i).getWeight() + " kg" + "\"");
                bBodyFat.setText("BMI " + "\"" + CustomerMeasurmentActivity.allmeasurementList.get(i).getBMI() + "\"");
                //bNeck.setText("Neck Measurement " + "\"" + CustomerMeasurmentActivity.allmeasurementList.get(i).getNeck() + " cm" + "\"");
                bWaist.setText("Waist Measurement " + "\"" + CustomerMeasurmentActivity.allmeasurementList.get(i).getWaist() + " cm" + "\"");
                bHip.setText("Hip Measurement " + "\"" + CustomerMeasurmentActivity.allmeasurementList.get(i).getHips() + " cm" + "\"");
                bArm.setText("Arm Measurement " + "\"" + CustomerMeasurmentActivity.allmeasurementList.get(i).getArm() + " cm" + "\"");
                bCalf.setText("Calf Measurement " + "\"" + CustomerMeasurmentActivity.allmeasurementList.get(i).getCalf() + " cm" + "\"");
                bChest.setText("Chest Measurement " + "\"" + CustomerMeasurmentActivity.allmeasurementList.get(i).getChest() + " cm" + "\"");
                bThigh.setText("Thigh Measurement " + "\"" + CustomerMeasurmentActivity.allmeasurementList.get(i).getThigh() + " cm" + "\"");
            }
        }

        if ((trainerData = (TrainerDashboardListResponse.ApprovedData) getIntent().getParcelableExtra(STAT))!=null)
        {
            clientId=trainerData.getCustomerId();
        }
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Add Stat");
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")){
            title.setText("Measurement");
        }
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
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        todayString = formatter.format(todayDate);
        Log.d("todayString","todayString"+todayString);
        setFont();
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            getUserInfo(clientId);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getUserInfo(String userId) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getData(userId).enqueue(new Callback<GetUserInfoResponse>() {
            @Override
            public void onResponse(Call<GetUserInfoResponse> call, Response<GetUserInfoResponse> response) {
                if (response.body().isStatus()) {
                    if (response.body().getUserData()!=null)
                    {
                        customerGender=response.body().getUserData().getGender();
                        initialHeight=response.body().getUserData().getHeight();
                        initialWeight=response.body().getUserData().getWeight();
                        initialFat=response.body().getUserData().getFat();
                        //initialNeck=response.body().getUserData().getNeck();
                        initialWaist=response.body().getUserData().getWaist();
                        initialHip=response.body().getUserData().getHips();
                        initialArm=response.body().getUserData().getArm();
                        initialCalf=response.body().getUserData().getCalf();
                        initialChest=response.body().getUserData().getChest();
                        initialThigh=response.body().getUserData().getThigh();
                        Log.d("measure","measure"+response.body().getUserData().getHeight()+response.body().getUserData().getWeight()+response.body().getUserData().getFat()+response.body().getUserData().getNeck()+response.body().getUserData().getWaist()+response.body().getUserData().getHips());
                        bHeight.setText("Body Height "+"\""+ response.body().getUserData().getHeight()+" cm"+"\"");
                        bWeight.setText("Body Weight "+"\""+response.body().getUserData().getWeight()+" kg"+"\"");
                        bBodyFat.setText("BMI "+"\""+response.body().getUserData().getFat()+"\"");
                        bNeck.setText("Neck Measurement "+"\""+ response.body().getUserData().getNeck()+" cm"+"\"");
                        bWaist.setText("Waist Measurement "+"\""+ response.body().getUserData().getWaist()+" cm"+"\"");
                        bHip.setText("Hip Measurement "+"\""+ response.body().getUserData().getHips()+" cm"+"\"");
                        bArm.setText("Arm Measurement "+"\""+ response.body().getUserData().getArm()+" cm"+"\"");
                        bCalf.setText("Calf Measurement "+"\""+ response.body().getUserData().getCalf()+" cm"+"\"");
                        bChest.setText("Chest Measurement "+"\""+ response.body().getUserData().getChest()+" cm"+"\"");
                        bThigh.setText("Thigh Measurement "+"\""+ response.body().getUserData().getThigh()+" cm"+"\"");
                    }

                }

                hideProgressBar();

            }

            @Override
            public void onFailure(Call<GetUserInfoResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setFont(){
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        bHeight.setTypeface(font);
        bWeight.setTypeface(font);
        bBodyFat.setTypeface(font);
       // bNeck.setTypeface(font);
        bHip.setTypeface(font);
        bWaist.setTypeface(font);
        bArm.setTypeface(font);
        bCalf.setTypeface(font);
        bChest.setTypeface(font);
        bThigh.setTypeface(font);
    }

    public void showAmount(final String key, String title) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StatLogActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.selectTime);
        dialogBuilder.setTitle(title);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setIcon(R.drawable.ptlogo);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(DialogInterface dialog, int whichButton) {
                if (key.equalsIgnoreCase("Height"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        height = Double.parseDouble(edt.getText().toString());
                        bHeight.setText("Body Height " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,String.valueOf(height),initialWaist,initialNeck,initialFat,initialHip,initialArm,initialChest,initialCalf,initialThigh,todayString);
                    }

                }
                else if (key.equalsIgnoreCase("Weight"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        weight = Double.parseDouble(edt.getText().toString());
                        bWeight.setText("Body Weight " + "\"" + edt.getText().toString() + " kg" + "\"");
                        addMeasurement(String.valueOf(edt.getText().toString()),initialHeight,initialWaist,initialNeck,initialFat,initialHip,initialArm,initialChest,initialCalf,initialThigh,todayString);
                    }

                }
//                else if (key.equalsIgnoreCase("Neck"))
//                {
//                    if (edt.getText().toString().trim().length()!=0) {
//                        neck = Double.parseDouble(edt.getText().toString());
//                        bNeck.setText("Neck Measurement " + "\"" + edt.getText().toString() + " cm" + "\"");
//                        addMeasurement(initialWeight,initialHeight,initialWaist,String.valueOf(neck),initialFat,initialHip,initialArm,initialChest,initialCalf,initialThigh,todayString);
//                    }
//
//                }
                else if (key.equalsIgnoreCase("Hip"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        hip = Double.parseDouble(edt.getText().toString());
                        bHip.setText("Hip Measurement " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,initialHeight,initialWaist,initialNeck,initialFat,String.valueOf(hip),initialArm,initialChest,initialCalf,initialThigh,todayString);
                    }

                }
                else if (key.equalsIgnoreCase("Waist"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        waist = Double.parseDouble(edt.getText().toString());
                        bWaist.setText("Waist Measurement " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,initialHeight,String.valueOf(waist),initialNeck,initialFat,initialHip,initialArm,initialChest,initialCalf,initialThigh,todayString);
                    }
                }
                else if (key.equalsIgnoreCase("Chest"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        chest = Double.parseDouble(edt.getText().toString());
                        bChest.setText("Chest Measurement " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,initialHeight,initialWaist,initialNeck,initialFat,initialHip,initialArm,String.valueOf(chest),initialCalf,initialThigh,todayString);
                    }
                }
                else if (key.equalsIgnoreCase("Arm"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        arm = Double.parseDouble(edt.getText().toString());
                        bArm.setText("Arm Measurement " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,initialHeight,initialWaist,initialNeck,initialFat,initialHip,String.valueOf(arm),initialChest,initialCalf,initialThigh,todayString);
                    }
                }
                else if (key.equalsIgnoreCase("Thigh"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        thigh = Double.parseDouble(edt.getText().toString());
                        bThigh.setText("Thigh Measurement " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,initialHeight,initialWaist,initialNeck,initialFat,initialHip,initialArm,initialChest,initialCalf,String.valueOf(thigh),todayString);
                    }
                }

                else if (key.equalsIgnoreCase("Calf"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        calf = Double.parseDouble(edt.getText().toString());
                        Log.d("calf1","calf1"+calf);
                        bCalf.setText("Calf Measurement " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,initialHeight,initialWaist,initialNeck,initialFat,initialHip,initialArm,initialChest,String.valueOf(calf),initialThigh,todayString);
                    }
                }


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addMeasurement(String weightValue,String heightValue,String waistValue,String neckValue,String fatValue,String hipValue,String armValue,String chestValue,String calfValue,String thighValue,String todayDate) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.addStats(Preferences.getInstance().getUserId(),clientId,weightValue,heightValue,waistValue,neckValue,fatValue,hipValue,armValue,chestValue,calfValue,thighValue,todayDate).
                enqueue(new Callback<MeasurementResponse>() {
                    @Override
                    public void onResponse(Call<MeasurementResponse> call, Response<MeasurementResponse> response) {
                        if (response.body().isStatus()){
                            alertUser(response.body().getMsg());
                        }
                        else {
                            alertUser(response.body().getMsg());
                        }

                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<MeasurementResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void alertUser(String msg) {
        if (msg!=null) {
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(StatLogActivity.this);
            alertDialogBuilder.setTitle(getString(R.string.app_name));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ptlogo);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getUserInfo(clientId);
                    dialog.dismiss();

                }
            });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public boolean validation(){
        if (bWeight.getText().toString().equalsIgnoreCase("Tap Here To Add Body Weight")){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Body Weight First.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (bHeight.getText().toString().equalsIgnoreCase("Tap Here To Add Body Height"))
        {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Body Height First.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }

    @OnClick(R.id.ll_height)
    void getHeight(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            showAmount("Height", "Enter Height like 56 cm");
        }
    }

    @OnClick(R.id.ll_weight)
    void getWeight(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            showAmount("Weight", "Enter Weight like 56 kg");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.ll_bodyfat)
    void getBodyFat(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            if (validation()) {
              //  if (customerGender.equalsIgnoreCase("Male")) {
            double heightInM=height/100;
            double height=heightInM*heightInM;
            double BMI=weight/height;
                   // double BMI = 495 / (1.0324 - 0.19077 * (Math.log10(waist - neck)) + 0.15456 * (Math.log10(height))) - 450;


                    Log.d("BMI", "BMI" + BMI+"heightInM"+heightInM+"height"+height);
                    bBodyFat.setText("BMI " + "\"" + BMI + "\"");
                    String updatedBMI = String.valueOf(BMI);
                    addMeasurement(initialWeight, initialHeight, initialWaist, initialNeck, updatedBMI, initialHip, initialArm, initialChest, initialCalf, initialThigh, todayString);
//                } else if (customerGender.equalsIgnoreCase("Female")) {
//                    double BMI = 495 / (1.29579 - 0.35004 * (Math.log10(waist + hip - neck)) + 0.22100 * (Math.log10(height))) - 450;
//                    bBodyFat.setText("BMI " + "\"" + BMI + "\"");
//                    String updatedBMI = String.valueOf(BMI);
//                    addMeasurement(initialWeight, initialHeight, initialWaist, initialNeck, updatedBMI, initialHip, initialArm, initialChest, initialCalf, initialThigh, todayString);
//                }
            }
        }
    }

    @OnClick(R.id.ll_neck)
    void getNeck(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            showAmount("Neck", "Enter Neck size like 56 cm");
        }
    }

    @OnClick(R.id.ll_waist)
    void getWaist(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            showAmount("Waist", "Enter Waist size like 56 cm");
        }
    }

    @OnClick(R.id.ll_hip)
    void getHip(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            showAmount("Hip", "Enter Hip size like 56 cm");
        }
    }

    @OnClick(R.id.ll_arm)
    void getArm(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            showAmount("Arm", "Enter Arm size like 56 cm");
        }
    }

    @OnClick(R.id.ll_calf)
    void getCalf(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            showAmount("Calf", "Enter Calf size like 56 cm");
        }
    }

    @OnClick(R.id.ll_thigh)
    void getThigh(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            showAmount("Thigh", "Enter Thigh size like 56 cm");
        }
    }

    @OnClick(R.id.ll_chest)
    void getChest(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            showAmount("Chest", "Enter Chest size like 56 cm");
        }
    }

    @BindView(R.id.bHeight)
    TextView bHeight;

    @BindView(R.id.bWeight)
    TextView bWeight;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bBodyFat)
    TextView bBodyFat;

    @BindView(R.id.bHip)
    TextView bHip;

    @BindView(R.id.bWaist)
    TextView bWaist;

    @BindView(R.id.bChest)
    TextView bChest;

    @BindView(R.id.bArm)
    TextView bArm;

    @BindView(R.id.bCalf)
    TextView bCalf;

    @BindView(R.id.bThigh)
    TextView bThigh;

    @BindView(R.id.bNeck)
    TextView bNeck;

    @BindView(R.id.container)
    LinearLayout container;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo(clientId);
    }
}
