package org.trident.model;
/*
 *  org.trident.model.TridentCompiler.java
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

import org.trident.Trident;
import org.trident.exception.UnsupportedFileException;
import org.trident.exception.UnsupportedOperatingSystemException;
import org.trident.util.FileTypeParser;
import org.trident.util.TridentLogger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Desktop;
import java.io.File;

/*
 * Trident Compiler v2.2
 * (Apache v2) Trident > Compilers
 * Since: Trident v0.2
 * Stable since: Trident v2.0
 */

public class TridentCompiler {
    /*
     * The Compiler and Tools of Trident Text Editor
     */

    public static String loadDriver() throws UnsupportedOperatingSystemException {
        String driver;
        switch (Trident.getInstance().doCheckOS()) {
            case 1: // Windows
                driver = "cmd /c start cmd.exe /K \"title Trident Compiler && ";
                break;

            case 2:
                driver = "/usr/bin/gnome-terminal --";
                break;

            default:
                throw new UnsupportedOperatingSystemException();
        }
        return driver;
    }

    public static void compile(String filepath)
            throws UnsupportedOperatingSystemException, UnsupportedFileException, IOException {
        /*
         * Executes language specific compile commands on a system terminal.
         *
         * @throws: Unsupported OS Exception, Unsupported File Exception, IOException,
         * InterruptedException
         */
        try {
            String driver = loadDriver();
            String fileType = FileTypeParser.getType(filepath);
            switch (fileType) {
                case "Python Source File":
                    Runtime.getRuntime().exec(driver + "echo && python \"" + filepath + "\" && pause && exit \"");
                    break;

                case "Java Source File":
                    Runtime.getRuntime().exec(driver + "echo && javac \"" + filepath
                            + "\" && echo Compilation ended. && pause && exit \"");
                    break;

                case "C Source File":
                    String fileLocation = (new File(filepath)).getParent();
                    Runtime.getRuntime().exec(driver + "cd \"" + fileLocation + "\" && echo && gcc \"" + filepath
                            + "\" -std=c99 && echo Compilation ended. && pause && exit \"");
                    // using C99 to avoid irritating forbidden errors
                    break;

                case "C++ Source File":
                    fileLocation = (new File(filepath)).getParent();
                    Runtime.getRuntime().exec(driver + "cd \"" + fileLocation + "\" && echo && g++ \"" + filepath
                            + "\" && echo Compilation ended. && pause && exit \"");
                    break;

                default:
                    throw new UnsupportedFileException(filepath);
            }
            Trident.getInstance().getstatusText().setText("Compilation ended.");
        } catch (UnsupportedOperatingSystemException uos) {
            TridentLogger.getInstance().error(TridentCompiler.class, "DRIVER_ERR: " + uos);
        } catch (UnsupportedFileException uf) {
            Trident.getInstance().getstatusText().setText("File could not be compiled.");
        }
    }

    public static void execute(String filepath) throws UnsupportedFileException, IOException, InterruptedException {
        /*
         * Runs any compiled output files defined for current language.
         *
         * @throws: Unsupported OS Exception, Unsupported File Exception, IOException,
         * InterruptedException
         */
        try {
            String driver = loadDriver();
            String exec;
            Process p = null;

            if (filepath.contains("powerboil")) {
                p = Runtime.getRuntime()
                        .exec("cmd /c start cmd.exe /K \"title Trident - PowerBoil Alpha && echo && python \""
                                + filepath + "\" && pause && exit \"");
                return;
            }

            String fileType = FileTypeParser.getType(filepath);
            switch (fileType) {
                case "Python Source File":
                    p = Runtime.getRuntime().exec(driver + "echo && python \"" + filepath + "\" && pause && exit \"");
                    break;

                case "Java Source File":
                case "Java Class File":
                    Path name = Paths.get(filepath);
                    String classFile = name.getFileName().toString();
                    exec = classFile.replaceFirst("[.][^.] + $", "");
                    String location = new File(filepath).getParent().toString();
                    p = Runtime.getRuntime()
                            .exec(driver + " cd \"" + location + "\" && echo && java " + exec + " && pause && exit \"");
                    break;

                case "C Source File":
                case "C++ Source File":
                    exec = (new File(filepath)).getParent() + "/a.exe";
                    p = Runtime.getRuntime().exec(driver + "echo && \"" + exec + "\" && pause && exit \"");
                    break;

                case "HTML File":
                    try {
                        filepath = filepath.replace('\\', '/');
                        Desktop.getDesktop().browse(java.net.URI.create("file:///" + filepath));
                    } catch (IOException ioe) {
                        TridentLogger.getInstance().error(TridentCompiler.class, "COMPILER_BROSWER_ERR: " + ioe);
                    }
                    break;

                default:
                    throw new UnsupportedFileException(filepath);
            }
            Trident.getInstance().getstatusText().setText("Execution window deployed.");
            p.waitFor();
            p.destroy();
        } catch (UnsupportedOperatingSystemException uos) {
            TridentLogger.getInstance().error(TridentCompiler.class, "DRIVER_ERR: " + uos);
        } catch (UnsupportedFileException uf) {
            Trident.getInstance().getstatusText().setText("File could not be executed.");
        }
    }

    public static void openTerminal() throws UnsupportedOperatingSystemException {

        try {
            int os = Trident.getInstance().doCheckOS();
            String driver = loadDriver();
            String parent = (new File(Trident.getInstance().getPath())).getParent();
            if (os == 1) {
                Runtime.getRuntime().exec(driver + "cd " + parent + "\"");
            } else if (os == 2) {
                Runtime.getRuntime().exec(driver + "cd \"" + parent + "\"");
            } else
                throw new UnsupportedOperatingSystemException();
        } catch (UnsupportedOperatingSystemException unOS) {
            TridentLogger.getInstance().error(TridentCompiler.class, "DRIVER_ERR: " + unOS);
        } catch (Exception uos) {
            TridentLogger.getInstance().error(TridentCompiler.class, "TERMINAL_ERROR; " + uos);
        }
    }

}
