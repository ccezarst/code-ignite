package org.firstinspires.ftc.teamcode.Core.DefaultComponents;

import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;

public class CoreComponent {
    public boolean active = false;
    public final String name;
    public final ComponentType type;
    public CoreComponent(String name, Boolean active, ComponentType type){
        if(name != null && !name.isEmpty()){
            this.name = name;
            this.active = active;
            this.type = type;
        }else{
            throw new IllegalArgumentException("Name cannot be empty (CoreComponent constructor)");
        }
    }
    public ArrayList<String> getStatus(){return null;}
    public void step(DefaultCore core){};
    public void update(DefaultCore core){}; // update function should contain all init code
    // to allow real-time updating of components and hot testing of components
}
