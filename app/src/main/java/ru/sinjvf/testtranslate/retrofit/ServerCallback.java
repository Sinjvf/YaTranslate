package ru.sinjvf.testtranslate.retrofit;

import ru.sinjvf.testtranslate.retrofit.responses.SuperResponse;
import rx.Observer;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class ServerCallback<R extends SuperResponse> implements Observer<R> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(R r) {

    }
}
