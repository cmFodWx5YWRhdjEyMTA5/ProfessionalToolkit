package health.app.Activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.CancelListAdapter;
import health.app.Model.StartEndTime;
import health.app.R;
import health.app.Response.MySessionResponse;
import health.app.Response.SessionSlotResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PackageDetailActivity extends BaseActivity {
    String hours,minutes,hours1;
    MySessionResponse.SessionData.PackageList packageList;
    public static final String PACKAGE = "package";
    List<StartEndTime> startEndTimeList=new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);
        ButterKnife.bind(this);
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Session Slots");
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
        packageList = (MySessionResponse.SessionData.PackageList)getIntent().getParcelableExtra(PACKAGE);
        if ((packageList = (MySessionResponse.SessionData.PackageList) getIntent().getParcelableExtra(PACKAGE))!=null)
        {
            Log.d("packageData1","packageData"+packageList.getNoOfDays());
            Log.d("packageData","packageData"+packageList.getSessionDays());
            sessionPerDay.setText("Number Of Session In A Day : "+packageList.getNoOfSlots());
            sessionPrice.setText("Package Price : "+"$"+packageList.getSessionPrice());
            setLength(packageList.getSessionTime());
            if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
                getPackageSlotList(packageList.getSessionDate(), packageList.getRequestId());
            }
            else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")){
                getPackageSlotList2(packageList.getSessionDate(), packageList.getRequestId());
            }

        }
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getPackageSlotList2(String date,String requestId){
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getSessionByCustomer(Preferences.getInstance().getUserId(),date,requestId).enqueue(new Callback<SessionSlotResponse>() {
            @Override
            public void onResponse(Call<SessionSlotResponse> call, Response<SessionSlotResponse> response) {
                if (response.body().isStatus()) {
                    for (int i=0;i<response.body().getSessionSlotData().getSessionsListList().size();i++) {
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
                        CancelListAdapter cancelListAdapter = new CancelListAdapter(PackageDetailActivity.this, startEndTimeList, response.body().getSessionSlotData().getSessionsListList().get(i).getTrainerId(), response.body().getSessionSlotData().getSessionsListList().get(i).getCustomerId());
                        calenderRecyclerview.setAdapter(cancelListAdapter);
                        cancelListAdapter.notifyDataSetChanged();
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getPackageSlotList(String date,String requestId){
            final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
            showProgressbar("Loading", "Please wait...");
            healthAppService.getSessionByTrainer(Preferences.getInstance().getUserId(),date,requestId).enqueue(new Callback<SessionSlotResponse>() {
                @Override
                public void onResponse(Call<SessionSlotResponse> call, Response<SessionSlotResponse> response) {
                    if (response.body().isStatus()) {
                        for (int i=0;i<response.body().getSessionSlotData().getSessionsListList().size();i++) {
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
                            CancelListAdapter cancelListAdapter = new CancelListAdapter(PackageDetailActivity.this, startEndTimeList, response.body().getSessionSlotData().getSessionsListList().get(i).getTrainerId(), response.body().getSessionSlotData().getSessionsListList().get(i).getCustomerId());
                            calenderRecyclerview.setAdapter(cancelListAdapter);
                            cancelListAdapter.notifyDataSetChanged();
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


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.calender_recyclerview)
    RecyclerView calenderRecyclerview;

    @BindView(R.id.tv_session_per_day)
    TextView sessionPerDay;

    @BindView(R.id.tv_session_price)
    TextView sessionPrice;

    @BindView(R.id.tv_session_duration)
    TextView sessionDuration;
}
