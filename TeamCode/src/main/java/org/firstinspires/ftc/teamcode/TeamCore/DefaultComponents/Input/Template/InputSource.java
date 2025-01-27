package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template.InputMapperDefinitions;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

import java.util.ArrayList;

public abstract class InputSource extends CoreComponent {
    public final int id;

    public InputSource(String name, Boolean active, TeamCore core, int id){
        super(name, active,  core, ComponentType.INPUT_SOURCE);
        this.id = id;
    }

}
