package org.firstinspires.ftc.teamcode.StageSystem;

public class StageCommunicationLinkLast extends StageCommunicationLink{ // workaround to let the stageList regain control after the last stage
    public StageList nextStageInstance = null;
    public void call(StageCommunicationPacket ... args){
        if(nextStageInstance != null){
            nextStageInstance.restartLoop(args);
        }else{
            throw new NullPointerException("No next stage instance ( StageCommunicationLinkLast -> call ) ");
        }
    }
}
