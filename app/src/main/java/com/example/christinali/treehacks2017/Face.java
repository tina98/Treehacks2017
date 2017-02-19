package com.example.christinali.treehacks2017;

import java.io.Serializable;

/**
 * Created by christinali on 2/19/17.
 */

public class Face implements Serializable {
    private String age;
    private String gender;
    private String expression;
    private String race;
    private String chin_size;
    private String eyebrow_size;
    private String beard;
    private String hair_color;
    private String head_shape;
    private String head_width;
    private String nose_width;
    private String nose_shape;

    public void setAge(String age){ this.age=age;}
    public void setGender(String gender){ this.gender=gender;}
    public void setExpression(String expression){ this.expression=expression;}
    public void setRace(String race){ this.race=race;}
    public void setChin_size(String chin_size){ this.chin_size=chin_size;}
    public void setEyebrow_size(String eyebrow_size){ this.eyebrow_size=eyebrow_size;}
    public void setBeard(String beard){ this.beard=beard;}
    public void setHair_color(String hair_color){ this.hair_color=hair_color;}
    public void setHead_shape(String head_shape){ this.head_shape=head_shape;}
    public void setHead_width(String head_width){ this.head_width=head_width;}
    public void setNose_width(String nose_width){ this.nose_width=nose_width;}
    public void setNose_shape(String nose_shape){ this.nose_shape=nose_shape;}

    public String getAge(){ return age;}
    public String getGender(){ return gender;}
    public String getExpression(){ return expression;}
    public String getRace(){ return race;}
    public String getChin_size(){ return chin_size;}
    public String getEyebrow_size(){ return eyebrow_size;}
    public String getBeard(){ return beard;}
    public String getHair_color(){ return hair_color;}
    public String getHead_shape(){ return head_shape;}
    public String getHead_width(){ return head_width;}
    public String getNose_width(){ return nose_width;}
    public String getNose_shape(){ return nose_shape;}

    @Override
    public String toString(){
        String print="Age: "+getAge()+"\nGender: "+getGender()+"\nExpression: "+getExpression()+
                "\nRace: "+getRace()+"\nChin size: "+getChin_size()+"\nEyebrow size: "+getEyebrow_size()+
                "\nBeard: "+getBeard()+"\nHair color: "+getHair_color()+"\nHead shape: "+getHead_shape()+
                "\nHead width: "+getHead_width()+"\nNose width: "+getNose_width()+"\nNose shape: "+getNose_shape();
        return print;
    }
}
