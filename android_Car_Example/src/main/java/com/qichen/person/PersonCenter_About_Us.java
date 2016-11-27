package com.qichen.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qichen.ruida.R;
import com.qichen.ruida.ui.Activity1;

//关于我们activity
public class PersonCenter_About_Us extends Activity implements View.OnClickListener {

    private Button mRe_1;
    private Button mRe_2;
    private Button mRe_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center__about__us);
        initView();
    }

    private void initView() {
        TextView relativeLayout_title_titleview = (TextView) this.findViewById(R.id.relativeLayout_title_titleview);
        relativeLayout_title_titleview.setText("关于我们");

        Button relativeLayout_title_leftbutton = (Button) findViewById(R.id.relativeLayout_title_leftbutton);
        relativeLayout_title_leftbutton.setVisibility(View.VISIBLE);
        relativeLayout_title_leftbutton.setBackgroundResource(R.drawable.title_leftbutton);

        relativeLayout_title_leftbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        mRe_1 = (Button) findViewById(R.id.person_button_1);
        mRe_2 = (Button) findViewById(R.id.person_button_2);
        mRe_3 = (Button) findViewById(R.id.person_button_3);
        mRe_1.setOnClickListener(this);
        mRe_2.setOnClickListener(this);
        mRe_3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Activity1. class);
        switch (v.getId()){
            case R.id.person_button_1:
                intent.putExtra("tag",0);
                startActivity(intent);
            break;
            case R.id.person_button_2:
                intent.putExtra("tag",1);
                startActivity(intent);
            break;
            case R.id.person_button_3:
                intent.putExtra("tag",2);
                startActivity(intent);
            break;


        }
    }
}
