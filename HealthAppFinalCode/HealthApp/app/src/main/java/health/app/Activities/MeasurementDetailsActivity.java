package health.app.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.R;
import health.app.Response.MeasurementResponse;
import health.app.Utilities.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MeasurementDetailsActivity extends BaseActivity {
    public static final String MEASURE = "client";
    String dayOfWeek,dayOfWeek1;
    Typeface type1;
    MeasurementResponse.MeasureData measureData;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_details);
        ButterKnife.bind(this);
        type1 = Typeface.createFromAsset(getAssets(),"fonts/Poppins-Regular.ttf");
        measureData = (MeasurementResponse.MeasureData) getIntent().getParcelableExtra(MEASURE);

        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);

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
        if (measureData!=null) {
            String[] splited = measureData.getDate().split("\\s+");
            SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat writeFormat = new SimpleDateFormat("MMM dd,yyyy");

            Date date = null;
            try {
                date = readFormat.parse(splited[0]);

                dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(date);

                Log.d("dayOfWeek","dayOfWeek"+dayOfWeek); // Friday
            } catch (ParseException e) {
                e.printStackTrace();
            }
            title.setText(writeFormat.format(date)+" ("+dayOfWeek+") ");
            setValues(measureData);
        }
        else {
            for (int i=0;i<CustomerMeasurmentActivity.allmeasurementList.size();i++) {
                tvWeight.setText(CustomerMeasurmentActivity.allmeasurementList.get(i).getHeight()+" kg");
                tvHeight.setText( CustomerMeasurmentActivity.allmeasurementList.get(i).getWeight()+" cm");
                tvWaist.setText(CustomerMeasurmentActivity.allmeasurementList.get(i).getWaist()+" cm");
                tvCalf.setText(CustomerMeasurmentActivity.allmeasurementList.get(i).getCalf()+" cm");
                tvThigh.setText(CustomerMeasurmentActivity.allmeasurementList.get(i).getThigh()+" cm");
                tvArm.setText(CustomerMeasurmentActivity.allmeasurementList.get(i).getArm()+" cm");
//                tvNeck.setText(CustomerMeasurmentActivity.allmeasurementList.get(i).getNeck()+" cm");
                tvChest.setText(CustomerMeasurmentActivity.allmeasurementList.get(i).getChest()+" cm");
                tvHip.setText(CustomerMeasurmentActivity.allmeasurementList.get(i).getHips()+" cm");
                tvBMI.setText(CustomerMeasurmentActivity.allmeasurementList.get(i).getBMI());
                String[] splited1 = CustomerMeasurmentActivity.allmeasurementList.get(i).getDate().split("\\s+");
                SimpleDateFormat readFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat writeFormat1 = new SimpleDateFormat("MMM dd,yyyy");

                Date date1 = null;
                try {
                    date1 = readFormat1.parse(splited1[0]);

                    dayOfWeek1 = new SimpleDateFormat("EEE", Locale.ENGLISH).format(date1);

                    Log.d("dayOfWeek","dayOfWeek"+dayOfWeek1); // Friday
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                title.setText(writeFormat1.format(date1)+" ("+dayOfWeek1+") ");

            }
        }
    }

    private void setValues(MeasurementResponse.MeasureData measureData) {
        t1.setTypeface(type1);
        t2.setTypeface(type1);
        t3.setTypeface(type1);
        t4.setTypeface(type1);
        t5.setTypeface(type1);
        t6.setTypeface(type1);
        t8.setTypeface(type1);
        t9.setTypeface(type1);
        t10.setTypeface(type1);
        tvWeight.setText(measureData.getWeight()+" kg");
        tvHeight.setText(measureData.getHeight()+" cm");
        tvWaist.setText(measureData.getWaist()+" cm");
        tvCalf.setText(measureData.getCalf()+" cm");
        tvThigh.setText(measureData.getThigh()+" cm");
        tvArm.setText(measureData.getArm()+" cm");
        //tvNeck.setText(measureData.getNeck()+" cm");
        tvChest.setText(measureData.getChest()+" cm");
        tvHip.setText(measureData.getHips()+" cm");
        tvBMI.setText(measureData.getBMI());
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvWeight)
    TextView tvWeight;

    @BindView(R.id.tvHeight)
    TextView tvHeight;

    @BindView(R.id.tvWaist)
    TextView tvWaist;

    @BindView(R.id.tvCalf)
    TextView tvCalf;

    @BindView(R.id.tvThigh)
    TextView tvThigh;

    @BindView(R.id.tvArm)
    TextView tvArm;

    @BindView(R.id.tvChest)
    TextView tvChest;

    @BindView(R.id.tvHip)
    TextView tvHip;

    @BindView(R.id.tvBMI)
    TextView tvBMI;

    @BindView(R.id.t1)
    TextView t1;

    @BindView(R.id.t2)
    TextView t2;

    @BindView(R.id.t3)
    TextView t3;

    @BindView(R.id.t4)
    TextView t4;

    @BindView(R.id.t5)
    TextView t5;

    @BindView(R.id.t6)
    TextView t6;

    @BindView(R.id.t8)
    TextView t8;

    @BindView(R.id.t9)
    TextView t9;

    @BindView(R.id.t10)
    TextView t10;


}
