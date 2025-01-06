package org.firstinspires.ftc.teamcode.TeamCore;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;

import java.util.ArrayList;

public final class TestingEnviromentCore extends TeamCore {
    public TestingEnviromentCore(HardwareMap hwMap) {
        super(null, hwMap);
        this.activateInteractionLogging();
    }

    private String currentComponentName = "Init";
    public ArrayList<String> logs = new ArrayList<>();
    @Override
    public void logInteraction(String message){
        this.logs.add(this.currentComponentName + " - " + message);
    }

    public void setCurrentComponentName(String newName){
        this.currentComponentName = newName;
    }

    public void reset(){
        this.disableInteractionLogging();
        HardwareMap hwMap = this.getGlobalVariable("HardwareMap", HardwareMap.class);
        // weird ass workaround
        this.wipeComponents();
        this.superSecretFunc(null, hwMap);
        this.update();
        this.activateInteractionLogging();
        }
}
