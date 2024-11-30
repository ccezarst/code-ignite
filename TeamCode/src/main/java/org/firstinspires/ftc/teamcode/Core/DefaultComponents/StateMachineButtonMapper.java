package org.firstinspires.ftc.teamcode.Core.DefaultComponents;

import org.opencv.core.Core;

public class StateMachineButtonMapper extends ButtonMapper {
    public StateMachineButtonMapper(String name, Boolean active){
        // mapping is button-
        super(name, active); // no need to specify type as this extends button mapper which sets the type in it's constructor
    }

}
