package com.google.codelabs.mdc.java.threeNews;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.codelabs.mdc.java.threeNews.R;
import com.google.codelabs.mdc.java.threeNews.application.ThreeNewsApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Register Fragment
 */
public class RegisterFragment extends Fragment {
    /**
     * Display register fragment
     *
     * @param inflater Layout Inflater
     * @param container Container of all view
     * @param savedInstanceState instance state which is saved
     * @return This fragment view
     */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_fragment, container, false);

        final TextInputEditText usernameEditText = view.findViewById(R.id.username_edit_input);
        final TextInputLayout usernameTextInput = view.findViewById(R.id.username_text_input);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_input);
        final TextInputLayout conPasswordTextInput = view.findViewById(R.id.con_password_text_input);
        final TextInputEditText conPasswordEditText = view.findViewById(R.id.con_password_edit_input);
        MaterialButton loginBtn = view.findViewById(R.id.login_btn);
        MaterialButton nextBtn = view.findViewById(R.id.next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Click Action
             * @param view The view
             */
            @Override
            public void onClick(View view){
                if (isUsernameValid(usernameEditText.getText())){
                    usernameTextInput.setError(getString(R.string.error_username));
                    passwordTextInput.setError(null);
                    conPasswordTextInput.setError(null);
                } else if (isPasswordValid(passwordEditText.getText())){
                    usernameTextInput.setError(null);
                    passwordTextInput.setError(getString(R.string.error_password));
                    conPasswordTextInput.setError(null);
                } else if (isConPasswordValid(passwordEditText.getText(), conPasswordEditText.getText())){
                    usernameTextInput.setError(null);
                    passwordTextInput.setError(null);
                    conPasswordTextInput.setError(getString(R.string.error_con_password));
                } else{
                    String url = "http://47.107.97.154:8081/signup";
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
                                switch (jsonObject.optInt("code")){
                                    case 1: // register successfully
                                        usernameTextInput.setError(null);
                                        passwordTextInput.setError(null);
                                        conPasswordTextInput.setError(null);
                                        ((NavigationHost)getActivity()).navigateTo(new LoginFragment(), false,true);
                                        break;
                                    case -1: // username already exists.
                                        usernameTextInput.setError(getString(R.string.error_username_exist));
                                        passwordTextInput.setError(null);
                                        conPasswordTextInput.setError(null);
                                        break;
                                    default:
                                        break;
                                }


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
                            passwordTextInput.setError(getString(R.string.error_service));
                            conPasswordTextInput.setError(null);
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> hashMap = new HashMap<>();
                            hashMap.put("username",usernameEditText.getText().toString());
                            hashMap.put("password",passwordEditText.getText().toString());
                            return hashMap;
                        }
                    };
                    request.setTag("testPost");
                    ThreeNewsApplication.getHttpQueues().add(request);
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Click Action
             * @param v view
             */
            @Override
            public void onClick(View v) {
                ((NavigationHost)getActivity()).navigateTo(new LoginFragment(), false,true);
            }
        });


        passwordTextInput.setOnKeyListener(new View.OnKeyListener(){
            /**
             * On key action
             * @param v view
             * @param keyCode The keycode of it
             * @param event Key event
             * @return On key or not
             */
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                conPasswordTextInput.setError(null);
                if (!isPasswordValid(passwordEditText.getText())){
                    passwordTextInput.setError(null);
                }
                return false;
            }
        });

        conPasswordTextInput.setOnKeyListener(new View.OnKeyListener(){
            /**
             * On key action
             *
             *
             * @param v view
             * @param keyCode The integer key code
             * @param event The key event
             * @return On key or not
             */
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!isPasswordValid(passwordEditText.getText())){
                    passwordTextInput.setError(null);
                }
                if (!isConPasswordValid(passwordEditText.getText(), conPasswordEditText.getText())){
                    conPasswordTextInput.setError(null);
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

    protected boolean isConPasswordValid(@Nullable Editable password, @Nullable Editable conPassword){
        return !(conPassword != null && password.toString().equals(conPassword.toString()));
    }
}
