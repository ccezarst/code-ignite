package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Extra;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_HwMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MotorPositioner extends CoreComponent {
    private Map<String, Double> motors = new HashMap<>();
    private Map<String, Double> servos = new HashMap<>();

    private HardwareMap hwMap;
    private ArrayList<String> motorOptions = new ArrayList<>();
    private ArrayList<String> servoOptions = new ArrayList<>();
    private UI_Manager man;

    public MotorPositioner(Boolean active, DefaultCore core) {
        super("MotorPositioner", active, core, ComponentType.OTHER);
    }
    private int selection = 0;
    private String selectedMotor = "";
    private String selectedMotorB = "";

    @Override
    public void step(DefaultCore core) {
        if(selection == 0){
            this.selectedMotorB = "";
            this.selectedMotor = "";
            ArrayList<String> options = new ArrayList<>();
            options.add("Single motor");
            options.add("Single servo");
            options.add("Motor pair");
            options.add("Servo pair");
            options.add("Show positions");
            int rez = man.showMenu("Select function", options);
            if(rez != -1){
                this.selection = rez;
            }
        }else if(selection == 1){ // single motor
            int rez = man.showMenu("Select motor", this.motorOptions);
            if(rez == -1){
                this.selection = 0;
            }else if(rez != 0){
                this.selectedMotor = this.motorOptions.get(rez - 1);
                this.selection = 11;// 1 for single motor + 1 for first option of this category
            }
        }else if(selection == 2){ // single servo
            int rez = man.showMenu("Select servo", this.servoOptions);
            if(rez == -1){
                this.selection = 0;
            }else if(rez != 0){
                this.selectedMotor = this.servoOptions.get(rez - 1);
                this.selection = 21;// 2 for single servo + 1 for first option of this category
            }
        }else if(selection == 3){ // motor pair
            int rez = man.showMenu("Select motors: " + selectedMotor + ", " + selectedMotorB, this.motorOptions);
            if(rez == -1){
                this.selection = 0;
            }else if(rez != 0){
                if (selectedMotor != "") {
                    this.selectedMotor = this.motorOptions.get(rez - 1);
                }else if(this.motorOptions.get(rez-1) != this.selectedMotor){
                    this.selectedMotorB = this.motorOptions.get(rez - 1);
                    this.selection = 31;// 3 for two motor + 1 for first option of this category
                }
            }
        }else if(selection == 4){ // servo pair
            int rez = man.showMenu("Select servos: " + selectedMotor + ", " + selectedMotorB, this.servoOptions);
            if(rez == -1){
                this.selection = 0;
            }else if(rez != 0){
                if (selectedMotor != "") {
                    this.selectedMotor = this.servoOptions.get(rez - 1);
                }else if(this.servoOptions.get(rez-1) != this.selectedMotor){
                    this.selectedMotorB = this.servoOptions.get(rez - 1);
                    this.selection = 41;// 4 for two servo + 1 for first option of this category
                }
            }
        }else if(selection == 5){ // show all positions
            for(Map.Entry<String, Double> mot : this.motors.entrySet()){
                man.print(mot.getKey() + ": " + mot + " - " + hwMap.dcMotor.get(mot.getKey()).getDirection());
            }
            for(Map.Entry<String, Double> mot: this.servos.entrySet()){
                man.print(mot.getKey() + ": " + mot+ " - " + hwMap.dcMotor.get(mot.getKey()).getDirection());
            }
        }

        // submenus here

        if(selection == 11){ // single motor
            ArrayList<String> opts = new ArrayList<>();
            opts.add("FORWARD - FLOAT");
            opts.add("FORWARD - BRAKE");
            opts.add("REVERSE - FLOAT");
            opts.add("REVERSE - BRAKE");
            int rez = man.showMenu("Select direction", opts);
            if(rez == -1){
                this.selection = 0;
            }else if (rez != 0){
                if(rez == 1){
                    this.initMotor(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT, this.selectedMotor);
                    this.selection = 12;
                }else if(rez == 2){
                    this.initMotor(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, this.selectedMotor);
                    this.selection = 12;
                }else if(rez == 3){
                    this.initMotor(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.FLOAT, this.selectedMotor);
                    this.selection = 12;
                }else if(rez == 4){
                    this.initMotor(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE, this.selectedMotor);
                    this.selection = 12;
                }
            }
        }else if(selection == 12){
            ArrayList<String> opts = new ArrayList<>();
            opts.add("+100");
            opts.add("+50");
            opts.add("+25");
            opts.add("+5");
            opts.add("+1");
            opts.add("-1");
            opts.add("-5");
            opts.add("-25");
            opts.add("-50");
            opts.add("-100");
            opts.add("Confirm");
            int rez = man.showMenu("Current: " + this.motors.get(this.selectedMotor), opts);
            switch(rez){
                case -1:
                    this.hwMap.dcMotor.get(selectedMotor).setPower(0);
                    this.motors.remove(this.selectedMotor);
                    this.selection = 11;
                    break;
                case 1:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) + 100);
                    break;
                case 2:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) + 50);
                    break;
                case 3:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) + 25);
                    break;
                case 4:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) + 5);
                    break;
                case 5:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) + 1);
                    break;
                case 6:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) - 1);
                    break;
                case 7:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) - 5);
                    break;
                case 8:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) - 25);
                    break;
                case 9:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) - 50);
                    break;
                case 10:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) - 100);
                    break;
                case 11:
                    this.selection = 0;
                    break;
            }
            hwMap.dcMotor.get(selectedMotor).setTargetPosition((int) Math.floor(this.motors.get(selectedMotor)));
        }



        if(selection == 21){ // single servo
            ArrayList<String> opts = new ArrayList<>();
            opts.add("FORWARD");
            opts.add("REVERSE");
            int rez = man.showMenu("Select direction", opts);
            if(rez == -1){
                this.selection = 0;
            }else if (rez != 0){
                if(rez == 1){
                    this.initServo(Servo.Direction.FORWARD, selectedMotor);
                    this.selection = 22;
                }else if(rez == 2){
                    this.initServo(Servo.Direction.REVERSE, selectedMotor);
                    this.selection = 22;
                }
            }
        }else if(selection == 22){
            ArrayList<String> opts = new ArrayList<>();
            opts.add("+0.5");
            opts.add("+0.1");
            opts.add("+0.05");
            opts.add("+0.01");
            opts.add("-0.01");
            opts.add("-0.05");
            opts.add("-0.1");
            opts.add("-0.5");
            opts.add("Confirm");
            int rez = man.showMenu("Current: " + this.servos.get(selectedMotor), opts);
            switch(rez){
                case -1:
                    this.hwMap.servo.get(this.selectedMotor).setPosition(0);
                    this.servos.remove(this.selectedMotor);
                    this.selection = 21;
                    break;
                case 1:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) + 0.5);
                    break;
                case 2:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) + 0.1);
                    break;
                case 3:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) + 0.05);
                    break;
                case 4:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) + 0.01);
                    break;
                case 5:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) - 0.01);
                    break;
                case 6:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) - 0.05);
                    break;
                case 7:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) - 0.1);
                    break;
                case 8:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) - 0.5);
                    break;
                case 9:
                    this.selection = 0;
                    break;
            }
            hwMap.servo.get(selectedMotor).setPosition(this.servos.get(selectedMotor));
        }



        if(selection == 31){ // two motor
            ArrayList<String> opts = new ArrayList<>();
            opts.add("FORWARD, FORWARD - FLOAT");
            opts.add("REVERSE, REVERSE - FLOAT");
            opts.add("FORWARD, REVERSE - FLOAT");
            opts.add("REVERSE, FORWARD - FLOAT");
            opts.add("FORWARD, FORWARD - BRAKE");
            opts.add("REVERSE, REVERSE - BRAKE");
            opts.add("FORWARD, REVERSE - BRAKE");
            opts.add("REVERSE, FORWARD - BRAKE");
            int rez = man.showMenu("Select direction", opts);
            if(rez == -1){
                this.selection = 0;
            }else if (rez != 0){
                switch(rez){
                    case 1:
                        this.initMotor(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT, this.selectedMotor);
                        this.initMotor(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT, this.selectedMotorB);
                        break;
                    case 2:
                        this.initMotor(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.FLOAT, this.selectedMotor);
                        this.initMotor(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.FLOAT, this.selectedMotorB);
                        break;
                    case 3:
                        this.initMotor(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT, this.selectedMotor);
                        this.initMotor(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.FLOAT, this.selectedMotorB);
                        break;
                    case 4:
                        this.initMotor(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.FLOAT, this.selectedMotor);
                        this.initMotor(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT, this.selectedMotorB);
                        break;

                    case 5:
                        this.initMotor(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, this.selectedMotor);
                        this.initMotor(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, this.selectedMotorB);
                        break;
                    case 6:
                        this.initMotor(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE, this.selectedMotor);
                        this.initMotor(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE, this.selectedMotorB);
                        break;
                    case 7:
                        this.initMotor(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, this.selectedMotor);
                        this.initMotor(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE, this.selectedMotorB);
                        break;
                    case 8:
                        this.initMotor(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE, this.selectedMotor);
                        this.initMotor(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, this.selectedMotorB);
                        break;
                }
                this.selection = 32;
            }
        }else if(selection == 32){
            ArrayList<String> opts = new ArrayList<>();
            opts.add("+100");
            opts.add("+50");
            opts.add("+25");
            opts.add("+5");
            opts.add("+1");
            opts.add("-1");
            opts.add("-5");
            opts.add("-25");
            opts.add("-50");
            opts.add("-100");
            opts.add("Confirm");
            int rez = man.showMenu("Current: " + (this.motors.get(this.selectedMotor) + this.motors.get(this.selectedMotorB)) / 2, opts);
            switch(rez){
                case -1:
                    this.hwMap.dcMotor.get(this.selectedMotor).setPower(0);
                    this.motors.remove(this.selectedMotor);
                    this.hwMap.dcMotor.get(this.selectedMotorB).setPower(0);
                    this.motors.remove(this.selectedMotorB);
                    this.selection = 31;
                    break;
                case 1:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) + 100);
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotorB) + 100);
                    break;
                case 2:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) + 50);
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotorB) + 50);
                    break;
                case 3:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) + 25);
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotorB) + 25);
                    break;
                case 4:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) + 5);
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotorB) + 5);
                    break;
                case 5:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) + 1);
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotorB) + 1);
                    break;
                case 6:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) - 1);
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotorB) - 1);
                    break;
                case 7:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) - 5);
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotorB) - 5);
                    break;
                case 8:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) - 25);
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotorB) - 25);
                    break;
                case 9:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) - 50);
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotorB) - 50);
                    break;
                case 10:
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotor) - 100);
                    this.motors.replace(selectedMotor, this.motors.get(selectedMotorB) - 100);
                    break;
                case 11:
                    this.selection = 0;
                    break;
            }
            hwMap.dcMotor.get(selectedMotor).setTargetPosition((int) Math.floor(this.motors.get(selectedMotor)));
            hwMap.dcMotor.get(selectedMotorB).setTargetPosition((int) Math.floor(this.motors.get(selectedMotorB)));
            hwMap.dcMotor.get(selectedMotor).setPower(1);
            hwMap.dcMotor.get(selectedMotorB).setPower(1);
        }

        if(selection == 41){ // two servo
            ArrayList<String> opts = new ArrayList<>();
            opts.add("FORWARD, FORWARD");
            opts.add("REVERSE, REVERSE");
            opts.add("FORWARD, REVERSE ");
            opts.add("REVERSE, FORWARD ");
            int rez = man.showMenu("Select direction", opts);
            if(rez == -1){
                this.selection = 0;
            }else if (rez != 0){
                switch(rez){
                    case 1:
                        this.initServo(Servo.Direction.FORWARD, this.selectedMotor);
                        this.initServo(Servo.Direction.FORWARD, this.selectedMotorB);
                        break;
                    case 2:
                        this.initServo(Servo.Direction.REVERSE, this.selectedMotor);
                        this.initServo(Servo.Direction.REVERSE, this.selectedMotorB);
                        break;
                    case 3:
                        this.initServo(Servo.Direction.FORWARD, this.selectedMotor);
                        this.initServo(Servo.Direction.REVERSE, this.selectedMotorB);
                        break;
                    case 4:
                        this.initServo(Servo.Direction.REVERSE, this.selectedMotor);
                        this.initServo(Servo.Direction.FORWARD, this.selectedMotorB);
                        break;
                }
                this.selection = 42;
            }
        }else if(selection == 42){
            ArrayList<String> opts = new ArrayList<>();
            opts.add("+0.5");
            opts.add("+0.1");
            opts.add("+0.05");
            opts.add("+0.01");
            opts.add("-0.01");
            opts.add("-0.05");
            opts.add("-0.1");
            opts.add("-0.5");
            opts.add("Confirm");
            int rez = man.showMenu("Current: " + (this.servos.get(this.selectedMotor) + this.servos.get(this.selectedMotorB)) / 2, opts);
            switch(rez){
                case -1:
                    this.hwMap.servo.get(this.selectedMotor).setPosition(0);
                    this.servos.remove(this.selectedMotor);
                    this.hwMap.servo.get(this.selectedMotorB).setPosition(0);
                    this.servos.remove(this.selectedMotorB);
                    this.selection = 41;
                    break;
                case 1:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) + 0.5);
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotorB) + 0.5);
                    break;
                case 2:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) + 0.1);
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotorB) + 0.1);
                    break;
                case 3:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) + 0.05);
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotorB) + 0.05);
                    break;
                case 4:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) + 0.01);
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotorB) + 0.01);
                    break;
                case 5:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) - 0.01);
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotorB) - 0.01);
                    break;
                case 6:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) - 0.05);
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotorB) - 0.05);
                    break;
                case 7:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) - 0.1);
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotorB) - 0.1);
                    break;
                case 8:
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotor) - 0.5);
                    this.servos.replace(selectedMotor, this.servos.get(selectedMotorB) - 0.5);
                    break;
                case 9:
                    this.selection = 0;
                    break;
            }
            hwMap.servo.get(selectedMotor).setPosition((int) Math.floor(this.motors.get(selectedMotor)));
            hwMap.servo.get(selectedMotorB).setPosition((int) Math.floor(this.motors.get(selectedMotorB)));
        }
    }
    private void initMotor(DcMotorSimple.Direction dir, DcMotor.ZeroPowerBehavior zeroPwr, String name){
        if(!this.motors.containsKey(name)){
            this.motors.put(name, 0.0);
            HardwareMap hwMap = ((HW_HwMap) this.core.getInterfacesOfType(InterfaceType.HARDWARE_MAP).get(0)).hwMap;
            DcMotor mot = hwMap.dcMotor.get(name);
            mot.setZeroPowerBehavior(zeroPwr);
            mot.setDirection(dir);
            mot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            mot.setPower(0);
        }

    }

    private void initServo(Servo.Direction dir, String name){
        this.hwMap.servo.get(name).setDirection(dir);
        this.servos.put(name, 0.0);
    }

    @Override
    public void update(DefaultCore core) {
        this.motors.clear();
        this.servos.clear();
        this.man = ((UI_Manager)this.core.getComponentFromName("UI_Manager"));
        hwMap = ((HW_HwMap) this.core.getInterfacesOfType(InterfaceType.HARDWARE_MAP).get(0)).hwMap;
        for(HardwareDevice device: hwMap){
            if(device instanceof DcMotor) {
                motorOptions.add(hwMap.getNamesOf(device).toString());
            }else if(device instanceof Servo){
                servoOptions.add(hwMap.getNamesOf(device).toString());
            }
        }
    }
}
