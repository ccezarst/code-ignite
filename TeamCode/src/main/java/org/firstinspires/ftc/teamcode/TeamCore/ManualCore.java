package org.firstinspires.ftc.teamcode.TeamCore;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.CustomGamepad;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.OldCustomGamepad;

public class ManualCore extends TeamCore {
    public ManualCore(Telemetry telem, HardwareMap hwMap, int drivingGamepadNumber, OldCustomGamepad... gps){
        super(telem, hwMap);
        for(OldCustomGamepad caca : gps){
            this.addComponent(new CustomGamepad("Gamepad Manager: " + caca.getNumber(), true, this, caca.getNumber(), caca.getGp()));
        }

    }
    // inits a new state machine and maps keybinds to states
    public void addStateMachine(StateMachine stMach){
        // multiple buttons maps because one can only contains one gamepad's buttons mapping
        this.addComponent(stMach);
    }
}
