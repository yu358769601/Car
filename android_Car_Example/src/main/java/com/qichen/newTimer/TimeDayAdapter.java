//package com.qichen.newTimer;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.qichen.ruida.R;
//
//
//public class TimeDayAdapter extends BaseAdapter {
//
//    Context context;
//    LayoutInflater inflater;
//    String[] foods;
//    int last_item;
//    private int selectedPosition = -1;
//
//    public TimeDayAdapter(Context context, String[] foods) {
//        this.context = context;
//        this.foods = foods;
//        inflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public int getCount() {
//        return foods.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.countrylist_item, null);
//            holder = new ViewHolder();
//            holder.textView = (TextView) convertView.findViewById(R.id.textview);
//            holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
//            holder.layout = (LinearLayout) convertView.findViewById(R.id.colorlayout);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        if (selectedPosition == position) {
//            holder.textView.setTextColor(Color.BLUE);
//            holder.layout.setBackgroundColor(Color.WHITE);
//        } else {
//            holder.textView.setTextColor(Color.WHITE);
//            holder.layout.setBackgroundColor(Color.TRANSPARENT);
//        }
//        holder.textView.setText(foods[position]);
//        holder.textView.setTextColor(Color.BLACK);
//
//        return convertView;
//    }
//
//    public static class ViewHolder {
//        public TextView textView;
//        public ImageView imageView;
//        public LinearLayout layout;
//    }
//
//    public void setSelectedPosition(int position) {
//        selectedPosition = position;
//    }
//
//}
