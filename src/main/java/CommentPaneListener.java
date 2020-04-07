/*
 *  CommentPaneListener.java
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

/*
 * [Apache v2] Trident > CommentPaneListener
 * @author: Krishna Moorthy
 */

class CommentPaneListener extends Thread {
  /*
   * This thread keeps running in the background to keep the status area 1 i.e the
   * comment area to be meaningful
   * 
   * started when status bar is initialized
   */

  public static boolean isRunning = true;

  @Override
  public void run() {
    try {
      while (true) {
        Trident.status1.setText("Ready.");
        Thread.sleep(20000);
      }
    } catch (InterruptedException died) {
      isRunning = false;
      Trident.ErrorDialog("STATUS_THREAD_KILLED", died);
    }
  }
}
