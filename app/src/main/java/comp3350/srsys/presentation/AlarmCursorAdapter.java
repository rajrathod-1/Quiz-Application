package comp3350.srsys.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.objects.Event;

public class AlarmCursorAdapter extends ArrayAdapter<Event> {

    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;
    private LayoutInflater inflater;

    public AlarmCursorAdapter(Context context, List<Event> events) {
        super(context, 0, events);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.activity_calendar_event_items, parent, false);
        }

        Event event = getItem(position);

        TextView mTitleText = listItemView.findViewById(R.id.recycle_title);
        TextView mDateAndTimeText = listItemView.findViewById(R.id.recycle_date_time);
        ImageView mThumbnailImage = listItemView.findViewById(R.id.thumbnail_image);

        mTitleText.setText(event.getTitle());

        // Handle null eventDateTime
        String dateTimeString;
        if (event.getNewDate() != null && event.getTime() != null) {
            dateTimeString = "Date: " + event.getNewDate() + " " + "Time:" + event.getTime();
        } else {
            dateTimeString = "No date/time set";
        }
        mDateAndTimeText.setText(dateTimeString);

        String letter = "A";
        if (event.getTitle() != null && !event.getTitle().isEmpty()) {
            letter = event.getTitle().substring(0, 1);
        }
        int color = mColorGenerator.getRandomColor();
        mDrawableBuilder = TextDrawable.builder().buildRound(letter, color);
        mThumbnailImage.setImageDrawable(mDrawableBuilder);

        return listItemView;
    }
}
