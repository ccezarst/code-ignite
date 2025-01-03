package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;
import org.firstinspires.ftc.teamcode.hardware.DriveMotors;

import java.util.ArrayList;
import java.util.Objects;

public class HW_DriveMotors extends HardwareInterface {

    public DriveMotors drive;
    public HW_DriveMotors(Boolean active, TeamCore core){
        super("HW_DriveMotors", active, core, InterfaceType.DRIVE_MOTORS);
    }

    @Override
    public void step(TeamCore core) {

    }
    

    @Override
    public void update(TeamCore core) {
        this.drive = new DriveMotors(Objects.requireNonNull(this.core.getGlobalVariable("HardwareMap", HardwareMap.class)));
    }

    @Override
    public int test(TestingEnviromentCore core) {
        // again, this is going to be replaced
        return 0;
    }
}
