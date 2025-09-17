package org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Configs;

import com.qualcomm.robotcore.util.ReadWriteFile;
import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.TeamCore.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template.SoftwareInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JSONConfigManager extends SoftwareInterface implements ConfigsInterface {

    private final String configFileName;
    private final ArrayList<String[]> configQueue = new ArrayList<String[]>();
    private volatile boolean isInited = false;
    private volatile boolean waitingForSetting = false;

    public JSONConfigManager(Boolean active, EngineCore core) {
        this(active, core, "CUSTOM_CODE_CONFIGS.json");
    }

    public JSONConfigManager(Boolean active, EngineCore core, String configFileName) {
        super("JSONConfigManager", active, core, InterfaceType.CONFIGS);
        this.configFileName = configFileName;

        // Print file location on startup
        File f = getSettingsFile();
        System.out.println("[JSONConfigManager] Using config file: " + f.getAbsolutePath());
    }

    // ---------- helpers ----------
    private File getSettingsFile() {
        return AppUtil.getInstance().getSettingsFile(configFileName);
    }

    private void ensureConfigFile() {
        File f = getSettingsFile();

        // Ensure parent directory exists
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) {
            //noinspection ResultOfMethodCallIgnored
            parent.mkdirs();
        }

        // If file missing or empty, seed with {}
        if (!f.exists()) {
            ReadWriteFile.writeFile(f, "{}");
            return;
        }
        try {
            String content = ReadWriteFile.readFile(f);
            if (content == null || content.trim().isEmpty()) {
                ReadWriteFile.writeFile(f, "{}");
            }
        } catch (Exception e) {
            ReadWriteFile.writeFile(f, "{}");
        }
    }

    private JSONObject readJson() {
        ensureConfigFile();
        try {
            String raw = ReadWriteFile.readFile(getSettingsFile());
            if (raw == null || raw.trim().isEmpty()) raw = "{}";
            return new JSONObject(raw);
        } catch (Exception e) {
            ReadWriteFile.writeFile(getSettingsFile(), "{}");
            return new JSONObject();
        }
    }

    private void writeJson(JSONObject obj) {
        try {
            ReadWriteFile.writeFile(getSettingsFile(), obj.toString(2));
        } catch (JSONException e) {
            // If JSON serialization fails, fall back to a compact version
            ReadWriteFile.writeFile(getSettingsFile(), obj.toString());
        }
    }

    /** Flushes any queued key/value pairs to disk immediately. */
    private void flushQueue() {
        ArrayList<String[]> batch = null;
        synchronized (this.configQueue) {
            if (this.configQueue.isEmpty()) return;
            batch = new ArrayList<String[]>(this.configQueue);
            this.configQueue.clear();
        }
        JSONObject obj = readJson();
        try {
            for (String[] pair : batch) {
                obj.put(pair[0], pair[1]);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        writeJson(obj);
    }

    // ---------- API ----------
    @Override
    public String loadValue(String valueName) {
        if (!this.active) return "";
        JSONObject obj = readJson();
        return obj.optString(valueName, "");
    }

    @Override
    public void saveValue(String valueName, String value) {
        if (!this.active) return;
        synchronized (this.configQueue) {
            configQueue.add(new String[]{valueName, value});
        }
    }

    @Override
    public void step(EngineCore core) {
        if (!this.active) return;

        if (!isInited) {
            List<CoreComponent> components = this.core.getAllComponents();
            for (CoreComponent comp : components) {
                List<String> settings = comp.getAllSettings();
                for (String setting : settings) {
                    String key = comp.name + "." + setting;
                    String val = this.loadValue(key);

                    if (val.isEmpty()) {
                        this.requestSetting(comp.name, setting);
                    } else {
                        boolean ok = this.core.getComponentFromName(comp.name)
                                .changeSetting(setting, val);
                        if (!ok) {
                            this.requestSetting(comp.name, setting);
                        }
                    }
                }
            }
            isInited = true;
            return;
        }

        // Flush any queued changes (e.g., from saveValue calls elsewhere)
        flushQueue();
    }

    private void requestSetting(final String compName, final String settingName) {
        System.out.println(this.name + " -> Requested setting: " + settingName);
        UI_Manager man = this.core.getComponentFromName("UI_Manager", UI_Manager.class);
        if (man == null) return;

        waitingForSetting = true;
        man.showMenu(
                compName + " -> " + settingName,
                this.core.getComponentFromName(compName).getSettingOptions(settingName),
                opt -> {
                    if (opt == -1) {
                        requestSetting(compName, settingName);
                        return;
                    }
                    handleSettingRequest(compName, settingName, opt.intValue());
                }
        );

        // Gentle wait (component thread only)
        while (waitingForSetting) {
            try { Thread.sleep(5); } catch (InterruptedException ignored) {}
        }
    }

    private void handleSettingRequest(String compName, String settingName, int opt) {
        CoreComponent comp = this.core.getComponentFromName(compName);
        List<String> options = comp.getSettingOptions(settingName);
        if (opt >= 0 && opt < options.size()) {
            // 1) Apply to component
            String chosen = options.get(opt);
            comp.changeSetting(settingName, chosen);

            // 2) Persist immediately
            String key = comp.name + "." + settingName;
            saveValue(key, chosen);
            flushQueue(); // ensure it's written to disk right now
        }
        waitingForSetting = false;
    }

    @Override
    public void update(EngineCore core) {
        this.isInited = false;
    }

    @Override
    public int test(TestingEnviromentCore core) {
        JSONConfigManager testComp = new JSONConfigManager(true, core, "testConfig.json");
        core.addComponent(testComp);
        core.init();
        testComp.saveValue("testValueName", "testValue");
        testComp.step(core);
        String loaded = testComp.loadValue("testValueName");
        return "testValue".equals(loaded) ? 0 : 1;
    }
}
