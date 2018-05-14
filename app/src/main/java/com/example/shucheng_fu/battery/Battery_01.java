package com.example.shucheng_fu.battery;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.view.Window;
import android.widget.TextView;

public class Battery_01 extends AppCompatActivity {

    private int intLevel;
    private int intScale;
    private Button mybotton01 , mybotton02 ;

    private BroadcastReceiver myBroad = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action))
            {
             intLevel = intent.getIntExtra("level" , 0);
             intScale = intent.getIntExtra("scale " , 100);
                onBatteryinfo(intLevel ,intScale) ;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_01);

        mybotton01 = (Button)findViewById(R.id.myBotton01);
        mybotton01.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                registerReceiver(myBroad , new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            }
        });


    }

    public void onBatteryinfo (int intLevel ,int intScale )
    {
        final Dialog d = new Dialog(Battery_01.this);
                d.setTitle(R.string.title);
                d.setContentView(R.layout.dialog);
        Window window = d.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        TextView mText = (TextView)d.findViewById(R.id.mytext);
        mText.setText(getResources().getText(R.string.body)+String.valueOf(intLevel * 100 / intScale) + "%");
        Button mybotton02 = (Button)d.findViewById(R.id.myButton02);
        mybotton02.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                unregisterReceiver(myBroad);
                d.dismiss();
            }
        });
        d.show();
    }
}
