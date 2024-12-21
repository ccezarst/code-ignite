package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Extra;

import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_HwMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.SW_Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;
import java.util.Iterator;

public class PeripheralValuePrinter extends CoreComponent {
    private UI_Manager man;
    private HardwareMap hwMap;
    int selection = 0;
    public PeripheralValuePrinter(Boolean active, DefaultCore core) {
        super("PheripheralValuePrinter", active, core, ComponentType.OTHER);
    }

    @Override
    public void step(DefaultCore core) {
        if(selection == 0){
            ArrayList<String> options = new ArrayList<>();
            options.add("DcMotor");
            options.add("Servo");
            options.add("AccelerationSensor");
            options.add("AnalogInput");
            options.add("ColorSensor");
            options.add("CompassSensor");
            options.add("CRServo");
            this.selection = this.man.showMenu("Select peripheral type", options);
        }else{
            this.hwMap.forEach(a -> {
                if (a instanceof DcMotor && this.selection == 1) {
                    this.man.print(((DcMotor) a).getPortNumber() + " " +this.hwMap.getNamesOf(a) + "-" + ((DcMotor) a).getMode() + "-" + ((DcMotor) a).getZeroPowerBehavior() + "-" + ((DcMotor) a).getDirection() + ": " + ((DcMotor) a).getCurrentPosition() + " . " + ((DcMotor)a).getTargetPosition());
                }
                if (a instanceof Servo && this.selection == 2) {
                    this.man.print(((Servo) a).getPortNumber() + " " + this.hwMap.getNamesOf(a) + "-" + ((Servo) a).getDirection() + ": " + ((Servo) a).getPosition());
                }
                if (a instanceof AccelerationSensor && this.selection == 3) {
                    this.man.print(this.hwMap.getNamesOf(a) + ": " + ((AccelerationSensor) a).getAcceleration());
                }
                if (a instanceof AnalogInput && this.selection == 4) {
                    this.man.print(this.hwMap.getNamesOf(a) + ": " + ((AnalogInput) a).getVoltage() + " . " + ((AnalogInput) a).getMaxVoltage());
                }
                if (a instanceof ColorSensor && this.selection == 5) {
                    this.man.print(this.hwMap.getNamesOf(a) + "-" + ((ColorSensor) a).getI2cAddress() + ": " + ((ColorSensor) a).red() + " . " + ((ColorSensor) a).green() + " . " + ((ColorSensor) a).blue() + " . " + ((ColorSensor) a).alpha());
                }
                if (a instanceof CompassSensor && this.selection == 6) {
                    this.man.print(this.hwMap.getNamesOf(a) + "-" + ((CompassSensor) a).calibrationFailed() +": " + ((CompassSensor) a).getDirection());
                }
                if (a instanceof CRServo && this.selection == 7) {
                    this.man.print(((CRServo) a).getPortNumber() + " " +this.hwMap.getNamesOf(a) + "-" + ((CRServo) a).getDirection() +": " + ((CRServo) a).getPower());
                }
            });
        }
    }

    @Override
    public void update(DefaultCore core) {
        this.man = (UI_Manager) this.core.getComponentFromName("UI_Manager");
        this.hwMap = ((HW_HwMap) this.core.getInterfacesOfType(InterfaceType.HARDWARE_MAP).get(0)).hwMap;
    }
}
