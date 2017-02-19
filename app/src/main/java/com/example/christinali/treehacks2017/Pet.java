package com.example.christinali.treehacks2017;

import java.io.Serializable;

/**
 * Created by christinali on 2/19/17.
 */

public class Pet implements Serializable {
    private String petName;
    private String imagePath;
    private String petID;

    private String description;
    private String location;
    private String age;
    private String gender;
    private String contactEmail;


    public Pet(String petName, String imagePath, String petID){
        this.petName=petName;
        this.imagePath=imagePath;
        this.petID=petID;
    }
    public String getPetName(){return petName;}
    public String getImagePath(){ return imagePath;}
    public String getPetID(){ return petID;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}

    public String getLocation(){return location;}
    public void setLocation(String location){this.location=location;}

    public String getAge(){return age;}
    public void setAge(String age){this.age=age;}

    public String getGender(){return gender;}
    public void setGender(String gender){this.gender=gender;}

    public String getContactEmail(){return contactEmail;}
    public void setContactEmail(String contactEmail){this.contactEmail=contactEmail;}

    public String toString(){
        String temp=""+getDescription()+"\nAge: "+getAge()+"\nLocation: "+getLocation()
                +"\nGender: "+getGender()+"\nContact Email: "+getContactEmail();
        return temp;
    }


}

