package org.firstinspires.ftc.teamcode.TeamCore;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.OldCustomGamepad;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.ManualDriving;
import org.firstinspires.ftc.teamcode.TeamCore.Input.CustomGamepad;
import org.firstinspires.ftc.teamcode.TeamCore.StateMachine.StateMachine;

public class ManualCore extends RobotTCore {

    public ManualCore(Telemetry telem, HardwareMap hwMap, OldCustomGamepad... gps){
        super(telem, hwMap);
        for(OldCustomGamepad caca : gps){

            this.addComponent(new CustomGamepad("Gamepad Manager: " + caca.getNumber(), true, this, caca.getNumber(), caca.getGp()));
        }
        this.addComponent(new ManualDriving(true, this));

    }
    // inits a new state machine and maps keybinds to states
    public void addStateMachine(StateMachine stMach){
        // multiple buttons maps because one can only contains one gamepad's buttons mapping
        this.addComponent(stMach);
    }
}
