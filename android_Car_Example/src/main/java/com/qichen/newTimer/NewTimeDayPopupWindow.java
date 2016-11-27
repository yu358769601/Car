package com.qichen.newTimer;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qichen.ruida.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class NewTimeDayPopupWindow extends PopupWindow {

    private View mMenuView;
    private TextView cancletv, cleartv, suretv;
    private Activity mContext;

    private CountryListView listView1;
    private CountryListView subListView;
    private LinearLayout travelLayout;
    private TimeDayAdapter myAdapter;
    private NewTimeDaySubAdapter subAdapter;

    private RelativeLayout startLayout;
    private RelativeLayout endLayout;
    private TextView startDate;
    private TextView endDate;
    private Handler mHandler;

    private String startStr, endStr;
    public ArrayList<String> selectid = new ArrayList<String>();

    private String initStartDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    private String initEndDateTime = "2014年8月23日 17:44"; // 初始化结束时间

    String cities[] = new String[]{"全选", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10"};

    String foods[] = new String[]{"行程天数", "出游时间"};
    private CheckBox quanxuan;
    private DateCompare mDateCompare = new DateCompare();

    public NewTimeDayPopupWindow(Activity context, ArrayList<String> mDayList,
                                 Handler handler) {
        super(context);
        mContext = context;
        if (mDayList.contains("全选")) {
            mDayList.remove("全选");
        }

        if (mDayList == null || mDayList.size() == 0) {
            selectid.add("全选");
            quanxuan = new CheckBox(mContext);
        } else {
            quanxuan = null;
            selectid = mDayList;
        }
        mHandler = handler;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_newtimeday, null);
        cancletv = (TextView) mMenuView.findViewById(R.id.canceltv);//取消
        cleartv = (TextView) mMenuView.findViewById(R.id.cleartv);//出发城市
        suretv = (TextView) mMenuView.findViewById(R.id.suretv);//确认

        startLayout = (RelativeLayout) mMenuView.findViewById(R.id.startLayout);
        endLayout = (RelativeLayout) mMenuView.findViewById(R.id.endLayout);

        startDate = (TextView) mMenuView.findViewById(R.id.startDate);//最早出发
        endDate = (TextView) mMenuView.findViewById(R.id.endDate);//最晚出发
        //清空选择记录
        mMenuView.findViewById(R.id.clearSelectTv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                PreferenceUtil.setPreString("earliesTime",startStr); //保存数据
//                PreferenceUtil.setPreString("latestTime",endStr);
                startStr = "";
                endStr = "";
                startDate.setText("最早出发");
                endDate.setText("最晚出发");
            }
        });

//        if (!"".equals(PreferenceUtil.getPreString("earliesTime"))) {
//            startDate.setText(PreferenceUtil.getPreString("earliesTime"));
//        }
//        if (!"".equals(PreferenceUtil.getPreString("latestTime"))) {
//            endDate.setText(PreferenceUtil.getPreString("latestTime"));
//        }
            startDate.setText("最早");
            endDate.setText("最晚");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        initEndDateTime = sdf.format(Calendar.getInstance().getTime());

        startLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                DatePickDialogUtil dateTimePicKDialog = new DatePickDialogUtil(
                        mContext, initEndDateTime,1);
                dateTimePicKDialog.dateTimePicKDialog(startDate);
            }
        });

        endLayout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                DatePickDialogUtil dateTimePicKDialog = new DatePickDialogUtil(
                        mContext, initEndDateTime,1);
                dateTimePicKDialog.dateTimePicKDialog(endDate);
            }
        });

        travelLayout = (LinearLayout) mMenuView.findViewById(R.id.traveltime);

        // 取消按钮
        cancletv.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        // 设置按钮监听
        cleartv.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        suretv.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框 2016/09/16
                startStr = startDate.getText().toString();
                endStr = endDate.getText().toString();
                boolean dateAble = true;
                if(TextUtils.isEmpty(endStr)) {
                    //为空 //不走日期判断
                }else {
                    //不为空
                    if("最晚出发".equals(endStr)) {
                        //是最晚出发，不走日期判断
                    }else {
                        //不是最晚出发，走日期判断
                        String tempStartStr = startStr.replace("/", "-");
                        String tempEndStr = endStr.replace("/", "-");
                        dateAble = mDateCompare.compareDate(tempStartStr, tempEndStr);
                    }
                }

                if (dateAble) {
                    if (startStr.equals("最早出发")) {
                        startStr = "";
                    }
                    if (endStr.equals("最晚出发")) {
                        endStr = "";
                    }
                    //日期正确，可以关闭dialog;
                    PreferenceUtil.setPreString("earliesTime", startStr); //保存数据
                    PreferenceUtil.setPreString("latestTime", endStr);

                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("startDate", startStr);
                    data.putString("endDate", endStr);
                    data.putStringArrayList("daysList", selectid);
                    msg.setData(data);
                    msg.what = 600;
                    mHandler.sendMessage(msg);
                    dismiss();
                } else {
                    Toast.makeText(mContext, "最晚出发日期请在最早之后", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
       // this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        //						dismiss();
                    }
                }
                return true;
            }
        });

        listView1 = (CountryListView) mMenuView.findViewById(R.id.listView);
        subListView = (CountryListView) mMenuView.findViewById(R.id.subListView);

        myAdapter = new TimeDayAdapter(context, foods);
        listView1.setAdapter(myAdapter);
        myAdapter.setSelectedPosition(0);
        myAdapter.notifyDataSetInvalidated();
        subAdapter = new NewTimeDaySubAdapter(mContext, cities, 0);
        subListView.setAdapter(subAdapter);

        listView1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                myAdapter.setSelectedPosition(position);
                myAdapter.notifyDataSetInvalidated();
                if (position == 0) {
                    subListView.setVisibility(View.VISIBLE);
                    travelLayout.setVisibility(View.GONE);
                    //                  subAdapter.notifyDataSetInvalidated();
                } else {
                    subListView.setVisibility(View.GONE);
                    travelLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    class NewTimeDaySubAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater = null;
        private HashMap<Integer, View> mView;
        public HashMap<Integer, Integer> visiblecheck;// 用来记录是否显示checkBox
        public HashMap<Integer, Boolean> ischeck;
        public String[] mDays;
        public int mPosition;

        public NewTimeDaySubAdapter(Context context, String[] days, int position) {
            this.context = context;
            mDays = days;
            this.mPosition = position;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = new HashMap<Integer, View>();
            visiblecheck = new HashMap<Integer, Integer>();
            ischeck = new HashMap<Integer, Boolean>();

            for (int i = 0; i < mDays.length; i++) {
                ischeck.put(i, false);
                visiblecheck.put(i, CheckBox.VISIBLE);
            }
        }

        public int getCount() {
            return mDays.length;
        }

        public Object getItem(int position) {
            return mDays[position];
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            View view = mView.get(position);
            String days = mDays[position];
            view = inflater.inflate(R.layout.tripdaysmultilistitem, null);
            TextView txt = (TextView) view.findViewById(R.id.txtName);
            TextView dayTv = (TextView) view.findViewById(R.id.dayTv);
            final CheckBox ceb = (CheckBox) view.findViewById(R.id.check);

            txt.setText(mDays[position]);

            if (selectid.contains(days)) {
                ceb.setChecked(true);
            }
            if (quanxuan != null && position != 0) {
                //如果选中了全选
                ceb.setChecked(false);
            }
            if (quanxuan == null && position == 0) {
                ceb.setChecked(false);
            }
            if (position == 0) {
                dayTv.setVisibility(View.INVISIBLE); //全选后面不显示天字
            }
            ceb.setVisibility(View.VISIBLE);
            if (position != 0) {
                view.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        if (ceb.isChecked()) {
                            ceb.setChecked(false);
                            selectid.remove(mDays[position]);
                        } else {
                            ceb.setChecked(true);
                            if (quanxuan != null) {
                                quanxuan.setChecked(false);
                                quanxuan = null;
                                selectid.remove("全选");
                            }
                            selectid.add(mDays[position]);
                        }
                        NewTimeDaySubAdapter.this.notifyDataSetChanged();
                    }
                });
            } else {
                //全选
                if (quanxuan != null) {
                    //全选已选中
                    quanxuan = ceb;
                }
                //全选没有被选中
                view.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        if (quanxuan == null) {
                            //点击全选
                            quanxuan = ceb;
                            quanxuan.setChecked(true);
                            selectid.clear();
                            selectid.add("全选");
                            NewTimeDaySubAdapter.this.notifyDataSetChanged();
                        }
                    }
                });
            }
            mView.put(position, view);
            //            }
            return view;
        }

    }
}
