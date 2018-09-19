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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Adapter.MyCompleteSessionAdapter;
import health.app.Adapter.MySessionListAdapter;
import health.app.Model.CompletedSessionList;
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


public class MyCompleteSessionFragment extends Fragment {
    MySessionListAdapter sessionListAdapter;
    MyCompleteSessionAdapter myCompleteSessionAdapter;
    private OnFragmentInteractionListener mListener;
    List<CompletedSessionList> completedSessionListList=new ArrayList<>();

    public MyCompleteSessionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_complete_session, container, false);
        ButterKnife.bind(this,view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        sessionRecyclerview.setLayoutManager(mLayoutManager);
        sessionRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        sessionRecyclerview.setItemAnimator(new DefaultItemAnimator());
        noRequestLayout.setVisibility(View.VISIBLE);
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer"))
        {
            getTrainerCompleteSession();
        }
        else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer"))
        {

            getCustomerCompleteSession();
        }

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getCustomerCompleteSession() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.customerCompleteSession(Preferences.getInstance().getUserId()).enqueue(new Callback<MySessionResponse>() {
            @Override
            public void onResponse(Call<MySessionResponse> call, Response<MySessionResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    if (response.body().getSessionData().getPackagesList().size() != 0) {
                        for (int i = 0; i < response.body().getSessionData().getPackagesList().size(); i++) {
                            if (response.body().getSessionData().getPackagesList().get(i).getOccupiedList().size() == 0) {
                                noRequestLayout.setVisibility(View.VISIBLE);
                                sessionRecyclerview.setVisibility(View.GONE);
                            } else {
                                noRequestLayout.setVisibility(View.GONE);
                                sessionRecyclerview.setVisibility(View.VISIBLE);
                                myCompleteSessionAdapter = new MyCompleteSessionAdapter(getContext(), response.body().getSessionData().getPackagesList(), response.body().getSessionData().getImg_url(), "Customer");
                                sessionRecyclerview.setAdapter(myCompleteSessionAdapter);
                                myCompleteSessionAdapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        noRequestLayout.setVisibility(View.VISIBLE);
                        sessionRecyclerview.setVisibility(View.GONE);
                    }
                }
                 else {
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
    private void getTrainerCompleteSession() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.trainerCompleteSession(Preferences.getInstance().getUserId()).enqueue(new Callback<MySessionResponse>() {
            @Override
            public void onResponse(Call<MySessionResponse> call, Response<MySessionResponse> response) {
                if (response.body() != null && response.body().isStatus()) {
                    if (response.body().getSessionData().getPackagesList().size()!=0){
                        for (int i = 0; i < response.body().getSessionData().getPackagesList().size(); i++) {
                            if (response.body().getSessionData().getPackagesList().get(i).getOccupiedList().size() == 0) {
                                noRequestLayout.setVisibility(View.VISIBLE);
                                sessionRecyclerview.setVisibility(View.GONE);
                            } else {
                                noRequestLayout.setVisibility(View.GONE);
                                sessionRecyclerview.setVisibility(View.VISIBLE);
                                myCompleteSessionAdapter = new MyCompleteSessionAdapter(getContext(), response.body().getSessionData().getPackagesList(), response.body().getSessionData().getImg_url(), "Trainer");
                                sessionRecyclerview.setAdapter(myCompleteSessionAdapter);
                                myCompleteSessionAdapter.notifyDataSetChanged();
                            }
                        }}
                    else {
                            noRequestLayout.setVisibility(View.VISIBLE);
                            sessionRecyclerview.setVisibility(View.GONE);
                        }}
//                            for (int j=0;j<response.body().getSessionData().getPackagesList().get(i).getOccupiedList().size();j++) {
//                                if (response.body().getSessionData().getPackagesList().get(i).getOccupiedList().get(j).getStatus().equals("2")) {
//                                    CompletedSessionList completedSessionList = new CompletedSessionList();
//                                    completedSessionList.setFirstName(response.body().getSessionData().getPackagesList().get(i).getFirst_name());
//                                    completedSessionList.setLastName(response.body().getSessionData().getPackagesList().get(i).getLast_name());
//                                    completedSessionList.setProfileImage(response.body().getSessionData().getPackagesList().get(i).getProfile_image());
//                                    completedSessionList.setImageUrl(response.body().getSessionData().getImg_url());
//                                    completedSessionList.setPhone(response.body().getSessionData().getPackagesList().get(i).getPhone());
//                                    completedSessionList.setNoOfSlots(response.body().getSessionData().getPackagesList().get(i).getNoOfSlots());
//                                    completedSessionList.setSessionPrice(response.body().getSessionData().getPackagesList().get(i).getSessionPrice());
//                                    completedSessionList.setSessionTime(response.body().getSessionData().getPackagesList().get(i).getSessionTime());
//                                    completedSessionList.setSessionDays(response.body().getSessionData().getPackagesList().get(i).getSessionDays());
//                                    completedSessionList.setSessionDate(response.body().getSessionData().getPackagesList().get(i).getOccupiedList().get(j).getSessionDate());
//                                    completedSessionList.setStartTime(response.body().getSessionData().getPackagesList().get(i).getOccupiedList().get(j).getStartTime());
//                                    completedSessionList.setEndTime(response.body().getSessionData().getPackagesList().get(i).getOccupiedList().get(j).getEndTime());
//                                    completedSessionList.setStatus(response.body().getSessionData().getPackagesList().get(i).getOccupiedList().get(j).getStatus());
//                                    completedSessionListList.add(completedSessionList);
//                                    if (completedSessionListList.size() != 0) {
//                                        noRequestLayout.setVisibility(View.GONE);
//                                        myCompleteSessionAdapter = new MyCompleteSessionAdapter(getContext(), completedSessionListList);
//                                        sessionRecyclerview.setAdapter(myCompleteSessionAdapter);
//                                        myCompleteSessionAdapter.notifyDataSetChanged();
//                                    } else {
//                                        noRequestLayout.setVisibility(View.VISIBLE);
//                                        sessionRecyclerview.setVisibility(View.GONE);
//                                    }
//                                }
//                            }
                else {
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

    @BindView(R.id.session_recyclerview)
    RecyclerView sessionRecyclerview;

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
