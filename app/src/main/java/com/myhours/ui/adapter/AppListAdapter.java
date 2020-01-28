package com.myhours.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myhours.databinding.AppListItemBinding;
import com.myhours.ui.model.AppList;

import java.util.List;

import static com.myhours.R.layout;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    private Context context;
    private List<AppList> appLists;

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
        holder.appListItemBinding.tvAppName.setText(appList.getAppName());
        holder.appListItemBinding.tvPackageName.setText(appList.getAppPackageName());
        Glide.with(context).load(appList.getAppIcon()).into(holder.appListItemBinding.ivApp);
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
}
