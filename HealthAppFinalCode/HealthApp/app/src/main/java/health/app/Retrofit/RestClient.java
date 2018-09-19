package health.app.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Developer Six on 8/8/2017.
 */

public class  RestClient {
    public static String BASE_URL = "https://www.ptmobileapp.com/api/";
    private static Retrofit retrofit = null;
    private static final String TAG = "RestClient";
    private static HealthAppService REST_CLIENT = null;
    public static HealthAppService get() {
        REST_CLIENT= getClient().create(HealthAppService.class);
        return REST_CLIENT;
    }
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES).connectTimeout(5,TimeUnit.MINUTES);
        httpClient.addInterceptor(logging);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

}
