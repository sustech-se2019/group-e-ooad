package com.google.codelabs.mdc.java.threeNews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.codelabs.mdc.java.threeNews.R;
import com.google.codelabs.mdc.java.threeNews.application.ThreeNewsApplication;
import com.google.codelabs.mdc.java.threeNews.network.CookiesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Fragment representing the login screen for threeNews.
 */
public class LoginFragment extends Fragment {

    /**
     *
     * Display login fragment
     *
     *
     * @param inflater Layout Inflater
     * @param container Container of all view
     * @param savedInstanceState instance state which is saved
     * @return This fragment view
     */
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        final TextInputEditText usernameEditText = view.findViewById(R.id.username_edit_input);
        final TextInputLayout usernameTextInput = view.findViewById(R.id.username_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_input);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);


        MaterialButton nextBtn = view.findViewById(R.id.next_btn);
        MaterialButton registerBtn = view.findViewById(R.id.register_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * Click action
             *
             * @param view A view of current fragment
             */
            @Override
            public void onClick(View view){
                if (isUsernameValid(usernameEditText.getText())){
                    usernameTextInput.setError(getString(R.string.error_username));
                    passwordTextInput.setError(null);
                } else if (isPasswordValid(passwordEditText.getText())){
                    usernameTextInput.setError(null);
                    passwordTextInput.setError(getString(R.string.error_password));
                }else{
                    String url = "http://47.107.97.154:8081/login";
//                    String url = "http://10.21.64.90:8081/login";

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
                                switch (jsonObject.optInt("code")){
                                    case 1: // login successfully
                                        usernameTextInput.setError(null);
                                        passwordTextInput.setError(null);
                                        getFragmentManager().popBackStack();

                                        //11611526 Fixed Stack
                                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                        ((NavigationHost)getActivity()).navigateTo(new NewsGridFragment(), false,true);
                                        break;
                                    case -1: // incorrect password
                                        usernameTextInput.setError(null);
                                        passwordTextInput.setError(getString(R.string.error_password_incorrect));
                                        break;
                                    case -2: // user not exist
                                        usernameTextInput.setError(getString(R.string.error_username_not_exist));
                                        passwordTextInput.setError(null);
                                        break;
                                    default:
                                        break;

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
                            hashMap.put("username",usernameEditText.getText().toString());
                            hashMap.put("password",passwordEditText.getText().toString());
                            return hashMap;
                        }
                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response){
                            Response<String> r = super.parseNetworkResponse(response);
                            Map<String,String> head = response.headers;
                            String cookies = head.get("Set-Cookie");
                            CookiesUtils.cookie = cookies.substring(0,cookies.indexOf(";"));
                            return r;
                        }

                    };
                    request.setTag("testPost");
                    ThreeNewsApplication.getHttpQueues().add(request);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Click Action
             * @param v Current view
             */
            @Override
            public void onClick(View v) {
                ((NavigationHost)getActivity()).navigateTo(new RegisterFragment(), false,true);
            }
        });

        passwordTextInput.setOnKeyListener(new View.OnKeyListener(){
            /**
             * Key press
             * @param v View
             * @param keyCode Integer keycode
             * @param event Key event
             * @return boolean type return
             */
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPasswordValid(passwordEditText.getText())){
                    passwordTextInput.setError(null);
                }
                return false;
            }
        });
        return view;
    }


    protected boolean isUsernameValid(@Nullable Editable text){
        return !(text != null && text.length() >= 1);
    }

    protected boolean isPasswordValid(@Nullable Editable text){
        return !(text != null && text.length() >= 8);

    }
}
