package org.firstinspires.ftc.teamcode.CustomComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_HwMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;
import java.util.Objects;

public class IntakeInterface extends HardwareInterface {
    public IntakeInterface(String cName, Boolean active, DefaultCore core) {
        super(cName, active, core, InterfaceType.INTAKE);
    }

    private DcMotor rightMotor;
    private DcMotor leftMotor;

    private int lowLimmit = 5;
    private int highLimit = 500;

    public void extend(double amount){
        int finalAm = (int) Math.round((amount * this.highLimit) + this.lowLimmit);
        this.rightMotor.setTargetPosition(finalAm);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightMotor.setPower(1);
        this.leftMotor.setTargetPosition(finalAm);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.leftMotor.setPower(1);
    }

    @Override
    public void step(DefaultCore core) {

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

    @Override
    public void update(DefaultCore core) {
        this.rightMotor = (DcMotor) this.getHwMap().hwMap.get("motorR");
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightMotor.setPower(0);
        this.leftMotor = (DcMotor) this.getHwMap().hwMap.get("motorL");
        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.leftMotor.setPower(0);
    }
}
