package org.trident.control.listeners;
/*
 *  org.trident.control.listeners.ToolsMenuListener.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.com/KrishnaMoorthy12
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import org.trident.Trident;
import org.trident.exception.UnsupportedFileException;
import org.trident.exception.UnsupportedOperatingSystemException;
import org.trident.model.TridentCompiler;
import org.trident.util.TridentLogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/*
 * (GPL v3) Trident > org.trident.control.listeners.ToolsMenuListener
 * @author: Krishna Moorthy
 */

public class ToolsMenuListener implements ActionListener {
  /*
   * Controls the actions of Tools Menu
   */

  public static boolean isRunning = true;

  public void actionPerformed(ActionEvent e) {
    /*
     * Controls the actions of Tools Menu items
     */
    try {
      String path = Trident.getInstance().getPath();
      switch (e.getActionCommand()) {
        case "Compile":
          TridentCompiler.compile(path);
          break;
        case "Run":
          TridentCompiler.execute(path);
          break;
        case "Compile and Run":
          TridentCompiler.compile(path);
          Thread.sleep(3000);
          TridentCompiler.execute(path);
          break;
        case "Open Console":
          TridentCompiler.openTerminal();
          break;
        default:
      }
    } catch (IOException ioException) {
      TridentLogger.getInstance().error(this.getClass(), "PROCESS_BUILD_FILEIO: " + ioException);
    } catch (UnsupportedOperatingSystemException unOs) {
      TridentLogger.getInstance().error(this.getClass(), "OS_UNSUPPORTED: " + unOs);
    } catch (UnsupportedFileException fileNS) {
      TridentLogger.getInstance().error(this.getClass(), "FILE_UNSUPPORTED: " + fileNS);
    } catch (Exception unknownException) {
      TridentLogger.getInstance().error(this.getClass(), "TOOLS_MENU_CRASH: " + unknownException);
      unknownException.printStackTrace();
    }
  }
}
