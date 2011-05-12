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

package mozg.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the structure of the program. It divide the program into
 * three parts: {@link #menu()}, {@link #main()} and {@link #statusLine()}.
 *
 * @author m039
 * @version 1.0
 */
abstract public class AllContent {
    private final int MAIN		= 0;
    private final int STATUS_LINE	= 1;

    private final Shell sh;
	    
    public AllContent(Shell sh) {
	this.sh = sh;
	sh.setLayout(new GridLayout(1, true));
    }
	    
    private Composite composite(int type) {
	Composite c = null;
		
	switch (type) {
	case STATUS_LINE:
	    c = new Composite(sh, SWT.NONE);
	    c.setLayout(new FillLayout(SWT.HORIZONTAL));
	    c.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
	    break;
	case MAIN:
	    c = new Composite(sh, SWT.NONE);
	    c.setLayout(new FillLayout(SWT.HORIZONTAL));
	    c.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    break;
	default:
	    throw new NullPointerException("There is no [" + type + "] content type");
	}

	return c;
    }

    private void menu() {
	menu(sh);
    }

    private void main() {
	Composite c = composite(MAIN);
	main(c);	
    }

    private void statusLine() {
	Composite c = composite(STATUS_LINE);
	statusLine(c);
    }
    
    /**
     * Creates the all parts in the right order.
     */
    public void createAll() {
	menu();
	main();
	statusLine();
    }

    /**
     * Used to create menu.
     *
     * @param sh an already created parent (and the sh is the
     * <code>Shell</code> class because of the 'setMenuBar')
     */
    abstract protected void menu(Shell sh);
    
    /**
     * Used to create main area.
     *
     * @param c an already created parent
     */
    abstract protected void main(Composite c);

    
    /**
     * Used to create bottom line.
     *
     * @param c an already created parent
     */
    abstract protected void statusLine(Composite c);
}