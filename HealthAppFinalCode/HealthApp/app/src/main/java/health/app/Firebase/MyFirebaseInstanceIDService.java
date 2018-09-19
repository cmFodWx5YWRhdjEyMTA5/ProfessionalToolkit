package health.app.Firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import health.app.Utilities.Preferences;

/**
 * Created by Developer Six on 8/7/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Preferences.getInstance().setDevice_token(refreshedToken);
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        Preferences.getInstance().setDevice_token(token);
    }
}