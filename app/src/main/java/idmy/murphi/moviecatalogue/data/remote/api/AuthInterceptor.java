package idmy.murphi.moviecatalogue.data.remote.api;

import androidx.annotation.NonNull;

import java.io.IOException;

import idmy.murphi.moviecatalogue.utils.Constants;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();

        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .build();

        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
