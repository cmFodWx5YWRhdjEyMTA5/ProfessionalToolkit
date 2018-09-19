package health.app.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Adapter.CompleteSessionAdapter;
import health.app.Adapter.CurrentSessionAdapter;
import health.app.Model.CompletedSessionList;
import health.app.Model.CurrentSessionList;
import health.app.R;
import health.app.Response.MyTrainerListResponse;
import health.app.Utilities.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MyTrainerDetailActivity extends BaseActivity {
    public MyTrainerListResponse.MyTrainerData myTrainerData;
    public static final String TRAINER = "trainer";
    String slotId, customerId, trainerId;
    List<CurrentSessionList> currentSessionListList=new ArrayList<>();
    List<CompletedSessionList> completedSessionListList=new ArrayList<>();
    CurrentSessionList currentSessionList=new CurrentSessionList();
    CompletedSessionList completedSessionList=new CompletedSessionList();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trainer_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Trainer Detail");
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
        myTrainerData = (MyTrainerListResponse.MyTrainerData) getIntent().getParcelableExtra(TRAINER);
        setInitValues();
        addToList();
        initViews();
        initViews2();
    }

    public void setInitValues() {
        if (myTrainerData.getProfile_image() != null) {
            Picasso.with(clientImage.getContext()).load(myTrainerData.getProfile_image()).into(clientImage);
        }
        typeTraining.setText(myTrainerData.getTraining_type());
        clientName.setText(myTrainerData.getFirst_name() + " " + myTrainerData.getLast_name());
        clientPhn.setText(myTrainerData.getPhone());
        clientLocation.setText(myTrainerData.getSlot_address());
        clientEmail.setText(myTrainerData.getEmail());
        if (myTrainerData.getPackageId() != null) {
            slotId = myTrainerData.getPackageId().toString();
        }
        if (myTrainerData.getCustomerId() != null) {
            customerId = myTrainerData.getCustomerId().toString();
        }
        if (myTrainerData.getTrainerId() != null) {
            trainerId = myTrainerData.getTrainerId().toString();
        }

    }

    public void addToList() {
        for (int i=0;i<myTrainerData.getSessionsList().size();i++) {
            if (myTrainerData.getSessionsList().get(i).getIsOccupied().equals("1"))
            {
                currentSessionList.setFirst_name(myTrainerData.getSessionsList().get(i).getFirst_name());
                currentSessionList.setLast_name(myTrainerData.getSessionsList().get(i).getLast_name());
                currentSessionList.setProfile_image(myTrainerData.getSessionsList().get(i).getProfile_image());
                currentSessionListList.add(currentSessionList);
            }
            else if (myTrainerData.getSessionsList().get(i).getIsOccupied().equals("2")){
                completedSessionList.setFirstName(myTrainerData.getSessionsList().get(i).getFirst_name());
                completedSessionList.setLastName(myTrainerData.getSessionsList().get(i).getLast_name());
                completedSessionList.setProfileImage(myTrainerData.getSessionsList().get(i).getProfile_image());
                completedSessionListList.add(completedSessionList);

            }
        }
        Log.d("currentSessionListList","currentSessionListList"+currentSessionListList);

    }

    private void initViews(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        currentSession.setLayoutManager(layoutManager);
        if (currentSessionListList.size()!=0) {
            CurrentSessionAdapter currentSessionAdapter = new CurrentSessionAdapter(getApplicationContext(), currentSessionListList);
            currentSession.setAdapter(currentSessionAdapter);
        }
        else {
            noCurrentSession.setVisibility(View.VISIBLE);
            currentSession.setVisibility(View.INVISIBLE);
        }
    }

    private void initViews2(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        completeSession.setLayoutManager(layoutManager);
        if (completedSessionListList.size()!=0) {
            CompleteSessionAdapter completeSessionAdapter = new CompleteSessionAdapter(getApplicationContext(), completedSessionListList);
            completeSession.setAdapter(completeSessionAdapter);
        }
        else {
            noCompleteSession.setVisibility(View.VISIBLE);
            completeSession.setVisibility(View.INVISIBLE);
        }
    }



    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.client_name)
    TextView clientName;

    @BindView(R.id.client_phn)
    TextView clientPhn;

    @BindView(R.id.client_type_training)
    TextView typeTraining;

    @BindView(R.id.client_image)
    ImageView clientImage;

    @BindView(R.id.current_items)
    RecyclerView currentSession;

    @BindView(R.id.complete_items)
    RecyclerView completeSession;

    @BindView(R.id.client_location)
    TextView clientLocation;


    @BindView(R.id.client_email)
    TextView clientEmail;

    @BindView(R.id.no_current)
    TextView noCurrentSession;

    @BindView(R.id.no_complete)
    TextView noCompleteSession;

    @OnClick(R.id.client_message)
    void sendMessage() {
        Intent intent = new Intent(getApplicationContext(), TrainerMessageActivity.class);
        intent.putExtra("trainerId",trainerId );
        startActivity(intent);
    }


}
