package com.kongnan.sidebar.Demo6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongnan.sidebar.R;

public class Demo1CopyTest extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1_copy_test);
        textView = (TextView) findViewById(R.id.textView);
        SideBar sideBar = (SideBar) findViewById(R.id.sideBar);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("OnClick ");
            }
        });
    }
}
