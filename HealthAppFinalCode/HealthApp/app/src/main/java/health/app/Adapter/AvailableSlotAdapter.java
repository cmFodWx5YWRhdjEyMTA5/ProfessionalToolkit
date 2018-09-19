package health.app.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import health.app.Model.AvailableSlot;
import health.app.R;

/**
 * Created by Developer Six on 2/17/2018.
 */

public class AvailableSlotAdapter extends RecyclerView.Adapter<AvailableSlotAdapter.ViewHolder> {
    ArrayList<AvailableSlot> numbers;
    static Context context;
    static String name;
    static String image;
    public static ArrayList<String> selectedStrings = new ArrayList<String>();
    static boolean colorChange;
    String packageDate,noOfSlots;

    public AvailableSlotAdapter(Context context,List<AvailableSlot> numbers, String name, String image, String packageDate, String noOfSlots) {
        this.numbers = new ArrayList<>(numbers);
        this.context = context;
        this.name = name;
        this.image = image;
        this.packageDate=packageDate;
        this.noOfSlots=noOfSlots;
    }

    @Override
    public AvailableSlotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_list_item, parent, false);
        //  mAnimator.onSpringItemCreate(v);
        return new AvailableSlotAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AvailableSlotAdapter.ViewHolder holder, final int position) {
        holder.bindData(numbers.get(position));
        // mAnimator.onSpringItemBind(holder.itemView, position);
        //in some cases, it will prevent unwanted situations
        holder.ivCheck.setOnCheckedChangeListener(null);
        //if true, your checkbox will be selected, else unselected
        holder.ivCheck.setChecked(numbers.get(position).isSelected());


        holder.ivCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                numbers.get(holder.getAdapterPosition()).setSelected(isChecked);
                if (isChecked){
                    selectedStrings.add(numbers.get(holder.getAdapterPosition()).getStartTime());
                }else{
                    selectedStrings.remove(numbers.get(holder.getAdapterPosition()).getStartTime());
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public static String removeLastChar(String time){
        String time1=time.substring(0, time.length() - 3);
        Log.d("time1","time1"+time1);
        return time1;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
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

        public void bindData(AvailableSlot number) {
            final Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Regular.ttf");
            tvTrainerName.setTypeface(font);
            tvTime.setText(removeLastChar(number.getStartTime())+" - "+removeLastChar(number.getEndTime()));
            tvTrainerName.setText(name);
            Picasso.with(context).load(image).into(ivUserImage);
//            if (colorChange){
//                cell.setBackgroundColor(Color.parseColor("#68AACE"));
//            }
//            else {
//                cell.setBackgroundColor(Color.parseColor("#E6F2F7"));
//            }
        }






    }
}