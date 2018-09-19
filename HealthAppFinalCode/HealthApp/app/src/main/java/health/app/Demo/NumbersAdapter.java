package health.app.Demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import health.app.R;

/**
 * Created by Developer Six on 11/2/2017.
 */

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.ViewHolder> {

    private List<Student> stList;
    public static ArrayList<String> selectedStrings = new ArrayList<String>();
    Context context;
    String name;
    String image;
    String packageDate,noOfSlots;

    public NumbersAdapter(Context context, List<Student> students, String name, String image, String packageDate, String noOfSlots) {
        this.context=context;
        this.stList = students;
        this.name = name;
        this.image = image;
        this.packageDate=packageDate;
        this.noOfSlots=noOfSlots;
    }

    // Create new views
    @Override
    public NumbersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_list_item, null);

        // create ViewHolder

        NumbersAdapter.ViewHolder viewHolder = new NumbersAdapter.ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NumbersAdapter.ViewHolder viewHolder, int position) {

        final int pos = position;
        final String startTime=removeLastChar(stList.get(position).getStartTime());
        final String endTime=removeLastChar(stList.get(position).getEndTime());
        viewHolder.tvTrainerName.setText(name);
        viewHolder.tvTime.setText(startTime+" - "+endTime);

        viewHolder.ivCheck.setChecked(stList.get(position).isSelected());

        viewHolder.ivCheck.setTag(stList.get(position));


        viewHolder.ivCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Student contact = (Student) cb.getTag();

                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());
                //viewHolder.cell.setBackgroundColor(Color.parseColor("#68AACE"));
//                Toast.makeText(
//                        v.getContext(),
//                        "Clicked on Checkbox: " + cb.getText() + " is "
//                                + cb.isChecked(), Toast.LENGTH_LONG).show();
                if (cb.isChecked())
                {
                    selectedStrings.clear();
                   selectedStrings.add(stList.get(pos).getStartTime()+" - "+stList.get(pos).getEndTime());
                }
                else {
                    selectedStrings.remove(stList.get(pos).getStartTime()+" - "+stList.get(pos).getEndTime());
                }
            }
        });
        Picasso.with(context).load(image).into(viewHolder.ivUserImage);
    }

    public String removeLastChar(String time){
        String time1=time.substring(0, time.length() - 3);
        Log.d("time1","time1"+time1);
        return time1;
    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return stList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTime,tvTrainerName;
        private CheckBox ivCheck;
        CircleImageView ivUserImage;
        RelativeLayout cell;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            cell=(RelativeLayout)itemLayoutView.findViewById(R.id.cell);
            tvTime= (TextView)itemLayoutView.findViewById(R.id.tv_time);
            tvTrainerName = (TextView)itemLayoutView.findViewById(R.id.tv_trainer_name);
            ivUserImage = (CircleImageView) itemLayoutView.findViewById(R.id.iv_user);
            ivCheck=(CheckBox)itemLayoutView.findViewById(R.id.iv_check);

        }

    }

    // method to access in activity after updating selection
    public List<Student> getStudentist() {
        return stList;
    }

}