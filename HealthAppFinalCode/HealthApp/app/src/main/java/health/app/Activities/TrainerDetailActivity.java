package health.app.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Adapter.CustomerSlotsAdapter;
import health.app.Model.SlotList;
import health.app.Model.TrainerSlotList;
import health.app.R;
import health.app.Response.TimeSlotByDateResponse;
import health.app.Response.TrainerListResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TrainerDetailActivity extends BaseActivity {
    public TrainerListResponse.TrainerData favoriteList;
    public static final String FAVORITE = "favorite";
    int cyear;
    int k = 0;
    List<SlotList> slotListList=new ArrayList<>();
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    List<TrainerSlotList> trainerSlotLists=new ArrayList<>();
    CustomerSlotsAdapter customerSlotsAdapter;
    SlotList slotList;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        slotList=new SlotList();
        favoriteList = (TrainerListResponse.TrainerData)getIntent().getParcelableExtra(FAVORITE);
        if(favoriteList.getTrainer().getProfile_image()!=null)
        {
            Picasso.with(imageTrainer.getContext()).load(favoriteList.getTrainer().getProfile_image()).resize(300,240).into(imageTrainer);
        }
        for (int i=0;i<favoriteList.getTimeSlotsList().size();i++){
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            Date d = null;

            Log.d("slotList","slotList"+favoriteList.getTimeSlotsList().get(i).getSessionDate());
            try {
                d = f.parse(favoriteList.getTimeSlotsList().get(i).getSessionDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long milliseconds = d.getTime();

            compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
            compactCalendarView.setUseThreeLetterAbbreviation(true);

            Event ev1 = new Event(Color.parseColor("#FF7010"), milliseconds);
            compactCalendarView.addEvent(ev1);
        }
        fullName.setText(favoriteList.getTrainer().getFirst_name()+" "+favoriteList.getTrainer().getLast_name());
        if (favoriteList.getTrainer().getTraining_type().equals("")){
            trainingType.setText("N/A");
        }
        else {
            trainingType.setText(favoriteList.getTrainer().getTraining_type());
        }

        emailAdd.setText(favoriteList.getTrainer().getEmail());
        phoneNo.setText(favoriteList.getTrainer().getPhone());
        if (favoriteList.getTrainer().getShort_bio().equals("")) {
            bio.setText("N/A");
        }
        else { bio.setText(favoriteList.getTrainer().getShort_bio());}
        if (favoriteList.getTrainer().getAge().equals("")) {
            age.setText("N/A");
        }
        else {
            age.setText(favoriteList.getTrainer().getAge());
        }
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Details");
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

    public void addToList() {
        for (int i=0;i<favoriteList.getTimeSlotsList().size();i++) {
            slotList.setdSlotId(favoriteList.getTimeSlotsList().get(i).getPackageId());
            slotList.setdTrainerId(favoriteList.getTimeSlotsList().get(i).getCustomerId());
            slotList.setdCreatedOn(favoriteList.getTimeSlotsList().get(i).getSessionDate());
            slotList.setdCustomerId(favoriteList.getTimeSlotsList().get(i).getCustomerId());
            slotList.setdEndTime(favoriteList.getTimeSlotsList().get(i).getEndTime());
            slotList.setdIsDeleted(favoriteList.getTimeSlotsList().get(i).getIsDeleted());
            slotList.setdIsOccupied(favoriteList.getTimeSlotsList().get(i).getIsDeleted());
            slotList.setdRequestId(favoriteList.getTimeSlotsList().get(i).getRequestId());
            slotList.setdSlotDate(favoriteList.getTimeSlotsList().get(i).getSessionDate());
            slotList.setdSlotDescription(favoriteList.getTimeSlotsList().get(i).getStartTime());
            slotList.setdSlotTitle(favoriteList.getTimeSlotsList().get(i).getEndTime());
            slotList.setdStartTime(favoriteList.getTimeSlotsList().get(i).getStartTime());
            slotList.setdIsDeleted(favoriteList.getTimeSlotsList().get(i).getIsDeleted());
            slotListList.add(slotList);
        }

        for (k = 0; k < slotListList.size(); k++) {

            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            Date d = null;

                Log.d("slotList","slotList"+slotListList.get(k).getdSlotDate().toString());
            try {
                d = f.parse(slotListList.get(k).getdSlotDate().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long milliseconds = d.getTime();

            compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
            compactCalendarView.setUseThreeLetterAbbreviation(true);

            Event ev1 = new Event(Color.parseColor("#FF7010"), milliseconds);
            compactCalendarView.addEvent(ev1);

            compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onDayClick(Date dateClicked) {
                    timeSlotList(dateClicked);
                }

                @Override
                public void onMonthScroll(Date firstDayOfNewMonth) {
                    title1.setText(dateFormatForMonth.format(firstDayOfNewMonth));
                }
            });
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void timeSlotList(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(date);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        calenderRecyclerview.setLayoutManager(mLayoutManager);
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.timeSlotByDateCustomer(favoriteList.getTrainer().getId(),format, Preferences.getInstance().getUserId()).enqueue(new Callback<TimeSlotByDateResponse>() {
            @Override
            public void onResponse(Call<TimeSlotByDateResponse> call, Response<TimeSlotByDateResponse> response) {
                if (response.body() != null) {
                    customerSlotsAdapter = new CustomerSlotsAdapter(TrainerDetailActivity.this, response.body().getDataResponse().getTimeSlotList(),favoriteList.getTrainer().getId());
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
    ImageView imageTrainer;

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
