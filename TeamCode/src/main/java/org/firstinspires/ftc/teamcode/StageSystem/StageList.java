package org.firstinspires.ftc.teamcode.StageSystem;

import java.util.ArrayList;

public class StageList {
    private ArrayList<Stage> stages = new ArrayList<Stage>();
    public void pushStage(Stage s){
        stages.add(s);
    }

    private void reconnectStages(){
        for(int i = 0; i < stages.size(); i++){
            if(i == stages.size() - 1){ // special case if it's the last stage because the output needs to be routed to this class so the function call chain can be reset
                StageCommunicationLinkLast port = new StageCommunicationLinkLast();
                port.nextStageInstance = this;
                stages.get(i).outPort = port;
            }else{// first and intermediary stages here
                StageCommunicationLink port = new StageCommunicationLink();
                port.nextStageInstance = stages.get(i + 1);
                stages.get(i).outPort = port;
            }
        }
    }

    private StageCommunicationPacket[] restartLoopTempList = {};

    // we can let the main function call this every time :/
    public void runOneStageIteration(){
        this.stages.get(0).inputPort(this.restartLoopTempList);
    }

    public void restartLoop(StageCommunicationPacket ... packets){
        this.restartLoopTempList = packets;
        // this saves the remaining communication packets to memory and returns, effectively freeing the call chain
    }

    public boolean removeStage(String stageName){
        for(int i = 0; i < this.stages.size(); i++){
            if(this.stages.get(i).stageName == stageName){
                stages.remove(i);
                this.reconnectStages();
                return true;
            }
        }
        throw new IllegalArgumentException("No stage found with specied Stage Name ( StageList-> removeStage )");
    }

    public void pushStageAfterStage(Stage newStage, String previousStageName){
        for(int i = 0; i < this.stages.size(); i++){
            if(this.stages.get(i).stageName == previousStageName){
                this.stages.add(i + 1, newStage);
                this.reconnectStages();
                return;
            }
        }
        throw new IllegalArgumentException("No stage found with specied Stage Name ( StageList-> pushStageAfterStage )");
    }
    public void pushStageBeforeStage(Stage newStage, String previousStageName){
        for(int i = 0; i < this.stages.size(); i++){
            if(this.stages.get(i).stageName == previousStageName){
                this.stages.add(i - 1, newStage);
                this.reconnectStages();
                return;
            }
        }
        throw new IllegalArgumentException("No stage found with specied Stage Name ( StageList-> pushStageBeforeStage )");
    }

    public void activateStage(String stageName){
        for(int i = 0; i < this.stages.size(); i++){
            if(this.stages.get(i).stageName == stageName){
                stages.get(i).setActive(true);
                this.reconnectStages();
            }
        }
        throw new IllegalArgumentException("No stage found with specied Stage Name ( StageList-> activateStage )");
    }
    public void deactivateStage(String stageName){
        for(int i = 0; i < this.stages.size(); i++){
            if(this.stages.get(i).stageName == stageName){
                stages.get(i).setActive(false);
                this.reconnectStages();
            }
        }
        throw new IllegalArgumentException("No stage found with specied Stage Name ( StageList-> activateStage )");
    }
    public void toggleStageActive(String stageName){
        for(int i = 0; i < this.stages.size(); i++){
            if(this.stages.get(i).stageName == stageName){
                stages.get(i).toggleActive();
                this.reconnectStages();
            }
        }
        throw new IllegalArgumentException("No stage found with specied Stage Name ( StageList-> activateStage )");
    }
}
