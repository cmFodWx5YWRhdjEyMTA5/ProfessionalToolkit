package health.app.Activities;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.AddMessageClientAdapter;
import health.app.Adapter.AddMessageTrainerAdapter;
import health.app.R;
import health.app.Response.MyTrainerListResponse;
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

public class AddMessageActivity extends BaseActivity {
    AddMessageTrainerAdapter addMessageTrainerAdapter;
    AddMessageClientAdapter addMessageClientAdapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.mipmap.backicon2x));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViews();
        if (Preferences.getInstance().getUserType().equals("Trainer"))
        {
            getClientList();
            title.setText("My Client");
            message.setText("No Client Found");

        }
        else if (Preferences.getInstance().getUserType().equals("Customer"))
        {
            getTrainerList();
            title.setText("My Trainer");
            message.setText("There are no trainer hired by you, Please send request first from your dashboard");

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getClientList() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getClients(Preferences.getInstance().getUserId()).enqueue(new Callback<TrainerDashboardListResponse>() {
            @Override
            public void onResponse(Call<TrainerDashboardListResponse> call, Response<TrainerDashboardListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getApprovedDataList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        trainerRecyclerview.setVisibility(View.GONE);
                    }
                    else {
                        addMessageClientAdapter = new AddMessageClientAdapter(getApplicationContext(), response.body().getApprovedDataList());
                        trainerRecyclerview.setAdapter(addMessageClientAdapter);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();

            }

            @Override
            public void onFailure(Call<TrainerDashboardListResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
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
                        trainerRecyclerview.setVisibility(View.GONE);
                    }
                    else {
                        addMessageTrainerAdapter = new AddMessageTrainerAdapter(getApplicationContext(),response.body().getMyTrainerDataList());
                        trainerRecyclerview.setAdapter(addMessageTrainerAdapter);}
                }
                else {
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();

            }

            @Override
            public void onFailure(Call<MyTrainerListResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews(){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        trainerRecyclerview.setLayoutManager(mLayoutManager);
        trainerRecyclerview.setItemAnimator(new DefaultItemAnimator());
    }

    @BindView(R.id.trainer_list_recyclerview)
    RecyclerView trainerRecyclerview;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.message)
    TextView message;
}
