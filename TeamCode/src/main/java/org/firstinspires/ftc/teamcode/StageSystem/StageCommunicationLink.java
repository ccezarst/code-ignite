package org.firstinspires.ftc.teamcode.StageSystem;

public class StageCommunicationLink { // links a packet output to the next ones input -- kindof a workaround :/
    public Stage nextStageInstance = null;
    public void call(StageCommunicationPacket ... args){
        if(nextStageInstance != null){
            nextStageInstance.defaultBehaviourInputPort(args);
        }else{
            throw new NullPointerException("No next stage instance ( StageCommunicationLink -> call ) ");
        }
    }
}
