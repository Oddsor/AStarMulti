/*
 * Copyright 2014 Odd A Sørsæther
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oddsor.AStarMulti;

/**
 * TODO figure out what the point of defining your own exceptions is. Added this
 * for bad distance-measurements. 
 * @author Odd
 */
public class DistanceOutOfRangeException extends Exception {

    /**
     * Creates a new instance of <code>DistanceOutOfRangeException</code>
     * without detail message.
     */
    public DistanceOutOfRangeException() {
    }

    /**
     * Constructs an instance of <code>DistanceOutOfRangeException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DistanceOutOfRangeException(String msg) {
        super(msg);
    }
}