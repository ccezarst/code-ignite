package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template;

import java.util.HashMap;
import java.util.Map;


// decided to use a class because we can pass it by refrence,
// thus saving time and resources when sending it from inputSource to inputMapper
public final class InputSetStates {
    private Map<ButtonTypes, Boolean> buttonStates = new HashMap<>();
    private Map<AnalogTypes, Double> analogValues = new HashMap<>();

    public InputSetStates (){
        for(ButtonTypes type: ButtonTypes.values()){
            this.buttonStates.put(type, false);
        }
        for(AnalogTypes type: AnalogTypes.values()){
            this.analogValues.put(type, 0.0);
        }
    }

    public final void updateButtonState(Boolean newS, ButtonTypes btn){
        this.buttonStates.remove(btn);
        this.buttonStates.put(btn, newS);
    }

    public final void updateAnalogValue(Double newV, AnalogTypes btn){
        this.analogValues.remove(btn);
        this.analogValues.put(btn, newV);
    }

    public final Boolean getButtonState(ButtonTypes button){
        return this.buttonStates.get(button);
    }

    public final Double getAnalogValue(AnalogTypes an){
        return this.analogValues.get(an);
    }

    public final Map<ButtonTypes, Boolean> getAllButtonStates(){
        return this.buttonStates;
    }
    public final Map<AnalogTypes, Double> getAllAnalogValues(){
        return this.analogValues;
    }
}
