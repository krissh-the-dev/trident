package org.trident.exception;
/*
 *  org.trident.exception.UnsupportedFileException.java
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

import org.trident.util.FileTypeParser;

/*
 * (Apache v2) Trident > org.trident.exception.UnsupportedFileException
 * @author: Krishna Moorthy
 */
public final class UnsupportedFileException extends Exception {
  /*
   * Thrown when a file is not supported by Trident/ Trident Compiler/ Source
   * Action
   */
  private static final long serialVersionUID = -6622809143038663344L;
  String file;

  public UnsupportedFileException(String filepath) {
    file = filepath;
  }

  @Override
  public String toString() {
    return FileTypeParser.getType(file) + " is unsupported.";
  }
}
