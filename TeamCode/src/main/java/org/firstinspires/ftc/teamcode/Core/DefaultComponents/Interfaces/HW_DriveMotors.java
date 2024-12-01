package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HW_DriveMotors extends HardwareInterface{
    Map<String, DcMotorSimple.Direction> motors = new HashMap<>();
    Map<String, DcMotor> physicalMotors = new HashMap<>();
    public HW_DriveMotors(String name, Boolean active, DefaultCore core){
        super(name, active, core, InterfaceType.DRIVE_MOTORS);
        this.motors.put("leftFront", DcMotorSimple.Direction.REVERSE);
        this.motors.put("rightFront", DcMotorSimple.Direction.FORWARD);
        this.motors.put("leftRear", DcMotorSimple.Direction.REVERSE);
        this.motors.put("rightRead", DcMotorSimple.Direction.FORWARD);
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
        HardwareMap hwMap = this.getHardwareMap();
        this.physicalMotors.clear();
        for(int i = 0; i < this.motors.keySet().size(); i++){
            DcMotor motor = hwMap.dcMotor.get(this.motors.keySet().toArray()[i].toString());
            motor.setDirection(this.motors.get(this.motors.keySet().toArray()[i]));
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            this.physicalMotors.put(this.motors.keySet().toArray()[i].toString(), motor);
        }
    }
}
