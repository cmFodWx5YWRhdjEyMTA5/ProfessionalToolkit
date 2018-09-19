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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import health.app.Activities.ClientDetailActivity;
import health.app.Model.StartEndTime;
import health.app.R;
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
 * Created by Developer Six on 10/24/2017.
 */

public class CancelListAdapter extends RecyclerSwipeAdapter<CancelListAdapter.SimpleViewHolder> {

    private Context mContext;
    String trainerId,customerId;
    List<StartEndTime> calenderLists =new ArrayList<>();

    public CancelListAdapter(Context context, List<StartEndTime> calenderLists,String trainerId,String customerId ) {
        this.mContext = context;
        this.calenderLists = calenderLists;
        this.trainerId=trainerId;
        this.customerId=customerId;
    }



    @Override
    public CancelListAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cancel_list, parent, false);
        return new CancelListAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CancelListAdapter.SimpleViewHolder viewHolder, final int position) {
        final StartEndTime item = calenderLists.get(position);
        viewHolder.startTime.setText("Start Time "+item.getStartTime()+" - "+"End Time "+item.getEndTime());
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        if (item.getStatus().equalsIgnoreCase("0")) {
            viewHolder.btStatus.setText("Pending");
            viewHolder.btStatus.setTextColor(Color.WHITE);
        }
        else if (item.getStatus().equalsIgnoreCase("2")) {
            viewHolder.btStatus.setText("Complete");
            viewHolder.btStatus.setTextColor(Color.WHITE);
        }
        else if (item.getStatus().equalsIgnoreCase("1")) {
            viewHolder.btStatus.setText("Ongoing");
            viewHolder.btStatus.setTextColor(Color.WHITE);
        }
        else if (item.getStatus().equalsIgnoreCase("4")) {
            viewHolder.btStatus.setText("Requested");
            viewHolder.btStatus.setTextColor(Color.WHITE);
        }
        viewHolder.swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartEndTime item1 = calenderLists.get(position);
                Intent intent = new Intent(mContext,ClientDetailActivity.class);
                intent.putExtra(ClientDetailActivity.CLIENT, item1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer"))
        {
            viewHolder.bottomwrapper.setVisibility(View.VISIBLE);
            viewHolder.tvLocation1.setVisibility(View.VISIBLE);
            viewHolder.tvLocation2.setVisibility(View.VISIBLE);
            viewHolder.swipeLayout.setClickable(true);
        }
        else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer"))
        {
            viewHolder.bottomwrapper.setVisibility(View.GONE);
            viewHolder.tvLocation1.setVisibility(View.GONE);
            viewHolder.tvLocation2.setVisibility(View.GONE);
            viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            viewHolder.swipeLayout.setClickable(false);
        }
        viewHolder.bottomwrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getStatus().equals("0")) {
                    final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setIcon(R.drawable.ptlogo);
                    alertDialogBuilder.setMessage("Are you sure you want to cancel session?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                            removeAt(position);
                            cancelSession(item);
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
                else {
                    final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setIcon(R.drawable.ptlogo);
                    alertDialogBuilder.setMessage("You can not cancel this slot");
                    alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
                        }
                    });
                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbutton.setTextColor(Color.parseColor("#FF7010"));
                }
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
    private void cancelSession(StartEndTime item) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) mContext).showProgressbar("Loading", "Please wait....");
        healthAppService.trainercancelSession(Preferences.getInstance().getUserId(), item.getSessionSlotId(),customerId).enqueue(new Callback<TimeSlotResponse>() {
            @Override
            public void onResponse(Call<TimeSlotResponse> call, Response<TimeSlotResponse> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()){
                        Toast.makeText(mContext,response.body().getMsg(),Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(mContext, TrainerDashboardActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        mContext.startActivity(intent);
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
        TextView startTime,tvDate,tvTime,tvDescription,tvLocation,tvBook,tvLocation1,tvLocation2;
        LinearLayout bottomwrapper;
        CircleImageView circularImageView;
        RelativeLayout mainLayout;
        Button btStatus;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            startTime=(TextView)itemView.findViewById(R.id.tv_start_time);
            tvDate=(TextView)itemView.findViewById(R.id.tv_date);
            tvTime=(TextView)itemView.findViewById(R.id.tv_time);
            tvDescription=(TextView)itemView.findViewById(R.id.tv_description);
            tvLocation=(TextView)itemView.findViewById(R.id.tv_location);
            bottomwrapper=(LinearLayout)itemView.findViewById(R.id.bottom_wrapper);
            mainLayout=(RelativeLayout)itemView.findViewById(R.id.main);
            tvBook=(TextView)itemView.findViewById(R.id.tv_book);
            circularImageView = (de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.user_calender);
            btStatus=(Button)itemView.findViewById(R.id.bt_status);
            tvLocation1=(TextView)itemView.findViewById(R.id.tv_location1);
            tvLocation2=(TextView)itemView.findViewById(R.id.tv_location);
        }
    }

    public void removeAt(int position) {
        calenderLists.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, calenderLists.size());
    }
}



