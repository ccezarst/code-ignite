package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.ButtonMapperKeybindMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.StateMachineButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_DriveMotors;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_HwMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.SoftwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.GamepadManager;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.InputSource;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.ManualDrivingManager;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;

public class ManualCore extends DefaultCore{
    public ManualCore(HardwareMap hwMap, int drivingGamepadNumber, Gamepad... gps){
        this.addComponent(new HW_HwMap("HW_hwMap", true, this, hwMap));
        this.addComponent(new GamepadManager("Gamepad Manager", true, this, gps));
        this.addComponent(new HW_DriveMotors("HW_DriveMotors", true, this));
        this.addComponent(new ManualDrivingManager("MAN_DrivingManager", true, drivingGamepadNumber, this));
        // TODO: ADD DRIVE MOTORS
        // TODO: DO IT WHILE "EXTENDING" THE OLD CLASS
    }
    // inits a new state machine and maps keybinds to states
    public void addStateMachine(StateMachine stMach, ButtonMapperKeybindMap...  btnMaps){
        // multiple buttons maps because one can only contains one gamepad's buttons mapping
        this.addComponent(stMach);
        this.addComponent(new StateMachineButtonMapper(stMach.name + " keybind mapper", true, stMach.name, this, btnMaps));
    }
}
