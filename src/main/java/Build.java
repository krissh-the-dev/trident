/*
 *  Build.java
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

import java.io.File;
import java.io.IOException;
import java.io.FilenameFilter;
import java.lang.ProcessBuilder.Redirect;

/* 
 * Trident Text Editor Build Bot v3.0
 * @author: Krishna Moorthy
 * [GPL v3] Trident > Build Bot
 * This is a build bot specially made for compiling, building and testing Trident Text Editor
 * Any resources/ softwares are not redistrubuted or shipped with this package
 * JarBuilder and InstallForge are Free Open Source Softwares available for commercial/ non-commercial uses
 * The paths used are arbitrary
 */

@Deprecated
class Build {
  public static File[] finder(String dirName, String type) {
    /*
     * @param: name of the directory as string;
     * 
     * returns all java files in the given directory as a File array
     */
    File dir = new File(dirName);
    return dir.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String filename) {
        return filename.endsWith(type);
      }
    });
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Getting project files...");
    File fileF[] = finder(".", "java");
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
      int res = p.waitFor();
      if (res != 0)
        System.err.println(file + "compilation ended with error.");
      else
        System.out.println(file + " was compiled successfully.");
    }
    System.out.println("All source files were compiled.");

    System.out.println("Copying class file to /bin...");

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

    System.out.println("Opening EXE Maker...");
    // Add EXE maker to your path
    processBuilder = new ProcessBuilder("toEXE");
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

    System.out.println("Opening InstallForge...");
    // Add InstallForge to your path and place scrit with IF installed dir
    processBuilder = new ProcessBuilder("InstallForge", "InstallScript.ifp");
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.appendTo(log));
    p = processBuilder.start();
    p.waitFor();
  }
}
