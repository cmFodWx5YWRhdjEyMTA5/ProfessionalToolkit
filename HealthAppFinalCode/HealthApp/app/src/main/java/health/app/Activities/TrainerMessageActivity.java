package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

public class TrainerMessageActivity extends BaseActivity {
    String trainerId;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_message);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        trainerId=getIntent().getStringExtra("trainerId").toString();
        Log.d("trainerId","trainerId"+trainerId);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Send Message");
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
        inputMessage.setMaxLines(5);
        inputMessage.setVerticalScrollBarEnabled(true);
    }

    public boolean validation() {
        if (inputMessage.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter First Name.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_send)
    void send(){
        if (validation()){
            sendMessage();}
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void sendMessage() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Sending Message...");
        healthAppService.sendMessageToTrainer(Preferences.getInstance().getUserId(),trainerId,String.valueOf(inputMessage.getText())).
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

    private void alertUser(String msg) {
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(TrainerMessageActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setIcon(R.drawable.ptlogo);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.input_message)
    EditText inputMessage;
}
