package org.firstinspires.ftc.teamcode.TeamCore.Drive.Implementation;

import org.firstinspires.ftc.teamcode.TeamCore.Drive.MotorConfiguration;

public enum DefaultMotorConfigurations implements MotorConfiguration {
    FourWheelMecanum("4WheelMecanum"); // motor order(BasePath-DriveBase-PathFollower): FL, FR, BR, BL;

    public String conf;
    DefaultMotorConfigurations(String conf){
        this.conf = conf;
    }

    @Override
    public String getConfiguration() {
        return conf;
    }
}
