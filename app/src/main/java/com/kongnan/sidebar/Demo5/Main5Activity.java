package com.kongnan.sidebar.Demo5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kongnan.sidebar.R;

public class Main5Activity extends AppCompatActivity {

    LxSideBar mLxSideBar;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        mLxSideBar = (LxSideBar) findViewById(R.id.mLxSideBar);
        mTextView = (TextView) findViewById(R.id.mTextView);


        mLxSideBar.setOnTextChangeListener(new LxSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                mTextView.setText(s);
            }
        });

    }

    public void change(View v) {

        mLxSideBar.setCheck(10);

    }

}
