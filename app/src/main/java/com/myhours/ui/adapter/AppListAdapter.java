package com.myhours.ui.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myhours.databinding.AppListItemBinding;
import com.myhours.ui.model.AppList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.myhours.R.layout;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    private Context context;
    private List<AppList> appLists;
    private DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public AppListAdapter(Context context, List<AppList> appLists) {
        this.context = context;
        this.appLists = appLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppListItemBinding appListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layout.app_list_item, parent, false);
        return new ViewHolder(appListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppList appList = appLists.get(position);
        long lastTimeUsed = appList.usageStats.getLastTimeUsed();
        holder.appListItemBinding.tvAppName.setText(getAppNameFromPackage(appList.usageStats.getPackageName(), context));
        holder.appListItemBinding.tvPackageName.setText(mDateFormat.format(new Date(lastTimeUsed)));
        try {
            Glide.with(context).load(context.getPackageManager().getApplicationIcon(appList.usageStats.getPackageName())).into(holder.appListItemBinding.ivApp);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return appLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppListItemBinding appListItemBinding;

        public ViewHolder(@NonNull AppListItemBinding itemView) {
            super(itemView.getRoot());
            appListItemBinding = itemView;
        }
    }

    private String getAppNameFromPackage(String packageName, Context context) {
        final PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
        if (!applicationName.isEmpty())
            return applicationName;
        else
            return null;
    }
}
