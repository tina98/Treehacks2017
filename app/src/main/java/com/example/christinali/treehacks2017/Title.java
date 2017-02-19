package com.example.christinali.treehacks2017;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Base64;
import java.util.Iterator;
import java.io.OutputStream;
import java.io.BufferedWriter;
import android.widget.Toast;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.lang.Exception;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import android.util.Log;
public class Title extends AppCompatActivity {
    private Face selfieFace=new Face();

    private static final int CAMERA_REQUEST = 1888;

    private Bitmap selfie;

    private String b64b="";
    private String img_uid="";
    private String img_uid2="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }
    public void picture(View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap photo;
        if (requestCode == CAMERA_REQUEST) {
            photo = (Bitmap) data.getExtras().get("data");


//            ImageView imageView = (ImageView) findViewById(R.id.img);
//            imageView.setImageBitmap(photo);


        } else {
            photo=Bitmap.createBitmap(0 , 0, Bitmap.Config.ARGB_8888);
        }
        selfie=photo;

        ProgressBar spinner=(ProgressBar) findViewById(R.id.loading);
        spinner.setVisibility(View.VISIBLE);
        try {
            specialGenomicAlgorithm(photo);
        }catch (Exception e){

        }

    }



    public String encode(Bitmap bmp)
    {
//        //File imagefile = new File(filename);
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(imagefile);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
    private void specialGenomicAlgorithm(Bitmap photo) throws Exception {

        b64b=encode(photo);


        new SendRequest().execute();
        new SendRequest2().execute();
        new AnalyzePicture().execute();
        new ClassifyPicture().execute();

//        Toast.makeText(getApplicationContext(), selfieFace.toString(),
//                        Toast.LENGTH_LONG).show();


        //figure out Google ML stuff
        //figure out genomic mapping
        //return animal, breed, age, size, sex
        String animal="dog";
        String breed="german+shepard";
        String age="young";
        String sex="F";
        String size="L";
        String location="94305";

        String URL="http://api.petfinder.com/pet.find?animal="+animal+
                "&format=json&key=9fbd89091d39e4f5f41aea03d402fdf7&location="+
                location+"&pet_breed="+breed+"&age="+age+"&sex="+sex+"&size="+size;
//        String URL="http://api.petfinder.com/pet.find?animal=dog&format=json&key=9fbd89091d39e4f5f41aea03d402fdf7&location=94305";
        new FindPet().execute(URL);


//        Intent dataVisualization=new Intent(this, DataV.class);
//        dataVisualization.putExtra("photo", photo);
//        startActivity(dataVisualization);
    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL("http://www.betafaceapi.com/service_json.svc/UploadNewImage_File");

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("api_key", "d45fd466-51e2-4701-8da8-04351c872236");
                postDataParams.put("api_secret", "171e8465-f548-401d-b63b-caf0dc28df5f");
                postDataParams.put("detection_flags", "extended");
                postDataParams.put("imagefile_data", b64b);
                postDataParams.put("original_filename","test.png");

                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                img_uid=jsonObj.getString("img_uid");

//                Toast.makeText(getApplicationContext(), uid,
//                        Toast.LENGTH_LONG).show();

            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "RIP",
                        Toast.LENGTH_LONG).show();
            }


        }
    }

    public class SendRequest2 extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL("http://www.betafaceapi.com/service_json.svc/UploadNewImage_File");

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("api_key", "d45fd466-51e2-4701-8da8-04351c872236");
                postDataParams.put("api_secret", "171e8465-f548-401d-b63b-caf0dc28df5f");
                postDataParams.put("detection_flags", "classifier");
                postDataParams.put("imagefile_data", b64b);
                postDataParams.put("original_filename","test.png");

                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                img_uid2=jsonObj.getString("img_uid");

//                Toast.makeText(getApplicationContext(), uid,
//                        Toast.LENGTH_LONG).show();

            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "RIP",
                        Toast.LENGTH_LONG).show();
            }


        }
    }

    public class AnalyzePicture extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL("http://www.betafaceapi.com/service_json.svc/GetImageInfo");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("api_key", "d45fd466-51e2-4701-8da8-04351c872236");
                postDataParams.put("api_secret", "171e8465-f548-401d-b63b-caf0dc28df5f");
//                postDataParams.put("img_uid",img_uid);
                postDataParams.put("img_uid","d7bd8ded-e1cb-452a-b61d-9f3e6d80024d");
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }


        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray tags=jsonObj.getJSONArray("faces").getJSONObject(0).getJSONArray("tags");
                for (int i=0; i<tags.length();i++){
                    JSONObject tag=tags.getJSONObject(i);
//                    Toast.makeText(getApplicationContext(), tag.toString(),
//                            Toast.LENGTH_LONG).show();
                    if (tag.getString("name").equals("chin size")){
                        selfieFace.setChin_size(tag.getString("value"));
                    }
                    if (tag.getString("name").equals("color hair")){
                        selfieFace.setHair_color(tag.getString("value"));
                    }
                    if (tag.getString("name").equals("eyebrows size")){
                        selfieFace.setEyebrow_size(tag.getString("value"));
                    }
                    if (tag.getString("name").equals("hair beard")){
                        selfieFace.setBeard(tag.getString("value"));
                    }
                    if (tag.getString("name").equals("head shape")){
                        selfieFace.setHead_shape(tag.getString("value"));
                    }
                    if (tag.getString("name").equals("head width")){
                        selfieFace.setHead_width(tag.getString("value"));
                    }
                    if (tag.getString("name").equals("nose shape")){
                        selfieFace.setNose_shape(tag.getString("value"));
                    }
                    if (tag.getString("name").equals("nose width")){
                        selfieFace.setNose_width(tag.getString("value"));
                    }
                }


            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "ERROR",
                        Toast.LENGTH_LONG).show();
            }


        }
    }

    public class ClassifyPicture extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://www.betafaceapi.com/service_json.svc/GetImageInfo");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("api_key", "d45fd466-51e2-4701-8da8-04351c872236");
                postDataParams.put("api_secret", "171e8465-f548-401d-b63b-caf0dc28df5f");
//                postDataParams.put("img_uid",img_uid2);
                postDataParams.put("img_uid", "545d2b42-8494-4f21-ac8a-0ebd09cd8800");
                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray tags = jsonObj.getJSONArray("faces").getJSONObject(0).getJSONArray("tags");
                for (int i = 0; i < tags.length(); i++) {
                    JSONObject tag = tags.getJSONObject(i);
//                    Toast.makeText(getApplicationContext(), tag.toString(),
//                            Toast.LENGTH_LONG).show();
                    if (tag.getString("name").equals("age")) {
                        selfieFace.setAge(tag.getString("value"));
                    }
                    if (tag.getString("name").equals("gender")) {
                        selfieFace.setGender(tag.getString("value"));
                    }
                    if (tag.getString("name").equals("race")) {
                        selfieFace.setRace(tag.getString("value"));
                    }
                }


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "ERROR",
                        Toast.LENGTH_LONG).show();
            }


        }
    }

        public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


    private class FindPet extends AsyncTask<String, Void, ArrayList<Pet>> {
//        private TextView textView=(TextView) findViewById(R.id.txt);

        @Override
        protected ArrayList<Pet> doInBackground(String... strings) {

            ArrayList<Pet> pets=new ArrayList<Pet>();
//            String pets = "";
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

                JSONArray possiblePets=json.getJSONObject("petfinder").getJSONObject("pets").getJSONArray("pet");
                for (int i=0; i<possiblePets.length(); i++){

                    String name=possiblePets.getJSONObject(i).getJSONObject("name").getString("$t");

                    String petID=possiblePets.getJSONObject(i).getJSONObject("id").getString("$t");

                    JSONArray photos=possiblePets.getJSONObject(i).getJSONObject("media").getJSONObject("photos").getJSONArray("photo");
                    String pURL="";
                    for (int p=0; p<photos.length(); p++){
                        JSONObject photo=photos.getJSONObject(p);
                        if (photo.getString("@size").equals("x") && photo.getString("@id").equals("1")){
                            pURL=photo.getString("$t");
//                            pets=pets+name+"~"+pURL+" ";
                        }
                    }

                    Pet tempPet=new Pet(name, pURL, petID);
                    pets.add(tempPet);

                }

                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return pets;
        }

        @Override
        protected void onPostExecute(ArrayList<Pet> pets) {
            super.onPostExecute(pets);

//            String[] tokens = temp.split(" |~");
//            Map<String, String> perfectPets = new HashMap<>();
//            for (int i=0; i<tokens.length-1; ) perfectPets.put(tokens[i++], tokens[i++]);
//
////            textView.setText(perfectPets.toString());
//            textView.setText(perfectPets.toString());
            Intent dataV=new Intent(getApplicationContext(), DataV.class);

            int counter=0;
            for (Pet tmpPet: pets){
                String tmpName="p"+counter;
                dataV.putExtra(tmpName, tmpPet);
                counter++;
            }

            dataV.putExtra("length", pets.size());
            dataV.putExtra("photo", selfie);
            dataV.putExtra("face", selfieFace);
            startActivity(dataV);
        }

    }



}
