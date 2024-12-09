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

import java.util.ArrayList;
import java.util.Map;

public class ManualDrivingManager extends AnalogMapper {
    private int gamepadNumber;
    private ArrayList<InputSource> inSources = new ArrayList<InputSource>();
    private HW_DriveMotors mot;
    public ManualDrivingManager(String name, Boolean active, int gamepadNumber, DefaultCore core){
        super(name, active, core); // thmbstck mappr to recieve thumbstick values
        this.gamepadNumber = gamepadNumber;
    }
    /// The direction in which the robot is translating
    private double direction(float x, float y){
        double nX = +x;  // -
        double nY = +y; // +

        return Math.atan2(y, x) / Math.PI * 180.0 - 90.0;
    }
    /// Rotation around the robot's Z axis.
    private double rotation(float x){
        return -x;
    }
    /// Translation speed.
    private double speed(float x, float y){
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

    @Override
    public void step(DefaultCore core) {
        if
    }

    @Override
    public void analog(Gamepad gp, Map<Gamepad.Analog, Double> in) {
        if(in.get(Gamepad.Analog.RIGHT_TRIGGER) > 0.2){

        }else if(in.get(Gamepad.Analog.LEFT_TRIGGER) > 0.2){

        }else{

        }
    }
}
