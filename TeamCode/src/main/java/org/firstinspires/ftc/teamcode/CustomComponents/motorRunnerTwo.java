package org.firstinspires.ftc.teamcode.CustomComponents;

import android.os.Build;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.OneButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_HwMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.SW_Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

public class motorRunnerTwo extends OneButtonMapper {

    private Servo motor;
    private Boolean toggle = false;
    private long last = 0;
    public motorRunnerTwo(DefaultCore core) {
        super(Gamepad.Button.B, true, 1, "caca3", true, core);
    }

    public void toggleRun(){
            motor.setPosition(1);
    }
    @Override
    public void callback() {
        this.toggleRun();
    }

    private HW_HwMap getHwMap(){
        ArrayList<CoreComponent> interfs = this.core.getComponentsOfType(ComponentType.HARDARE_INTERFACE);
        for(int i = 0; i < interfs.size(); i++){
            if(((HardwareInterface)(interfs.get(i))).interfaceType == InterfaceType.HARDWARE_MAP){
                return (HW_HwMap) interfs.get(i);
            }
        }
        return null;
    }

    private SW_Telemetry getTelemetry(){
        ArrayList<CoreComponent> interfs = this.core.getComponentsOfType(ComponentType.SOFTWARE_INTERFACE);
        for(int i = 0; i < interfs.size(); i++){
            if(((SoftwareInterface)(interfs.get(i))).interfaceType == InterfaceType.TELEMETRY){
                return (SW_Telemetry) interfs.get(i);
            }
        }
        return null;
    }

    @Override
    public void update(DefaultCore core) {
        this.motor = Objects.requireNonNull(this.getHwMap()).hwMap.servo.get("eatingMotor");
        this.motor.setDirection(Servo.Direction.FORWARD);
        this.motor.setPosition(0);
    }
}
