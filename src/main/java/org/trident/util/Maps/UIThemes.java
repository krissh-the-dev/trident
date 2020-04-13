package Maps;

import mdlaf.MaterialLookAndFeel;

import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;

/**
 * Errors:
 * - The package is wrong, there are rules:
 * - NO upper case
 * - No undescor
 * - all world like your.package.iswrong
 * - If you don't know, read about it before to write code
 */

public class UIThemes {
  private static Map<String, String> installedThemes = new HashMap<>();

  private UIThemes() {
    /**
     * This idea is good but you don't manager the material theming
     * With UIManager.getLookAndFeel() you have all L&F, don't have need to develop a map
     */
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

  /**
   * With this method is static?
   * - The static method is very bad in Java
   * Thi method have the same your result UIManager.getLookAndFeel()
   * @return
   */

  public static Map getInstalledThemes() {
    return installedThemes;
  }

}
