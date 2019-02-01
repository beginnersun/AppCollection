package com.example.myboy.appcollection.search.weigets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myboy.appcollection.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 这是一个用来显示列表的Dialog
 */
public class ListDialog extends Dialog {

    private Context context;
    private String title;
    private List<String> datas;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    /**
     * OnCancelListener 是指被显示的取消 比如按下back键 或者调用cancel函数取消 就会转入这里
     * 但是还有一个是onDismissListener 所有的只要隐藏掉都会进入这个方法  所以如果要区别对待是被显示隐藏了还是不需要区别对待 要分别考虑两种方法
     * @param context 上下文
     * @param themeResId 弹窗的style (要用这个设置默认背景为透明)
     * @param title 标题
     * @param datas 数据
     */
    public ListDialog(Context context, int themeResId,String title, List<String> datas) {
        super(context, themeResId);
        this.context = context;
        this.title = title;
        this.datas = datas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_list,null);
        setContentView(view);
        setCanceledOnTouchOutside(true); //设置点击外部可以取消显示


        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);

        dialogTitle.setText(title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter adapter = new MyAdapter(R.layout.recyclerview_item_dialog_select_type,datas,context);
        recyclerView.setAdapter(adapter);

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private int resId;
        private List<String> data;
        private Context context;

        public MyAdapter(int resId, List<String> data, Context context) {
            this.resId = resId;
            this.data = data;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(this.context).inflate(resId,parent,false));
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            holder.refresh(data.get(position));
            if(itemClickListener!=null){
                holder.setItemClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemClickListener.click(position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return data!=null?data.size():0;
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView type;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            type = itemView.findViewById(R.id.item_type);
        }
        public void refresh(String data){
            type.setText(data);
        }
        public void setItemClickListener(View.OnClickListener onClickListener){
            itemView.setOnClickListener(onClickListener);
        }
    }

    public interface ItemClickListener{
        void click(int position);
    }
}
