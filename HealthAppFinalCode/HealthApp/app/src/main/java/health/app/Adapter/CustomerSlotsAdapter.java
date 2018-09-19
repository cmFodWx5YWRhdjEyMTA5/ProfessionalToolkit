package health.app.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import health.app.Activities.CustomerDashboardActivity;
import health.app.R;
import health.app.Response.NewMessageResponse;
import health.app.Response.TimeSlotByDateResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Developer Six on 8/29/2017.
 */

public class CustomerSlotsAdapter extends RecyclerView.Adapter<CustomerSlotsAdapter.MyViewHolder> {

    private List<TimeSlotByDateResponse.DataResponse.TimeSlotByDate> trainerSlotLists;
    Context context;
    Date date1;
    Date slotTime,todayTime;
String userId;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvLocation,tvTime,tvDate,tvDescription;
        Button btRequest,btRequested;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvLocation = (TextView) view.findViewById(R.id.tv_location);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvDate= (TextView) view.findViewById(R.id.tv_date);
            tvDescription=(TextView) view.findViewById(R.id.tv_description);
            btRequest=(Button) view.findViewById(R.id.bt_request);
            btRequested=(Button)view.findViewById(R.id.bt_requested);
        }
    }


    public CustomerSlotsAdapter(Context context,List<TimeSlotByDateResponse.DataResponse.TimeSlotByDate> trainerSlotLists,String userId) {
        this.context=context;
        this.trainerSlotLists = trainerSlotLists;
        this.userId = userId;
    }

    @Override
    public CustomerSlotsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calender_list_item, parent, false);

        return new CustomerSlotsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomerSlotsAdapter.MyViewHolder holder, final int position) {
        final TimeSlotByDateResponse.DataResponse.TimeSlotByDate trainerSlotList1 = trainerSlotLists.get(position);
        holder.tvTitle.setText(trainerSlotList1.getSlotTitle());
        holder.tvLocation.setText(trainerSlotList1.getAddress());
        holder.tvTime.setText("("+trainerSlotList1.getStartTime()+" - "+trainerSlotList1.getEndTime()+")");
        holder.tvDate.setText(trainerSlotList1.getSlotDate());
        holder.tvDescription.setText(trainerSlotList1.getSlotDescription());
        if (trainerSlotList1.getRequestId().equals("none"))
        {
            holder.btRequest.setVisibility(View.VISIBLE);
            holder.btRequested.setVisibility(View.GONE);
        }
        else {
            holder.btRequest.setVisibility(View.GONE);
            holder.btRequested.setVisibility(View.VISIBLE);
            holder.btRequested.setClickable(false);
        }
        String dateString = trainerSlotList1.getSlotDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = Calendar.getInstance().getTime();
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean getToday=health.app.Utilities.DateUtils.isToday(convertedDate);
        if (getToday){
            Date dt = new Date();
            int hours = dt.getHours();
            int minutes = dt.getMinutes();
            int seconds = dt.getSeconds();
            String curTime = hours + ":" + minutes + ":" + seconds;
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");
            Date convertedDate1=null,convertedDate2=null;
            try {
                convertedDate1 = dateFormat1.parse(curTime);
                convertedDate2=dateFormat1.parse(trainerSlotList1.getStartTime());
                Log.d("Today","Today"+convertedDate1);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (convertedDate1.after(convertedDate2))
            {
                holder.btRequest.setVisibility(View.GONE);
                holder.btRequested.setVisibility(View.GONE);
            }
        }
        boolean getResult=health.app.Utilities.DateUtils.isBeforeDay(convertedDate,currentTime);
        if (getResult)
        {
            holder.btRequest.setVisibility(View.GONE);
            holder.btRequested.setVisibility(View.GONE);
        }
        else {
        }

        Log.d("getToday","getToday"+getToday);

        holder.btRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(context.getString(R.string.app_name));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.ptlogo);
                alertDialogBuilder.setMessage("Are you sure you want to send request for this time slot?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendReguest(trainerSlotList1);
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
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sendReguest(final TimeSlotByDateResponse.DataResponse.TimeSlotByDate item) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        healthAppService.sendRequest(Preferences.getInstance().getUserId(), item.getSlotId(),item.getTrainerId()).enqueue(new Callback<NewMessageResponse>() {
            @Override
            public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Health App");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setIcon(R.drawable.ptlogo);
                    alertDialogBuilder.setMessage(response.body().getMsg());
                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(context,CustomerDashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });

                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else{
                    final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Health App");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setIcon(R.drawable.ptlogo);
                    alertDialogBuilder.setMessage(response.body().getMsg());
                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(context,CustomerDashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });

                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return trainerSlotLists.size();
    }
}

