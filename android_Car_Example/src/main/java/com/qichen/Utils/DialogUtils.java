package com.qichen.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.qichen.ruida.R;


/**
 * Created by Administrator 于萌萌
 * 创建日期: 9:17 . 2016年06月24日
 * 描述: Dialog 方便的 工具类   懒死了  是不是一辈子 都不用写了
 * <p/>
 * <p/>
 * 备注:
 */
public class DialogUtils {
    //
    //dialog 显示的信息
    //电话寻购
    public   static final String PHONE_GENERATE_INQUIRY   = "电话询价提示";
    public  static final String PHONE_GENERATE_INQUIRY_HINT = "您是要通知买家按照电话所述生成订单并支付吗?";


    //取消订单
    public   static final String CANCEL_SETTITLE_ORDER_FORM  = "取消订单提示";
    public  static final String CANCEL_SETMESSAGE_ORDER_FORM = "是否取消订单?";
    //货站代收
    public   static final String FREIGHT_STATION  = "货站代收收货提示";
    public  static final String FREIGHT_STATION_TISHI = "是否确定货站代收收货? 货站代收所产生的一切纠纷就爱车概不负责";
    //确定付款
    public   static final String CONFIRM_SETTITLE_ORDER_FORM  = "确认收货提示";
    public  static final String CONFIRM_SETMESSAGE_ORDER_FORM = "是否确认收货?";
    //提醒发货
    public   static final String REMINDER_DELIVERY   = "提醒发货提示";
    public  static final String CONFIRM_THE_DELIVERY  = "是否确认提醒发货?";

    //删除订单
    public   static final String DELETE_ORDER_PROMPT   = "删除订单提示";
    public  static final String CONFIRM_DELETE_ORDER  = "是否确认删除订单?";
    //闪电收货
    public   static final String LIGHTNING_RECEIVING_TIPS   = "闪电收货提示";
    public  static final String SELECT_LIGHTNING_RECEIVING  = "是否确认闪电收货?";

    //延时收货
    public   static final String DELAY_PAYMENT   = "延时收货提示";
    public  static final String SELECT_DELAY_PAYMENT  = "是否确认延时收货?";

    //评价
    public   static final String ASSESS   = "评价提示";
    public  static final String SELECT_ASSESS  = "是否确认评价?";

    //确定 和取消
    public static final String CONFIRM = "确定";
    public static final String CANCEL = "取消";


    //返回状态
    public static final int CONFIRM_STATUSCODE = -1;
    public static final int CANCEL_STATUSCODE = -2;



    public Context context;

    //你点了啥
    private static String selectorButton ="";

    /**
     *
     * 可定制  按钮上面的字  和  标题  还有内容  目前 按钮是 确定 和取消
     * @param context  上下文
     * @param
     * @param setTitle  设置标题  可以是  DialogUtils.CANCEL_SETTITLE_ORDER_FORM  取消订单 的标题字符串
     * @param setMessage 设置内容  可以是  DialogUtils.CANCEL_SETMESSAGE_ORDER_FORM 取消订单 的内容字符串
     */
    public static void showDialog(final Context context, final String setTitle, String setMessage, String confirm,String cancel,int type,final callback listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        TextView textView = new TextView(context);
        textView.setText(setTitle);
        textView.setTextColor(context.getResources().getColor(R.color.black));
        builder.setTitle(setTitle); //设置标题
        TextView textViewfu = new TextView(context);
        textViewfu.setText(setMessage);
        textViewfu.setTextColor(context.getResources().getColor(R.color.black));
        String s = textViewfu.getText().toString();
        builder.setMessage(s); //设置内容
        //点及外面不关闭哦
        builder.setCancelable(false);
        builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                //点的确定
                listener.confirm(selectorButton,CONFIRM);
            }
        });
        if (type == 1){
            builder.setNegativeButton(cancel,
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }


        builder.create().show();
    }
    /**
     * 回调   状态 按钮的 回调
     */
    public  interface callback{
         void confirm(String selectorButton, String which);
    }
//    public static Dialog showLoading(Context context, String msg){
//        Dialog dialog = new Dialog(context, );
//        dialog.setContentView(R.layout.loading_layout);
//        TextView msgView = (TextView) dialog.findViewById(R.id.tv_loading);
//        msgView.setText(msg);
//        dialog.show();
//        return dialog;
//    }
//
//    public static Dialog showLoading(Context context, int msg){
//        return  showLoading(context,context.getString(msg));
//    }

}
