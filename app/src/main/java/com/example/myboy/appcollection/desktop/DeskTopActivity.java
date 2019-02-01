package com.example.myboy.appcollection.desktop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.desktop.animator.PointEvaluator;
import com.example.myboy.appcollection.desktop.base.BaseActivity;
import com.example.myboy.appcollection.desktop.bean.MenuItem;
import com.example.myboy.appcollection.desktop.bean.Point;
import com.example.myboy.appcollection.desktop.presenter.DeskTopPresenter;
import com.example.myboy.appcollection.desktop.presenter.Presenter;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DeskTopActivity extends BaseActivity implements View.OnClickListener,Presenter.View {

    TextView menu;
    RelativeLayout root;
    RelativeLayout circle;
    ArrayList<MenuItem> menuItems = new ArrayList<>();
    ArrayList<TextView> viewItems = new ArrayList<>();
    DeskTopPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = new DeskTopPresenter(this);
        setPresenter(presenter);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initView() {
        menu = this.findViewById(R.id.menu);
        root = this.findViewById(R.id.root);
        circle = this.findViewById(R.id.circle_bg);

        menu.setOnClickListener(this);

        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                root.getBackground().setAlpha(150);
            }
        });
    }

    @Override
    protected int getLayoutResourcesId() {
        return R.layout.activity_desktop;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        presenter.queryMenuItems();
    }


    @Override
    public void initViews() {
        for(MenuItem menuItem:menuItems){
            TextView item = new TextView(this);
            item.setGravity(Gravity.CENTER);
            item.setPadding(0,0,0,dip2px(6));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dip2px(80),dip2px(80));
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            item.setLayoutParams(params);
            item.setBackgroundResource(R.drawable.menu_background);
            item.setTag(menuItem);
            item.setText(menuItem.getTitle());
            item.setVisibility(View.INVISIBLE);
            root.addView(item);
            item.setOnClickListener(this);
            viewItems.add(item);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.menu){
            Toast.makeText(this,"点击菜单",Toast.LENGTH_LONG).show();
            presenter.setButtonState(!presenter.getButtonState());
        }else{

        }
    }

    @Override
    public void setItemVisibility(int state){
        for(View view:viewItems){
            view.setVisibility(state);
        }
        circle.setVisibility(state);
    }

    /**
     * 打开菜单动画
     */
    @Override
    public void startAnimator(){
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        AnimatorSet.Builder builder = null;
        boolean first = true;
        int size = viewItems.size();
        double corner = 360 / size;
        for(final TextView view:viewItems){
            MenuItem item = (MenuItem) view.getTag();
            PointEvaluator evaluator = new PointEvaluator();
            int center_y = view.getTop()+view.getHeight()/2;
            final int center_x = view.getLeft() + view.getWidth()/2;

            Point start = new Point(center_x,center_y);
            Point end = new Point(center_x+getAddForWidth(item.getId()*corner),center_y+getAddForHeight(item.getId()*corner));
            ValueAnimator animator = ValueAnimator.ofObject(evaluator,start,end);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Point point = (Point) valueAnimator.getAnimatedValue();
                    view.layout((int)(point.getX()-view.getWidth()/2),(int)(point.getY()-view.getHeight()/2),(int)(point.getX()+view.getWidth()/2),(int)(point.getY()+view.getHeight()/2));
                }
            });
            animator.setTarget(view);
            if(first){
                first = !first;
                builder = animatorSet.play(animator);
            }else{
                builder = builder.with(animator);
            }
        }
        animatorSet.start();
    }

    /**
     * 关闭菜单动画
     */
    @Override
    public void endAnimator(Animator.AnimatorListener listener) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(listener);

    }

    private float getAddForWidth(double corner){
        float width = (float) Math.sin(corner/180*Math.PI)*(int) (menu.getWidth()*0.8);
        return width;
    }

    private float getAddForHeight(double corner){
        float heigh = -(float) Math.cos(corner/180*Math.PI)*(int) (menu.getHeight()*0.8);
        return heigh;
    }

    /**
     * 设置好菜单项数据
     * @param menuItems
     */
    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * dp转px
     * @param dp
     * @return
     */
    public int dip2px(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5);
    }

}
