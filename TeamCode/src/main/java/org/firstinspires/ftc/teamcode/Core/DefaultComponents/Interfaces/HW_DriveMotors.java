package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.hardware.DriveMotors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HW_DriveMotors extends HardwareInterface{

    public DriveMotors drive;
    public HW_DriveMotors(String name, Boolean active, DefaultCore core){
        super(name, active, core, InterfaceType.DRIVE_MOTORS);
    }

    @Override
    public void step(DefaultCore core) {

    }

    private final HardwareMap getHardwareMap(){
        ArrayList<CoreComponent> comps = this.core.getComponentsOfType(ComponentType.HARDARE_INTERFACE);
        for(int i = 0; i < comps.size(); i++){
            if(((HardwareInterface)comps.get(i)).interfaceType == InterfaceType.HARDWARE_MAP){
                return ((HW_HwMap)comps.get(i)).hwMap;
            }
        }
        return null;
    }

    @Override
    public void update(DefaultCore core) {
        this.drive = new DriveMotors(Objects.requireNonNull(this.getHardwareMap()));
    }
}
