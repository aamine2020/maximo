package controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
/*
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
logging.setLevel(Level.BODY);

    OkHttpClient httpClient = new OkHttpClient();
// add your other interceptors …

// add logging as last interceptor
httpClient.interceptors().add(logging);  // <-- this is the important line!

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(LoginActivity.URL_LOGIN)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();
}

*/
/*
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserServiceClient {
    private static UserService service;

    public static UserService getClient(String url) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();


   return      service = retrofit.create(UserService.class);


    }
}

*/