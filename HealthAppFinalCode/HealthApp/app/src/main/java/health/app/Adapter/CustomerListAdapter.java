package health.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import health.app.Activities.TrainerDetailActivity;
import health.app.R;
import health.app.Response.TrainerListResponse;

/**
 * Created by Developer Six on 8/28/2017.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> {

    private List<TrainerListResponse.TrainerData> customerList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView customerImage;
        TextView tvFullName,tvTrainingType;

        public MyViewHolder(View view) {
            super(view);
            customerImage = (ImageView) view.findViewById(R.id.iv_customer);
            tvFullName = (TextView) view.findViewById(R.id.fname);
            tvTrainingType = (TextView) view.findViewById(R.id.training_type);
        }
    }


    public CustomerListAdapter(Context context,List<TrainerListResponse.TrainerData> customerList) {
        this.context=context;
        this.customerList = customerList;
    }

    @Override
    public CustomerListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_list_item, parent, false);

        return new CustomerListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomerListAdapter.MyViewHolder holder, final int position) {
        TrainerListResponse.TrainerData customerList1 = customerList.get(position);
        holder.tvFullName.setText(customerList1.getTrainer().getFirst_name()+" "+customerList1.getTrainer().getLast_name());
        if(customerList1.getTrainer().getProfile_image()!=null)
        {
            Picasso.with(holder.customerImage.getContext()).load(customerList1.getTrainer().getProfile_image()).resize(300,240).into(holder.customerImage);
        }
        if (customerList1.getTrainer().getTraining_type().equals("")) {
            holder.tvTrainingType.setText("N/A");
        }
        else {
            holder.tvTrainingType.setText(customerList1.getTrainer().getTraining_type());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrainerListResponse.TrainerData favoriteList1 = (TrainerListResponse.TrainerData) customerList.get(position);
                Intent intent = new Intent(context,TrainerDetailActivity.class);
                intent.putExtra(TrainerDetailActivity.FAVORITE, favoriteList1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }
}
