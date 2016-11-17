package com.example.c_jecy.mail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import android.content.*;

public class MainActivity extends AppCompatActivity {


    boolean acf=false,paf=false;
    EditText ac;EditText pa;
    Button sb,gb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        acf=false;paf=false;
        ac=(EditText)findViewById(R.id.aceditText);

        /*ac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                    if (!acf) {
                        ac.setText(null);
                    }
                    acf = true;
                }

        });*/


        pa=(EditText)findViewById(R.id.paeditText);
        /*
        pa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!paf) {
                    pa.setText(null);
                }
                paf=true;
            }
        });*/

        sb=(Button)findViewById(R.id.sentbutton);
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,SentMail.class);
                it.putExtra("account",ac.getText().toString());
                it.putExtra("password",pa.getText().toString());
                MainActivity.this.startActivity(it);
            }
        });
        gb=(Button)findViewById(R.id.getbutton);
        gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,GetMail.class);
                it.putExtra("account",ac.getText().toString());
                it.putExtra("password",pa.getText().toString());
                MainActivity.this.startActivity(it);
            }
        });
        //new Thread(sentMail).start();
        //new Thread(getMail).start();



    }

    ;



};
