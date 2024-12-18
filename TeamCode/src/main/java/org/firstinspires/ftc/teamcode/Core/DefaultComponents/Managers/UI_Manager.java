package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.Interface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SW_UserInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;

public class UI_Manager extends CoreComponent {
    public ArrayList<Interface> interfs;
    private String secondaryTextOutput = "";
    private String primaryTextOutput = "";
    private long lastTime = System.currentTimeMillis();
    private int warningLastTime = 3000;
    public UI_Manager(Boolean active, DefaultCore core) {
        super("UI_Manager", active, core, ComponentType.UI_MANAGER);
    }

    public void refresh(){
        for(Interface interf : interfs){
            if(this.secondaryTextOutput == ""){
                ((SW_UserInterface)interf).print(this.primaryTextOutput);
            }else{
                ((SW_UserInterface)interf).print(this.secondaryTextOutput);
            }
            ((SW_UserInterface)interf).updatePrint();
            this.primaryTextOutput = "";
            if(System.currentTimeMillis() - this.lastTime > this.warningLastTime){
                this.secondaryTextOutput = "";
            }
        }
    }

    public int showMenu(String title, ArrayList<String> options){ // TODO: implement support for a menu on multiple SW_UserInterface's at the same time
        return ((SW_UserInterface)interfs.get(0)).showMenu(title, options);
    }

    public void print(String toPrint){
        if(toPrint.endsWith("\n")){
            this.primaryTextOutput += toPrint;
        }else{
            this.primaryTextOutput += toPrint + "\n";
        }
    }

    public void showWarning(String warning){
        this.secondaryTextOutput = warning;
        this.lastTime = System.currentTimeMillis();
    }

    @Override
    public void step(DefaultCore core) {

    }

    @Override
    public void update(DefaultCore core) {
        this.interfs = this.core.getInterfacesOfType(InterfaceType.USER_INTERFACE);
    }
}
