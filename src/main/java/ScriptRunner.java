import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

class StreamGobbler implements Runnable {
  private InputStream inputStream;
  private Consumer<String> consumer;

  public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
    this.inputStream = inputStream;
    this.consumer = consumer;
  }

  @Override
  public void run() {
    new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
  }
}

public class ScriptRunner {
  public static void main0(String[] args) {
    try {
      String homeDirectory = System.getProperty("user.home");
      Process process;
      boolean isWindows = false;
      if (isWindows) {
        process = Runtime.getRuntime().exec(String.format("cmd.exe /c dir %s", homeDirectory));
      } else {
        process = Runtime.getRuntime().exec(String.format("/bin/bash -c -c ls %s", homeDirectory));
      }
      StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
      Executors.newSingleThreadExecutor().submit(streamGobbler);
      int exitCode = process.waitFor();
      assert exitCode == 0;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    try {
      ProcessBuilder builder = new ProcessBuilder();
      if (false) {
        builder.command("cmd /c start cmd.exe", "/K", "dir");
      } else {
        builder.command("/usr/bin/gnome-terminal", "--", "cat");
      }
      builder.directory(new File(System.getProperty("user.home")));
      Process process = builder.start();
      StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
      Executors.newSingleThreadExecutor().submit(streamGobbler);
      int exitCode = process.waitFor();
      assert exitCode == 0;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
