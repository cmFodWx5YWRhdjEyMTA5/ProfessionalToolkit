package health.app.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import health.app.Activities.CommonChatActivity;
import health.app.Activities.CustomerDashboardActivity;
import health.app.Activities.TrainerDetailByIdActivity;
import health.app.Activities.TrainerProfile1Activity;
import health.app.Fragments.MySessionFragment;
import health.app.R;
import health.app.Response.MyTrainerListResponse;
import health.app.Response.TrainerByIdResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.FragmentChangeListener;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Developer Six on 9/1/2017.
 */

public class TrainerListAdapter extends RecyclerView.Adapter<TrainerListAdapter.ViewHolder> {
    private List<MyTrainerListResponse.MyTrainerData> myTrainerDatas;
    private Context context;
    String ShowLayout;
    public TrainerListAdapter(Context context, List<MyTrainerListResponse.MyTrainerData> myTrainerDatas,String ShowLayout) {
        this.myTrainerDatas = myTrainerDatas;
        this.context = context;
        this.ShowLayout=ShowLayout;
    }

    @Override
    public TrainerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_trainer_list, viewGroup, false);
        return new TrainerListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrainerListAdapter.ViewHolder viewHolder, final int i) {
        final MyTrainerListResponse.MyTrainerData trainerData=myTrainerDatas.get(i);
        viewHolder.tv_android.setText(trainerData.getFirst_name()+" "+trainerData.getLast_name());
        viewHolder.tv_phone.setText(trainerData.getEmail());
        Picasso.with(context).load(trainerData.getProfile_image()).into(viewHolder.img_android);
        if (ShowLayout.equalsIgnoreCase("ShowLayout"))
        {
            viewHolder.header.setVisibility(View.GONE);
            viewHolder.tvClick.setVisibility(View.VISIBLE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                trainerById(trainerData.getUniqueId());
            }
        });
        }
        viewHolder.llviewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTrainerListResponse.MyTrainerData approvedData1 = (MyTrainerListResponse.MyTrainerData) myTrainerDatas.get(i);
                Intent intent = new Intent(context,CommonChatActivity.class);
                intent.putExtra(CommonChatActivity.MESSAGETRAINER, approvedData1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.llviewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,TrainerProfile1Activity.class);
                intent.putExtra("UserId",trainerData.getTrainerId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.llnewAppointment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                trainerById(trainerData.getUniqueId());
//                MyTrainerListResponse.MyTrainerData approvedData1 = (MyTrainerListResponse.MyTrainerData) myTrainerDatas.get(i);
//                Intent intent = new Intent(context,TrainerDetailByIdActivity.class);
//                intent.putExtra(TrainerDetailByIdActivity.PACKAGEDATA, approvedData1);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
            }
        });
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MyTrainerListResponse.MyTrainerData approvedData1 = (MyTrainerListResponse.MyTrainerData) myTrainerDatas.get(i);
//                Intent intent = new Intent(context,MyTrainerDetailActivity.class);
//                intent.putExtra(MyTrainerDetailActivity.TRAINER, approvedData1);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
        viewHolder.llviewSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr=new MySessionFragment();
                FragmentChangeListener fc=(FragmentChangeListener)context;
                fc.replaceFragment(fr,"My Session");
                CustomerDashboardActivity.bottomNavigationView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTrainerDatas.size();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void trainerById(String uniqueId) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait...");
        healthAppService.trainerById(uniqueId, Preferences.getInstance().getUserId()).enqueue(new Callback<TrainerByIdResponse>() {
            @Override
            public void onResponse(Call<TrainerByIdResponse> call, Response<TrainerByIdResponse> response) {
                if (response.body().isStatus()) {
                    Intent intent = new Intent(context, TrainerDetailByIdActivity.class);
                    intent.putExtra("listing1", response.body().getTrainerIdDataList());
                    // intent.putParcelableArrayListExtra("listing", (ArrayList<? extends Parcelable>) trainerIdDataList);
                    context.startActivity(intent);
                } else {
                    final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
                    alertDialogBuilder.setTitle(context.getString(R.string.app_name));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setIcon(R.drawable.ptlogo);
                    alertDialogBuilder.setMessage(response.body().getMsg());
                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                ((BaseActivity) context).hideProgressBar();

            }

            @Override
            public void onFailure(Call<TrainerByIdResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }
        public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_android,tv_phone,tvClick;
        private ImageView img_android,sendIcon;RelativeLayout header;
        LinearLayout llviewProfile,llviewMessage,llviewSessions,llnewAppointment;
        public ViewHolder(View view) {
            super(view);
            tv_phone= (TextView)view.findViewById(R.id.tv_phone);
            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.img_android);
            sendIcon=(ImageView)view.findViewById(R.id.message_icon);
            llviewProfile=(LinearLayout)view.findViewById(R.id.ll_viewProfile);
            llviewMessage=(LinearLayout)view.findViewById(R.id.ll_viewMessage);
            llviewSessions=(LinearLayout)view.findViewById(R.id.ll_viewSessions);
            llnewAppointment=(LinearLayout)view.findViewById(R.id.ll_newApp);
            header=(RelativeLayout)view.findViewById(R.id.header);
            tvClick=(TextView)view.findViewById(R.id.tvClick);
        }
    }

}