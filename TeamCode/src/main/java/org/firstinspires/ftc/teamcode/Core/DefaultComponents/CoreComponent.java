package org.firstinspires.ftc.teamcode.Core.DefaultComponents;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.Interface;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class CoreComponent {
    public boolean active = false;
    public final String name;
    public final ComponentType[] types;
    public final DefaultCore core;
    public CoreComponent(String name, Boolean active, DefaultCore core, ComponentType... type){
        if(name != null && !name.isEmpty()){
            this.name = name;
            this.active = true; // dumb feature, don't use
            this.types = type;
            this.core = core;
        }else{
            throw new IllegalArgumentException("Name cannot be empty (CoreComponent constructor)");
        }
    }
    public ArrayList<String> getStatus(){
        ArrayList<String> caca = new ArrayList<>();
        caca.add("active");
        return caca;
    }
    public final void primitiveStep(DefaultCore core){
        if(this.active){
            this.step(core);
        }
    }

    public abstract void step(DefaultCore core);
    public abstract void update(DefaultCore core); // update function should contain all init code
    // to allow real-time updating of components and hot testing of components
}
