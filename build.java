/*
 * This is a build bot specially made for compiling, building and testing Trident Text Editor
 * Any resorces/ softwares are not redistrubuted or shipped with this package
 * JarBuilder and InstallForge are Free Open Source Softwares available for commercial/ non-commercial
 * The paths used are arbitrary
 */

import java.io.File;
import java.io.IOException;
import java.io.FilenameFilter;
import java.lang.ProcessBuilder.Redirect;

class Build {
  public static File[] finder(String dirName){
    File dir = new File(dirName);
    return dir.listFiles(new FilenameFilter() { 
      public boolean accept(File dir, String filename) {
        return filename.endsWith(".java");
      }
    });
  }
  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Getting project files...");
    File fileF[] = finder(".");
    String files[] = new String[fileF.length];

    for (int i = 0; i < fileF.length; i++) {
      // if (!fileF[i].getName().equals("build.java")){
        files[i] = fileF[i].getName();
      // } else {
      //   i--;
      //   continue;
      // }
    }
    

    System.out.println("Compiling source files...");
    for (String file : files) {
      ProcessBuilder processBuilder = new ProcessBuilder("javac", file);
      File log = new File("output.txt");
      processBuilder.redirectErrorStream(true);
      processBuilder.redirectOutput(Redirect.appendTo(log));
      Process p = processBuilder.start();
      p.waitFor();
      System.out.println(file + " was compiled.");
    }
    System.out.println("All source files were compiled.");

    System.out.println("Opening Trident...");
    ProcessBuilder processBuilder = new ProcessBuilder("java", "Trident");
    File log = new File("output.txt");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    Process p = processBuilder.start();
    p.waitFor();
    System.out.println("Trident was closed.");

    System.out.println("Editing version config...");
    processBuilder = new ProcessBuilder("notepad", "version.config");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();

    System.out.println("Editing Readme...");
    processBuilder = new ProcessBuilder("notepad", "README.md");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();

    System.out.println("Opening JarBuilder...");
    processBuilder = new ProcessBuilder("java", "-jar", "../JarBuilder-0.8.0/JarBuilder-0.8.0.jar");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();
    System.out.println("Opening InstallForge...");
    processBuilder = new ProcessBuilder("C:/Program Files (x86)/solicus/InstallForge/InstallForge.exe");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();
  }
}
