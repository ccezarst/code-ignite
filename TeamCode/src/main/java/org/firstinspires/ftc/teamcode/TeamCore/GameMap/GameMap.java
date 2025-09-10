package org.firstinspires.ftc.teamcode.TeamCore.GameMap;

import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects.Defaults.GameObject;
import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects.Robot;

import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.DefaultComponents.ComponentType;
import EngineCore.TestingEnviromentCore;

import java.util.ArrayList;

public class GameMap extends CoreComponent {
    private ArrayList<GameObject> objects = new ArrayList<>();

    public GameMap(Boolean active, EngineCore core) {
        super("GameMap", active, core, ComponentType.GAME_MAP);
        this.objects.add(new Robot("self"));
    }

    public GameObject getObjectByName(String name){
        for(GameObject caca : objects){
            if(caca.name == name){
                return caca;
            }
        }
        return null;
    }

    public Robot getRobotSelf(){ // a representation of the physical robot
        return (Robot)this.getObjectByName("self");
    }

    @Override
    protected void step(EngineCore core) {

    }

    @Override
    protected void update(EngineCore core) {

    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
