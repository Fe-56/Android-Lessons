package com.example.myfirstapp;

// Create a new android studio project with Empty Activity
// Copy the code below
// Go to your own MainActivity.java and
// paste it over the existing code BELOW the package statement ***
// ***Sep 2019

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

// 1.1 Put in some images in the drawables folder
// 1.2 Go to activity_main.xml and modify the layout

public class MainActivity extends AppCompatActivity {

    // 1.2 Instance variables are declared for you, please import the libraries
    ArrayList<Integer> images;
    Button charaButton;
    ImageView charaImage;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1.3 Instantiate An ArrayList object
        ArrayList<Integer> images = new ArrayList<Integer>();

        // 1.4 Add the image IDs to the ArrayList
        images.add(R.drawable.ashketchum);
        images.add(R.drawable.bartsimpson);
        images.add(R.drawable.edogawaconan);
        images.add(R.drawable.judyhopps);
        images.add(R.drawable.nemo);
        images.add(R.drawable.nickwilde);
        images.add(R.drawable.pikachu);
        images.add(R.drawable.snorlax);
        images.add(R.drawable.tomandjerry);
        images.add(R.drawable.yoda);

        // 1.5 Get references to the charaButton and charaImage widgets using findViewById
        Button charaButton = findViewById(R.id.charaButton);
        ImageView charaImage = findViewById(R.id.charaImage);

        // 1.6 For charaButton, invoke the setOnClickListener method
        // 1.7 Create an anonymous class which implements View.OnClickListener interface
        // 1.8 Within onClick, write code to randomly select an image ID from the ArrayList and display it in the ImageView
        charaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Random random = new Random();
                int randomNumber = random.nextInt(images.size() - 1);

                charaImage.setImageResource(images.get(randomNumber));
            }
        });

        // 1.9 [On your own] Create another button, which when clicked, will cause one image to always be displayed
        Button snorlaxButton = findViewById(R.id.snorlaxButton);

        snorlaxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                charaImage.setImageResource(R.drawable.snorlax);
            }
        });
    }
}