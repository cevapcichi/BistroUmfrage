package de.munich.iteratec.bistroumfrage;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Question> questionList = new ArrayList<>();
    private EditText questionEdit;
    private EditText a1Edit;
    private EditText a2Edit;
    private EditText a3Edit;
    private EditText a4Edit;
    private FloatingActionButton addBtn;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionEdit = (EditText) findViewById(R.id.editQuestion);
        a1Edit = (EditText) findViewById(R.id.editA1);
        a2Edit = (EditText) findViewById(R.id.editA2);
        a3Edit = (EditText) findViewById(R.id.editA3);
        a4Edit = (EditText) findViewById(R.id.editA4);
        addBtn = (FloatingActionButton) findViewById(R.id.addFLoatingBtn);


        //test question
        questionList.add(new Question("Das ist ein Test", "\uD83D\uDE03", "\uD83D\uDE09", "\uD83D\uDCA9", "\uD83D\uDE48"));


        //set custom ArrayAdapter to ListView
        final CustomAdapter adapter = new CustomAdapter(this, questionList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);


        //FloatingActionButton adds a new question to the ArrayList, clears the EditTexts and notifies the adapter for changes.
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionList.add(new Question(questionEdit.getText().toString(), a1Edit.getText().toString(), a2Edit.getText().toString(), a3Edit.getText().toString(), a4Edit.getText().toString()));
                clearEditText();
                adapter.notifyDataSetChanged();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, DisplayQuestion.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                questionList.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    public void clearEditText(){
        questionEdit.getText().clear();
        a1Edit.getText().clear();
        a2Edit.getText().clear();
        a3Edit.getText().clear();
        a4Edit.getText().clear();
    }

}
