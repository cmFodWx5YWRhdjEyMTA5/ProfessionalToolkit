package health.app.AddPackage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.R;
import health.app.Utilities.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddNoOfDayActivity extends BaseActivity {
String selectedNoDays,hour,hour1,minute;
    public static Activity fa;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_no_of_day);
        ButterKnife.bind(this);
        fa=this;
        if (AddDurationActivity.trainerAvailabilityModel.getSelectDays()!=null){
            tvSelectDays.setText(AddDurationActivity.trainerAvailabilityModel.getSelectDays()+" Days");
            selectedNoDays=AddDurationActivity.trainerAvailabilityModel.getSelectDays();
            AddDurationActivity.trainerAvailabilityModel.setSelectDays(selectedNoDays);

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
        setLength(AddDurationActivity.trainerAvailabilityModel.getSelectDuration());
        String[] splited = AddDurationActivity.trainerAvailabilityModel.getSelectNumber().split("\\s+");
        Log.d("splited","splited"+splited[0]);
        slot.setText("Session\n"+splited[0]);
        days.setText("Days");
        amount.setText("Amount");
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

    public void showAmount() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.selectTime);
        dialogBuilder.setTitle("Add number of days in a package");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setIcon(R.drawable.ptlogo);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (edt.getText().toString().trim().length()!=0) {
                    selectedNoDays=edt.getText().toString();
                    AddDurationActivity.trainerAvailabilityModel.setSelectDays(selectedNoDays);
                    tvSelectDays.setText(edt.getText().toString()+" Days");
                    tvSelectDays.setTextColor(Color.BLACK);
                    dialog.dismiss();
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

    @OnClick(R.id.iv_select_days)
    void selectDays(){
        showAmount();
    }

    @BindView(R.id.duration)
    TextView duration;

    @BindView(R.id.slot)
    TextView slot;

    @BindView(R.id.tv_select_days)
    TextView tvSelectDays;

    @BindView(R.id.container)
    RelativeLayout container;

    @BindView(R.id.days)
    TextView days;

    @BindView(R.id.amount)
    TextView amount;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


//    @OnClick(R.id.slot)
//    void goSlot(){
//        startActivity(new Intent(getApplicationContext(), AddSlotsActivity.class));
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
//        finish();
//    }

//    @OnClick(R.id.duration)
//    void goDuration(){
//        startActivity(new Intent(getApplicationContext(), AddDurationActivity.class));
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
//        finish();
//    }


    @OnClick(R.id.bt_next)
    void next(){
        if (tvSelectDays.getText().equals("Add Number Of Days In A Package")) {
            Snackbar snackbar = Snackbar.make(container, "Please select number of days in a package", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        }
        else {
            if (selectedNoDays!=null) {
                AddDurationActivity.trainerAvailabilityModel.setSelectDays(selectedNoDays);
                startActivity(new Intent(getApplicationContext(), AddAmountActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }

        }

    }
}
