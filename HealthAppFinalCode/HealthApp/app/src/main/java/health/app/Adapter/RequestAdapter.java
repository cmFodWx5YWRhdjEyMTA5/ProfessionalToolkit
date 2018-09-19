package health.app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import health.app.Activities.ReceivedRequestActivity;
import health.app.Activities.RequestTypeActivity;
import health.app.R;
import health.app.Response.GetRequestResponse;
import health.app.Response.NewMessageResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Developer Six on 9/1/2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private List<GetRequestResponse.RequestData.Request> requestDatas;
    private Context context;
    String dayOfWeek;

    public RequestAdapter(Context context, List<GetRequestResponse.RequestData.Request> requestDatas) {
        this.requestDatas = requestDatas;
        this.context = context;
    }

    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_list, viewGroup, false);
        return new RequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestAdapter.ViewHolder viewHolder, final int i) {
        final Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-SemiBold.ttf");
        final GetRequestResponse.RequestData.Request requestData1 =requestDatas.get(i);
        viewHolder.tvName.setText(requestData1.getTrainer().getFirst_name()+" "+requestData1.getTrainer().getLast_name());
        viewHolder.tvPhone.setText(requestData1.getTrainer().getPhone());
        viewHolder.request.setTypeface(font);
        String[] splited = requestData1.getTrainer().getCreated_at().split("\\s+");
        SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat writeFormat = new SimpleDateFormat("MMM dd,yyyy");

        Date date = null;
        try {
            date = readFormat.parse(splited[0]);

// Then get the day of week from the Date based on specific locale.
            dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(date);

            Log.d("dayOfWeek","dayOfWeek"+dayOfWeek); // Friday
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.tvSlotDate.setText(writeFormat.format(date)+" ("+dayOfWeek+") ");
        Picasso.with(context).load(requestData1.getTrainer().getProfile_image()).into(viewHolder.ivImage);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetRequestResponse.RequestData.Request requestData2 = (GetRequestResponse.RequestData.Request) requestDatas.get(i);
                Intent intent = new Intent(context,RequestTypeActivity.class);
                intent.putExtra(RequestTypeActivity.REQUEST, requestData2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(context.getString(R.string.app_name));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.ptlogo);
                alertDialogBuilder.setMessage("Are you sure you want to accept this request?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        acceptRequest(requestData1,i);

                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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
        viewHolder.reject.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(context.getString(R.string.app_name));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.ptlogo);
                alertDialogBuilder.setMessage("Are you sure you want to reject this request?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rejectRequest(requestData1,i);

                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void acceptRequest(final GetRequestResponse.RequestData.Request item, final int position) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        healthAppService.acceptRequest(Preferences.getInstance().getUserId(),item.getRequestId(), item.getPackageId(),item.getCustomerId(),"1").enqueue(new Callback<NewMessageResponse>() {
            @Override
            public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        removeAt(position);
                        ((Activity) context).finish();
                        Intent intent=new Intent(context, ReceivedRequestActivity.class);
                        context.startActivity(intent);
                    }
                    else {
                        Toast.makeText(context,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void rejectRequest(final GetRequestResponse.RequestData.Request item, final int position) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        healthAppService.rejectRequest(Preferences.getInstance().getUserId(),item.getRequestId(), item.getPackageId(),item.getCustomerId(),"2").enqueue(new Callback<NewMessageResponse>() {
            @Override
            public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        removeAt(position);
                        ((Activity) context).finish();
                        Intent intent=new Intent(context, ReceivedRequestActivity.class);
                        context.startActivity(intent);
                    }
                    else {
                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return requestDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName,tvPhone,tvSlotDate,tvSlotTime,request;
        private ImageView ivImage;
        Button accept,reject;
        public ViewHolder(View view) {
            super(view);
            request=(TextView)view.findViewById(R.id.request);
            tvName= (TextView)view.findViewById(R.id.tv_name);
            tvPhone= (TextView)view.findViewById(R.id.tv_phone);
            tvSlotDate= (TextView)view.findViewById(R.id.tv_slot_date);
            tvSlotTime= (TextView)view.findViewById(R.id.tv_slot_time);
            ivImage = (ImageView) view.findViewById(R.id.iv_profile);
            accept=(Button)view.findViewById(R.id.bt_accept);
            reject=(Button)view.findViewById(R.id.bt_reject);

        }
    }

    public void removeAt(int position) {
        requestDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, requestDatas.size());
    }

}
