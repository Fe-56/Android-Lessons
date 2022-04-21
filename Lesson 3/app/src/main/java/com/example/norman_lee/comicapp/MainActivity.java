package com.example.norman_lee.comicapp;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    EditText editTextComicNo;
    Button buttonGetComic;
    TextView textViewTitle;
    ImageView imageViewComic;

    String comicNo;
    public static final String TAG = "Logcat";
    final String ERROR_NO_NETWORK = "No Network";
    final String ERROR_NOT_VALID = "Comic No Not Valid";
    final String ERROR_MALFORMED_URL = "Malformed URL";
    final String ERROR_BAD_JSON = "Bad JSON Response";
    final String ERROR_HTTPS_ERROR = "HTTPS Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 6.0 Study the Utils class and see what methods are available for you
        // 6.1 Ensure that Android Manifest has permissions for internet and has orientation fixed
        // 6.2 Get references to widgets
        editTextComicNo = findViewById(R.id.editTextComicNo);
        buttonGetComic = findViewById(R.id.buttonGetComic);
        textViewTitle = findViewById(R.id.textViewTitle);
        imageViewComic = findViewById(R.id.imageViewComic);

        // 6.3 Set up setOnClickListener for the button
        buttonGetComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // 6.4 Retrieve the user input from the EditText
                comicNo = editTextComicNo.getText().toString();

                // 6.5 - 6.9 Modify getComic below
                //TODO 6.10 If network is active, call the getComic method with the userInput
                if (Utils.isNetworkAvailable(MainActivity.this)){
                    getComic(comicNo);
                }
            }
        });
    }

    // 6.5 - 6.9 ****************
    // you are reminded that new Runnable{} is an anonymous inner class

    void getComic(final String userInput) {
        // 6.5 Make sure getComic has the signature getComic(final String userInput); make sure an executor and a handler are instantiated
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable(){
            @Override
            public void run(){ // child thread/background work
                // 6.6 (background work) create a final Container<Bitmap> cBitmap object which will be used for communication between the main thread and the child thread
                final Container<Bitmap> cBitmap = new Container<>();

                // 6.7 (background work) Call Utils.getImageURLFromXkcdApi to get the image URL from comicNo
                try{
                    String imageURLString = Utils.getImageURLFromXkcdApi(comicNo);

                    // 6.8 (background work)Call Utils.getBitmap using the URL to get the bitmap
                    URL imageURL = new URL(imageURLString);
                    Bitmap comic = Utils.getBitmap(imageURL);
                    cBitmap.set(comic);

                    handler.post(new Runnable(){
                       @Override
                       public void run(){
                            // 6.9 (UI thread work)Assign the Bitmap downloaded to imageView. The bitmap may be null.
                           Bitmap downloadedImage = cBitmap.get();

                           if (downloadedImage != null){
                               imageViewComic.setImageBitmap(downloadedImage);
                           }
                       }
                    });
                }

                catch (IOException ex1) {}

                catch (JSONException ex1) {}
            }
        });
    }

    class Container<T> {
        T value;
        Container() {
            this.value = null;
        }
        void set(T x) { this.value = x; }
        T get() { return this.value; }
    }
}
