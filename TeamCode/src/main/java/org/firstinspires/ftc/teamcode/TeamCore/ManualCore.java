package org.firstinspires.ftc.teamcode.TeamCore;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.HW_DriveMotors;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.GamepadManager;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.ManualDrivingManager;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.CustomGamepad;

public class ManualCore extends TeamCore {
    public ManualCore(Telemetry telem, HardwareMap hwMap, int drivingGamepadNumber, CustomGamepad... gps){
        super(telem, hwMap);
        for(CustomGamepad caca : gps){
            this.addComponent(new GamepadManager("Gamepad Manager: " + caca.getNumber(), true, this, caca));
        }
        this.addComponent(new HW_DriveMotors(true, this));
        this.addComponent(new ManualDrivingManager(true, drivingGamepadNumber, this));
        // TODO: ADD DRIVE MOTORS
        // TODO: DO IT WHILE "EXTENDING" THE OLD CLASS
    }
    // inits a new state machine and maps keybinds to states
    public void addStateMachine(StateMachine stMach){
        // multiple buttons maps because one can only contains one gamepad's buttons mapping
        this.addComponent(stMach);
    }
}
