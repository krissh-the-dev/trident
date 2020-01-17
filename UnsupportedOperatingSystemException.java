
/*
 *  UnsupportedOperatingSystemException.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.io/KrishnaMoorthy12
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
 * Thrown when the Trident/ Trident Compiler does not support/ not expected to work properly with user's plaftorm
 * (GPL v3) Trident > UnsupportedOperatingSystemException
 * @author: Krishna Moorthy
 */

public class UnsupportedOperatingSystemException extends Exception {
  private static final long serialVersionUID = 8760547834236745475L;

  @Override
  public String toString() {
    return "Your Operating System is not supported.";
  }
}
