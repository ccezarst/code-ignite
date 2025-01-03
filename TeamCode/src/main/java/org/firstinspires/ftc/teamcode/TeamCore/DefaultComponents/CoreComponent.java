package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;

public abstract class CoreComponent {
    public boolean active = false;
    public final String name;
    public final ComponentType[] types;
    public final TeamCore core;
    public final ArrayList<CoreComponentSettings> settings;
    public final ArrayList<ComponentType> dependencies;
    public CoreComponent(String name, Boolean active, TeamCore core, ComponentType... type){
        this(name, active, core, new ArrayList<>(), type);
    }
    public CoreComponent(String name, Boolean active, TeamCore core, ArrayList<ComponentType> dependencies,ComponentType... type){
        if(name != null && !name.isEmpty()){
            this.name = name;
            this.active = true; // dumb feature, don't use
            this.types = type;
            this.core = core;
            this.settings = new ArrayList<CoreComponentSettings>();
            this.dependencies = dependencies;
        }else{
            throw new IllegalArgumentException("Name cannot be empty (CoreComponent constructor)");
        }
    }

    public ArrayList<String> getAllSettings(){
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
    public ArrayList<String> getSettingOptions(String settingName){
        for(CoreComponentSettings caca: this.settings){
            if(caca.getSettings().contains(settingName)){
                return caca.getSettingOptions(settingName);
            }
        }
        return new ArrayList<>();
    }

    public void changeSetting(String settingName, String option){
        for(CoreComponentSettings caca: this.settings){
            if(caca.getSettings().contains(settingName)){
                caca.changeSetting(settingName, option);
            }
        }
    }

    public ArrayList<String> getStatus(){
        ArrayList<String> caca = new ArrayList<>();
        caca.add("active");
        return caca;
    }

    public void exit(){}
    public final void primitiveStep(TeamCore core){
        if(this.active){
            this.step(core);
        }
    }

    public abstract void step(TeamCore core);
    public abstract void update(TeamCore core); // update function should contain all init code
    // to allow real-time updating of components and hot testing of components
    public abstract int test(TestingEnviromentCore core);
}
