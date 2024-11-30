package org.firstinspires.ftc.teamcode.Core;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;

import java.util.ArrayList;

public class DefaultCore {
    private ArrayList<CoreComponent> components = new ArrayList<CoreComponent>();

    public void addComponent(CoreComponent comp){
        if(this.getComponentFromName(comp.name) == null){
            this.components.add(comp);
        }else{
            throw new IllegalArgumentException("CoreComponent already exists with the name " + comp.name);
        }
    }

    public CoreComponent getComponentFromName(String name){
        for(int i = 0; i < this.components.size(); i++){
            if(this.components.get(i).name == name){
                return this.components.get(i);
            }
        }
        return null;
    }

    public ArrayList<CoreComponent> getAllComponents(){return this.components;}

    public void step(){
        for(int i = 0; i < this.components.size(); i++){
            this.components.get(i).step(this);
        }
    }

    public String getStatus(){
        String toReturn = "";
        for(int i = 0; i < this.components.size(); i ++){
            ArrayList<String> status = this.components.get(i).getStatus();
            String temp = "";
            for(int b = 0; b < status.size(); b++){
                temp += this.components.get(i).name + ": " + status.get(i) + "\n";
            }
            toReturn += temp + "\n";
        }
        return toReturn;
    }
}
