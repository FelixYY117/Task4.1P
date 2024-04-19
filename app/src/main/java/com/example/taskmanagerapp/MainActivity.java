package com.example.taskmanagerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView listViewTasks;
    private ArrayAdapter<Task> tasksAdapter;
    private List<Task> taskList;
    private TaskManagerDB taskManagerDB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTasks = findViewById(R.id.listViewTasks);
        taskList = new ArrayList<>();
        taskManagerDB = TaskManagerDB.getInstance(this);

        taskList = taskManagerDB.getAllTasksSortedByDate();

       // tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);

        tasksAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_2, android.R.id.text1, taskList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textViewTitle = view.findViewById(android.R.id.text1);
                TextView textViewDate = view.findViewById(android.R.id.text2);

                Task task = taskList.get(position);
                textViewTitle.setText(task.getTitle());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String dateString = dateFormat.format(task.getDueDate());
                textViewDate.setText(dateString);

                return view;
            }
        };


        //----------------------------------

        listViewTasks.setAdapter(tasksAdapter);

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = taskList.get(position);
                Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
                intent.putExtra("task", selectedTask);
                startActivity(intent);
            }
        });
    }

    public void fabAddTask(View view) {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskList.clear();
        taskList.addAll(taskManagerDB.getAllTasks());
        tasksAdapter.notifyDataSetChanged();
    }
}