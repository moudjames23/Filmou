package com.moudjames23.filmou.model;

/**
 * Created by Moud on 29-Sep-18.
 */
public class Film {

    private int id;
    private String titre;
    private String resume;
    private String trailer;
    private String poster;

    public Film(String titre) {
        this.titre = titre;
    }

    public Film(int id, String titre, String resume, String trailer, String poster) {
        this.id = id;
        this.titre = titre;
        this.resume = resume;
        this.trailer = trailer;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getResume() {
        return resume;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getPoster() {
        return poster;
    }
}
