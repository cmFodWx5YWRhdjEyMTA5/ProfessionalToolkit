package health.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import health.app.Activities.CommonChatActivity;
import health.app.Activities.CustomerProfile1Activity;
import health.app.Activities.StatLogActivity;
import health.app.Fragments.MyCustomerSessionFragment;
import health.app.R;
import health.app.Response.TrainerDashboardListResponse;

/**
 * Created by Developer Six on 8/31/2017.
 */

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ViewHolder> {
    private List<TrainerDashboardListResponse.ApprovedData> android;
    private Context context;

    public ClientListAdapter(Context context, List<TrainerDashboardListResponse.ApprovedData> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ClientListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trainer_dashboard_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClientListAdapter.ViewHolder viewHolder, final int i) {
        final TrainerDashboardListResponse.ApprovedData approvedData=android.get(i);
        viewHolder.tv_android.setText(approvedData.getFirst_name()+" "+approvedData.getLast_name());
        viewHolder.tv_phone.setText(approvedData.getPhone());
        Picasso.with(context).load(approvedData.getProfile_image()).into(viewHolder.img_android);
        viewHolder.viewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrainerDashboardListResponse.ApprovedData approvedData1 = (TrainerDashboardListResponse.ApprovedData) android.get(i);
                Intent intent = new Intent(context,CommonChatActivity.class);
                intent.putExtra(CommonChatActivity.MESSAGECLIENT, approvedData1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,CustomerProfile1Activity.class);
                intent.putExtra("customerId",approvedData.getCustomerId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.viewSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrainerDashboardListResponse.ApprovedData approvedData1 = (TrainerDashboardListResponse.ApprovedData) android.get(i);
                Intent intent = new Intent(context,MyCustomerSessionFragment.class);
                intent.putExtra(MyCustomerSessionFragment.MYSESSION, approvedData1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.viewStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrainerDashboardListResponse.ApprovedData approvedData1 = (TrainerDashboardListResponse.ApprovedData) android.get(i);
                Intent intent = new Intent(context,StatLogActivity.class);
                intent.putExtra(StatLogActivity.STAT, approvedData1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TrainerDashboardListResponse.ApprovedData approvedData1 = (TrainerDashboardListResponse.ApprovedData) android.get(i);
//                Intent intent = new Intent(context,ClientDetailActivity.class);
//                intent.putExtra(ClientDetailActivity.CLIENT, approvedData1);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_android,tv_phone;
        private CircleImageView img_android,messageIcon;
        LinearLayout viewMessage,viewProfile,viewSessions,viewStat;
        public ViewHolder(View view) {
            super(view);
            tv_phone= (TextView)view.findViewById(R.id.tv_phone);
            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (CircleImageView) view.findViewById(R.id.img_android);
            viewMessage=(LinearLayout)view.findViewById(R.id.ll_viewMessage);
            viewProfile=(LinearLayout)view.findViewById(R.id.ll_viewProfile);
            viewSessions=(LinearLayout)view.findViewById(R.id.ll_viewSessions);
            viewStat=(LinearLayout)view.findViewById(R.id.ll_viewStat);
        }
    }

}