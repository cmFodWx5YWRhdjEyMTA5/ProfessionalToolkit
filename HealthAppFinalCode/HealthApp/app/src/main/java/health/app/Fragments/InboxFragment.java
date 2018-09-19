package health.app.Fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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


public class InboxFragment extends Fragment {

    InboxMessageAdapter inboxMessageAdapter;
    List<MessageListResponse.MessageData> messageDataList;
    private ActionMode mActionMode;

    private OnFragmentInteractionListener mListener;

    public InboxFragment() {
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_inbox, container, false);
        ButterKnife.bind(this,view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        messagesInboxRecyclerview.setLayoutManager(mLayoutManager);
        messagesInboxRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        messagesInboxRecyclerview.setItemAnimator(new DefaultItemAnimator());
        getMessageList();
        return view;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        //getMessageList();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @BindView(R.id.messages_inbox_recyclerview)
    RecyclerView messagesInboxRecyclerview;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;
}

