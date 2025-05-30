package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class CoreComponent {
    public boolean active = false;
    public final String name;
    public final ComponentType[] types;
    public final TeamCore core;
    public final ArrayList<CoreComponentSettings> settings;
    public final ArrayList<ComponentType> dependencies;

    protected CoreComponentBackingThread runningThread;

    public static class CoreComponentBackingThread extends Thread{

        public ArrayList<Consumer<Integer>> stepFuncs;
        public TeamCore core;
        public CoreComponentBackingThread(ArrayList<Consumer<Integer>> callback, TeamCore core){
            this.stepFuncs = callback;
            this.core = core;
        }

        public void startRunning(){
            this.run = true;
            this.stoppedRunning = false;
            this.start();
        }
        private Boolean run = false;
        private Boolean stoppedRunning = true;
        public void run(){
            while(true){
                if(run){
                    double start = System.currentTimeMillis();
                    for(Consumer<Integer> cons: this.stepFuncs){
                        cons.accept(0);
                    }
                    this.core.reportThreadLoopTime(Thread.currentThread().getName(), System.currentTimeMillis()-start);
                }else{
                    this.stoppedRunning = true;
                    break;
                }
            }
        }

        public void stopRunning(){
            this.run = false;
            while(this.stoppedRunning != true){} // COULD END REALLY BADLY IF THIS RUNS forever...
        }
    }

    public CoreComponent(String name, Boolean active, TeamCore core, ComponentType... type){
        this(name, active, core, new ArrayList<>(), type);
    }
    public CoreComponent(String name, Boolean active, TeamCore core, ArrayList<ComponentType> dependencies,ComponentType... type){
        if(name != null && !name.isEmpty()){
            this.name = name;
            this.active = active;
            /// is fixed now :)
            this.types = type;
            this.core = core;
            this.settings = new ArrayList<CoreComponentSettings>();
            this.dependencies = dependencies;
        }else{
            throw new IllegalArgumentException("Name cannot be empty (CoreComponent constructor)");
        }
    }

    public final HardwareMap getHwMap(){
        return this.core.getHwMap();
    }

    public final ArrayList<String> getAllSettings(){
        ArrayList<String> res = new ArrayList<>();
        for(CoreComponentSettings caca : this.settings){
            for(String setting : caca.getSettings()){
                if(!res.contains(setting)){
                    res.add(setting);
                }
            }
        }
        return res;
    }
    public final ArrayList<String> getSettingOptions(String settingName){
        for(CoreComponentSettings caca: this.settings){
            if(caca.getSettings().contains(settingName)){
                return caca.getSettingOptions(settingName);
            }
        }
        return new ArrayList<>();
    }

    public final boolean changeSetting(String settingName, String option){
        synchronized (this.settings){
            // first check if the option is still valid, as it can happen that after a code push the old config is applied and the component might not work.
            if(this.getSettingOptions(settingName).contains(option)){
                for(CoreComponentSettings caca: this.settings){
                    if(caca.getSettings().contains(settingName)){
                        caca.changeSetting(settingName, option);
                    }
                }
                return true;
            }
        }
        return false;
    }
    public final ArrayList<String> primitiveGetStatus(){
        if(this.active){
            return this.getStatus();
        }else{
            ArrayList<String> toReturn = new ArrayList<String>();
            toReturn.add("Component not active");
            return toReturn;
        }
    }
    protected ArrayList<String> getStatus(){
        ArrayList<String> caca = new ArrayList<>();
        caca.add("active");
        return caca;
    }

    public final void primitiveStep(TeamCore core){
        if(this.active){
            this.step(core);
        }
    }

    protected abstract void step(TeamCore core);

    public final void primitiveUpdate(TeamCore core){
        if(this.active){
            this.update(core);
        }
    }
    protected abstract void update(TeamCore core); // update function should contain all init code
    // to allow real-time updating of components and hot testing of components

    public final int primitiveTest(TestingEnviromentCore core){
        if(this.active){
            return this.test(core);
        }else{
            return -404; // hopefully users remember this means component not active
        }
    }
    protected abstract int test(TestingEnviromentCore core);

    public final void primitiveExit(){
        if(this.active){
            this.exit();
        }
    }
    protected void exit(){}
}
