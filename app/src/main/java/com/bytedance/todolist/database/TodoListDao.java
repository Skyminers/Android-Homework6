package com.bytedance.todolist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
@Dao
public interface TodoListDao {
    @Query("SELECT * FROM todo")
    List<TodoListEntity> loadAll();

    @Insert
    long addTodo(TodoListEntity entity);

    @Update
    void updateTodo(TodoListEntity entity);

    @Query("DELETE FROM todo WHERE id = :ID")
    void deleteTodo(Long ID);

    @Query("SELECT * FROM todo WHERE id = :ID")
    TodoListEntity findById(Long ID);

    @Query("DELETE FROM todo")
    void deleteAll();
}
