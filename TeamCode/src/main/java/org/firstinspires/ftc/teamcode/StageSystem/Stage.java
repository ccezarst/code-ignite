package org.firstinspires.ftc.teamcode.StageSystem;

public class Stage {

    public Stage(String name){
        if (!name.isEmpty()) {
            this.stageName = name;
        }else {
            // TODO: implement global exception handler stage
            // "Use Thread.setDefaultUncaughtExceptionHandler. See Rod Hilton's "Global Exception Handling" blog post for an example."
            // de pe stack overflow
            throw new IllegalArgumentException("No name passed to Stage constructor ( Stage->constructor )");
        }
    }

    private boolean active = false;
    public String stageName = "";
    public StageCommunicationLink outPort;
    public final void setActive(boolean n){
        this.active = n;
    }

    public final void toggleActive(){
        this.active = !this.active;
    }
    public final boolean isActive(){
        return this.active;
    }

    public void stageInit(){}
    public void stageExit(){}
    // if this stage isn't active just send the packets directly to the next stage
    public final void defaultBehaviourInputPort(StageCommunicationPacket ... packets){
        if(this.active){
            this.inputPort(packets);
        }else{
            this.outPort.call(packets);
        }
    }
    public void inputPort(StageCommunicationPacket ... packets){}
}
