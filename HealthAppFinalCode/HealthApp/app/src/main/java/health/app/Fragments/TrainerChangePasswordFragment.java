package health.app.Fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Activities.TrainerDashboardActivity;
import health.app.R;
import health.app.Response.NewMessageResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.FragmentChangeListener;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrainerChangePasswordFragment extends Fragment {

       private OnFragmentInteractionListener mListener;

    public TrainerChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_trainer_change_password, container, false);
        TrainerDashboardActivity.bottomNavigationView.setVisibility(View.GONE);
        ButterKnife.bind(this,view);
        addTypeFace();
        return view;
    }

    private void addTypeFace() {
        Typeface typefaceOld = oldPassword.getTypeface();
        oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        oldPassword.setTypeface(typefaceOld);
        oldPwdLayout.setTypeface(typefaceOld);

        Typeface typefaceNew = newPassword.getTypeface();
        newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newPassword.setTypeface(typefaceNew);
        newPwdLayout.setTypeface(typefaceNew);

        Typeface typefaceConNew = newConPassword.getTypeface();
        newConPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newConPassword.setTypeface(typefaceConNew);
        conNewPwdLayout.setTypeface(typefaceConNew);
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


    public boolean validation() {
        if (oldPassword.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Old Password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (newPassword.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter New Password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (newPassword.getText().toString().trim().length()!=6) {
            Snackbar snackbar = Snackbar.make(container, "Your new password must be 6 characters long.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (newConPassword.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Confirm Password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (newConPassword.getText().toString().trim().length()!=6) {
            Snackbar snackbar = Snackbar.make(container, "Your confirm password must be 6 characters long.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if(!newPassword.getText().toString().trim().matches(newConPassword.getText().toString().trim())){
            Snackbar snackbar = Snackbar.make(container, "Your confirm password not match with new password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changePassword() {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        ((BaseActivity) getContext()).showProgressbar("Loading", "Please wait...");
        healthAppService.changePassword(Preferences.getInstance().getUserId(),String.valueOf(oldPassword.getText()),String.valueOf(newPassword.getText())).
                enqueue(new Callback<NewMessageResponse>() {
                    @Override
                    public void onResponse(Call<NewMessageResponse> call, Response<NewMessageResponse> response) {
                        if (response.body().isStatus()){
                            alertUser(response.body().getMsg());
                        }
                        else {
                            alertUser(response.body().getMsg());
                        }


                        ((BaseActivity) getContext()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<NewMessageResponse> call, Throwable t) {
                        ((BaseActivity) getContext()).hideProgressBar();
                    }
                });
    }

    private void alertUser(String msg) {
        if (msg!=null){
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle(getString(R.string.app_name));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ptlogo);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Fragment fr = new MyCustomerFragment();
                    FragmentChangeListener fc = (FragmentChangeListener) getActivity();
                    fc.replaceFragment(fr, "Dashboard");
                }
            });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_submit)
    void submitPassword(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        if (validation()) {
            changePassword();
        }
    }

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.input_old_pwd)
    EditText oldPassword;

    @BindView(R.id.input_new_pwd)
    EditText newPassword;

    @BindView(R.id.input_con_new_pwd)
    EditText newConPassword;

    @BindView(R.id.input_layout_old_pwd)
    TextInputLayout oldPwdLayout;

    @BindView(R.id.input_layout_new_pwd)
    TextInputLayout newPwdLayout;

    @BindView(R.id.input_layout_con_new_pwd)
    TextInputLayout conNewPwdLayout;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
