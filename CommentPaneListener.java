/*
 *  CommentPaneListener.java
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

/*
 * [GPL v3] Trident > CommentPaneListener
 * @author: Krishna Moorthy
 */

class CommentPaneListener extends Thread {
  /*
   * This thread keeps running in the background to keep the status area 1 i.e the
   * comment area to be meaningful
   * 
   * started when status bar is initialized
   */
  @Override
  public void run() {
    try {
      while (true) {
        Trident.status1.setText("Ready.");
        Thread.sleep(20000);
      }
    } catch (InterruptedException died) {
      Trident.ErrorDialog("STATUS_THREAD_KILLED", died);
    }
  }
}
