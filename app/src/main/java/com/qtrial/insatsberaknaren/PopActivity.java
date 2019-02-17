package com.qtrial.insatsberaknaren;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PopActivity extends Activity {

    private Button mCloseButton;
    private TextView mSumma;
    private TextView mLagfart;
    private TextView mPantBrev;

    private String insats;
    private double lagfart;
    private double pantbrev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        mCloseButton = (Button) findViewById(R.id.btn_close);
        mSumma = (TextView) findViewById(R.id.textView6);
        mLagfart = (TextView) findViewById(R.id.textView);
        mPantBrev = (TextView) findViewById(R.id.textView7);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(((int)(width*.7)), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        params.dimAmount = 0.60f;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(params);

        Intent i = getIntent();
        insats = i.getStringExtra("summa");
        lagfart = i.getDoubleExtra("lagfart", -1);
        pantbrev = i.getDoubleExtra("pantbrev", -1);

        int lagfartInt = (int) lagfart;
        int pantbrevInt = (int) pantbrev;

        String lagfartDisplay = getResources().getString(R.string.lagfart_info, lagfartInt);
        String pantbrevDisplay = getResources().getString(R.string.pantbrev_info, pantbrevInt);

        mLagfart.setText(lagfartDisplay);
        mPantBrev.setText(pantbrevDisplay);
        mSumma.setText(insats);

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
