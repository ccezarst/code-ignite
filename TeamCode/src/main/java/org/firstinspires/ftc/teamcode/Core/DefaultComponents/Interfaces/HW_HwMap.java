package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public class HW_HwMap extends HardwareInterface {
    public final HardwareMap hwMap;
    public HW_HwMap(Boolean active, DefaultCore core, HardwareMap hwMap){
        super("HW_HwMap", active, core, InterfaceType.HARDWARE_MAP);
        this.hwMap = hwMap;
    }

    @Override
    public void step(DefaultCore core) {

    }

    @Override
    public void update(DefaultCore core) {

    }
}
