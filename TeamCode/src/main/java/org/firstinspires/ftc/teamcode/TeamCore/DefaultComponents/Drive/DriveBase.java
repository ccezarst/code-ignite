package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Pathing.Point;

public abstract class DriveBase {
    public abstract void moveRobotCentricPolar(double radius, double angle); // move the robot in respect to it's current position radius cm in angle radians direction counteclockwise to the X axis
    public abstract void moveRobotCentricCartesian(double x, double y); // cm, cm

    public final void moveFieldCentricPoint(Point point){
        this.moveFieldCentricPolar(point.getPolarRadius(), point.getPolarAngle());
    };
    public final void moveFieldCentricCartesian(double x, double y){
        this.moveFieldCentricPoint(new Point(x, y));
    };
    public final void moveFieldCentricPolar(double radius, double angle){

    };

    // add pathing commands
}
