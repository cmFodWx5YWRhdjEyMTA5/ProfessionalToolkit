package health.app.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import health.app.Adapter.CustomerSlotsAdapter;
import health.app.Adapter.TrainerIdPackageAdapter;
import health.app.Model.SlotList;
import health.app.Model.TrainerDetails;
import health.app.Model.TrainerSlotList;
import health.app.R;
import health.app.Response.TimeSlotByDateResponse;
import health.app.Response.TrainerByIdResponse;
import health.app.Response.TrainerPackageResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Developer Six on 10/6/2017.
 */

public class TrainerDetailByIdActivity extends BaseActivity {
    int cyear;
    int k = 0;
    String userId;
    String uniqueId;
    TrainerIdPackageAdapter trainerPackageAdapter;
    TrainerByIdResponse.TrainerIdData trainerList;
    TrainerByIdResponse.TrainerIdData trainerList1;
    List<SlotList> slotListList=new ArrayList<>();
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    List<TrainerSlotList> trainerSlotLists=new ArrayList<>();
    CustomerSlotsAdapter customerSlotsAdapter;
    SlotList slotList;
    public static TrainerDetails trainerDetails;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_detail);
        ButterKnife.bind(this);
        trainerDetails=new TrainerDetails();
        uniqueId=getIntent().getStringExtra("uniqueId");
        trainerList=getIntent().getParcelableExtra("listing");
        trainerList1=getIntent().getParcelableExtra("listing1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        if (trainerList!=null) {
            setValues(trainerList);
        }
        else {
            setValues(trainerList1);
        }

        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Trainer Details");
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
        title1.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDayClick(Date dateClicked) {
                Calendar myCal = Calendar.getInstance();
                myCal.setTime(dateClicked);
                tvYear.setText(String.valueOf(myCal.get(Calendar.YEAR)));
                tvMonthDay.setText(String.valueOf(myCal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH))+", "+myCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)+" "+String.valueOf(myCal.get(Calendar.DAY_OF_MONTH)));
                Log.d("Day","Day: " + myCal.get(Calendar.DAY_OF_MONTH));
                Log.d("Month","Month: " + String.valueOf(myCal.get(Calendar.MONTH) + 1));
                Log.d("Year","Year: " + myCal.get(Calendar.YEAR));
                Log.d("MonthName","MonthName: " + myCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
                Log.d("DayName","DayName: " + myCal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH));
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d("Day was clicked:", "Day was clicked: " + dateClicked + " with events " + events);
                timeSlotList(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                title1.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
        getDate();
    }



    private void setValues(TrainerByIdResponse.TrainerIdData trainerIdDataList) {
            userId=trainerIdDataList.getTrainerId().getId();
        trainerDetails.setName(trainerIdDataList.getTrainerId().getFirst_name() + " " + trainerIdDataList.getTrainerId().getLast_name());
        trainerDetails.setImage(trainerIdDataList.getBase_url()+trainerIdDataList.getTrainerId().getProfile_image());
            fullName.setText(trainerIdDataList.getTrainerId().getFirst_name() + " " + trainerIdDataList.getTrainerId().getLast_name());
            if (trainerIdDataList.getTrainerId().getTraining_type().equals("")) {
                trainingType.setText("N/A");
            } else {
                trainingType.setText(trainerIdDataList.getTrainerId().getTraining_type());
            }
            if (trainerIdDataList.getTrainerId().getProfile_image() != null) {
                Picasso.with(imageTrainer.getContext()).load(trainerIdDataList.getBase_url()+trainerIdDataList.getTrainerId().getProfile_image()).resize(300, 240).into(imageTrainer);
            }

            emailAdd.setText(trainerIdDataList.getTrainerId().getEmail());
            phoneNo.setText(trainerIdDataList.getTrainerId().getPhone());
            if (trainerIdDataList.getTrainerId().getShort_bio().equals("")) {
                bio.setText("N/A");
            } else {
                bio.setText(trainerIdDataList.getTrainerId().getShort_bio());
            }
            if (trainerIdDataList.getTrainerId().getAge().equals("")) {
                age.setText("N/A");
            } else {
                age.setText(trainerIdDataList.getTrainerId().getAge()+" Years");
            }
            setPackageList(trainerIdDataList.getTrainerPackageList());
    }

    private void setPackageList(List<TrainerPackageResponse.PackageData> trainerPackageList) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        packageRecyclerView.setLayoutManager(mLayoutManager);
        packageRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        packageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        trainerPackageAdapter = new TrainerIdPackageAdapter(getApplicationContext(), trainerPackageList);
        packageRecyclerView.setAdapter(trainerPackageAdapter);
        trainerPackageAdapter.notifyDataSetChanged();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void timeSlotList(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(date);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        calenderRecyclerview.setLayoutManager(mLayoutManager);
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.timeSlotByDateCustomer(userId,format, Preferences.getInstance().getUserId()).enqueue(new Callback<TimeSlotByDateResponse>() {
            @Override
            public void onResponse(Call<TimeSlotByDateResponse> call, Response<TimeSlotByDateResponse> response) {
                if (response.body() != null) {
                    customerSlotsAdapter = new CustomerSlotsAdapter(TrainerDetailByIdActivity.this, response.body().getDataResponse().getTimeSlotList(),userId);
                    calenderRecyclerview.setItemAnimator(new DefaultItemAnimator());
                    calenderRecyclerview.setAdapter(customerSlotsAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();

            }

            @Override
            public void onFailure(Call<TimeSlotByDateResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDate(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayName = simpleDateFormat.format(date);
        String day= (String) DateFormat.format("dd",   date);
        cyear = calendar.get(Calendar.YEAR);
        tvYear.setText(String.valueOf(cyear));
        tvMonthDay.setText(dayName+", "+calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH )+" "+day);
        Log.d("monthName","monthName" +calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ) );
        Log.d("dayName","dayName" +dayName);
        Log.d("day","day" +day);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_customer)
    CircleImageView imageTrainer;

    @BindView(R.id.fname)
    TextView fullName;

    @BindView(R.id.training_type)
    TextView trainingType;

    @BindView(R.id.email_add)
    TextView emailAdd;

    @BindView(R.id.phone_no)
    TextView phoneNo;

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;

    @BindView(R.id.text_view_toolbar1)
    TextView title1;

    @BindView(R.id.bio)
    TextView bio;

    @BindView(R.id.age)
    TextView age;

    @BindView(R.id.package_recycler_view)
    RecyclerView packageRecyclerView;

    @OnClick(R.id.next)
    void next(){
        compactCalendarView.showNextMonth();
    }

    @BindView(R.id.calender_recyclerview)
    RecyclerView calenderRecyclerview;

    @OnClick(R.id.previous)
    void previous(){
        compactCalendarView.showPreviousMonth();
    }

    @BindView(R.id.tvYear)
    TextView tvYear;

    @BindView(R.id.tvMonth)
    TextView tvMonthDay;


}