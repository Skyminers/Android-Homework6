package com.bytedance.todolist.activity;

import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;

import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.bytedance.todolist.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;

public class TodoListActivity extends AppCompatActivity implements TodoListAdapter.ItemOnClickCheckListener{

    private TodoListAdapter mAdapter;
    private FloatingActionButton mFab;
    private String TAG = "TodoListActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TodoListAdapter();
        mAdapter.setItemOnClickCheckListener(this);
        recyclerView.setAdapter(mAdapter);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Intent intent = new Intent(TodoListActivity.this,InputTodoActivity.class);
                startActivityForResult(intent,1);
            }
        });

        mFab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                        dao.deleteAll();
                        for (int i = 0; i < 20; ++i) {
                            dao.addTodo(new TodoListEntity("This is " + i + " item", new Date(System.currentTimeMillis())));
                        }
                        Snackbar.make(mFab, R.string.hint_insert_complete, Snackbar.LENGTH_SHORT).show();
                    }
                }.start();
                return true;
            }
        });
        loadFromDatabase();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String content = data.getStringExtra("content");
        Date date = (Date)data.getSerializableExtra("date");
        Log.d(TAG,"insert content : " + content + " date : " + date);
        TodoListEntity entity = new TodoListEntity(content,date);
        mAdapter.addData(entity);
    }

    private void loadFromDatabase() {
        new Thread() {
            @Override
            public void run() {
                TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                final List<TodoListEntity> entityList = dao.loadAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(entityList);
                    }
                });
            }
        }.start();
    }

    @Override
    public void onCheckedChanged(final Long ID, final boolean isChecked) {
        new Thread(){
            @Override
            public void run() {
                TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                Log.d(TAG,"Search with ID : " + ID);
                TodoListEntity entity = dao.findById(ID);
                entity.setFlag(isChecked);
                dao.updateTodo(entity);

            }
        }.start();
    }

    @Override
    public void onClickButton(final Long ID) {
        new Thread(){
            @Override
            public void run() {
                TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                Log.d(TAG,"Delete ID : " + ID);
                dao.deleteTodo(ID);
            }
        }.start();
    }
}
