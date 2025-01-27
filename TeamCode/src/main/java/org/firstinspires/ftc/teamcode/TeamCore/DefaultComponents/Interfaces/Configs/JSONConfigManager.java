package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Configs;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;
import org.json.JSONException;
import org.json.JSONObject;

import com.qualcomm.robotcore.util.ReadWriteFile;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;

public class JSONConfigManager extends SoftwareInterface implements ConfigsInterface {
    private final String configFileName ;
    public JSONConfigManager(Boolean active, TeamCore core) {
        this( active, core, "IGNITE_CUSTOM_CODE_CONFIGS.json");
    }
    public JSONConfigManager(Boolean active, TeamCore core, String configFileName) {
        super("JSONConfigManager", active, core, InterfaceType.CONFIGS);
        this.configFileName = configFileName;
    }

    private ArrayList<String[]> configQueue = new ArrayList<>();

    @Override
    public String loadValue(String valueName) {
        if(this.active){
            File myFileName = AppUtil.getInstance().getSettingsFile(configFileName);
            if(ReadWriteFile.readFile(myFileName).trim() != ""){
                try {
                    JSONObject  obj = new JSONObject(ReadWriteFile.readFile(myFileName));
                    if(obj.has(valueName)){
                        return (String) obj.get(valueName);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return "";
    }

    @Override
    public void saveValue(String valueName, String value) {
        if(this.active){
            // queue to batch up fileWrite requets and process them at the end
            configQueue.add(new String[]{valueName, value});
        }
    }

    @Override
    public void step(TeamCore core) {
        if(!configQueue.isEmpty()){ // if we have anything to save
            File myFileName = AppUtil.getInstance().getSettingsFile(configFileName);
            String result = "";
            if(ReadWriteFile.readFile(myFileName).trim() == ""){
                JSONObject jo = new JSONObject();
                try {
                    for(String[] pair: this.configQueue){
                        jo.put(pair[0], pair[1]); // process first batch of configs after file creation
                    }
                    result = jo.toString(2);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    JSONObject  obj = new JSONObject(ReadWriteFile.readFile(myFileName));
                    for(String[] pair: this.configQueue){
                        obj.put(pair[0], pair[1]); // process a batch of configs
                    }
                    result = obj.toString(2);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            // https://ftc-docs.firstinspires.org/en/latest/programming_resources/shared/myblocks/rw_example/rw-example.html
            // https://www.geeksforgeeks.org/parse-json-java/
            if(result != ""){
                ReadWriteFile.writeFile(myFileName, result);
            }
        }
    }

    @Override
    public void update(TeamCore core) {

    }

    @Override
    public int test(TestingEnviromentCore core) {
        JSONConfigManager testComp = new JSONConfigManager(true, core, "testConfig");
        core.addComponent(testComp);
        core.init();
        testComp.saveValue("testValueName", "testValue");
        if(testComp.loadValue("testValueName") != "testValue"){
            return 1;
        }
        return 0;
    }
}
