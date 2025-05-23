package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.Interface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SW_UserInterface;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;
import java.util.function.Consumer;

public class UI_Manager extends CoreComponent {
    public ArrayList<Interface> interfs;
    private String secondaryTextOutput = "";
    private String primaryTextOutput = "";
    private boolean changed = false;
    private long lastTime = 0;
    private int warningLastTime = 3000;
    public UI_Manager(Boolean active, TeamCore core) {
        super("UI_Manager", active, core, ComponentType.UI_MANAGER);
    }
    public void refresh(){
        if(this.active){
            synchronized (this.interfs){
                synchronized (this.secondaryTextOutput){
                    synchronized (this.warningQueue){
                        for(Interface interf : interfs){
                            if(this.secondaryTextOutput == ""){
                                ((SW_UserInterface)interf).print(this.primaryTextOutput, false);
                            }else{
                                ((SW_UserInterface)interf).print(this.secondaryTextOutput, true);
                            }
                            ((SW_UserInterface)interf).updatePrint();
                            if(System.currentTimeMillis() - this.lastTime > this.warningLastTime){
                                this.secondaryTextOutput = "";
                                if(!this.warningQueue.isEmpty()){
                                    this.secondaryTextOutput = this.warningQueue.remove(0);
                                    this.lastTime = System.currentTimeMillis();
                                }
                            }
                            this.changed = true;
                        }
                    }
                }
            }

        }

    }

    public void showMenu(String title, ArrayList<String> options, Consumer<Integer> callback){ // TODO: implement support for a menu on multiple SW_UserInterface's at the same time
        if(this.active){
            synchronized (this.interfs){
                ((SW_UserInterface)interfs.get(0)).showMenu(title, options, callback);
            }
        }
        // >=0 selected option
        // -1 cancelled
    }

    public void print(String toPrint){
        synchronized (this.primaryTextOutput){
            if(this.active){
                if(this.changed){
                    // print the same text regardless of refreshes until a new print is called
                    this.primaryTextOutput = "";
                    this.changed = false;
                }
                if(toPrint.endsWith("\n")){
                    this.primaryTextOutput += toPrint;
                }else{
                    this.primaryTextOutput += toPrint + "\n";
                }
            }
        }
    }
    private final ArrayList<String> warningQueue = new ArrayList<>();
    public void showWarning(String warning){
        synchronized (this.warningQueue){
            if(this.active){
                if(!warningQueue.contains(warning)){ // prevent spamming from step functions
                    warningQueue.add(warning);
                }
            }
        }
    }

    @Override
    public void step(TeamCore core) {

    }

    @Override
    public void update(TeamCore core) {
        synchronized (this.interfs){
            this.interfs = this.core.getInterfacesOfType(InterfaceType.USER_INTERFACE);
        }
    }

    @Override
    public int test(TestingEnviromentCore core) {
        // there aren't really any tests to be done rn
        return 0;
    }
}
