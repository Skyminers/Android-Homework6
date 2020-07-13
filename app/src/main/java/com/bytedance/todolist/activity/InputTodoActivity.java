package com.bytedance.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.DateConverter;
import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;

import java.util.Date;

public class InputTodoActivity extends AppCompatActivity {

    private EditText editText;
    private Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_edit_activity);
        editText = findViewById(R.id.text);
        submit = findViewById(R.id.submit);
        setResult(1);
        submit.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String content = editText.getText().toString();
                final Date date = new Date(System.currentTimeMillis());
                Intent intent = new Intent();
                intent.putExtra("content",content);
                intent.putExtra("date",date);
                setResult(0,intent);
                finish();
            }
        });
    }
}
