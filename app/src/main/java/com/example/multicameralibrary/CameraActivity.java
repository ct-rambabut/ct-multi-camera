package com.example.multicameralibrary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.multicameralibrary.camera.controls.Flash;
import com.example.multicameralibrary.camera.overlay.OverlayLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.multicameralibrary.camera.CameraListener;
import com.example.multicameralibrary.camera.CameraView;
import com.example.multicameralibrary.camera.PictureResult;
import com.example.multicameralibrary.camera.VideoResult;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.camera_testing)
    CameraView camera_testing;
    @BindView(R.id.fab_video)
    FloatingActionButton fabVideo;
    @BindView(R.id.fab_front)
    ImageView fabFront;

    @BindView(R.id.img_flash)
    ImageView img_flash;

    @BindView(R.id.watermark_logo)
    ImageView watermark_logo;

    @BindView(R.id.img_overlay)
    ImageView img_overlay;

    @BindView(R.id.fl_view)
    FrameLayout fl_view;

    @BindView(R.id.fl_view_hide)
    FrameLayout fl_view_hide;

    @BindView(R.id.txt_title)
    TextView txt_title;

    @BindView(R.id.txt_desc)
    TextView txt_desc;

    int position = 0 ;
    private ArrayList<ImageTags> arlImages;
    private float animation_flip=180f;
    ConstraintLayout.LayoutParams layoutParams;

    String watermark_logo_path = "https://www.cartradetech.com/images/logo.png";
    String camaspectratio = "full";//1:1/4:3/full
    private AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // go full screen
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        appLocationService = new AppLocationService(CameraActivity.this);

        //getting json response
        arlImages = (ArrayList<ImageTags>) getIntent().getExtras().getSerializable("data");
        position = getIntent().getExtras().getInt("pos");


        layoutParams = (ConstraintLayout.LayoutParams) camera_testing.getLayoutParams();
        camera_testing.setLifecycleOwner(this);
        camera_testing.setUseDeviceOrientation(true);
        camera_testing.setVideoMaxDuration(120 * 1000); // max 2mins
        camFlash();

        /*Camera settings*/
        if(camaspectratio!="") {
            if(camaspectratio.equalsIgnoreCase("full")){
                camera_testing.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }else {
                layoutParams.dimensionRatio = camaspectratio;
                camera_testing.setLayoutParams(layoutParams);
            }
        }

        if(watermark_logo_path!=""){
            watermark_logo.setVisibility(View.VISIBLE);
            Glide.with(CameraActivity.this)
                    .load(watermark_logo_path) // resizes the image to these dimensions (in pixel). resize does not respect aspect ratio
                    .into(watermark_logo);
        }else{
            watermark_logo.setVisibility(View.GONE);
        }

        /*water mark position*/
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) watermark_logo.getLayoutParams();
        params.gravity = Gravity.RIGHT|Gravity.BOTTOM;
        watermark_logo.setLayoutParams(params);

        /*desc position*/
        txt_desc.setGravity(Gravity.RIGHT|Gravity.TOP);


        /*image controls*/
        if(arlImages.get(position).getImgOverlayLogo()!=""){
            img_overlay.setVisibility(View.VISIBLE);
            Glide.with(CameraActivity.this)
                    .load(arlImages.get(position).getImgOverlayLogo()) // resizes the image to these dimensions (in pixel). resize does not respect aspect ratio
                    .into(img_overlay);
        }else{
            img_overlay.setVisibility(View.GONE);
        }

        if(arlImages.get(position).getImgName()!=""){
            txt_title.setVisibility(View.VISIBLE);
            txt_title.setText(arlImages.get(position).getImgName());
        }else{
            txt_title.setVisibility(View.GONE);
        }

        FrameLayout.LayoutParams fl_params = (FrameLayout.LayoutParams) fl_view.getLayoutParams();
        FrameLayout.LayoutParams fl_hide_params = (FrameLayout.LayoutParams) fl_view_hide.getLayoutParams();


        camera_testing.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                PicturePreviewActivity.setPictureResult(result);
                Intent intent = new Intent(CameraActivity.this, PicturePreviewActivity.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putInt("pos", position);
                bundleObject.putSerializable("data", (Serializable) arlImages);
                intent.putExtras(bundleObject);
                startActivity(intent);
            }

            @Override
            public void onVideoTaken(@NonNull VideoResult result) {
                super.onVideoTaken(result);
                VideoPreviewActivity.setVideoResult(result);
                Intent intent = new Intent(CameraActivity.this, VideoPreviewActivity.class);
                startActivity(intent);

                // refresh gallery
                MediaScannerConnection.scanFile(CameraActivity.this,
                        new String[]{result.getFile().toString()}, null,
                        (filePath, uri) -> {
                            Log.i("ExternalStorage", "Scanned " + filePath + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        });
            }



            public void onOrientationChanged(int orientation,int width,int height){

                if(orientation%180!=0) {

                    fl_params.width = height;
                    fl_params.height = width;
                    fl_view.setLayoutParams(fl_params);
                    fl_view.setRotation(((float)orientation - 180f));

                    fl_hide_params.width = height;
                    fl_hide_params.height = width ;
                    fl_view_hide.setLayoutParams(fl_hide_params);
                    fl_view_hide.setRotation(((float)orientation - 180f));


                }else {


                    fl_params.width = width;
                    fl_params.height = height;
                    fl_view.setLayoutParams(fl_params);
                    fl_view.setRotation(((float)orientation));

                    fl_hide_params.width = width;
                    fl_hide_params.height = height ;
                    fl_view_hide.setLayoutParams(fl_hide_params);
                    fl_view_hide.setRotation(((float)orientation));

                }

            }
        });

        PermissionUtils.requestReadWriteAppPermissions(this);

    }

    @OnClick(R.id.fab_video)
    void captureVideoSnapshot() {
        if (camera_testing.isTakingVideo()) {
            camera_testing.stopVideo();
            fabVideo.setImageResource(R.drawable.ic_videocam_black_24dp);
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss", Locale.US);
        String currentTimeStamp = dateFormat.format(new Date());

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "CameraViewFreeDrawing";
        File outputDir= new File(path);
        outputDir.mkdirs();
        File saveTo = new File(path + File.separator + currentTimeStamp + ".mp4");
        camera_testing.takeVideoSnapshot(saveTo);

        fabVideo.setImageResource(R.drawable.ic_stop_black_24dp);
    }

    @OnClick(R.id.fab_picture)
    void capturePictureSnapshot() {
        if (camera_testing.isTakingVideo()) {
            Toast.makeText(this, "Already taking video.", Toast.LENGTH_SHORT).show();
            return;
        }
       camera_testing.takePictureSnapshot();
        //camera_testing.takePicture();
    }

    @OnClick(R.id.fab_front)
    void toggleCamera() {

        if (camera_testing.isTakingPicture() || camera_testing.isTakingVideo()) return;
        fabFront.animate().rotation(animation_flip).setDuration(1000).start();
        camera_testing.toggleFacing();
        if(animation_flip==180f){
            animation_flip = animation_flip - 180f;
        }else{
            animation_flip = 180f;
        }
        /*switch (camera_testing.toggleFacing()) {
            case BACK:
                //fabFront.setImageResource(R.drawable.ic_convert_3208);
                fabFront.animate().rotation(animation_flip).setDuration(1000).start();
                break;

            case FRONT:
                //fabFront.setImageResource(R.drawable.ic_convert_3208);
                fabFront.animate().rotation(animation_flip).setDuration(1000).start();
                break;
        }*/
    }

    @OnClick(R.id.img_flash)

    void flashclick(){
        switch (Pref.getIn(CameraActivity.this).getCameraFlash()) {
            case "auto":
                Pref.getIn(CameraActivity.this).saveCameraFlash("on");
                break;
            case "on":
                Pref.getIn(CameraActivity.this).saveCameraFlash("off");
                break;
            case "off":
                Pref.getIn(CameraActivity.this).saveCameraFlash("auto");
                break;
            case "":
                Pref.getIn(CameraActivity.this).saveCameraFlash("auto");
                break;
        }
        camFlash();
    }

    void camFlash() {

        if(Pref.getIn(CameraActivity.this).getCameraFlash().equals("")|| Pref.getIn(CameraActivity.this).getCameraFlash().equals("off")) {
           // layoutParams.dimensionRatio = "9:16";
            //camera_testing.setLayoutParams(layoutParams);
            //camera_testing.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            camera_testing.setFlash(Flash.OFF);
            img_flash.setImageResource(R.drawable.ic_flash_off_24);
        } else if(Pref.getIn(CameraActivity.this).getCameraFlash().equals("on")) {
            //layoutParams.dimensionRatio = "3:4";
            //camera_testing.setLayoutParams(layoutParams);
            camera_testing.setFlash(Flash.ON);
            img_flash.setImageResource(R.drawable.ic_flash_on_24);
        }else if(Pref.getIn(CameraActivity.this).getCameraFlash().equals("auto")) {
            //layoutParams.dimensionRatio = "1:1";
            //camera_testing.setLayoutParams(layoutParams);
            camera_testing.setFlash(Flash.AUTO);
            img_flash.setImageResource(R.drawable.ic_flash_auto_24);
        }else{
            /*layoutParams.dimensionRatio = "9:16";
            camera_testing.setLayoutParams(layoutParams);
            camera_testing.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));*/
            camera_testing.setFlash(Flash.OFF);
            img_flash.setImageResource(R.drawable.ic_flash_off_24);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

}
