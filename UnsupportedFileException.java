
/*
 *  UnsupportedFileException.java
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
 * (GPL v3) Trident > UnsupportedFileException
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
