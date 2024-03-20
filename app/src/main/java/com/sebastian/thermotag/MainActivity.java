package com.sebastian.thermotag;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button enterButton;
    TextView textViewTitle10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        textViewTitle10 = findViewById(R.id.textViewTitle10);
        textViewTitle10.setSelected(true);
        textViewTitle10.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textViewTitle10.setSingleLine(true);
        textViewTitle10.setMarqueeRepeatLimit(-1);
        enterButton=findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, menuKalkulator.class);
                startActivity(intent);
            }
        });
    }
}