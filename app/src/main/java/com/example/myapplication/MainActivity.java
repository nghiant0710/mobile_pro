package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Database.UserTable;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Thêm các fragment vào ViewPager
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new RegisterFragment(), "Đăng ký");
        pagerAdapter.addFragment(new LoginFragment(), "Đăng nhập");
        viewPager.setAdapter(pagerAdapter);

        // Kết nối TabLayout với ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }

    static class PagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
        private final java.util.List<androidx.fragment.app.Fragment> fragmentList = new java.util.ArrayList<>();
        private final java.util.List<String> fragmentTitleList = new java.util.ArrayList<>();

        public PagerAdapter(androidx.fragment.app.FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        public void addFragment(androidx.fragment.app.Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @NonNull
        @Override
        public androidx.fragment.app.Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
