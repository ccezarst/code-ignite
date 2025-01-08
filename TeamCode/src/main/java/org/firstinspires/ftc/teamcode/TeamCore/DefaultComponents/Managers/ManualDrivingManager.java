package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.GeneralInputMapper;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.HW_DriveMotors;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.AnalogTypes;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;

public class ManualDrivingManager extends CoreComponent {
    private int inputSourceID;
    private double scale = 0.5;
    private HW_DriveMotors mot;
    public ManualDrivingManager(Boolean active, int inputSourceID, TeamCore core){
        super("ManualDrivingManager", active, core, ComponentType.DRIVING_MANAGER); // thmbstck mappr to recieve thumbstick values
        this.inputSourceID = inputSourceID;
    }
    /// The direction in which the robot is translating
    private double direction(double x, double y){
        double nX = x * -1;  // -
        double nY = -y; // +

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
    private GeneralInputMapper getInputMapper(){
        return (GeneralInputMapper) this.core.getComponentFromName("DefaultGeneralInputMapper");
    }

    private double getAnalog(int gamepadNr, AnalogTypes an){
        return this.getInputMapper().getAnalogValue(gamepadNr, an);
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
    public void update(TeamCore core) {
        this.mot = this.getDriveMotors();
    }

    @Override
    public int test(TestingEnviromentCore core) {
        // this code is going to be deleted anyways
        return 0;
    }

    @Override
    public void step(TeamCore core) {
        if(this.getAnalog(inputSourceID, AnalogTypes.RIGHT_TRIGGER) > 0.2){
            this.mot.drive.move(this.direction(this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_X), this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_Y)),
                    this.speed(this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_X), this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_Y)),
                    this.rotation(this.getAnalog(inputSourceID, AnalogTypes.RIGHT_STICK_X)) * scale);
        }else if(this.getAnalog(inputSourceID, AnalogTypes.LEFT_TRIGGER) > 0.2){
            this.mot.drive.move(this.direction(this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_X), this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_Y)),
                    this.speed(this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_X), this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_Y)),
                    this.rotation(this.getAnalog(inputSourceID, AnalogTypes.RIGHT_STICK_X)) * scale / 2);
        }else{
            this.mot.drive.move(this.direction(this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_X), this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_Y)),
                    this.speed(this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_X), this.getAnalog(inputSourceID, AnalogTypes.LEFT_STICK_Y)),
                    this.rotation(this.getAnalog(inputSourceID, AnalogTypes.RIGHT_STICK_X)));
        }
    }

}
