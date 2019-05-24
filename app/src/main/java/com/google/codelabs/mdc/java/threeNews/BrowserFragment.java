package com.google.codelabs.mdc.java.threeNews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import me.yokeyword.swipebackfragment.SwipeBackFragment;
import me.yokeyword.swipebackfragment.SwipeBackLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.codelabs.mdc.java.threeNews.application.ThreeNewsApplication;
import com.google.codelabs.mdc.java.threeNews.network.CookiesUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Fragment representing the login screen for Shrine.
 */
public class BrowserFragment extends SwipeBackFragment {

    private int isBookmarked;
    private int isLike;
    private int originIsBookmarked;
    private int originIsLike;
    private String website;
    private int newsid;
    private int isNight = 0;
    private WebView webView;
    private LinearLayout browserBottom;
    private ImageButton back;
    private ImageButton share;
    private ImageButton like;
    private ImageButton dislike;
    private ImageButton bookmark;
    private ImageButton night;

    public BrowserFragment() {
    }

    @SuppressLint("ValidFragment")
    public BrowserFragment(int isBookmarked, int isLike, String website, int newsid) {
        this.isBookmarked = isBookmarked;
        this.isLike = isLike;
        this.website = website;
        this.newsid = newsid;
        this.originIsBookmarked = isBookmarked;
        this.originIsLike = isLike;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.browser_fragment, container, false);
        setEdgeLevel(100);

        // 滑动过程监听
        getSwipeBackLayout().addSwipeListener(new SwipeBackLayout.OnSwipeListener() {
            @Override
            public void onDragStateChange(int state) {
                // Drag state
                if (state == 2) {
                    /**
                     * 返回更新
                     */
                    sendChange();
                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
            }

            @Override
            public void onDragScrolled(float scrollPercent) {
            }
        });

        openWebsite(website, view);
        return attachToSwipeBack(view);
    }


    // sets the app from the xml layout to be the action bar for this activity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * @param website open the website
     * @param view    View
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void openWebsite(String website, View view) {
        webView = view.findViewById(R.id.browser_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12F70 Safari/600.1.4");
        final AVLoadingIndicatorView avi = view.findViewById(R.id.loading_view);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                avi.hide();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                avi.show();
            }
        });
        webView.loadUrl(website);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        back = Objects.requireNonNull(getActivity()).findViewById(R.id.browser_back);
        share = getActivity().findViewById(R.id.browser_share);
        like = getActivity().findViewById(R.id.browser_like);
        dislike = getActivity().findViewById(R.id.browser_dislike);
        bookmark = getActivity().findViewById(R.id.browser_bookmark);
        night = getActivity().findViewById(R.id.browser_night);
        browserBottom = getActivity().findViewById(R.id.browser_bottom);
        if (isLike == 1) {
            like.setImageDrawable(getResources().getDrawable(R.drawable.liked));
        } else if (isLike == -1) {
            dislike.setImageDrawable(getResources().getDrawable(R.drawable.disliked));
        }
        if (isBookmarked == 0) {
            bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bookmark));
        } else {
            bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bookmarked));
        }
        if (isNight == 0) {
            night.setImageDrawable(getResources().getDrawable(R.drawable.night));
            setSun();
        } else {
            night.setImageDrawable(getResources().getDrawable(R.drawable.nighted));
            setNight();
        }
        super.onActivityCreated(savedInstanceState);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                /**
                 * 返回更新
                 */
                sendChange();
                getFragmentManager().popBackStack();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText("我正在用叁闻看新闻，点击观看："+website);
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                @SuppressLint("ShowToast") Toast toast;
                if (isLike == 0 || isLike == -1) {
                    like.setImageDrawable(getResources().getDrawable(R.drawable.liked));
                    dislike.setImageDrawable(getResources().getDrawable(R.drawable.dislike));
                    toast = Toast.makeText(getActivity(), "喜欢", Toast.LENGTH_LONG);
                    isLike = 1;
                } else {
                    like.setImageDrawable(getResources().getDrawable(R.drawable.like));
                    toast = Toast.makeText(getActivity(), "取消喜欢", Toast.LENGTH_LONG);
                    isLike = 0;
                }
                showToast(toast, 500);
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                @SuppressLint("ShowToast") Toast toast;
                if (isLike == 0 || isLike == 1) {
                    dislike.setImageDrawable(getResources().getDrawable(R.drawable.disliked));
                    like.setImageDrawable(getResources().getDrawable(R.drawable.like));
                    toast = Toast.makeText(getActivity(), "不喜欢", Toast.LENGTH_LONG);
                    isLike = -1;
                } else {
                    dislike.setImageDrawable(getResources().getDrawable(R.drawable.dislike));
                    toast = Toast.makeText(getActivity(), "取消不喜欢", Toast.LENGTH_LONG);
                    isLike = 0;
                }
                showToast(toast, 500);
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                @SuppressLint("ShowToast") Toast toast;
                if (isBookmarked == 0) {
                    bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bookmarked));
                    toast = Toast.makeText(getActivity(), "已收藏", Toast.LENGTH_LONG);
                    isBookmarked = 1;
                } else {
                    bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bookmark));
                    toast = Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_LONG);
                    isBookmarked = 0;
                }
                showToast(toast, 500);
            }
        });


        night.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                @SuppressLint("ShowToast") Toast toast;
                if (isNight == 0) {
                    night.setImageDrawable(getResources().getDrawable(R.drawable.nighted));
                    toast = Toast.makeText(getActivity(), "夜间模式", Toast.LENGTH_LONG);
                    setNight();
                    isNight = 1;
                } else {
                    night.setImageDrawable(getResources().getDrawable(R.drawable.night));
                    toast = Toast.makeText(getActivity(), "取消夜间模式", Toast.LENGTH_LONG);
                    setSun();
                    isNight = 0;
                }
                showToast(toast, 500);
            }
        });
    }

    /**
     * 日间模式
     */
    private void setSun() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("document.body.style.backgroundColor=\"white\";document.body.style.color=\"black\";document.getElementsByTagName('body')[0].style.webkitTextFillColor=\"black\";", null);
                } else {
                    webView.loadUrl("javascript:document.body.style.backgroundColor=\"#white\";document.body.style.color=\"black\";document.getElementsByTagName('body')[0].style.webkitTextFillColor= \"black\";");
                }
            }
        });
        browserBottom.setBackgroundColor(getResources().getColor(R.color.white));
        back.setBackgroundColor(getResources().getColor(R.color.white));
        share.setBackgroundColor(getResources().getColor(R.color.white));
        like.setBackgroundColor(getResources().getColor(R.color.white));
        dislike.setBackgroundColor(getResources().getColor(R.color.white));
        bookmark.setBackgroundColor(getResources().getColor(R.color.white));
        night.setBackgroundColor(getResources().getColor(R.color.white));
    }

    /**
     * 夜间模式
     */
    private void setNight() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("document.body.style.backgroundColor=\"black\";document.body.style.color=\"white\";document.getElementsByTagName('body')[0].style.webkitTextFillColor= '#8a8a8a';", null);
                } else {
                    webView.loadUrl("javascript:document.body.style.backgroundColor=\"#black\";document.body.style.color=\"white\";;document.getElementsByTagName('body')[0].style.webkitTextFillColor= '#8a8a8a';");
                }
            }
        });
        browserBottom.setBackgroundColor(getResources().getColor(R.color.black));
        back.setBackgroundColor(getResources().getColor(R.color.transparent));
        share.setBackgroundColor(getResources().getColor(R.color.transparent));
        like.setBackgroundColor(getResources().getColor(R.color.transparent));
        dislike.setBackgroundColor(getResources().getColor(R.color.transparent));
        bookmark.setBackgroundColor(getResources().getColor(R.color.transparent));
        night.setBackgroundColor(getResources().getColor(R.color.transparent));
    }


    //ShareText

    /**
     * @param text The text that the application wants to share to other APP, such as website
     */
    private void shareText(String text) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "请选择您要分享的应用"));
    }


    public void showToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

    /**
     * Overwrite KEYCODE_BACK
     */
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
//                    Toast.makeText(getActivity(), "按了返回键", Toast.LENGTH_SHORT).show();
                    /**
                     * 返回更新
                     */
                    sendChange();
                    assert getFragmentManager() != null;
                    getFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });
    }

    private void sendChange() {
        if (isLike != originIsLike || isBookmarked != originIsBookmarked) {
            String url = "http://47.107.97.154:8081/closeNews";
//        String url = "http://10.21.64.90:8081/closeNews";
            StringRequest request = new StringRequest(
                    Request.Method.POST, url,
                    new Response.Listener<String>() {
                        /**
                         * Get response from server
                         *
                         * @param response A JSONString response
                         */
                        @Override
                        public void onResponse(String response) {
                        }
                    },
                    new Response.ErrorListener() {
                        /**
                         * When meet error in response
                         *
                         * @param error Error information
                         */
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> hashMap = new HashMap<>();
                    hashMap.put("website", website);
                    hashMap.put("rLikeStatus", String.valueOf(isLike));
                    hashMap.put("rBookmarkStatus", String.valueOf(isBookmarked));
                    hashMap.put("newsId", String.valueOf(newsid));
                    return hashMap;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    if (CookiesUtils.cookie != null && CookiesUtils.cookie.length() > 0) {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("cookie", CookiesUtils.cookie);
                        Log.d("调试", "headers--" + headers);
                        return headers;
                    } else {
                        return super.getHeaders();
                    }
                }
            };
            request.setTag("testPost");
            ThreeNewsApplication.getHttpQueues().add(request);
        }
    }

}
