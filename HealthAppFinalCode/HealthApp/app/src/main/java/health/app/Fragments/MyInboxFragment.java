package health.app.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Activities.AddMessageActivity;
import health.app.Activities.CustomerDashboardActivity;
import health.app.Activities.TrainerDashboardActivity;
import health.app.Adapter.InboxMessageAdapter;
import health.app.R;
import health.app.Response.MessageListResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInboxFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    InboxMessageAdapter inboxMessageAdapter;
    List<MessageListResponse.MessageData> messageDataList;

    public MyInboxFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_inbox, container, false);
        ButterKnife.bind(this,view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        messagesInboxRecyclerview.setLayoutManager(mLayoutManager);
        messagesInboxRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        messagesInboxRecyclerview.setItemAnimator(new DefaultItemAnimator());
        if (Preferences.getInstance().getUserType().equals("Trainer"))
        {
            TrainerDashboardActivity.bottomNavigationView.setVisibility(View.GONE);
        }
        else if (Preferences.getInstance().getUserType().equals("Customer"))
        {
            CustomerDashboardActivity.bottomNavigationView.setVisibility(View.GONE);
        }
        getMessageList();
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.bell).setVisible(false).setEnabled(false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_message, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_message:
                if (Preferences.getInstance().getUserType().equals("Trainer")) {
                    startActivity(new Intent(getActivity(), AddMessageActivity.class));
                }
                else if (Preferences.getInstance().getUserType().equals("Customer"))
                {
                    startActivity(new Intent(getActivity(), AddMessageActivity.class));
                }
                break;
        }
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getMessageList() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.getInboxMessage(Preferences.getInstance().getUserId()).enqueue(new Callback<MessageListResponse>() {
            @Override
            public void onResponse(Call<MessageListResponse> call, Response<MessageListResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    if (response.body().getMessageDataList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        messagesInboxRecyclerview.setVisibility(View.GONE);
                    }
                    else {
                        messageDataList=response.body().getMessageDataList();
                        inboxMessageAdapter = new InboxMessageAdapter(getContext(), messageDataList);
                        messagesInboxRecyclerview.setAdapter(inboxMessageAdapter);
                        inboxMessageAdapter.notifyDataSetChanged();
                    }
                } else {
                    noRequestLayout.setVisibility(View.VISIBLE);
                    messagesInboxRecyclerview.setVisibility(View.GONE);
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) getContext()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<MessageListResponse> call, Throwable t) {
                ((BaseActivity) getContext()).hideProgressBar();
                Toast.makeText(getContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @BindView(R.id.messages_inbox_recyclerview)
    RecyclerView messagesInboxRecyclerview;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        getMessageList();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
