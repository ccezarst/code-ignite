package org.firstinspires.ftc.teamcode.Core.DefaultComponents;

import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;

public abstract class CoreComponent {
    public boolean active = false;
    public final String name;
    public final ComponentType type;
    public final DefaultCore core;
    public CoreComponent(String name, Boolean active, ComponentType type, DefaultCore core){
        if(name != null && !name.isEmpty()){
            this.name = name;
            this.active = active;
            this.type = type;
            this.core = core;
        }else{
            throw new IllegalArgumentException("Name cannot be empty (CoreComponent constructor)");
        }
    }
    public ArrayList<String> getStatus(){return null;}
    public abstract void step(DefaultCore core);
    public abstract void update(DefaultCore core); // update function should contain all init code
    // to allow real-time updating of components and hot testing of components
}
