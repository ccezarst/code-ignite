package org.firstinspires.ftc.teamcode.TeamCore.Drive;

import org.firstinspires.ftc.teamcode.TeamCore.Pathing.BasePath;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.Point;

import EngineCore.DefaultComponents.ComponentType;
import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;

public abstract class PathFetcher extends CoreComponent {
    public PathFetcher(String name, Boolean active, EngineCore core, ComponentType... type) {
        super(name, active, core, type);
    }

    public abstract BasePath fetch(Point start, Point end);
}
