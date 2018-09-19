package health.app.Fragments;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.ReportAdapter;
import health.app.R;
import health.app.Response.ReportListResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ReportAdapter reportAdapter;
    public ReportsFragment() {
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
        View view= inflater.inflate(R.layout.fragment_reports, container, false);
        ButterKnife.bind(this,view);
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")) {
//            getReport();
            noRequestLayout.setVisibility(View.VISIBLE);
       }
       else {
            noRequestLayout.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getReport() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        sessionRecyclerview.setLayoutManager(mLayoutManager);
        sessionRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        sessionRecyclerview.setItemAnimator(new DefaultItemAnimator());
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.getReport(Preferences.getInstance().getUserId()).enqueue(new Callback<ReportListResponse>() {
            @Override
            public void onResponse(Call<ReportListResponse> call, Response<ReportListResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    if (response.body().getReportDataList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        sessionRecyclerview.setVisibility(View.GONE);
                    }
                    else {
                        reportAdapter = new ReportAdapter(getContext(),response.body().getReportDataList());
                        sessionRecyclerview.setAdapter(reportAdapter);
                        reportAdapter.notifyDataSetChanged();

                    }
                } else {
                    noRequestLayout.setVisibility(View.VISIBLE);
                    sessionRecyclerview.setVisibility(View.GONE);
                }
                ((BaseActivity) getContext()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<ReportListResponse> call, Throwable t) {
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

    @BindView(R.id.session_recyclerview)
    RecyclerView sessionRecyclerview;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
