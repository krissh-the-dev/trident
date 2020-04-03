import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RecentsTracker {
  public static ArrayList<String> recents = new ArrayList<>();

  RecentsTracker() {
    readRecords();
  }

  @SuppressWarnings("unchecked")
  public static void readRecords() {
    try {
      if (!(new File("logs/recents.tcf")).exists()) {
        (new File("logs/recents.tcf")).createNewFile();
        System.out.println("New file created.");
      }
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream("logs/recents.tcf"));
      recents = (ArrayList<String>) ois.readObject();
      ois.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void addRecord(String filePath) {
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
        e.printStackTrace();
      }
    }
  }

  public static void printRecords() {
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
