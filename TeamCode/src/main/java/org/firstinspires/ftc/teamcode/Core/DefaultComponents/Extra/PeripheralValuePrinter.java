package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Extra;

import com.qualcomm.robotcore.hardware.DcMotor;
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
        this.telem.telemetry.addLine("servo,dcMotor");
        Iterator<Servo> servoList = this.hwMap.servo.iterator();
        this.hwMap.forEach(a -> {
            if (a instanceof DcMotor) {
                this.telem.telemetry.addLine(a.getDeviceName() + "-" + ((DcMotor) a).getTargetPosition() + "-" + ((DcMotor) a).getZeroPowerBehavior() + "-" + ((DcMotor) a).getDirection() + ": " + ((DcMotor) a).getCurrentPosition());
            }
        });

    }

    @Override
    public void update(DefaultCore core) {
        this.telem = (SW_Telemetry) this.core.getInterfacesOfType(InterfaceType.TELEMETRY).get(0);
        this.hwMap = ((HW_HwMap) this.core.getInterfacesOfType(InterfaceType.HARDWARE_MAP).get(0)).hwMap;
    }
}
