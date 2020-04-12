package Maps;

import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;

public class UIThemes {
  private static Map<String, String> installedThemes = new HashMap<>();

  private UIThemes() {
    installedThemes.put("System Default", UIManager.getSystemLookAndFeelClassName());
    installedThemes.put("Material", (new MaterialLookAndFeel()).getClass().getName());
    installedThemes.put("Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    installedThemes.put("Metal", "javax.swing.plaf.metal.MetalLookAndFeel");
    installedThemes.put("Nimbus", "javax.swing.plaf.nimbus.NimbusLookAndFeel");
    installedThemes.put("Windows", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
  }

  private static UIThemes SINGLETON;
  private static final Class TAG = UIThemes.class;

  public static UIThemes getInstance() {
    if (SINGLETON == null) {
      SINGLETON = new UIThemes();
    }
    return SINGLETON;
  }

  public static Map getInstalledThemes() {
    return installedThemes;
  }

}
