package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive;

public interface DriveBase {
    void move(double x, double y, boolean rotateInDirection);
    void rotate(double x, double y);
    void setMaxWheelSpeed(int rpm);
}
