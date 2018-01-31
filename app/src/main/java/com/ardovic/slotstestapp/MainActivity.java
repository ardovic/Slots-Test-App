package com.ardovic.slotstestapp;

import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GamePanel gamePanel;
    Button button;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        if (StaticLoader.OVER == null) {
            new StaticLoader(getResources(), point.x, point.y);
        }

        ImageView bgImagePanel2 = new ImageView(this);
        bgImagePanel2.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                button.setEnabled(true);
            }
        };

        gamePanel = new GamePanel(this, mHandler);
        gamePanel.setZOrderOnTop(true);
        gamePanel.getHolder().setFormat(PixelFormat.TRANSPARENT);

        RelativeLayout.LayoutParams fillParentLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rootPanel = new RelativeLayout(this);
        rootPanel.setLayoutParams(fillParentLayout);

        button = new Button(this);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rl.setMargins(0,0,0, StaticLoader.OVER_OFFSET);
        button.setText("SPIN");
        button.setLayoutParams(rl);


        rootPanel.addView(bgImagePanel2, fillParentLayout);
        rootPanel.addView(gamePanel, fillParentLayout);
        rootPanel.addView(button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePanel.roll();
                button.setEnabled(false);
            }
        });

        setContentView(rootPanel);
    }
}
