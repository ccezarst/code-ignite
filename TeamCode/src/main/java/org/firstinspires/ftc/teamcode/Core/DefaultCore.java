package org.firstinspires.ftc.teamcode.Core;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;

import java.util.ArrayList;
import java.util.Arrays;

public class DefaultCore {
    private ArrayList<CoreComponent> components = new ArrayList<CoreComponent>();

    public final void addComponent(CoreComponent comp){
        if(this.getComponentFromName(comp.name) == null){
            this.components.add(comp);
        }else{
            throw new IllegalArgumentException("CoreComponent already exists with the name " + comp.name);
        }
    }

    public final CoreComponent getComponentFromName(String name){
        for(int i = 0; i < this.components.size(); i++){
            if(this.components.get(i).name == name){
                return this.components.get(i);
            }
        }
        return null;
    }

    public final ArrayList<CoreComponent> getAllComponents(){return this.components;}

    private boolean containsCompType(ComponentType[] list, ComponentType type){
        for(int i = 0; i < list.length; i++){
            if(list[i].name().equals(type.name())){
                return true;
            }
        }
        return false;
    }

    public final ArrayList<CoreComponent> getComponentsOfType(ComponentType type){
        ArrayList<CoreComponent> toReturn = new ArrayList<CoreComponent>();

        for(int i =0; i< this.components.size(); i++){
            if(this.containsCompType(this.components.get(i).types, type)){
                toReturn.add(this.components.get(i));
            }
        }

        return toReturn;
    }

    public final void init(){this.update();} // the same

    public final void update(){
        for(int i = 0; i < this.components.size(); i++){
            this.components.get(i).update(this);
        }
    }

    public final void step(){
        for(int i = 0; i < this.components.size(); i++){
            // primitive step to help not accidentally run components
            // that don't check if they should be active or not
            this.components.get(i).primitiveStep(this);
        }
    }

    public final String getStatus(){
        String toReturn = "";
        for(int i = 0; i < this.components.size(); i ++){
            ArrayList<String> status = this.components.get(i).getStatus();
            String temp = "";
            for(int b = 0; b < status.size(); b++) {
                temp += Arrays.toString(this.components.get(i).types) + "-" + this.components.get(i).name + ": " + status.get(b) + "\n";
            }
            toReturn += temp;
        }
        return toReturn;
    }
}
