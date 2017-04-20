package ru.sinjvf.testtranslate.retrofit;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import ru.sinjvf.testtranslate.retrofit.responses.GetLangsResponse;
import ru.sinjvf.testtranslate.retrofit.responses.SuperResponse;
import ru.sinjvf.testtranslate.retrofit.responses.TranslateResponse;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class ServerHandler {

    protected final String TAG = "My_Tag:"+getClass().getSimpleName();
    private ServerContract.TranslateAPI service;

    public ServerHandler() {
        service = ServerContract.getTranslateService();
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

    public void translate(String text, String pairStr, ServerCallback<TranslateResponse> callback) {
        Log.d(TAG, "translate: ");
        Map<String, String> map = new HashMap<>();
        map.put(ServerContract.TEXT, text);
        map.put(ServerContract.LANG, pairStr);
        map.put(ServerContract.KEY, ServerContract.KEY_TRANSLATE);
        callService(callback, () -> service.translate(map));
    }


    public void getLang(String ui, ServerCallback<GetLangsResponse> callback) {
        Log.d(TAG, "getLang: ");
        Map<String, String> map = new HashMap<>();
        map.put(ServerContract.UI, ui);
        map.put(ServerContract.KEY, ServerContract.KEY_TRANSLATE);
        callService(callback, () -> service.getlangs(map));
    }

    public void detectLang(String text, String hint, ServerCallback<TranslateResponse> callback) {
        Log.d(TAG, "getLang: ");
        Map<String, String> map = new HashMap<>();
        map.put(ServerContract.TEXT, text);
        map.put(ServerContract.HINT, hint);
        map.put(ServerContract.KEY, ServerContract.KEY_TRANSLATE);
        callService(callback, () -> service.detectlang(map));
    }
}
