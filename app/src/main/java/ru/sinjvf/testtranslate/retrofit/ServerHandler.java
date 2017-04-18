package ru.sinjvf.testtranslate.retrofit;

import java.util.HashMap;
import java.util.Map;

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

    private ServerContract.ProfileAPI service;

    public ServerHandler() {
        service = ServerContract.getService();
    }


    public interface Invocation<R extends SuperResponse> {
        Observable<R> invoke();
    }

    public <R extends SuperResponse> void callService(ServerCallback<R> callback, Invocation<R> invocation){
        invocation.invoke()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);

    }

    public void translate(String text, String langFrom, String langTo, ServerCallback<TranslateResponse> callback) {
        String lang = langFrom.concat("-").concat(langTo);
        Map<String, String> map = new HashMap<>();
        map.put(ServerContract.TEXT, text);
        map.put(ServerContract.LANG, lang);
        map.put(ServerContract.KEY, ServerContract.KEY_VALUE);
        callService(callback, () -> service.translate(map));
    }


    public void getLang(String ui, ServerCallback<GetLangsResponse> callback) {

        Map<String, String> map = new HashMap<>();
        map.put(ServerContract.UI, ui);
        map.put(ServerContract.KEY, ServerContract.KEY_VALUE);
        callService(callback, () -> service.getlangs(map));
    }
}
