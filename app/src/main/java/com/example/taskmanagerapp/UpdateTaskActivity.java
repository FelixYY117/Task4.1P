package com.example.taskmanagerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateTaskActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    //private Button buttonSelectDate;
    private Task task;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        editTextTitle = findViewById(R.id.editTextTitle2);
        editTextDescription = findViewById(R.id.editTextDescription2);
       // buttonSelectDate = findViewById(R.id.buttonSelectDate);

        // 获取传递过来的任务信息
        task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            editTextTitle.setText(task.getTitle());
            editTextDescription.setText(task.getDescription());
        }

        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新任务信息并返回结果
                String updatedTitle = editTextTitle.getText().toString().trim();
                String updatedDescription = editTextDescription.getText().toString().trim();

                if (!updatedTitle.isEmpty()) {
                    task.setTitle(updatedTitle);
                    task.setDescription(updatedDescription);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedTask", task);
                    setResult(RESULT_OK, resultIntent);
                }

                finish();
            }
        });
    }
}
