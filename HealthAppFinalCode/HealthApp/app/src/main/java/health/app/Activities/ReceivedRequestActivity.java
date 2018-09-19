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

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.RequestAdapter;
import health.app.R;
import health.app.Response.GetRequestResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceivedRequestActivity extends BaseActivity {
    RequestAdapter requestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_request);
        ButterKnife.bind(this);
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Received Request");
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
        getRequests();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getRequests() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        requestRecyclerView.setLayoutManager(mLayoutManager);
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
         showProgressbar("Loading", "Please wait...");
        healthAppService.getRequest(Preferences.getInstance().getUserId()).enqueue(new Callback<GetRequestResponse>() {
            @Override
            public void onResponse(Call<GetRequestResponse> call, Response<GetRequestResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    if (response.body().getRequestData().getRequestList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        requestRecyclerView.setVisibility(View.GONE);
                    }
                    else {
                        requestAdapter = new RequestAdapter(ReceivedRequestActivity.this, response.body().getRequestData().getRequestList());
                        requestRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        requestRecyclerView.setAdapter(requestAdapter);
                    }
                } else {
                    noRequestLayout.setVisibility(View.VISIBLE);
                    requestRecyclerView.setVisibility(View.GONE);
                }
               hideProgressBar();

            }

            @Override
            public void onFailure(Call<GetRequestResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @BindView(R.id.request_recycler_view)
    RecyclerView requestRecyclerView;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
}
