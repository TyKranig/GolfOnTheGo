package com.tyler.gridlayouttester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clickHere = (Button) findViewById(R.id.btnTextChange);
        final TextView txtChange = (TextView) findViewById(R.id.txtHello);

        clickHere.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v){
                        txtChange.setText("Test");
                    }
                }
        );

        clickHere.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v){
                        txtChange.setText("Test");
                        return true;
                    }
                }
        );
    }
}
