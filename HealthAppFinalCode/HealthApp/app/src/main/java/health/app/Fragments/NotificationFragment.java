package health.app.Fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import health.app.Activities.CustomerDashboardActivity;
import health.app.Activities.TrainerDashboardActivity;
import health.app.Adapter.NotificationAdapter;
import health.app.Model.DateItem;
import health.app.Model.GeneralItem;
import health.app.Model.ListItem;
import health.app.Model.PojoOfJsonArray;
import health.app.R;
import health.app.Response.NotificationResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private List<PojoOfJsonArray> myOptions = new ArrayList<>();
    List<ListItem> consolidatedList = new ArrayList<>();
    NotificationAdapter notificationAdapter;
    public NotificationFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this,view);
        setUpRecyclerView(view);
        getNotificationList();
        return view;
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

    private void setUpRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.sectioned_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getNotificationList() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        healthAppService.getNotifications(Preferences.getInstance().getUserId()).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.body() != null) {
                    if (response.body().getNotificationDataList().size()==0){
                        noRequestLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else {
                        for (int i=0;i<response.body().getNotificationDataList().size();i++) {
                            String date=response.body().getNotificationDataList().get(i).getCreatedOn();
                            String[] ary = date.split("\\s+");
                            String onlyDate=ary[0];
                            Log.d("onlyDate","onlyDate"+onlyDate);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date addDate = null;
                            try {
                                addDate = dateFormat.parse(onlyDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                           // myOptions.add(new PojoOfJsonArray(response.body().getNotificationDataList().get(i).getRequestId(),response.body().getNotificationDataList().get(i).getSessionSlotId(),response.body().getNotificationDataList().get(i).getNId(),"1",response.body().getNotificationDataList().get(i).getNFromId(),response.body().getNotificationDataList().get(i).getFirstName()+" "+response.body().getNotificationDataList().get(i).getLastName(),onlyDate,response.body().getNotificationDataList().get(i).getNType(),response.body().getNotificationDataList().get(i).getNTitle(),response.body().getNotificationDataList().get(i).getProfileImage()));
                            myOptions.add(new PojoOfJsonArray(response.body().getNotificationDataList().get(i).getRequestId(),response.body().getNotificationDataList().get(i).getSessionSlotId(),response.body().getNotificationDataList().get(i).getNId(),"1",response.body().getNotificationDataList().get(i).getNFromId(),response.body().getNotificationDataList().get(i).getFirstName()+" "+response.body().getNotificationDataList().get(i).getLastName(),date,response.body().getNotificationDataList().get(i).getNType(),response.body().getNotificationDataList().get(i).getNTitle(),response.body().getNotificationDataList().get(i).getProfileImage()));
                        }
                        TreeMap<String, List<PojoOfJsonArray>> groupedHashMap = groupDataIntoHashMap(myOptions);


                        for (String date : groupedHashMap.keySet()) {
                            DateItem dateItem = new DateItem();
                            dateItem.setDate(date);
                            consolidatedList.add(dateItem);

                            for (PojoOfJsonArray pojoOfJsonArray : groupedHashMap.get(date)) {
                                GeneralItem generalItem = new GeneralItem();
                                generalItem.setPojoOfJsonArray(pojoOfJsonArray);//setBookingDataTabs(bookingDataTabs);
                                consolidatedList.add(generalItem);
                            }
                        }
                        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")) {
                            notificationAdapter = new NotificationAdapter(getContext(), consolidatedList, NotificationFragment.this,"Customer", ((CustomerDashboardActivity) getContext()));
                            recyclerView.setAdapter(notificationAdapter);
                        }
                        else {
                            Log.d("consolidatedList","consolidatedList"+consolidatedList);
                            notificationAdapter = new NotificationAdapter(getContext(), consolidatedList, NotificationFragment.this,"Trainer", ((TrainerDashboardActivity) getContext()));
                            recyclerView.setAdapter(notificationAdapter);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) getActivity()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                ((BaseActivity) getActivity()).hideProgressBar();
                Toast.makeText(getContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private TreeMap<String, List<PojoOfJsonArray>> groupDataIntoHashMap(List<PojoOfJsonArray> listOfPojosOfJsonArray) {

        TreeMap<String, List<PojoOfJsonArray>> groupedHashMap = new TreeMap<>(Collections.reverseOrder());

        for (PojoOfJsonArray pojoOfJsonArray : listOfPojosOfJsonArray) {

            String hashMapKey = pojoOfJsonArray.getDate();

            if (groupedHashMap.containsKey(hashMapKey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.
                groupedHashMap.get(hashMapKey).add(pojoOfJsonArray);
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                List<PojoOfJsonArray> list = new ArrayList<>();
                list.add(pojoOfJsonArray);
                groupedHashMap.put(hashMapKey, list);
            }
        }


        return groupedHashMap;
    }

    @BindView(R.id.no_request)
    RelativeLayout noRequestLayout;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
