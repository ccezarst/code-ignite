package org.firstinspires.ftc.teamcode.TeamCore;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.Actions.Action;
import org.firstinspires.ftc.teamcode.TeamCore.Actions.ActionDataContainer;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GlobalVariableContainer;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.SW_Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.Interface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.UI_Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class TeamCore {
    private ArrayList<CoreComponent> components = new ArrayList<CoreComponent>();

    private Map<String, GlobalVariableContainer> globalVariables;

    private ArrayList<Action> actions;

    private boolean logInteractions = false;

    public TeamCore(Telemetry telem, HardwareMap hwMap){
        this.superSecretFunc(telem, hwMap);
    }

    public final void superSecretFunc(Telemetry telem, HardwareMap hwMap){
        this.globalVariables = new HashMap<>();
        // didn't know what to name it to not have it confused
        this.addComponent(new UI_Manager(true, this));
        this.addComponent(new SW_Telemetry(true, this));
        if(telem != null){
            //this.addComponent(new SW_Telemetry(true, this, telem));
            this.setGlobalVariable("Telemetry", telem);
        }
        if(hwMap != null){
            //this.addComponent(new HW_HwMap(true, this, hwMap));
            this.setGlobalVariable("HardwareMap", hwMap);
        }
    }

    public final <T> void addAction(String actionName, T actionDataType, Consumer<ActionDataContainer>... defaultCallbacks){
        if(this.getActionFromName(actionName) == null){
            this.actions.add(new Action<T>(actionName, actionDataType, defaultCallbacks));
        }
    }
    public final Action getActionFromName(String name){
        for(Action pl: this.actions){
            if(pl.name == name){
                return pl;
            }
        }
        return null;
    }
    public final void subscribeToAction(String actionName, Consumer<ActionDataContainer> callback){
        Action res = this.getActionFromName(actionName);
        if(res != null){
            res.subscribe(callback);
        }
    }

    public final void connectActions(String actionA, String actionB){
        Action firstAction = this.getActionFromName(actionA);
        Action secondAction = this.getActionFromName(actionB);
        if(firstAction.getContainer().getValue().getClass() == secondAction.getContainer().getValue().getClass()){
            firstAction.subscribe((ActionDataContainer) -> {
                secondAction.trigger((org.firstinspires.ftc.teamcode.TeamCore.Actions.ActionDataContainer) ActionDataContainer);
            });
        }else{
            firstAction.subscribe((ActionDataContainer) -> {
                secondAction.trigger();
            });
        }
    }

    public final <T> void setGlobalVariable(String name, T instance){
        if(this.globalVariables.containsKey(name)){
            this.globalVariables.get(name).value = instance;
        }else{
            this.globalVariables.put(name, new GlobalVariableContainer(instance));
        }
    }

    public final <T> T getGlobalVariable(String name, Class<T> caster){
        if(this.globalVariables.containsKey(name)){
            return caster.cast(Objects.requireNonNull(this.globalVariables.get(name)).value);
        }else{
            //throw new RuntimeException("Failed to find variable");
            return null;
        }
    }

    public final void activateInteractionLogging(){
        this.logInteractions = true;
    }

    public final void disableInteractionLogging(){
        this.logInteractions = false;
    }

    public void logInteraction(String message){}

    private String compsToString(ArrayList<CoreComponent> in){
        String res = "";
        for(CoreComponent comp:  in){
            res += comp.name;
        }
        return res;
    }
    public final void reorderComponents(){
        ArrayList<String> history = new ArrayList<>();
        while(true){
            for(CoreComponent comp : this.components){
                for(CoreComponent mata : this.components){
                    if(this.components.indexOf(comp) < this.components.indexOf(mata)){
                        for(ComponentType tip : comp.dependencies){
                            for(ComponentType mataType: mata.types){
                                if(mataType.name() == tip.name()){
                                    this.components.remove(mata);
                                    this.components.add(this.components.indexOf(comp), mata);
                                    if(history.contains(this.components)){
                                        throw new IllegalArgumentException("CoreComponents circular dependency, check dependencies");
                                    }else{
                                        history.add(this.compsToString(this.components));
                                    }
                                    continue; // DO NOT REMOVE.
                                }
                            }
                        }
                    }
                }
            }
            break;
        }
    }

    public final void removeComponent(String name){
        if(this.logInteractions){
            this.logInteraction("Removed component: " + name);
        }
        this.components.remove(this.getComponentFromName(name));
        this.reorderComponents();
    }

    public final void addComponent(CoreComponent comp){
        if(this.getComponentFromName(comp.name) == null){
            if(this.logInteractions){
                this.logInteraction("Added component: " + comp.name);
            }
            this.components.add(comp);
            this.reorderComponents();
        }else{
            throw new IllegalArgumentException("CoreComponent already exists with the name " + comp.name);
        }
    }
    @Nullable
    public final CoreComponent getComponentFromName(String name){
        for(int i = 0; i < this.components.size(); i++){
            if(Objects.equals(this.components.get(i).name, name)){
                if(this.logInteractions){
                    this.logInteraction("Returned component from name: " + name);
                }
                return this.components.get(i);
            }
        }
        if(this.logInteractions){
            this.logInteraction("Failed to return component from name(name not found): " + name);
        }
        return null;
    }
    @Nullable
    public final <T> T getComponentFromName(String name, Class<? extends T> caster){
        for(int i = 0; i < this.components.size(); i++){
            if(Objects.equals(this.components.get(i).name, name)){
                if(this.logInteractions){
                    this.logInteraction("Returned component from name: " + name);
                }
                return caster.cast(this.components.get(i));
            }
        }
        if(this.logInteractions){
            this.logInteraction("Failed to return component from name(name not found): " + name);
        }
        return null;
    }

    public final ArrayList<CoreComponent> getAllComponents(){
        if(this.logInteractions){
            this.logInteraction("Returned all components");
        }
        return this.components;
    }

    public final void wipeComponents(){
        this.components.clear();
    }

    private final boolean containsCompType(ComponentType[] list, ComponentType type){
        for(int i = 0; i < list.length; i++){
            if(list[i].name().equals(type.name())){
                return true;
            }
        }
        return false;
    }

    public final ArrayList<CoreComponent> getComponentsOfType(ComponentType type){
        ArrayList<CoreComponent> toReturn = new ArrayList<CoreComponent>();

        for(int i =0; i< this.components.size(); i++){
            if(this.containsCompType(this.components.get(i).types, type)){
                toReturn.add(this.components.get(i));
            }
        }
        if(this.logInteractions){
            this.logInteraction("Gotten components of type: " + type.name());
        }
        return toReturn;
    }

    public final <T> ArrayList<T> getComponentsOfType(ComponentType type, Class<? extends T> caster){
        ArrayList<T> toReturn = new ArrayList<T>();

        for(int i =0; i< this.components.size(); i++){
            if(this.containsCompType(this.components.get(i).types, type)){
                toReturn.add(caster.cast(this.components.get(i)));
            }
        }
        if(this.logInteractions){
            this.logInteraction("Gotten components of type: " + type.name());
        }
        return toReturn;
    }

    public final ArrayList<Interface> getInterfacesOfType(InterfaceType type){
        ArrayList<CoreComponent> interfs = this.getComponentsOfType(ComponentType.INTERFACE);
        ArrayList<Interface> toReturn = new ArrayList<>();
        for(int i = 0; i < interfs.size(); i++){
            if(((Interface)(interfs.get(i))).interfaceType == type){
                toReturn.add((Interface) interfs.get(i));
            }
        }
        if(this.logInteractions){
            this.logInteraction("Gotten interfaces of type: " + type.name());
        }
        return toReturn;
    }

    public final <T> ArrayList<T> getInterfacesOfType(InterfaceType type, Class<? extends T> caster){
        ArrayList<CoreComponent> interfs = this.getComponentsOfType(ComponentType.INTERFACE);
        ArrayList<T> toReturn = new ArrayList<>();
        for(int i = 0; i < interfs.size(); i++){
            if(((Interface)(interfs.get(i))).interfaceType == type){
                toReturn.add(caster.cast(interfs.get(i)));
            }
        }
        if(this.logInteractions){
            this.logInteraction("Gotten interfaces of type: " + type.name());
        }
        return toReturn;
    }

    public void init(){this.update();} // the same

    public void update(){
        if(this.logInteractions){
            this.logInteraction("Core updated");
        }
        for(int i = 0; i < this.components.size(); i++){
            this.components.get(i).primitiveUpdate(this);
        }
        this.reorderComponents(); // just to be safe
    }

    public void step(){
        if(this.logInteractions){
            this.logInteraction("Core stepped");
        }
        for(int i = 0; i < this.components.size(); i++){
            // primitive step to help not accidentally run components
            // that don't check if they should be active or not
            this.components.get(i).primitiveStep(this);
        }
        ((UI_Manager)this.getComponentFromName("UI_Manager")).refresh();
    }

    public String getStatus(){
        String toReturn = "";
        if(this.logInteractions){
            this.logInteraction("Core status requested");
        }
        for(int i = 0; i < this.components.size(); i ++){
            ArrayList<String> status = this.components.get(i).primitiveGetStatus();
            String temp = "-";
            if(status != null){
                for(int b = 0; b < status.size(); b++) {
                    temp += Arrays.toString(this.components.get(i).types) + "-" + this.components.get(i).name + ": " + status.get(b) + "\n";
                }
            }

            toReturn += temp;
        }
        return toReturn;
    }

    public void exit(){
        for(CoreComponent comp : this.components){
            comp.primitiveExit();
        }
    }

    public ArrayList<String> testComponents(){
        // make new TestingEnviroment
        TestingEnviromentCore env = new TestingEnviromentCore(this.getGlobalVariable("HardwareMap", HardwareMap.class));
        env.init();
        for(CoreComponent comp : this.components){
            env.setCurrentComponentName(comp.name);
            env.logInteraction("Component returned: " + comp.primitiveTest(env));
            env.reset();
        }
        return env.logs;
    }
}
