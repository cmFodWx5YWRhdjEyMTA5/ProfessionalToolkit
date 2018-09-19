package health.app.Activities;

import android.content.Intent;
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
import health.app.Adapter.TrainerPackageAdapter;
import health.app.R;
import health.app.Response.TrainerPackageResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerPackageActivity extends BaseActivity {
    TrainerPackageAdapter trainerPackageAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_package);
        ButterKnife.bind(this);
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("My Package");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.mipmap.backicon2x));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),TrainerDashboardActivity.class));
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        packageRecyclerview.setLayoutManager(mLayoutManager);
        packageRecyclerview.setItemAnimator(new DefaultItemAnimator());
        getPackageList();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getPackageList() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getTrainerPackage(Preferences.getInstance().getUserId()).enqueue(new Callback<TrainerPackageResponse>() {
            @Override
            public void onResponse(Call<TrainerPackageResponse> call, Response<TrainerPackageResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    if (response.body().getPackageDataList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        packageRecyclerview.setVisibility(View.GONE);
                    }
                    else {
                        trainerPackageAdapter = new TrainerPackageAdapter(TrainerPackageActivity.this, response.body().getPackageDataList());
                        packageRecyclerview.setAdapter(trainerPackageAdapter);
                        trainerPackageAdapter.notifyDataSetChanged();
                    }
                } else {
                    noRequestLayout.setVisibility(View.VISIBLE);
                    packageRecyclerview.setVisibility(View.GONE);
                   // Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
               hideProgressBar();

            }

            @Override
            public void onFailure(Call<TrainerPackageResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.package_recycler_view)
    RecyclerView packageRecyclerview;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;
}
