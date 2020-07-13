package com.bytedance.todolist.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListItemHolder> {
    private List<TodoListEntity> mDatas;
    private ItemOnClickCheckListener itemOnClickCheckListener;
    private String TAG = "ejrnejhncjsncjhdfn";

    public TodoListAdapter() {
        mDatas = new ArrayList<>();
    }
    @NonNull
    @Override
    public TodoListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoListItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_layout, parent, false));
    }

    public void removeData(int position) {
        if (null != mDatas && mDatas.size() > position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
            if (position != mDatas.size()) {
                notifyItemRangeChanged(position, mDatas.size() - position);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final TodoListItemHolder holder, final int position) {
        holder.bind(mDatas.get(position));
        holder.setCheckBoxListener(new CheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.setUI(isChecked);
                if(itemOnClickCheckListener != null){
                    itemOnClickCheckListener.onCheckedChanged(mDatas.get(position).getId(),isChecked);
                }
            }
        });
        holder.setImageBtnListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(itemOnClickCheckListener != null){
                    itemOnClickCheckListener.onClickButton(mDatas.get(position).getId());
                }
                removeData(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @MainThread
    public void setData(List<TodoListEntity> list) {
        mDatas = list;
        notifyDataSetChanged();
    }

    void setItemOnClickCheckListener(ItemOnClickCheckListener listener){
        itemOnClickCheckListener = listener;
    }

    public interface ItemOnClickCheckListener{
        void onCheckedChanged(final Long ID, final boolean isChecked);
        void onClickButton(final Long ID);
    }
}
