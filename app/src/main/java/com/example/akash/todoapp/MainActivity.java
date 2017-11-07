package com.example.akash.todoapp;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView reminderListView;
    TextView listviewItem;
    Button completeTask,addReminder;
    EditText reminderField;
    Switch actionSwitch;

    ArrayList<String> reminderEvents = new ArrayList<String>();
    ArrayAdapter<String> reminderAdapter;
    int selectedNum = 0;
    int cellSelected = -1;
    boolean reminderSelected = false;

    Color selectedBackgroundCol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        actionSwitch = (Switch)findViewById(R.id.delete_completeSwitch);
        listviewItem = (TextView)findViewById(R.id.reminder_item);
        reminderField = (EditText)findViewById(R.id.reminderField);

        completeTask = (Button)findViewById(R.id.btnComplete);
        addReminder = (Button)findViewById(R.id.btnAddReminder);
        reminderListView = (ListView)findViewById(R.id.reminderList);
        reminderEvents.add("You either remembered to do everything or forgot to put it here!");

        reminderAdapter = new ArrayAdapter<String>(this,R.layout.reminder_layout,R.id.reminder_item,reminderEvents);
        reminderListView.setAdapter(reminderAdapter);

        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(cellSelected > -1)
                    clearCellSelection();

                view.setBackgroundColor(getResources().getColor(R.color.colorCellSelDark));
                view.setSelected(true);
                completeTask.setVisibility(View.VISIBLE);
                reminderSelected = true;
                addReminder.setText("x");
                cellSelected = position;

            }
        });

        actionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }



    public void onCompleteAdd(View view){
        //Toast.makeText(getApplicationContext(),reminderField.getText().toString(),Toast.LENGTH_SHORT).show();
        if(selectedNum == 1){
            if(!reminderField.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(),"added",Toast.LENGTH_SHORT).show();
                if(reminderEvents.get(0).toString() == "You either remembered to do everything or forgot to put it here!")
                    reminderEvents.remove(0);
                reminderEvents.add(reminderField.getText().toString());
                Collections.reverse(reminderEvents);
                reminderAdapter.notifyDataSetChanged();
            }
        }
    }
    public void clearCellSelection(){

        reminderListView.setBackgroundColor(Color.TRANSPARENT);
        reminderListView.getChildAt(cellSelected).setBackgroundColor(Color.TRANSPARENT);
    }
    public void onAddClick(View view){
        selectedNum++;

    }
}

