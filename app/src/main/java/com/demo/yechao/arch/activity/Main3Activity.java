package com.demo.yechao.arch.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.demo.yechao.arch.R;
import com.demo.yechao.arch.utils.ReadByCommon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Main3Activity extends Activity {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private Button button_left;
    private Button button_right;
    private Button button_carmera;
    private Button button_file;

    private Uri imageUri;

    private TextView text_result;
    private ImageView picture;

    public static String TOKEN_PATH = "";


    @Override
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.println(this.getApplicationContext().getFilesDir().toString());
        TOKEN_PATH = this.getApplicationContext().getFilesDir().toString() + "token.txt";

//        TOKEN_PATH=this.getApplicationContext();
//        File file=new File();
        System.out.println("");
        setContentView(R.layout.activity_main3);
        initButton();
        initView();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Log.d("left", "left");
                intent.setClass(Main3Activity.this, Main2Activity.class);
                startActivity(intent);
                Main3Activity.this.finish();
            }
        });

//        button_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                Log.d("right", "right");
//                intent.setClass(Main3Activity.this, Main2Activity.class);
//                startActivity(intent);
//                Main3Activity.this.finish();
//            }
//        });

        button_carmera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建file对象，用于存储拍照后的图片；
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(Main3Activity.this,
                            "com.demo.yechao.arch.activity.fileprovider", outputImage);
                    Log.d("debug", "imageUri from take photo:" + imageUri);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                    System.out.println("imageUri from file:" + imageUri);

                }

                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        button_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Main3Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Main3Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }

    //打开相册
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bm);

                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        displayText(inputStream);
//                        byte[] byt = new byte[inputStream.available()];
//                        inputStream.read(byt);
//
//                        String base64String = Base64.encodeToString(byt, Base64.DEFAULT);
//                        Log.d("base64String", base64String);
//                        base64String = base64String.replaceAll("[\\s*\t\n\r]", "");
//                        Log.d("base64String", base64String);
//                        String res = ReadByCommon.getWord(base64String);
//                        Log.d("res", res);
//                        CharSequence r = new StringBuffer(res);
//                        text_result.setText(r);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {  //4.4及以上的系统使用这个方法处理图片；
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);  //4.4及以下的系统使用这个方法处理图片
                    }
                }
            default:
                break;
        }
    }

    private void displayText(InputStream inputStream) {
        try {
            byte[] byt = new byte[inputStream.available()];
            inputStream.read(byt);
            String base64String = Base64.encodeToString(byt, Base64.DEFAULT);
            Log.d("base64String", base64String);
            base64String = base64String.replaceAll("[\\s*\t\n\r]", "");
            Log.d("base64String", base64String);
            String res = ReadByCommon.getWord(base64String);
            Log.d("res", res);
            CharSequence r = new StringBuffer(res);
            text_result.setText(r);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("display text", "exception");

        }

    }

    private void initButton() {

        button_left = findViewById(R.id.button_left3);
//        button_right = findViewById(R.id.button_right3);
        button_carmera = findViewById(R.id.button_carmera);
        button_file = findViewById(R.id.button_file);
    }

    private void initView() {
        text_result = findViewById(R.id.text_result);
        picture = findViewById(R.id.imageView);

    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);
            displayText(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("uri", "url is null");
        }
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }


    /**
     * 4.4及以上的系统使用这个方法处理图片
     *
     * @param data
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        try {
            displayText(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            Log.e("uri", "url is null");
            e.printStackTrace();
        }
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果document类型的Uri,则通过document来处理
            String docID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docID.split(":")[1];     //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;

                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/piblic_downloads"), Long.valueOf(docID));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式使用
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取路径即可
            imagePath = uri.getPath();

        }

        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }


}
