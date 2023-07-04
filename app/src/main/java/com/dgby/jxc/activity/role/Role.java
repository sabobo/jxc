package com.dgby.jxc.activity.role;

public class Role {


    private  String  name;

    private  int  id;

    public Role(int Id, String Name) {
        this.id = Id;
        this.name = Name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

}
