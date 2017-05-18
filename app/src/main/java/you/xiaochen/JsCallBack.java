package you.xiaochen;

import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by you on 2017/3/18.
 * js交互类
 */

public class JsCallBack {

    private final WebView mWebView;

    public JsCallBack(WebView mWebView) {
        this.mWebView = mWebView;
    }

    @JavascriptInterface
    public void requestEvent(boolean request) {
        Log.i("you", "requestDisallowInterceptTouchEvent " + request);
        mWebView.requestDisallowInterceptTouchEvent(request);
    }

    public static void initWebView(final WebView mWebView, final ProgressBar mProgress) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        JsCallBack jsCallback = new JsCallBack(mWebView);
        mWebView.addJavascriptInterface(jsCallback, "Android");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setSavePassword(false);

        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);

        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                int touchSlop = ViewConfiguration.get(mWebView.getContext()).getScaledTouchSlop();
                StringBuilder jsSb = new StringBuilder("javascript:initTouchSlop('").append(touchSlop).append("')");
                mWebView.loadUrl(jsSb.toString());
            }
        });
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.setHapticFeedbackEnabled(false);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 0) {
                    mProgress.setVisibility(View.VISIBLE);
                    mProgress.setProgress(0);
                    return;
                }
                if (newProgress == 100) {
                    mProgress.setVisibility(View.GONE);
                } else {
                    mProgress.setVisibility(View.VISIBLE);
                    mProgress.setProgress(newProgress);
                }
            }
        });
    }

}
