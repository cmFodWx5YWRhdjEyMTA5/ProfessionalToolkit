package health.app.Activities;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.TrainerListAdapter;
import health.app.Model.CurrentSessionList;
import health.app.Model.StartEndTime;
import health.app.R;
import health.app.Response.MyTrainerListResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySessionActivity extends BaseActivity {
    TrainerListAdapter trainerListAdapter;
    public static StartEndTime startEndTime;
    List<CurrentSessionList> currentSessionListList=new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_session);
        ButterKnife.bind(this);
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Book New Appointment");
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
        sessionRecyclerview.setLayoutManager(mLayoutManager);
        sessionRecyclerview.setItemAnimator(new DefaultItemAnimator());
        getTrainerList();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getTrainerList() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getTrainer(Preferences.getInstance().getUserId()).enqueue(new Callback<MyTrainerListResponse>() {
            @Override
            public void onResponse(Call<MyTrainerListResponse> call, Response<MyTrainerListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getMyTrainerDataList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        sessionRecyclerview.setVisibility(View.GONE);
                    }
                    else {
                        trainerListAdapter = new TrainerListAdapter(MySessionActivity.this,response.body().getMyTrainerDataList(),"ShowLayout");
                        sessionRecyclerview.setAdapter(trainerListAdapter);}
                } else {
                    Toast.makeText(MySessionActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();

            }

            @Override
            public void onFailure(Call<MyTrainerListResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(MySessionActivity.this, HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @BindView(R.id.session_recyclerview)
    RecyclerView sessionRecyclerview;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;
}
