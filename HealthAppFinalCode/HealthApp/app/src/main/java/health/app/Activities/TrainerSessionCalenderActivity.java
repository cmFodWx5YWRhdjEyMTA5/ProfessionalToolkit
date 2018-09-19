package health.app.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Adapter.CancelListAdapter;
import health.app.Model.SendList;
import health.app.Model.SlotList;
import health.app.Model.StartEndTime;
import health.app.R;
import health.app.Response.MySessionResponse;
import health.app.Response.TimeSlotByDateResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TrainerSessionCalenderActivity extends BaseActivity {
    public static final String CANCEL = "cancel";
    ArrayList<String> yearList = new ArrayList<String>();
    public MySessionResponse.SessionData.PackageList packageData = null;
    List<StartEndTime> startEndTimeList=new ArrayList<>();
    int month, month1, year, cyear;
    int k = 0;
    String format;
    List<Calendar> weekends;
    String sessionTime,hours,minutes,hours1;
    SlotList slotList;
    ArrayAdapter arrayAdapter1, arrayAdapter2;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    String[] monthList = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_session_calender);
        ButterKnife.bind(this);
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        packageLabel.setTypeface(font);
        tvYear.setTypeface(font);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("My Sessions");
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
        if ((packageData =(MySessionResponse.SessionData.PackageList) getIntent().getParcelableExtra(CANCEL)) != null) {
            Log.d("packageData1", "packageData" + packageData.getNoOfDays());
            Log.d("packageData", "packageData" + packageData.getSessionDays());
            sessionPerDay.setText("Number Of Session In A Day : " + packageData.getNoOfSlots());
            sessionPrice.setText("Package Price : " + "$" + packageData.getSessionPrice());
            setLength(packageData.getSessionTime());
            sessionTime = packageData.getSessionTime();

        }
        if (packageData.getSessionDays().contains(",")) {
            List<String> result = Arrays.asList(packageData.getSessionDays().split("\\s*,\\s*"));
            Log.d("list","list"+result);
            for (int i=0;i<result.size();i++){
                getDates(Integer.parseInt(packageData.getNoOfDays()),result.get(i));
            }
        } else {
               getDates(Integer.parseInt(packageData.getNoOfDays()),packageData.getSessionDays());
        }
        slotList = new SlotList();
        showDataYear();
        showDataMonth();
        getDate();
        final int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            yearList.add(Integer.toString(i));
        }
        title1.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDayClick(Date dateClicked) {
                startEndTimeList.clear();
                Calendar myCal = Calendar.getInstance();
                myCal.setTime(dateClicked);
                tvYear.setText(String.valueOf(myCal.get(Calendar.YEAR)));
                tvMonthDay.setText(String.valueOf(myCal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)) + ", " + myCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + String.valueOf(myCal.get(Calendar.DAY_OF_MONTH)));
                Date currentTime = Calendar.getInstance().getTime();
                boolean getResult = health.app.Utilities.DateUtils.isBeforeDay(dateClicked, currentTime);
                if (getResult) {

                } else {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    format = formatter.format(dateClicked);
                    Log.d("format","format"+format);
                    for(int i=0;i<packageData.getOccupiedList().size();i++){
                        if (packageData.getOccupiedList().get(i).getSessionDate().equals(format))
                        {
                            calenderRecyclerview.setVisibility(View.VISIBLE);
                            StartEndTime startEndTime=new StartEndTime();
                            startEndTime.setCustomerId(packageData.getOccupiedList().get(i).getCustomerId());
                            startEndTime.setPackageId(packageData.getOccupiedList().get(i).getPackageId());
                            startEndTime.setRequestId(packageData.getOccupiedList().get(i).getRequestId());
                            startEndTime.setSessionDate(packageData.getOccupiedList().get(i).getSessionDate());
                            startEndTime.setIsDeleted(packageData.getOccupiedList().get(i).getIsDeleted());
                            startEndTime.setSessionSlotId(packageData.getOccupiedList().get(i).getSessionSlotId());
                            startEndTime.setStatus(packageData.getOccupiedList().get(i).getStatus());
                            startEndTime.setStartTime(packageData.getOccupiedList().get(i).getStartTime());
                            startEndTime.setEndTime(packageData.getOccupiedList().get(i).getEndTime());
                            startEndTimeList.add(startEndTime);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            calenderRecyclerview.setLayoutManager(mLayoutManager);
                            CancelListAdapter cancelListAdapter=new CancelListAdapter(TrainerSessionCalenderActivity.this,startEndTimeList,packageData.getTrainerId(),packageData.getCustomerId());
                            calenderRecyclerview.setAdapter(cancelListAdapter);
                            cancelListAdapter.notifyDataSetChanged();
                        }
                        else {
                            calenderRecyclerview.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                title1.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                setMonthSpinner(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                setYearSpinner(item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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
                sessionDuration.setText("Session Length:- "+minutes + " mins");

            }
            else {
                if (minutes.equalsIgnoreCase("00")) {
                    sessionDuration.setText("Session Length:- "+hours1 + " hr");

                }
                else {
                    sessionDuration.setText("Session Length:- "+hours1 + " hr " + minutes + " mins");

                }
            }
        }
        else {
            if (minutes.equalsIgnoreCase("00")) {
                Log.d("startsWith1","startsWith1"+"startsWith1");
                sessionDuration.setText("Session Length:- "+hours + " hr");
            }
            else {
                sessionDuration.setText("Session Length:- "+hours + " hr " + minutes + " mins");
            }
        }
    }

//    private void getDates(int week,String day) {
//        Calendar monday;
//        Calendar tuesday;
//        Calendar wednesday;
//        Calendar thursday;
//        Calendar friday;
//        Calendar saturday;
//        Calendar sunday;
//        weekends = new ArrayList<>();
//        for (int i = 0; i < (week * 7) ; i = i + 7) {
//            monday = Calendar.getInstance();
//            tuesday = Calendar.getInstance();
//            wednesday = Calendar.getInstance();
//            thursday = Calendar.getInstance();
//            friday = Calendar.getInstance();
//            saturday = Calendar.getInstance();
//            sunday = Calendar.getInstance();
//            if (day.equalsIgnoreCase("Sunday")) {
//                sunday.add(Calendar.DAY_OF_YEAR, (Calendar.SUNDAY - sunday.get(Calendar.DAY_OF_WEEK) + 7 + i));
//                weekends.add(sunday);
//            }
//            else if (day.equalsIgnoreCase("Monday")){
//                //monday.add(Calendar.DAY_OF_YEAR, (Calendar.MONDAY - monday.get(Calendar.DAY_OF_WEEK) + 7 + i));
//                monday.add(Calendar.DAY_OF_YEAR, (Calendar.MONDAY - monday.get(Calendar.DAY_OF_WEEK) + i));
//                weekends.add(monday);
//            }
//            else if (day.equalsIgnoreCase("Tuesday")){
//                // tuesday.add(Calendar.DAY_OF_YEAR, (Calendar.TUESDAY - tuesday.get(Calendar.DAY_OF_WEEK) + 7 + i));
//                tuesday.add(Calendar.DAY_OF_YEAR, (Calendar.TUESDAY - tuesday.get(Calendar.DAY_OF_WEEK) + i));
//                weekends.add(tuesday);
//            }
//            else if (day.equalsIgnoreCase("Wednesday")){
//                //wednesday.add(Calendar.DAY_OF_YEAR, (Calendar.WEDNESDAY - wednesday.get(Calendar.DAY_OF_WEEK) + 7 + i));
//                wednesday.add(Calendar.DAY_OF_YEAR, (Calendar.WEDNESDAY - wednesday.get(Calendar.DAY_OF_WEEK) + i));
//                weekends.add(wednesday);
//            }
//            else if (day.equalsIgnoreCase("Thursday")){
//                //thursday.add(Calendar.DAY_OF_YEAR, (Calendar.THURSDAY - thursday.get(Calendar.DAY_OF_WEEK) + 7 + i));
//                thursday.add(Calendar.DAY_OF_YEAR, (Calendar.THURSDAY - thursday.get(Calendar.DAY_OF_WEEK) + i));
//                weekends.add(thursday);
//            }
//            else if (day.equalsIgnoreCase("Friday")){
//                // friday.add(Calendar.DAY_OF_YEAR, (Calendar.FRIDAY - friday.get(Calendar.DAY_OF_WEEK) + 7 + i));
//                friday.add(Calendar.DAY_OF_YEAR, (Calendar.FRIDAY - friday.get(Calendar.DAY_OF_WEEK) + i));
//                weekends.add(friday);
//            }
//            else if (day.equalsIgnoreCase("Saturday")){
//                saturday.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - saturday.get(Calendar.DAY_OF_WEEK) + i));
//                weekends.add(saturday);
//            }
//        }
//        getCalenderList(weekends);
////        Calendar[] disabledDays = weekends.toArray(new Calendar[weekends.size()]);
//        for (int k=0;k<weekends.size();k++) {
//            Log.d("disabledDays", "disabledDays" + weekends.get(k).getTime());
//        }
//    }

    private void getDates(int week,String day) {
        // Calendar code is from this link https://coderanch.com/t/385117/java/date-Monday
        Calendar calendar;
        weekends = new ArrayList<>();
        for (int i = 0; i < (week * 7) ; i = i + 7) {
            calendar = Calendar.getInstance();
            int weekday = calendar.get(Calendar.DAY_OF_WEEK);
            if (day.equalsIgnoreCase("Sunday")) {
                calendar.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - weekday + 1) % 7 + i);
                weekends.add(calendar);
            }
            else if (day.equalsIgnoreCase("Monday")){
                calendar.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - weekday + 2) % 7 + i);
                weekends.add(calendar);
            }
            else if (day.equalsIgnoreCase("Tuesday")){
                calendar.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - weekday + 3) % 7 + i);
                weekends.add(calendar);
            }
            else if (day.equalsIgnoreCase("Wednesday")){
                calendar.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - weekday + 4) % 7 + i);
                weekends.add(calendar);
            }
            else if (day.equalsIgnoreCase("Thursday")){
                calendar.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - weekday + 5) % 7 + i);
                weekends.add(calendar);
            }
            else if (day.equalsIgnoreCase("Friday")){
                calendar.add(Calendar.DAY_OF_YEAR , (Calendar.SATURDAY - weekday + 6) % 7 + i);
                weekends.add(calendar);
            }
            else if (day.equalsIgnoreCase("Saturday")){
                calendar.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - weekday + 0) % 7 + i);
                weekends.add(calendar);
            }
        }
        getCalenderList(weekends);
    }
    public void getCalenderList(List<Calendar> dayList){
        for (int i=0;i<dayList.size();i++) {
            long milliseconds = dayList.get(i).getTimeInMillis();


            compactCalendarView.setUseThreeLetterAbbreviation(true);
            compactCalendarView.setShouldDrawDaysHeader(true);
            Event ev1 = new Event(Color.parseColor("#FF7010"), milliseconds);
            compactCalendarView.addEvent(ev1);
            Log.d("listofdays", "listofdays" + dayList);
        }
    }



    private void setYearSpinner(String item) {

        Log.d("selectedYear", "selectedYear" + item);
        if (spinnerMonth.getSelectedItem() != null) {
            if (spinnerMonth.getSelectedItem().equals("January")) {
                month1 = 1;
            } else if (spinnerMonth.getSelectedItem().equals("February")) {
                month1 = 2;
            } else if (spinnerMonth.getSelectedItem().equals("March")) {
                month1 = 3;
            } else if (spinnerMonth.getSelectedItem().equals("April")) {
                month1 = 4;
            } else if (spinnerMonth.getSelectedItem().equals("May")) {
                month1 = 5;
            } else if (spinnerMonth.getSelectedItem().equals("June")) {
                month1 = 6;
            } else if (spinnerMonth.getSelectedItem().equals("July")) {
                month1 = 7;
            } else if (spinnerMonth.getSelectedItem().equals("August")) {
                month1 = 8;
            } else if (spinnerMonth.getSelectedItem().equals("September")) {
                month1 = 9;
            } else if (spinnerMonth.getSelectedItem().equals("October")) {
                month1 = 10;
            } else if (spinnerMonth.getSelectedItem().equals("November")) {
                month1 = 11;
            } else if (spinnerMonth.getSelectedItem().equals("December")) {
                month1 = 12;
            }
            title1.setText(String.valueOf(spinnerMonth.getSelectedItem().toString()) + " " + item);
            tvYear.setText(item);
            int day = 1;
            String startDateString2 = String.valueOf(day) + "/" + String.valueOf(month1) + "/" + item;
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate2 = null;
            try {
                startDate2 = df2.parse(startDateString2);
                String newDateString2 = df2.format(startDate2);
                Log.d("newDateString2", "newDateString2" + newDateString2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            compactCalendarView.setCurrentDate(startDate2);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
            Date date = new Date();
            Log.d("Month", dateFormat.format(date));
            month1 = Integer.parseInt(dateFormat.format(date));
            //month1= Integer.parseInt(dateFormat.format(date))-1;
            Log.d("cmonth", "cmonth" + month1);
            Calendar c = Calendar.getInstance();
            Log.d("month1", "month1" + c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
            title1.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + item);
            tvYear.setText(String.valueOf(cyear));
            int day = 1;
            String startDateString2 = String.valueOf(day) + "/" + String.valueOf(month1) + "/" + item;
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate2 = null;
            try {
                startDate2 = df2.parse(startDateString2);
                String newDateString2 = df2.format(startDate2);
                Log.d("newDateString2", "newDateString2" + newDateString2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            compactCalendarView.setCurrentDate(startDate2);
        }
    }

    private void setMonthSpinner(String item) {
        Log.d("selectedMonth", "selectedMonth" + item);
        if (item.equals("January")) {
            month = 1;
        } else if (item.equals("February")) {
            month = 2;
        } else if (item.equals("March")) {
            month = 3;
        } else if (item.equals("April")) {
            month = 4;
        } else if (item.equals("May")) {
            month = 5;
        } else if (item.equals("June")) {
            month = 6;
        } else if (item.equals("July")) {
            month = 7;
        } else if (item.equals("August")) {
            month = 8;
        } else if (item.equals("September")) {
            month = 9;
        } else if (item.equals("October")) {
            month = 10;
        } else if (item.equals("November")) {
            month = 11;
        } else if (item.equals("December")) {
            month = 12;
        }
        int day = 1;
        if (spinnerYear.getSelectedItem() != null) {
            year = Integer.parseInt(spinnerYear.getSelectedItem().toString());
            title1.setText(item + " " + String.valueOf(year));
            tvYear.setText(String.valueOf(year));
            String startDateString1 = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate1 = null;
            try {
                startDate1 = df.parse(startDateString1);
                String newDateString1 = df.format(startDate1);
                Log.d("newDateString1", "newDateString1" + newDateString1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            compactCalendarView.setCurrentDate(startDate1);
        } else {
            title1.setText(item + " " + String.valueOf(cyear));
            tvYear.setText(String.valueOf(cyear));
            String startDateString1 = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(cyear);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate1 = null;
            try {
                startDate1 = df.parse(startDateString1);
                String newDateString1 = df.format(startDate1);
                Log.d("newDateString1", "newDateString1" + newDateString1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            compactCalendarView.setCurrentDate(startDate1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void timeSlotList1() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(c.getTime());
        Log.d("format", "format" + format);
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.timeSlotByDate(Preferences.getInstance().getUserId(), format).enqueue(new Callback<TimeSlotByDateResponse>() {
            @Override
            public void onResponse(Call<TimeSlotByDateResponse> call, Response<TimeSlotByDateResponse> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().getDataResponse().getAllSlotsList().size(); i++) {
                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                        Date d = null;

                        Log.d("mylist", "mylist" + response.body().getDataResponse().getAllSlotsList().get(i).getSlotDate());
                        try {
                            d = f.parse(response.body().getDataResponse().getAllSlotsList().get(i).getSlotDate());
                            Log.d("d", "d" + d);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long milliseconds = d.getTime();


                        compactCalendarView.setUseThreeLetterAbbreviation(true);
                        compactCalendarView.setShouldDrawDaysHeader(true);
                        Event ev1 = new Event(Color.parseColor("#FF7010"), milliseconds);
                        compactCalendarView.addEvent(ev1);
                    }

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

    public void getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayName = simpleDateFormat.format(date);
        String day = (String) DateFormat.format("dd", date);
        cyear = calendar.get(Calendar.YEAR);
        tvYear.setText(String.valueOf(cyear));
        tvMonthDay.setText(dayName + ", " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + day);
        Log.d("monthName", "monthName" + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
        Log.d("dayName", "dayName" + dayName);
        Log.d("day", "day" + day);
    }


    public void showDataYear() {
        arrayAdapter1 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item_calender, yearList);
        spinnerYear.setAdapter(arrayAdapter1);
        spinnerYear.setTitle("Select Year");
    }

    public void showDataMonth() {
        arrayAdapter2 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item_calender, monthList);
        spinnerMonth.setAdapter(arrayAdapter2);
        spinnerMonth.setTitle("Select Month");
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;

    @BindView(R.id.tvYear)
    TextView tvYear;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.tvMonth)
    TextView tvMonthDay;

    @BindView(R.id.sp_year)
    SearchableSpinner spinnerYear;

    @BindView(R.id.package_label)
    TextView packageLabel;

    @BindView(R.id.sp_month)
    SearchableSpinner spinnerMonth;

    @OnClick(R.id.next)
    void next() {
        compactCalendarView.showNextMonth();
    }

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

    @BindView(R.id.calender_recyclerview)
    RecyclerView calenderRecyclerview;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_view_toolbar1)
    TextView title1;

    @BindView(R.id.tv_session_per_day)
    TextView sessionPerDay;

    @BindView(R.id.tv_session_price)
    TextView sessionPrice;

    @BindView(R.id.tv_session_duration)
    TextView sessionDuration;

    @OnClick(R.id.previous)
    void previous() {
        compactCalendarView.showPreviousMonth();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        // timeSlotList1();
    }
}

