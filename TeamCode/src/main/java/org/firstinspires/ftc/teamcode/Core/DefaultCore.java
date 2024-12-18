package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.GeneralInputMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_HwMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.SW_Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.Interface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.UI_Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DefaultCore {
    private ArrayList<CoreComponent> components = new ArrayList<CoreComponent>();

    public DefaultCore(Telemetry telem, HardwareMap hwMap){
        this.addComponent(new UI_Manager(true, this));
        this.addComponent(new SW_Telemetry(true, this, telem));
        this.addComponent(new HW_HwMap(true, this, hwMap));
        this.addComponent(new GeneralInputMapper("DefaultGeneralInputMapper", true, this));
    }

    public final void removeComponent(String name){
        this.components.remove(this.getComponentFromName(name));
    }

    public final void addComponent(CoreComponent comp){
        if(this.getComponentFromName(comp.name) == null){
            this.components.add(comp);
        }else{
            throw new IllegalArgumentException("CoreComponent already exists with the name " + comp.name);
        }
    }

    public final CoreComponent getComponentFromName(String name){
        for(int i = 0; i < this.components.size(); i++){
            if(Objects.equals(this.components.get(i).name, name)){
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

    public final ArrayList<Interface> getInterfacesOfType(InterfaceType type){
        ArrayList<CoreComponent> interfs = this.getComponentsOfType(ComponentType.INTERFACE);
        ArrayList<Interface> toReturn = new ArrayList<>();
        for(int i = 0; i < interfs.size(); i++){
            if(((Interface)(interfs.get(i))).interfaceType == type){
                toReturn.add((Interface) interfs.get(i));
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
        ((UI_Manager)this.getComponentFromName("UI_Manager")).refresh();
    }

    public final String getStatus(){
        String toReturn = "";
        for(int i = 0; i < this.components.size(); i ++){
            ArrayList<String> status = this.components.get(i).getStatus();
            String temp = "-";
            if(status != null){
                for(int b = 0; b < status.size(); b++) {
                    temp += Arrays.toString(this.components.get(i).types) + "-" + this.components.get(i).name + ": " + status.get(b) + "\n";
                }
            }

            toReturn += temp;
        }
        return toReturn;
    }
}
