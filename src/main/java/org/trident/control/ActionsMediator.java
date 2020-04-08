package org.trident.control;

import org.trident.control.actions.AboutMenuListener;
import org.trident.control.listeners.EditMenuListener;
import org.trident.control.listeners.FileMenuListener;
import org.trident.control.listeners.SettingsMenuListener;
import org.trident.control.listeners.ToolsMenuListener;
import org.trident.util.Constant;

import java.awt.event.ActionListener;
import java.util.EnumMap;


public class ActionsMediator {

    private static final ActionsMediator SINGLETON = new ActionsMediator();

    public static ActionsMediator getInstance(){
        return SINGLETON;
    }

    private EnumMap<Constant, ActionListener> actions = new EnumMap<>(Constant.class);

    private ActionsMediator() {
        actions.put(Constant.LISTENER_FILE_MENU, new FileMenuListener());
        actions.put(Constant.LISTENER_EDIT_MENU, new  EditMenuListener());
        actions.put(Constant.LISTENER_SETTIN_MENU, new SettingsMenuListener());
        actions.put(Constant.LISTENER_TOOLS_MENU, new ToolsMenuListener());
        actions.put(Constant.LISTENER_ABOUT_MENU, new AboutMenuListener());
    }

    public ActionListener getAction(Constant constant){
        if(actions.containsKey(constant)){
            return actions.get(constant);
        }
        return null;
    }
}
