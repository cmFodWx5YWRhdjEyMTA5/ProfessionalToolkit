package health.app.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import health.app.Activities.CancelSlotActivity;
import health.app.Activities.CustomerDashboardActivity;
import health.app.Activities.ReceivedRequestActivity;
import health.app.Activities.TrainerDashboardActivity;
import health.app.Fragments.MyCompleteSessionFragment;
import health.app.Fragments.MyMessagesFragment;
import health.app.Fragments.MySessionFragment;
import health.app.Fragments.MyTrainerFragment;
import health.app.Fragments.NotificationFragment;
import health.app.Model.DateItem;
import health.app.Model.GeneralItem;
import health.app.Model.ListItem;
import health.app.Model.PojoOfJsonArray;
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

/**
 * Created by Developer Six on 9/13/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    String userType;
    TrainerDashboardActivity trainerDashboardActivity;
    CustomerDashboardActivity customerDashboardActivity;
    NotificationFragment notificationFragment;
    List<ListItem> consolidatedList = new ArrayList<>();

    public NotificationAdapter(Context context, List<ListItem> consolidatedList, NotificationFragment notificationFragment, String userType, TrainerDashboardActivity trainerDashboardActivity) {
        this.mContext = context;
        this.consolidatedList = consolidatedList;
        this.notificationFragment=notificationFragment;
        this.userType=userType;
        this.trainerDashboardActivity=trainerDashboardActivity;
    }

    public NotificationAdapter(Context context, List<ListItem> consolidatedList, NotificationFragment notificationFragment, String userType, CustomerDashboardActivity customerDashboardActivity) {
        this.mContext = context;
        this.consolidatedList = consolidatedList;
        this.notificationFragment=notificationFragment;
        this.userType=userType;
        this.customerDashboardActivity = customerDashboardActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case ListItem.TYPE_GENERAL:
                View v1 = inflater.inflate(R.layout.item_custom_row_layout, parent,
                        false);
                viewHolder = new GeneralViewHolder(v1);
                break;

            case ListItem.TYPE_DATE:
                View v2 = inflater.inflate(R.layout.date_header_layout, parent, false);
                viewHolder = new DateViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case ListItem.TYPE_GENERAL:
                final GeneralItem generalItem = (GeneralItem) consolidatedList.get(position);
                GeneralViewHolder generalViewHolder= (GeneralViewHolder) viewHolder;
                Typeface type = Typeface.createFromAsset(mContext.getAssets(),"fonts/Poppins-Medium.ttf");
                generalViewHolder.itemLabel.setText(generalItem.getPojoOfJsonArray().getName());
                generalViewHolder.itemLabel.setTypeface(type);
                if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("Start_Session")) {
                    generalViewHolder.acceptComplete.setVisibility(View.GONE);
                    generalViewHolder.acceptStart.setVisibility(View.VISIBLE);
                }
                else if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("session_complete"))
                {
                    generalViewHolder.acceptStart.setVisibility(View.GONE);
                    generalViewHolder.acceptComplete.setVisibility(View.VISIBLE);
                }
                else {
                    generalViewHolder.acceptStart.setVisibility(View.GONE);
                    generalViewHolder.acceptComplete.setVisibility(View.GONE);
                }
                generalViewHolder.itemLabelMsg.setText(generalItem.getPojoOfJsonArray().getMsg());
                generalViewHolder.acceptStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                        alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setIcon(R.drawable.ptlogo);
                        alertDialogBuilder.setMessage("Are you sure you want to accept this request?");
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                acceptRequest(generalItem.getPojoOfJsonArray());
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
                generalViewHolder.acceptComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                        alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setIcon(R.drawable.ptlogo);
                        alertDialogBuilder.setMessage("Are you sure you want to accept this request?");
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                completeRequest(generalItem.getPojoOfJsonArray());
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
                Picasso.with(mContext).load(generalItem.getPojoOfJsonArray().getImage()).into(generalViewHolder.image);
                generalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (userType.equalsIgnoreCase("Customer")) {
                            if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("send_message")) {
                                customerDashboardActivity.replaceFragment(new MyMessagesFragment(), "My Messages");
                                customerDashboardActivity.bottomNavigationView.setVisibility(View.GONE);
                            }
                            else if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("accept_request"))
                            {
                                customerDashboardActivity.replaceFragment(new MyTrainerFragment(), "My Trainer");
                                customerDashboardActivity.bottomNavigationView.getMenu().findItem(R.id.action_item3).setChecked(true);
                            }
                            else if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("reject_request"))
                            {
                                customerDashboardActivity.replaceFragment(new MyTrainerFragment(), "My Trainer");
                                customerDashboardActivity.bottomNavigationView.getMenu().findItem(R.id.action_item3).setChecked(true);
                            }
                            else if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("session_complete"))
                            {
                                customerDashboardActivity.replaceFragment(new MyTrainerFragment(), "My Trainer");
                                customerDashboardActivity.bottomNavigationView.getMenu().findItem(R.id.action_item3).setChecked(true);
                            }
                            else if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("session_slot_cancel"))
                            {
                                Intent intent=new Intent(mContext,CancelSlotActivity.class);
                                intent.putExtra("SessionSlotId",generalItem.getPojoOfJsonArray().getSessionSlotId());
                                mContext.startActivity(intent);
                            }
                        }
                        else {
                            if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("send_message")) {
                                trainerDashboardActivity.replaceFragment(new MyMessagesFragment(),"My Messages");
                                trainerDashboardActivity.bottomNavigationView.setVisibility(View.GONE);
                            }
                            else if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("send_request"))
                            {
                                Intent intent=new Intent(mContext,ReceivedRequestActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(intent);
//                                trainerDashboardActivity.replaceFragment(new ReceivedRequestFragment(),"Received Request");
//                                trainerDashboardActivity.bottomNavigationView.getMenu().findItem(R.id.action_item2).setChecked(true);
                            }
                            else if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("add_session_slot_after_cancel"))
                            {
                                trainerDashboardActivity.replaceFragment(new MySessionFragment(),"My Session");
                                trainerDashboardActivity.bottomNavigationView.setVisibility(View.GONE);
                            }
                            else if (generalItem.getPojoOfJsonArray().getnType().equalsIgnoreCase("session_complete_accept"))
                            {
                                trainerDashboardActivity.replaceFragment(new MyCompleteSessionFragment(),"Complete Session");
                                trainerDashboardActivity.bottomNavigationView.setVisibility(View.GONE);
                            }
                        }
                    }
                });
                break;

            case ListItem.TYPE_DATE:
                DateItem dateItem = (DateItem) consolidatedList.get(position);
                DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;
                String dateString = dateItem.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date convertedDate = new Date();
                try {
                    convertedDate = dateFormat.parse(dateString);
                    Log.d("convertedDate","convertedDate"+convertedDate);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Typeface type1 = Typeface.createFromAsset(mContext.getAssets(),"fonts/Poppins-Bold.ttf");
                DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d;
                Long sec= Long.valueOf(0);
                try {
                    if (dateString!=null){
                        d = f.parse(dateString);
                        sec=d.getTime();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("Eror",e+"");
                }
               // String dayStatus= getDaysAgo(convertedDate);
                String dayStatus= getTimeAgo(sec,mContext);
                Log.d("dayStatus","dayStatus"+dayStatus);
                if(dayStatus!=null){
                    dateViewHolder.txtTitle.setText(dayStatus);
                    dateViewHolder.txtTitle.setTypeface(type1);
                }
                else {
                    dateViewHolder.txtTitle.setText(dateItem.getDate());
                    dateViewHolder.txtTitle.setTypeface(type1);
                }
                break;
        }
    }

    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
            Log.e("time","time: "+time);
        }

        long now = System.currentTimeMillis();
        Log.e("time", "now: " + now + " time: "+time);
        if (time > now || time <= 0) {
            return "just now";
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else
        {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void acceptRequest(final PojoOfJsonArray item) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) mContext).showProgressbar("Loading", "Please wait....");
        healthAppService.startRequest(Preferences.getInstance().getUserId(),item.getRequestId(), item.getSessionSlotId(),item.getnId(),"1",item.getTrainerId()).enqueue(new Callback<NewMessageResponse>() {
            @Override
            public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(mContext,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(mContext,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) mContext).hideProgressBar();
            }

            @Override
            public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                ((BaseActivity) mContext).hideProgressBar();
                Toast.makeText(mContext, HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void completeRequest(final PojoOfJsonArray item) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) mContext).showProgressbar("Loading", "Please wait....");
        healthAppService.completeRequest(Preferences.getInstance().getUserId(),item.getRequestId(), item.getSessionSlotId(),item.getnId(),"2",item.getTrainerId()).enqueue(new Callback<NewMessageResponse>() {
            @Override
            public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(mContext,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(mContext,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) mContext).hideProgressBar();
            }

            @Override
            public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                ((BaseActivity) mContext).hideProgressBar();
                Toast.makeText(mContext, HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // ViewHolder for date row item
    class DateViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtTitle;

        public DateViewHolder(View v) {
            super(v);
            this.txtTitle = (TextView) v.findViewById(R.id.txt);

        }
    }

    // View holder for general row item
    class GeneralViewHolder extends RecyclerView.ViewHolder {
        protected TextView itemLabel,itemLabelMsg;
        ImageView image;
        Button acceptStart,acceptComplete;
        public GeneralViewHolder(View v) {
            super(v);
            this.itemLabel = (TextView) v.findViewById(R.id.item_label);
            this.itemLabelMsg = (TextView) v.findViewById(R.id.item_label_meg);
            this.image = (ImageView) v.findViewById(R.id.profile_image);
            this.acceptComplete = (Button) v.findViewById(R.id.accept_complete);
            this.acceptStart = (Button) v.findViewById(R.id.accept_start);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return consolidatedList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return consolidatedList != null ? consolidatedList.size() : 0;
    }

    private String getDaysAgo(Date date){
        long days = (new Date().getTime() - date.getTime()) / 86400000;
        Log.d("ago","ago"+days);
        if(days == 0) return "Today";
        else if(days == 1) return "Yesterday";
        else return days + " days ago";

    }

}

