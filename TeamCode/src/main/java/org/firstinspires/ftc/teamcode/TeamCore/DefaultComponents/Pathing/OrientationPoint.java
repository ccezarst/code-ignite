package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Pathing;

public class OrientationPoint extends Point{
    public double orientation = 0;
    public OrientationPoint(double carthesianX, double carthesianY, double orientation) {
        super(carthesianX, carthesianY);
        this.orientation = orientation;// in radians, counterclockwise from X axis.
    } // same thing as a point but also stores orientation
}
