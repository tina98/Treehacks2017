package com.example.christinali.treehacks2017;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.widget.TextView;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Final extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);


        Intent finalIntent=getIntent();
        String petID=finalIntent.getStringExtra("perfectPet");

        new FindPetID().execute("http://api.petfinder.com/pet.get?format=json&key=9fbd89091d39e4f5f41aea03d402fdf7&id="+petID);

    }

    private class FindPetID extends AsyncTask<String, Void, Pet> {
        private TextView petFacts=(TextView) findViewById(R.id.petFacts);

        private Context context;

        //        public FindPet(Context context){
//            this.context=context;
//        }
        @Override
        protected Pet doInBackground(String... strings) {
            Pet perfectPet=new Pet("NULL","NULL","NULL");
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                JSONObject json = new JSONObject(builder.toString());
                JSONObject pet=json.getJSONObject("petfinder").getJSONObject("pet");

                String name=pet.getJSONObject("name").getString("$t");
                String petID=pet.getJSONObject("id").getString("$t");
                JSONArray photos=pet.getJSONObject("media").getJSONObject("photos").getJSONArray("photo");
                String pURL="";
                for (int p=0; p<photos.length(); p++){
                    JSONObject photo=photos.getJSONObject(p);
                    if (photo.getString("@size").equals("x") && photo.getString("@id").equals("1")){
                        pURL=photo.getString("$t");

                    }
                }
                perfectPet=new Pet(name, pURL, petID);
                perfectPet.setDescription(pet.getJSONObject("description").getString("$t"));
                perfectPet.setLocation(pet.getJSONObject("contact").getJSONObject("city").getString("$t")
                        +", "+pet.getJSONObject("contact").getJSONObject("state").getString("$t"));
                perfectPet.setAge(pet.getJSONObject("age").getString("$t"));
                perfectPet.setGender(pet.getJSONObject("sex").getString("$t"));
                perfectPet.setContactEmail(pet.getJSONObject("contact").getJSONObject("email").getString("$t"));

                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return perfectPet;
        }

        @Override
        protected void onPostExecute(Pet perfectPet) {

            ImageView petPicFinal = (ImageView) findViewById(R.id.petPicFinal);

            Glide.with(getApplicationContext()).load(perfectPet.getImagePath()).into(petPicFinal);

            TextView nameFinal=(TextView) findViewById(R.id.petNameFinal);
            nameFinal.setText(perfectPet.getPetName());

            TextView textFinal=(TextView) findViewById(R.id.petFacts);
            textFinal.setText(perfectPet.toString());
        }


    }
}
