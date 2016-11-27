package com.qichen.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qichen.ruida.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 35876 于萌萌
 * 创建日期: 14:47 . 2016年09月26日
 * 描述:
 * <p/>
 * <p/>
 * 备注:
 */
public class LineRadioGroup {
    //统计点击了多少的集合
    static ArrayList<RadioGroup> arrayList = new ArrayList<RadioGroup>();
    //统计点击了多少的集合
    static ArrayList<CheckBox[]> sCheckBoxes = new ArrayList<CheckBox[]>();
    //改变金钱数字的集合
    static ArrayList<TextView> arrayList1 = new ArrayList<TextView>();
    private static FeedbackOptionItem selectedOptionItem;
    private static LinearLayout llOptionsContainer;
    private static CheckBox[] artsList;

    public static linesFristCallBack mLinesFristCallBack;

    public static void setLinesFristCallBack(linesFristCallBack fristCallBack){
       mLinesFristCallBack =fristCallBack;
    }

    public interface CallBackItemNum {
        void callback(int pos, String title, int linespos, String num, String span);

    }

//    partsList = new CheckBox[]{     artsList = new CheckBox[];
    // artsList[i] = new CheckBox(context);
//        (CheckBox) findViewById(R.id.checkbox1),
//                (CheckBox) findViewById(R.id.checkbox2),
//                (CheckBox) findViewById(R.id.checkbox3),
//                (CheckBox) findViewById(R.id.checkbox4),
//        //(CheckBox) findViewById(R.id.checkbox5),
//    };


    public static ArrayList<RadioGroup> getArrayList0() {
        return arrayList;
    }

    public static ArrayList<CheckBox[]> getArrayList0box() {
        return sCheckBoxes;
    }

    public static ArrayList<TextView> getArrayList1() {
        return arrayList1;
    }

    public static void setmoveList() {
        arrayList1.clear();
        arrayList.clear();
        sCheckBoxes.clear();
    }



    public static void updateOptionsView(Context context, List<FeedbackOptionItem> optionsList, LinearLayout llOptionsContainer, final CallBackItemNum callBackItemNum) {
        LineRadioGroup.llOptionsContainer = llOptionsContainer;
        LineRadioGroup.llOptionsContainer.removeAllViews();
        int size = optionsList.size();
        final int radioCount = 3;
        int mod = size % radioCount;
        int radioGroupCount = 0 == mod ? size / radioCount : size / radioCount + 1;
        RadioGroup.LayoutParams groupParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        RadioGroup.LayoutParams radioParams = new RadioGroup.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT,1);

        RadioGroup radioGroup = null;
        for (int i = 0; i < radioGroupCount; i++) {
            if (i != radioGroupCount - 1) {
                //  groupParams.setMargins(0, 0, 0, 24);
            }
            radioGroup = new RadioGroup(context);

            radioGroup.setLayoutParams(groupParams);
            radioGroup.setOrientation(RadioGroup.HORIZONTAL);
            radioGroup.setWeightSum(radioCount);
            int dmins = R.dimen.size_base480_4dp;
            radioParams.setMargins(getDimens(context, dmins), getDimens(context,dmins), getDimens(context, dmins), getDimens(context,dmins));
            int radioMaxeSize = (i + 1) * radioCount;
            for (int j = i * radioCount; j < radioMaxeSize && j < size; j++) {
                FeedbackOptionItem optionItem = optionsList.get(j);
                LogUtils.i("对象是"+optionItem);
                optionItem.radioGroupIndex = i;
                RadioButton radio = new RadioButton(context);
                radio.setLayoutParams(radioParams);
                //如果是 空字符串 就填写个0 显示  和设置0  给这个对象的这个字段
                if (!TextUtils.isEmpty(optionItem.title)) {
                    LogUtils.i("设置标题为"+optionItem.title);
                    radio.setText(optionItem.title);
                    radio.setTextColor(context.getResources().getColor(R.color.black));
                    radio.setGravity(Gravity.CENTER);
                } else {
                    optionItem.title = "0";
                    radio.setText("无货");
                }

                radio.setTag(optionItem);

                radio.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
                radio.setBackgroundResource(R.drawable.btn_jinqian);
                // 设置图片与文本的间距，据说必须先setButtonDrawable，再调用
                int dimens = R.dimen.size_base480_20dp;
                radio.setPadding(getDimens(context, dimens), getDimens(context, dimens), getDimens(context, dimens), getDimens(context, dimens));

                radioGroup.addView(radio);


                //初始化默认 选中 丧心病狂 20000
                if (i==radioGroupCount-1&&j==size-1){
                    radio.setChecked(true);
                    mLinesFristCallBack.fristCallback(optionsList.get(j));
                }


            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                    if (null != radioButton && radioButton.isChecked()) {// 选中的item才会执行这个
                        selectedOptionItem = (FeedbackOptionItem) radioButton.getTag();
                       // FeedbackOptionItem selectedOptionItem = (FeedbackOptionItem) radioButton.getTag();
                        //  首次选中不需要重置各个radioGroup的状态
                            resetUnselectedRadioGroupStatus(selectedOptionItem.radioGroupIndex);
                        callBackItemNum.callback(Integer.parseInt(selectedOptionItem.tid), selectedOptionItem.title, selectedOptionItem.radioGroupIndex, selectedOptionItem.num, selectedOptionItem.span);

                    }
                }
            });

            llOptionsContainer.addView(radioGroup);
            radioGroup.setTag(i);
            arrayList.add(radioGroup);

        }

    }

//    //一行单选带取消
//    public static void updateOptionsViewcheBox(Context context, List<FeedbackOptionItem> optionsList, LinearLayout llOptionsContainer, final CallBackItemNum callBackItemNum) {
//        LineRadioGroup.llOptionsContainer = llOptionsContainer;
//        LineRadioGroup.llOptionsContainer.removeAllViews();
//        int size = optionsList.size();
//        final int radioCount = 4;
//        int mod = size % radioCount;
//        int radioGroupCount = 0 == mod ? size / radioCount : size / radioCount + 1;
//        LinearLayout.LayoutParams groupParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        LinearLayout.LayoutParams radioParams = new LinearLayout.LayoutParams(getDimens(context, R.dimen.lines_buttonwit),
//                getDimens(context, R.dimen.lines_buttonhei));
//
//        //RadioGroup radioGroup = null;
//        CheckBox[] checkBox = null;
//        LinearLayout linearLayout = null;
//        for (int i = 0; i < radioGroupCount; i++) {
//            if (i != radioGroupCount - 1) {
//                groupParams.setMargins(0, 0, 0, 0);
//            }
//
//            checkBox = new CheckBox[4];
//            linearLayout = new LinearLayout(context);
//            linearLayout.setLayoutParams(groupParams);
//            linearLayout.setOrientation(RadioGroup.HORIZONTAL);
//
//            // radioGroup.setWeightSum(radioCount);
//
//            radioParams.setMargins(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
//            int radioMaxeSize = (i + 1) * radioCount;
//            for (int j = i * radioCount; j < radioMaxeSize && j < size; j++) {
//                FeedbackOptionItem optionItem = optionsList.get(j);
//                optionItem.radioGroupIndex = i;
//                final CheckBox radio = new CheckBox(context);
//                //  radio.setLayoutParams(radioParams);
//                //如果是 空字符串 就填写个0 显示  和设置0  给这个对象的这个字段
//                if (!TextUtils.isEmpty(optionItem.title)) {
//
//                    radio.setText(optionItem.title);
//                } else {
//                    optionItem.title = "0";
//                    radio.setText("无货");
//                }
//                radio.setLayoutParams(radioParams);
//
//                radio.setTag(optionItem);
//                //radio.setPadding(getDimens(context,R.dimen.lines_pading),getDimens(context,R.dimen.lines_pading),getDimens(context,R.dimen.lines_pading),getDimens(context,R.dimen.lines_pading));
//                radio.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
//                radio.setBackgroundResource(R.drawable.feedback_radiobtn_style_selector);
//                // 设置图片与文本的间距，据说必须先setButtonDrawable，再调用
//                radio.setPadding(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
//                radio.setGravity(Gravity.CENTER);
//                // radioGroup.addView(radio);
//                //给checkBox 数组 添加数据
//                checkBox[Integer.parseInt(optionItem.span)] = radio;
//                final CheckBox[] finalCheckBox = checkBox;
//                radio.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        FeedbackOptionItem tag = (FeedbackOptionItem) v.getTag();
//                        //勾选状态
//                        if (radio.isChecked()) {
//
//                            LogUtils.i("单独按钮里面的数据" + tag.toString());
//                            LogUtils.i("要放进去的数组的内容是" + finalCheckBox);
//                            setBoxCheck(tag, finalCheckBox, callBackItemNum);
//                        }
//                        //取消状态
//                        else {
//
//
//                            for (int i = 0; i < finalCheckBox.length; i++) {
//                                CheckBox checkBox = finalCheckBox[i];
//                                tag = (FeedbackOptionItem) checkBox.getTag();
//                                if (tag.span.equals(tag.span)) {
//
//                                    callBackItemNum.callback(Integer.parseInt(tag.tid), tag.title, tag.radioGroupIndex, tag.num, tag.span);
//                                }
//                            }
//                            TextView textView = arrayList1.get(tag.radioGroupIndex);
//                            textView.setText("0");
//                        }
//                    }
//                });
//            }
//            for (int x = 0; x < checkBox.length; x++) {
//                CheckBox checkBox1 = checkBox[x];
//
//                linearLayout.addView(checkBox1);
//            }
//            llOptionsContainer.addView(linearLayout);
//
//            sCheckBoxes.add(checkBox);
////                radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
////                    @Override
////                    public void onCheckedChanged(RadioGroup group, int checkedId) {
////                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
////                        if (null != radioButton && radioButton.isChecked()) {// 选中的item才会执行这个
////                            selectedOptionItem = (FeedbackOptionItem) radioButton.getTag();
////                            FeedbackOptionItem selectedOptionItem = (FeedbackOptionItem) radioButton.getTag();
////                            callBackItemNum.callback(Integer.parseInt(selectedOptionItem.tid), selectedOptionItem.title,selectedOptionItem.radioGroupIndex,selectedOptionItem.num);
////
////                        }
////                    }
////                });
//
////            checkBox.setTag();
////            checkBox.
////            radioGroup.setTag(i);
////            arrayList.add(radioGroup);
//        }
//
//    }

    private static void setBoxCheck(FeedbackOptionItem checkedId, CheckBox[] checkBoxes, CallBackItemNum callBackItemNum) {
        for (int i = 0; i < checkBoxes.length; i++) {
            CheckBox checkBox = checkBoxes[i];
            checkBox.setChecked(false);
            FeedbackOptionItem tag = (FeedbackOptionItem) checkBox.getTag();
            if (checkedId.span.equals(tag.span)) {
                checkBox.setChecked(true);
                callBackItemNum.callback(Integer.parseInt(checkedId.tid), checkedId.title, checkedId.radioGroupIndex, checkedId.num, checkedId.span);
            }


            //  LogUtils.i("我传进来的id"+checkedId+"我取出来的id"+checkBox.getId()+"我从数组取出来的元素是第"+i+"个"+"内容是"+(FeedbackOptionItem) checkBox.getTag());
            //如果 数组里面拿出来的和 方法传进来的 是 同一个 我就让他选中 如果不是 我就让 不选中
//            if (checkBox.getId()==checkedId){
//                checkBox.setChecked(true);
//            }else {
//                checkBox.setChecked(false);
//            }
        }


//        for (CheckBox pos : checkBoxes) {
//
//            if (pos.getId() == checkedId) {
//                pos.setChecked(true);
//            }else{
//                pos.setChecked(false);
//            }
//        }
    }

    /**
     * 加载左上右的文字
     *
     * @param context
     * @param mode               0 左面  1 上面 2 行结算 3个数
     * @param optionsList
     * @param llOptionsContainer
     */
//    public static void updateLeftNameView(final Context context, int mode, List<leftname> optionsList, LinearLayout llOptionsContainer) {
//        LinearLayout.LayoutParams layoutParams = null;
//        int size = optionsList.size();
//        if (mode == 0 || mode == 2) {
//            layoutParams = new LinearLayout.LayoutParams(getDimens(context, R.dimen.lines_buttonwit),
//                    getDimens(context, R.dimen.lines_buttonhei));
//        }
//
//
//        if (mode == 1) {
//            layoutParams = new LinearLayout.LayoutParams(getDimens(context, R.dimen.lines_buttonwit),
//                    getDimens(context, R.dimen.lines_buttonhei));
//            final TextView radio1 = new TextView(context);
//            radio1.setText("名称");
//            radio1.setGravity(Gravity.CENTER);
//            radio1.setLayoutParams(layoutParams);
//            layoutParams.setMargins(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
//            radio1.setBackgroundResource(R.drawable.feedback_radiobtn_style_selector);
//            // 设置图片与文本的间距，据说必须先setButtonDrawable，再调用
//            radio1.setPadding(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
//
//            llOptionsContainer.addView(radio1);
//            radio1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    UtilsToast.showToast(context, context.getString(R.string.infoString) + radio1.getText().toString());
//                }
//            });
//            //radio1.setVisibility(View.INVISIBLE);
//        }
//
//        for (int i = 0; i < size; i++) {
//            final TextView radio = new TextView(context);
//            layoutParams = new LinearLayout.LayoutParams(getDimens(context, R.dimen.lines_buttonwit),
//                    getDimens(context, R.dimen.lines_buttonhei));
//            layoutParams.setMargins(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
//            leftname optionItem = optionsList.get(i);
//            optionItem.radioGroupIndex = i;
//            radio.setGravity(Gravity.CENTER);
//            radio.setLayoutParams(layoutParams);
//            if (mode == 1) {
//                if (i == 4) {
//                    radio.setText("个数");
//                } else if (i==5){
//                    layoutParams = new LinearLayout.LayoutParams(getDimens(context, R.dimen.lines_buttonwit2),
//                            getDimens(context, R.dimen.lines_buttonhei2));
//                    layoutParams.setMargins(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
//                    radio.setTextColor(context.getResources().getColor(R.color.red));
//                    radio.setLayoutParams(layoutParams);
//                    radio.setText("行结算");
//                }else{
//                    radio.setText(optionItem.title);
//                }
//
//            } else if (mode == 2) {
//                layoutParams = new LinearLayout.LayoutParams(getDimens(context, R.dimen.lines_buttonwit2),
//                        getDimens(context, R.dimen.lines_buttonhei2));
//                layoutParams.setMargins(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
//                radio.setText("0");
//                radio.setTextColor(context.getResources().getColor(R.color.red));
//                radio.setLayoutParams(layoutParams);
//            } else if (mode == 0) {
//                radio.setText(optionItem.title);
//            } else if (mode == 3) {
//                radio.setText(optionItem.num);
//            }
//
//            radio.setTag(optionItem);
//            radio.setBackgroundResource(R.drawable.feedback_radiobtn_style_selector);
//            // 设置图片与文本的间距，据说必须先setButtonDrawable，再调用
//            radio.setPadding(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
////                if (mode==0&&i==0)
////                radio.setVisibility(View.INVISIBLE);
//            llOptionsContainer.addView(radio);
//            if (mode == 2) {
//                radio.setTag(i);
//                arrayList1.add(radio);
//            }
//
//            radio.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    UtilsToast.showToast(context, "详细内容是" + radio.getText().toString());
//                }
//            });
//        }
//
//
//    }

    /**
     * 订单显示的清单列表
     * @param context
     * @param accidents
     * @param sum
     * @param llOptionsContainer
     */
//    public static void setverticalTextView(Context context, List<accident> accidents, String sum, LinearLayout llOptionsContainer) {
//        llOptionsContainer.removeAllViews();
//        LinearLayout.LayoutParams layoutParams =null;
//        TextView textViewtitle =null;
//        LinearLayout.LayoutParams textlayoutParams =null;
//        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//         textlayoutParams = new LinearLayout.LayoutParams(getDimens(context, R.dimen.lines_buttonwit1),
//                getDimens(context, R.dimen.lines_buttonhei1));
//        LinearLayout linearLayout = new LinearLayout(context);
//        layoutParams.setMargins(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
//        linearLayout.setLayoutParams(layoutParams);
//
//        for (int i = 0; i < 4; i++) {
//            textViewtitle =  new TextView(context);
//            textViewtitle.setBackgroundResource(R.drawable.feedback_radiobtn_style_selector);
//            UtilsSetSize.setTextSize(context, textViewtitle,R.dimen.lines_textsp1);
//            textViewtitle.setGravity(Gravity.CENTER);
//            textViewtitle.setLayoutParams(textlayoutParams);
//           // layoutParams.setMargins(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
//            if (i==0){
//                textViewtitle.setText("名称");
//            }
//            if (i==1){
//                textViewtitle.setText("个数");
//            }
//            if (i==2){
//                textViewtitle.setText("类型");
//            }
//            if (i==3){
//                textViewtitle.setText("单价");
//            }
//
//            linearLayout.addView(textViewtitle);
//
//        }
//        llOptionsContainer.addView(linearLayout);
//
//        if (accidents != null) {
//            TextView textView =null;
//            for (int i = 0; i < accidents.size(); i++) {
//                LinearLayout linearLayout1 = new LinearLayout(context);
//                linearLayout1.setLayoutParams(layoutParams);
//
//                accident accident = accidents.get(i);
//                for (int z = 0; z < 4; z++) {
//
//                    textView = new TextView(context);
//                    textView.setBackgroundResource(R.drawable.feedback_radiobtn_style_selector);
//                  //  textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getDimens(context, R.dimen.lines_textsp1));
//                    UtilsSetSize.setTextSize(context, textView,R.dimen.lines_textsp1);
//                    textView.setTextColor(context.getResources().getColor(R.color.red));
//                    textlayoutParams.setMargins(getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading), getDimens(context, R.dimen.lines_pading));
//
//                    textView.setLayoutParams(textlayoutParams);
//                    textView.setGravity(Gravity.CENTER);
//                   // textView.setText(accident.name + "\t" + accident.num + "\t" + accident.type + "\t" + accident.title);
//                    if (z==0){
//                        textView.setText(accident.name);
//                    }
//                    if (z==1){
//                        textView.setText(accident.num);
//                    }
//                    if (z==2){
//                        textView.setText(accident.type);
//                    }
//                    if (z==3){
//                        textView.setText(accident.title);
//                    }
//
//
//
//                    linearLayout1.addView(textView);
//
//                }
//                llOptionsContainer.addView(linearLayout1);
//            }
//             textView = new TextView(context);
//            textView.setText("总金额" + sum);
//            llOptionsContainer.addView(textView);
//        }
//    }

    /***
     * @description 恢复未选中的radioGroup状态
     * @author zhongwr
     * @params radioGroupIndex 被选中的radioGroup的下标
     * @update 2016年1月9日 下午2:44:06
     */
    private static void resetUnselectedRadioGroupStatus(int radioGroupIndex) {
        if (null != llOptionsContainer) {
            // int size = radioGroupList.size();
            int size = llOptionsContainer.getChildCount();
            //Logcat.dLog("size = " + size);
            LogUtils.i("size = " + size);
            for (int i = 0; i < size; i++) {
                if (i == radioGroupIndex) {
                    continue;
                }
                View view = llOptionsContainer.getChildAt(i);
                if (null != view && view instanceof RadioGroup) {
                    ((RadioGroup) view).clearCheck();
                }
                // RadioGroup radioGroup = radioGroupList.get(i);
                // radioGroup.clearCheck();
            }
        }
    }

    /**
     * 清除所有状态
     */
    public  static void  clearCheckAll(){
        if (null != llOptionsContainer){
            int size = llOptionsContainer.getChildCount();
            LogUtils.i("有几个子孩子"+size);
            for (int i = 0; i < size; i++) {
                View childAt = llOptionsContainer.getChildAt(i);
                if(null!=childAt &&childAt instanceof RadioGroup){
                    ((RadioGroup) childAt).clearCheck();
                }
            }
        }
    }
    /***
     * 意见反馈：信息详情item
     *
     * @author "zhongwr"
     */
    public static class FeedbackOptionItem {
        /**
         * 类型id
         */
        public String tid;

        public String title;
        public String name;
        public String type;
        public String rows;
        public String num;
        public String span;



        public FeedbackOptionItem(String tid, String title, String name, String type, String rows, String num, String span) {
            this.tid = tid;
            this.title = title;
            this.name = name;
            this.type = type;
            this.rows = rows;
            this.num = num;
            this.span = span;
        }

        public int sum;

        @Override
        public String toString() {
            return "{" +
                    "\"" + "tid" + '\"' + ":" + '\"' + tid + '\"' +
                    "," + '\"' + "title" + '\"' + ":" + '\"' + title + '\"' +
                    ", " + '\"' + "name" + "\"" + ":" + '\"' + name + '\"' +
                    ", " + '\"' + "type" + '\"' + ":" + '\"' + type + '\"' +
                    ", " + '\"' + "rows" + '\"' + ":" + '\"' + rows + '\"' +
                    ", " + '\"' + "num" + '\"' + ":" + '\"' + num + '\"' +
                    ", " + '\"' + "radioGroupIndex" + '\"' + ":" + '\"' + radioGroupIndex + '\"' +
                    "," + '\"' + "span" + '\"' + ":" + '\"' + span + '\"' +
                    '}';
        }

        /**
         * 本地赋值： 当前item所属的radioGroup的下标
         */
        public int radioGroupIndex;
    }

    public static class leftname {
        /**
         * 类型id
         */
        public String tid;

        public String title;
        public String rows;
        public String num;


        public leftname(String tid, String title, String rows, String num) {
            this.tid = tid;
            this.title = title;
            this.rows = rows;
            this.num = num;
        }

        @Override
        public String toString() {
            return "leftname{" +
                    "tid='" + tid + '\'' +
                    ", title='" + title + '\'' +
                    ", rows='" + rows + '\'' +
                    ", num='" + num + '\'' +
                    ", radioGroupIndex=" + radioGroupIndex +
                    '}';
        }

        /**
         * 本地赋值： 当前item所属的radioGroup的下标
         */
        public int radioGroupIndex;
    }

    public static class accident {
        /**
         * 类型id
         */
        public String tid;
        //钱
        public String title;
        //名
        public String name;
        public String type;
        public String rows;
        public String num;

        public accident(String tid, String title, String name, String type, String rows, String num) {
            this.tid = tid;
            this.title = title;
            this.name = name;
            this.type = type;
            this.rows = rows;
            this.num = num;
        }
    }

    public static int getDimens(Context context, int resId) {

        return context.getResources().getDimensionPixelSize(resId);
    }


}
