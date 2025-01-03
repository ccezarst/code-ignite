package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Extra;

import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;

public class PeripheralValuePrinter extends CoreComponent {
    private UI_Manager man;
    private HardwareMap hwMap;
    public PeripheralValuePrinter(Boolean active, TeamCore core) {
        super("PheripheralValuePrinter", active, core, ComponentType.OTHER);
    }
    private boolean show = true;
    @Override
    public void step(TeamCore core) {

        if(show == false){
            ArrayList<String> options = new ArrayList<>();
            options.add("DcMotor");
            options.add("Servo");
            options.add("AccelerationSensor");
            options.add("AnalogInput");
            options.add("ColorSensor");
            options.add("CompassSensor");
            options.add("CRServo");
            this.man.showMenu("Select peripheral type", options, selection ->{
                this.hwMap.forEach(a -> {
                    if (a instanceof DcMotor && selection == 1) {
                        this.man.print(((DcMotor) a).getPortNumber() + " " +this.hwMap.getNamesOf(a) + "-" + ((DcMotor) a).getMode() + "-" + ((DcMotor) a).getZeroPowerBehavior() + "-" + ((DcMotor) a).getDirection() + ": " + ((DcMotor) a).getCurrentPosition() + " . " + ((DcMotor)a).getTargetPosition());
                    }
                    if (a instanceof Servo && selection == 2) {
                        this.man.print(((Servo) a).getPortNumber() + " " + this.hwMap.getNamesOf(a) + "-" + ((Servo) a).getDirection() + ": " + ((Servo) a).getPosition());
                    }
                    if (a instanceof AccelerationSensor && selection == 3) {
                        this.man.print(this.hwMap.getNamesOf(a) + ": " + ((AccelerationSensor) a).getAcceleration());
                    }
                    if (a instanceof AnalogInput && selection == 4) {
                        this.man.print(this.hwMap.getNamesOf(a) + ": " + ((AnalogInput) a).getVoltage() + " . " + ((AnalogInput) a).getMaxVoltage());
                    }
                    if (a instanceof ColorSensor && selection == 5) {
                        this.man.print(this.hwMap.getNamesOf(a) + "-" + ((ColorSensor) a).getI2cAddress() + ": " + ((ColorSensor) a).red() + " . " + ((ColorSensor) a).green() + " . " + ((ColorSensor) a).blue() + " . " + ((ColorSensor) a).alpha());
                    }
                    if (a instanceof CompassSensor && selection == 6) {
                        this.man.print(this.hwMap.getNamesOf(a) + "-" + ((CompassSensor) a).calibrationFailed() +": " + ((CompassSensor) a).getDirection());
                    }
                    if (a instanceof CRServo && selection == 7) {
                        this.man.print(((CRServo) a).getPortNumber() + " " +this.hwMap.getNamesOf(a) + "-" + ((CRServo) a).getDirection() +": " + ((CRServo) a).getPower());
                    }
                });
            });
        }
    }

    @Override
    public void update(TeamCore core) {
        this.man = (UI_Manager) this.core.getComponentFromName("UI_Manager");
        this.hwMap = this.core.getGlobalVariable("HardwareMap", HardwareMap.class);
    }

    @Override
    public int test(TestingEnviromentCore core) {
        return 0;
    }
}
