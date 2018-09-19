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

import health.app.Model.CurrentSessionList;
import health.app.R;

/**
 * Created by Developer Six on 9/1/2017.
 */

public class CurrentSessionAdapter extends RecyclerView.Adapter<CurrentSessionAdapter.ViewHolder> {
    private List<CurrentSessionList> android;
    private Context context;

    public CurrentSessionAdapter(Context context, List<CurrentSessionList> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public CurrentSessionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.current_session_list, viewGroup, false);
        return new CurrentSessionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrentSessionAdapter.ViewHolder viewHolder, final int i) {
        CurrentSessionList approvedData=android.get(i);

        viewHolder.tv_android.setText(approvedData.getFirst_name()+" "+approvedData.getLast_name());

        Picasso.with(context).load(approvedData.getProfile_image()).into(viewHolder.img_android);
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