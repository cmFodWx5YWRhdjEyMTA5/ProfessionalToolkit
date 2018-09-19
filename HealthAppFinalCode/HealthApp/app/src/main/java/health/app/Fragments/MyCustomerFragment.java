package health.app.Fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.ClientListAdapter;
import health.app.R;
import health.app.Response.TrainerDashboardListResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyCustomerFragment extends Fragment {
    TextView notifCount;
    private OnFragmentInteractionListener mListener;
    ClientListAdapter dataAdapter;

    public MyCustomerFragment() {
    }
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dashboard_new, container, false);
        ButterKnife.bind(this,view);
        initViews(view);
        getClientList();
        return view;
    }





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getClientList() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.getClients(Preferences.getInstance().getUserId()).enqueue(new Callback<TrainerDashboardListResponse>() {
            @Override
            public void onResponse(Call<TrainerDashboardListResponse> call, Response<TrainerDashboardListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getApprovedDataList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else {
                        dataAdapter = new ClientListAdapter(getContext(), response.body().getApprovedDataList());
                        recyclerView.setAdapter(dataAdapter);
                    }
                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) getContext()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<TrainerDashboardListResponse> call, Throwable t) {
                ((BaseActivity) getContext()).hideProgressBar();
                Toast.makeText(getContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initViews(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
//        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.bell).setVisible(false).setEnabled(false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_message, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

}
