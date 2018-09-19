package health.app.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import health.app.Model.CompletedSessionList;
import health.app.R;

/**
 * Created by Developer Six on 9/1/2017.
 */

public class CompleteSessionAdapter extends RecyclerView.Adapter<CompleteSessionAdapter.ViewHolder> {
    private List<CompletedSessionList> android;
    private Context context;

    public CompleteSessionAdapter(Context context, List<CompletedSessionList> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public CompleteSessionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.current_session_list, viewGroup, false);
        return new CompleteSessionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompleteSessionAdapter.ViewHolder viewHolder, final int i) {
        CompletedSessionList approvedData=android.get(i);

        viewHolder.tv_android.setText(approvedData.getFirstName()+" "+approvedData.getLastName());

        Picasso.with(context).load(approvedData.getProfileImage()).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_android;
        private ImageView img_android;
        public ViewHolder(View view) {
            super(view);
            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.img_android);

        }
    }

}
