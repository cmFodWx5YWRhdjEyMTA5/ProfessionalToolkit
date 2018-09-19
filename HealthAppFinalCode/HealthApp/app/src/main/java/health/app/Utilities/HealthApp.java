package health.app.Utilities;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import health.app.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by Developer Six on 8/5/2017.
 */

public class HealthApp extends MultiDexApplication {

    private static HealthApp instance;
    public String SERVER_ERROR = "Unable to connect to server, please try again after sometime";
    private double lat, lng;
    private LatLng appLatLng=null,currentLatng=null;

    @Override

    public void onCreate() {
        super.onCreate();
        instance = this;
        JodaTimeAndroid.init(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Poppins-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static HealthApp getInstance() {
        return instance;
    }

    public LatLng getAppLatLng() {
        return appLatLng;
    }

    public void setAppLatLng(LatLng appLatLng) {
        this.appLatLng = appLatLng;
    }

    public LatLng getCurrentLatng() {
        return currentLatng;
    }

    public void setCurrentLatng(LatLng currentLatng) {
        this.currentLatng = currentLatng;
    }

    public File reduceImageSize(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 150;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);

            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);

            return file;
        } catch (Exception e) {
            Toast.makeText(instance, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
