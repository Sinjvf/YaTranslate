package ru.sinjvf.testtranslate.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sinjvf.testtranslate.retrofit.responses.SuperResponse;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class ServerCallback<R extends SuperResponse> implements Callback<R> {

    protected final String TAG = "My_Tag:"+getClass().getSimpleName();
    public void onSuccess(R response){}
    public void onError(ResponseBody response){}

    @Override
    public void onResponse(Call<R> call, Response<R> response) {
        if (response.isSuccessful()){
            onSuccess(response.body());
        }else {
            onError((response.errorBody()));
        }

    }

    @Override
    public void onFailure(Call<R> call, Throwable t) {

    }
}
