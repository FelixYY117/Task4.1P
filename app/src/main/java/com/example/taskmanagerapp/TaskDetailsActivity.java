package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import androidx.annotation.Nullable;

public class TaskDetailsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_UPDATE_TASK = 1;
    private TextView textViewTitle, textViewDescription, textViewDueDate;
    private Calendar dueDate;
    private TaskManagerDB taskManagerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details_activity);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewDueDate = findViewById(R.id.textViewDueDate);
        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);


        Task task = (Task) getIntent().getSerializableExtra("task");
        //taskManagerDB = TaskManagerDB.getInstance(this);
        if (task != null) {
            textViewTitle.setText(String.format("Title: %s", task.getTitle()));
            textViewDescription.setText(String.format("Description: %s", task.getDescription()));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            textViewDueDate.setText(String.format("DueDate: %s", dateFormat.format(task.getDueDate())));
        }
       // ----------------------------------------
        taskManagerDB = TaskManagerDB.getInstance(this);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到UpdateTaskActivity进行更新
                Intent intent = new Intent(TaskDetailsActivity.this, UpdateTaskActivity.class);
                intent.putExtra("task", task);
                startActivityForResult(intent, 1);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskManagerDB.deleteTask(task);
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Get updated task from UpdateTaskActivity
            Task updatedTask = (Task) data.getSerializableExtra("updatedTask");
            if (updatedTask != null) {
                // Update task in database
                taskManagerDB.updateTask(updatedTask);
                // Finish the activity
                finish();
            }
        }
    }
}



//    public void delete(View view){
//        Task task = (Task) getIntent().getSerializableExtra("task");
//        if (task != null) {
//            taskManagerDB = TaskManagerDB.getInstance(this);
//            taskManagerDB.deleteTask(task);
//            Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
//            finish();
//        } else {
//            Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//    public  void update(View view) {
//        Task task = (Task) getIntent().getSerializableExtra("task");
//        if (task != null) {
//            Intent intent = new Intent(this, CreateTaskActivity.class);
//            intent.putExtra("taskToUpdate", task);
//            startActivity(intent);
//            taskManagerDB = TaskManagerDB.getInstance(this);
//            //  taskManagerDB.updateTask(task);
//            taskManagerDB.deleteTask(task);
//
//        } else {
//            Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
    //-------------------------------------------------------
//public void update(View view) {
//    Task task = (Task) getIntent().getSerializableExtra("task");
//    if (task != null) {
//        // 获取当前任务信息
//        String currentTitle = task.getTitle();
//        String currentDescription = task.getDescription();
//        Date currentDueDate = task.getDueDate();
//
//        // 启动 CreateTaskActivity 并传递当前任务信息
//        Intent intent = new Intent(this, CreateTaskActivity.class);
//        intent.putExtra("taskToUpdate", task);
//        startActivityForResult(intent, REQUEST_CODE_UPDATE_TASK);
//    } else {
//        Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
//    }
//}


