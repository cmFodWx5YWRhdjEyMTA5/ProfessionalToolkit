package health.app.Activities;

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
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Demo.NumbersAdapter;
import health.app.Demo.Student;
import health.app.R;
import health.app.Response.CancelSessionSlot;
import health.app.Response.NewMessageResponse;
import health.app.Response.TrainerSlotsResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelSlotActivity extends BaseActivity {
     String sessionSlotId;
    public static TextView send;
    List<Student> stList;
    int slotsNo=0;
    String dayOfWeek;
    String startTime,endTime;
    int seletedSlots;
    String sessionDate,packageId,trainerId,requestId,sessionSlotId1;
    private List<Student> studentList=new ArrayList<Student>();
    NumbersAdapter numbersAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_slot);
        ButterKnife.bind(this);
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        send=(TextView) toolbar.findViewById(R.id.send);
        title.setText("Session Slots");
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
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stList = ((NumbersAdapter) numbersAdapter).getStudentist();
                if (((NumbersAdapter) numbersAdapter).selectedStrings.size()!=0) {
                    Log.d("listsize", "listsize" + ((NumbersAdapter) numbersAdapter).selectedStrings.size());
                    seletedSlots=((NumbersAdapter) numbersAdapter).selectedStrings.size();
                    //if (seletedSlots!=0 && seletedSlots==slotsNo) {
                        if (seletedSlots!=0) {
                        sendPackage(stList);
                    }
                    else {
                        Snackbar snackbar = Snackbar.make(container, "Please select slot ", Snackbar.LENGTH_LONG);
                        snackbar.getView().setBackgroundColor(Color.RED);
                        snackbar.show();
                    }
                }

            }

        });
        sessionSlotId=getIntent().getExtras().getString("SessionSlotId");
        getSlotById(sessionSlotId);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getSlotById(final String sessionSlotId) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getSlotById(sessionSlotId,Preferences.getInstance().getUserId()).enqueue(new Callback<CancelSessionSlot>() {
            @Override
            public void onResponse(Call<CancelSessionSlot> call, Response<CancelSessionSlot> response) {
                if (response.body().isStatus()) {
                    sessionDate=response.body().getSessionSlotData().getSessionDate();
                    packageId=response.body().getSessionSlotData().getPackageId();
                    requestId=response.body().getSessionSlotData().getRequestId();
                    trainerId=response.body().getSessionSlotData().getTrainerId();
                    sessionSlotId1=response.body().getSessionSlotData().getSessionSlotId();
                    SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat writeFormat = new SimpleDateFormat("MMM dd,yyyy");
                    Date date = null;
                    try {
                        date = readFormat.parse(response.body().getSessionSlotData().getSessionDate());
                        dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    getdifference(response.body().getSessionSlotData().getStartTime(),response.body().getSessionSlotData().getEndTime(),response.body().getSessionSlotData().getSessionDate(),response.body().getSessionSlotData().getFirstName()+" "+response.body().getSessionSlotData().getLastName(),response.body().getSessionSlotData().getBaseUrl()+response.body().getSessionSlotData().getProfileImage(),response.body().getSessionSlotData().getCancelSlotList());
                    final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(CancelSlotActivity.this);
                    alertDialogBuilder.setTitle(getString(R.string.app_name));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setIcon(R.drawable.ptlogo);
                    alertDialogBuilder.setMessage("Your Session "+response.body().getSessionSlotData().getStartTime()+"-"+response.body().getSessionSlotData().getEndTime()+" on "+dayOfWeek+", "+writeFormat.format(date)+" has been deleted by "+response.body().getSessionSlotData().getFirstName()+" "+response.body().getSessionSlotData().getLastName()+".Please select another slot for this date");
                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(CancelSlotActivity.this);
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
            public void onFailure(Call<CancelSessionSlot> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getdifference(String startTime, String endTime, String sessionDate, String name, String profile, List<CancelSessionSlot.SessionSlotsData.CancelSlot> cancelSlotList) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date1=null;Date date2=null;
        try {
            date1 = simpleDateFormat.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = date2.getTime() - date1.getTime();
        int days = (int) (difference / (1000*60*60*24));
        int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        hours = (hours < 0 ? -hours : hours);
        int minutes=hours*60;
        getSlots(String.valueOf(minutes),sessionDate,name,profile,cancelSlotList);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getSlots(final String interval, final String sessionDate, final String name, final String profile, final List<CancelSessionSlot.SessionSlotsData.CancelSlot> cancelSlotList) {
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
                            Student st = new Student(response.body().getMySlotsList().get(i).getStart(), response.body().getMySlotsList().get(i).getEnd(), false);
                            studentList.add(st);
                        }

                        numbersAdapter = new NumbersAdapter(getApplicationContext(),studentList,name,profile,sessionDate,"");
                        slotRecyclerview.setItemAnimator(new DefaultItemAnimator());
                        slotRecyclerview.setAdapter(numbersAdapter);

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
    private void sendPackage(List<Student> packageSlotsList) {
        Log.d("packageSlotsList","packageSlotsList"+packageSlotsList.size());
        for (int i = 0; i < packageSlotsList.size(); i++) {
            Student singleStudent = packageSlotsList.get(i);
            if (singleStudent.isSelected() == true) {
                         startTime=singleStudent.getStartTime();
                         endTime=singleStudent.getEndTime();
                    }
        }


        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Adding Slots...");
        healthAppService.addNewSlots(endTime,packageId,requestId,sessionDate,sessionSlotId1,startTime,trainerId,Preferences.getInstance().getUserId()).
                enqueue(new Callback<NewMessageResponse>() {
                    @Override
                    public void onResponse(Call<NewMessageResponse> call, final Response<NewMessageResponse> response) {
                        if (response.body().isStatus()) {
                            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(CancelSlotActivity.this);
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
                            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(CancelSlotActivity.this);
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
                    public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });

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


//