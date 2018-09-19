package health.app.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import health.app.R;
import health.app.Response.ReportListResponse;

/**
 * Created by Developer Six on 10/6/2017.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List< ReportListResponse.ReportData> sessionsList;
    private Context context;
    String day,month,year;
    public ReportAdapter(Context context, List< ReportListResponse.ReportData> sessionsList) {
        this.sessionsList = sessionsList;
        this.context = context;
    }

    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_list, viewGroup, false);
        return new ReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportAdapter.ViewHolder viewHolder, final int i) {
        ReportListResponse.ReportData sessions=sessionsList.get(i);
        Typeface type1 = Typeface.createFromAsset(context.getAssets(),"fonts/Poppins-SemiBold.ttf");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=(Date)formatter.parse(sessions.getDate());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
            String dateString=dateFormat.format(date);
            String[] array = dateString.split("\\s+");
            day=array[1];
            month=array[0];
            year=array[2];
            Log.d("array","array"+array[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.tvDay.setTypeface(type1);
        viewHolder.tvDay.setText(removeLastChar(day));
        viewHolder.tvMonth.setText(month);
        viewHolder.tvYear.setText(year);
        viewHolder.tvHeight.setText(sessions.getHeight()+" cm");
        viewHolder.tvWaist.setText(sessions.getWaist()+" cm");
        viewHolder.tvWeight.setText(sessions.getWeight()+" kg");
        viewHolder.tvNeck.setText(sessions.getNeck()+" cm");
        viewHolder.tvHip.setText(sessions.getHips()+" cm");
        viewHolder.tvBmi.setText(sessions.getBMI());
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
        private TextView tvHeight,tvWaist,tvWeight,tvNeck,tvHip,tvBmi,tvDay,tvMonth,tvYear;
        public ViewHolder(View view) {
            super(view);
            view1=(LinearLayout)view.findViewById(R.id.view1);
            view2=(LinearLayout)view.findViewById(R.id.view2);
            tvHeight = (TextView)view.findViewById(R.id.tv_height);
            tvWaist = (TextView) view.findViewById(R.id.tv_waist);
            tvWeight=(TextView)view.findViewById(R.id.tv_weight);
            tvNeck = (TextView)view.findViewById(R.id.tv_neck);
            tvHip = (TextView)view.findViewById(R.id.tv_hip);
            tvBmi = (TextView)view.findViewById(R.id.tv_bmi);
            tvDay = (TextView)view.findViewById(R.id.tv_day);
            tvMonth = (TextView)view.findViewById(R.id.tv_month);
            tvYear = (TextView)view.findViewById(R.id.tv_year);
        }
    }

}