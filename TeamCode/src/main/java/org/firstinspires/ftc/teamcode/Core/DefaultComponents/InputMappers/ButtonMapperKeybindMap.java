package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.Map;

public class ButtonMapperKeybindMap {
    public final int gamepadNr;
    public final Map<Gamepad.Button, String> keybinds;
    public ButtonMapperKeybindMap(int caca, Map<Gamepad.Button, String> bt){
        this.gamepadNr = caca;
        this.keybinds = bt;
    }
}
