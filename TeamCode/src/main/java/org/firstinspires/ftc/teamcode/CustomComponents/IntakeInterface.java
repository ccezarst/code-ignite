package org.firstinspires.ftc.teamcode.CustomComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_HwMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;

public class IntakeInterface extends HardwareInterface {
    public IntakeInterface(String cName, Boolean active, DefaultCore core) {
        super(cName, active, core, InterfaceType.INTAKE);
    }

    private DcMotor rightMotor;
    private DcMotor leftMotor;

    private Servo rightRotate;
    private Servo leftRotate;

    private Servo intakeServo;

    private int lowLimmit = 5;
    private int highLimit = 400;

    // 0 to 100
    public void extend(int amount){
        int finalAm = (int) Math.round((((double) amount / 100) * this.highLimit) + this.lowLimmit);
        this.rightMotor.setTargetPosition(finalAm);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightMotor.setPower(0.8);
        this.leftMotor.setTargetPosition(finalAm);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.leftMotor.setPower(0.8);
    }

    @Override
    public void step(DefaultCore core) {

    }

    private HW_HwMap getHwMap(){
        return ((HW_HwMap) this.core.getInterfacesOfType(InterfaceType.HARDWARE_MAP).get(0));
    }

    public void startEating(){
        this.intakeServo.setPosition(1);
    }

    public void stopEating(){
        this.intakeServo.setPosition(0.5);
    }

    public void startPooping(){
        this.intakeServo.setPosition(0);
    }
    // 0 -> 100
    public void rotateMouth(int pos){
        this.leftRotate.setPosition((double) pos / 100);
        this.rightRotate.setPosition((double) pos / 100);
    }

    @Override
    public void update(DefaultCore core) {
        this.rightMotor = (DcMotor) this.getHwMap().hwMap.get("motorR");
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setPower(0);
        this.leftMotor = (DcMotor) this.getHwMap().hwMap.get("motorL");
        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftMotor.setPower(0);

        this.leftRotate = (Servo) this.getHwMap().hwMap.get("intakeServoL");
        this.leftRotate.setDirection(Servo.Direction.REVERSE);
        this.rightRotate = (Servo) this.getHwMap().hwMap.get("intakeServoR");
        this.rightRotate.setDirection(Servo.Direction.FORWARD);
        this.intakeServo = (Servo) this.getHwMap().hwMap.get("eatingMotor");
        this.intakeServo.setDirection(Servo.Direction.FORWARD);
        this.intakeServo.setPosition(0.5);
    }
}
