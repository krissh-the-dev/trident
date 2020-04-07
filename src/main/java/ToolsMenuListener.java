
/*
 *  ToolsMenuListener.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.com/KrishnaMoorthy12
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/*
 * (Apache v2) Trident > ToolsMenuListener
 * @author: Krishna Moorthy
 */

class ToolsMenuListener implements ActionListener {
  /*
   * Controls the actions of Tools Menu
   */

  public static boolean isRunning = true;

  public void actionPerformed(ActionEvent e) {
    /*
     * Controls the actions of Tools Menu items
     */
    try {
      switch (e.getActionCommand()) {
        case "Compile":
          TridentCompiler.compile(Trident.path);
          break;

        case "Run":
          TridentCompiler.execute(Trident.path);
          break;

        case "Compile and Run":
          TridentCompiler.compile(Trident.path);
          Thread.sleep(3000);
          TridentCompiler.execute(Trident.path);
          break;

        case "Open Console":
          TridentCompiler.openTerminal();
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
