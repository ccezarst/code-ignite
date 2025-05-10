package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive.Localization;

public final class LocalizationPacket {
    public float fieldOrientation; // degrees counter-clockwise
    public float acceleration; // m/s²
    public float speed;// m/s
    public LocalizationPacket(){
        this.fieldOrientation = 0;
        this.acceleration = 0;
        this.speed = 0;
    }
}
