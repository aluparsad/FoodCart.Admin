package com.icmi.foodcartadmin.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.icmi.foodcartadmin.Home.Fragments.ScreenSlidePagerAdapter;
import com.icmi.foodcartadmin.NewProduct.AddProductActivity;
import com.icmi.foodcartadmin.R;


public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    private void Init() {
        viewPager = findViewById(R.id.VP2_orders);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();

        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            startActivity(new Intent(this, AddProductActivity.class));
        });

    }

}

