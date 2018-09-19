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

public class AddAmountActivity extends BaseActivity {
String selectedAmount,hour,minute,hour1;
    public static Activity fa;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amount);
        ButterKnife.bind(this);
        fa=this;
        if (AddDurationActivity.trainerAvailabilityModel.getSelectAmount()!=null){
            tvSelectAmount.setText("$"+AddDurationActivity.trainerAvailabilityModel.getSelectAmount());
            selectedAmount= AddDurationActivity.trainerAvailabilityModel.getSelectAmount();
            AddDurationActivity.trainerAvailabilityModel.setSelectAmount(selectedAmount);
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
        days.setText("Days\n"+AddDurationActivity.trainerAvailabilityModel.getSelectDays());
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
        dialogBuilder.setTitle("Add Package Amount");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setIcon(R.drawable.ptlogo);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (edt.getText().toString().trim().length() != 0) {
                    selectedAmount=edt.getText().toString();
                    AddDurationActivity.trainerAvailabilityModel.setSelectAmount(selectedAmount);
                    tvSelectAmount.setText("$"+edt.getText().toString());
                    tvSelectAmount.setTextColor(Color.BLACK);
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

    @OnClick(R.id.iv_select_amount)
    void ivSelectAmount() {
        showAmount();
    }

    @BindView(R.id.tv_select_amount)
    TextView tvSelectAmount;


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

    @OnClick(R.id.duration)
    void goDuration(){
        startActivity(new Intent(getApplicationContext(), AddDurationActivity.class));
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    @OnClick(R.id.bt_next)
    void next(){
        if (tvSelectAmount.getText().equals("Add Package Amount")) {
            Snackbar snackbar = Snackbar.make(container, "Please select package amount", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        }
        else {
            if (selectedAmount!=null) {
                AddDurationActivity.trainerAvailabilityModel.setSelectAmount(selectedAmount);
                startActivity(new Intent(getApplicationContext(), AddDaysActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }

        }

    }


}
