package com.google.codelabs.mdc.java.threeNews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;

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
    protected static Uri tempUri;
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

        TextView genderShow = view.findViewById(R.id.gender_show_text);
        TextView occupationShow = view.findViewById(R.id.occupation_show_text);
        TextView ageShow = view.findViewById(R.id.age_show_text);

        String savedGender = sp.getString("gender","男");
        String savedOccupation = sp.getString("occupation", "学生");
        int savedAge = sp.getInt("age", 1970);
        String savedAgeStr = savedAge+"";

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
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("gender", gender[0]);
                editor.putString("occupation", occupation[0]);
                editor.putInt("age", age[0]);
                editor.commit();
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
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create().show();
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
            //Log.i("Request code",""+requestCode);
            if(data == null) {
                Log.i("data", "null");
            }else{
                Log.i("data", data.toString());
            }
            switch (requestCode) {
                case TAKE_PICTURE:
                    cropRawPhoto(tempUri);
                    break;
                case CHOOSE_PICTURE:
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
        // data.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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


//    private void uploadPic(Bitmap photo) {
////        String imagePath = Utils.savePhoto(bitmap, Environment
////                .getExternalStorageDirectory().getAbsolutePath(), String
////                .valueOf(System.currentTimeMillis()));
////        Log.e("imagePath", imagePath+"");
////        if(imagePath != null){
////            // ...
////        }
////    }
}
