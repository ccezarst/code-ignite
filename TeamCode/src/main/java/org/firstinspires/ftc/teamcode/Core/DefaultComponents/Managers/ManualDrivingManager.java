package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.AnalogMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_DriveMotors;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;
import org.firstinspires.ftc.teamcode.hardware.DriveMotors;
import org.opencv.core.Core;

import java.util.ArrayList;
import java.util.Map;

public class ManualDrivingManager extends CoreComponent {
    private int gamepadNumber;
    private double scale = 0.5;
    private ArrayList<InputSource> inSources = new ArrayList<InputSource>();
    private HW_DriveMotors mot;
    public ManualDrivingManager(String name, Boolean active, int gamepadNumber, DefaultCore core){
        super(name, active, core, ComponentType.DRIVING_MANAGER); // thmbstck mappr to recieve thumbstick values
        this.gamepadNumber = gamepadNumber;
    }
    /// The direction in which the robot is translating
    private double direction(double x, double y){
        double nX = +x;  // -
        double nY = +y; // +

        return Math.atan2(y, x) / Math.PI * 180.0 - 90.0;
    }
    /// Rotation around the robot's Z axis.
    private double rotation(double x){
        return -x;
    }
    /// Translation speed.
    private double speed(double x, double y){
        return Math.sqrt((x * x) + (y*y));
    }

    // aceste 3 functii de mai sus sunt toate furate din CompleteDrive
    // de la ciprian

    // gets a speifid gamepad number from all input sources
    private Gamepad getGamepadFromNumber(int gamepadNr){
        for(int i = 0; i < this.inSources.size(); i++){
            if(this.inSources.get(i).containsGamepad(gamepadNr)){
                return this.inSources.get(i).getGamepad(gamepadNr);
            }
        }
        return null;
    }

    private ArrayList<InputSource> getInputSources(){
        ArrayList<CoreComponent> inputSources = this.core.getComponentsOfType(ComponentType.INPUT_SOURCE);
        ArrayList<InputSource> caca = new ArrayList<InputSource>();
        for(int i = 0; i < inputSources.size(); i++){
            caca.add((InputSource) inputSources.get(i));
        }
        return caca;
    }

    private Boolean checkForButtonHoldOnAllSources(int GamepadNr, Gamepad.Button btn){
        ArrayList<InputSource> inputSources = this.getInputSources();
        Boolean toReturn = false;
        for(int i = 0; i < inputSources.size(); i ++){
            if(inputSources.get(i).checkHold(GamepadNr, btn)){
                toReturn = true;
            }
        }
        return  toReturn;
    }

    private Boolean checkForButtonToggleOnAllSources(int GamepadNr, Gamepad.Button btn){
        ArrayList<InputSource> inputSources = this.getInputSources();
        Boolean toReturn = false;
        for(int i = 0; i < inputSources.size(); i ++){
            if(inputSources.get(i).checkToggle(GamepadNr, btn)){
                toReturn = true;
            }
        }
        return  toReturn;
    }

    private HW_DriveMotors getDriveMotors(){
        ArrayList<CoreComponent> interfaces = this.core.getComponentsOfType(ComponentType.HARDARE_INTERFACE);
        for(int i = 0; i < interfaces.size(); i++){
            if(((HardwareInterface)interfaces.get(i)).interfaceType == InterfaceType.DRIVE_MOTORS){
                return (HW_DriveMotors) interfaces.get(i);
            }
        }
        return null;
    }



    @Override
    public void update(DefaultCore core) {
        this.inSources = this.getInputSources();
        this.mot = this.getDriveMotors();
    }
    private double getAnalogFromAllInputSources(int gamepadNr, Gamepad.Analog an){
        for(int i = 0; i < this.inSources.size(); i++){
            if(this.inSources.get(i).containsGamepad(gamepadNr)){
                return this.inSources.get(i).getAnalog(gamepadNr, an);
            }
        }
        return 1;// here we return 1 so if we have an error somewhere it will always be 1
        // might be dangerous
    }
    @Override
    public void step(DefaultCore core) {
        if(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.RIGHT_TRIGGER) > 0.2){
            this.mot.drive.move(this.direction(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_X), this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_Y)),
                    this.speed(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_X), this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_Y) * scale),
                    this.rotation(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.RIGHT_STICK_X)) * scale);
        }else if(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_TRIGGER) > 0.2){
            this.mot.drive.move(this.direction(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_X), this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_Y)),
                    this.speed(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_X), this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_Y) * scale/ 2),
                    this.rotation(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.RIGHT_STICK_X)) * scale / 2);
        }else{
            this.mot.drive.move(this.direction(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_X), this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_Y)),
                    this.speed(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_X), this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.LEFT_STICK_Y)),
                    this.rotation(this.getAnalogFromAllInputSources(gamepadNumber, Gamepad.Analog.RIGHT_STICK_X)));
        }
    }

}
