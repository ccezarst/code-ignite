package org.firstinspires.ftc.teamcode.TeamCore.Drive.Implementation;

import org.firstinspires.ftc.teamcode.TeamCore.Drive.DrivingManager;

import EngineCore.DefaultComponents.ComponentType;
import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;
public class ManualDriving extends CoreComponent {
    public ManualDriving(Boolean active, EngineCore core) {
        super("ManualDriving", active, core, ComponentType.OTHER);
    }

    @Override
    protected void step(EngineCore core) {
        man.moveRobotCentricCartesianNoPathing(-this.core.getGlobalVariable("1RIGHT_STICK_X", Double.class), -this.core.getGlobalVariable("1RIGHT_STICK_Y", Double.class));
    }
    DrivingManager man;
    @Override
    protected void update(EngineCore core) {
        man = this.core.getComponentFromName("DrivingManager", DrivingManager.class);
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
