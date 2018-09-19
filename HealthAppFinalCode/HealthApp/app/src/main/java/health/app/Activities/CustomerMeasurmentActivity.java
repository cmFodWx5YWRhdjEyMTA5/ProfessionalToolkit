package health.app.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import health.app.Adapter.MeasurementAdapter;
import health.app.Model.AllMeasurement;
import health.app.Model.MyDateList;
import health.app.R;
import health.app.Response.MeasurementResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerMeasurmentActivity extends BaseActivity implements OnChartGestureListener,OnChartValueSelectedListener
         {

    private LineChart mChart;
             List<MeasurementResponse.MeasureData> measurementList=new ArrayList<>();
            public static List<AllMeasurement> allmeasurementList=new ArrayList<>();
    List<MyDateList> myDateListList=new ArrayList<MyDateList>();
             MeasurementAdapter measurementAdapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



             @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To make full screen layout
        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Measurement Report");
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

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerviewMeasurement.setLayoutManager(mLayoutManager);
        recyclerviewMeasurement.setItemAnimator(new DefaultItemAnimator());
        mChart = (LineChart) findViewById(R.id.linechart);

        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // add data
//        setData(response.body().getMeasureDataList());

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        mChart.setDescription("Health App");
        mChart.setNoDataTextDescription("No Measurement Report Found");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
//         mChart.setScaleXEnabled(true);
//         mChart.setScaleYEnabled(true);

//        LimitLine upper_limit = new LimitLine(60, "Upper Limit");
//        upper_limit.setLineWidth(4f);
//        upper_limit.enableDashedLine(10f, 10f, 0f);
//        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        upper_limit.setTextSize(10f);

//        LimitLine lower_limit = new LimitLine(0, "Lower Limit");
//        lower_limit.setLineWidth(4f);
//        lower_limit.enableDashedLine(10f, 10f, 0f);
//        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        lower_limit.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(upper_limit);
//        leftAxis.addLimitLine(lower_limit);
        //leftAxis.setAxisMaxValue(60);
        leftAxis.setAxisMinValue(0);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mChart.invalidate();
        setFields();
        getAllMeasurements();

    }

    public void setFields() {
        cusEmail.setText(Preferences.getInstance().getEmail());
        cusName.setText(Preferences.getInstance().getFirstName()+" "+Preferences.getInstance().getLastName());
        Picasso.with(getApplicationContext()).load(Preferences.getInstance().getUserPhotoPath()).into(cusImage);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllMeasurements() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getAllMeasurement(Preferences.getInstance().getUserId()).enqueue(new Callback<MeasurementResponse>() {
            @Override
            public void onResponse(Call<MeasurementResponse> call, Response<MeasurementResponse> response) {
                if (response.body() != null && response.body().isStatus()){
                    measurementList=response.body().getMeasureDataList();
                    measurementAdapter = new MeasurementAdapter(getApplicationContext(), response.body().getMeasureDataList());
                    recyclerviewMeasurement.setAdapter(measurementAdapter);
                    measurementAdapter.notifyDataSetChanged();
                    Collections.reverse(response.body().getMeasureDataList());
                    ArrayList<MeasurementResponse.MeasureData> getlist=removeDuplicates(new ArrayList<MeasurementResponse.MeasureData>(response.body().getMeasureDataList()));
                    Collections.reverse(getlist);
                    setData(getlist);
                    }

                else {
                    noRequest.setVisibility(View.VISIBLE);
                    header.setVisibility(View.GONE);
                    //Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
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

    public ArrayList<MeasurementResponse.MeasureData>  removeDuplicates(ArrayList<MeasurementResponse.MeasureData> list){
        Set set = new TreeSet(new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                if(((MeasurementResponse.MeasureData)o1).getDate().equalsIgnoreCase(((MeasurementResponse.MeasureData)o2).getDate())){
                    return 0;
                }
                return 1;
            }
        });
        set.addAll(list);

        final ArrayList newList = new ArrayList(set);
        return newList;
    }


    private void setData(List<MeasurementResponse.MeasureData> measureDataList) {
        ArrayList<String> xVals = setXAxisValues(measureDataList);

        ArrayList<Entry> yVals = setYAxisValues(measureDataList);

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "BMI");
        set1.setCircleSize(9f);
        set1.setFillAlpha(110);
        set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.parseColor("#98DAF9"));
        set1.setCircleColor(Color.parseColor("#98DAF9"));
        set1.setLineWidth(3f);
        set1.setCircleRadius(5f);
        set1.setDrawCircleHole(true);
        set1.setValueTextSize(11f);
        set1.setDrawFilled(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);

    }

    private ArrayList<String> setXAxisValues(List<MeasurementResponse.MeasureData> measureDataList){
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i=0;i<measureDataList.size();i++) {
//            xVals.add("22 oct");
//            xVals.add("23 oct");
//            xVals.add("24 oct");
//            xVals.add("25 oct");
//            xVals.add("26 oct");
            String[] splited = measureDataList.get(i).getDate().split("\\s+");
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).parse(splited[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // set date string
            String stringDate = new SimpleDateFormat("MMM dd", Locale.US).format(date).toUpperCase(Locale.ROOT);
            xVals.add(stringDate);
        }
        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(List<MeasurementResponse.MeasureData> measureDataList){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i=0;i<measureDataList.size();i++) {
//            yVals.add(new Entry(60, 0));
//            yVals.add(new Entry(48, 1));
//            yVals.add(new Entry(22, 2));
//            yVals.add(new Entry(11, 3));
//            yVals.add(new Entry(21, 4));
            yVals.add(new Entry(Float.valueOf(measureDataList.get(i).getBMI()), i));
        }
        return yVals;
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cus_image)
    CircleImageView cusImage;

    @BindView(R.id.cus_name)
    TextView cusName;

    @BindView(R.id.cus_email)
    TextView cusEmail;

    @BindView(R.id.no_request)
    RelativeLayout noRequest;

    @BindView(R.id.header)
    LinearLayout header;

    @BindView(R.id.recyclerview_measurement)
    RecyclerView recyclerviewMeasurement;


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d("Value","Value"+"START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

             @Override
             public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                 Log.d("Entry selected","Entry selected"+e.getVal()+e.getData());
                 findDetails(e.getVal());
                 mChart.centerViewToAnimated(e.getXIndex(), e.getVal(), mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                         .getAxisDependency(), 500);
             }

             private void findDetails(float val) {
                 for (int i=0;i<measurementList.size();i++){
                     if (measurementList.get(i).getBMI().contains(String.valueOf(val)))
                     {
                         AllMeasurement allMeasurement=new AllMeasurement();
                         allMeasurement.setHeight(measurementList.get(i).getHeight());
                         allMeasurement.setWeight(measurementList.get(i).getWeight());
                         allMeasurement.setArm(measurementList.get(i).getArm());
                         allMeasurement.setBMI(measurementList.get(i).getBMI());
                         allMeasurement.setCalf(measurementList.get(i).getCalf());
                         allMeasurement.setChest(measurementList.get(i).getChest());
                         allMeasurement.setHips(measurementList.get(i).getHips());
                         allMeasurement.setNeck(measurementList.get(i).getNeck());
                         allMeasurement.setThigh(measurementList.get(i).getThigh());
                         allMeasurement.setWaist(measurementList.get(i).getWaist());
                         allMeasurement.setDate(measurementList.get(i).getDate());
                         allmeasurementList.add(allMeasurement);
                         startActivity(new Intent(getApplicationContext(),MeasurementDetailsActivity.class));
                     }
                 }
             }

             @Override
             public void onNothingSelected() {


             }
         }
