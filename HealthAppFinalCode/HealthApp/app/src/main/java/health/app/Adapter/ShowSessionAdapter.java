package health.app.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import health.app.Model.StartEndTime;
import health.app.R;

/**
 * Created by Developer Six on 10/31/2017.
 */

public class ShowSessionAdapter extends RecyclerView.Adapter<ShowSessionAdapter.ViewHolder> {
    private List<StartEndTime> sessionsList;
    private Context context;

    public ShowSessionAdapter(Context context, List<StartEndTime> sessionsList) {
        this.sessionsList = sessionsList;
        this.context = context;
    }

    @Override
    public ShowSessionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.session_time_list, viewGroup, false);
        return new ShowSessionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowSessionAdapter.ViewHolder viewHolder, final int position) {
        StartEndTime sessions=sessionsList.get(position);
        Typeface type1 = Typeface.createFromAsset(context.getAssets(),"fonts/Poppins-Regular.ttf");
        viewHolder.sessionTime.setText("Start Time : "+sessions.getStartTime()+" - "+"End Time : "+sessions.getEndTime());
        }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Override
    public int getItemCount() {
        return sessionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView sessionTime;
        public ViewHolder(View view) {
            super(view);
            sessionTime=(TextView)view.findViewById(R.id.sessionTime);


        }
    }

}