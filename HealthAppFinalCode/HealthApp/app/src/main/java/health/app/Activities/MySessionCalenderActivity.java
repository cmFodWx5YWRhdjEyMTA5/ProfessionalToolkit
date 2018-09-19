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

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.CancelListAdapter;
import health.app.CalenderCount.CalendarView;
import health.app.Model.SlotList;
import health.app.Model.StartEndTime;
import health.app.R;
import health.app.Response.SessionSlotResponse;
import health.app.Response.TrainerDashboardListResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MySessionCalenderActivity extends BaseActivity implements CalendarView.OnDateSelectedListener,CalendarView.OnDateChangeListener{
    public static final String SESSION = "session";
    ArrayList<String> yearList = new ArrayList<String>();
    public TrainerDashboardListResponse.ApprovedData.Packages packageData = null;
    List<StartEndTime> startEndTimeList=new ArrayList<>();
    int month, month1, year, cyear;
    int k = 0;
    String hours,hours1,minutes;
    private int mYear;
    static List<health.app.CalenderCount.Calendar> schemes;
    List<String> list;
    static CalendarView calendarView;
    String format;
    List<Calendar> weekends;
    String sessionTime;
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
        setContentView(R.layout.activity_my_session_calender);
        ButterKnife.bind(this);
        schemes = new ArrayList<>();
        list=new ArrayList<String>();
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
        calendarView.setOnDateSelectedListener(this);
        setDate(calendarView.getCurMonth(),calendarView.getCurYear());
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        packageLabel.setTypeface(font);
        mTextMonthDay.setTypeface(font);
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
        if ((packageData =(TrainerDashboardListResponse.ApprovedData.Packages) getIntent().getParcelableExtra(SESSION)) != null) {
            Log.d("packageData1", "packageData" + packageData.getNoOfDays());
            Log.d("packageData", "packageData" + packageData.getSessionDays());
            sessionPerDay.setText("Number Of Session In A Day : " + packageData.getNoOfSlots());
            sessionPrice.setText("Package Price : " + "$" + packageData.getSessionPrice());
            setLength(packageData.getSessionTime());
            sessionTime = packageData.getSessionTime();
            setCalenderDate(packageData.getSelectSlotsList());
        }
        slotList = new SlotList();
        showDataYear();
        showDataMonth();
        getDate();
        final int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear+1; i++) {
            yearList.add(Integer.toString(i));
        }

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Calendar calendar = Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                if (item.equals("January")) {
                    month1 = 1;
                } else if (item.equals("February")) {
                    month1 = 2;
                } else if (item.equals("March")) {
                    month1 = 3;
                } else if (item.equals("April")) {
                    month1 = 4;
                } else if (item.equals("May")) {
                    month1 = 5;
                } else if (item.equals("June")) {
                    month1 = 6;
                } else if (item.equals("July")) {
                    month1 = 7;
                } else if (item.equals("August")) {
                    month1 = 8;
                } else if (item.equals("September")) {
                    month1 = 9;
                } else if (item.equals("October")) {
                    month1 = 10;
                } else if (item.equals("November")) {
                    month1 = 11;
                } else if (item.equals("December")) {
                    month1 = 12;
                }
                health.app.CalenderCount.Calendar calendar1=getSchemeCalendar1(year, month1,1);
                setMonthSpinner(item,calendar1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Calendar calendar = Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                health.app.CalenderCount.Calendar calendar1=getSchemeCalendar1(year, month,1);
                setMonthSpinner(String.valueOf(month),calendar1);
            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Calendar calendar = Calendar.getInstance();
                int month=calendar.get(Calendar.MONTH);
                health.app.CalenderCount.Calendar calendar1=getSchemeCalendar1(Integer.parseInt(item),month,1);
                setYearSpinner(item,calendar1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Calendar calendar = Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                health.app.CalenderCount.Calendar calendar1=getSchemeCalendar1(year, month,1);
                setYearSpinner(String.valueOf(year),calendar1);
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

    private void setCalenderDate(List<TrainerDashboardListResponse.ApprovedData.Packages.SelectSlots> customersList) {
        for (int i=0;i<customersList.size();i++) {
            list.add(customersList.get(i).getSessionDate());
            Log.d("which","which"+list);
        }
        getCount(list);
    }

    private void getCount(List<String> sessionDate) {
        Map<String, Integer> map = new HashMap<String, Integer>();

        for (String temp : sessionDate) {
            Integer count = map.get(temp);
            map.put(temp, (count == null) ? 1 : count + 1);
        }
        printMap(map);
    }

    public static void printMap(Map<String, Integer> map){

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String[] splited =entry.getKey().split("-");
            schemes.add(getSchemeCalendar(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]), Integer.parseInt(splited[2]), Color.parseColor("#FFFFFF"), String.valueOf(entry.getValue())));
            calendarView.setSchemeDate(schemes);
            System.out.println("Key : " + entry.getKey() + " Value : "
                    + entry.getValue());
        }

    }



    private void setDate(int month, int year) {
        if (month==1)
        {
            mTextMonthDay.setText("January "+String.valueOf(year));
        }
        else if (month==2){
            mTextMonthDay.setText("February "+String.valueOf(year));
        }
        else if (month==3){
            mTextMonthDay.setText("March "+String.valueOf(year));
        }
        else if (month==4){
            mTextMonthDay.setText("April "+String.valueOf(year));
        }
        else if (month==5){
            mTextMonthDay.setText("May "+String.valueOf(year));
        }
        else if (month==6){
            mTextMonthDay.setText("June "+String.valueOf(year));
        }
        else if (month==7){
            mTextMonthDay.setText("July "+String.valueOf(year));
        }
        else if (month==8){
            mTextMonthDay.setText("August "+String.valueOf(year));
        }
        else if (month==9){
            mTextMonthDay.setText("September "+String.valueOf(year));
        }
        else if (month==10){
            mTextMonthDay.setText("October "+String.valueOf(year));
        }
        else if (month==11){
            mTextMonthDay.setText("November "+String.valueOf(year));
        }
        else if (month==12){
            mTextMonthDay.setText("December "+String.valueOf(year));
        }
    }

    private static health.app.CalenderCount.Calendar getSchemeCalendar1(int year, int month, int day) {
        health.app.CalenderCount.Calendar calendar = new health.app.CalenderCount.Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        return calendar;
    }

    private static health.app.CalenderCount.Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        health.app.CalenderCount.Calendar calendar = new health.app.CalenderCount.Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        return calendar;
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
//               // tuesday.add(Calendar.DAY_OF_YEAR, (Calendar.TUESDAY - tuesday.get(Calendar.DAY_OF_WEEK) + 7 + i));
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
//               // friday.add(Calendar.DAY_OF_YEAR, (Calendar.FRIDAY - friday.get(Calendar.DAY_OF_WEEK) + 7 + i));
//                friday.add(Calendar.DAY_OF_YEAR, (Calendar.FRIDAY - friday.get(Calendar.DAY_OF_WEEK) + i));
//                weekends.add(friday);
//            }
//            else if (day.equalsIgnoreCase("Saturday")){
//                saturday.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - saturday.get(Calendar.DAY_OF_WEEK) + i));
//                weekends.add(saturday);
//            }
//        }
//  //      getCalenderList(weekends);
////        Calendar[] disabledDays = weekends.toArray(new Calendar[weekends.size()]);
//        for (int k=0;k<weekends.size();k++) {
//            Log.d("disabledDays", "disabledDays" + weekends.get(k).getTime());
//        }
//    }

    private void setYearSpinner(String item, health.app.CalenderCount.Calendar calendar1) {

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
            mTextMonthDay.setText(String.valueOf(spinnerMonth.getSelectedItem().toString()) + " " + item);
            //  tvYear.setText(item);
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
            calendarView.scrollToYear(calendar1.getYear(),month1);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
            Date date = new Date();
            Log.d("Month", dateFormat.format(date));
            month1 = Integer.parseInt(dateFormat.format(date));
            //month1= Integer.parseInt(dateFormat.format(date))-1;
            Log.d("cmonth", "cmonth" + month1);
            Calendar c = Calendar.getInstance();
            Log.d("month1", "month1" + c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
            mTextMonthDay.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + item);
            //  tvYear.setText(String.valueOf(cyear));
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
            calendarView.scrollToYear(calendar1.getYear(),month1);
            // compactCalendarView.setCurrentDate(startDate2);
        }
    }

    private void setMonthSpinner(String item, health.app.CalenderCount.Calendar calendar1) {
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
            mTextMonthDay.setText(item + " " + String.valueOf(year));
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

            calendarView.scrollToYear(year, calendar1.getMonth());
        } else {
            mTextMonthDay.setText(item + " " + String.valueOf(cyear));
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

            calendarView.scrollToYear(cyear, calendar1.getMonth());
        }
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

    @Override
    public void onDateChange(health.app.CalenderCount.Calendar calendar) {
        setDate(calendar.getMonth(),calendar.getYear());
        mYear = calendar.getYear();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDateSelected(health.app.CalenderCount.Calendar calendar) {
        startEndTimeList.clear();
        String selectedDate=calendar.getYear()+"-"+calendar.getMonth()+"-"+calendar.getDay();
        tvYear.setText(String.valueOf(calendar.getYear()));
        Log.d("getdate", "getDate" + calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Date startDate;
        try {
            startDate = df.parse(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            String month_name = month_date.format(startDate);
            String dayName = simpleDateFormat.format(startDate);
            Log.d("dayName1", "dayName1" + dayName);
            tvMonthDay.setText(dayName+", "+month_name+" "+calendar.getDay());
            Date currentTime = Calendar.getInstance().getTime();
            boolean getResult=health.app.Utilities.DateUtils.isBeforeDay(startDate,currentTime);
            if (getResult) {

            }
            else {
                getPackageSlotList(selectedDate,packageData.getRequestId());
            }
            onDateChange(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getPackageSlotList(String date, final String requestId){
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getSessionByTrainer(Preferences.getInstance().getUserId(),date,requestId).enqueue(new Callback<SessionSlotResponse>() {
            @Override
            public void onResponse(Call<SessionSlotResponse> call, Response<SessionSlotResponse> response) {
                if (response.body().isStatus()) {
                    if (response.body().getSessionSlotData().getSessionsListList().size()!=0) {
                        calenderRecyclerview.setVisibility(View.VISIBLE);
                        scrollView.fullScroll(View.FOCUS_DOWN);
                        for (int i = 0; i < response.body().getSessionSlotData().getSessionsListList().size(); i++) {
                            StartEndTime startEndTime = new StartEndTime();
                            startEndTime.setCustomerId(response.body().getSessionSlotData().getSessionsListList().get(i).getCustomerId());
                            startEndTime.setPackageId(response.body().getSessionSlotData().getSessionsListList().get(i).getPackageId());
                            startEndTime.setRequestId(response.body().getSessionSlotData().getSessionsListList().get(i).getRequestId());
                            startEndTime.setSessionDate(response.body().getSessionSlotData().getSessionsListList().get(i).getSessionDate());
                            startEndTime.setIsDeleted(response.body().getSessionSlotData().getSessionsListList().get(i).getIsDeleted());
                            startEndTime.setSessionSlotId(response.body().getSessionSlotData().getSessionsListList().get(i).getSessionSlotId());
                            startEndTime.setStatus(response.body().getSessionSlotData().getSessionsListList().get(i).getStatus());
                            startEndTime.setStartTime(response.body().getSessionSlotData().getSessionsListList().get(i).getStartTime());
                            startEndTime.setEndTime(response.body().getSessionSlotData().getSessionsListList().get(i).getEndTime());
                            startEndTimeList.add(startEndTime);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            calenderRecyclerview.setLayoutManager(mLayoutManager);
                            CancelListAdapter cancelListAdapter = new CancelListAdapter(MySessionCalenderActivity.this, startEndTimeList, response.body().getSessionSlotData().getSessionsListList().get(i).getTrainerId(), response.body().getSessionSlotData().getSessionsListList().get(i).getCustomerId());
                            calenderRecyclerview.setAdapter(cancelListAdapter);
                            cancelListAdapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        calenderRecyclerview.setVisibility(View.GONE);
                        scrollView.fullScroll(View.FOCUS_UP);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();

            }

            @Override
            public void onFailure(Call<SessionSlotResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }




    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

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

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

    @BindView(R.id.calender_recyclerview)
    RecyclerView calenderRecyclerview;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_session_per_day)
    TextView sessionPerDay;

    @BindView(R.id.tv_session_price)
    TextView sessionPrice;

    @BindView(R.id.tv_session_duration)
    TextView sessionDuration;

    @BindView(R.id.tv_month_day) TextView mTextMonthDay;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        // timeSlotList1();
    }
}


