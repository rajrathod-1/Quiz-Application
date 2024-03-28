package comp3350.srsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import comp3350.srsys.application.Services;
import comp3350.srsys.business.validators.NoteValidator;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Note;
import comp3350.srsys.business.AccessNotes;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;


public class NotesInfo extends Activity{

    private int selectedNotePosition = -1;

    private ArrayAdapter<Note> noteArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Intent thisIntent = getIntent();
        Class<? extends Course> clazz = Course.class;
        Course receivedCourse = (Course) thisIntent.getSerializableExtra("courseKey", clazz);
        selectedNotePosition = thisIntent.getIntExtra("selectedNotePos", selectedNotePosition);

        TextView pageTitle = findViewById(R.id.courseTitle);
        pageTitle.setText(receivedCourse.getCourseName());

        AccessNotes accessNotes = new AccessNotes(receivedCourse);
        List<Note> notes = accessNotes.getNotes();
        accessNotes.sortByDate();
        if(notes.size()>0){
            selectedNotePosition = thisIntent.getIntExtra("selectedNotePos", 0);
            Note selected = notes.get(selectedNotePosition);
            EditText title = (EditText)findViewById(R.id.noteTitle);
            EditText content = (EditText)findViewById(R.id.noteContent);
            title.setText(selected.getTitle());
            content.setText(selected.getContent());
        }



        if(notes.size()>0){
            selectedNotePosition = thisIntent.getIntExtra("selectedNotePos", 0);
            Note selected = notes.get(selectedNotePosition);
            EditText title = (EditText)findViewById(R.id.noteTitle);
            EditText content = (EditText)findViewById(R.id.noteContent);
            title.setText(selected.getTitle());
            content.setText(selected.getContent());
        }

        noteArrayAdapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, android.R.id.text1, notes)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setText(notes.get(position).getTitle());

                //changing background colour when selected to highlight selected
                boolean shouldHighlight = (position == selectedNotePosition);

                int backgroundColor = shouldHighlight ? ContextCompat.getColor(getContext(), R.color.highlightColor) : Color.TRANSPARENT;
                view.setBackgroundColor(backgroundColor);
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
                    noteArrayAdapter.notifyDataSetChanged();
                }
            }
        });
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Services.clean();
            Intent intent = new Intent(NotesInfo.this, ClassesInfo.class);
            startActivity(intent);
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
                            selectedNotePosition = selectedNotePosition-1;
                            noteArrayAdapter.notifyDataSetChanged();
                            popupWindow.dismiss();
                            EditText title = (EditText)findViewById(R.id.noteTitle);
                            EditText content = (EditText)findViewById(R.id.noteContent);

                            if(notes.size()==0){
                                title.setText("");
                                content.setText("");
                            }else {
                                if(selectedNotePosition<0)
                                    selectedNotePosition=0;
                                selected = notes.get(selectedNotePosition);
                                title.setText(selected.getTitle());
                                content.setText(selected.getContent());
                            }

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
                accessNotes.insertNote(receivedCourse.getTopic(), receivedCourse.getCourseNum());
                selectedNotePosition = 0;
                restartActivity();
                noteArrayAdapter.notifyDataSetChanged();
            }
        });

        Button buttonToSortByTitle = findViewById(R.id.sortByTitleButton);
        buttonToSortByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notes.size()>0) {
                    Note selected = notes.get(selectedNotePosition);
                    accessNotes.sortNotesByTitle();
                    selectedNotePosition = notes.indexOf(selected);
                    noteArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        Button buttonToSortByLastEdited = findViewById(R.id.sortByLastEditedButton);
        buttonToSortByLastEdited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notes.size()>0 ) {
                    Note selected = notes.get(selectedNotePosition);
                    accessNotes.sortByDate();
                    selectedNotePosition = notes.indexOf(selected);
                    noteArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        Button buttonToSave = findViewById(R.id.saveNoteButton);
        buttonToSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTitle = (EditText)findViewById(R.id.noteTitle);
                String updatedTitle = editTitle.getText().toString();
                EditText editContent = (EditText)findViewById(R.id.noteContent);
                String updatedContent = editContent.getText().toString();
                if(selectedNotePosition>-1) {
                    if (NoteValidator.validateNoteName(updatedTitle) && NoteValidator.validateContent(updatedContent)){
                        Note currentNote = noteArrayAdapter.getItem(selectedNotePosition);
                        currentNote.setTitle(updatedTitle);
                        currentNote.setContent(updatedContent);
                        accessNotes.updateNotes(currentNote);
                        selectedNotePosition=0;
                        restartActivity();
                        Toast.makeText(getApplicationContext(), "New Note Successfully Saved", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Note title or content size too large", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void restartActivity() {
        finish();
        overridePendingTransition(0, 0);
        getIntent().putExtra("selectedNotePos", selectedNotePosition);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    //these just save the selected note when tablet orientation changes
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("selectedNotePos", selectedNotePosition);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedNotePosition = savedInstanceState.getInt("selectedNotePos");
    }
}