package ru.sinjvf.testtranslate.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sinjvf.testtranslate.retrofit.responses.SuperResponse;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class ServerCallback<R extends SuperResponse> implements Callback<R> {

    public void onSuccess(Response<R> response){}

    @Override
    public void onResponse(Call<R> call, Response<R> response) {
        onSuccess(response);
    }

    @Override
    public void onFailure(Call<R> call, Throwable t) {

    }
}
