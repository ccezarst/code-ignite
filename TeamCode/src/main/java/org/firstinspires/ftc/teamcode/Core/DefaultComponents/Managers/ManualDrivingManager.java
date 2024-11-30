package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.ThumbstickMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;

public class ManualDrivingManager extends ThumbstickMapper {
    private int gamepadNumber;
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
    @Override
    public void thumbstick(Gamepad gp, float Lx, float Ly, float Rx, float Ry){
        if(gp.getNumber() == this.gamepadNumber){
            // because we want special features, we're gonna have to manually search for a input source and check right thumbstick
            // TODO: FINNISH IMPLEMENTING DRIVING LOGIC
            // check complete drive, in the beginning of hardware.run you will find the code
        }
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

    @Override
    public void step(DefaultCore core) {

    }

    @Override
    public void update(DefaultCore core) {

    }
}
