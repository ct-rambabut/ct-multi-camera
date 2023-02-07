package com.example.multicameralibrary;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multicameralibrary.camera.controls.Flash;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.multicameralibrary.camera.CameraListener;
import com.example.multicameralibrary.camera.CameraView;
import com.example.multicameralibrary.camera.PictureResult;
import com.example.multicameralibrary.camera.VideoResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.camera_testing)
    CameraView camera_testing;
    @BindView(R.id.fab_video)
    FloatingActionButton fabVideo;
    @BindView(R.id.fab_front)
    FloatingActionButton fabFront;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // go full screen
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /*ViewGroup.LayoutParams view = camera_testing.getLayoutParams();
        view.setdimenation

        val layoutParams = imageView.layoutParams as ConstraintLayout.ViewGroup.LayoutParams
        layoutParams.dimensionRatio = "H,2:1"
        imageView.layoutParams = layoutParams
        //camera_testing.setLayoutParams(view);
        camera_testing.ResetAspect();*/
        //camera_testing.setLayoutParams(new ViewGroup.LayoutParams(view.width, view.width));//working
        //camera_testing.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));//working
        //(view.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "16:9"

        //view.dimensionRation = "16:9";



        camera_testing.setLifecycleOwner(this);

        camera_testing.setUseDeviceOrientation(true);

        camera_testing.setFlash(Flash.ON);

        camera_testing.setVideoMaxDuration(120 * 1000); // max 2mins
        camera_testing.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                PicturePreviewActivity.setPictureResult(result);
                Intent intent = new Intent(MainActivity.this, PicturePreviewActivity.class);
                startActivity(intent);
            }

            @Override
            public void onVideoTaken(@NonNull VideoResult result) {
                super.onVideoTaken(result);
                VideoPreviewActivity.setVideoResult(result);
                Intent intent = new Intent(MainActivity.this, VideoPreviewActivity.class);
                startActivity(intent);

                // refresh gallery
                MediaScannerConnection.scanFile(MainActivity.this,
                        new String[]{result.getFile().toString()}, null,
                        (filePath, uri) -> {
                            Log.i("ExternalStorage", "Scanned " + filePath + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        });
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
        switch (camera_testing.toggleFacing()) {
            case BACK:
                fabFront.setImageResource(R.drawable.ic_camera_front_black_24dp);
                break;

            case FRONT:
                fabFront.setImageResource(R.drawable.ic_camera_rear_black_24dp);
                break;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }
}
