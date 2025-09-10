package org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects;


import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects.Defaults.GameObjectType;
import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects.Defaults.StaticGameObject;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.Point;

public class GamePoint extends StaticGameObject {

    private Point pathingPoint;
    public GamePoint(String name, double x, double y) {
        super(name, GameObjectType.Point, false);
        this.pathingPoint = new Point(x, y);
    }
}
