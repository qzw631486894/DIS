package com.kongrongqi.shopmall.modules.me;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.http.URLConstans;
import com.kongrongqi.shopmall.modules.me.IView.IFansView;
import com.kongrongqi.shopmall.modules.me.IView.IListView;
import com.kongrongqi.shopmall.modules.me.presenter.AboutPresenter;
import com.kongrongqi.shopmall.modules.me.presenter.FansPresenter;

/**
 * 创建日期：2017/5/19 0019 on 14:46
 * 作者:penny
 */
public class FansActivity extends BaseMVPActivity<FansPresenter> implements IFansView {


    private WebView wb;

    public static String URL="url";

    public static void lunch(Context con,String url) {
        Intent intent = new Intent(con, FansActivity.class);
        intent.putExtra(URL,url);
        con.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_fans;
    }

    @Override
    protected FansPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new FansPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return FansActivity.this;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.me_fans);
        right_tv2.setVisibility(View.VISIBLE);
        right_tv2.setText(R.string.logout);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finishActivity() {
        if (wb.canGoBack()) {
            wb.goBack();
            return;
        }
        super.finishActivity();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra(URL);

        wb = (WebView) findViewById(R.id.wb);
      /* 设置支持Js,必须设置的,不然网页基本上不能看 */
        wb.getSettings().setJavaScriptEnabled(true);
/* 设置缓存模式,我这里使用的默认,不做多讲解 */
        wb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
/* 设置为true表示支持使用js打开新的窗口 */
        wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    /* 大部分网页需要自己保存一些数据,这个时候就的设置下面这个属性 */
        wb.getSettings().setDomStorageEnabled(true);
 /* 设置为使用webview推荐的窗口 */
        wb.getSettings().setUseWideViewPort(true);
    /* 设置网页自适应屏幕大小 ---这个属性应该是跟上面一个属性一起用 */
        wb.getSettings().setLoadWithOverviewMode(true);
/* HTML5的地理位置服务,设置为true,启用地理定位 */
        wb.getSettings().setGeolocationEnabled(true);
/* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
        wb.getSettings().setBuiltInZoomControls(false);
/* 提高网页渲染的优先级 */
        wb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
/* 设置显示水平滚动条,就是网页右边的滚动条.我这里设置的不显示 */
        wb.setHorizontalScrollBarEnabled(false);
/* 指定垂直滚动条是否有叠加样式 */
        wb.setVerticalScrollbarOverlay(true);
/* 设置滚动条的样式 */
        wb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        wb.loadUrl(URLConstans.fans_url);
        wb.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wb.loadUrl(url);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            this.finish();
//        }
//        return false;
//    }

    // 设置回退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wb.canGoBack()) {
            wb.goBack();
            return true;
        }
        this.finish();
        return false;
    }


}
