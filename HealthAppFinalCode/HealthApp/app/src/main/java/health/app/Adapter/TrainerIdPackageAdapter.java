package health.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import health.app.Activities.TrainerIdCalenderActivity;
import health.app.R;
import health.app.Response.TrainerPackageResponse;

/**
 * Created by Developer Six on 10/13/2017.
 */

public class TrainerIdPackageAdapter extends RecyclerView.Adapter<TrainerIdPackageAdapter.ViewHolder> {
    private Context mContext;
    int count;

    List<TrainerPackageResponse.PackageData> packageDataList ;

    public TrainerIdPackageAdapter(Context context, List<TrainerPackageResponse.PackageData> packageDataList) {
        this.mContext = context;
        this.packageDataList = packageDataList;
    }

    @Override
    public TrainerIdPackageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trainer_id_package_list, viewGroup, false);
        return new TrainerIdPackageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrainerIdPackageAdapter.ViewHolder viewHolder, final int postion) {
        final TrainerPackageResponse.PackageData item = packageDataList.get(postion);
        count=postion+1;
        //viewHolder.packageCount.setText("Package "+count);
        viewHolder.packageCount.setText(item.getSlotTitle());
        viewHolder.sessionPrice.setText("$"+item.getSessionPrice());
        viewHolder.sessionTime.setText(item.getSessionTime());
        viewHolder.sessionValid.setText(item.getNoOfDays()+" Days");
        ArrayList<String> items = new ArrayList(Arrays.asList(item.getSessionDays().split("\\s*,\\s*")));
        for (int i=0;i<items.size();i++)
        {
            if (items.get(i).equalsIgnoreCase("Sunday"))
            {
                viewHolder.tvSun.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvSun.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Monday"))
            {
                viewHolder.tvMon.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvMon.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Tuesday"))
            {
                viewHolder.tvTus.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvTus.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Wednesday"))
            {
                viewHolder.tvWed.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvWed.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Thursday"))
            {
                viewHolder.tvThu.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvThu.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Friday"))
            {
                viewHolder.tvFri.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvFri.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Saturday"))
            {
                viewHolder.tvSat.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvSat.setTextColor(Color.WHITE);
            }
        }
        Log.d("days","days"+items);
        viewHolder.sessionNo.setText(String.valueOf(item.getNoOfSlots()+" per day"));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrainerPackageResponse.PackageData packageData = (TrainerPackageResponse.PackageData) packageDataList.get(postion);
                Intent intent = new Intent(mContext,TrainerIdCalenderActivity.class);
                intent.putExtra(TrainerIdCalenderActivity.PACKAGE_DAY, packageData);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packageDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView sessionValid,sessionTime,sessionPrice,sessionNo,tvSun,tvMon,tvTus,tvWed,tvThu,tvFri,tvSat,packageCount;
        RelativeLayout mainLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            packageCount=(TextView)itemView.findViewById(R.id.tv_package);
            sessionValid=(TextView)itemView.findViewById(R.id.tv_session_valid);
            sessionTime=(TextView)itemView.findViewById(R.id.tv_session_time);
            sessionPrice=(TextView)itemView.findViewById(R.id.tv_session_price);
            sessionNo=(TextView)itemView.findViewById(R.id.tv_session_no);
            mainLayout=(RelativeLayout)itemView.findViewById(R.id.main);
            tvSun=(TextView)itemView.findViewById(R.id.tv_sun);
            tvMon=(TextView)itemView.findViewById(R.id.tv_mon);
            tvTus=(TextView)itemView.findViewById(R.id.tv_tus);
            tvWed=(TextView)itemView.findViewById(R.id.tv_wed);
            tvThu=(TextView)itemView.findViewById(R.id.tv_thu);
            tvFri=(TextView)itemView.findViewById(R.id.tv_fri);
            tvSat=(TextView)itemView.findViewById(R.id.tv_sat);
        }
    }

}

