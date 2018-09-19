package health.app.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Demo.AvailableSlotsActivity;
import health.app.Model.SendList;
import health.app.Model.SlotList;
import health.app.R;
import health.app.Response.TimeSlotByDateResponse;
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

public class TrainerIdCalenderActivity extends BaseActivity {
    ArrayList<String> yearList = new ArrayList<String>();
    public TrainerPackageResponse.PackageData packageData=null;
    public static final String PACKAGE_DAY = "packageDay";
    int month,month1,year,cyear;
    int k=0;
    String sessionTime;
    ArrayList<Calendar> weekends;
    ArrayList<Date> weekendDates;
    ArrayList<String> weekendString;
    SlotList slotList;
    String result1;
    public static SendList sendList;
    ArrayList<String> selectedDateTime;
    List<Date> add=new ArrayList<Date>();
    ArrayList<Date> newList;
    List<Date> add1;
    Date date;
    ArrayAdapter arrayAdapter1,arrayAdapter2;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    String[] monthList = {"January", "February","March","April","May","June","July","August","September","October","November","December"};
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_id_calender);
        ButterKnife.bind(this);
        add1=new ArrayList<Date>();
        weekends = new ArrayList<>();
        weekendString=new ArrayList<>();
        weekendDates=new ArrayList<>();
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        packageLabel.setTypeface(font);
        selectedDateTime = new ArrayList<String>();
        tvYear.setTypeface(font);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Package");
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
        if ((packageData = (TrainerPackageResponse.PackageData) getIntent().getParcelableExtra(PACKAGE_DAY))!=null)
        {
            Log.d("packageData1","packageData"+packageData.getNoOfDays());
            Log.d("packageData","packageData"+packageData.getSessionDays());
            sessionPerDay.setText("Number Of Session In A Day : "+packageData.getNoOfSlots());
            sessionPrice.setText("Package Price : "+"$"+packageData.getSessionPrice());
            sessionDuration.setText("Session Duration : "+packageData.getSessionTime());
            sessionTime=packageData.getSessionTime();

        }
        if (packageData.getSessionDays().contains(",")) {
            List<String> result = Arrays.asList(packageData.getSessionDays().split("\\s*,\\s*"));
            Log.d("list","list"+result);
            for (int i=0;i<result.size();i++){
                weekends = getDates(Integer.parseInt(packageData.getNoOfDays()),result.get(i));
            }
        }
        else {
           weekends= getDates(Integer.parseInt(packageData.getNoOfDays()),packageData.getSessionDays());
        }

        for (int i=0;i<weekends.size();i++) {
            SimpleDateFormat parseFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            Date dateq = null;

            try {
                dateq = parseFormat.parse(String.valueOf(weekends.get(i).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat cformat = new SimpleDateFormat("dd-MM-yyyy");
            weekendString.add(cformat.format(dateq));
        }

        Collections.sort(weekendString, new Comparator<String>() {
            @Override
            public int compare(String arg0, String arg1) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                int compareResult = 0;
                try {
                    Date arg0Date = format.parse(arg0);
                    Date arg1Date = format.parse(arg1);
                    compareResult = arg0Date.compareTo(arg1Date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    compareResult = arg0.compareTo(arg1);
                }
                return compareResult;
            }
        });
        for (int i=0;i<weekendString.size();i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date myDate=null;
            try {
                 myDate = dateFormat.parse(weekendString.get(i));
                 weekendDates.add(myDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (weekendDates.size()>Integer.parseInt(packageData.getNoOfDays())) {
            newList = new ArrayList<Date>(weekendDates.subList(0,Integer.parseInt(packageData.getNoOfDays())));
        }
        else {
            newList=weekendDates;
        }
        getCalenderList(newList);
        sendList=new SendList();
        sendList.setSendList(newList);
        getSelectedDays(newList);
        slotList=new SlotList();
        showDataYear();
        showDataMonth();
        getDate();
        final int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear+1; i++) {
            yearList.add(Integer.toString(i));
        }
        for (int i=0;i<selectedDateTime.size();i++) {
            Log.d("values","values"+selectedDateTime.get(i));
        }
        title1.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDayClick(Date dateClicked) {
                Log.d("dateClicked","dateClicked"+String.valueOf(dateClicked));
                Log.d("weekends","weekends"+selectedDateTime);
                Calendar myCal = Calendar.getInstance();
                myCal.setTime(dateClicked);
                tvYear.setText(String.valueOf(myCal.get(Calendar.YEAR)));
                tvMonthDay.setText(String.valueOf(myCal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH))+", "+myCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)+" "+String.valueOf(myCal.get(Calendar.DAY_OF_MONTH)));
                Date currentTime = Calendar.getInstance().getTime();
                boolean getResult=health.app.Utilities.DateUtils.isBeforeDay(dateClicked,currentTime);
                SimpleDateFormat parseFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                Date dateq = null;

                try {
                    dateq = parseFormat.parse(String.valueOf(dateClicked));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("DateFormatted " + dateq.toString());
                SimpleDateFormat cformat = new SimpleDateFormat("yyyy-MM-dd");
                result1 = cformat.format(dateq);
                System.out.println("StrinFormatted" + result1);
                Log.d("result1","result1"+result1);
                if(result1!=null) {
                    for (String string : selectedDateTime) {
                        if (string.matches(result1)) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date d1 = null;
                            try {
                                d1 = dateFormat.parse(result1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            addAvailability(d1,selectedDateTime);
                            Log.d("sucess", "sucess" + "sucess");
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

    String getCommonSeperatedString(List<String> actionObjects) {
        StringBuffer sb = new StringBuffer();
        for (String actionObject : actionObjects){
            sb.append(actionObject).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }


    private ArrayList<Calendar> getDates(int week,String day) {
        // Calendar code is from this link https://coderanch.com/t/385117/java/date-Monday
        Calendar calendar;
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

        return weekends;
    }

    public void getSelectedDays(ArrayList<Date> time) {
        for (int i = 0; i < time.size(); i++) {
            Log.d("time", "time" + time.get(i));
            convertFormat(time);
        }
    }

    private void convertFormat(ArrayList<Date> time) {
        for (int i=0;i<time.size();i++) {
            SimpleDateFormat parseFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

            Date date = null;
            try {
                date = parseFormat.parse(time.get(i).toString());
                System.out.println("DateFormatted " + date.toString());
                SimpleDateFormat cformat = new SimpleDateFormat("yyyy-MM-dd");
                String result = cformat.format(date);
                System.out.println("StrinGFormatted" + result);
                selectedDateTime.add(result);
                Log.d("selectedDates", "selectedDates" + selectedDateTime);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    private static boolean containsName(final List<String> transaction, final String search) {
        Log.d("search", "search" + transaction+search);
        for(String s : transaction)
            if(s.equalsIgnoreCase(search)){
                Log.d("true", "true" + "true");
                return true;
            }
        return false;
    }


    public void getCalenderList(ArrayList<Date> dayList){
        for (int i=0;i<dayList.size();i++) {
            Log.d("cute", "cute" + dayList.get(i).getTime());
            long milliseconds = dayList.get(i).getTime();
            compactCalendarView.setUseThreeLetterAbbreviation(true);
            compactCalendarView.setShouldDrawDaysHeader(true);
            Event ev1 = new Event(Color.parseColor("#FF7010"), milliseconds);
            compactCalendarView.addEvent(ev1);
        }
    }


    private void setYearSpinner(String item) {

        Log.d("selectedYear","selectedYear"+item);
        if (spinnerMonth.getSelectedItem()!=null){
            if (spinnerMonth.getSelectedItem().equals("January"))
            {
                month1=1;
            }
            else if (spinnerMonth.getSelectedItem().equals("February")){
                month1=2;
            }
            else if (spinnerMonth.getSelectedItem().equals("March")){
                month1=3;
            }
            else if (spinnerMonth.getSelectedItem().equals("April")){
                month1=4;
            }
            else if (spinnerMonth.getSelectedItem().equals("May")){
                month1=5;
            }
            else if (spinnerMonth.getSelectedItem().equals("June")){
                month1=6;
            }
            else if (spinnerMonth.getSelectedItem().equals("July")){
                month1=7;
            }
            else if (spinnerMonth.getSelectedItem().equals("August")){
                month1=8;
            }
            else if (spinnerMonth.getSelectedItem().equals("September")){
                month1=9;
            }
            else if (spinnerMonth.getSelectedItem().equals("October")){
                month1=10;
            }
            else if (spinnerMonth.getSelectedItem().equals("November")){
                month1=11;
            }
            else if (spinnerMonth.getSelectedItem().equals("December")){
                month1=12;
            }
            title1.setText(String.valueOf(spinnerMonth.getSelectedItem().toString())+" "+item);
            tvYear.setText(item);
            int day=1;
            String startDateString2=String.valueOf(day)+"/"+String.valueOf(month1)+"/"+item;
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate2=null;
            try {
                startDate2 = df2.parse(startDateString2);
                String newDateString2 = df2.format(startDate2);
                Log.d("newDateString2","newDateString2"+newDateString2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            compactCalendarView.setCurrentDate(startDate2);
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
            Date date = new Date();
            Log.d("Month",dateFormat.format(date));
            month1= Integer.parseInt(dateFormat.format(date));
            //month1= Integer.parseInt(dateFormat.format(date))-1;
            Log.d("cmonth","cmonth"+month1);
            Calendar c= Calendar.getInstance();
            Log.d("month1","month1" +c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ) );
            title1.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH )+" "+item);
            tvYear.setText(String.valueOf(cyear));
            int day=1;
            String startDateString2=String.valueOf(day)+"/"+String.valueOf(month1)+"/"+item;
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate2=null;
            try {
                startDate2 = df2.parse(startDateString2);
                String newDateString2 = df2.format(startDate2);
                Log.d("newDateString2","newDateString2"+newDateString2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            compactCalendarView.setCurrentDate(startDate2);
        }
    }

    private void setMonthSpinner(String item) {
        Log.d("selectedMonth","selectedMonth"+item);
        if (item.equals("January"))
        {
            month=1;
        }
        else if (item.equals("February")){
            month=2;
        }
        else if (item.equals("March")){
            month=3;
        }
        else if (item.equals("April")){
            month=4;
        }
        else if (item.equals("May")){
            month=5;
        }
        else if (item.equals("June")){
            month=6;
        }
        else if (item.equals("July")){
            month=7;
        }
        else if (item.equals("August")){
            month=8;
        }
        else if (item.equals("September")){
            month=9;
        }
        else if (item.equals("October")){
            month=10;
        }
        else if (item.equals("November")){
            month=11;
        }
        else if (item.equals("December")){
            month=12;
        }
        int day=1;
        if (spinnerYear.getSelectedItem()!=null){
            year= Integer.parseInt(spinnerYear.getSelectedItem().toString());
            title1.setText(item+" "+String.valueOf(year));
            tvYear.setText(String.valueOf(year));
            String startDateString1=String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate1=null;
            try {
                startDate1 = df.parse(startDateString1);
                String newDateString1 = df.format(startDate1);
                Log.d("newDateString1","newDateString1"+newDateString1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            compactCalendarView.setCurrentDate(startDate1);
        }
        else
        {
            title1.setText(item+" "+String.valueOf(cyear));
            tvYear.setText(String.valueOf(cyear));
            String startDateString1=String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(cyear);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate1=null;
            try {
                startDate1 = df.parse(startDateString1);
                String newDateString1 = df.format(startDate1);
                Log.d("newDateString1","newDateString1"+newDateString1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            compactCalendarView.setCurrentDate(startDate1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void timeSlotList1(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(c.getTime());
        Log.d("format","format"+format);
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.timeSlotByDate(Preferences.getInstance().getUserId(),format).enqueue(new Callback<TimeSlotByDateResponse>() {
            @Override
            public void onResponse(Call<TimeSlotByDateResponse> call, Response<TimeSlotByDateResponse> response) {
                if (response.body() != null) {
                    for (int i=0;i<response.body().getDataResponse().getAllSlotsList().size();i++) {
                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                        Date d = null;

                        Log.d("mylist","mylist"+response.body().getDataResponse().getAllSlotsList().get(i).getSlotDate());
                        try {
                            d = f.parse(response.body().getDataResponse().getAllSlotsList().get(i).getSlotDate());
                            Log.d("d","d"+d);
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

    void addAvailability(final Date date, ArrayList<String> selectedDateTime) {
        //Intent intent = new Intent(getApplicationContext(), NumberActivity.class);
        Intent intent = new Intent(getApplicationContext(), AvailableSlotsActivity.class);
        intent.putExtra("date", date.getTime());
        intent.putExtra("noOfSlots",packageData.getNoOfSlots());
        intent.putExtra("sessionTime",sessionTime);
        intent.putExtra("packageId",packageData.getSlotId());
        intent.putExtra("trainerId",packageData.getTrainerId());
        intent.putStringArrayListExtra("dateList",selectedDateTime);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void showDataYear(){
        arrayAdapter1 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item_calender,yearList );
        spinnerYear.setAdapter(arrayAdapter1);
        spinnerYear.setTitle("Select Year");
    }

    public void showDataMonth(){
        arrayAdapter2 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item_calender,monthList );
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

    @BindView(R.id.tvMonth)
    TextView tvMonthDay;

    @BindView(R.id.sp_year)
    SearchableSpinner spinnerYear;

    @BindView(R.id.package_label)
    TextView packageLabel;

    @BindView(R.id.sp_month)
    SearchableSpinner spinnerMonth;

    @OnClick(R.id.next)
    void next(){
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
    void previous(){
        compactCalendarView.showPreviousMonth();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        // timeSlotList1();
    }
}
