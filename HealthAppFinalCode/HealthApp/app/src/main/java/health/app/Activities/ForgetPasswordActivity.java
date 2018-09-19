package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.R;
import health.app.Response.ForgotPasswordResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.ValidationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgetPasswordActivity extends BaseActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        final Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins-SemiBold.ttf");
        btLogin.setTypeface(font);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Forgot Password");
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
    }

    private boolean valid(){
        if(etEmail.getText().toString().length()<=0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (!ValidationUtils.isValidEmail(etEmail.getText())) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Valid Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void submitEmail(){
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
            showProgressbar("Loading", "Sending request...");
            healthAppService.forgetPassword(etEmail.getText().toString()).
                    enqueue(new Callback<ForgotPasswordResponse>() {
                        @Override
                        public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                            alertDialog.dismiss();
                            if (response.body() != null) {
                                if (response.body().isStatus()){
                                    alertUser(response.body().getMsg());
                                }
                                else {
                                  alertUser(response.body().getMsg());
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }

                            hideProgressBar();
                        }

                        @Override
                        public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                            hideProgressBar();
                            Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    private void alertUser(String msg) {
        if (msg!=null){
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(ForgetPasswordActivity.this);
            alertDialogBuilder.setTitle(getString(R.string.app_name));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ptlogo);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(),CommonLoginActivity.class));
                    finish();

                }
            });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


    @BindView(R.id.input_email)
    EditText etEmail;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_login)
    void userLogin(){
        if (valid()){
            submitEmail();
        }
    }

    @BindView(R.id.bt_login)
    Button btLogin;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
}
