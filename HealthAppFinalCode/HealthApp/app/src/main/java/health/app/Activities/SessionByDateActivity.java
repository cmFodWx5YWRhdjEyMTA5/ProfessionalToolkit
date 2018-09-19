package health.app.Activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.SessionByDateAdapter;
import health.app.R;
import health.app.Response.MySessionResponse;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.Preferences;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SessionByDateActivity extends BaseActivity {
    MySessionResponse.SessionData sessionData1;
    MySessionResponse.SessionData sessionData;
    SessionByDateAdapter sessionByDateAdapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_by_date);
        ButterKnife.bind(this);
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("My Session");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.mipmap.backicon2x));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sessionData=getIntent().getParcelableExtra("sessionList");
        sessionData1=getIntent().getParcelableExtra("sessionList1");
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            setValues(sessionData1);
        }
        else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")){
            setValues(sessionData);
        }
    }
    private void setValues(MySessionResponse.SessionData sessionData) {
        setPackageList(sessionData.getPackagesList(),sessionData.getImg_url());
        Preferences.getInstance().setUserPhotoPath(sessionData.getImg_url());
    }

    private void setPackageList(List<MySessionResponse.SessionData.PackageList> packageList, String img_url) {
        if (packageList.size()!=0) {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            sessionRecyclerview.setLayoutManager(mLayoutManager);
            sessionRecyclerview.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
            sessionRecyclerview.setItemAnimator(new DefaultItemAnimator());
            sessionByDateAdapter = new SessionByDateAdapter(getApplicationContext(), packageList, img_url);
            sessionRecyclerview.setAdapter(sessionByDateAdapter);
            sessionByDateAdapter.notifyDataSetChanged();
        }
        else {
            noRequestLayout.setVisibility(View.VISIBLE);
            sessionRecyclerview.setVisibility(View.GONE);
        }
    }

    @BindView(R.id.session_recyclerview)
    RecyclerView sessionRecyclerview;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;
}
