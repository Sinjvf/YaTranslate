package ru.sinjvf.testtranslate.retrofit;

import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sinjvf.testtranslate.main.pages.SuperPageView;
import ru.sinjvf.testtranslate.retrofit.responses.SuperResponse;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class ServerCallback<R extends SuperResponse> implements Callback<R> {

    protected final String TAG = "My_Tag:" + getClass().getSimpleName();
    private SuperPageView view;

    public ServerCallback(SuperPageView view) {
        this.view = view;
    }

    public void onSuccess(R response) {
    }

    public void onError(ResponseBody response) {

        if (view == null) return;
        Gson gson = new Gson();
        try {
            String respStr = response.string();
            SuperResponse resp = gson.fromJson(respStr, SuperResponse.class);
            switch (resp.getCode()) {
                case 401:
                    view.showSnack(ru.sinjvf.testtranslate.R.string.err_wrong_key);
                    break;
                case 402:
                    view.showSnack(ru.sinjvf.testtranslate.R.string.err_invalid_key);
                    break;
                case 404:
                    view.showSnack(ru.sinjvf.testtranslate.R.string.err_too_many);
                    break;
                case 413:
                    view.showSnack(ru.sinjvf.testtranslate.R.string.err_long_text);
                    break;
                case 422:
                    view.showSnack(ru.sinjvf.testtranslate.R.string.err_cant_translate);
                    break;
                case 501:
                    view.showSnack(ru.sinjvf.testtranslate.R.string.err_wrong_lang);
                    break;

            }
        } catch (Exception e) {
            view.showSnack(ru.sinjvf.testtranslate.R.string.err_unfortunate);

        }
    }

    @Override
    public void onResponse(Call<R> call, Response<R> response) {
        if (view!=null) view.showProgress(false);
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onError((response.errorBody()));
        }

    }

    @Override
    public void onFailure(Call<R> call, Throwable t) {
        if (view == null) return;
        view.showProgress(false);
        view.showSnack(ru.sinjvf.testtranslate.R.string.err_unfortunate);
    }
}
