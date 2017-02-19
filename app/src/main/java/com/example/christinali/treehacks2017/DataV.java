package com.example.christinali.treehacks2017;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DataV extends AppCompatActivity {

    ArrayList<Pet> pets=new ArrayList<Pet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_v);

        Intent title=getIntent();
        Bitmap photo=(Bitmap) title.getExtras().get("photo");
        int size=(int)title.getExtras().get("length");

        for (int i=0; i<size; i++){
            String tmpName="p"+i;

            Pet tmpPet=(Pet)title.getSerializableExtra(tmpName);
            pets.add(tmpPet);
        }

        Face selfieFace=(Face)title.getSerializableExtra("face");

        TextView tv=(TextView) findViewById(R.id.tvDATAV);
        tv.setText(selfieFace.toString());
        ImageView imageView=(ImageView) findViewById(R.id.img2);
        imageView.setImageBitmap(photo);
    }
    public void startT(View view){

        Intent petTinder=new Intent(this, PetTinder.class);

        int counter=0;
        for (Pet tmpPet: pets){
            String tmpName="p"+counter;
            petTinder.putExtra(tmpName, tmpPet);
            counter++;
        }

        petTinder.putExtra("length", pets.size());

        startActivity(petTinder);
    }
}
