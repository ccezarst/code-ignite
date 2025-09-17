package org.firstinspires.ftc.teamcode.TeamCore.Drive.Implementation;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TeamCore.Drive.DriveBase;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.Point;

import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;

public class MecanumPedroDriveBase extends DriveBase {
    public MecanumPedroDriveBase(Boolean active, EngineCore core) {
        super(active, core);
    }

    @Override
    public void rotateRobotCentric(double angle) {

    }

    // !!! ADD CALIBRATION FOR RADIUS MODIFIER SO IT REPRESENTS
    public static final double magnitudeModifier = 5.0;
    public final HardwareMap hw = this.core.getGlobalVariable("HardwareMap", HardwareMap.class);
    public final DcMotor FR = hw.dcMotor.get("frontRight");
    public final DcMotor FL = hw.dcMotor.get("frontLeft");
    public final DcMotor BL = hw.dcMotor.get("backLeft");
    public final DcMotor BR = hw.dcMotor.get("backRight");
    @Override
    public void moveRobotCentricPolar(double radius, double angle) {
        double FRBL = Math.sin(angle - 1/4* Point.pi) * radius*magnitudeModifier;
        double FLBR = Math.sin(angle + 1/4* Point.pi) * radius*magnitudeModifier;
        synchronized (this.FR){
            synchronized (this.BL){
                synchronized (this.FL){
                    synchronized (this.BR){
                        FR.setTargetPosition((int) (FR.getCurrentPosition() + FRBL));
                        BL.setTargetPosition((int) (BL.getCurrentPosition() + FRBL));
                        FL.setTargetPosition((int) (FL.getCurrentPosition() + FLBR));
                        BR.setTargetPosition((int) (BR.getCurrentPosition() + FLBR));

                        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    }
                }
            }
        }
    }

    @Override
    protected void step(EngineCore core) {

    }

    @Override
    protected void update(EngineCore core) {
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setPower(1);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setPower(1);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setPower(1);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setPower(1);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    protected void measureMaxVelocity(){
        int start = FR.getCurrentPosition();
        long startTime = System.currentTimeMillis();
        FR.setPower(1); FL.setPower(1); BR.setPower(1); BL.setPower(1);
        try{ Thread.sleep(1000); }catch(InterruptedException e){ }
        long endTime = System.currentTimeMillis();
        int end = FR.getCurrentPosition();
        FR.setPower(0); FL.setPower(0); BR.setPower(0); BL.setPower(0);
        double dt = (endTime - startTime)/1000.0;
        this.maxVelocity = Math.abs(end - start)/dt;
    }

    protected void measureInertia(){
        int before = FR.getCurrentPosition();
        FR.setPower(1); FL.setPower(1); BR.setPower(1); BL.setPower(1);
        try{ Thread.sleep(500); }catch(InterruptedException e){ }
        FR.setPower(0); FL.setPower(0); BR.setPower(0); BL.setPower(0);
        try{ Thread.sleep(500); }catch(InterruptedException e){ }
        int after = FR.getCurrentPosition();
        this.inertia = Math.abs(after - before);
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        super.test(core);
        return 0;
    }
}
