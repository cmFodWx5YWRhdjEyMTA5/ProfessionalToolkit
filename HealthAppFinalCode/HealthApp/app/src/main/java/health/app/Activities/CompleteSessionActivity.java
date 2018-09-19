package health.app.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Model.CompletedSessionList;
import health.app.R;
import health.app.Utilities.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CompleteSessionActivity extends BaseActivity {
    public static final String COMP = "comp";
    CompletedSessionList completedSessionList;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_session);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Client Detail");
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
        if ((completedSessionList =(CompletedSessionList) getIntent().getParcelableExtra(COMP)) != null) {
            sessionPerDay.setText("Number Of Session In A Day : "+completedSessionList.getNoOfSlots());
            sessionPrice.setText("Package Price : "+"$"+completedSessionList.getSessionPrice());
            sessionDuration.setText("Session Duration : "+completedSessionList.getSessionTime());
            sessionDate.setText("Date : "+completedSessionList.getSessionDate());
            sessionStartTime.setText("Start Time : "+completedSessionList.getStartTime());
            sessionEndTime.setText("End Time : "+completedSessionList.getEndTime());
            if (completedSessionList.getStatus().equalsIgnoreCase("2")) {
                sessionStatus.setText("Completed");
            }

        }

    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_session_per_day)
    TextView sessionPerDay;

    @BindView(R.id.tv_session_price)
    TextView sessionPrice;

    @BindView(R.id.tv_session_duration)
    TextView sessionDuration;

    @BindView(R.id.tv_session_date)
    TextView sessionDate;

    @BindView(R.id.tv_session_start_time)
    TextView sessionStartTime;

    @BindView(R.id.tv_session_end_time)
    TextView sessionEndTime;

    @BindView(R.id.tv_session_status)
    TextView sessionStatus;
}
