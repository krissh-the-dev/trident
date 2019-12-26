/*
 * [GPL v3] Trident > Build Bot
 * This is a build bot specially made for compiling, building and testing Trident Text Editor
 * Any resources/ softwares are not redistrubuted or shipped with this package
 * JarBuilder and InstallForge are Free Open Source Softwares available for commercial/ non-commercial uses
 * The paths used are arbitrary
 */

import java.io.File;
import java.io.IOException;
import java.io.FilenameFilter;
import java.lang.ProcessBuilder.Redirect;

/* 
* Trident Text Editor Build Bot v3.0
* @author: Krishna Moorthy
*/

class Build {
  public static File[] finder(String dirName) {
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
      files[i] = fileF[i].getName();
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
    processBuilder = new ProcessBuilder("java", "Trident", "version.config");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();

    System.out.println("Editing Readme...");
    processBuilder = new ProcessBuilder("java", "Trident", "README.md");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();

    System.out.println("Opening JarBuilder...");
    // The location used is dependent on your enviroment
    processBuilder = new ProcessBuilder("java", "-jar", "../JarBuilder-0.8.0/JarBuilder-0.8.0.jar");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();

    System.out.println("Opening Trident Jar...");
    processBuilder = new ProcessBuilder("java", "-jar", "Trident.jar");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();

    System.out.println("Opening WinRAR...");
    // Add WinRAR to your path
    processBuilder = new ProcessBuilder("winrar", "trident.zip");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();

    System.out.println("Opening EXE Maker...");
    // Add WinRAR to your path
    processBuilder = new ProcessBuilder("toEXE");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();

    System.out.println("Opening InstallForge...");
    // Add InstallForge to your path and place scrit with IF installed dir
    processBuilder = new ProcessBuilder("InstallForge", "InstallScript.ifp");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();
  }
}
