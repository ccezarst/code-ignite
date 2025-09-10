package org.firstinspires.ftc.teamcode.TeamCore.CustomComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TeamCore.RobotTCore;

import EngineCore.DefaultComponents.ComponentType;
import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;


public class DumbDrive extends CoreComponent {
    public DumbDrive(Boolean active, RobotTCore core) {
        super("DumbDrive", active, core, ComponentType.OTHER);
    }

    @Override
    protected void step(EngineCore c) {
        RobotTCore core = (RobotTCore) c;
        // pune aici codul
        DcMotor FL = core.getHwMap().dcMotor.get(" SCRIE ");
        DcMotor FR = core.getHwMap().dcMotor.get( " SCRIE ");
        DcMotor RR = core.getHwMap().dcMotor.get( " SCRIE ");
        DcMotor RL = core.getHwMap().dcMotor.get( " SCRIE ");
        // binding pe gp1, right joystick
        double joyX = this.core.getGlobalVariable("1RIGHT_STICK_X", double.class);
        double joyY = this.core.getGlobalVariable("1RIGHT_STICK_Y", double.class);

        // de aici scrie ti magia
    }

    @Override
    protected void update(EngineCore core) {

    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
