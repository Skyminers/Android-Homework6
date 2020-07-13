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
        Log.d(TAG,"remove pos : " + position + " size : " + mDatas.size());
        if (null != mDatas && mDatas.size() > position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
            if (position != mDatas.size()) {
                notifyItemRangeChanged(position, mDatas.size() - position);
            }
        }
        Log.d(TAG,"removed pos : " + position + " size : " + mDatas.size() + "in list");
    }

    @Override
    public void onBindViewHolder(@NonNull final TodoListItemHolder holder, final int position) {
        Log.d(TAG,"Bind " + position);
        holder.bind(mDatas.get(position));
        holder.setCheckBoxListener(new CheckBox.OnClickListener(){

            @Override
            public void onClick(View v) {
                TodoListEntity tmp = mDatas.get(position);
                Boolean isChecked = !tmp.getFlag();
                tmp.setFlag(isChecked);

                holder.setUI(isChecked);
                Log.d(TAG,"get pos : " + position + " size : " + mDatas.size());

                mDatas.set(position,tmp);
                if(itemOnClickCheckListener != null){
                    itemOnClickCheckListener.onClickCheckBox(mDatas.get(position).getId(),isChecked);
                }
            }
        });
        holder.setImageBtnListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(TAG,"pre-remove pos : " + position + " size : " + mDatas.size());
                if(itemOnClickCheckListener != null){
                    itemOnClickCheckListener.onClickButton(mDatas.get(position).getId());
                    Log.d(TAG,"removed pos : " + position + " size : " + mDatas.size() + " in DB");
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
        void onClickCheckBox(final Long ID, final boolean isChecked);
        void onClickButton(final Long ID);
    }
}
