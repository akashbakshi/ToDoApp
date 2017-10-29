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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView reminderListView;
    Button completeTask,addReminder;
    EditText reminderField;
    ArrayList<String> reminderEvents = new ArrayList<String>();
    ArrayAdapter<String> reminderAdapter;
    int selectedNum = 0;
    boolean reminderSelected = false;

    Color selectedBackgroundCol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reminderField = (EditText)findViewById(R.id.reminderField);
        reminderField.setVisibility(View.INVISIBLE);
        reminderField.setEnabled(false);

        completeTask = (Button)findViewById(R.id.btnComplete);
        addReminder = (Button)findViewById(R.id.btnAddReminder);
        reminderListView = (ListView)findViewById(R.id.reminderList);
        reminderEvents.add("You either remembered to do everything or forgot to put it here!");

        reminderAdapter = new ArrayAdapter<String>(this,R.layout.reminder_layout,R.id.reminder_item,reminderEvents);
        reminderListView.setAdapter(reminderAdapter);

        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view.setSelected(true);
                completeTask.setVisibility(View.VISIBLE);
                openCompleteBtnAnimation(false,true);
                reminderSelected = true;
                addReminder.setText("x");
            }
        });

    }

    public void openCompleteBtnAnimation(boolean height, boolean width){


        completeTask.setVisibility(View.VISIBLE);
        if(height) {
            float transAmnt = -(addReminder.getHeight() + 20.0f);
            completeTask.animate().translationY(transAmnt).setDuration(200).setListener(null).start();
        }
        else if (width){

            float transAmnt = -(addReminder.getWidth()+20.0f);
            completeTask.animate().translationX(transAmnt).setDuration(200).setListener(null).start();
        }
    }

    public void closeCompleteBtnAnimation(boolean height, boolean width){
        //Hide edit text
        reminderField.setEnabled(false);
        //change back to normal plus

        if(height) {
            completeTask.animate().translationY(0.0f).setDuration(350).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (reminderField.getVisibility() == View.VISIBLE) {
                        completeTask.setVisibility(View.INVISIBLE);
                        addReminder.setText("+");
                        reminderField.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        else if (width){

            completeTask.animate().translationX(0.0f).setDuration(350).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (reminderSelected) {
                        completeTask.setVisibility(View.INVISIBLE);
                        addReminder.setText("+");
                        reminderField.setVisibility(View.INVISIBLE);
                        reminderListView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        reminderListView.setBackgroundColor(Color.TRANSPARENT);
                        final TextView tv = (TextView)findViewById(R.id.reminder_item);
                        tv.setBackgroundColor(Color.alpha(0));
                        reminderSelected = false;
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
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
                closeCompleteBtnAnimation(true,false);
            }
        }
    }

    public void onAddClick(View view){
        selectedNum++;
        if(selectedNum == 1 && reminderSelected == false) {
            openCompleteBtnAnimation(true,false);
            //Enable edit text
            reminderField.setEnabled(true);
            reminderField.setText("");
            reminderField.setVisibility(View.VISIBLE);

            //change to X
            addReminder.setText("x");
        }
        else if (selectedNum > 1 && reminderSelected == false){
            closeCompleteBtnAnimation(true,false);

            selectedNum = 0;
        }

        if(reminderSelected == true && selectedNum == 1){
            closeCompleteBtnAnimation(false,true);
            selectedNum = 0;
        }
    }
}

