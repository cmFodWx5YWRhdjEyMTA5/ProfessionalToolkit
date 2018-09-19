package health.app.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import health.app.R;
import health.app.Response.GetRequestResponse;

/**
 * Created by Developer Six on 11/4/2017.
 */

public class RequestStartEndAdapter extends RecyclerView.Adapter<RequestStartEndAdapter.MyViewHolder> {

    private List<GetRequestResponse.RequestData.Request.SelectedSlots> customerList;
    Context context;
    String dayOfWeek;
    List<String> dateList=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStart,tvEnd,tvDate;

        public MyViewHolder(View view) {
            super(view);
            tvStart = (TextView) view.findViewById(R.id.tv_start);
            tvEnd = (TextView) view.findViewById(R.id.tv_end);
            tvDate=(TextView) view.findViewById(R.id.tv_date);
        }
    }


    public RequestStartEndAdapter(Context context,List<GetRequestResponse.RequestData.Request.SelectedSlots> customerList) {
        this.context=context;
        this.customerList = customerList;
    }

    @Override
    public RequestStartEndAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.start_end_list, parent, false);

        return new RequestStartEndAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RequestStartEndAdapter.MyViewHolder holder, final int position) {
        GetRequestResponse.RequestData.Request.SelectedSlots customerList1 = customerList.get(position);
        holder.tvStart.setText("Start Time :- "+customerList1.getStartTime());
        holder.tvEnd.setText("End Time :- "+customerList1.getEndTime());
        SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat writeFormat = new SimpleDateFormat("MMM dd,yyyy");
        dateList.add(customerList1.getSessionDate());
        Collections.sort(dateList);
        for (int i=0;i<dateList.size();i++) {
            Log.d("dateList", "dateList" + dateList.get(i));
        }

        Date date = null;
        try {
            date = readFormat.parse(customerList1.getSessionDate());
            dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(date);

            Log.d("dayOfWeek","dayOfWeek"+dayOfWeek); // Friday
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvDate.setText("Date :- "+writeFormat.format(date)+" ("+dayOfWeek+") ");
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }
}

