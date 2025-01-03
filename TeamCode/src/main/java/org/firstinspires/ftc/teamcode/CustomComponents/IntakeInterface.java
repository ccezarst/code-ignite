package org.firstinspires.ftc.teamcode.CustomComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

public class IntakeInterface extends HardwareInterface {
    public IntakeInterface(Boolean active, TeamCore core) {
        super("IntakeInterface", active, core, InterfaceType.INTAKE);
    }

    private DcMotor rightMotor;
    private DcMotor leftMotor;

    private Servo rightRotate;
    private Servo leftRotate;

    private Servo intakeServo;

    private int lowLimmit = 10;
    private int highLimit = 490;

    // 0 to 100
    public void extend(double amount){
        int finalAm = (int) Math.round((((double) amount / 100) * this.highLimit) + this.lowLimmit);
        this.rightMotor.setTargetPosition(finalAm);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightMotor.setPower(0.8);
        this.leftMotor.setTargetPosition(finalAm);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.leftMotor.setPower(0.8);
        this.last = amount;
    }
    public void extend(double amount, double power){
        int finalAm = (int) Math.round((((double) amount / 100) * this.highLimit) + this.lowLimmit);
        this.rightMotor.setPower(power);
        this.leftMotor.setPower(power);
        this.rightMotor.setTargetPosition(finalAm);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.leftMotor.setTargetPosition(finalAm);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.last = amount;
    }
    private double last;
    public void subtract(double am){
        this.extend(last - am);
    }

    public void add(double am){
        this.extend(last + am);
    }

    @Override
    public void step(TeamCore core) {

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
    public void rotateMouth(double pos){
        this.leftRotate.setPosition(pos / 100);
        this.rightRotate.setPosition(pos / 100);
    }

    @Override
    public void update(TeamCore core) {
        this.rightMotor = (DcMotor) this.core.getGlobalVariable("HardwareMap", HardwareMap.class).get("motorR");
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setPower(0);
        this.leftMotor = (DcMotor) this.core.getGlobalVariable("HardwareMap", HardwareMap.class).get("motorL");
        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftMotor.setPower(0);

        this.leftRotate = (Servo) this.core.getGlobalVariable("HardwareMap", HardwareMap.class).get("intakeServoL");
        this.leftRotate.setDirection(Servo.Direction.REVERSE);
        this.rightRotate = (Servo) this.core.getGlobalVariable("HardwareMap", HardwareMap.class).get("intakeServoR");
        this.rightRotate.setDirection(Servo.Direction.FORWARD);
        this.intakeServo = (Servo) this.core.getGlobalVariable("HardwareMap", HardwareMap.class).get("eatingMotor");
        this.intakeServo.setDirection(Servo.Direction.FORWARD);
        this.intakeServo.setPosition(0.5);
    }

    @Override
    public int test(TestingEnviromentCore core) {
        return 0;
    }

    @Override
    public void exit(){
        this.extend(0);
        this.rotateMouth(57);
        this.stopEating();
    }
}
