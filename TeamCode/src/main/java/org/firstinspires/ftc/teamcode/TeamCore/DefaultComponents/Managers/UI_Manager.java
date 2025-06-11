package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.Interface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SW_UserInterface;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class UI_Manager extends CoreComponent {
    public ArrayList<Interface> interfs = new ArrayList<>();
    private String secondaryTextOutput = "";
    private String primaryTextOutput = "";
    private boolean changed = false;
    private long lastTime = 0;
    private int warningLastTime = 3000;
    public UI_Manager(Boolean active, TeamCore core) {
        super("UI_Manager", active, core, ComponentType.UI_MANAGER);
    }

    Map<String, String> keysToThreadID = new HashMap<>();
    Map<String, Thread> threadIDToThread = new HashMap<>();


    private Boolean updateNotifer = false; // basically when a thread calls a print func, it gets paused until the refresh. This is done so code that prints repeatedly is easier to make and doesn't need extra checks
    // to do this i use .wait and .notify on the object.
    // for example: thread A calls print. Print adds to queue and pauses by using updateNotifier.wait
    // then, the core calls refresh, which after updating interfaces, calls updateNotifier, letting thread A continue running(and all other waiting threads)
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
                            synchronized (this.updateNotifer){
                                this.updateNotifer.notifyAll();
                            }
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
        try {
            synchronized (this.updateNotifer){
                this.updateNotifer.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
        try {
            synchronized (this.updateNotifer){
                this.updateNotifer.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
        try {
            synchronized (this.updateNotifer){
                this.updateNotifer.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void step(TeamCore core) {
        try {
            this.uiThread.stepNotifier.wait();
            this.refresh();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    CoreComponentBackingThread uiThread; // for the components which print regularly
    CoreComponentBackingThread uiManagerThread; // for the ui_manager itself because it's easier to wait in the ui_managers step func

    @Override
    public void update(TeamCore core) {
        this.interfs = this.core.getInterfacesOfType(InterfaceType.USER_INTERFACE);
        this.refresh();
        this.uiThread = this.core.createNewThread();
        this.uiThread.setName("UI-thread");
        this.uiManagerThread = this.core.createNewThread();
        this.uiManagerThread.setName("UI_Manager-thread");
        this.core.moveComponentToThread(this, "UI_Manager-thread");
    }

    public void enableRegularPrintingForComponent(CoreComponent comp){
        CoreComponentBackingThread orgThread = core.getComponentBackingThread(comp.name);
        orgThread.deattachComponent(comp);
        this.uiThread.attachComponent(comp);
    }

    @Override
    public int test(TestingEnviromentCore core) {
        // there aren't really any tests to be done rn
        return 0;
    }
}
