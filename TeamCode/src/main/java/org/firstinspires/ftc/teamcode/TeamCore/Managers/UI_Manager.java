package org.firstinspires.ftc.teamcode.TeamCore.Managers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template.Interface;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template.SW_UserInterface;
import org.firstinspires.ftc.teamcode.TeamCore.RobotTCore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.DefaultComponents.ComponentType;
import EngineCore.TestingEnviromentCore;
public class UI_Manager extends CoreComponent {
    public ArrayList<Interface> interfs = new ArrayList<>();
    private String secondaryTextOutput = "";
    private String primaryTextOutput = "";
    private final Object outputLock = new Object();
    private boolean changed = false;
    private long lastTime = 0;
    private int warningLastTime = 3000;
    public UI_Manager(Boolean active, EngineCore core) {
        super("UI_Manager", active, core, ComponentType.UI_MANAGER);
    }

    Map<String, String> keysToThreadID = new HashMap<>();
    Map<String, Thread> threadIDToThread = new HashMap<>();


    private final Object updateNotifier = new Object(); // basically when a thread calls a print func, it gets paused until the refresh. This is done so code that prints repeatedly is easier to make and doesn't need extra checks
    // to do this i use .wait and .notify on the object.
    // for example: thread A calls print. Print adds to queue and pauses by using updateNotifier.wait
    // then, the core calls refresh, which after updating interfaces, calls updateNotifier, letting thread A continue running(and all other waiting threads)
    public void refresh(){
        if(this.active){
            synchronized (this.interfs){
                synchronized (this.outputLock){
                    synchronized (this.warningQueue){
                        for(Interface interf : interfs){
                            if(this.secondaryTextOutput.equals("")){
                                if(this.core.debugMode){
                                    this.core.getGlobalVariable("Telemetry", Telemetry.class).addLine(this.primaryTextOutput);
                                }
                                ((SW_UserInterface)interf).print(this.primaryTextOutput, false);
                            }else{
                                ((SW_UserInterface)interf).print(this.secondaryTextOutput, true);
                                if(this.core.debugMode){
                                    this.core.getGlobalVariable("Telemetry", Telemetry.class).addLine(this.secondaryTextOutput);
                                }
                            }
                            ((SW_UserInterface)interf).updatePrint();
                            if(this.core.debugMode){
                                this.core.getGlobalVariable("Telemetry", Telemetry.class).update();
                            }
                            if(System.currentTimeMillis() - this.lastTime > this.warningLastTime){
                                this.secondaryTextOutput = "";
                                if(!this.warningQueue.isEmpty()){
                                    this.secondaryTextOutput = this.warningQueue.remove(0);
                                    this.lastTime = System.currentTimeMillis();
                                }
                            }
                        }
                        this.changed = true;
                    }
                }
            }
            synchronized (this.updateNotifier){
                this.updateNotifier.notifyAll();
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
            synchronized (this.updateNotifier){
                this.updateNotifier.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // >=0 selected option
        // -1 cancelled
    }

    public void print(String toPrint){
        synchronized (this.outputLock){
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
            synchronized (this.updateNotifier){
                this.updateNotifier.wait();
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
            synchronized (this.updateNotifier){
                this.updateNotifier.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void step(EngineCore core) {
        try {
            if(this.uiThread != null){
                synchronized (this.uiThread.stepNotifier){
                    this.uiThread.stepNotifier.wait();
                }
            }
            this.refresh();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    CoreComponentBackingThread uiThread; // for the components which print regularly
    CoreComponentBackingThread uiManagerThread; // for the ui_manager itself because it's easier to wait in the ui_managers step func

    @Override
    public void update(EngineCore c) {
        RobotTCore core = (RobotTCore) c;
        this.interfs = core.getInterfacesOfType(InterfaceType.USER_INTERFACE);
        this.refresh();
        this.uiThread = new CoreComponentBackingThread(this.core);
        this.uiThread.setName("UI-thread");
        this.uiThread.startRunning();
        //this.uiManagerThread = this.core.createNewThread();
        //this.uiManagerThread.setName("UI_Manager-thread");
        //this.core.moveComponentToThread(this, "UI_Manager-thread");
        //this.uiManagerThread.startRunning();
    }

    public void enableRegularPrintingForComponent(CoreComponent comp){
        CoreComponentBackingThread orgThread = core.getComponentBackingThread(comp.name);
        orgThread.deattachComponent(comp);
        synchronized (this.uiThread){
            this.uiThread.attachComponent(comp);
        }
    }

    @Override
    public int test(TestingEnviromentCore core) {
        // there aren't really any tests to be done rn
        return 0;
    }
}
