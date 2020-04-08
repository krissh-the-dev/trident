
/*
 *  UnsupportedOperatingSystemException.java
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

/*
 * (Apache v2) Trident > UnsupportedOperatingSystemException
 * @author: Krishna Moorthy
 */

public class UnsupportedOperatingSystemException extends Exception {
  /*
   * Thrown when the Trident/ Trident Compiler does not support/ not expected to
   * work properly with user's plaftorm
   */
  private static final long serialVersionUID = 8760547834236745475L;

  @Override
  public String toString() {
    return "Your Operating System is not supported.";
  }
}
