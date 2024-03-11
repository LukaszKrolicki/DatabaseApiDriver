package org.example;

public class Report{
    private String opis;
    private String username;

    private Integer id;
    public Report(String opis, String username, Integer id){
        this.opis=opis;
        this.username=username;
        this.id=id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
