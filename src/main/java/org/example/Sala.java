package org.example;

public class Sala {

    //the name of variables should be same as in db
    private Integer wielkość;
    private String nazwa;

    public Sala(Integer wielkość, String nazwa){
        this.wielkość = wielkość;
        this.nazwa = nazwa;
    }

    public Integer getWielkosc() {
        return wielkość;
    }



    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
