package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String City = "Narowal";
    //Your Key
    String Key = "432ebf9b8227149684e73756ced18389";

    String url1 = "https://samples.openweathermap.org/data/2.5/weather?q=Narowal&appid=432ebf9b8227149684e73756ced18389";



    TextView txtCity,txtTime,txtValue,txtValueFeelLike,txtValueHumidity,txtValueVision,txtValueVision1,txtValueVision2;

    String nameIcon = "10d";

    EditText editText;

    Button btnLoading;

    ImageView imgIcon,click;

    RelativeLayout relativeLayoutMain;
    RelativeLayout relativeLayout;
    public class DownloadImage extends AsyncTask<String,  Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;

            URL url;

            HttpURLConnection httpURLConnection;

            InputStream inputStream;

            try {
                Log.i("LINK",strings[0]);
                url = new URL(strings[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                inputStream = httpURLConnection.getInputStream();

                bitmap = BitmapFactory.decodeStream(inputStream);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }
    public class DownloadTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";

            URL url;

            HttpURLConnection httpURLConnection;

            InputStream inputStream;

            InputStreamReader inputStreamReader;

            try {

                url = new URL(strings[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                inputStream = httpURLConnection.getInputStream();

                inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while(data != -1) {

                    result += (char) data;

                    data = inputStreamReader.read();

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;        }
    }

        public void loading(View view) {
            City = editText.getText().toString();

            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + City +"&units=metric&appid=" + Key;

            DownloadTask downloadTask = new DownloadTask();

            try {

                String result = "abc";

                result = downloadTask.execute(url).get();

                Log.i("Result:",result);

                JSONObject jsonObject = new JSONObject(result);

                JSONObject main = jsonObject.getJSONObject("main");
                JSONObject wind = jsonObject.getJSONObject("wind");

                String windspeed = wind.getString("speed");
                String temp = main.getString("temp");
                String min_temp=main.getString("temp_min");
                String max_temp=main.getString("temp_max");
                String humidity = main.getString("humidity");

                String feel_like = main.getString("feels_like");

                nameIcon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");

                Log.i("Name Icon",nameIcon);

                Long time = jsonObject.getLong("dt");

                String sTime = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH)
                        .format(new Date(time * 1000));

                txtCity.setText(City);

                txtValue.setText(temp + "°");

                txtValueVision.setText(windspeed+" m/s");

                txtValueHumidity.setText(humidity+ "%");

                txtValueFeelLike.setText(feel_like+"°");
                txtValueVision1.setText(min_temp+"°");
                txtValueVision2.setText(max_temp+"°");
                txtTime.setText(sTime);

                DownloadImage downloadImage = new DownloadImage();

                String urlIcon = " https://openweathermap.org/img/wn/"+ nameIcon +"@2x.png";

                Bitmap bitmap = downloadImage.execute(urlIcon).get();

                imgIcon.setImageBitmap(bitmap);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edt_input);

        txtCity = findViewById(R.id.txtCity);

        txtTime = findViewById(R.id.txtTime);

        txtValue = findViewById(R.id.txtValue);

        txtValueFeelLike = findViewById(R.id.txtValueFeelLike);

        txtValueHumidity = findViewById(R.id.txtValueHumidity);

        txtValueVision = findViewById(R.id.txtValueVision);
        txtValueVision1 = findViewById(R.id.txtValueVision1);
        txtValueVision2= findViewById(R.id.txtValueVision2);


        imgIcon = findViewById(R.id.imgIcon);
        click = findViewById(R.id.click);


        relativeLayout = findViewById(R.id.rlWeather);

        relativeLayoutMain = findViewById(R.id.rlMain_Ac);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.predict:
                        startActivity(new Intent(getApplicationContext(), Prediction.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(), MainActivity3.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;                }
                return false;
            }
        });
    }

}