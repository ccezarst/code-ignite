package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap.GameObjects.Defaults;

public abstract class GameObject {
    public String name;
    public GameObjectType type;
    public boolean collision;

    public GameObject(String name, GameObjectType type, boolean collision){
        this.name = name;
        this.type = type;
        this.collision = collision;
    }
}
