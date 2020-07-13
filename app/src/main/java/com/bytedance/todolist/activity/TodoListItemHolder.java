package com.bytedance.todolist.activity;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListEntity;

import org.w3c.dom.Entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListItemHolder extends RecyclerView.ViewHolder {
    private TextView mContent;
    private TextView mTimestamp;
    private CheckBox checkBox;
    private ImageButton btn;
    private Long ID;

    public TodoListItemHolder(@NonNull View itemView) {
        super(itemView);
        mContent = itemView.findViewById(R.id.tv_content);
        mTimestamp = itemView.findViewById(R.id.tv_timestamp);
        checkBox = itemView.findViewById(R.id.checkBox);
        btn = itemView.findViewById(R.id.removeBtn);
    }

    public void setCheckBoxListener(CheckBox.OnCheckedChangeListener listener){
        checkBox.setOnCheckedChangeListener(listener);
    }

    public void setImageBtnListener(ImageButton.OnClickListener listener){
        btn.setOnClickListener(listener);
    }

    public void setUI(Boolean flag){
        if(flag){
            mContent.setPaintFlags(mContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mTimestamp.setPaintFlags(mTimestamp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            mContent.setPaintFlags(mContent.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            mTimestamp.setPaintFlags(mTimestamp.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public void bind(TodoListEntity entity) {
        ID = entity.getId();
        mContent.setText(entity.getContent());
        mTimestamp.setText(formatDate(entity.getTime()));
        checkBox.setChecked(entity.getFlag());
        setUI(entity.getFlag());
    }

    private String formatDate(Date date) {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }
}
