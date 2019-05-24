package com.google.codelabs.mdc.java.threeNews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.codelabs.mdc.java.threeNews.application.ThreeNewsApplication;
import com.google.codelabs.mdc.java.threeNews.network.CookiesUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Account fragment
 *
 * @author Yu Qiu
 * @author Sen Peng
 * @version 1.0
 */
public class AccountFragment extends Fragment {
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int RESULT_REQUEST = 2;
    private ImageView avatarImageView;
    private static int output_X = 238;
    private static int output_Y = 238;
    SharedPreferences sp;

    /**
     *
     * @param inflater Layout Inflater
     * @param container Container of all view
     * @param savedInstanceState instance state which is saved
     * @return This fragment view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isAdded()){
            sp = this.getActivity().getSharedPreferences("profile", MODE_PRIVATE);
        }
        return inflater.inflate(R.layout.account_fragment, container, false);
    }

    /**
     *
     * @param savedInstanceState instance state which is saved
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /**
     *
     * @param view This fragment's view
     * @param savedInstanceState instance state which is saved
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatarImageView = view.findViewById(R.id.account_avatar_view);

        MaterialButton logOutBtn = view.findViewById(R.id.logout_btn);
        MaterialButton accountAvatarBtn = view.findViewById(R.id.account_avatar_btn);
        MaterialButton updateProfileBtn = view.findViewById(R.id.update_profile_btn);

        final Spinner genderSpinner = view.findViewById(R.id.gender_input);
        final Spinner occupationSpinner = view.findViewById(R.id.occupation_input);
        final Spinner ageSpinner = view.findViewById(R.id.age_input);

        final TextView genderShow = view.findViewById(R.id.gender_show_text);
        final TextView occupationShow = view.findViewById(R.id.occupation_show_text);
        final TextView ageShow = view.findViewById(R.id.age_show_text);

        final String savedGender = sp.getString("gender","男");
        final String savedOccupation = sp.getString("occupation", "学生");
        int savedAge = sp.getInt("age", 1970);
        final String savedAgeStr = savedAge+"";

        genderShow.setText(savedGender);
        occupationShow.setText(savedOccupation);
        ageShow.setText(savedAgeStr);

        final String[] gender = {"男"};
        final String[] occupation = {"学生"};
        final int[] age = {1970};

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                String[] genders = getResources().getStringArray(R.array.gender);
                gender[0] = genders[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender[0] = "男";
            }
        });

        occupationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                String[] occupations = getResources().getStringArray(R.array.occupation);
                occupation[0] = occupations[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                occupation[0] = "学生";
            }
        });

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                String[] ages = getResources().getStringArray(R.array.age);
                age[0] = Integer.parseInt(ages[pos]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                age[0] = 1970;
            }
        });


        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookiesUtils.cookie = null;
                String url = "http://47.107.97.154:8081/logout";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
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
                });
                request.setTag("testPost");
                ThreeNewsApplication.getHttpQueues().add(request);
                ((NavigationHost)getActivity()).navigateTo(new LoginFragment(), false,true);
            }
        });

        accountAvatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog();
            }
        });

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("choice", gender[0]+occupation[0]+age[0]);
                genderShow.setText(gender[0]);
                occupationShow.setText(occupation[0]);
                ageShow.setText(String.valueOf(age[0]));

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("gender", gender[0]);
                editor.putString("occupation", occupation[0]);
                editor.putInt("age", age[0]);
                editor.apply();
                String url = "http://47.107.97.154:8081/updateInformation";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
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
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> hashMap = new HashMap<>();
                        hashMap.put("genderString",gender[0]);
                        hashMap.put("occupation",occupation[0]);
                        hashMap.put("birthday",String.valueOf(age[0]));
                        hashMap.put("name","");
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
        });


    }

    /**
     * show the change dialog
     */
    protected void showChangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        chooseImageFromGallery();
                        break;
                    case TAKE_PICTURE: // 拍照
                        chooseImageFromCamera();
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create().show();
    }

    /**
     * Choose image from camera to use as avatar
     */
    private void chooseImageFromCamera() {
        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this.getContext(), "com.camera.fileProvider", new File(Environment.getExternalStorageDirectory(), "head_image.jpg"));
                intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head_image.jpg"));
            }
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intentFromCapture, TAKE_PICTURE);
        }
    }

    /**
     * To check if this phone has sd card
     * @return is has
     */
    private boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * To choose a image from gallery as avatar
     */
    private void chooseImageFromGallery() {
        Intent intentFromGallery = new Intent();
        Uri uri;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(this.getContext(),"com.camera.fileProvider", new File(Environment.getExternalStorageDirectory(), "head_image.jpg"));
            intentFromGallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }else{
            uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head_image.jpg"));

        }
        intentFromGallery.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CHOOSE_PICTURE);

    }

    /**
     *
     * Deal with some return values after the activity.
     *
     * @param requestCode Request code
     * @param resultCode Result code
     * @param data The Intent type data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            Log.i("requestCode", requestCode+"");
            switch (requestCode) {
                case TAKE_PICTURE: // 1
                    if (hasSdcard()) {
                        File tempFile = new File(
                                Environment.getExternalStorageDirectory(),
                                "head_image.jpg");
                        cropRawPhoto(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(this.getActivity().getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                                .show();
                    }
                    break;
                case CHOOSE_PICTURE: // 0
                    cropRawPhoto(data.getData());
                    break;
                case RESULT_REQUEST:
                    if(data != null){
                        setImageToView(data);
                    }
                    break;
                default:
                    break;
            }
        }else{
            Toast.makeText(this.getActivity().getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     *
     * Crop the raw image into 238 * 238 size
     *
     * @param uri The uri of image
     */
    public void cropRawPhoto(Uri uri) {


        Intent data = new Intent("com.android.camera.action.CROP");

        data.setDataAndType(uri, "image/*");

        data.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // data.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        data.putExtra("crop", "true");

        data.putExtra("aspectX", 1);
        data.putExtra("aspectY", 1);

        data.putExtra("outputX", output_X);
        data.putExtra("outputY", output_Y);
        data.putExtra("return-data", true);

        startActivityForResult(data, RESULT_REQUEST);
    }

    /**
     * set image to front view
     *
     * @param data image
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Log.i("extra", extras.toString());
            Bitmap photo = extras.getParcelable("data");
            avatarImageView.setImageBitmap(photo);
            // uploadPic(photo);
        }
    }

}
