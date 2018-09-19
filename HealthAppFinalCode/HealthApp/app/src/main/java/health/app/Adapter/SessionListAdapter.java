package health.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import health.app.Activities.MySessionCalenderActivity;
import health.app.R;
import health.app.Response.TrainerDashboardListResponse;

/**
 * Created by Developer Six on 10/4/2017.
 */

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.ViewHolder> {
    private List<TrainerDashboardListResponse.ApprovedData.Packages> sessionsList;
    private Context context;
    String imageUrl,hours,minutes,hours1;
    String day,month,year;
    public SessionListAdapter(Context context, List<TrainerDashboardListResponse.ApprovedData.Packages> sessionsList, String imageUrl) {
        this.sessionsList = sessionsList;
        this.context = context;
        this.imageUrl=imageUrl;
    }

    @Override
    public SessionListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trainer_session_list, viewGroup, false);
        return new SessionListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionListAdapter.ViewHolder viewHolder, final int position) {
        TrainerDashboardListResponse.ApprovedData.Packages sessions=sessionsList.get(position);
        Typeface type1 = Typeface.createFromAsset(context.getAssets(),"fonts/Poppins-Regular.ttf");
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date date=(Date)formatter.parse(sessions.getSlotDate());
//            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
//            String dateString=dateFormat.format(date);
//            String[] array = dateString.split("\\s+");
//            day=array[1];
//            month=array[0];
//            year=array[2];
//            Log.d("array","array"+array[1]);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (sessions.getSessionStatus().equalsIgnoreCase("0"))
//        {
//            viewHolder.sessionStatus.setText("Pending");
//            viewHolder.sessionStatus.setTextColor(Color.RED);
//        }
//        else if (sessions.getSessionStatus().equalsIgnoreCase("1"))
//        {
//            viewHolder.sessionStatus.setText("Booked");
//            viewHolder.sessionStatus.setTextColor(Color.GREEN);
//        }
//        else if (sessions.getSessionStatus().equalsIgnoreCase("2"))
//        {
//            viewHolder.sessionStatus.setText("Complete");
//            viewHolder.sessionStatus.setTextColor(Color.BLUE);
//        }
//        viewHolder.sessionDay.setText(removeLastChar(day));
//        viewHolder.sessionMonth.setText(month);
//        viewHolder.sessionYear.setText(year);
//        viewHolder.sessionPrice.setText("$"+sessions.getSessionPrice());
//        viewHolder.sessionLocation.setText(sessions.getAddress());
//        viewHolder.sessionStartTime.setText(sessions.getStartTime());
//        viewHolder.sessionName.setTypeface(type1);
//        viewHolder.sessionName.setText(sessions.getSessionDuration());
        viewHolder.tvName.setText("Client Name:- "+sessions.getFirst_name()+" "+sessions.getLast_name());
        viewHolder.tvPhone.setText("Phone No:- "+sessions.getPhone());
        viewHolder.tvbookedSession.setVisibility(View.GONE);
        viewHolder.tvleftSession.setVisibility(View.GONE);
        setLength(sessions.getSessionTime(),viewHolder);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrainerDashboardListResponse.ApprovedData.Packages sessions1 = (TrainerDashboardListResponse.ApprovedData.Packages) sessionsList.get(position);
                Intent intent = new Intent(context,MySessionCalenderActivity.class);
                intent.putExtra(MySessionCalenderActivity.SESSION, sessions1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        Picasso.with(viewHolder.profileImage.getContext()).load(imageUrl+sessions.getProfile_image()).into(viewHolder.profileImage);
        ArrayList<String> items = new ArrayList(Arrays.asList(sessions.getSessionDays().split("\\s*,\\s*")));
        for (int i=0;i<items.size();i++)
        {
            if (items.get(i).equalsIgnoreCase("Sunday"))
            {
                viewHolder.tvSun.setBackgroundResource(R.drawable.circle_orange);
            }
            else if (items.get(i).equalsIgnoreCase("Monday"))
            {
                viewHolder.tvMon.setBackgroundResource(R.drawable.circle_orange);
            }
            else if (items.get(i).equalsIgnoreCase("Tuesday"))
            {
                viewHolder.tvTus.setBackgroundResource(R.drawable.circle_orange);
            }
            else if (items.get(i).equalsIgnoreCase("Wednesday"))
            {
                viewHolder.tvWed.setBackgroundResource(R.drawable.circle_orange);
            }
            else if (items.get(i).equalsIgnoreCase("Thursday"))
            {
                viewHolder.tvThu.setBackgroundResource(R.drawable.circle_orange);
            }
            else if (items.get(i).equalsIgnoreCase("Friday"))
            {
                viewHolder.tvFri.setBackgroundResource(R.drawable.circle_orange);
            }
            else if (items.get(i).equalsIgnoreCase("Saturday"))
            {
                viewHolder.tvSat.setBackgroundResource(R.drawable.circle_orange);

            }
    }}

    private void setLength(String selectDuration,ViewHolder viewHolder) {
        String[] splited = selectDuration.split(":");
        hours=splited[0];
        minutes=splited[1];
        if (hours.startsWith("0")) {
            Log.d("startsWith","startsWith"+hours.substring(1));
            hours1= hours.substring(1);
            if (hours1.equalsIgnoreCase("0")) {
                viewHolder.tvPrice.setText("Session Length:- "+minutes + " mins");
            }
            else {
                if (minutes.equalsIgnoreCase("00")) {
                    viewHolder.tvPrice.setText("Session Length:- "+hours1 + " hr");
                }
                else {
                    viewHolder.tvPrice.setText("Session Length:- "+hours1 + " hr " + minutes + " mins");
                }
            }
        }
        else {
            if (minutes.equalsIgnoreCase("00")) {
                Log.d("startsWith1","startsWith1"+"startsWith1");
                viewHolder.tvPrice.setText("Session Length:- "+hours + " hr");
            }
            else {
                viewHolder.tvPrice.setText("Session Length:- "+hours + " hr " + minutes + " mins");
            }
        }
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Override
    public int getItemCount() {
        return sessionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout view1,view2;
        CircleImageView profileImage;
        private TextView tvName,tvPhone,tvPrice,tvSun,tvMon,tvTus,tvWed,tvThu,tvFri,tvSat,tvleftSession,tvbookedSession;
        private TextView sessionName,sessionPrice,sessionLocation,sessionStatus,sessionStartTime,sessionDay,sessionMonth,sessionYear;
        public ViewHolder(View view) {
            super(view);
            view1=(LinearLayout)view.findViewById(R.id.view1);
            view2=(LinearLayout)view.findViewById(R.id.view2);
            sessionName = (TextView)view.findViewById(R.id.tv_session_name);
            sessionPrice = (TextView) view.findViewById(R.id.tv_session_price);
            sessionLocation=(TextView)view.findViewById(R.id.tv_session_location);
            sessionStatus = (TextView)view.findViewById(R.id.tv_session_status);
            sessionStartTime = (TextView)view.findViewById(R.id.tv_session_start_time);
            sessionDay = (TextView)view.findViewById(R.id.tv_day);
            sessionMonth = (TextView)view.findViewById(R.id.tv_month);
            sessionYear = (TextView)view.findViewById(R.id.tv_year);
            profileImage=(CircleImageView)view.findViewById(R.id.civ_profile);
            tvName=(TextView)view.findViewById(R.id.tv_name);
            tvPhone=(TextView)view.findViewById(R.id.tv_phone);
            tvPrice=(TextView)view.findViewById(R.id.tv_price);
            tvSun=(TextView)view.findViewById(R.id.tv_sun);
            tvMon=(TextView)view.findViewById(R.id.tv_mon);
            tvTus=(TextView)view.findViewById(R.id.tv_tus);
            tvWed=(TextView)view.findViewById(R.id.tv_wed);
            tvThu=(TextView)view.findViewById(R.id.tv_thu);
            tvFri=(TextView)view.findViewById(R.id.tv_fri);
            tvSat=(TextView)view.findViewById(R.id.tv_sat);
            tvleftSession=(TextView)view.findViewById(R.id.tv_leftSession);
            tvbookedSession=(TextView)view.findViewById(R.id.tv_bookedSession);
        }
    }

}