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
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.Iterator;

public class PeripheralValuePrinter extends CoreComponent {
    private SW_Telemetry telem;
    private HardwareMap hwMap;
    public PeripheralValuePrinter(String name, Boolean active, DefaultCore core, ComponentType... type) {
        super(name, active, core, type);
    }

    @Override
    public void step(DefaultCore core) {
        this.telem.telemetry.addLine("servo,dcMotor, acc sensor, analogInput, colorSensor, compassSensor, CRServo");
        this.hwMap.forEach(a -> {
            if (a instanceof DcMotor) {
                this.telem.telemetry.addLine(this.hwMap.getNamesOf(a) + "-" + ((DcMotor) a).getZeroPowerBehavior() + "-" + ((DcMotor) a).getDirection() + ": " + ((DcMotor) a).getCurrentPosition() + " . " + ((DcMotor)a).getTargetPosition());
            }
            if (a instanceof Servo) {
                this.telem.telemetry.addLine(this.hwMap.getNamesOf(a) + "-" + ((Servo) a).getDirection() + ": " + ((Servo) a).getPosition());
            }
            if (a instanceof AccelerationSensor) {
                this.telem.telemetry.addLine(this.hwMap.getNamesOf(a) + ": " + ((AccelerationSensor) a).getAcceleration());
            }
            if (a instanceof AnalogInput) {
                this.telem.telemetry.addLine(this.hwMap.getNamesOf(a) + ": " + ((AnalogInput) a).getVoltage() + " . " + ((AnalogInput) a).getMaxVoltage());
            }
            if (a instanceof ColorSensor) {
                this.telem.telemetry.addLine(this.hwMap.getNamesOf(a) + "-" + ((ColorSensor) a).getI2cAddress() + ": " + ((ColorSensor) a).red() + " . " + ((ColorSensor) a).green() + " . " + ((ColorSensor) a).blue() + " . " + ((ColorSensor) a).alpha());
            }
            if (a instanceof CompassSensor) {
                this.telem.telemetry.addLine(this.hwMap.getNamesOf(a) + "-" + ((CompassSensor) a).calibrationFailed() +": " + ((CompassSensor) a).getDirection());
            }
            if (a instanceof CRServo) {
                this.telem.telemetry.addLine(this.hwMap.getNamesOf(a) + "-" + ((CRServo) a).getDirection() +": " + ((CRServo) a).getPower());
            }
        });

    }

    @Override
    public void update(DefaultCore core) {
        this.telem = (SW_Telemetry) this.core.getInterfacesOfType(InterfaceType.TELEMETRY).get(0);
        this.hwMap = ((HW_HwMap) this.core.getInterfacesOfType(InterfaceType.HARDWARE_MAP).get(0)).hwMap;
    }
}
