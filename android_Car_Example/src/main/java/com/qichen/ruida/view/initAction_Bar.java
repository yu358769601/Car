package com.qichen.ruida.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qichen.ruida.R;

import static com.qichen.ruida.R.id.relativeLayout_title_leftbutton;
import static com.qichen.ruida.R.id.relativeLayout_title_rightbutton;


/**
 * Created by 35876 于萌萌
 * 创建日期: 15:50 . 2016年10月03日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */
public class initAction_Bar extends RelativeLayout implements View.OnClickListener {
    public static final int AUTO_ONCLICK_MODE = 0;
    public static final int MANUAL_ONCLICK_MODE = 1;

    public int mode;
    Context context;
    private RelativeLayout mAction_bar;
    private Button mAction_bar_tv_back;
    private TextView mAction_bar_title_name;
    private ImageView action_bar_ib_back;
    private ImageView action_bar_more;
    Action_bar_call_back mActionBarCallBack;
    private RelativeLayout mBase_action_bar;
    private Button mRelativeLayout_title_rightbutton;

    public initAction_Bar(Context context) {
        super(context);
        initView(context);
    }


    public initAction_Bar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public initAction_Bar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


//    public initAction_Bar(Activity activity) {
//        this.activity = activity;
//    }

//    public  initAction_Bar(Activity activity){
//        this.activity = activity;
//
//    }
//
//    /**
//     * 通过构造方法 获得标题对象
//     * @param activity 上下文
//     * @param mode 点击事件的模式 自动 手动
//     * @param action_bar_call_back 回调 主要内容有 这个雷 里面的 能找到的 控件
//     *
//     */
//    public  initAction_Bar(Activity activity, int mode,Action_bar_call_back action_bar_call_back ){
//        this.activity = activity;
//        setCallBack(mode,action_bar_call_back);
//
//    }

    public interface Action_bar_call_back {
        void getAction_barView_backbutton(Button button);

        //void getAction_barView_backbutton_icon(ImageView imageView);

        void getAction_barView_title(TextView textView);

        //void getAction_barView_right_icon(ImageView imageView);
    }

    private initAction_Bar initView(Context context) {
        this.context = context;
        mBase_action_bar = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.activity_title, this);


        mAction_bar_tv_back = (Button) mBase_action_bar.findViewById(relativeLayout_title_leftbutton);
        mRelativeLayout_title_rightbutton = (Button) mBase_action_bar.findViewById(relativeLayout_title_rightbutton);
        mAction_bar_title_name = (TextView) mBase_action_bar.findViewById(R.id.relativeLayout_title_titleview);
       // action_bar_ib_back = (ImageView) mBase_action_bar.findViewById(R.id.action_bar_iv_back);
       // action_bar_more = (ImageView) mBase_action_bar.findViewById(R.id.action_bar_more);
        mAction_bar_title_name.setText("展示标题");
        //mAction_bar_tv_back.setText("返回");
        mAction_bar_tv_back.setVisibility(View.VISIBLE);
        mAction_bar_tv_back.setBackgroundResource(R.drawable.title_leftbutton);
       // action_bar_ib_back.setVisibility(View.VISIBLE);

        return this;
    }



    public void setCallBack(Action_bar_call_back action_bar_call_back){



        if (mode==AUTO_ONCLICK_MODE){
           // action_bar_ib_back.setOnClickListener(this);
            mAction_bar_title_name.setOnClickListener(this);
            mAction_bar_tv_back.setOnClickListener(this);
        } else if (mode==MANUAL_ONCLICK_MODE){

        }


        action_bar_call_back.getAction_barView_backbutton(mAction_bar_tv_back);
        //action_bar_call_back.getAction_barView_backbutton_icon(action_bar_ib_back);
        //action_bar_call_back.getAction_barView_right_icon(action_bar_more);
        action_bar_call_back.getAction_barView_title(mAction_bar_title_name);
        }

    public initAction_Bar setMode(int mode){
        this.mode = mode;
        return this;
    }
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == relativeLayout_title_leftbutton ) {
           // Utils.finish((Activity) context);
            Activity context = (Activity) this.context;
            context.finish();
        }
    }
    }
