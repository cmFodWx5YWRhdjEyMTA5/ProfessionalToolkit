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
import android.widget.CheckBox;
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
import health.app.Activities.ChatActivity;
import health.app.R;
import health.app.Response.MessageListResponse;
import health.app.Response.SignUpResponse;
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

public class InboxMessageAdapter extends RecyclerSwipeAdapter<InboxMessageAdapter.SimpleViewHolder> {

    private Context mContext;
    String showMinus;
    List<MessageListResponse.MessageData> messageList =new ArrayList<>();
    public InboxMessageAdapter(Context context, List<MessageListResponse.MessageData> messageList1) {
        this.mContext = context;
        this.messageList = messageList1;

    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final MessageListResponse.MessageData item = messageList.get(position);
        viewHolder.tvName.setText(item.getFirst_name()+" "+item.getLast_name());
        viewHolder.tvdetails.setText(item.getMsg());
        viewHolder.tvclock.setText(item.getCreatedOn());
        viewHolder.tvCount.setText(String.valueOf(item.getUnread()));
        Log.d("showMinus","showMinus"+showMinus);
        if(item.getProfile_image()!=null)
        {
            Picasso.with(viewHolder.circularImageView.getContext()).load(item.getProfile_image()).into(viewHolder.circularImageView);
        }
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageListResponse.MessageData favoriteList1 = (MessageListResponse.MessageData) messageList.get(position);
                Intent intent = new Intent(mContext,ChatActivity.class);
                intent.putExtra(ChatActivity.MESSAGE, favoriteList1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
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
                        removeMessage(item);
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
    public  void removeMessage(final MessageListResponse.MessageData item) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) mContext).showProgressbar("Loading", "Please wait....");
        healthAppService.deleteMessage(Preferences.getInstance().getUserId(), item.getMsgId()).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.body() != null) {
                    Toast.makeText(mContext,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) mContext).hideProgressBar();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                ((BaseActivity) mContext).hideProgressBar();
                Toast.makeText(mContext, HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView tvName,tvdetails,tvclock;
        LinearLayout bottomwrapper;
        CircleImageView circularImageView;
        RelativeLayout mainLayout;
        CheckBox checkBox;
        TextView tvCount;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvdetails=(TextView)itemView.findViewById(R.id.tvdetails);
            tvclock=(TextView)itemView.findViewById(R.id.tvclock);
            bottomwrapper=(LinearLayout)itemView.findViewById(R.id.bottom_wrapper);
            mainLayout=(RelativeLayout)itemView.findViewById(R.id.main);
            checkBox=(CheckBox)itemView.findViewById(R.id.chkSelected);
            circularImageView = (de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.user_image);
            tvCount=(TextView)itemView.findViewById(R.id.counter);
        }
    }


    public void removeAt(int position) {
        messageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, messageList.size());
    }


}


