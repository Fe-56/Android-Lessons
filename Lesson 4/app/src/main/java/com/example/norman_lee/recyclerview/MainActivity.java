package com.example.norman_lee.recyclerview;

import static com.example.norman_lee.recyclerview.DataEntry.KEY_NAME;
import static com.example.norman_lee.recyclerview.DataEntry.KEY_PATH;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CharaAdapter charaAdapter;
    ImageView imageViewAdded;

    DataSource dataSource;

    final String KEY_DATA = "data";
    final String LOGCAT = "RV";
    final String PREF_FILE = "mainsharedpref";
    final int REQUEST_CODE_IMAGE = 1000;

    SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 11.1 Get references to the widgets
        recyclerView = findViewById(R.id.charaRecyclerView);
        imageViewAdded = findViewById(R.id.imageViewAdded);

        // 12.7 Load the Json string from shared Preferences
        mPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        Gson gson = new Gson();

        // 12.8 Initialize your dataSource object with the Json string
        String json = mPreferences.getString(KEY_DATA, "");
        dataSource = gson.fromJson(json, DataSource.class);

        // 11.2 Create your dataSource object by calling Utils.firstLoadImages
        ArrayList<Integer> drawablesIds = new ArrayList<>();
        drawablesIds.add(R.drawable.bulbasaur);
        drawablesIds.add(R.drawable.eevee);
        drawablesIds.add(R.drawable.gyrados);
        drawablesIds.add(R.drawable.pikachu);
        drawablesIds.add(R.drawable.psyduck);
//        drawablesIds.add(R.drawable.questionmark);
        drawablesIds.add(R.drawable.snorlax);
        drawablesIds.add(R.drawable.spearow);
        drawablesIds.add(R.drawable.squirtle);
        dataSource = Utils.firstLoadImages(MainActivity.this, drawablesIds);

        // 11.3 --> Go to CharaAdapter
        // 11.8 Complete the necessary code to initialize your RecyclerView
        charaAdapter = new CharaAdapter(this, dataSource);
        recyclerView.setAdapter(charaAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        //TODO 12.9 [OPTIONAL] Add code to delete a RecyclerView item upon swiping. See notes for the code.
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1){
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction){
                CharaAdapter.CharaViewHolder charaViewHolder = (CharaAdapter.CharaViewHolder) viewHolder;
                int position = charaViewHolder.getAdapterPosition();
                dataSource.removeDataData(position);
                charaAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "An entry has been deleted!", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // 12.1 Set up an Explicit Intent to DataEntry Activity with startActivityForResult (no coding)
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataEntry.class);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
    }

    // 12.6 Complete onPause to store the DataSource object in SharedPreferences as a JSON string
    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataSource);
        preferencesEditor.putString(KEY_DATA, json);
        preferencesEditor.apply();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

        return super.onOptionsItemSelected(item);
    }*/

    // 12.5 Write onActivityResult to get the data passed back from DataEntry and add to DataSource object
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra(KEY_NAME);
            String path = data.getStringExtra(KEY_PATH);
            dataSource.addData(name, path);
            int newestIndex = dataSource.getSize() - 1;
            imageViewAdded.setImageBitmap(dataSource.getImage(newestIndex));
            Toast.makeText(MainActivity.this, "New image added! Name is " + name, Toast.LENGTH_SHORT).show();
            charaAdapter.notifyDataSetChanged();
        }
    }
}
