/*
 * Copyright (C) 2011 Mozgin Dmitry
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mozg.logic;

/**
 * This class is used in the {@link mozg.gui.HoverBox}.
 *
 * Created: Wed May 11 13:27:06 2011
 *
 * @author m039
 * @version 1.0
 */
abstract public class HoverBox {

    private final float coeff;
    
    /**
     * Creates a new <code>HoverBox</code> instance.
     *
     * @param coeff a coefficient of the square around the hover area.
     */
    public HoverBox(float coeff) {
	this.coeff = coeff;
    }

    public void hoverBox(int w, int h) {
	final int x = (int) (w / 2 * coeff);
	final int y = (int) (h / 2 * coeff);
	// top left corner
	line(0, 0, x, 0);
	line(0, 0, 0, y);
	// top right
	line(w, 0, w - x, 0);
	line(w, 0, w, y);
	// bottom left
	line(0, h, 0, h - y);
	line(0, h, x, h);
	// bottom right
	line(w, h, w, h - y);
	line(w, h, w - x, h);
    }

    /**
     * Function to draw a line from (x1, y1) to (x2, y2).
     */
    abstract protected void line(int x1, int y1, int x2, int y2);
}
