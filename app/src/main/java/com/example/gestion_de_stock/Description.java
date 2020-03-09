package com.example.gestion_de_stock;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Description extends AppCompatActivity {

        private TextView desc ;
        private ImageView link_regist1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

         desc = (TextView)findViewById(R.id.desc);
        link_regist1 = (ImageView) findViewById(R.id.link_regist1);

        link_regist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Description.this, MainActivity.class);

                Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim3, R.anim.anim4).toBundle();
                startActivity(i, bundle);
            }


        });

    }}


