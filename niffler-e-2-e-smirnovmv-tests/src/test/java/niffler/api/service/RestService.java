package niffler.api.service;

import niffler.allure.AllureOkHttp3Custom;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public abstract class RestService {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new AllureOkHttp3Custom())
            .build();

    private final String restServiceUrl;

    protected final Retrofit retrofit;

    protected RestService(String restServiceUrl) {
        this.restServiceUrl = restServiceUrl;
        this.retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(this.restServiceUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
