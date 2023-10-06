package org.example;

public class Anime {
    private String name;
    private Double rating;

    public Anime(String name, Double rating) {
        this.name = name;
        this.rating = rating;
    }
    public Anime() {
        rating = -1.0;
    }
    public String getName() {
        return name;
    }

    public Double getRating() {
        return rating;
    }

    public boolean exist(){
        return getName()!=null && getRating()!=-1.0;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Name : " + name + "  -  "+ "Rating : " + rating;
    }
}
