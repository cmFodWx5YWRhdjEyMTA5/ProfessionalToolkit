package health.app.Fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.R;
import health.app.Response.MeasurementResponse;
import health.app.Response.ProfileDataResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.FragmentChangeListener;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MeasurementFragment extends Fragment {

    double waist,neck,hip,height;
    String todayString;
    String initialHeight, initialWeight,initialFat,initialNeck,initialWaist,initialHip;
    private OnFragmentInteractionListener mListener;

    public MeasurementFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_measurement, container, false);
        ButterKnife.bind(this,view);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        todayString = formatter.format(todayDate);
        Log.d("todayString","todayString"+todayString);
        setFont();
        getCustomerData();
        return view;
    }

    //    body fat calculator formula for man:
//        495/(1.0324-0.19077(LOG(waist-neck))+0.15456(LOG(height)))-450
//
//        body fat calculator formula for woman:
//            495/(1.29579-0.35004(LOG(waist+hip-neck))+0.22100(LOG(height)))-450

    public void setFont(){
        final Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-SemiBold.ttf");
        bHeight.setTypeface(font);
        bWeight.setTypeface(font);
        bBodyFat.setTypeface(font);
        bNeck.setTypeface(font);
        bHip.setTypeface(font);
        bWaist.setTypeface(font);
    }

    public void showAmount(final String key, String title) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.selectTime);
        dialogBuilder.setTitle(title);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setIcon(R.drawable.ptlogo);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(DialogInterface dialog, int whichButton) {
                if (key.equalsIgnoreCase("Height"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                    height = Double.parseDouble(edt.getText().toString());
                    bHeight.setText("Body Height " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,String.valueOf(height),initialWaist,initialNeck,initialFat,initialHip,todayString);
                }

                }
                else if (key.equalsIgnoreCase("Weight"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        bWeight.setText("Body Weight " + "\"" + edt.getText().toString() + " kg" + "\"");
                        addMeasurement(String.valueOf(edt.getText().toString()),initialHeight,initialWaist,initialNeck,initialFat,initialHip,todayString);
                    }

                }
                else if (key.equalsIgnoreCase("Neck"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        neck = Double.parseDouble(edt.getText().toString());
                        bNeck.setText("Neck Measurement " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,initialHeight,initialWaist,String.valueOf(neck),initialFat,initialHip,todayString);
                    }

                }
                else if (key.equalsIgnoreCase("Hip"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        hip = Double.parseDouble(edt.getText().toString());
                        bHip.setText("Hip Measurement " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,initialHeight,initialWaist,initialNeck,initialFat,String.valueOf(hip),todayString);
                    }

                }
                else if (key.equalsIgnoreCase("Waist"))
                {
                    if (edt.getText().toString().trim().length()!=0) {
                        waist = Double.parseDouble(edt.getText().toString());
                        bWaist.setText("Waist Measurement " + "\"" + edt.getText().toString() + " cm" + "\"");
                        addMeasurement(initialWeight,initialHeight,String.valueOf(waist),initialNeck,initialFat,initialHip,todayString);
                    }
                }


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addMeasurement(String weightValue,String heightValue,String waistValue,String neckValue,String fatValue,String hipValue,String todayDate) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.addMeasurement(Preferences.getInstance().getUserId(),weightValue,heightValue,waistValue,neckValue,fatValue,hipValue,todayDate).
                enqueue(new Callback<MeasurementResponse>() {
                    @Override
                    public void onResponse(Call<MeasurementResponse> call, Response<MeasurementResponse> response) {
                        if (response.body().isStatus()){
                            alertUser(response.body().getMsg());
                        }
                        else {
                            alertUser(response.body().getMsg());
                        }

                        ((BaseActivity) getContext()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<MeasurementResponse> call, Throwable t) {
                        ((BaseActivity) getContext()).hideProgressBar();
                        Toast.makeText(getActivity(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getCustomerData() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.getCustomerData(Preferences.getInstance().getUserId()).enqueue(new Callback<ProfileDataResponse>() {
            @Override
            public void onResponse(Call<ProfileDataResponse> call, Response<ProfileDataResponse> response) {
                if (response.body().isStatus()) {
                    if (response.body().getProfileData()!=null)
                    {
                        setCustomerData(response.body().getProfileData());
                    }
                }
                else {
                    Toast.makeText(getContext(),response.body().getMsg() , Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) getContext()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<ProfileDataResponse> call, Throwable t) {
                ((BaseActivity) getContext()).hideProgressBar();
                Toast.makeText(getContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCustomerData(ProfileDataResponse.ProfileData profileData) {
        initialHeight=profileData.getUser().getHeight();
        initialWeight=profileData.getUser().getWeight();
        initialFat=profileData.getUser().getFat();
        initialNeck=profileData.getUser().getNeck();
        initialWaist=profileData.getUser().getWaist();
        initialHip=profileData.getUser().getHips();
        bHeight.setText("Body Height "+"\""+ profileData.getUser().getHeight()+" cm"+"\"");
        bWeight.setText("Body Weight "+"\""+profileData.getUser().getWeight()+" kg"+"\"");
        bBodyFat.setText("BMI "+"\""+profileData.getUser().getFat()+"\"");
        bNeck.setText("Neck Measurement "+"\""+ profileData.getUser().getNeck()+" cm"+"\"");
        bWaist.setText("Waist Measurement "+"\""+ profileData.getUser().getWaist()+" cm"+"\"");
        bHip.setText("Waist Measurement "+"\""+ profileData.getUser().getHips()+" cm"+"\"");
    }

    private void alertUser(String msg) {
        if (msg!=null) {
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle(getString(R.string.app_name));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ptlogo);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Fragment fr = new CDashboardAgainFragment();
                    FragmentChangeListener fc = (FragmentChangeListener) getActivity();
                    fc.replaceFragment(fr, "Dashboard");
                }
            });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public boolean validation(){
        if (bNeck.getText().toString().equalsIgnoreCase("Tap Here To Add Neck Measurement")){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Neck Measurement First.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (bWaist.getText().toString().equalsIgnoreCase("Tap Here To Add Waist Measurement"))
        {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Waist Measurement.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }

        else if (bHip.getText().toString().equalsIgnoreCase("Tap Here To Add Hip Measurement")){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Hip Measurement.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
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

@OnClick(R.id.ll_height)
void getHeight(){
showAmount("Height","Enter Height like 56 cm");
}

    @OnClick(R.id.ll_weight)
    void getWeight(){
        showAmount("Weight","Enter Weight like 56 kg");
    }

    @OnClick(R.id.ll_bodyfat)
    void getBodyFat(){
if (validation())
{
    if (Preferences.getInstance().getGender().equalsIgnoreCase("Male")) {
        double BMI=495/(1.0324-0.19077*(Math.log10(waist-neck))+0.15456*(Math.log10(height)))-450;
        String updatedBMI= String.valueOf(BMI);
        bBodyFat.setText("BMI "+"\""+BMI+"\"");
    }
    else if (Preferences.getInstance().getGender().equalsIgnoreCase("Female"))
    {
        double BMI= 495/(1.29579-0.35004*(Math.log10(waist+hip-neck))+0.22100*(Math.log10(height)))-450;
        bBodyFat.setText("BMI "+"\""+BMI+"\"");
        String updatedBMI= String.valueOf(BMI);
    }
}
    }

    @OnClick(R.id.ll_neck)
    void getNeck(){
        showAmount("Neck","Enter Neck size like 56 cm");
    }

    @OnClick(R.id.ll_waist)
    void getWaist(){
        showAmount("Waist","Enter Waist size like 56 cm");
    }

    @OnClick(R.id.ll_hip)
    void getHip(){
        showAmount("Hip","Enter Hip size like 56 cm");
    }

    @BindView(R.id.bHeight)
    TextView bHeight;

    @BindView(R.id.bWeight)
    TextView bWeight;

    @BindView(R.id.bBodyFat)
    TextView bBodyFat;

    @BindView(R.id.bHip)
    TextView bHip;

    @BindView(R.id.bWaist)
    TextView bWaist;

    @BindView(R.id.bNeck)
    TextView bNeck;

    @BindView(R.id.container)
    LinearLayout container;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
