import java.lang.ProcessBuilder;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

public class TridentCompiler extends Trident {
  String compileCommand;
  String runCommand;
  String path;

  public TridentCompiler(String path) {
    this.path = path;
    String languageIdentifier = fileTypeParser(path) + " File";
    switch (languageIdentifier) {
    case "py File":
      compileCommand = "python";
      runCommand = "python";
      break;
    case "java File":
      compileCommand = "javac";
      runCommand = "java";
      break;
    default:
      compileCommand = "javac";
      runCommand = "java";
      break;
    }
  }

  public void compile() throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder(compileCommand, path);
    processBuilder.directory((new File(path)).getParentFile());
    File log = new File(path + "-output.txt");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    Process p = processBuilder.start();
    p.waitFor();
    // status1.setText("Compilation ended.");
  }

  public void execute() throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder(runCommand, path);
    processBuilder.directory((new File(path)).getParentFile());
    File log = new File(path + "-output.txt");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    Process p = processBuilder.start();
    // status1.setText("Execution ended.");
  }
}
