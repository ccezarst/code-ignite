package org.firstinspires.ftc.teamcode.TeamCore.Drive.Implementation;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TeamCore.Drive.DefaultMotorConfigurations;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.DriveBase;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.MotorConfiguration;
import org.firstinspires.ftc.teamcode.TeamCore.Managers.UI_Manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import EngineCore.DefaultComponents.CoreComponentSettings;
import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;

/**
 * Basic mecanum drive base implementation that assumes a four wheel robot
 * with the following motor layout:
 *     Front Left  -\_/-- Front Right
 *     Back Left   --^\-  Back Right
 * Motor power order is FL, FR, BR, BL to match {@link DefaultMotorConfigurations#FourWheelMecanum}.
 */
public class FourWheelMecanumDriveBase extends DriveBase {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;

    private String frontLeftName = "frontLeft";
    private String frontRightName = "frontRight";
    private String backRightName = "backRight";
    private String backLeftName = "backLeft";

    private DcMotorSimple.Direction frontLeftDirection = DcMotorSimple.Direction.FORWARD;
    private DcMotorSimple.Direction frontRightDirection = DcMotorSimple.Direction.REVERSE;
    private DcMotorSimple.Direction backRightDirection = DcMotorSimple.Direction.REVERSE;
    private DcMotorSimple.Direction backLeftDirection = DcMotorSimple.Direction.FORWARD;

    private final Object driveCommandLock = new Object();
    private double targetStrafe = 0;
    private double targetForward = 0;
    private double targetRotation = 0;

    private boolean motorsInitialized = false;
    private boolean warningIssued = false;

    public FourWheelMecanumDriveBase(Boolean active, EngineCore core) {
        this(active, core, DefaultMotorConfigurations.FourWheelMecanum);
    }

    public FourWheelMecanumDriveBase(Boolean active, EngineCore core, MotorConfiguration conf) {
        super(active, core, conf == null ? DefaultMotorConfigurations.FourWheelMecanum : conf);
        this.settings.add(new CoreComponentSettings() {
            @Override
            public ArrayList<String> getSettings() {
                ArrayList<String> sets = new ArrayList<>();
                sets.add("Front Left Motor");
                sets.add("Front Right Motor");
                sets.add("Back Right Motor");
                sets.add("Back Left Motor");
                sets.add("Front Left Direction");
                sets.add("Front Right Direction");
                sets.add("Back Right Direction");
                sets.add("Back Left Direction");
                return sets;
            }

            @Override
            public ArrayList<String> getSettingOptions(String settingName) {
                ArrayList<String> options = new ArrayList<>();
                if(settingName.endsWith("Direction")){
                    options.add(DcMotorSimple.Direction.FORWARD.name());
                    options.add(DcMotorSimple.Direction.REVERSE.name());
                } else {
                    options.addAll(getAvailableMotorNames());
                    ensureOptionPresent(options, currentMotorNameFor(settingName));
                    options.sort(String::compareTo);
                }
                return options;
            }

            @Override
            public void changeSetting(String settingName, String option) {
                if(settingName.endsWith("Direction")){
                    if(option == null || option.isEmpty()) return;
                    DcMotorSimple.Direction newDirection = DcMotorSimple.Direction.valueOf(option);
                    if(settingName.startsWith("Front Left")){
                        frontLeftDirection = newDirection;
                    }else if(settingName.startsWith("Front Right")){
                        frontRightDirection = newDirection;
                    }else if(settingName.startsWith("Back Right")){
                        backRightDirection = newDirection;
                    }else if(settingName.startsWith("Back Left")){
                        backLeftDirection = newDirection;
                    }
                }else{
                    if(option == null || option.isEmpty()) return;
                    if(settingName.startsWith("Front Left")){
                        frontLeftName = option;
                    }else if(settingName.startsWith("Front Right")){
                        frontRightName = option;
                    }else if(settingName.startsWith("Back Right")){
                        backRightName = option;
                    }else if(settingName.startsWith("Back Left")){
                        backLeftName = option;
                    }
                }
                initMotorsFromHardwareMap();
            }
        });
    }

    @Override
    public void rotateRobotCentric(double angle) {
        synchronized (driveCommandLock) {
            targetStrafe = 0;
            targetForward = 0;
            double requested = angle / Math.PI; // PI radians corresponds to full power spin
            targetRotation = clamp(requested, -1.0, 1.0);
        }
    }

    @Override
    public void moveRobotCentricPolar(double radius, double angle) {
        double strafe = Math.cos(angle) * radius;
        double forward = Math.sin(angle) * radius;
        double magnitude = Math.hypot(strafe, forward);
        if (magnitude > 1.0) {
            strafe /= magnitude;
            forward /= magnitude;
        }
        synchronized (driveCommandLock) {
            targetStrafe = clamp(strafe, -1.0, 1.0);
            targetForward = clamp(forward, -1.0, 1.0);
            targetRotation = 0;
        }
    }

    @Override
    public void customStep(EngineCore core) {
        if (!motorsInitialized || this.currentPath != null) {
            return;
        }
        double strafe;
        double forward;
        double rotation;
        synchronized (driveCommandLock) {
            strafe = targetStrafe;
            forward = targetForward;
            rotation = targetRotation;
        }
        applyDrivePowers(strafe, forward, rotation);
    }

    @Override
    protected void update(EngineCore core) {
        initMotorsFromHardwareMap();
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }

    private void initMotorsFromHardwareMap() {
        HardwareMap hwMap = this.core.getGlobalVariable("HardwareMap", HardwareMap.class);
        if (hwMap == null) {
            motorsInitialized = false;
            warningIssued = false;
            return;
        }

        DcMotor fl = tryGetMotor(hwMap, frontLeftName);
        DcMotor fr = tryGetMotor(hwMap, frontRightName);
        DcMotor br = tryGetMotor(hwMap, backRightName);
        DcMotor bl = tryGetMotor(hwMap, backLeftName);

        if (fl == null || fr == null || br == null || bl == null) {
            motorsInitialized = false;
            warnMissingMotors();
            return;
        }

        frontLeft = fl;
        frontRight = fr;
        backRight = br;
        backLeft = bl;

        configureMotor(frontLeft, frontLeftDirection);
        configureMotor(frontRight, frontRightDirection);
        configureMotor(backRight, backRightDirection);
        configureMotor(backLeft, backLeftDirection);

        setMotors(frontLeft, frontRight, backRight, backLeft);
        motorsInitialized = true;
        warningIssued = false;
    }

    private DcMotor tryGetMotor(HardwareMap hwMap, String name) {
        if (name == null || name.isEmpty()) return null;
        try {
            return hwMap.get(DcMotor.class, name);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private void configureMotor(DcMotor motor, DcMotorSimple.Direction direction) {
        if (motor == null) return;
        motor.setDirection(direction);
        try {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } catch (Exception ignored) {
        }
        try {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } catch (Exception ignored) {
        }
    }

    private void applyDrivePowers(double strafe, double forward, double rotation) {
        double flPower = forward + strafe + rotation;
        double frPower = forward - strafe - rotation;
        double brPower = forward + strafe - rotation;
        double blPower = forward - strafe + rotation;

        double max = Math.max(1.0, Math.max(Math.max(Math.abs(flPower), Math.abs(frPower)),
                Math.max(Math.abs(brPower), Math.abs(blPower))));

        flPower /= max;
        frPower /= max;
        brPower /= max;
        blPower /= max;

        frontLeft.setPower(flPower);
        frontRight.setPower(frPower);
        backRight.setPower(brPower);
        backLeft.setPower(blPower);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private ArrayList<String> getAvailableMotorNames() {
        ArrayList<String> names = new ArrayList<>();
        HardwareMap hwMap = this.core.getGlobalVariable("HardwareMap", HardwareMap.class);
        if (hwMap == null) {
            return names;
        }
        Set<String> unique = new HashSet<>();
        for (DcMotor motor : hwMap.getAll(DcMotor.class)) {
            for (String name : hwMap.getNamesOf(motor)) {
                unique.add(name);
            }
        }
        names.addAll(unique);
        names.sort(String::compareTo);
        return names;
    }

    private void warnMissingMotors() {
        UI_Manager manager = this.core.getComponentFromName("UI_Manager", UI_Manager.class);
        String message = "FourWheelMecanumDriveBase: Unable to locate all configured drive motors";
        if (!warningIssued) {
            if (manager != null) {
                manager.showWarning(message);
            } else {
                System.out.println(message);
            }
            warningIssued = true;
        }
    }

    private void ensureOptionPresent(ArrayList<String> options, String value){
        if(value == null || value.isEmpty()) return;
        if(!options.contains(value)){
            options.add(value);
        }
    }

    private String currentMotorNameFor(String settingName){
        if(settingName.startsWith("Front Left")){
            return frontLeftName;
        }
        if(settingName.startsWith("Front Right")){
            return frontRightName;
        }
        if(settingName.startsWith("Back Right")){
            return backRightName;
        }
        if(settingName.startsWith("Back Left")){
            return backLeftName;
        }
        return "";
    }
}
