package comp3350.srsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Note;
import comp3350.srsys.business.AccessNotes;
import android.text.Editable;
import android.text.TextWatcher;



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

        EditText editTitle = findViewById(R.id.noteTitle);
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String updatedText = editable.toString();

                if(selectedNotePosition>-1) {
                    Note currentNote = noteArrayAdapter.getItem(selectedNotePosition);
                    if (!updatedText.equals(currentNote.getTitle())) {
                        currentNote.setTitle(updatedText);
                        accessNotes.updateNotes(currentNote);
                        noteArrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        EditText editContent = findViewById(R.id.noteContent);
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

                String updatedText = editable.toString();

                if(selectedNotePosition>-1) {
                    Note currentNote = noteArrayAdapter.getItem(selectedNotePosition);
                    if(!updatedText.equals(currentNote.getContent())) {
                        currentNote.setContent(updatedText);
                        accessNotes.updateNotes(currentNote);
                        noteArrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        Button buttonToDeleteNote = findViewById(R.id.deleteNoteButton);
        buttonToDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedNotePosition>-1) {
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.activity_delete_note, null);

                    boolean focusable = true;
                    final PopupWindow popupWindow = new PopupWindow(popupView,
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, focusable);

                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    Button yesButton = popupView.findViewById(R.id.yesButton);
                    yesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Note selected = noteArrayAdapter.getItem(selectedNotePosition);
                            accessNotes.deleteNotes(selected);
                            noteArrayAdapter.notifyDataSetChanged();
                            popupWindow.dismiss();
                            selectedNotePosition = -1;
                        }
                    });

                    Button noButton = popupView.findViewById(R.id.noButton);
                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
            }
        });

        Button buttonToAddNote = findViewById(R.id.addNoteButton);
        buttonToAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessNotes.insertNote();
                noteArrayAdapter.notifyDataSetChanged();
            }
        });

        Button buttonToSortByTitle = findViewById(R.id.sortByTitleButton);
        buttonToSortByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessNotes.sortNotesByTitle();
                noteArrayAdapter.notifyDataSetChanged();
            }
        });

        Button buttonToSortByLastEdited = findViewById(R.id.sortByLastEditedButton);
        buttonToSortByLastEdited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessNotes.sortByDate();
                noteArrayAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
