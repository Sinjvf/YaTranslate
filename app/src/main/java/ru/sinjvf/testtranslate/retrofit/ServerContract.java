package ru.sinjvf.testtranslate.retrofit;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import ru.sinjvf.testtranslate.retrofit.responses.GetLangsResponse;
import ru.sinjvf.testtranslate.retrofit.responses.TranslateResponse;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class ServerContract {
    private static final String TRANSLATE_HOST_NAME = "translate.yandex.net";
    private static final String TRANSLATE_BASE_URL = "https://" + TRANSLATE_HOST_NAME + "/api/v1.5/tr.json/";

    private static final String TRANSLATE = "translate";
    private static final String GET_LANGS = "getLangs";
    private static final String DETECT_LANG = "detect";
    //   private static final String LOGIN = "app/login";

    public static final String TEXT = "text";
    public static final String LANG = "lang";
    public static final String KEY = "key";
    public static final String UI = "ui";
    public static final String HINT = "hint";
    public static final String KEY_TRANSLATE = "trnsl.1.1.20170417T073908Z.04b66ff6448be705.7f8f88b33b21f34a73e395378213d69d59f8a4da";
    public static final String KEY_DICTIONARY = "dict.1.1.20170420T082837Z.1b56b16e88d82c32.908a337fda5684a353a4082ca581357c181b4059";

    public interface TranslateAPI {
        @POST(TRANSLATE)
        Call<TranslateResponse> translate(@QueryMap Map<String, String> params);

        @POST(GET_LANGS)
        Call<GetLangsResponse> getlangs(@QueryMap Map<String, String> params);

        @POST(DETECT_LANG)
        Call<TranslateResponse> detectlang(@QueryMap Map<String, String> params);
    }

    private static class ServiceGenerator {

        private static <S> S createService(String baseUrl, Class<S> serviceClass) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create(new Gson()))
                            .baseUrl(baseUrl);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .header("Accept", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            //        }
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // logging
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
            OkHttpClient client = httpClient.build();
            Retrofit retrofit;
            retrofit = builder.client(client).build();
            return retrofit.create(serviceClass);
        }
    }

    public static TranslateAPI getTranslateService() {
        return ServerContract.ServiceGenerator.createService(TRANSLATE_BASE_URL, TranslateAPI.class);
    }
}
