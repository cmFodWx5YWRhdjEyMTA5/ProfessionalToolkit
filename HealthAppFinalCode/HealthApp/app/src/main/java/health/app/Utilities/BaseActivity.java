package health.app.Utilities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import health.app.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Developer Six on 8/5/2017.
 */

public class BaseActivity extends AppCompatActivity {

    public ProgressDialog progressDialog;
    public AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        progressDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        alertDialog = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle)
                .setTitle(getString(R.string.app_name))
                .setIcon(R.drawable.ptlogo)
                .setMessage(getString(R.string.host_internet))

                .setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!isInternetAvailable()) {
                            showErrorDialog();
                        }
                    }
                })
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showProgressbar(String title, String message) {

        if (!progressDialog.isShowing())
            progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgressBar() {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInternetAvailable()) {
            showErrorDialog();
        }
    }

    public void showErrorDialog() {
        if (!isFinishing()) {
            alertDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}

