package idmy.murphi.moviecatalogue.data.remote.api;

import idmy.murphi.moviecatalogue.utils.LiveDataCallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static idmy.murphi.moviecatalogue.utils.Constants.BASE_URL;

public class TvShowApiClient {
    private static TvShowService sInstance;

    private static final OkHttpClient client;

    private static final Object sLock = new Object();

    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        client = new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new AuthInterceptor()).build();
    }

    public static TvShowService getClient() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = getRetrofitInstance().create(TvShowService.class);
            }
            return sInstance;
        }
    }

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(client)
                .baseUrl(BASE_URL)
                .build();
    }
}
