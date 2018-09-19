package health.app.Activities;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import health.app.Firebase.MyFirebaseInstanceIDService;
import health.app.R;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.DateUtils;
import health.app.Utilities.Preferences;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends BaseActivity {

    String deviceToken;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        Intent intent = new Intent(this, MyFirebaseInstanceIDService.class);
        startService(intent);
        if (deviceToken == null) {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
        }
        //Toast.makeText(getApplicationContext(),"deviceToken:"+deviceToken,Toast.LENGTH_SHORT).show();
        Preferences.getInstance().setDevice_token(deviceToken);
        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Preferences.getInstance().isLogIn()) {
                        startMain();

                    } else {
                        startLogin();
                    }

                }
            }, SPLASH_DISPLAY_LENGTH);
    }

    private void startMain() {
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")){
            Intent intent = new Intent(getApplicationContext(), TrainerDashboardActivity.class);
            startActivity(intent);
            this.finish();
        }
        else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")){
            Intent intent = new Intent(getApplicationContext(), CustomerDashboardActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    private void startLogin() {
        Intent intent = new Intent(getApplicationContext(), CommonLoginActivity.class);
        startActivity(intent);
        this.finish();
    }

}







