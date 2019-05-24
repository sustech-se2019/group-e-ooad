package com.google.codelabs.mdc.java.threeNews;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.codelabs.mdc.java.threeNews.application.ThreeNewsApplication;
import com.google.codelabs.mdc.java.threeNews.network.CookiesUtils;
import com.google.codelabs.mdc.java.threeNews.network.NewsEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A class for news grid fragment
 */
public class NewsGridFragment extends Fragment {
    private boolean move = false;
    private int mIndex = 0;
    List<NewsEntry> newsList = null; // the news list from news.json
    NewsCardRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.news_grid_fragment, container, false);
        setUpToolbar(view);
        // set up the recyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if (isAdded()) {
            newsList = NewsEntry.initNewsEntryList(getResources());
            adapter = new NewsCardRecyclerViewAdapter(newsList);
        }

        recyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new OnRecycleViewClickListener() {
            @Override
            public void onItemClickListener(int position, int itemPosition) {
                NewsEntry item = newsList.get(position);
                String newsUrl = "";
                int newsId = 0;
                switch (itemPosition){
                    case 1:
                        newsUrl = item.url1;
                        newsId = item.id1;
                        break;
                    case 2:
                        newsUrl = item.url2;
                        newsId = item.id2;
                        break;
                    case 3:
                        newsUrl = item.url3;
                        newsId = item.id3;
                        break;
                    default:
                        break;
                }
                final String finalurl = newsUrl;
                Log.i("Go to", position+","+itemPosition+","+newsUrl);
                String url = "http://47.107.97.154:8081/getNews";

                final int finalNewsId = newsId;
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    /**
                     * Get response from server
                     *
                     * @param response A JSONString response
                     */
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("test",response);
                            int isLike = 0;
                            int isBookmarked = 0;
                            switch (jsonObject.optInt("code")){
                                case 1: // isLiked
                                    isLike = 1;
                                    isBookmarked = 0;
                                    break;
                                case 0: // NoData
                                    isLike = 0;
                                    isBookmarked = 0;
                                    break;
                                case -1: // isNotLiked
                                    isLike = -1;
                                    isBookmarked = 0;
                                    break;
                                case 11:
                                    isBookmarked = 1;
                                    isLike = 1;
                                    break;//isBookMark
                                case 10:
                                    isBookmarked = 1;
                                    isLike = 0;
                                    break;//isNotBookMark
                                case 9:
                                    isBookmarked = 1;
                                    isLike = -1;
                                default:
                                    break;
                            }
//                        BrowserFragment browserfragment = Snake.newProxySupport(BrowserFragment.class, isBookmarked, isLike, website);
                                ((NavigationHost)getActivity()).navigateTo(new BrowserFragment(isBookmarked,isLike,finalurl, finalNewsId), true,false);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    /**
                     * When meet error in response
                     *
                     * @param error Error information
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> hashMap = new HashMap<>();
                        hashMap.put("newsId",String.valueOf(finalNewsId));
                        return hashMap;
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        if (CookiesUtils.cookie != null && CookiesUtils.cookie.length() > 0) {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("cookie", CookiesUtils.cookie);
                            Log.d("调试", "headers--" + headers);
                            return headers;
                        }else {
                            return super.getHeaders();
                        }
                    }

                };
                request.setTag("testPost");
                ThreeNewsApplication.getHttpQueues().add(request);

            }
        });
        int largePadding = getResources().getDimensionPixelSize(R.dimen.news_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.news_grid_spacing_small);
        recyclerView.addItemDecoration(new NewsGridItemDecoration(largePadding, smallPadding));
        return view;
    }

    /**
     * The action when this view create
     *
     * @param view The view
     * @param savedInstanceState Instance state which is saved
     */
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // final boolean lightMode = isLightMode();

        MaterialButton myAccountBtn = view.findViewById(R.id.my_account_btn);
        final MaterialButton changeModeBtn = view.findViewById(R.id.change_mode_btn);
        MaterialButton searchBtn = view.findViewById(R.id.search);
        MaterialButton popNews = view.findViewById(R.id.pop_btn);
        MaterialButton aboutUsBtn = view.findViewById(R.id.about_us_btn);

        aboutUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hello World", Toast.LENGTH_SHORT).show();
            }
        });


        myAccountBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Click Action
             * @param v view
             */
            @Override
            public void onClick(View v) {
                ((NavigationHost)getActivity()).navigateTo(new AccountFragment(), true,true);

            }
        });


        changeModeBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Click Action
             * @param v view
             */
            @Override
            public void onClick(View v) {
                Log.i("theme mode", changeModeBtn.getText().toString());
                changeModeBtn.setText("Light Mode");
            }
        });

        popNews.setOnClickListener(new View.OnClickListener() {
            /**
             * Click Action
             * @param v view
             */
            @Override
            public void onClick(View v) {

                String url = "http://47.107.97.154:8081/getRecommend";
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
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int id1 = jsonObject.optInt("id1");
                                    int id2 = jsonObject.optInt("id2");
                                    int id3 = jsonObject.optInt("id3");
                                    String kind1 = jsonObject.optString("kind1");
                                    String kind2 = jsonObject.optString("kind2");
                                    String kind3 = jsonObject.optString("kind3");
                                    String url1 = jsonObject.optString("url1");
                                    String url2 = jsonObject.optString("url2");
                                    String url3 = jsonObject.optString("url3");
                                    String introduction1 = jsonObject.optString("introduction1");
                                    String introduction2 = jsonObject.optString("introduction2");
                                    String introduction3 = jsonObject.optString("introduction3");
                                    String title1 = jsonObject.optString("title1");
                                    String title2 = jsonObject.optString("title2");
                                    String title3 = jsonObject.optString("title3");
                                    NewsEntry news1 = new NewsEntry(id1,id2,id3,kind1,kind2,kind3,url1,url2,url3,title1,title2,title3,introduction1,introduction2,introduction3);

                                    adapter.popNews(newsList.size(), news1, getResources());
                                    recyclerView.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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

                int lastItem = adapter.getItemCount();
                recyclerView.scrollToPosition(0);

                Log.i("info", "pop"+lastItem);
            }
        });

    }


    /**
     * Sets the app from the xml layout to be the action bar for this activity
     *
     * @param savedInstanceState Instance state which is saved
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * To pause
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * set the tool bar
     * @param view the whole view
     */
    private void setUpToolbar(View view){
        Toolbar toolbar = view.findViewById(R.id.app_bar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null){
            activity.setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                getContext(),
                view.findViewById(R.id.product_grid),
                new AccelerateDecelerateInterpolator(),
                getContext().getResources().getDrawable(R.drawable.branded_menu),
                getContext().getResources().getDrawable(R.drawable.close_menu)));
    }

    /**
     * Tells the activity what to use as the menu.
     *
     * @param menu The menu
     * @param menuInflater Inflater of menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.shr_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    /**
     * Select item from menu
     *
     * @param item A menu item
     * @return Options Item Selected
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        /*switch (item.getItemId()) {
            //Navigation to Browser
            case R.id.filter:
                ((NavigationHost)getActivity()).navigateTo(new BrowserFragment(), true);
                break;
            default:
                break;
        }*/
        /**/
        String url = "http://47.107.97.154:8081/getNews";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            /**
             * Get response from server
             *
             * @param response A JSONString response
             */
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int isLike = 0;
                    int isBookmarked = 0;
                    String website = "";
                    switch (jsonObject.optInt("code")){
                        case 1: // isLiked
                            isLike = 1;
                            isBookmarked = 0;
                            break;
                        case 0: // NoData
                            isLike = 0;
                            isBookmarked = 0;
                            break;
                        case -1: // isNotLiked
                            isLike = -1;
                            isBookmarked = 0;
                            break;
                        case 11:
                            isBookmarked = 1;
                            isLike = 1;
                            break;//isBookMark
                        case 10:
                            isBookmarked = 1;
                            isLike = 0;
                            break;//isNotBookMark
                        case 9:
                            isBookmarked = 1;
                            isLike = -1;
                        default:
                            break;
                    }
                    website = "https://m.toutiao.com/search/?need_open_window=1";
//                    int newsid = jsonObject.getJSONObject("data").optInt("id");
                    if(item.getItemId()==R.id.search){
//                        BrowserFragment browserfragment = Snake.newProxySupport(BrowserFragment.class, isBookmarked, isLike, website);
                        ((NavigationHost)getActivity()).navigateTo(new BrowserFragment(isBookmarked, isLike, website,0), true,false);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            /**
             * When meet error in response
             *
             * @param error Error information
             */
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> hashMap = new HashMap<>();
                hashMap.put("newsId","462");
                return hashMap;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (CookiesUtils.cookie != null && CookiesUtils.cookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", CookiesUtils.cookie);
                    Log.d("调试", "headers--" + headers);
                    return headers;
                }else {
                    return super.getHeaders();
                }
            }

        };
        request.setTag("testPost");
        ThreeNewsApplication.getHttpQueues().add(request);
        /**/

        return super.onOptionsItemSelected(item);
    }
}
