package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Fragments.MyCustomerSessionFragment;
import health.app.Fragments.MySessionFragment;
import health.app.Model.StartEndTime;
import health.app.R;
import health.app.Response.NewMessageResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NewClientDetailActivity extends BaseActivity {
    public StartEndTime approvedData;
    String sessionDate,sessionStartTime;
    public static final String CLIENT = "client";
    String sessionsSlotId,customerId,clientId;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client_detail);
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
        approvedData = (StartEndTime) getIntent().getParcelableExtra(CLIENT);
        setInitValues();
    }

    public void setInitValues() {
        if(MySessionFragment.startEndTime.getProfileImage()!=null)
        {
            Picasso.with(clientImage.getContext()).load(MyCustomerSessionFragment.startEndTime.getProfileImage()).into(clientImage);
        }
        sessionDate=approvedData.getSessionDate();
        sessionStartTime=approvedData.getStartTime();
        clientAge.setText(MySessionFragment.startEndTime.getAge()+" Years");
        clientName.setText(MySessionFragment.startEndTime.getFirstName()+" "+MySessionFragment.startEndTime.getLastName());
        clientPhn.setText(MyCustomerSessionFragment.startEndTime.getPhone());
        clientSlottime.setText("Start Time "+approvedData.getStartTime()+" - "+"End Time "+approvedData.getEndTime());
        clientEmail.setText(MyCustomerSessionFragment.startEndTime.getEmail());
        clientGender.setText(MyCustomerSessionFragment.startEndTime.getGender());
        clientHeight.setText(MyCustomerSessionFragment.startEndTime.getHeight()+"cm");
        clientWeight.setText(MyCustomerSessionFragment.startEndTime.getWeight()+"kg");
        clientFat.setText(MyCustomerSessionFragment.startEndTime.getBodyFat());
        clientNeck.setText(MyCustomerSessionFragment.startEndTime.getNeck()+"cm");
        clientWaist.setText(MyCustomerSessionFragment.startEndTime.getWaist()+"cm");
        if (approvedData.getSessionSlotId() != null) {
            sessionsSlotId = approvedData.getSessionSlotId();
        }
        if (approvedData.getCustomerId() != null) {
            customerId = approvedData.getCustomerId();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startSession() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.startSession(Preferences.getInstance().getUserId(),customerId,sessionsSlotId).
                enqueue(new Callback<NewMessageResponse>() {
                    @Override
                    public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                        if (response.body().isStatus()){
                            alertUser(response.body().getMsg());
                        }
                        else {
                            alertUser(response.body().getMsg());
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void markCompleteSession() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.markSession(Preferences.getInstance().getUserId(),customerId,sessionsSlotId).
                enqueue(new Callback<NewMessageResponse>() {
                    @Override
                    public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                        if (response.body().isStatus()){
                            alertUser(response.body().getMsg());
                        }
                        else {
                            alertUser(response.body().getMsg());
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void alertUser(String msg) {
        if (msg!=null) {
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(NewClientDetailActivity.this);
            alertDialogBuilder.setTitle(getString(R.string.app_name));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ptlogo);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), TrainerDashboardActivity.class));
                    finish();
                }
            });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void openConfirmDialog() {
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(NewClientDetailActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setIcon(R.drawable.ptlogo);
        alertDialogBuilder.setMessage("Are you sure you want to mark session complete?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                markCompleteSession();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        pbutton.setTextColor(Color.parseColor("#FF7010"));
        nbutton.setTextColor(Color.parseColor("#FF7010"));
    }

    @OnClick(R.id.client_message)
    void sendMessage(){
        Intent intent = new Intent(getApplicationContext(),CommonChatActivity.class);
        intent.putExtra("customerId",customerId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.client_start_session)
    void clientStartSession(){
        Date date1 = null;
        Date date2 = null;
        try {
            SimpleDateFormat cformat = new SimpleDateFormat("yyyy-MM-dd");
            Date todayDate = Calendar.getInstance().getTime();
            String todayString = cformat.format(todayDate);
            date1 = cformat.parse(sessionDate);
            date2 = cformat.parse(todayString);
            Log.d("date1","date1"+date1+date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean isSame=health.app.Utilities.DateUtils.isSameDay(date1,date2);
        if (isSame) {
            startSession();
        }
        else {
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(NewClientDetailActivity.this);
            alertDialogBuilder.setTitle(getString(R.string.app_name));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ptlogo);
            alertDialogBuilder.setMessage("Please start your session on "+sessionDate+" "+sessionStartTime);
            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(Color.parseColor("#FF7010"));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.client_mark_session)
    void clientmarkSession(){
        openConfirmDialog();
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.client_age)
    TextView clientAge;

    @BindView(R.id.client_neck)
    TextView clientNeck;

    @BindView(R.id.client_waist)
    TextView clientWaist;

    @BindView(R.id.client_name)
    TextView clientName;

    @BindView(R.id.client_phn)
    TextView clientPhn;

    @BindView(R.id.client_image)
    ImageView clientImage;

    @BindView(R.id.client_slottime)
    TextView clientSlottime;

    @BindView(R.id.client_email)
    TextView clientEmail;

    @BindView(R.id.client_gender)
    TextView clientGender;

    @BindView(R.id.client_height)
    TextView clientHeight;

    @BindView(R.id.client_weight)
    TextView clientWeight;

    @BindView(R.id.client_fat)
    TextView clientFat;
}
