package com.example.taskmanagerapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerapp.R;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private Calendar dueDate;
    private TaskManagerDB taskManagerDB;
   // private static final int REQUEST_CODE_UPDATE_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);

        dueDate = Calendar.getInstance();

        taskManagerDB = TaskManagerDB.getInstance(this);
    }

    public void showDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datePicker, year, monthOfYear, dayOfMonth) -> {
                    dueDate.set(Calendar.YEAR, year);
                    dueDate.set(Calendar.MONTH, monthOfYear);
                    dueDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                },
                dueDate.get(Calendar.YEAR),
                dueDate.get(Calendar.MONTH),
                dueDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public void saveTask(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }

        Task task = new Task(title, description, dueDate.getTime());
        taskManagerDB.addTask(task);

        finish();
    }
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_UPDATE_TASK && resultCode == RESULT_OK) {
//            if (data != null) {
//                Task updatedTask = (Task) data.getSerializableExtra("updatedTask");
//                if (updatedTask != null) {
//                    taskManagerDB.updateTask(updatedTask);
//
//                    // 发送广播通知 MainActivity 刷新任务列表
//                    Intent intent = new Intent("com.example.taskmanagerapp.UPDATE_TASK_LIST");
//                    sendBroadcast(intent);
//
//                    // 返回上一个活动
//
//                }
//            }
//        }
//    }


}
