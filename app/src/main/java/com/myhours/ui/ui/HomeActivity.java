package com.myhours.ui.ui;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.myhours.R;
import com.myhours.databinding.ActivityHomeBinding;
import com.myhours.ui.adapter.AppListAdapter;
import com.myhours.ui.model.AppList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding activityHomeBinding;
    private List<AppList> appLists = new ArrayList<>();
    private UsageStatsManager mUsageStatsManager;
    private AppListAdapter appListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        }
        setRecyclerView();
        List<UsageStats> usageStatsList = getUsageStatistics(UsageStatsManager.INTERVAL_DAILY);
        Collections.sort(usageStatsList, new LastTimeLaunchedComparatorDesc());
        getInstalledApps(usageStatsList);
    }


    private void setRecyclerView() {
        activityHomeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityHomeBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        appListAdapter = new AppListAdapter(this, appLists);
        activityHomeBinding.recyclerView.setAdapter(appListAdapter);
    }

    private void getInstalledApps(List<UsageStats> usageStatsList) {
        appLists.clear();
        for (UsageStats usageStats : usageStatsList) {
            AppList list = new AppList(usageStats);
            appLists.add(list);
            appListAdapter.notifyDataSetChanged();
        }
    }

    private static class LastTimeLaunchedComparatorDesc implements Comparator<UsageStats> {
        @Override
        public int compare(UsageStats left, UsageStats right) {
            return Long.compare(right.getLastTimeUsed(), left.getLastTimeUsed());
        }
    }

    public List<UsageStats> getUsageStatistics(int intervalType) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        List<UsageStats> queryUsageStats = mUsageStatsManager
                .queryUsageStats(intervalType, cal.getTimeInMillis(),
                        System.currentTimeMillis());
        if (queryUsageStats.size() == 0) {
            Log.i("duhui", "The user may not allow the access to apps usage. ");
            Toast.makeText(this,
                    "Not enabled",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
        return queryUsageStats;
    }
}
