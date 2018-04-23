package com.example.eunkong.mysampleandroid.data;

/**
 * Created by eunkong on 2018. 3. 15..
 */

public class Person {

    private String name;
    private String country;
    private String twiiter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTwiiter() {
        return twiiter;
    }

    public void setTwiiter(String twiiter) {
        this.twiiter = twiiter;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", twiiter='" + twiiter + '\'' +
                '}';
    }
}
