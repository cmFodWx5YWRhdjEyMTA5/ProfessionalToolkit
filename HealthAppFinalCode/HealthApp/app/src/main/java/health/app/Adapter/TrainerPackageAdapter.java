package health.app.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.Arrays;
import java.util.List;

import health.app.Activities.TrainerPackageActivity;
import health.app.AddPackage.AddDurationActivity;
import health.app.R;
import health.app.Response.TrainerPackageResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Developer Six on 10/11/2017.
 */

public class TrainerPackageAdapter extends RecyclerSwipeAdapter<TrainerPackageAdapter.SimpleViewHolder> {

    private Context mContext;
    String hour,minute,hour1,minute1;
    List<TrainerPackageResponse.PackageData> packageDataList ;
    public TrainerPackageAdapter(Context context, List<TrainerPackageResponse.PackageData> packageDataList) {
        this.mContext = context;
        this.packageDataList = packageDataList;

    }

    @Override
    public TrainerPackageAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_package_list, parent, false);
        return new TrainerPackageAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrainerPackageAdapter.SimpleViewHolder viewHolder, final int position) {
        final TrainerPackageResponse.PackageData item = packageDataList.get(position);
        int count=position+1;
       // viewHolder.tvPackage.setText("Package "+count);
        viewHolder.tvPackage.setText(item.getSlotTitle());
        viewHolder.sessionPrice.setText("$"+item.getSessionPrice());
        String[] splited = item.getSessionTime().split(":");
        hour=splited[0];
        minute=splited[1];
        if (hour.startsWith("0")) {
            Log.d("startsWith","startsWith"+hour.substring(1));
            hour1= hour.substring(1);
            if (hour1.equalsIgnoreCase("0")) {
                viewHolder.sessionTime.setText(minute + " mins");
            }
            else {
                if (minute.equalsIgnoreCase("00")) {
                    viewHolder.sessionTime.setText(hour1 + " hr");
                }
                else {
                    viewHolder.sessionTime.setText(hour1 + " hr " + minute + " mins");
                }
            }
        }
        else {
            if (minute.equalsIgnoreCase("00")) {
                Log.d("startsWith1","startsWith1"+"startsWith1");
                viewHolder.sessionTime.setText(hour + " hr");
            }
            else {
                viewHolder.sessionTime.setText(hour + " hr " + minute + " mins");
            }
        }
        viewHolder.sessionValid.setText(item.getNoOfDays()+" Days");
        ArrayList<String> items = new ArrayList(Arrays.asList(item.getSessionDays().split("\\s*,\\s*")));
        for (int i=0;i<items.size();i++)
        {
            if (items.get(i).equalsIgnoreCase("Sunday"))
            {
                viewHolder.tvSun.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvSun.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Monday"))
            {
                viewHolder.tvMon.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvMon.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Tuesday"))
            {
                viewHolder.tvTus.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvTus.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Wednesday"))
            {
                viewHolder.tvWed.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvWed.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Thursday"))
            {
                viewHolder.tvThu.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvThu.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Friday"))
            {
                viewHolder.tvFri.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvFri.setTextColor(Color.WHITE);
            }
            else if (items.get(i).equalsIgnoreCase("Saturday"))
            {
                viewHolder.tvSat.setBackgroundResource(R.drawable.circle_orange);
                viewHolder.tvSat.setTextColor(Color.WHITE);
            }
        }
        Log.d("days","days"+items);
        viewHolder.sessionNo.setText(String.valueOf(item.getNoOfSlots()+" per day"));
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewHolder.editPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.ptlogo);
                alertDialogBuilder.setMessage("Are you sure you want to edit this package?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        mItemManger.removeShownLayouts(viewHolder.swipeLayout);
//                        TrainerPackageResponse.PackageData packageData = (TrainerPackageResponse.PackageData) packageDataList.get(position);
//                        Intent intent = new Intent(mContext,TrainerAvailFragment.class);
//                        intent.putExtra(TrainerAvailFragment.PACKAGE, packageData);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        mContext.startActivity(intent);
//                        mItemManger.closeAllItems();
                        mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                        TrainerPackageResponse.PackageData packageData = (TrainerPackageResponse.PackageData) packageDataList.get(position);
                        Intent intent = new Intent(mContext,AddDurationActivity.class);
                        intent.putExtra(AddDurationActivity.PACKAGE, packageData);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
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
        viewHolder.deletePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.ptlogo);
                alertDialogBuilder.setMessage("Are you sure you want to delete this package?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                        removePackage(item,viewHolder);
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
    public  void removePackage(final TrainerPackageResponse.PackageData item, final SimpleViewHolder viewHolder) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) mContext).showProgressbar("Loading", "Please wait....");
        healthAppService.deletePackage(Preferences.getInstance().getUserId(), item.getSlotId()).enqueue(new Callback<TrainerPackageResponse>() {
            @Override
            public void onResponse(Call<TrainerPackageResponse> call, Response<TrainerPackageResponse> response) {
                if (response.body().isStatus()) {
                    final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setIcon(R.drawable.ptlogo);
                    alertDialogBuilder.setMessage(response.body().getMsg());
                    alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if(mContext instanceof TrainerPackageActivity){
                                ((TrainerPackageActivity)mContext).getPackageList();
                            }
//                            Intent intent=new Intent(mContext, TrainerDashboardActivity.class);
//                            mContext.startActivity(intent);
                        }
                    });
                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbutton.setTextColor(Color.parseColor("#FF7010"));
                }
                else {
                    final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setIcon(R.drawable.ptlogo);
                    alertDialogBuilder.setMessage(response.body().getMsg());
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
                ((BaseActivity) mContext).hideProgressBar();
            }

            @Override
            public void onFailure(Call<TrainerPackageResponse> call, Throwable t) {
                ((BaseActivity) mContext).hideProgressBar();
                Toast.makeText(mContext, HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return packageDataList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView sessionValid,sessionTime,sessionPrice,sessionNo,tvSun,tvMon,tvTus,tvWed,tvThu,tvFri,tvSat,tvPackage;
        LinearLayout editPackage,deletePackage;
        RelativeLayout bottomwrapper;
        RelativeLayout mainLayout;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            sessionValid=(TextView)itemView.findViewById(R.id.tv_session_valid);
            sessionTime=(TextView)itemView.findViewById(R.id.tv_session_time);
            sessionPrice=(TextView)itemView.findViewById(R.id.tv_session_price);
            sessionNo=(TextView)itemView.findViewById(R.id.tv_session_no);
            bottomwrapper=(RelativeLayout)itemView.findViewById(R.id.bottom_wrapper);
            editPackage=(LinearLayout)bottomwrapper.findViewById(R.id.ll_edit);
            deletePackage=(LinearLayout)bottomwrapper.findViewById(R.id.ll_delete);
            mainLayout=(RelativeLayout)itemView.findViewById(R.id.main);
            tvSun=(TextView)itemView.findViewById(R.id.tv_sun);
            tvMon=(TextView)itemView.findViewById(R.id.tv_mon);
            tvTus=(TextView)itemView.findViewById(R.id.tv_tus);
            tvWed=(TextView)itemView.findViewById(R.id.tv_wed);
            tvThu=(TextView)itemView.findViewById(R.id.tv_thu);
            tvFri=(TextView)itemView.findViewById(R.id.tv_fri);
            tvSat=(TextView)itemView.findViewById(R.id.tv_sat);
            tvPackage=(TextView)itemView.findViewById(R.id.tv_package);
        }
    }


    public void removeAt(int position) {
        packageDataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, packageDataList.size());
    }


}

