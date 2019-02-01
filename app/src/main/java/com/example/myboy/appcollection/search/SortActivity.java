package com.example.myboy.appcollection.search;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.search.utils.ArraysToString;
import com.example.myboy.appcollection.search.weigets.ListDialog;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SortActivity extends AppCompatActivity implements View.OnClickListener {

    TextView heap;
    TextView insertion;
    TextView merge;
    TextView quick;
    TextView shell;
    TextView straight;

    TextView showSort;
    TextView sortType;
    EditText content;
    ArrayList<String> types;

    boolean haveContent;

    boolean first = true; //第一次只有点击输入框才会弹出选择框  之后只能点击最下方的类型才能显示
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        content = this.findViewById(R.id.ed_content);
        heap = this.findViewById(R.id.sort_heap);
        insertion = this.findViewById(R.id.sort_insertion);
        merge = this.findViewById(R.id.sort_merge);
        quick = this.findViewById(R.id.sort_quick);
        shell = this.findViewById(R.id.sort_shell);
        straight = this.findViewById(R.id.sort_straight);

        showSort = this.findViewById(R.id.show_sort);
        sortType = this.findViewById(R.id.sort_type);

        heap.setOnClickListener(this);
        insertion.setOnClickListener(this);
        merge.setOnClickListener(this);
        quick.setOnClickListener(this);
        shell.setOnClickListener(this);
        straight.setOnClickListener(this);
        sortType.setOnClickListener(this);
        content.setOnClickListener(this);

        initData();
    }

    /**
     * 展示选择弹窗
     */
    private void showSelectDialog() {
        final ListDialog dialog = new ListDialog(this, R.style.MyMiddleDialogStyle,"选择你要输入数据的类型",types);
        dialog.setItemClickListener(new ListDialog.ItemClickListener() {
            @Override
            public void click(int position) {
                sortType.setText(types.get(position));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initData() {
        types = new ArrayList<>();
        types.add("Integer");
        types.add("Double");
        types.add("Character");
        types.add("String");
    }

    @Override
    public void onClick(View view) {
        String[] info = null;
        if(view.getId()!=R.id.ed_content&&view.getId()!=R.id.sort_type){
            String sortContent = content.getText().toString();
            info = sortContent.split(",");
        }
        switch (view.getId()){
            case R.id.ed_content:
                if(first){
                    first = !first;
                    showSelectDialog();
                }
                break;
            case R.id.sort_type:
                if(!first){
                    showSelectDialog();
                }
                break;
        }
        if(info!=null&&info.length>=2){ //如果内容不为空则开始处理   这里默认按照String处理
            String result = "";
            switch (view.getId()){
                case R.id.sort_heap: //小顶堆 排序
                    String[] info1 = new String[info.length+1];
                    System.arraycopy(info,0,info1,1,info.length);
                    HeapSort<String> heapSort = new HeapSort<>();
                    info1[0] = "";
                    result = ArraysToString.ListToString(heapSort.sort(info1,info.length));
                    break;
                case R.id.sort_insertion: //直接插入排序
                    InsertionSort<String> insertionSort = new InsertionSort<>();
                    insertionSort.sort(info);
                    result = ArraysToString.arraysToString(info);
                    break;
                case R.id.sort_merge: //归并排序
                    MergeSort<String> mergeSort = new MergeSort<>();
                    mergeSort.sort(info,info.length);
                    result = ArraysToString.arraysToString(info);
                    break;
                case R.id.sort_quick: //快速排序
                    QuickSort<String> quickSort = new QuickSort<>();
                    quickSort.quick(info,0,info.length-1);
                    result = ArraysToString.arraysToString(info);
                    break;
                case R.id.sort_shell: //希尔排序
                    ShellSort<String> shellSort = new ShellSort<>();
                    shellSort.sort(info);
                    result = ArraysToString.arraysToString(info);
                    break;
                case R.id.sort_straight: //简单选择排序
                    StraightSelectSort<String> straightSelectSort = new StraightSelectSort();
                    straightSelectSort.sort(info);
                    result = ArraysToString.arraysToString(info);
                    break;
            }
            Toast.makeText(SortActivity.this,result,Toast.LENGTH_LONG).show();
        }
    }

}
