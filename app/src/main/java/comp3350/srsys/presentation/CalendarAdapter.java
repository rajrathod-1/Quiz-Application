package comp3350.srsys.presentation;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessCalendarUtils;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    public final ArrayList<LocalDate> days;
    public final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener) {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view = inflate.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() >= 15) {
            layoutParams.height = (int) (parent.getHeight() * 0.16);
        }
        else {
            layoutParams.height = (int) (parent.getHeight());
        }
        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder temp, int pos) {
        final LocalDate date = days.get(pos);
        if(date == null)
        {
            temp.dayOfMonth.setText("");
        }
        else {
            temp.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(AccessCalendarUtils.selectedDate))
            {
                temp.parentView.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }
}
