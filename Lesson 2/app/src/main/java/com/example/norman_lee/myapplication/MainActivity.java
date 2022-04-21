package com.example.norman_lee.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    Button buttonConvert;
    Button buttonSetExchangeRate;
    EditText editTextValue;
    TextView textViewResult;
    TextView textViewExchangeRate;
    double exchangeRate;
    public final String TAG = "Logcat";
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.mainsharedprefs";
    public static final String RATE_KEY = "Rate_Key";
    public final static String A_KEY = "A_KEY";
    public final static String B_KEY = "B_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MainActivity", "onCreate");

        // 4.5 Get a reference to the sharedPreferences object
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // 4.6 Retrieve the value using the key, and set a default when there is none
        String savedExchangeRate = mPreferences.getString(RATE_KEY, null);
        Log.i("savedExchangeRate", savedExchangeRate);

        // 3.13 Get the intent, retrieve the values passed to it, and instantiate the ExchangeRate class
        Intent intent = getIntent();
        String exchangeRateString = intent.getStringExtra(RATE_KEY);

        final ExchangeRate exchangeRateObject;

        if (exchangeRateString == null){
            exchangeRateObject = new ExchangeRate(savedExchangeRate);
        }

        else{
            exchangeRateObject = new ExchangeRate(exchangeRateString);
        }

        BigDecimal exchangeRateAmount = exchangeRateObject.getExchangeRate();
        Log.i("exchangeRateAmount", exchangeRateAmount.toString());
        exchangeRate = exchangeRateAmount.doubleValue();

        // 3.13a See ExchangeRate class --->

        // 2.1 Use findViewById to get references to the widgets in the layout
        editTextValue = findViewById(R.id.editTextValue);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewExchangeRate = findViewById(R.id.textViewExchangeRate);
        textViewResult = findViewById(R.id.textViewResult);

        // 2.2 Assign a default exchange rate of 2.95 to the textView
        textViewExchangeRate.setText(Double.toString(exchangeRate));

        // 2.3 Set up setOnClickListener for the Convert Button
        // 2.4 Display a Toast & Logcat message if the editTextValue widget contains an empty string
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextValue.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, R.string.toast_empty_input, Toast.LENGTH_SHORT).show();
                    Log.i("editTextValue", "Empty input!");
                }

                // 2.5 If not, calculate the units of B with the exchange rate and display it
                // 2.5a See ExchangeRate class --->
                else{
                    String foreignAmountString = editTextValue.getText().toString();
                    BigDecimal homeAmount = exchangeRateObject.calculateAmount(foreignAmountString);
                    homeAmount = homeAmount.setScale(2, RoundingMode.CEILING);
                    String homeAmountString = homeAmount.toString();
                    textViewResult.setText(homeAmountString);
                }
            }
        });

        // 3.1 Modify the Android Manifest to specify that the parent of SubActivity is MainActivity
        // 3.2 Get a reference to the Set Exchange Rate Button
        buttonSetExchangeRate = findViewById(R.id.buttonSetExchangeRate);

        // 3.3 Set up setOnClickListener for this
        buttonSetExchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3.4 Write an Explicit Intent to get to SubActivity
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 4.1 Go to res/menu/menu_main.xml and add a menu item Set Exchange Rate
    // 5.1 Go to res/menu/menu_main.xml and add a menu item Open Map App

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // 4.2 In onOptionsItemSelected, add a new if-statement and code accordingly
        else if (id == R.id.set_exchange_rate){
            Intent intent = new Intent(MainActivity.this, SubActivity.class);
            startActivity(intent);
        }

        // 5.2 In onOptionsItemSelected, add a new if-statement
        else if (id == R.id.open_map_app){
            // 5.3 code the Uri object and set up the intent
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("geo").opaquePart("0.0").appendQueryParameter("q", "Pyongyang");
            Uri geoLocation = builder.build();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geoLocation);

            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // 4.3 override the methods in the Android Activity Lifecycle here
    // 4.4 for each of them, write a suitable string to display in the Logcat
    @Override
    protected void onStart(){
        super.onStart();
        Log.i("MainActivity", "onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("MainActivity", "onResume");
    }

    // 4.7 In onPause, get a reference to the SharedPreferences.Editor object
    // 4.8 store the exchange rate using the putString method with a key
    @Override
    protected void onPause(){
        super.onPause();
        Log.i("MainActivity", "onPause");

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(RATE_KEY, Double.toString(exchangeRate));
        Log.i("RATE_KEY MAIN", Double.toString(exchangeRate));
        preferencesEditor.apply();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainActivity", "onRestart");
    }
}
