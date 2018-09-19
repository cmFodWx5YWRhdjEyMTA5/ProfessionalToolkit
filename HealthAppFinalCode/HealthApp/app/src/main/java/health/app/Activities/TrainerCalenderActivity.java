package health.app.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.R;
import health.app.Utilities.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TrainerCalenderActivity extends BaseActivity implements OnDateSelectedListener{
    private static final DateFormat FORMATTER= new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_calender);
        ButterKnife.bind(this);
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Add Time");
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
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        materialCalendarView.setOnDateChangedListener(this);
        Calendar calendar = Calendar.getInstance();
        materialCalendarView.setSelectedDate(calendar.getTime());
        Log.d("instance1","instance1"+calendar.getTime());
        materialCalendarView.state().edit()
                .setMinimumDate(calendar.getTime())
                .commit();
        list.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_next, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.next)
        {
//            SelectSessionFragment.availabilityModel.setDateList(list);
           // Toast.makeText(getApplicationContext(),"Heloo"+list,Toast.LENGTH_SHORT).show();
            if (list.size()!=0) {
//                Intent intent = new Intent(getApplicationContext(), TrainerAvailabilityActivity.class);
//                startActivity(intent);
            }
            else {
                Snackbar snackbar = Snackbar.make(container, "Please select day first.", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(Color.RED);
                snackbar.show();
                //Toast.makeText(getApplicationContext(), "Please select day first", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @BindView(R.id.calendarView)
    MaterialCalendarView materialCalendarView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    LinearLayout container;

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        if (selected) {
            list.add(FORMATTER.format(date.getDate()));
            //Toast.makeText(getApplicationContext(), "Selected Date" + FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
        }
        else {
            list.remove(FORMATTER.format(date.getDate()));
           // Toast.makeText(getApplicationContext(), "Unselected Date" + FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
        }
    }
}
