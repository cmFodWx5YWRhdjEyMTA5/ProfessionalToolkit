package health.app.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import butterknife.OnClick;
import health.app.Activities.ReceivedRequestActivity;
import health.app.Activities.SessionByDateActivity;
import health.app.Activities.TrainerDashboardActivity;
import health.app.AddPackage.AddDurationActivity;
import health.app.CalenderCount.CalendarView;
import health.app.R;
import health.app.Response.MySessionResponse;
import health.app.Response.NotificationCount;
import health.app.Response.TrainerCalenderResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.FragmentChangeListener;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TDashboardAgainFragment extends Fragment implements CalendarView.OnDateSelectedListener,CalendarView.OnDateChangeListener{
    TextView notifCount;
    private int mYear;
    RelativeLayout badgeLayout;
    static List<health.app.CalenderCount.Calendar> schemes;
    List<String> list;
    static CalendarView calendarView;
    int cyear;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private OnFragmentInteractionListener mListener;
    public TDashboardAgainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dashboard_again, container, false);
        ButterKnife.bind(this,view);
        Log.d("which","which"+"oncreate");
        schemes = new ArrayList<>();
        list=new ArrayList<String>();
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
        calendarView.setOnDateSelectedListener(this);
        setDate(calendarView.getCurMonth(),calendarView.getCurYear());
        final Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Regular.ttf");
        final Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-SemiBold.ttf");
        mTextMonthDay.setTypeface(font1);
        getCalenderCount();
        getDate();
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void timeSlotList(String date) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.getTSessionDate(Preferences.getInstance().getUserId(), date).enqueue(new Callback<MySessionResponse>() {
            @Override
            public void onResponse(Call<MySessionResponse> call, Response<MySessionResponse> response) {
                if (response.body() != null) {
                    Intent intent = new Intent(getContext(), SessionByDateActivity.class);
                    intent.putExtra("sessionList1", response.body().getSessionData());
                    // intent.putParcelableArrayListExtra("listing", (ArrayList<? extends Parcelable>) trainerIdDataList);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) getContext()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<MySessionResponse> call, Throwable t) {
                ((BaseActivity) getContext()).hideProgressBar();
                Toast.makeText(getActivity(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
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
        tv_year.setText(String.valueOf(cyear));
        tv_month.setText(dayName+", "+calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH )+" "+day);
        Log.d("monthName","monthName" +calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ) );
        Log.d("dayName","dayName" +dayName);
        Log.d("day","day" +day);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getCalenderCount() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.getCalenderCountTrainer(Preferences.getInstance().getUserId()).enqueue(new Callback<TrainerCalenderResponse>() {
            @Override
            public void onResponse(Call<TrainerCalenderResponse> call, Response<TrainerCalenderResponse> response) {
                if (response.body().isStatus()) {
                    setCalenderDate(response.body().getCalenderData().getCustomersList());
                    }
                 else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) getContext()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<TrainerCalenderResponse> call, Throwable t) {
                ((BaseActivity) getContext()).hideProgressBar();
                Toast.makeText(getContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCalenderDate(List<TrainerCalenderResponse.CalenderData.Customers> customersList) {
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

    private static health.app.CalenderCount.Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        health.app.CalenderCount.Calendar calendar = new health.app.CalenderCount.Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        return calendar;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.bell).setVisible(true).setEnabled(true);
        getNotificationCount();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard, menu);
        super.onCreateOptionsMenu(menu,inflater);
        badgeLayout = (RelativeLayout) menu.findItem(R.id.bell).getActionView();
        notifCount = (TextView) badgeLayout.findViewById(R.id.txtCount);
        notifCount.setVisibility(View.GONE);
        ImageView on=(ImageView)badgeLayout.findViewById(R.id.hotlist_bell);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr=new NotificationFragment();
                FragmentChangeListener fc=(FragmentChangeListener)getActivity();
                fc.replaceFragment(fr,"Notifications");
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getNotificationCount() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.getCount(Preferences.getInstance().getUserId()).enqueue(new Callback<NotificationCount>() {
            @Override
            public void onResponse(Call<NotificationCount> call, Response<NotificationCount> response) {
                if (response.body().isStatus()) {
                    if (response.body().getData()==0) {
                        notifCount.setVisibility(View.GONE);
                    }
                    else {
                        notifCount.setVisibility(View.VISIBLE);
                        notifCount.setText(String.valueOf(response.body().getData()));
                    }
                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) getContext()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<NotificationCount> call, Throwable t) {
                ((BaseActivity) getContext()).hideProgressBar();
                Toast.makeText(getContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }



    @OnClick(R.id.rl_messages)
    void messageLayout(){
        Fragment fr=new MyInboxFragment();
        FragmentChangeListener fc=(FragmentChangeListener)getActivity();
        fc.replaceFragment(fr,"My Messages");
        TrainerDashboardActivity.bottomNavigationView.setVisibility(View.GONE);
    }

    @OnClick(R.id.rl_request)
    void requestLayout(){
        startActivity(new Intent(getActivity(), ReceivedRequestActivity.class));
    }

    @OnClick(R.id.rl_addPackage)
    void packageLayout(){
        startActivity(new Intent(getActivity(), AddDurationActivity.class));
    }



    @Override
    public void onDateChange(health.app.CalenderCount.Calendar calendar) {
        setDate(calendar.getMonth(),calendar.getYear());
        mYear = calendar.getYear();
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

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDateSelected(health.app.CalenderCount.Calendar calendar) {
        tv_year.setText(String.valueOf(calendar.getYear()));
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
            tv_month.setText(dayName+", "+month_name+" "+calendar.getDay());
            Date currentTime = Calendar.getInstance().getTime();
            boolean getResult=health.app.Utilities.DateUtils.isBeforeDay(startDate,currentTime);
            if (getResult) {}
            else {
                timeSlotList(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());
            }
            onDateChange(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BindView(R.id.tv_year) TextView tv_year;
    @BindView(R.id.tv_month) TextView tv_month;
    @BindView(R.id.tv_month_day) TextView mTextMonthDay;



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
