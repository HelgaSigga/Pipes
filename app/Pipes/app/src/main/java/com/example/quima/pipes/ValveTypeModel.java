package com.example.quima.pipes;

/**
 * Created by Quima on 12/02/2015.
 */
public class ValveTypeModel {
    static long id;
    static String type;
    static String comment;
    static int category;

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

    public String getComment(){ return comment;}

    public void setComment(String comment){ this.comment = comment;}

    public int getCategory(){ return category; }

    public void setCategory(int category){ this.category = category; }

    public String toString(){
        return type;
    }
}
