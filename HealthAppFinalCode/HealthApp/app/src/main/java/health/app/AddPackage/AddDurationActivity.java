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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Model.TrainerAvailabilityModel;
import health.app.R;
import health.app.Response.TrainerPackageResponse;
import health.app.Utilities.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddDurationActivity extends BaseActivity {
    SimpleDateFormat simpleTimeFormat;
    String packageName1;
    public static TrainerAvailabilityModel trainerAvailabilityModel;
    public TrainerPackageResponse.PackageData packageData;
    public static final String PACKAGE = "package";
    SingleDateAndTimePickerDialog.Builder singleBuilder;
    SingleDateAndTimePicker singleDateAndTimePicker;
    boolean isAmPm=false;
    List<String> timeArrayList=new ArrayList<>();
    String [] timeArray1;
    String hour,minute1;
    String hours,minutes,hours1;
    String timeDuration;
    LinearLayout wheel;
    TextView title;
    WheelPicker wheelLeft,wheelCenter;
    TextView done,cancel;
    public static Activity fa;
    String [] timeArray={"15 mins","30 mins","45 mins","1 Hour","Add Others"};

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_duration);
        ButterKnife.bind(this);
        fa=this;
        trainerAvailabilityModel=new TrainerAvailabilityModel();
        title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Add Package");
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
        timeArrayList.add("15 mins");
        timeArrayList.add("30 mins");
        timeArrayList.add("45 mins");
        timeArrayList.add("1 Hour");
        timeArrayList.add("Add Others");
        if ((packageData = (TrainerPackageResponse.PackageData) getIntent().getParcelableExtra(PACKAGE))!=null)
        {
            Log.d("packageData","packageData"+packageData.getSessionPrice());
            setValues(packageData);

        }
        addPackageName();
        if (trainerAvailabilityModel.getSelectDuration()!=null){
            setLength(trainerAvailabilityModel.getSelectDuration());
           // tvselectDuration.setText(TDashboardAgainFragment.trainerAvailabilityModel.getSelectDuration());
            timeDuration=trainerAvailabilityModel.getSelectDuration();
        }
        timeArray1 = timeArrayList.toArray(new String[timeArrayList.size()]);
        singleDateAndTimePicker=new SingleDateAndTimePicker(getApplicationContext());
        singleDateAndTimePicker.setIsAmPm(!isAmPm);
        this.simpleTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        List<String> datalist1=new ArrayList<String>();
        for (int i=0;i<24;i++)
        {
            if (String.valueOf(i).length()==1)
            {
                datalist1.add("0"+String.valueOf(i));
                Log.d("datalist","datalist"+datalist1);
            }
            else if (String.valueOf(i).length()==2)
            {
                datalist1.add(String.valueOf(i));
                Log.d("datalist","datalist"+datalist1);
            }
        }
        List<String> datalist2=new ArrayList<String>();
        for (int i=0;i<60;i++)
        {
            if (String.valueOf(i).length()==1)
            {
                datalist2.add("0"+String.valueOf(i));
                Log.d("datalist","datalist"+datalist1);
            }
            else if (String.valueOf(i).length()==2)
            {
                datalist2.add(String.valueOf(i));
                Log.d("datalist","datalist"+datalist1);
            }
        }
        wheel=(LinearLayout)findViewById(R.id.wheel);
        done=(TextView) findViewById(R.id.done);
        cancel=(TextView) findViewById(R.id.cancel);
        wheelLeft = (WheelPicker) findViewById(R.id.main_wheel_left);
        wheelLeft.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                hour = (String.valueOf(data));
                Log.d("hour","hour"+hour);
            }


        });
        wheelCenter = (WheelPicker) findViewById(R.id.main_wheel_center);
        wheelCenter.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                minute1 = (String.valueOf(data));
            }
        });
        wheelCenter.setData(datalist2);
        wheelLeft.setData(datalist1);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("minute","minute"+minute1+hour);
                wheel.setVisibility(View.GONE);
                if (hour==null && minute1==null){
                    //tvselectDuration.setText("00" + ":" + "00");
                    timeDuration="00" + ":" + "00";
                    trainerAvailabilityModel.setSelectDuration("00" + ":" + "00");
                    setLength(timeDuration);
                }
                else if (hour!=null && minute1!=null) {
                    //tvselectDuration.setText(hour + ":" + minute1);
                    timeDuration=hour + ":" + minute1;
                    trainerAvailabilityModel.setSelectDuration(timeDuration);
                    setLength(timeDuration);
                }
                else if (hour==null){
                    //tvselectDuration.setText("00" + ":" + minute1);
                    timeDuration="00" + ":" + minute1;
                    trainerAvailabilityModel.setSelectDuration(timeDuration);
                    setLength(timeDuration);
                }
                else if (hour!=null && minute1==null){
                    //tvselectDuration.setText(hour + ":" + "00");
                    timeDuration=hour + ":" + "00";
                    trainerAvailabilityModel.setSelectDuration(timeDuration);
                    setLength(timeDuration);
                }
                else if (hour==null && minute1!=null){
                    //tvselectDuration.setText("00" + ":" + minute1);
                    timeDuration="00" + ":" + minute1;
                    trainerAvailabilityModel.setSelectDuration(timeDuration);
                    setLength(timeDuration);
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wheel.setVisibility(View.GONE);
            }
        });
    }

    private void setLength(String selectDuration) {
        String[] splited = selectDuration.split(":");
        hours=splited[0];
        minutes=splited[1];
        if (hours.startsWith("0")) {
            Log.d("startsWith","startsWith"+hours.substring(1));
            hours1= hours.substring(1);
            if (hours1.equalsIgnoreCase("0")) {
                tvselectDuration.setText(minutes + " mins");
            }
            else {
                if (minutes.equalsIgnoreCase("00")) {
                    tvselectDuration.setText(hours1 + " hr");
                }
                else {
                    tvselectDuration.setText(hours1 + " hr " + minutes + " mins");
                }
            }
        }
        else {
            if (minutes.equalsIgnoreCase("00")) {
                Log.d("startsWith1","startsWith1"+"startsWith1");
                tvselectDuration.setText(hours + " hr");
            }
            else {
                tvselectDuration.setText(hours + " hr " + minutes + " mins");
            }
        }
    }

    public void setValues(TrainerPackageResponse.PackageData packageData) {
        trainerAvailabilityModel.setPackageName(packageData.getSlotTitle());
        trainerAvailabilityModel.setSelectAmount(packageData.getSessionPrice());
        trainerAvailabilityModel.setSelectDuration(packageData.getSessionTime());
        trainerAvailabilityModel.setSelectNumber(packageData.getNoOfSlots());
        trainerAvailabilityModel.setSelectDays(packageData.getNoOfDays());
        trainerAvailabilityModel.setSlotId(packageData.getSlotId());
        ArrayList<String> items = new ArrayList(Arrays.asList(packageData.getSessionDays().split("\\s*,\\s*")));
        trainerAvailabilityModel.setSelectDay(items);
    }

    public void addPackageName() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.selectTime);
        edt.setInputType(InputType.TYPE_CLASS_TEXT);
        if (trainerAvailabilityModel.getPackageName()!=null){
            edt.setText(trainerAvailabilityModel.getPackageName());
        }
        dialogBuilder.setTitle("What would you like to name your package?");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setIcon(R.drawable.ptlogo);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(DialogInterface dialog, int whichButton) {
                if (edt.getText().toString().trim().length() == 0) {
                    Snackbar snackbar = Snackbar.make(container, "Please Enter Package Name", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(Color.RED);
                    snackbar.show();
                }
                else {
                    packageName1 =edt.getText().toString();
                    title.setText(packageName1);
                    trainerAvailabilityModel.setPackageName(packageName1);
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

    public static void showDialog(Context c, String title, final String[] array, final onGenderSelected target) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.genderSelection(i);
                dialogInterface.dismiss();
            }
        });

        builder.show();


    }

    public interface onGenderSelected{
        public void genderSelection(int position);
    }



    @BindView(R.id.tv_select_duration)
    TextView tvselectDuration;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    RelativeLayout container;

    @OnClick(R.id.bt_next)
    void next(){
        if (packageName1== null){
            addPackageName();
        }
        else if (tvselectDuration.getText().equals("Select Session Length")) {
            Snackbar snackbar = Snackbar.make(container, "Please select session length", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        }
        else {
            if (timeDuration!=null) {
                trainerAvailabilityModel.setSelectDuration(timeDuration);
                startActivity(new Intent(getApplicationContext(), AddSlotsActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        }
    }



    @OnClick(R.id.iv_select_duration)
    void selectDuration(){
        showDialog(AddDurationActivity.this, "Select Session",timeArray1,new onGenderSelected() {
            @Override
            public void genderSelection(int position) {
                tvselectDuration.setText(timeArray1[position]);
                tvselectDuration.setTextColor(Color.BLACK);
                if (timeArray1[position].equalsIgnoreCase("15 mins"))
                {
                    timeDuration="00:15:00";
                   // trainerAvailabilityModel.setSelectDuration("00:15:00");
                }
                else if (timeArray1[position].equalsIgnoreCase("30 mins"))
                {
                    timeDuration="00:30:00";
                   //trainerAvailabilityModel.setSelectDuration("00:30:00");
                }
                else if (timeArray1[position].equalsIgnoreCase("45 mins"))
                {
                    timeDuration="00:45:00";
                    //trainerAvailabilityModel.setSelectDuration("00:45:00");
                }
                else if (timeArray1[position].equalsIgnoreCase("1 Hour"))
                {
                    timeDuration="01:00:00";
                    //trainerAvailabilityModel.setSelectDuration("01:00:00");
                }

                if (timeArray1[position].equalsIgnoreCase("Add Others")) {
                    wheel.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
