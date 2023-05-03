package com.example.music_player.activity;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;


import android.annotation.SuppressLint;


import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.music_player.MyApplication;
import com.example.music_player.R;
import com.example.music_player.database.LikeDatabase;


import java.io.File;

import java.io.IOException;

public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    //进度条
    private SeekBar seekBar;
    private TextView tv_progress, tv_total, name_song;
    public static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;
    ImageView picture;


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        //进度条上小绿点的位置，也就是当前已播放时间
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        //进度条的总长度，就是总时间
        tv_total = (TextView) findViewById(R.id.tv_total);

        //进度条的控件
        seekBar = (SeekBar) findViewById(R.id.sb);
        Intent intent = getIntent();
        String musicName = intent.getStringExtra("musicName");//得到歌名
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource("/storage/emulated/0/" + musicName);//找到播放地址
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int duration = Integer.parseInt(time);//毫秒
        int min = duration / 1000 / 60;//分
        int sec = duration / 1000 % 60;//秒
        String Min = Integer.toString(min);
        String Sec = Integer.toString(sec);
        String song_time = Min + ":" + Sec;
        tv_total.setText(song_time);//设置总时间
        try {
            retriever.release();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {//让进度条动起来
            @Override
            public void onPrepared(MediaPlayer mp) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mediaPlayer.isPlaying()) {
                            int progress = mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(progress);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
        // 设置SeekBar的最大值为音乐的总长度
        seekBar.setMax(duration);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {//移动进度条改变时间
                    mediaPlayer.seekTo(i);
                    seekBar.setMax(duration);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //歌曲名显示的控件
        name_song = (TextView) findViewById(R.id.song_name);
        name_song.setText(musicName);
        //绑定控件的同时设置点击事件监听器
        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_continue_play).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);


        File sdDir = Environment.getExternalStorageDirectory();
        String sdpath = sdDir.getAbsolutePath();//路径
        Toast.makeText(this, sdpath.toString(), Toast.LENGTH_SHORT).show();
        //动态申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_AUDIO}, 1);
        } else {
            initMediaPlayer(); // 初始化MediaPlayer
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //创建菜单的方法，第一个参数是通过哪一个资源文件创建菜单，
        //第二个是将菜单添加到哪一个Menu对象里？，返回true才会显示出来
        getMenuInflater().inflate(R.menu.change_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_picture:
                if (ContextCompat.checkSelfPermission(PlayMusicActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    //弹出权限申请对话框,请求用户授予写外部存储权限。第一个参数是在哪个Activity弹出,第二个参数是权限列表,第三个参数是请求码,用于在回调中判断是哪个权限的请求结果。
                    ActivityCompat.requestPermissions(PlayMusicActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
                } else {
                    openAlbum();
                }
                break;
            case R.id.add_like:
                LikeDatabase likeDatabase = new LikeDatabase(MyApplication.getContextObject(), "Likes.db", null, 1);
                SQLiteDatabase writableDatabase = likeDatabase.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("song_name", name_song.getText().toString());
                writableDatabase.insert("Likes", null, contentValues);
                contentValues.clear();
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_like:
                likeDatabase = new LikeDatabase(MyApplication.getContextObject(), "Likes.db", null, 1);
                writableDatabase = likeDatabase.getWritableDatabase();
                writableDatabase.delete("Likes", "song_name = ?", new String[]{name_song.getText().toString()});
                Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    private void openAlbum() {
        //GET_CONTENT是一个标准的action,用于打开其他应用的文件选择界面,允许用户选择要获取的内容。当用户选择好文件后,结果会返回到创建的Activity中。
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        //设置了Intent的 MIME 类型为image/*,意味着只显示图片类型的文件,过滤其他类型的内容。所以用户只能选择图片
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); //打开相册，第二个参数是requestCode
    }


    private void initMediaPlayer() {
        try {
            Intent intent = getIntent();
            String musicName = intent.getStringExtra("musicName");
            File file = new File(Environment.getExternalStorageDirectory(), musicName);
            mediaPlayer.setDataSource(file.getPath()); // 指定音频文件的路径
            mediaPlayer.prepare(); // 让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case 2:
                //判断用户是否同意了权限申请。如果同意,grantResults数组第一个元素会返回PERMISSION_GRANTED。
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You dennied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;

            default:
                break;
        }
    }

    @TargetApi(19)   //在使用高版本API方法前加
    private void handleImageOnKitKat(Intent data) {
        //从Intent中获取图片的Uri
        String imagePath = null;
        Uri uri = data.getData();

        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            //如果是Document类型的Uri,则根据Authority判断是从相册还是下载目录获取的文件,并使用不同的方法解析出图片id,最终构建图片Uri
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                /*
                * 这行代码从Document Uri的docId中解析出图片id。docId的格式通常是:
                article:123456
                所以这里使用":"分割,并取后半部分,得到图片id:123456。*/

                String id = docId.split(":")[1];  //解析出数字格式的id

                /*这行代码构建MediaStore图片Uri所需要的selection条件。格式为:_ID=图片id。
                  所以如果图片id是123456,selection为:_ID=123456。selection相当于路径*/

                String selection = MediaStore.Images.Media._ID + "=" + id;
                /*这行代码使用构建的selection,从MediaStore的Uri中查询图片,并获取图片路径。
                  EXTERNAL_CONTENT_URI表示外部存储卡中的图片Uri。*/
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downsloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); //根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        /*Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder)
        - uri:要查询的内容URI,指明查询哪个内容提供者中的数据。
        - projection:要查询的列,null表示查询所有列。
        - selection:查询条件(where语句),可为空表示查询所有数据。
        - selectionArgs:查询条件的参数值,与selection条件配合使用。
        - sortOrder:排序方式(order by语句),可为空不排序。
        所以这个方法的主要作用是根据Uri,查询内容提供者中的数据,你可以通过projection指定查询哪些列,selection和selectionArgs添加查询条件来过滤数据,sortOrder来指定排序方式。
        */
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString((int) cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {//设置图片
        picture = findViewById(R.id.iv_music);
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start(); // 开始播放
                }
                break;
            case R.id.btn_pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause(); // 暂停播放
                }
                break;
            case R.id.btn_continue_play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start(); // 恢复播放
                }
                break;
            case R.id.btn_exit:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset(); // 停止播放
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}