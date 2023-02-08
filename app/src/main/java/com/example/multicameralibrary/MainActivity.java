package com.example.multicameralibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String jsonstring = "{\"imagetags\": [  {    \"imgId\": \"1\",    \"imgSno\": \"1\",    \"imgName\": \"File No \",    \"imgMand\": \"y\",    \"imgLogo\": \"\",    \"imgOrientation\": \"P\",    \"imgLat\": \"\",    \"imgLong\": \"\",    \"imgTime\": \"\",    \"imgPath\": \"\",    \"imgFlag\": \"0\",    \"imgOverlayLogo\": \"https://w7.pngwing.com/pngs/629/825/png-transparent-car-cartoon-car-parts-compact-car-vehicle-transport-thumbnail.png\"  },  {    \"imgId\": \"90\",    \"imgSno\": \"2\",    \"imgName\": \"Authorisation Letter \",    \"imgMand\": \"y\",    \"imgLogo\": \"\",    \"imgOrientation\": \"P\",    \"imgLat\": \"\",    \"imgLong\": \"\",    \"imgTime\": \"\",    \"imgPath\": \"\",    \"imgFlag\": \"0\",    \"imgOverlayLogo\":\"\"  },  {    \"imgId\": \"91\",    \"imgSno\": \"3\",    \"imgName\": \"Pre repossession \",    \"imgMand\": \"y\",    \"imgLogo\": \"\",    \"imgOrientation\": \"P\",    \"imgLat\": \"\",    \"imgLong\": \"\",    \"imgTime\": \"\",    \"imgPath\": \"\",    \"imgFlag\": \"0\"  },  {    \"imgId\": \"87\",    \"imgSno\": \"4\",    \"imgName\": \"Post repossession \",    \"imgMand\": \"y\",    \"imgLogo\": \"\",    \"imgOrientation\": \"P\",    \"imgLat\": \"\",    \"imgLong\": \"\",    \"imgTime\": \"\",    \"imgPath\": \"\",    \"imgFlag\": \"0\",    \"imgOverlayLogo\":\"\"  },  {    \"imgId\": \"88\",    \"imgSno\": \"5\",    \"imgName\": \"Surrender Letter \",    \"imgMand\": \"y\",    \"imgLogo\": \"\",    \"imgOrientation\": \"P\",    \"imgLat\": \"\",    \"imgLong\": \"\",    \"imgTime\": \"\",    \"imgPath\": \"\",    \"imgFlag\": \"0\",    \"imgOverlayLogo\":\"\"  },  {    \"imgId\": \"89\",    \"imgSno\": \"6\",    \"imgName\": \"Inventory Sheet \",    \"imgMand\": \"y\",    \"imgLogo\": \"\",    \"imgOrientation\": \"P\",    \"imgLat\": \"\",    \"imgLong\": \"\",    \"imgTime\": \"\",    \"imgPath\": \"\",    \"imgFlag\": \"0\",    \"imgOverlayLogo\":\"\"  },  {    \"imgId\": \"86\",    \"imgSno\": \"7\",    \"imgName\": \"Other \",    \"imgMand\": \"n\",    \"imgLogo\": \"\",    \"imgOrientation\": \"P\",    \"imgLat\": \"\",    \"imgLong\": \"\",    \"imgTime\": \"\",    \"imgPath\": \"\",    \"imgFlag\": \"0\",    \"imgOverlayLogo\":\"\"  }],\"status\": 1,\"details\": \"Ok\",\"newversion_notice\": 3\n" +
            "}";
    List<ImageTags> arlImages = new ArrayList<>();
    int image_count = 0;
    private RecyclerView recycler_imageTags;
    private ImageAdapter img_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btn_camera = findViewById(R.id.btn_camera);
        TextView tv_info = findViewById(R.id.tv_info);
        recycler_imageTags = (RecyclerView) findViewById(R.id.recycler_imageTags);

        recycler_imageTags.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        arlImages.clear();


        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putInt("pos", 0);
                bundleObject.putString("from", "images");
                bundleObject.putString("lan", "123456");
                bundleObject.putSerializable("data", (Serializable) arlImages);
                intent.putExtras(bundleObject);
                startActivity(intent);
                finish();
            }
        });

        if (getIntent().getExtras() != null) {
            arlImages.clear();
            image_count = getIntent().getExtras().getInt("pos");
            arlImages = (ArrayList<ImageTags>) getIntent().getExtras().getSerializable("data");
            if(arlImages!=null && arlImages.size()>0) {
                tv_info.setText("count:" + arlImages.size() + "");
                img_adapter = new ImageAdapter(MainActivity.this, arlImages);
                recycler_imageTags.setAdapter(img_adapter);
            }
        }else{
            img_adapter = new ImageAdapter(MainActivity.this, arlImages);
            recycler_imageTags.setAdapter(img_adapter);
            checkcamera();
        }

    }

    private void checkcamera() {


        JSONObject object = null;
        try {
            object = new JSONObject(jsonstring);
            JSONArray images = object.getJSONArray("imagetags");
            arlImages.clear();
            for (int i = 0; i < images.length(); i++) {
                JSONObject rec = images.getJSONObject(i);
                arlImages.add(new Gson().fromJson(images.get(i).toString(), ImageTags.class));

            }
            img_adapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        public Context context;
        public List<ImageTags> _list_data;
        ViewHolder vh;

        public ImageAdapter(Context context, List<ImageTags> imagesList) {
            this._list_data = imagesList;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_items, parent, false);
            vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.tv_picture.setText(position + "::" + arlImages.get(position).getImgName());

            Glide.with(context)
                    .load(arlImages.get(position).getImgPath()) // resizes the image to these dimensions (in pixel). resize does not respect aspect ratio
                    .into(holder.iv_picture);
            holder.iv_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, CameraActivity.class);
                    Bundle bundleObject = new Bundle();
                    bundleObject.putInt("pos", position);
                    bundleObject.putSerializable("data", (Serializable) arlImages);
                    i.putExtras(bundleObject);
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return _list_data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_picture;
            public ImageView iv_picture;
            //   protected Images images;

            public ViewHolder(View view) {
                super(view);
                this.tv_picture = view.findViewById(R.id.tv_picture);
                this.iv_picture = view.findViewById(R.id.iv_picture);
            }
        }
    }
}