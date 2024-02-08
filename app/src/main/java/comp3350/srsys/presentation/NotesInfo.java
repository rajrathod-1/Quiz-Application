package comp3350.srsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.objects.Note;
import comp3350.srsys.business.AccessNotes;

import comp3350.srsys.objects.Course;

public class NotesInfo extends Activity{

    private int selectedNotePosition = -1;

    private ArrayAdapter<Note> noteArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        AccessNotes accessNotes = new AccessNotes();
        List<Note> notes = accessNotes.getNotes();

        noteArrayAdapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, android.R.id.text1, notes)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);

                text1.setText(notes.get(position).getTitle());
                return view;
            }
        };

        ListView listView = (ListView)findViewById(R.id.listNotes);
        listView.setAdapter(noteArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note selected;

                if (position == selectedNotePosition) {
                    listView.setItemChecked(position, false);
                    selectedNotePosition = -1;
                } else {
                    listView.setItemChecked(position, true);

                    selectedNotePosition = position;

                    selected = noteArrayAdapter.getItem(position);
                    EditText title = (EditText)findViewById(R.id.noteTitle);
                    EditText content = (EditText)findViewById(R.id.noteContent);
                    title.setText(selected.getTitle());
                    content.setText(selected.getContent());

                }
            }
        });
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(NotesInfo.this, ClassesInfo.class);
            startActivity(intent);
        });



    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
