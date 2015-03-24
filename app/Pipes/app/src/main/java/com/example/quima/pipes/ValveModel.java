package com.example.quima.pipes;

/**
 * Created by Quima on 12/02/2015.
 */
public class ValveModel {

    private long id;
    private String type;
    private int area;
    private int number;
    private String location;
    private String source;
    private String comment;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public int getArea(){
        return area;
    }

    public void setArea(int area){
        this.area = area;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getSource(){
        return source;
    }

    public void setSource(String source){
        this.source = source;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    @Override
    public String toString(){
        return area + " - " + number + " - " + type + " - " +  location + " - " + source + " - " + comment;
    }


}
