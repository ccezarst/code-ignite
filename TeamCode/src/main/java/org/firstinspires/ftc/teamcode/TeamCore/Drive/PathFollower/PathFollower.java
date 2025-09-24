package org.firstinspires.ftc.teamcode.TeamCore.Drive.PathFollower;

import org.firstinspires.ftc.teamcode.TeamCore.Drive.MotorConfiguration;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.BasePath;
import java.util.function.Consumer;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.DriveBase;

/**
 * Follows a provided path using a DriveBase.
 */
public interface PathFollower {
    Double[] getMotorPowers(double robotX, double robotY, MotorConfiguration conf);

    boolean reachedTarget();
}
