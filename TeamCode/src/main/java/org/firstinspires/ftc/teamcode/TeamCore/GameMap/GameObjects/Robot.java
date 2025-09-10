package org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects;

import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects.Defaults.DynamicGameObject;
import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects.Defaults.GameObjectType;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.OrientationPoint;

public class Robot extends DynamicGameObject {
    public OrientationPoint center = new OrientationPoint(0,0, 0);
    public double speed = 0;
    public Robot(String name) {
        super(name, GameObjectType.Robot,true);
    }
    // add shape etc..
}
