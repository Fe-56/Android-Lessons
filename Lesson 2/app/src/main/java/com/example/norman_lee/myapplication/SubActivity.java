package com.example.norman_lee.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class SubActivity extends AppCompatActivity {

    Button buttonBackToCalculator;
    EditText editTextSubValueOfA;
    EditText editTextSubValueOfB;
    public final static String INTENT_EXCH_RATE = "Exchange Rate";
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.subsharedprefs";
    public static final String RATE_KEY = "Rate_Key";
    public final static String A_KEY = "A_KEY";
    public final static String B_KEY = "B_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // 4.9 Implement saving to shared preferences for the contents of the EditText widget
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // 3.5 Get references to the editText widgets
        editTextSubValueOfA = findViewById(R.id.editTextSubValueA);
        editTextSubValueOfB = findViewById(R.id.editTextSubValueB);

        // 3.6 Get a reference to the Back To Calculator Button
        buttonBackToCalculator = findViewById(R.id.buttonBackToCalculator);

        // 3.7 Set up setOnClickListener
        buttonBackToCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3.8 Obtain the values stored in the editTextWidgets
                String unitsOfA = editTextSubValueOfA.getText().toString(); // home
                String unitsOfB = editTextSubValueOfB.getText().toString(); // foreign

                // 3.9 Check that these values are valid --> See the Utils class
                try{
                    Utils.checkInvalidInputs(unitsOfA);
                    Utils.checkInvalidInputs(unitsOfB);

                    // 3.10 Set up an explicit intent and pass the exchange rate back to MainActivity
                    Intent intent = new Intent(SubActivity.this, MainActivity.class);
                    ExchangeRate exchangeRateObject = new ExchangeRate(unitsOfA, unitsOfB);
                    intent.putExtra(RATE_KEY, exchangeRateObject.getExchangeRate().toString());
                    Log.i("RATE_KEY INTENT SUB", exchangeRateObject.getExchangeRate().toString());
                    startActivity(intent);
                }

                // 3.12 Decide how you are going to handle a situation when the editText widgets are empty
                catch (NumberFormatException ex1){
                    Toast.makeText(SubActivity.this, R.string.toast_empty_input, Toast.LENGTH_SHORT).show();
                    Log.i("unitsOfAorB", "Empty input!");
                }

                // 3.11 Decide how you are going to handle a divide-by-zero situation or when negative numbers are entered
                catch (IllegalArgumentException ex2){
                    Toast.makeText(SubActivity.this, R.string.toast_zero_or_negative_input, Toast.LENGTH_SHORT).show();
                    Log.i("unitsOfAorB", "Zero or negative input!");
                }
            }
        });
    }

    // 4.10 Don't forget to override onPause()
    @Override
    public void onPause(){
        super.onPause();

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        String unitsOfA = editTextSubValueOfA.getText().toString(); // home
        String unitsOfB = editTextSubValueOfB.getText().toString(); //foreign
        ExchangeRate exchangeRateObject;

        if ((unitsOfA.equals("")) || (unitsOfB.equals(""))){
            exchangeRateObject = new ExchangeRate();
        }

        else{
            exchangeRateObject = new ExchangeRate(unitsOfA, unitsOfB);
        }

        preferencesEditor.putString(RATE_KEY, exchangeRateObject.getExchangeRate().toString());
        Log.i("RATE_KEY SUB", exchangeRateObject.getExchangeRate().toString());
        preferencesEditor.apply();
    }
}
