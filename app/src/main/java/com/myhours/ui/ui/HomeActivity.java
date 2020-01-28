package com.myhours.ui.ui;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.myhours.R;
import com.myhours.databinding.ActivityHomeBinding;
import com.myhours.ui.adapter.AppListAdapter;
import com.myhours.ui.model.AppList;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding activityHomeBinding;
    private List<AppList> appLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        appLists = getInstalledApps();
        setRecyclerView();
    }

    private void setRecyclerView() {
        activityHomeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityHomeBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        AppListAdapter appListAdapter = new AppListAdapter(this, appLists);
        activityHomeBinding.recyclerView.setAdapter(appListAdapter);
        appListAdapter.notifyDataSetChanged();
    }

    private List<AppList> getInstalledApps() {
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packs = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p))) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable appIcon = p.applicationInfo.loadIcon(getPackageManager());
                String appPackageName = p.applicationInfo.packageName;
                appLists.add(new AppList(appName, appPackageName, appIcon));
            }
        }
        return appLists;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }
}
