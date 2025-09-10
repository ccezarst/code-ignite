package org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization;

import java.util.ArrayList;
import java.util.Arrays;

public final class LocalizationPacket {

    public static enum Features{
        FIELD_ORIENTATION,
        ACCELERATION,
        SPEED,
        X,
        Y,
        Z
    }

    public double fieldOrientation; // degrees counter-clockwise
    public double acceleration; // m/s²
    public double speed;// m/s

    public double x;
    public double y;
    public double z;

    public double getFeatureValue(Features feat){
        if(feat == Features.FIELD_ORIENTATION){
            return this.fieldOrientation;
        }
        if(feat == Features.ACCELERATION){
            return this.acceleration;
        }
        if(feat == Features.SPEED){
            return this.speed;
        }
        if(feat == Features.X){
            return this.x;
        }
        if(feat == Features.Y){
            return this.y;
        }
        if(feat == Features.Z){
            return this.z;
        }
        return 0;
    }

    public ArrayList<Features> activeFeatures = new ArrayList<>();
    public LocalizationPacket(Features... feats){
        this.fieldOrientation = 0;
        this.acceleration = 0;
        this.speed = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.activeFeatures.clear();
        for(Features feat: feats){
            this.activeFeatures.add(feat);
        }
    }
}
