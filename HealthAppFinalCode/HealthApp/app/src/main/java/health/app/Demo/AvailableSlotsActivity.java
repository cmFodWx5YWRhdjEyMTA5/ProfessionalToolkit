package health.app.Demo;

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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Activities.CustomerDashboardActivity;
import health.app.Activities.TrainerDetailByIdActivity;
import health.app.Adapter.AvailableSlotAdapter;
import health.app.Model.AvailableSlot;
import health.app.R;
import health.app.Response.GetSlotResponse;
import health.app.Response.TrainerSlotsResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableSlotsActivity extends BaseActivity {
    AvailableSlotAdapter availableSlotAdapter;
    String format;
    String packageId,trainerId,noOfSlots;
    Date dateObj;
    ArrayList<String> myList=new ArrayList<>();
    public static TextView send;
    int slotsNo=0;
    String dateString;
    ArrayList<String> selectedDateTime;
    ArrayList<String> myArrayList;
    String timeDuration;
    public List<AvailableSlot> availableSlotList=new ArrayList<AvailableSlot>();
    public ArrayList<String> selectedStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_slots);
        ButterKnife.bind(this);
        selectedStart=new ArrayList<String>();
        availableSlotList.clear();
        selectedStart.clear();
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        send=(TextView) toolbar.findViewById(R.id.send);
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
        selectedDateTime=new ArrayList<>();
        dateObj = new Date(getIntent().getExtras().getLong("date", -1));
        timeDuration=getIntent().getExtras().getString("sessionTime");
        packageId=getIntent().getExtras().getString("packageId");
        trainerId=getIntent().getExtras().getString("trainerId");
        noOfSlots=getIntent().getExtras().getString("noOfSlots");
        slotsNo=Integer.parseInt(noOfSlots);
        selectedDateTime=getIntent().getStringArrayListExtra("dateList");
        myArrayList = new ArrayList<String>(new LinkedHashSet<String>(selectedDateTime));
        Collections.sort(myArrayList);
        Log.d("selectedDateTime","selectedDateTime"+myArrayList);
        Log.d("timeDuration","timeDuration"+Integer.valueOf(noOfSlots));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        CharSequence time = DateFormat.format("EEEE", dateObj.getTime());
        dateString=dateFormat.format(dateObj);
        format = formatter.format(dateObj);
        Log.d("dateString","dateString"+format);
        int interval=convertToMin(removeLastChar(timeDuration));
        getSlots(String.valueOf(interval));
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int i= ((AvailableSlotAdapter)availableSlotAdapter).selectedStrings.size();
                Log.d("pt","pt"+i);
                if(i == slotsNo){
               sendPackage(availableSlotList);
                }
                    else {
                        Snackbar snackbar = Snackbar.make(container, "Please select "+noOfSlots+" slot ", Snackbar.LENGTH_LONG);
                        snackbar.getView().setBackgroundColor(Color.RED);
                        snackbar.show();
                    }
            }
        });
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
                        for (int i=0;i<response.body().getMySlotsList().size();i++) {
                            AvailableSlot availableSlot = new AvailableSlot(response.body().getMySlotsList().get(i).getStart(), response.body().getMySlotsList().get(i).getEnd(),false);
                            availableSlotList.add(availableSlot);
                            myList.add(response.body().getMySlotsList().get(i).getStart());
                        }

                        availableSlotAdapter = new AvailableSlotAdapter(getApplicationContext(),availableSlotList, TrainerDetailByIdActivity.trainerDetails.getName(),TrainerDetailByIdActivity.trainerDetails.getImage(),format,noOfSlots);
                        slotRecyclerview.setItemAnimator(new DefaultItemAnimator());
                        slotRecyclerview.setAdapter(availableSlotAdapter);

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sendPackage(List<AvailableSlot> packageSlotsList) {
        JSONArray req = new JSONArray();
        Log.d("packageSlotsList","packageSlotsList"+packageSlotsList.size());
        for (int i = 0; i < packageSlotsList.size(); i++) {
            AvailableSlot singleStudent = packageSlotsList.get(i);
            if (singleStudent.isSelected()) {
                for (int k = 0; k < myArrayList.size(); k++) {
                    JSONObject reqObj = new JSONObject();
                    try {
                        reqObj.put("SessionDate", myArrayList.get(k));
                        reqObj.put("StartTime", singleStudent.getStartTime());
                        reqObj.put("EndTime", singleStudent.getEndTime());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    req.put(reqObj);
                    try {
                        Log.d("orderPackage", "orderPackage" + req.get(i).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //field.put("CustomerSessionSlots", String.valueOf(req));
                }
            }

        }


        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Adding Slots...");
        Log.d("packageSlotsList","packageSlotsList"+String.valueOf(req));
        healthAppService.sendPackage(Preferences.getInstance().getUserId(),packageId,trainerId,String.valueOf(req)).
                enqueue(new Callback<GetSlotResponse>() {
                    @Override
                    public void onResponse(Call<GetSlotResponse> call, final Response<GetSlotResponse> response) {
                        if (response.body().isStatus()) {
                            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(AvailableSlotsActivity.this);
                            alertDialogBuilder.setTitle(getString(R.string.app_name));
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setIcon(R.drawable.ptlogo);
                            alertDialogBuilder.setMessage(response.body().getMsg());
                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
//                                    for (int i = 0; i < response.body().getSlotDataList().size(); i++) {
//                                        AvailableSlot availableSlot = new AvailableSlot(response.body().getSlotDataList().get(i).getStartTime(),response.body().getSlotDataList().get(i).getEndTime(),containsSubString(response.body().getSlotDataList().get(i).getStartTime()));
//                                        availableSlotList.add(availableSlot);
//                                    }
//                                    availableSlotAdapter = new AvailableSlotAdapter(getApplicationContext(),availableSlotList, TrainerDetailByIdActivity.trainerDetails.getName(),TrainerDetailByIdActivity.trainerDetails.getImage(),format,noOfSlots);
//                                    slotRecyclerview.setItemAnimator(new DefaultItemAnimator());
//                                    slotRecyclerview.setAdapter(availableSlotAdapter);
                                    Intent intent = new Intent(getApplicationContext(), CustomerDashboardActivity.class);
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                    startActivity(intent);
                                    finish();

                                }
                            });

                            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {
                            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(AvailableSlotsActivity.this);
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
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<GetSlotResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public boolean containsSubString(String substring){
        for (String string : myList){
            if (string.contains(substring)){
                return true;
            }
        }
        return false;
    }



    @BindView(R.id.slot_recyclerview)
    RecyclerView slotRecyclerview;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    LinearLayout container;
}
