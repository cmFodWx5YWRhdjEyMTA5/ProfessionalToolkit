package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import health.app.AddPackage.AddDurationActivity;
import health.app.Fragments.MyCompleteSessionFragment;
import health.app.Fragments.MyCustomerFragment;
import health.app.Fragments.MyInboxFragment;
import health.app.Fragments.MyMessagesFragment;
import health.app.Fragments.MySessionFragment;
import health.app.Fragments.NotificationFragment;
import health.app.Fragments.ReportsFragment;
import health.app.Fragments.TDashboardAgainFragment;
import health.app.Fragments.TrainerChangePasswordFragment;
import health.app.R;
import health.app.Response.NewMessageResponse;
import health.app.Response.ProfileDataResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.BottomNavigationViewHelper;
import health.app.Utilities.FragmentChangeListener;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TrainerDashboardActivity extends BaseActivity implements FragmentChangeListener, NavigationView.OnNavigationItemSelectedListener,TrainerChangePasswordFragment.OnFragmentInteractionListener,TDashboardAgainFragment.OnFragmentInteractionListener,MyInboxFragment.OnFragmentInteractionListener,ReportsFragment.OnFragmentInteractionListener,MySessionFragment.OnFragmentInteractionListener,MyMessagesFragment.OnFragmentInteractionListener,NotificationFragment.OnFragmentInteractionListener {

    public static TextView title;
    public static Toolbar toolbar;
    CircleImageView dashboardImage;
    LinearLayout dashboardHeader;
    TextView dashboardName;

    public static BottomNavigationView bottomNavigationView;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_dashboard);
        ButterKnife.bind(this);
        String type = getIntent().getStringExtra("notificationTrainer");
        String sessionSlotId = getIntent().getStringExtra("SessionSlotId");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        double percentageM=495/(1.0324-0.19077*(Math.log10(90-50))+0.15456*(Math.log10(180)))-450;
        double percentageF=495/(1.29579-0.35004*(Math.log10(90+90-50))+0.22100*(Math.log10(180)))-450;
        Log.d("percentageM","percentageM"+percentageM);
        Log.d("percentageF","percentageF"+percentageF);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);
        toolbar=(Toolbar)findViewById (R.id.toolbar_dashboard);
        title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Dashboard");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainLayout.setTranslationX(slideOffset * drawerView.getWidth());
                drawer.bringChildToFront(drawerView);
                drawer.requestLayout();
            }
        };
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.menu, this.getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {

                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        dashboardHeader=(LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.dashboard_header);
        dashboardImage = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profile1);
        dashboardName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        dashboardHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrainerProfile1Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                Class fragmentClass1 = null;
                switch (item.getItemId()) {
                    case R.id.action_item1:
                        fragmentClass1 = TDashboardAgainFragment.class;
                        title.setText("Dashboard");
                        break;
                    case R.id.action_item2:
                        fragmentClass1 = MyCompleteSessionFragment.class;
                        title.setText("Completed Session");
                        break;
                    case R.id.action_item3:
                        fragmentClass1 = MyCustomerFragment.class;
                        title.setText("My Client");
                        break;
                    case R.id.action_item4:
                        fragmentClass1 = ReportsFragment.class;
                        title.setText("Reports");
                }
                try {
                    selectedFragment = (Fragment) fragmentClass1.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent1, selectedFragment).commit();
                return true;
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent1, new TDashboardAgainFragment()).commit();
        }
        Log.d("type","type"+type);
        if (type != null) {
            switch (type) {
                case "send_message":
                    replaceFragment(new MyMessagesFragment(),"My Message");
                    break;
                case "send_request":
                    startActivity(new Intent(getApplicationContext(),ReceivedRequestActivity.class));
                    break;
                case "accept_request":
                    startActivity(new Intent(getApplicationContext(),ReceivedRequestActivity.class));
                    break;
                case "reject_request":
                    startActivity(new Intent(getApplicationContext(),ReceivedRequestActivity.class));
                    break;
                case "session_complete":
                    replaceFragment(new MySessionFragment(),"My Session");
                    break;
                case "session_slot_cancel":
                    replaceFragment(new MySessionFragment(),"My Session");
                    break;
                case "add_session_slot_after_cancel":
                    replaceFragment(new MySessionFragment(),"My Session");
                    break;
            }
        }
        getTrainerData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getTrainerData() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getTrainerData(Preferences.getInstance().getUserId()).enqueue(new Callback<ProfileDataResponse>() {
            @Override
            public void onResponse(Call<ProfileDataResponse> call, Response<ProfileDataResponse> response) {
                if (response.body().isStatus() && response.body().getProfileData()!=null) {
                    Preferences.getInstance().setUserPhotoPath(response.body().getProfileData().getUser().getProfile_image_path());
                    Picasso.with(dashboardImage.getContext()).load(response.body().getProfileData().getUser().getProfile_image()).into(dashboardImage);
                    dashboardName.setText(response.body().getProfileData().getUser().getFirst_name()+" "+response.body().getProfileData().getUser().getLast_name());

                }
                else {
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();

            }

            @Override
            public void onFailure(Call<ProfileDataResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(TrainerDashboardActivity.this);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void logoutApp() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Logging Out", "Please wait...");
        healthAppService.logoutUser(Preferences.getInstance().getUserId(),Preferences.getInstance().getDevice_token(),"android_token").
                enqueue(new Callback<NewMessageResponse>() {
                    @Override
                    public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                        alertDialog.dismiss();
                        if (response.body().isStatus()){
                            Intent intent = new Intent(getApplicationContext(), CommonLoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }
                        else {
                            alertUser(response.body().getMsg());
                        }


                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                        hideProgressBar();
                    }
                });
    }

    private void alertUser(String msg) {
        if (msg!=null){
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(TrainerDashboardActivity.this);
            alertDialogBuilder.setTitle(getString(R.string.app_name));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ptlogo);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @OnClick(R.id.nav_logout)
    void doLogout(){
        logout();
    }

    public void logout(){
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(TrainerDashboardActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setIcon(R.drawable.ptlogo);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logoutApp();
                Preferences.getInstance().setLogIn(false);
                Preferences.getInstance().clearUserDetails();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                drawer.closeDrawer(GravityCompat.START);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.trainer_profile) {
            startActivity(new Intent(getApplicationContext(),TrainerProfile1Activity.class));
            drawer.closeDrawer(GravityCompat.START);
        }

        else if (id == R.id.trainer_dashboard) {
            fragmentClass = TDashboardAgainFragment.class;
            title.setText("Dashboard");
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.action_item1).setChecked(true);
        }
        else if (id == R.id.trainer_addpackage)
        {
            startActivity(new Intent(getApplicationContext(),AddDurationActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.trainer_package)
        {
            startActivity(new Intent(getApplicationContext(),TrainerPackageActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.trainer_request) {
            startActivity(new Intent(getApplicationContext(),ReceivedRequestActivity.class));
            drawer.closeDrawer(GravityCompat.START);
//            fragmentClass = ReceivedRequestFragment.class;
//            title.setText("Received Request");
//            bottomNavigationView.setVisibility(View.GONE);
        }
        else if (id == R.id.trainer_message) {
            fragmentClass = MyInboxFragment.class;
            title.setText("My Messages");
            bottomNavigationView.setVisibility(View.GONE);
        }
        else if (id == R.id.trainer_notifications) {
            fragmentClass = NotificationFragment.class;
            title.setText("Notification");
            bottomNavigationView.setVisibility(View.GONE);
        }
        else if (id == R.id.trainer_change_password) {
            fragmentClass = TrainerChangePasswordFragment.class;
            title.setText("Change Password");
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.action_item4).setChecked(true);
        }
        if (fragmentClass!=null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent1, fragment).commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @BindView(R.id.main_layout_trainer)
    RelativeLayout mainLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @Override
    public void replaceFragment(Fragment fragment, String fragmentTitle) {
        FragmentManager fragmentManager = getSupportFragmentManager();;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent1, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
        title.setText(fragmentTitle);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        getTrainerData();
    }
}

