package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.StateMachineButtonMapper.StateMachineButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.StateMachineButtonMapper.StateMachineButtonMapperKeybinds;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_DriveMotors;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_HwMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.GamepadManager;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.ManualDrivingManager;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Gamepad;

public class ManualCore extends DefaultCore{
    public ManualCore(Telemetry telem, HardwareMap hwMap, int drivingGamepadNumber, Gamepad... gps){
        super(telem, hwMap);
        for(Gamepad caca : gps){
            this.addComponent(new GamepadManager("Gamepad Manager: " + caca.getNumber(), true, this, caca));
        }
        this.addComponent(new HW_DriveMotors(true, this));
        this.addComponent(new ManualDrivingManager(true, drivingGamepadNumber, this));
        // TODO: ADD DRIVE MOTORS
        // TODO: DO IT WHILE "EXTENDING" THE OLD CLASS
    }
    // inits a new state machine and maps keybinds to states
    public void addStateMachine(StateMachine stMach, StateMachineButtonMapperKeybinds keys){
        // multiple buttons maps because one can only contains one gamepad's buttons mapping
        this.addComponent(stMach);
        this.addComponent(new StateMachineButtonMapper(stMach.name + " keybind mapper", true, stMach.name, this, keys));
    }
}
