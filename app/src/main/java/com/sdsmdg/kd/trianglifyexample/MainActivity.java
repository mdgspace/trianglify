package com.sdsmdg.kd.trianglifyexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sdsmdg.kd.trianglify.TrianglifyView;

public class MainActivity extends AppCompatActivity {

    public TrianglifyView trianglifyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trianglifyView = (TrianglifyView) findViewById(R.id.trianglify_main_view);
        trianglifyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.invalidate();
            }
        });
    }
}
