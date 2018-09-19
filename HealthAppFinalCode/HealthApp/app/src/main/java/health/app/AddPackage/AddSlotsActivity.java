package health.app.AddPackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.R;
import health.app.Utilities.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddSlotsActivity extends BaseActivity {
    String slots,sendSlot,hour,minute,hour1;
    public static Activity fa;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_slots);
        ButterKnife.bind(this);
        fa=this;
        if (AddDurationActivity.trainerAvailabilityModel.getSelectNumber()!=null){
            selectNumber.setText(AddDurationActivity.trainerAvailabilityModel.getSelectNumber());
            sendSlot = AddDurationActivity.trainerAvailabilityModel.getSelectNumber();
            AddDurationActivity.trainerAvailabilityModel.setSelectNumber(sendSlot);
        }
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText(AddDurationActivity.trainerAvailabilityModel.getPackageName());
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
        List<String> datalist1=new ArrayList<String>();
        datalist1.add("1 Session");
        datalist1.add("2 Session");
        datalist1.add("3 Session");
        datalist1.add("4 Session");
        datalist1.add("5 Session");
        setLength(AddDurationActivity.trainerAvailabilityModel.getSelectDuration());
        slot.setText("Session");
        days.setText("Days");
        amount.setText("Amount");
        wheelLeft.setData(datalist1);
        wheelLeft.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                slots = (String.valueOf(data));
                Log.d("hour","hour"+slots);
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


    @OnClick(R.id.done)
    void done(){
        wheel.setVisibility(View.GONE);
        if (slots!=null) {
            selectNumber.setText(slots);
            sendSlot = slots;
            AddDurationActivity.trainerAvailabilityModel.setSelectNumber(sendSlot);
        }
    }

    @OnClick(R.id.cancel)
    void cancel(){
        wheel.setVisibility(View.GONE);
    }

    @BindView(R.id.main_wheel_left)
    WheelPicker wheelLeft;

    @BindView(R.id.ll_duration)
    LinearLayout llDuration;

    @OnClick(R.id.iv_select_number)
    void selectSlot(){
        wheel.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_next)
    void next(){
        if (selectNumber.getText().equals("Select Number Of Session Count In One Day")) {
            Snackbar snackbar = Snackbar.make(container, "Please select session count", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        }
        else {
            if (sendSlot!=null) {
                AddDurationActivity.trainerAvailabilityModel.setSelectNumber(sendSlot);
                startActivity(new Intent(getApplicationContext(), AddNoOfDayActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        }
    }


    @BindView(R.id.tv_select_number)
    TextView selectNumber;

    @BindView(R.id.wheel)
    LinearLayout wheel;

    @BindView(R.id.ll_slots)
    LinearLayout llSlots;

    @BindView(R.id.ll_days)
    LinearLayout llDays;

    @BindView(R.id.ll_amount)
    LinearLayout llAmount;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.duration)
    TextView duration;

    @BindView(R.id.slot)
    TextView slot;

    @BindView(R.id.container)
    RelativeLayout container;

    @BindView(R.id.days)
    TextView days;

    @BindView(R.id.amount)
    TextView amount;
//
//    @OnClick(R.id.duration)
//    void goDuration(){
//        startActivity(new Intent(getApplicationContext(), AddDurationActivity.class));
//        this.finish();
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
//    }


}
