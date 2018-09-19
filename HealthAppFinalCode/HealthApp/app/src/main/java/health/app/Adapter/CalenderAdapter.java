package health.app.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import health.app.Activities.TrainerDashboardActivity;
import health.app.R;
import health.app.Response.TimeSlotByDateResponse;
import health.app.Response.TimeSlotResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Developer Six on 8/9/2017.
 */

public class CalenderAdapter extends RecyclerSwipeAdapter<CalenderAdapter.SimpleViewHolder> {

    private Context mContext;

    List<TimeSlotByDateResponse.DataResponse.TimeSlotByDate> calenderLists =new ArrayList<>();

    public CalenderAdapter(Context context, List<TimeSlotByDateResponse.DataResponse.TimeSlotByDate> calenderLists ) {
        this.mContext = context;
        this.calenderLists = calenderLists;

    }



    @Override
    public CalenderAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_list, parent, false);
        return new CalenderAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CalenderAdapter.SimpleViewHolder viewHolder, final int position) {
        final TimeSlotByDateResponse.DataResponse.TimeSlotByDate item = calenderLists.get(position);
        viewHolder.tvTitle.setText(item.getSlotTitle());
        viewHolder.tvDescription.setText(item.getSlotDescription());
        viewHolder.tvDate.setText(item.getSlotDate());
        viewHolder.tvTime.setText(item.getStartTime()+"-"+item.getEndTime());
        viewHolder.tvLocation.setText(item.getAddress());
        if (item.getIsOccupied().equals("1")) {
            viewHolder.tvBook.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.tvBook.setVisibility(View.GONE);
        }
        Picasso.with(mContext).load(Preferences.getInstance().getUserPhotoPath()).into(viewHolder.circularImageView);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.bottomwrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.ptlogo);
                alertDialogBuilder.setMessage("Are you sure you want to delete?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                        removeAt(position);
                        deleteSlot(item);
                        mItemManger.closeAllItems();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
                    }
                });
                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                pbutton.setTextColor(Color.parseColor("#FF7010"));
                nbutton.setTextColor(Color.parseColor("#FF7010"));
            }
        });

        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));


        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });





        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void deleteSlot(final TimeSlotByDateResponse.DataResponse.TimeSlotByDate item) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) mContext).showProgressbar("Loading", "Please wait....");
        healthAppService.deleteSlotTrainer(Preferences.getInstance().getUserId(), item.getSlotId()).enqueue(new Callback<TimeSlotResponse>() {
            @Override
            public void onResponse(Call<TimeSlotResponse> call, Response<TimeSlotResponse> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()){
                        Toast.makeText(mContext,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, TrainerDashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                    else {
                        Toast.makeText(mContext,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(mContext,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) mContext).hideProgressBar();
            }

            @Override
            public void onFailure(Call<TimeSlotResponse> call, Throwable t) {
                ((BaseActivity) mContext).hideProgressBar();
                Toast.makeText(mContext, HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return calenderLists.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }




    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView tvTitle,tvDate,tvTime,tvDescription,tvLocation,tvBook;
        LinearLayout bottomwrapper;
        CircleImageView circularImageView;
        RelativeLayout mainLayout;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvTitle=(TextView)itemView.findViewById(R.id.tv_title);
            tvDate=(TextView)itemView.findViewById(R.id.tv_date);
            tvTime=(TextView)itemView.findViewById(R.id.tv_time);
            tvDescription=(TextView)itemView.findViewById(R.id.tv_description);
            tvLocation=(TextView)itemView.findViewById(R.id.tv_location);
            bottomwrapper=(LinearLayout)itemView.findViewById(R.id.bottom_wrapper);
            mainLayout=(RelativeLayout)itemView.findViewById(R.id.main);
            tvBook=(TextView)itemView.findViewById(R.id.tv_book);
            circularImageView = (de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.user_calender);
        }
    }

    public void removeAt(int position) {
        calenderLists.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, calenderLists.size());
    }
}