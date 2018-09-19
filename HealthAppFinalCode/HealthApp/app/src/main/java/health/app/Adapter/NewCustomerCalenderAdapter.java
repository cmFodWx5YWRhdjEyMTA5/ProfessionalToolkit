package health.app.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import health.app.R;
import health.app.Response.SessionListResponse;

/**
 * Created by Developer Six on 10/6/2017.
 */

public class NewCustomerCalenderAdapter extends RecyclerView.Adapter<NewCustomerCalenderAdapter.ViewHolder> {
    private List<SessionListResponse.SessionData.Sessions> sessionsList;
    private Context context;
    String day,month,year;
    public NewCustomerCalenderAdapter(Context context, List<SessionListResponse.SessionData.Sessions> sessionsList) {
        this.sessionsList = sessionsList;
        this.context = context;
    }

    @Override
    public NewCustomerCalenderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calender_list_new, viewGroup, false);
        return new NewCustomerCalenderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewCustomerCalenderAdapter.ViewHolder viewHolder, final int i) {
        SessionListResponse.SessionData.Sessions sessions=sessionsList.get(i);
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
        viewHolder.tvDay.setText(removeLastChar(day));
        viewHolder.tvMonth.setText(month);
        viewHolder.tvYear.setText(year);
        viewHolder.tvHeight.setText(sessions.getSessionTime());
        viewHolder.tvWaist.setText(sessions.getSessionPrice());
//        viewHolder.tvWeight.setText(sessions.getAddress());
//        viewHolder.tvNeck.setText(sessions.getStartTime());
        if(sessions.getIsOccupied().equalsIgnoreCase("0"))
        {
            viewHolder.tvHip.setText("Pending");
            viewHolder.tvHip.setTextColor(Color.RED);
        }
        else if (sessions.getIsOccupied().equalsIgnoreCase("1"))
        {
            viewHolder.tvHip.setText("Booked");
            viewHolder.tvHip.setTextColor(Color.GREEN);
        }
        else if (sessions.getIsOccupied().equalsIgnoreCase("2"))
        {
            viewHolder.tvHip.setText("Completed");
            viewHolder.tvHip.setTextColor(Color.BLUE);
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
        private TextView tvHeight,tvWaist,tvWeight,tvNeck,tvHip,tvDay,tvMonth,tvYear;
        public ViewHolder(View view) {
            super(view);
            view1=(LinearLayout)view.findViewById(R.id.view1);
            view2=(LinearLayout)view.findViewById(R.id.view2);
            tvHeight = (TextView)view.findViewById(R.id.tv_height);
            tvWaist = (TextView) view.findViewById(R.id.tv_waist);
            tvWeight=(TextView)view.findViewById(R.id.tv_weight);
            tvNeck = (TextView)view.findViewById(R.id.tv_neck);
            tvHip = (TextView)view.findViewById(R.id.tv_hip);
            tvDay = (TextView)view.findViewById(R.id.tv_day);
            tvMonth = (TextView)view.findViewById(R.id.tv_month);
            tvYear = (TextView)view.findViewById(R.id.tv_year);
        }
    }

}