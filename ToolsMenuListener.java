
/*
 *  ToolsMenuListener.java
 *  (c) Copyright, 2019 - 2020 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.io/KrishnaMoorthy12
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/*
 * (GPL v3) Trident > ToolsMenuListener
 * @author: Krishna Moorthy
 */

class ToolsMenuListener implements ActionListener {
  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {
      case "Compile":
        TridentCompiler.compile(Trident.path);
        Trident.status1.setText("Compilation ended.");
        break;

      case "Run":
        TridentCompiler.execute(Trident.path);
        Trident.status1.setText("Execution window deployed.");
        break;

      case "Compile and Run":
        TridentCompiler.compile(Trident.path);
        Trident.status1.setText("Compilation ended.");
        Thread.sleep(3000);
        TridentCompiler.execute(Trident.path);
        Trident.status1.setText("Execution window deployed.");
        break;

      case "Open Console":
        TridentCompiler.openTerminal(Trident.checkOS());
        break;
      }
    } catch (IOException ioException) {
      Trident.ErrorDialog("PROCESS_BUILD_FILEIO", ioException);
    } catch (UnsupportedOperatingSystemException unOs) {
      Trident.ErrorDialog("OS_UNSUPPORTED", unOs);
    } catch (UnsupportedFileException fileNS) {
      Trident.ErrorDialog("FILE_UNSUPPORTED", fileNS);
    } catch (Exception unknownException) {
      unknownException.printStackTrace();
      Trident.ErrorDialog("TOOLS_MENU_CRASH", unknownException);
    }
  }
}
