package com.example.shamik.wally;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {

    Intent intent;
    ImageView imageView;
    Button btnSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        // Action Bar settings
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        btnSet = findViewById(R.id.btn_set_wallpaper);
        imageView = findViewById(R.id.wallpaper_img);
        intent = getIntent();

        String url = intent.getStringExtra("imageUrl");
        Picasso.get().load(url).into(imageView);

        btnSet.setOnClickListener(v -> {

            try {
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                wallpaperManager.setBitmap(bitmap);
                Toast.makeText(getApplicationContext(),"Wallpaper Set Successfully",Toast.LENGTH_LONG).show();
            }catch (IOException e){
                e.printStackTrace();
            }

        });

    }
}