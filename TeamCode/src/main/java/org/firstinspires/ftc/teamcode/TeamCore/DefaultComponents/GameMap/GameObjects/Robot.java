package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap.GameObjects;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap.GameObjects.Defaults.DynamicGameObject;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap.GameObjects.Defaults.GameObjectType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Pathing.OrientationPoint;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Pathing.Point;

public class Robot extends DynamicGameObject {
    public OrientationPoint center = new OrientationPoint(0,0, 0);
    public double speed = 0;
    public Robot(String name) {
        super(name, GameObjectType.Robot,true);
    }
    // add shape etc..
}
