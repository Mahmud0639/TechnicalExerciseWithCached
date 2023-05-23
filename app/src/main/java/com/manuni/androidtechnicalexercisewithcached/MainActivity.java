package com.manuni.androidtechnicalexercisewithcached;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.manuni.androidtechnicalexercisewithcached.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    RandomImageGenerator randomImageGenerator;
    CacheData cacheData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (isConnectionAvailable()) {
            binding.clickBtn.setText("Click Me");
            binding.clickBtn.setEnabled(true);
        } else {
            binding.clickBtn.setText("No Connection");
            binding.clickBtn.setEnabled(false);
        }


        randomImageGenerator = new RandomImageGenerator(MainActivity.this);

        cacheData = CacheData.getInstance();
        Bitmap nowCached = cacheData.getBitmapFromCache("MyKey");

        if (nowCached != null) {
            binding.imageViw.setImageBitmap(nowCached);
        } else {
            Toast.makeText(this, "No image available in the cache.", Toast.LENGTH_SHORT).show();
        }

        binding.clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnectionAvailable()) {
                    Random randomForWidthOrHeight = new Random();
                    int width = randomForWidthOrHeight.nextInt(101) + 200;
                    int height = randomForWidthOrHeight.nextInt(101) + 300;

                    randomImageGenerator.showImageRandomly(binding.imageViw, width, height);
                    binding.clickBtn.setText("Click Me");
                    binding.clickBtn.setEnabled(true);
                } else {
                    binding.clickBtn.setEnabled(false);
                    binding.clickBtn.setText("No connection");
                    Toast.makeText(MainActivity.this, "No connection. Please connect your device to further proceed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isConnectionAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobile.isConnected() || wifi.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


}