package org.firstinspires.ftc.teamcode.StageSystem;

import java.util.ArrayList;

public class StageManager {
    // basically a wrapper to hide all the ugly code
    private StageList stages;
    public boolean pushStage(Stage stage){
        this.stages.pushStage(stage);
        return true;
    }

    public void addStage(Stage s){
        this.stages.pushStage(s);
    }

    public void removeStage(Stage stageInstance){
        this.stages.removeStage(stageInstance.stageName);
    }
    public void removeStage(String stageName){
        this.stages.removeStage(stageName);
    }

    public void activateStage(Stage stageInstance){
        this.stages.activateStage(stageInstance.stageName);
    }
    public void activateStage(String stageName){
        this.stages.activateStage(stageName);
    }

    public void deactivateStage(Stage stageInstance){
        this.stages.deactivateStage(stageInstance.stageName);
    }
    public void deactivateStage(String stageName){
        this.stages.deactivateStage(stageName);
    }

    public void toggleStageActive(Stage stageInstance){
        this.stages.toggleStageActive(stageInstance.stageName);
    }
    public void toggleStageActive(String stageName){
        this.stages.toggleStageActive(stageName);
    }

    public void runOneStagesIteration(){
        this.stages.runOneStageIteration();
    }
}
