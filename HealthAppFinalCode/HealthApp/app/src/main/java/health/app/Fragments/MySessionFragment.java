package health.app.Fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.MySessionListAdapter;
import health.app.Model.CurrentSessionList;
import health.app.Model.StartEndTime;
import health.app.R;
import health.app.Response.MySessionResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MySessionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    MySessionListAdapter sessionListAdapter;
    public static StartEndTime startEndTime;
    List<CurrentSessionList> currentSessionListList=new ArrayList<>();
    public MySessionFragment() {
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
        View view= inflater.inflate(R.layout.fragment_my_session, container, false);
        ButterKnife.bind(this,view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        sessionRecyclerview.setLayoutManager(mLayoutManager);
        sessionRecyclerview.setItemAnimator(new DefaultItemAnimator());
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer"))
        {
            getSessionList();
        }
        else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer"))
        {
           getCustomerSessionList();
        }

        return view;
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getSessionList() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.getSessions(Preferences.getInstance().getUserId()).enqueue(new Callback<MySessionResponse>() {
            @Override
            public void onResponse(Call<MySessionResponse> call, Response<MySessionResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    if (response.body().getSessionData().getPackagesList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        sessionRecyclerview.setVisibility(View.GONE);
                    }
                    else {
                        sessionListAdapter = new MySessionListAdapter(getContext(), response.body().getSessionData().getPackagesList(),response.body().getSessionData().getImg_url(),"Trainer");
                        sessionRecyclerview.setAdapter(sessionListAdapter);
                        sessionListAdapter.notifyDataSetChanged();
                    }
                } else {
                    noRequestLayout.setVisibility(View.VISIBLE);
                    sessionRecyclerview.setVisibility(View.GONE);
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) getContext()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<MySessionResponse> call, Throwable t) {
                ((BaseActivity) getContext()).hideProgressBar();
                Toast.makeText(getContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getCustomerSessionList() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.getAllSession(Preferences.getInstance().getUserId()).enqueue(new Callback<MySessionResponse>() {
            @Override
            public void onResponse(Call<MySessionResponse> call, Response<MySessionResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    if (response.body().getSessionData().getPackagesList().size() == 0) {
                        noRequestLayout.setVisibility(View.VISIBLE);
                        sessionRecyclerview.setVisibility(View.GONE);
                    } else {
                            sessionListAdapter = new MySessionListAdapter(getContext(), response.body().getSessionData().getPackagesList(), response.body().getSessionData().getImg_url(),"Customer");
                            sessionRecyclerview.setAdapter(sessionListAdapter);
                            sessionListAdapter.notifyDataSetChanged();
                    }
                } else {
                    noRequestLayout.setVisibility(View.VISIBLE);
                    sessionRecyclerview.setVisibility(View.GONE);
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) getContext()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<MySessionResponse> call, Throwable t) {
                ((BaseActivity) getContext()).hideProgressBar();
                Toast.makeText(getContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @BindView(R.id.session_recyclerview)
    RecyclerView sessionRecyclerview;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

}

