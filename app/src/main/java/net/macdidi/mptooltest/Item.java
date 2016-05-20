package net.macdidi.mptooltest;

/**
 * Created by hsianghua on 16/5/20.
 */
public class Item {

    private int id;
    private String name;
    private String pass;

    public Item()
    {
        id = 0;
        name = "";
        pass = "";

    }

    public Item(int id, String name, String pass){

        this.id = id;
        this.name = name;
        this.pass = pass;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
