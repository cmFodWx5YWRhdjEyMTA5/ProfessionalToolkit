package health.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import health.app.Activities.MeasurementDetailsActivity;
import health.app.R;
import health.app.Response.MeasurementResponse;

/**
 * Created by Developer Six on 11/15/2017.
 */

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.ViewHolder> {
    private List<MeasurementResponse.MeasureData> sessionsList;
    private Context context;
    String dayOfWeek;

    public MeasurementAdapter(Context context, List<MeasurementResponse.MeasureData> sessionsList) {
        this.sessionsList = sessionsList;
        this.context = context;
    }

    @Override
    public MeasurementAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.measurement_list, viewGroup, false);
        return new MeasurementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeasurementAdapter.ViewHolder viewHolder, final int position) {
        MeasurementResponse.MeasureData sessions=sessionsList.get(position);
        Typeface type1 = Typeface.createFromAsset(context.getAssets(),"fonts/Poppins-Regular.ttf");
        viewHolder.tvDate.setTypeface(type1);
        String[] splited = sessions.getDate().split("\\s+");
        SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat writeFormat = new SimpleDateFormat("MMM dd,yyyy");

        Date date = null;
        try {
            date = readFormat.parse(splited[0]);

            dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(date);

            Log.d("dayOfWeek","dayOfWeek"+dayOfWeek); // Friday
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.tvDate.setText(writeFormat.format(date)+" ("+dayOfWeek+") ");
        viewHolder.tvTime.setText(splited[1]);
        viewHolder.tvBMI.setText("BMI : "+sessions.getBMI());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeasurementResponse.MeasureData sessions1=sessionsList.get(position);
                Intent intent = new Intent(context,MeasurementDetailsActivity.class);
                intent.putExtra(MeasurementDetailsActivity.MEASURE, sessions1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDate,tvBMI,tvTime;
        public ViewHolder(View view) {
            super(view);
            tvDate=(TextView)view.findViewById(R.id.tvDate);
            tvBMI=(TextView)view.findViewById(R.id.tvBMI);
            tvTime=(TextView)view.findViewById(R.id.tvTime);

        }
    }

}
