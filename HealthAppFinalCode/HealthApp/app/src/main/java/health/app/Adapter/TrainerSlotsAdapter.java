package health.app.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import health.app.Model.TimeModel;
import health.app.R;
import health.app.Response.TrainerSlotsResponse;

/**
 * Created by Developer Six on 10/16/2017.
 */

public class TrainerSlotsAdapter extends RecyclerView.Adapter<TrainerSlotsAdapter.ViewHolder> {
    private List<TrainerSlotsResponse.MySlots> myTrainerDatas;
    public static List<TimeModel> timeModels=new ArrayList<>();
    public static List<String> slotList=new ArrayList<>();
    private Context context;
    String name;
    String image;
    String packageDate,noOfSlots;

    public TrainerSlotsAdapter(Context context, List<TrainerSlotsResponse.MySlots> myTrainerDatas, String name, String image,String packageDate,String noOfSlots) {
        this.context = context;
        this.myTrainerDatas = myTrainerDatas;
        this.name = name;
        this.image = image;
        this.packageDate=packageDate;
        this.noOfSlots=noOfSlots;
    }

    @Override
    public TrainerSlotsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slot_list_item, viewGroup, false);
        return new TrainerSlotsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrainerSlotsAdapter.ViewHolder viewHolder, final int i) {
        final TrainerSlotsResponse.MySlots trainerData= myTrainerDatas.get(i);
        final TimeModel timeModel=new TimeModel();
        final String startTime=removeLastChar(trainerData.getStart());
        final String endTime=removeLastChar(trainerData.getEnd());
        viewHolder.tvTime.setText(startTime+"-"+endTime);
        viewHolder.tvTrainerName.setText(name);
        viewHolder.ivCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    viewHolder.ivCheck.setChecked(true);
                    viewHolder.cell.setBackgroundColor(Color.parseColor("#68AACE"));
                    timeModel.setDate(packageDate);
                    slotList.add(startTime+"-"+endTime);
                    timeModel.setStartEnd(slotList);
                    timeModel.setStart(trainerData.getStart());
                    timeModel.setEnd(trainerData.getEnd());
                    timeModels.add(timeModel);
                    Log.d("start","start"+timeModel.getStartEnd());
                }
                else {
                    viewHolder.ivCheck.setChecked(false);
                    viewHolder.cell.setBackgroundColor(Color.parseColor("#E6F2F7"));
                    timeModel.setDate(packageDate);
                    slotList.remove(startTime+"-"+endTime);
                    timeModel.setStart(trainerData.getStart());
                    timeModel.setEnd(trainerData.getEnd());
                    timeModels.add(timeModel);
                    Log.d("end","end"+timeModel.getStartEnd());
                }

            }
        });
        Picasso.with(context).load(image).into(viewHolder.ivUserImage);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return myTrainerDatas.size();
    }

    public String removeLastChar(String time){
        String time1=time.substring(0, time.length() - 3);
        Log.d("time1","time1"+time1);
        return time1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTime,tvTrainerName;
        private CheckBox ivCheck;
        CircleImageView ivUserImage;
        RelativeLayout cell;
        public ViewHolder(View view) {
            super(view);
            cell=(RelativeLayout)view.findViewById(R.id.cell);
            tvTime= (TextView)view.findViewById(R.id.tv_time);
            tvTrainerName = (TextView)view.findViewById(R.id.tv_trainer_name);
            ivUserImage = (CircleImageView) view.findViewById(R.id.iv_user);
            ivCheck=(CheckBox)view.findViewById(R.id.iv_check);

        }
    }

}
