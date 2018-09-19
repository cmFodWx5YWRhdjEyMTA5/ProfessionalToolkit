package health.app.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.RequestStartEndAdapter;
import health.app.R;
import health.app.Response.GetRequestResponse;
import health.app.Utilities.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RequestTypeActivity extends BaseActivity {
    GetRequestResponse.RequestData.Request request;
    public static final String REQUEST = "request";
    RequestStartEndAdapter requestStartEndAdapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_type);
        ButterKnife.bind(this);
        request = (GetRequestResponse.RequestData.Request) getIntent().getParcelableExtra(REQUEST);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Package Request");
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
        startEndRecyclerview.setLayoutManager(mLayoutManager);
        startEndRecyclerview.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        startEndRecyclerview.setItemAnimator(new DefaultItemAnimator());
        setPackage(request);

    }

    private void setPackage(GetRequestResponse.RequestData.Request request) {
        requestStartEndAdapter = new RequestStartEndAdapter(getApplicationContext(), request.getSelectedSlotsList());
        startEndRecyclerview.setAdapter(requestStartEndAdapter);
        requestStartEndAdapter.notifyDataSetChanged();
        sessionTime.setText(request.getSlot().getSessionTime());
        sessionNo.setText(request.getSlot().getNoOfSlots()+" per day");
        sessionPrice.setText("$"+request.getSlot().getSessionPrice());
        sessionValid.setText(request.getSlot().getNoOfDays()+" Days");
        ArrayList<String> items = new ArrayList(Arrays.asList(request.getSlot().getSessionDays().split("\\s*,\\s*")));
        for (int i=0;i<items.size();i++)
        {
            if (items.get(i).equalsIgnoreCase("Sunday"))
            {
                tvSun.setBackgroundResource(R.drawable.circle_orange);
                tvSun.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Monday"))
            {
                tvMon.setBackgroundResource(R.drawable.circle_orange);
               tvMon.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Tuesday"))
            {
                tvTus.setBackgroundResource(R.drawable.circle_orange);
                tvTus.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Wednesday"))
            {
               tvWed.setBackgroundResource(R.drawable.circle_orange);
                tvWed.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Thursday"))
            {
                tvThu.setBackgroundResource(R.drawable.circle_orange);
                tvThu.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Friday"))
            {
                tvFri.setBackgroundResource(R.drawable.circle_orange);
                tvFri.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Saturday")) {
                tvSat.setBackgroundResource(R.drawable.circle_orange);
                tvSat.setTextColor(Color.WHITE);
            }}

    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_session_time)
    TextView sessionTime;

    @BindView(R.id.tv_session_no)
    TextView sessionNo;

    @BindView(R.id.tv_session_price)
    TextView sessionPrice;

    @BindView(R.id.tv_session_valid)
    TextView sessionValid;

    @BindView(R.id.tv_sun)
    TextView tvSun;

    @BindView(R.id.tv_mon)
    TextView tvMon;

    @BindView(R.id.tv_tus)
    TextView tvTus;

    @BindView(R.id.tv_wed)
    TextView tvWed;

    @BindView(R.id.tv_thu)
    TextView tvThu;

    @BindView(R.id.tv_fri)
    TextView tvFri;

    @BindView(R.id.tv_sat)
    TextView tvSat;

    @BindView(R.id.start_end_recyclerview)
    RecyclerView startEndRecyclerview;

}
