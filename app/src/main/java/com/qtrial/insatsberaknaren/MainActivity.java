package com.qtrial.insatsberaknaren;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnCalculate;
    private ImageView infoActivity;

    SeekBar mSeekBar;
    EditText mHousePrice;
    EditText mLagfart;
    EditText mKontantInsats;
    TextView mTextViewKontantInsats;

    EditText mTotalDownPayment;
    EditText mTotalLagfart;
    EditText mTotalPantbrev;

    CalculatorUtil calculatorUtil = new CalculatorUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        btnCalculate = (Button) findViewById(R.id.buttonBeraknaTotalInsats);
        mHousePrice = (EditText) findViewById(R.id.editText);
        mLagfart = (EditText) findViewById(R.id.editText2);
        mKontantInsats = (EditText) findViewById(R.id.editText4);
        infoActivity = (ImageView) findViewById(R.id.imageView);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mTextViewKontantInsats = (TextView) findViewById(R.id.textView4);

        mTotalDownPayment = (EditText) findViewById(R.id.editText5);
        mTotalPantbrev = (EditText) findViewById(R.id.editText6);
        mTotalLagfart = (EditText) findViewById(R.id.editText7);

        mSeekBar.setProgress(0);
        mSeekBar.setMax(25-15);
        mSeekBar.setEnabled(false);
        mTextViewKontantInsats.setText(getResources().getString(R.string.kontantinsats_default));

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!anyUnset()) {
                    int housePriceToUse = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(mHousePrice.getText().toString()));
                    int lagfartToUse = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(mLagfart.getText().toString()));
                    int kontantInsatsToUse = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(mKontantInsats.getText().toString()));

                    double result = calculatorUtil.calcluteDownPaymentWithParameters(housePriceToUse, lagfartToUse, kontantInsatsToUse);
                    double getLagFart = calculatorUtil.sumLagfart(housePriceToUse);
                    double getPantbrev = calculatorUtil.sumPantbrev(housePriceToUse, lagfartToUse, kontantInsatsToUse);

                    // String spacedString = (String.format("%,d", result));

                    mTotalDownPayment.addTextChangedListener(new NumberTextWatcherForThousand(mTotalDownPayment));
                    mTotalDownPayment.setText(String.valueOf(result));

                    mTotalPantbrev.addTextChangedListener(new NumberTextWatcherForThousand(mTotalPantbrev));
                    mTotalPantbrev.setText(String.valueOf(getPantbrev));

                    mTotalLagfart.addTextChangedListener(new NumberTextWatcherForThousand(mTotalLagfart));
                    mTotalLagfart.setText(String.valueOf(getLagFart));

                    // Intent i = new Intent(getApplicationContext(), PopActivity.class);
                    // i.putExtra("summa", spacedString);
                    // i.putExtra("lagfart", getLagFart);
                    // i.putExtra("pantbrev", getPantbrev);
                    // startActivity(i);

                } else {
                    toastMessage("Ange belopp i samtliga fält");
                }
            }
        });

        mHousePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mHousePrice.getText().toString().equals("")){
                    mSeekBar.setEnabled(false);
                } else {
                    mSeekBar.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!mHousePrice.getText().toString().equals("")){
                    int housePriceToUse = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(mHousePrice.getText().toString()));

                    double seekbar = mSeekBar.getProgress();
                    double calculatedPrctInsats = (seekbar + 15) / 100;
                    double insatsToHave = calculatedPrctInsats * housePriceToUse;

                    int insatsToHaveToString = (int) insatsToHave;
                    String whatWeNeedLol = String.valueOf(insatsToHaveToString);
                    mKontantInsats.setText(whatWeNeedLol);
                }
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progress += 15;

                if (mHousePrice.length() != 0) {
                    int housePriceToUse = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(mHousePrice.getText().toString()));

                    double prctInsats = mSeekBar.getProgress();
                    double calculatedPrctInsats = (prctInsats + 15) / 100;
                    double insatsToHave = calculatedPrctInsats * housePriceToUse;

                    int insatsToHaveToString = (int) insatsToHave;
                    String whatWeNeedLol = String.valueOf(insatsToHaveToString);
                    mKontantInsats.setText(whatWeNeedLol);

                    String kontantOne = getResources().getString(R.string.kontantinsats_one, progress);
                    String kontantTwo = getResources().getString(R.string.kontantinsats_otwo);

                    mTextViewKontantInsats.setText(kontantOne+kontantTwo);
                } else {
                    toastMessage(getResources().getString(R.string.hus_pris_saknas));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mHousePrice.addTextChangedListener(new NumberTextWatcherForThousand(mHousePrice));
        mLagfart.addTextChangedListener(new NumberTextWatcherForThousand(mLagfart));
        mKontantInsats.addTextChangedListener(new NumberTextWatcherForThousand(mKontantInsats));

        infoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InfoPopActivity.class);
                startActivity(i);
            }
        });
    }

    public void resetCalculation(View view) {
        mHousePrice.setText(getResources().getString(R.string.tom_strang));
        mLagfart.setText(getResources().getString(R.string.tom_strang));
        mKontantInsats.setText(getResources().getString(R.string.tom_strang));
        mTotalDownPayment.setText("");
        mTotalPantbrev.setText("");
        mTotalLagfart.setText("");
    }

    private void toastMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.show();
    }

    public Boolean anyUnset() {
        if (mHousePrice.length() == 0) return true;
        if (mLagfart.length() == 0) return true;
        if (mKontantInsats.length() == 0) return true;
        return false;
    }
}
