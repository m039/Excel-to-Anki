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

import mozg.logic.FileFormat;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * This class creates {@link button(String, FileFormat)} buttons that
 * are grouped together.
 *
 * Created: Wed May 11 14:21:07 2011
 *
 * @author m039
 * @version 1.0
 */
abstract public class SelectFormatButton {
    final private Group parent;
    final private SelectionListener listener;
    
    /**
     * Creates a new <code>FormatButtonCreatorBase</code> instance.
     */
    public SelectFormatButton(Composite composite) {
	parent		= new Group(composite, SWT.NONE);
	RowLayout rl	= new RowLayout(SWT.VERTICAL);

	parent.setText("Output formats");
	parent.setLayout(rl);
	parent.setData(this); // This is the fucking trick

	listener = new SelectionAdapter () {
		public void widgetSelected(SelectionEvent e) {
		    Button b = (Button) e.widget;
		    
		    if (b.getSelection()) {
			SelectFormatButton fbcb = (SelectFormatButton) b.getParent().getData();
			fbcb.selected((FileFormat) b.getData());
		    }
		}
	    };
    }

    /**
     * Returns a radio button that already is initialized
     *
     * @param text a text of the button
     * @param f an ID of the button
     * @return the button
     */
    public Button button(String text, FileFormat f) {
	final Button b = new Button(parent, SWT.RADIO);

	b.setText(text);
	b.setData(f);
	b.addSelectionListener(listener);
	
	return b;
    }

    /**
     * Calls then an element of the group is selected
     *
     * @param f which element of the group is selected
     */
    abstract protected void selected(FileFormat f);
}