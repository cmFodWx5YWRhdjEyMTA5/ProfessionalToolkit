package health.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import health.app.Activities.CommonChatActivity;
import health.app.R;
import health.app.Response.MyTrainerListResponse;

/**
 * Created by Developer Six on 9/29/2017.
 */

public class AddMessageTrainerAdapter extends RecyclerView.Adapter<AddMessageTrainerAdapter.ViewHolder> {
    private List<MyTrainerListResponse.MyTrainerData> myTrainerDatas;
    private Context context;

    public AddMessageTrainerAdapter(Context context, List<MyTrainerListResponse.MyTrainerData> myTrainerDatas) {
        this.myTrainerDatas = myTrainerDatas;
        this.context = context;
    }

    @Override
    public AddMessageTrainerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trainer_list_layout, viewGroup, false);
        return new AddMessageTrainerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddMessageTrainerAdapter.ViewHolder viewHolder, final int i) {
        MyTrainerListResponse.MyTrainerData trainerData=myTrainerDatas.get(i);

        viewHolder.tv_android.setText(trainerData.getFirst_name()+" "+trainerData.getLast_name());
        viewHolder.tv_phone.setText(trainerData.getPhone());
        Picasso.with(context).load(trainerData.getProfile_image()).into(viewHolder.img_android);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTrainerListResponse.MyTrainerData approvedData1 = (MyTrainerListResponse.MyTrainerData) myTrainerDatas.get(i);
                Intent intent = new Intent(context,CommonChatActivity.class);
                intent.putExtra(CommonChatActivity.ADDTRAINER, approvedData1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTrainerDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_android,tv_phone;
        private CircleImageView img_android;
        public ViewHolder(View view) {
            super(view);
            tv_phone= (TextView)view.findViewById(R.id.tv_phone);
            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (CircleImageView) view.findViewById(R.id.img_android);

        }
    }

}