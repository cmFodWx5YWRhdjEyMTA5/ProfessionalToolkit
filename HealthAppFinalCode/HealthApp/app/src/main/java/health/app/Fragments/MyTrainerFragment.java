package health.app.Fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.TrainerListAdapter;
import health.app.R;
import health.app.Response.MyTrainerListResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyTrainerFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    TrainerListAdapter trainerListAdapter;
    public MyTrainerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_trainer, container, false);
        ButterKnife.bind(this,view);
        initViews(view);
        getTrainerList();
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getTrainerList() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        healthAppService.getTrainer(Preferences.getInstance().getUserId()).enqueue(new Callback<MyTrainerListResponse>() {
            @Override
            public void onResponse(Call<MyTrainerListResponse> call, Response<MyTrainerListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getMyTrainerDataList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else {
                    trainerListAdapter = new TrainerListAdapter(getContext(),response.body().getMyTrainerDataList(),"");
                    recyclerView.setAdapter(trainerListAdapter);}
                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) getActivity()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<MyTrainerListResponse> call, Throwable t) {
                ((BaseActivity) getActivity()).hideProgressBar();
                Toast.makeText(getContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initViews(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        //recyclerView.setLayoutManager(layoutManager);
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


    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;
}
