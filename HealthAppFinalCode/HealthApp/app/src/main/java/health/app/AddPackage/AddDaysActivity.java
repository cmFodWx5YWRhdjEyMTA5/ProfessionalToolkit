package health.app.AddPackage;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Activities.TrainerPackageActivity;
import health.app.R;
import health.app.Response.TimeSlotResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddDaysActivity extends BaseActivity  {
    List<String> daysList;
    String packageName1;
    TextView title;
    public static Activity fa;
    String slotId,hour,minute,hour1;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_days);
        ButterKnife.bind(this);
        fa=this;
        daysList=new ArrayList<>();
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        if (AddDurationActivity.trainerAvailabilityModel.getPackageName()!=null) {
            title.setText(AddDurationActivity.trainerAvailabilityModel.getPackageName());
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
        setLength(AddDurationActivity.trainerAvailabilityModel.getSelectDuration());
        String[] splited = AddDurationActivity.trainerAvailabilityModel.getSelectNumber().split("\\s+");
        Log.d("splited","splited"+splited[0]);
        slot.setText("Session\n"+splited[0]);
        days.setText("Days\n"+AddDurationActivity.trainerAvailabilityModel.getSelectDays());
        amount.setText("Amount\n"+"$"+AddDurationActivity.trainerAvailabilityModel.getSelectAmount());
        if (AddDurationActivity.trainerAvailabilityModel.getSelectDay().size()!=0) {
            setDays();
        }
        else {setAllChecked();}
        if (AddDurationActivity.trainerAvailabilityModel.getSlotId()!=null)
        {
            slotId=AddDurationActivity.trainerAvailabilityModel.getSlotId();
            btSubmit.setVisibility(View.GONE);
            btUpdate.setVisibility(View.VISIBLE);
        }
        cbSun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_tick1.setBackgroundColor(Color.parseColor("#004080"));
                    tick1.setVisibility(View.VISIBLE);
                    daysList.add("Sunday");

                }
                else {
                    tick1.setVisibility(View.GONE);
                    ll_tick1.setBackgroundColor(Color.parseColor("#2d9ebf"));
                    daysList.remove("Sunday");
                }
            }
        });
        cbMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_tick2.setBackgroundColor(Color.parseColor("#004080"));
                    tick2.setVisibility(View.VISIBLE);
                    daysList.add("Monday");

                }
                else {
                    ll_tick2.setBackgroundColor(Color.parseColor("#2d9ebf"));
                    tick2.setVisibility(View.GONE);
                    daysList.remove("Monday");

                }
            }
        });
        cbTus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_tick3.setBackgroundColor(Color.parseColor("#004080"));
                    tick3.setVisibility(View.VISIBLE);
                    daysList.add("Tuesday");

                }
                else {
                    ll_tick3.setBackgroundColor(Color.parseColor("#2d9ebf"));
                    tick3.setVisibility(View.GONE);
                    daysList.remove("Tuesday");

                }
            }
        });
        cbWed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_tick4.setBackgroundColor(Color.parseColor("#004080"));
                    tick4.setVisibility(View.VISIBLE);
                    daysList.add("Wednesday");

                }
                else {
                    ll_tick4.setBackgroundColor(Color.parseColor("#2d9ebf"));
                    tick4.setVisibility(View.GONE);
                    daysList.remove("Wednesday");

                }
            }
        });
        cbThu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_tick5.setBackgroundColor(Color.parseColor("#004080"));
                    tick5.setVisibility(View.VISIBLE);
                    daysList.add("Thursday");

                }
                else {
                    ll_tick5.setBackgroundColor(Color.parseColor("#2d9ebf"));
                    tick5.setVisibility(View.GONE);
                    daysList.remove("Thursday");

                }
            }
        });
        cbFri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_tick6.setBackgroundColor(Color.parseColor("#004080"));
                    tick6.setVisibility(View.VISIBLE);
                    daysList.add("Friday");

                }
                else {
                    ll_tick6.setBackgroundColor(Color.parseColor("#2d9ebf"));
                    tick6.setVisibility(View.GONE);
                    daysList.remove("Friday");

                }
            }
        });
        cbSat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_tick7.setBackgroundColor(Color.parseColor("#004080"));
                    tick7.setVisibility(View.VISIBLE);
                    daysList.add("Saturday");

                }
                else {
                    ll_tick7.setBackgroundColor(Color.parseColor("#2d9ebf"));
                    tick7.setVisibility(View.GONE);
                    daysList.remove("Saturday");

                }
            }
        });
    }

    private void setLength(String selectDuration) {
        String[] splited = selectDuration.split(":");
        hour=splited[0];
        minute=splited[1];
        if (hour.startsWith("0")) {
            Log.d("startsWith","startsWith"+hour.substring(1));
            hour1= hour.substring(1);
            if (hour1.equalsIgnoreCase("0")) {
                duration.setText("Length\n"+minute + " mins");
            }
            else {
                if (minute.equalsIgnoreCase("00")) {
                    duration.setText("Length\n"+hour1 + " hr");
                }
                else {
                    duration.setText("Length\n"+hour1 + " hr " + minute + " mins");
                }
            }
        }
        else {
            if (minute.equalsIgnoreCase("00")) {
                Log.d("startsWith1","startsWith1"+"startsWith1");
                duration.setText("Length\n"+hour + " hr");
            }
            else {
                duration.setText("Length\n"+hour + " hr " + minute + " mins");
            }
        }
    }

    private void setAllChecked() {
        cbSun.setChecked(true);
        cbMon.setChecked(true);
        cbTus.setChecked(true);
        cbWed.setChecked(true);
        cbThu.setChecked(true);
        cbFri.setChecked(true);
        cbSat.setChecked(true);
        if (cbSun.isChecked())
        {
            ll_tick1.setBackgroundColor(Color.parseColor("#004080"));
            tick1.setVisibility(View.VISIBLE);
            daysList.add("Sunday");
        }
        if (cbMon.isChecked()){
            ll_tick2.setBackgroundColor(Color.parseColor("#004080"));
            tick2.setVisibility(View.VISIBLE);
            daysList.add("Monday");
        }
        if (cbTus.isChecked()){
            ll_tick3.setBackgroundColor(Color.parseColor("#004080"));
            tick3.setVisibility(View.VISIBLE);
            daysList.add("Tuesday");
        }
        if (cbWed.isChecked()){
            ll_tick4.setBackgroundColor(Color.parseColor("#004080"));
            tick4.setVisibility(View.VISIBLE);
            daysList.add("Wednesday");
        }
        if (cbThu.isChecked()){
            ll_tick5.setBackgroundColor(Color.parseColor("#004080"));
            tick5.setVisibility(View.VISIBLE);
            daysList.add("Thursday");
        }
        if (cbFri.isChecked()){
            ll_tick6.setBackgroundColor(Color.parseColor("#004080"));
            tick6.setVisibility(View.VISIBLE);
            daysList.add("Friday");
        }
        if (cbSat.isChecked()){
            ll_tick7.setBackgroundColor(Color.parseColor("#004080"));
            tick7.setVisibility(View.VISIBLE);
            daysList.add("Saturday");
        }
    }

    private void setDays() {
            for (int i=0;i<AddDurationActivity.trainerAvailabilityModel.getSelectDay().size();i++)
            {
                if (AddDurationActivity.trainerAvailabilityModel.getSelectDay().get(i).equalsIgnoreCase("Sunday"))
                {
                    cbSun.setChecked(true);
                    ll_tick1.setBackgroundColor(Color.parseColor("#004080"));
                    tick1.setVisibility(View.VISIBLE);
                    daysList.add("Sunday");
                }
                 if (AddDurationActivity.trainerAvailabilityModel.getSelectDay().get(i).equalsIgnoreCase("Monday"))
                {
                    cbMon.setChecked(true);
                    ll_tick2.setBackgroundColor(Color.parseColor("#004080"));
                    tick2.setVisibility(View.VISIBLE);
                    daysList.add("Monday");
                }
                 if (AddDurationActivity.trainerAvailabilityModel.getSelectDay().get(i).equalsIgnoreCase("Tuesday"))
                {
                    ll_tick3.setBackgroundColor(Color.parseColor("#004080"));
                    tick3.setVisibility(View.VISIBLE);
                    cbTus.setChecked(true);
                    daysList.add("Tuesday");
                }
                 if (AddDurationActivity.trainerAvailabilityModel.getSelectDay().get(i).equalsIgnoreCase("Wednesday"))
                {
                    ll_tick4.setBackgroundColor(Color.parseColor("#004080"));
                    tick4.setVisibility(View.VISIBLE);
                    cbWed.setChecked(true);
                    daysList.add("Wednesday");
                }
                 if (AddDurationActivity.trainerAvailabilityModel.getSelectDay().get(i).equalsIgnoreCase("Thursday"))
                {
                    ll_tick5.setBackgroundColor(Color.parseColor("#004080"));
                    tick5.setVisibility(View.VISIBLE);
                    cbThu.setChecked(true);
                    daysList.add("Thursday");
                }
                 if (AddDurationActivity.trainerAvailabilityModel.getSelectDay().get(i).equalsIgnoreCase("Friday"))
                {
                    ll_tick6.setBackgroundColor(Color.parseColor("#004080"));
                    tick6.setVisibility(View.VISIBLE);
                    cbFri.setChecked(true);
                    daysList.add("Friday");
                }
                 if (AddDurationActivity.trainerAvailabilityModel.getSelectDay().get(i).equalsIgnoreCase("Saturday"))
                {
                    ll_tick7.setBackgroundColor(Color.parseColor("#004080"));
                    tick7.setVisibility(View.VISIBLE);
                    cbSat.setChecked(true);
                    daysList.add("Saturday");
                }
            }
    }

    String getCommonSeperatedString(List<String> actionObjects) {
        StringBuffer sb = new StringBuffer();
        for (String actionObject : actionObjects){
            sb.append(actionObject).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void makePackage(){
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.addTrainerPackage(AddDurationActivity.trainerAvailabilityModel.getSelectDuration(),AddDurationActivity.trainerAvailabilityModel.getSelectAmount(),AddDurationActivity.trainerAvailabilityModel.getPackageName(), getCommonSeperatedString(daysList),AddDurationActivity.trainerAvailabilityModel.getSelectDays(), Preferences.getInstance().getUserId(),AddDurationActivity.trainerAvailabilityModel.getSelectNumber()).
                enqueue(new Callback<TimeSlotResponse>() {
                    @Override
                    public void onResponse(Call<TimeSlotResponse> call, final Response<TimeSlotResponse> response) {
                        if (response.body().isStatus()) {
                            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(AddDaysActivity.this);
                            alertDialogBuilder.setTitle(getString(R.string.app_name));
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setIcon(R.drawable.ptlogo);
                            alertDialogBuilder.setMessage(response.body().getMsg());
                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), TrainerPackageActivity.class);
                                    intent.putExtra("replace","replace");
                                    startActivity(intent);

                                }
                            });

                            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        else {
                            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(AddDaysActivity.this);
                            alertDialogBuilder.setTitle(getString(R.string.app_name));
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setIcon(R.drawable.ptlogo);
                            alertDialogBuilder.setMessage(response.body().getMsg());
                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });

                            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<TimeSlotResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updatePackage(){
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.updateTrainerPackage(AddDurationActivity.trainerAvailabilityModel.getSelectDuration(),AddDurationActivity.trainerAvailabilityModel.getSelectAmount(),AddDurationActivity.trainerAvailabilityModel.getPackageName(), getCommonSeperatedString(daysList),AddDurationActivity.trainerAvailabilityModel.getSelectDays(), Preferences.getInstance().getUserId(),AddDurationActivity.trainerAvailabilityModel.getSelectNumber(),slotId).
                enqueue(new Callback<TimeSlotResponse>() {
                    @Override
                    public void onResponse(Call<TimeSlotResponse> call, final Response<TimeSlotResponse> response) {
                        if (response.body().isStatus()) {
                            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(AddDaysActivity.this);
                            alertDialogBuilder.setTitle(getString(R.string.app_name));
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setIcon(R.drawable.ptlogo);
                            alertDialogBuilder.setMessage(response.body().getMsg());
                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                   Intent intent = new Intent(getApplicationContext(), TrainerPackageActivity.class);
                                    intent.putExtra("replace","replace");
                                    startActivity(intent);

                                }
                            });

                            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        else {
                            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(AddDaysActivity.this);
                            alertDialogBuilder.setTitle(getString(R.string.app_name));
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setIcon(R.drawable.ptlogo);
                            alertDialogBuilder.setMessage(response.body().getMsg());
                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });

                            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<TimeSlotResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_submit)
    void addPackage(){
        Log.d("daysList","daysList"+daysList.size());
        if (daysList.size()!=0) {
            if (daysList.size() < Integer.parseInt(AddDurationActivity.trainerAvailabilityModel.getSelectDays()) || daysList.size() == Integer.parseInt(AddDurationActivity.trainerAvailabilityModel.getSelectDays())) {
                makePackage();
            } else {
                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.app_name));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.ptlogo);
                alertDialogBuilder.setMessage("Please select weekdays according to your selected number of days.");
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
        else {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Weekdays", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_update)
    void updatePack(){
        if (daysList.size()!=0) {
            if (daysList.size() < Integer.parseInt(AddDurationActivity.trainerAvailabilityModel.getSelectDays()) || daysList.size() == Integer.parseInt(AddDurationActivity.trainerAvailabilityModel.getSelectDays())) {
                updatePackage();
            } else {
                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.app_name));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.ptlogo);
                alertDialogBuilder.setMessage("Please select weekdays according to your selected number of days.");
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
        else {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Weekdays", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        }
    }

    @BindView(R.id.ll_tick1)
    LinearLayout ll_tick1;

    @BindView(R.id.ll_tick2)
    LinearLayout ll_tick2;

    @BindView(R.id.ll_tick3)
    LinearLayout ll_tick3;

    @BindView(R.id.ll_tick4)
    LinearLayout ll_tick4;

    @BindView(R.id.ll_tick5)
    LinearLayout ll_tick5;

    @BindView(R.id.ll_tick6)
    LinearLayout ll_tick6;

    @BindView(R.id.ll_tick7)
    LinearLayout ll_tick7;

    @BindView(R.id.cb_sun)
    CheckBox cbSun;

    @BindView(R.id.cb_mon)
    CheckBox cbMon;

    @BindView(R.id.cb_tus)
    CheckBox cbTus;

    @BindView(R.id.cb_wed)
    CheckBox cbWed;

    @BindView(R.id.cb_thu)
    CheckBox cbThu;

    @BindView(R.id.cb_fri)
    CheckBox cbFri;


    @BindView(R.id.cb_sat)
    CheckBox cbSat;

    @BindView(R.id.duration)
    TextView duration;

    @BindView(R.id.container)
    RelativeLayout container;

    @BindView(R.id.tick1)
    ImageView tick1;

    @BindView(R.id.tick2)
    ImageView tick2;

    @BindView(R.id.tick3)
    ImageView tick3;

    @BindView(R.id.tick4)
    ImageView tick4;

    @BindView(R.id.tick5)
    ImageView tick5;

    @BindView(R.id.tick6)
    ImageView tick6;

    @BindView(R.id.tick7)
    ImageView tick7;

    @BindView(R.id.slot)
    TextView slot;

    @BindView(R.id.days)
    TextView days;

    @BindView(R.id.amount)
    TextView amount;

//    @OnClick(R.id.amount)
//    void goAmount(){
//        startActivity(new Intent(getApplicationContext(), AddAmountActivity.class));
//        finish();
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
//    }
//
//    @OnClick(R.id.slot)
//    void goSlot(){
//        startActivity(new Intent(getApplicationContext(), AddSlotsActivity.class));
//        finish();
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
//    }
//
//    @OnClick(R.id.days)
//    void goDay(){
//        startActivity(new Intent(getApplicationContext(), AddNoOfDayActivity.class));
//        finish();
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
//    }
//
//    @OnClick(R.id.duration)
//    void goDuration(){
//        startActivity(new Intent(getApplicationContext(), AddDurationActivity.class));
//        finish();
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
//    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bt_submit)
    Button btSubmit;

    @BindView(R.id.bt_update)
    Button btUpdate;

}