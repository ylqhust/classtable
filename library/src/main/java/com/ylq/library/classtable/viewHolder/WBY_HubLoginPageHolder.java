package com.ylq.library.classtable.viewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ylq.library.R;
import com.ylq.library.classtable.Global;
import com.ylq.library.classtable.dialog.IngLoginHubDialog;
import com.ylq.library.classtable.model.AllClasses;
import com.ylq.library.classtable.query.Callback;
import com.ylq.library.classtable.query.HubBean;
import com.ylq.library.classtable.query.Query;
import com.ylq.library.classtable.query.Who;
import com.ylq.library.common.ClickGuard;
import com.ylq.library.classtable.Store;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by apple on 16/7/12.
 */
public class WBY_HubLoginPageHolder extends ClasstableBaseHolder implements Callback, Handler.Callback {
    private static final int WHAT_PARSING_FAILURE = 2;
    private Handler mHandler;

    private WebView mWebView;
    private ProgressBar mProgressBar;


    private EditText mUsername;
    private EditText mPassword;
    private View mLogin;
    private boolean mCanLogin; // indicate whether or not http://hub.hust.edu.cn/index.jsp is completely loaded.

    private boolean mRestart = false;//如果获取成功并且解析成功准备进入ClassPageHolder的时候，如果这个是true，就将所有的已存在的ViewHolder全部清空，从新开始

    private View mLoadFirstPageCompleted; // 右侧连接hub成功
    private View mLoadFirstPageUncompleted; // 中部提示

    public WBY_HubLoginPageHolder(Context context) {
        super(R.layout.syllabus_fragment_hub, context);
        initWebView();
        prepare();
    }

    public WBY_HubLoginPageHolder(int id, Context context) {
        super(id, context);
    }

    public WBY_HubLoginPageHolder(Context context, boolean restart) {
        super(R.layout.syllabus_fragment_hub, context);
        mRestart = restart;
        initWebView();
        prepare();
    }

    @Override
    public void initView() {
        mHandler = new Handler(Looper.getMainLooper(), this);
        mProgressBar = (ProgressBar) findViewById(R.id.syllabus_fragment_hub_progress);
        mLoadFirstPageCompleted = findViewById(R.id.syllabus_fragment_hub_loaded_status);
        mLoadFirstPageUncompleted = findViewById(R.id.syllabus_fragment_hub_loading_status);
        mUsername = (EditText) findViewById(R.id.syllabus_fragment_hub_username);
        mPassword = (EditText) findViewById(R.id.syllabus_fragment_hub_password);
        mLogin = findViewById(R.id.syllabus_fragment_hub_login);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        }, findViewById(R.id.syllabus_fragment_hub_top_back));


        ClickGuard.guard(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        }, mLogin);

    }

    @Override
    public void unGurad() {
        ClickGuard.unGuard(findViewById(R.id.syllabus_fragment_hub_top_back), mLogin);
    }


    private void prepare() {
        mLogin.setVisibility(View.VISIBLE);
        mLoadFirstPageUncompleted.setVisibility(View.GONE);
//        mLoadFirstPageCompleted.setVisibility(View.GONE);
        mCanLogin = false;

        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {
                    mWebView.loadUrl("http://hub.hust.edu.cn");
                }
            });
        } else {
            CookieManager.getInstance().removeAllCookie();
            mWebView.loadUrl("http://hub.hust.edu.cn");
        }
    }

    private void login() {
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(getContext(), "学号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(getContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mCanLogin) {
            Toast.makeText(getContext(), "连接ing hub系统", Toast.LENGTH_SHORT).show();
            mLoadFirstPageUncompleted.setVisibility(View.VISIBLE);
            return;
        }

        Global.USER_ID = new String(username);
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mLogin.getWindowToken(),0);
        String commend = "javascript:" +
                "document.getElementById(\"username\").value=\"%s\";" +
                "document.getElementById(\"password\").value=\"%s\";" +
                "$('#form1').submit();";
        mWebView.loadUrl(String.format(commend, username, password));
        mLogin.setVisibility(View.GONE);
        dialogIn(new IngLoginHubDialog(getContext()));
    }


    private void initWebView() {
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBlockNetworkImage(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                try {
                    mProgressBar.setVisibility(View.VISIBLE);

                    if (url.equals("http://s.hub.hust.edu.cn/hub.jsp")) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    isTheEnd = false;
                } catch (Exception e) {
                    // 退出之后，引用会爆错,null point
                    e.printStackTrace();
                }
            }

            private boolean isTheEnd; // url不在进行跳转了，用来判断密码是否错误

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                    mProgressBar.setVisibility(View.GONE);

                    if (url.equals("http://hub.hust.edu.cn/index.jsp")) {
                        mCanLogin = true;
                        mLoadFirstPageUncompleted.setVisibility(View.GONE);
                    }

                    if (url.equals("http://hub.hust.edu.cn/hublogin.action")) {
                        isTheEnd = true;
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                if (isTheEnd) {
                                    if(isShown())
                                        back();
                                    Toast.makeText(getContext(),"学号或密码错误",Toast.LENGTH_LONG).show();
                                    prepare();
                                }
                            }
                        }, 3000);
                    }

                    if (url.equals("http://s.hub.hust.edu.cn/hub.jsp")) {
                        String cookie = android.webkit.CookieManager.getInstance().getCookie("s.hub.hust.edu.cn");
                        Query.getInstance().hubCourse(Who.HUB_COURSE, cookie, WBY_HubLoginPageHolder.this);
                    }
                } catch (Exception e) {
                    // 退出之后，引用会爆错,null point
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void begin(@NonNull Who who) {
        if (who == Who.HUB_COURSE) {
            if(isShown()){
                //显示正在解析的Dialog
                //这里就不显示了，反正数据都获取了，解析1，2秒的事，显不显示都无所谓
            }
        }
    }

    @Override
    public void hubCourseNoError(@NonNull Who who, @NonNull final HubBean bean, @NonNull Response response) {
        if (who == Who.HUB_COURSE) {
            //从hub查询没有出错，从这里开始解析数据
            AllClasses allClasses = null;
            try {
                allClasses = AllClasses.parserHubBean(bean);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Query.getInstance().upload(bean);
                    }
                }).start();
                Store.saveClassData(getContext(), allClasses);
                if(!isShown())
                    return;
                if (!mRestart) {
                    back();
                    anotherBack();
                    holderIn(new ClassPageHolder(getContext(), allClasses));
                } else
                    reStart(new ClassPageHolder(getContext(), allClasses));

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "HUB出问题了，无法获取课表", Toast.LENGTH_LONG).show();
                back();
                back();
            }
        }
    }

    @Override
    public void canNotConnectToServer(@NonNull Who who, @NonNull RetrofitError error) {
        if (who == Who.HUB_COURSE) {
            //无法连接到hub服务器，可能是hub服务器关闭或者其他原因，在这里提示用户从冰岩服务器上拉取数据
            Toast.makeText(getContext(), "无法连接到HUB服务器，可能是HUB已关闭或者其他原因，请稍后再试", Toast.LENGTH_LONG).show();
            back();
            back();
        }
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_PARSING_FAILURE: {
                canNotConnectToServer(Who.HUB_COURSE, null);
                return true;
            }
        }

        return false;
    }

    @Override
    public void finish(@NonNull Who who) {

    }
}
