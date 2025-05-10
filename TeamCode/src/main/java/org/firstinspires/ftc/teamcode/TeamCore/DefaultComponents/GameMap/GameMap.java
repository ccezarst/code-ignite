package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap.GameObjects.Defaults.GameObject;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap.GameObjects.Robot;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;

public class GameMap extends CoreComponent {
    private ArrayList<GameObject> objects = new ArrayList<>();

    public GameMap(Boolean active, TeamCore core) {
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
    protected void step(TeamCore core) {

    }

    @Override
    protected void update(TeamCore core) {

    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
