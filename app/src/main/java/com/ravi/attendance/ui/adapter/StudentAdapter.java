package com.ravi.attendance.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravi.attendance.R;
import com.ravi.attendance.data.model.StudentStatus;
import com.ravi.attendance.ui.attendance.AttendanceViewModel;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<StudentStatus> studentStatusList;
    private int currentIndex = -1;
    private final AttendanceViewModel viewModel;
//    private boolean isInternalUpdate = false;
    public StudentAdapter(AttendanceViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setData(List<StudentStatus> list) {
        this.studentStatusList = list;
        notifyDataSetChanged();
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentStatus status = studentStatusList.get(position);

        holder.tvSlNo.setText(String.valueOf(position + 1));
        holder.tvName.setText(status.student.studentName);
        holder.tvRoll.setText(status.student.rollNumber);

        holder.switchAbsent.setOnCheckedChangeListener(null);
        holder.switchAbsent.setChecked(status.isAbsent);
        int currentIndex = this.currentIndex;
        if (status.isAbsent && position == currentIndex) {
            holder.itemLayout.setBackgroundColor(Color.RED);
        } else if (status.isAbsent) {
            holder.itemLayout.setBackgroundColor(Color.RED);
        } else if (position == currentIndex && currentIndex != -1) {
            holder.itemLayout.setBackgroundColor(Color.YELLOW);
        } else {
            holder.itemLayout.setBackgroundColor(Color.WHITE);
        }


        holder.switchAbsent.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (viewModel.isFromButtonClick()) return;

            if (isChecked) {
                viewModel.markAbsentManually(status);
                holder.itemLayout.setBackgroundColor(Color.RED);
            } else {
                viewModel.unmarkAbsentManually(status);
                holder.itemLayout.setBackgroundColor(Color.WHITE);
            }
        });

    }


    @Override
    public int getItemCount() {
        return studentStatusList == null ? 0 : studentStatusList.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvSlNo, tvName, tvRoll;
        Switch switchAbsent;
        View itemLayout;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSlNo = itemView.findViewById(R.id.tvSlNo);
            tvName = itemView.findViewById(R.id.tvName);
            tvRoll = itemView.findViewById(R.id.tvRoll);
            switchAbsent = itemView.findViewById(R.id.switchAbsent);
            itemLayout = itemView.findViewById(R.id.item_layout);
        }
    }
}
