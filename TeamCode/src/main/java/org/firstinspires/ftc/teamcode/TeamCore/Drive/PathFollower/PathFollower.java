package org.firstinspires.ftc.teamcode.TeamCore.Drive.PathFollower;

import org.firstinspires.ftc.teamcode.TeamCore.Pathing.BasePath;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.DriveBase;

/**
 * Follows a provided path using a DriveBase.
 */
public interface PathFollower {
    void follow(DriveBase drive, BasePath path);
}
