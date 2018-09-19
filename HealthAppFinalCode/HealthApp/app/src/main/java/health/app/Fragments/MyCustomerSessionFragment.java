package health.app.Fragments;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.SessionListAdapter;
import health.app.Model.StartEndTime;
import health.app.R;
import health.app.Response.TrainerDashboardListResponse;
import health.app.Utilities.BaseActivity;


public class MyCustomerSessionFragment extends BaseActivity {
List<StartEndTime> startEndTimeList=new ArrayList<>();

    SessionListAdapter sessionListAdapter;
    public static StartEndTime startEndTime;
    TrainerDashboardListResponse.ApprovedData trainerData;
    public static final String MYSESSION = "mysession";
    RecyclerView recyclerView;
    public MyCustomerSessionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_customer_session);
        ButterKnife.bind(this);
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
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        sessionRecyclerview.setLayoutManager(mLayoutManager);
        sessionRecyclerview.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        sessionRecyclerview.setItemAnimator(new DefaultItemAnimator());
        if ((trainerData = (TrainerDashboardListResponse.ApprovedData) getIntent().getParcelableExtra(MYSESSION))!=null)
        {
            startEndTime=new StartEndTime();
            startEndTime.setFirstName(trainerData.getFirst_name());
            startEndTime.setLastName(trainerData.getLast_name());
            startEndTime.setAge(trainerData.getAge());
            startEndTime.setGender(trainerData.getGender());
            startEndTime.setProfileImage(trainerData.getProfile_image());
            startEndTime.setPhone(trainerData.getPhone());
            startEndTime.setHeight(trainerData.getHeight());
            startEndTime.setWeight(trainerData.getWeight());
            startEndTime.setWaist(trainerData.getWaist());
            startEndTime.setNeck(trainerData.getNeck());
            startEndTime.setBodyFat(trainerData.getFat());
            startEndTime.setEmail(trainerData.getEmail());
            sessionListAdapter = new SessionListAdapter(getApplicationContext(),trainerData.getPackagesList(),trainerData.getImage_base_url());
            sessionRecyclerview.setAdapter(sessionListAdapter);
            sessionListAdapter.notifyDataSetChanged();
        }


    }








    @BindView(R.id.session_recyclerview)
    RecyclerView sessionRecyclerview;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
