/*
 *  RecentsTracker.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RecentsTracker {
  /**
   * Contains methods that keeps writes / reads recently opened files in Trident
   * (ONLY last 5) into a ArrayList Object as tcf file
   */
  public static ArrayList<String> recents = new ArrayList<>();

  RecentsTracker() {
    /**
     * Reads the records upon class object initializaton (Not implemented)
     */
    readRecords();
  }

  @SuppressWarnings("unchecked")
  public static void readRecords() {
    /**
     * Reads records from the .tcf file and casts it into an ArrayList object
     * (recents)
     */
    try {
      if (!(new File("logs/recents.tcf")).exists()) {
        (new File("logs/recents.tcf")).createNewFile();
        System.err.println("Recents object file not found.");
        System.out.println("Created new blank tcf file.");
      }
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream("logs/recents.tcf"));
      recents = (ArrayList<String>) ois.readObject();
      ois.close();
    } catch (Exception e) {
      // e.printStackTrace(); Avoid throwing
      recents = new ArrayList<String>();
    }
  }

  public static void addRecord(String filePath) {
    /**
     * adds a new element to the recents arraylist and then writes to the tcf file
     * 
     * @param: path of the file opened (String)
     */
    readRecords();
    if (recents.size() >= 5) {
      recents.remove(0);
    }
    if (recents.contains(filePath))
      return;
    else {
      recents.add(filePath);
      try (FileOutputStream fos = new FileOutputStream("logs/recents.tcf");
          ObjectOutputStream oos = new ObjectOutputStream(fos)) {
        oos.writeObject(recents);
      } catch (Exception e) {
        // e.printStackTrace(); Avoid throwing
      }
    }
  }

  public static ArrayList<String> getRecords() {
    /**
     * returns the arraylist of recently opened file paths
     */
    readRecords();
    return recents;
  }

  @Deprecated
  public static void printRecords() {
    /**
     * Prints the records from the arraylist
     */
    readRecords();
    if (recents.size() == 0) {
      System.out.println("No records found");
      return;
    }
    for (String x : recents) {
      System.out.println(x);
    }
  }
}
