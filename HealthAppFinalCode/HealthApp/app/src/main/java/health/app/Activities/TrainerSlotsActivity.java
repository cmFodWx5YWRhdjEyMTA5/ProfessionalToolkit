package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.TrainerSlotsAdapter;
import health.app.Model.PackageSlots;
import health.app.Model.TimeModel;
import health.app.R;
import health.app.Response.GetSlotResponse;
import health.app.Response.TrainerSlotsResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TrainerSlotsActivity extends BaseActivity {
    String format;
    PackageSlots orderPackage;
    String packageId,trainerId,noOfSlots;
    Date dateObj;
    TrainerSlotsAdapter trainerSlotsAdapter;
    String dateString;
    String timeDuration;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_slots);
        ButterKnife.bind(this);
        dateObj = new Date(getIntent().getExtras().getLong("date", -1));
        timeDuration=getIntent().getExtras().getString("sessionTime");
        packageId=getIntent().getExtras().getString("packageId");
        trainerId=getIntent().getExtras().getString("trainerId");
        noOfSlots=getIntent().getExtras().getString("noOfSlots");
        Log.d("timeDuration","timeDuration"+Integer.valueOf(noOfSlots));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        CharSequence time = DateFormat.format("EEEE", dateObj.getTime());
        dateString=dateFormat.format(dateObj);
        format = formatter.format(dateObj);
        Log.d("dateString","dateString"+format);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Available Slots");
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
        int interval=convertToMin(removeLastChar(timeDuration));
        Log.d("interval","interval"+interval);
        getSlots(String.valueOf(interval));
    }

    public String removeLastChar(String time){
        String time1=time.substring(0, time.length() - 3);
        Log.d("time1","time1"+time1);
        return time1;
    }

    public int convertToMin(String hrmin) {
        String[] tokens = hrmin.split(":");
        int minutes = 0;
        for (int i = tokens.length; i > 0; i--) {
            int value = Integer.parseInt(tokens[i - 1]);
            if (i == 1) {
                minutes += 60 * value;
            }
            else {
                minutes += value;
            }
        }
        return minutes;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getSlots(String interval) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        slotRecyclerview.setLayoutManager(mLayoutManager);
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
       showProgressbar("Loading", "Please wait...");
        healthAppService.getSlots(interval).enqueue(new Callback<TrainerSlotsResponse>() {
            @Override
            public void onResponse(Call<TrainerSlotsResponse> call, Response<TrainerSlotsResponse> response) {
                if (response.body() != null) {
                    if (response.body().getMySlotsList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        slotRecyclerview.setVisibility(View.GONE);
                    }
                    else {
                        trainerSlotsAdapter = new TrainerSlotsAdapter(getApplicationContext(), response.body().getMySlotsList(),TrainerDetailByIdActivity.trainerDetails.getName(),TrainerDetailByIdActivity.trainerDetails.getImage(),format,noOfSlots);
                        slotRecyclerview.setItemAnimator(new DefaultItemAnimator());
                        slotRecyclerview.setAdapter(trainerSlotsAdapter);
                    }
                } else {
                    noRequestLayout.setVisibility(View.VISIBLE);
                    slotRecyclerview.setVisibility(View.GONE);
                }
                hideProgressBar();

            }

            @Override
            public void onFailure(Call<TrainerSlotsResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.send)
        {
            if (trainerSlotsAdapter.timeModels.size()!=0) {
                Log.d("timeModels","timeModels"+trainerSlotsAdapter.timeModels.size());
                if (trainerSlotsAdapter.slotList.size()==Integer.valueOf(noOfSlots))
                {
                    sendPackage(trainerSlotsAdapter.timeModels);
                }
                else if (trainerSlotsAdapter.slotList.size()<Integer.valueOf(noOfSlots)){
                    Snackbar snackbar = Snackbar.make(container, "Please select "+noOfSlots+" slot.", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(Color.RED);
                    snackbar.show();
                }
                else if (trainerSlotsAdapter.slotList.size()>Integer.valueOf(noOfSlots)){
                    Snackbar snackbar = Snackbar.make(container, "Please select "+noOfSlots+" slot.", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(Color.RED);
                    snackbar.show();
                }
            }
            else {
                Snackbar snackbar = Snackbar.make(container, "Please select slot.", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(Color.RED);
                snackbar.show();
                //Toast.makeText(getApplicationContext(), "Please select day first", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
    public RequestBody requestBody(String name) {
        return RequestBody.create(MediaType.parse("text/plain"), name);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sendPackage(List<TimeModel> packageSlotsList) {
        if(packageSlotsList.size()!=0) {
            JSONArray req = new JSONArray();
            final Map<String, String> field = new HashMap<String, String>();
            Log.d("packageSlotsList","packageSlotsList"+packageSlotsList.size());
            for (int i=0;i<packageSlotsList.size();i++)
            {
                JSONObject reqObj = new JSONObject();
                try {
                    reqObj.put("SessionDate", packageSlotsList.get(i).getDate());
                    reqObj.put("StartTime", packageSlotsList.get(i).getStart());
                    reqObj.put("EndTime", packageSlotsList.get(i).getEnd());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                req.put(reqObj);
                try {
                    Log.d("orderPackage","orderPackage"+req.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //field.put("CustomerSessionSlots", String.valueOf(req));
            }
            final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
            showProgressbar("Loading", "Adding Slots...");
            healthAppService.sendPackage(Preferences.getInstance().getUserId(),packageId,trainerId,String.valueOf(req)).
                    enqueue(new Callback<GetSlotResponse>() {
                        @Override
                        public void onResponse(Call<GetSlotResponse> call, final Response<GetSlotResponse> response) {
                            if (response.body().isStatus()) {
                                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(TrainerSlotsActivity.this);
                                alertDialogBuilder.setTitle(getString(R.string.app_name));
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setIcon(R.drawable.ptlogo);
                                alertDialogBuilder.setMessage(response.body().getMsg());
                                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(getApplicationContext(), CustomerDashboardActivity.class);
                                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                        startActivity(intent);
                                        finish();

                                    }
                                });

                                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            } else {
                                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(TrainerSlotsActivity.this);
                                alertDialogBuilder.setTitle(getString(R.string.app_name));
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setIcon(R.drawable.ptlogo);
                                alertDialogBuilder.setMessage(response.body().getMsg());
                                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });

                                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                            hideProgressBar();
                        }

                        @Override
                        public void onFailure(Call<GetSlotResponse> call, Throwable t) {
                            hideProgressBar();
                            Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        }
                    });

        }}

    @BindView(R.id.slot_recyclerview)
    RecyclerView slotRecyclerview;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    RelativeLayout container;
}


