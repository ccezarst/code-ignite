package org.firstinspires.ftc.teamcode.CustomComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

public class DumbDrive extends CoreComponent {
    public DumbDrive(Boolean active, TeamCore core) {
        super("DumbDrive", active, core, ComponentType.OTHER);
    }

    @Override
    protected void step(TeamCore core) {
        // pune aici codul
        DcMotor FL = this.getHwMap().dcMotor.get(" SCRIE ");
        DcMotor FR = this.getHwMap().dcMotor.get( " SCRIE ");
        DcMotor RR = this.getHwMap().dcMotor.get( " SCRIE ");
        DcMotor RL = this.getHwMap().dcMotor.get( " SCRIE ");
        // binding pe gp1, right joystick
        double joyX = this.core.getGlobalVariable("1RIGHT_STICK_X", double.class);
        double joyY = this.core.getGlobalVariable("1RIGHT_STICK_Y", double.class);

        // de aici scrie ti magia
    }

    @Override
    protected void update(TeamCore core) {

    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
