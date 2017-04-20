package ru.sinjvf.testtranslate.main.pages;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.sinjvf.testtranslate.R;
import ru.sinjvf.testtranslate.main.TranslateApplication;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Sinjvf on 17.04.2017.
 * SuperClass for ViewPager's fragments
 */

public abstract class SuperPageFragment<V extends SuperPageView, P extends SuperPagePresenter<V>> extends MvpFragment<V, P> implements SuperPageView {

    protected final String TAG = "My_Tag:"+getClass().getSimpleName();
    protected Unbinder unbinder;
    protected CompositeSubscription subs = new CompositeSubscription();
    private View rootView;

    @BindView(R.id.license)
    TextView licenseView;

    //pict in viewPager tab
    @Override
    public Drawable getPict(Resources res) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return res.getDrawable(getIconId(), null);
        } else {
            return res.getDrawable(getIconId());
        }
    }

    @Override
    public void showSnack(int strId) {
        String str = getString(strId);
        Snackbar.make(rootView, str, Snackbar.LENGTH_LONG).show();
    }

    //text in toolbar
    @Override
    public String getTitle(Resources res) {
        return res.getString(getTitleId());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setSpan();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (subs != null)
            subs.unsubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }

    //set spanned text with link needed in license
    protected void setSpan() {
        Log.d(TAG, "setSpan: ");
        String text = getString(R.string.license)+" "+getString(R.string.service_name);
        Spannable spannableLicence = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                String licenseUrl =  getString(R.string.license_url);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(licenseUrl));
                startActivity(browserIntent);
            }
        };
        int posStart = text.indexOf(getString(R.string.service_name));
        int posEnd = text.length();
        spannableLicence.setSpan(new UnderlineSpan(), posStart, posEnd,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        spannableLicence.setSpan(clickableSpan, posStart, posEnd,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        licenseView.setText(spannableLicence);
        licenseView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    @Override
    public TranslateApplication getApp() {
        return (TranslateApplication) getActivity().getApplication();
    }

    protected abstract int getIconId();
    protected abstract int getTitleId();
    protected abstract int getLayoutId();
    public  void init(){
        if (subs != null)
            subs.unsubscribe();
        subs = new CompositeSubscription();
    }
}
