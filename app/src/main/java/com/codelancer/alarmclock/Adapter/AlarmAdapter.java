package com.codelancer.alarmclock.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codelancer.alarmclock.Alarm.Alarm;
import com.codelancer.alarmclock.Database.AlarmModel;
import com.codelancer.alarmclock.Database.ViewModel;
import com.codelancer.alarmclock.EditAlarm;
import com.codelancer.alarmclock.Helper;
import com.codelancer.alarmclock.R;

public class AlarmAdapter extends ListAdapter<AlarmModel, AlarmAdapter.ViewHolder> {

    private final Context context;
    public AlarmAdapter(@NonNull DiffUtil.ItemCallback<AlarmModel> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlarmModel alarmModel = getItem(position);
        holder.bind(alarmModel);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditAlarm.class);
            startEdit(intent, alarmModel);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, label, amp;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch active;
        ViewModel viewModel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.timeText);
            label = itemView.findViewById(R.id.labelText);
            active = itemView.findViewById(R.id.activeSwitch);
            amp = itemView.findViewById(R.id.ampmTextView);
            viewModel = new ViewModelProvider((ViewModelStoreOwner) itemView.getContext()).get(ViewModel.class);
        }

        static ViewHolder create(ViewGroup parent){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_item, parent, false);
            return new ViewHolder(view);
        }


        void bind(AlarmModel alarmModel){
            time.setText(Helper.getTimeString(alarmModel.getTime())[0]);
            label.setText(alarmModel.getLabel());
            active.setChecked(alarmModel.isActive());
            amp.setText(Helper.getTimeString(alarmModel.getTime())[1]);
            active.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Alarm alarm = new Alarm(buttonView.getContext());
                if(isChecked){
                    alarmModel.setActive(true);
                    alarm.setAlarm(alarmModel);
                }else{
                    alarmModel.setActive(false);
                    alarm.deleteAlarm(alarmModel);
                }
            });
        }
    }

    public static class AlarmDiff extends DiffUtil.ItemCallback<AlarmModel>{
        @Nullable
        @Override
        public Object getChangePayload(@NonNull AlarmModel oldItem, @NonNull AlarmModel newItem) {
            return super.getChangePayload(oldItem, newItem);
        }

        @Override
        public boolean areItemsTheSame(@NonNull AlarmModel oldItem, @NonNull AlarmModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AlarmModel oldItem, @NonNull AlarmModel newItem) {
            return oldItem.getTime().equals(newItem.getTime())
                    && oldItem.getLabel().equals(newItem.getLabel())
                    && String.valueOf(oldItem.getId()).equals(String.valueOf(newItem.getId()))
                    && String.valueOf(oldItem.isActive()).equals(String.valueOf(newItem.isActive()))
                    && String.valueOf(oldItem.isRepeate()).equals(String.valueOf(newItem.isRepeate()))
                    && String.valueOf(oldItem.isVibrate()).equals(String.valueOf(newItem.isVibrate()));
        }
    }

    public void startEdit(Intent intent, AlarmModel alarmModel){
        Bundle bundle = new Bundle();
        bundle.putSerializable(context.getString(R.string.model_name), alarmModel);
        intent.putExtra(context.getString(R.string.bundle_name), bundle);
        context.startActivity(intent);
    }
}


