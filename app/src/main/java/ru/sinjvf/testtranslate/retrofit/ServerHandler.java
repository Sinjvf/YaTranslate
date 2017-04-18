package ru.sinjvf.testtranslate.retrofit;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import ru.sinjvf.testtranslate.data.LangPair;
import ru.sinjvf.testtranslate.retrofit.responses.GetLangsResponse;
import ru.sinjvf.testtranslate.retrofit.responses.SuperResponse;
import ru.sinjvf.testtranslate.retrofit.responses.TranslateResponse;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class ServerHandler {

    protected final String TAG = "My_Tag:"+getClass().getSimpleName();
    private ServerContract.ProfileAPI service;

    public ServerHandler() {
        service = ServerContract.getService();
    }


    public interface Invocation<R extends SuperResponse> {
        Call<R> invoke();
    }

    public <R extends SuperResponse> void callService(ServerCallback<R> callback, Invocation<R> invocation){
      /*  invocation.invoke()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);*/

        Call<R> call = invocation.invoke();
        call.enqueue(callback);

    }

    public void translate(String text, LangPair pair, ServerCallback<TranslateResponse> callback) {
        Log.d(TAG, "translate: ");
        String lang = pair.getStr();
        Map<String, String> map = new HashMap<>();
        map.put(ServerContract.TEXT, text);
        map.put(ServerContract.LANG, lang);
        map.put(ServerContract.KEY, ServerContract.KEY_VALUE);
        callService(callback, () -> service.translate(map));
    }


    public void getLang(String ui, ServerCallback<GetLangsResponse> callback) {
        Log.d(TAG, "getLang: ");
        Map<String, String> map = new HashMap<>();
        map.put(ServerContract.UI, ui);
        map.put(ServerContract.KEY, ServerContract.KEY_VALUE);
        callService(callback, () -> service.getlangs(map));
    }
}
