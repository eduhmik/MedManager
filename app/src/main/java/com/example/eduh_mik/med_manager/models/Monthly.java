package com.example.eduh_mik.med_manager.models;

import java.util.ArrayList;

/**
 * Created by Mayore on 17/04/2018.
 */

public class Monthly {
    private String name;

    public Monthly(String name) {
        this.name = name;
    }

    private ArrayList<Dose> doses = new ArrayList<>();

    public String getName() {
        return name;
    }

    public ArrayList<Dose> getDoses() {
        return doses;
    }

    public Monthly(String name, ArrayList<Dose> doses) {

        this.name = name;
        this.doses = doses;
    }
    public void add(Dose dose){
        this.doses.add(dose);
    }
}
