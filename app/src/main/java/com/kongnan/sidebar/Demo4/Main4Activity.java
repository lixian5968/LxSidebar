package com.kongnan.sidebar.Demo4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CanvasDemo(this));
    }


}
