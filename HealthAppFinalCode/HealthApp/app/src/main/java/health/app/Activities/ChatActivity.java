package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Adapter.ChatAdapter;
import health.app.Model.ChatModal;
import health.app.R;
import health.app.Response.ChatListResponse;
import health.app.Response.MessageListResponse;
import health.app.Response.MyTrainerListResponse;
import health.app.Response.NewMessageResponse;
import health.app.Response.TrainerDashboardListResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChatActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    public MessageListResponse.MessageData messageData;
    public MyTrainerListResponse.MyTrainerData trainerData;
    public TrainerDashboardListResponse.ApprovedData clientData;
    ChatAdapter chatAdapter;
    LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    public static String Next_Page = "1";
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private static boolean loading = true;
    boolean test = false;
    public static List<ChatModal> chat_array = new ArrayList<>();
    public static final String ADDCLIENT = "addclient";
    public static final String ADDTRAINER = "addtrainer";
    public static final String MESSAGE = "messge";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        messageData = (MessageListResponse.MessageData) getIntent().getParcelableExtra(MESSAGE);
        trainerData = (MyTrainerListResponse.MyTrainerData) getIntent().getParcelableExtra(ADDTRAINER);
        clientData = (TrainerDashboardListResponse.ApprovedData) getIntent().getParcelableExtra(ADDCLIENT);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        if (messageData!=null) {
            title.setText(messageData.getFirst_name()+" "+messageData.getLast_name());
        }
        else {
            title.setText("Chat");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.mipmap.backicon2x));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        messagesInboxRecyclerview.setLayoutManager(mLayoutManager);
        messagesInboxRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        chat_array.clear();
                                        getAllUser(Preferences.getInstance().getUserId());
                                    }
                                }
        );
 }


 @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
 public void addItems(){
     visibleItemCount = mLayoutManager.getChildCount();
     totalItemCount = mLayoutManager.getItemCount();
     pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
     if (loading) {
         if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
             loading = false;
             if (Next_Page.equals("")) {

             } else {
                 test = true;
                 getAllUser1(Preferences.getInstance().getUserId());
             }

             Log.v("...", "Last Item Wow !");
             //Do pagination.. i.e. fetch new data
         }

     }
 }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void sendNewMessage() {
        String toServer = etMessage.getText().toString();
        String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(toServer);
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Sending Message...");
        healthAppService.sendMessage(Preferences.getInstance().getUserId(),messageData.getMsgFrom(),toServerUnicodeEncoded).
                enqueue(new Callback<NewMessageResponse>() {
                    @Override
                    public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                        if (response.body().isStatus()){
                            chat_array.clear();
                            getAllUser(Preferences.getInstance().getUserId());
                            etMessage.setText("");
                        }
                        else {
                            alertUser(response.body().getMsg());
                        }

                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllUser(final String userId) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        swipeRefreshLayout.setRefreshing(true);
        healthAppService.getChatList(userId,messageData.getMsgFrom(),"1").enqueue(new Callback<ChatListResponse>() {
            @Override
            public void onResponse(Call<ChatListResponse> call, Response<ChatListResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    loading=true;
                    Next_Page=String.valueOf(response.body().getNext_Page());
                    for(int i=0;i<response.body().getChatList().size();i++){
                        if (response.body().getChatList().get(i).getMsgFrom().equals(userId)) {
                            chat_array.add(new ChatModal(response.body().getChatList().get(i).getMessageTxt(),response.body().getChatList().get(i).getCreatedOn(), 0));
                            Log.d("chat_array","chat_array"+chat_array.get(i).getCreatedOn());
                            chatAdapter = new ChatAdapter(chat_array, ChatActivity.this);
                            messagesInboxRecyclerview.scrollToPosition(0);
                            messagesInboxRecyclerview.setAdapter(chatAdapter);
                            chatAdapter.notifyDataSetChanged();
                        } else {
                            chat_array.add(new ChatModal(response.body().getChatList().get(i).getMessageTxt(), response.body().getChatList().get(i).getCreatedOn(), 1));
                            chatAdapter = new ChatAdapter(chat_array, ChatActivity.this);
                            messagesInboxRecyclerview.scrollToPosition(0);
                            messagesInboxRecyclerview.setAdapter(chatAdapter);
                            chatAdapter.notifyDataSetChanged();
                        }
                    }
                    }
              hideProgressBar();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ChatListResponse> call, Throwable t) {
                hideProgressBar();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllUser1(final String userId) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        swipeRefreshLayout.setRefreshing(true);
        healthAppService.getChatList(userId,messageData.getMsgFrom(),Next_Page).enqueue(new Callback<ChatListResponse>() {
            @Override
            public void onResponse(Call<ChatListResponse> call, Response<ChatListResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    loading=true;
                    for(int i=0;i<response.body().getChatList().size();i++){
                        if (response.body().getChatList().get(i).getMsgFrom().equals(userId)) {
                            chat_array.add(new ChatModal(response.body().getChatList().get(i).getMessageTxt(),response.body().getChatList().get(i).getCreatedOn(), 0));
                            Log.d("chat_array","chat_array"+chat_array.get(i).getCreatedOn());
                            chatAdapter = new ChatAdapter(chat_array, ChatActivity.this);
                            messagesInboxRecyclerview.scrollToPosition(0);
                            messagesInboxRecyclerview.setAdapter(chatAdapter);
                            chatAdapter.notifyDataSetChanged();
                        } else {
                            chat_array.add(new ChatModal(response.body().getChatList().get(i).getMessageTxt(), response.body().getChatList().get(i).getCreatedOn(), 1));
                            chatAdapter = new ChatAdapter(chat_array, ChatActivity.this);
                            messagesInboxRecyclerview.scrollToPosition(0);
                            messagesInboxRecyclerview.setAdapter(chatAdapter);
                            chatAdapter.notifyDataSetChanged();
                        }
                    }
                }
                hideProgressBar();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ChatListResponse> call, Throwable t) {
                hideProgressBar();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validation() {
        if (etMessage.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Message.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }

    private void alertUser(String msg) {
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(ChatActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setIcon(R.drawable.ptlogo);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_message)
    EditText etMessage;

    @BindView(R.id.sent_recycler_view)
    RecyclerView messagesInboxRecyclerview;

    @BindView(R.id.container)
    RelativeLayout container;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.send)
    void send(){
        if (validation()) {
            sendNewMessage();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRefresh() {
        addItems();
        mLayoutManager.scrollToPosition(0);
    }

}
