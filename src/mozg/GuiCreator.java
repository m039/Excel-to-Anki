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

package mozg;

import java.io.File;

import mozg.gui.AllContent;
import mozg.gui.HoverBox;
import mozg.gui.SelectFormatButton;
import mozg.logic.Converter;
import mozg.logic.FileFormat;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * This class fills all methods provided by the {@link AllContent}
 * superclass with a content.
 *
 * Created: Thu May 12 14:23:14 2011
 *
 * @author m039
 * @version 1.0
 */
public class GuiCreator extends AllContent {

    /**
     * Holds instance of the class that does all work.
     */
    private Converter converter = new Converter(FileFormat.NONE);

    /**
     * Use to show the user a status message.
     */
    private Label statusLine;

    /**
     * Initiates convert operations and then shows the user the
     * completion message.
     *
     * @param f a file to be converted
     */
    private void convertFile(final File f) {
	converter.setFile(f);
	converter.convert();
	statusLine.setText(converter.getStatus());
    }

    /**
     * Selects a format to use in the convert operation.
     *
     * @param f a format to use
     */
    private void onSelect(final FileFormat f) {
	converter.setFileFormat(f);
    }

    private void createMenu(final Shell sh) {
	Menu m		= new Menu(sh, SWT.BAR);
	MenuItem mi	= new MenuItem(m, SWT.PUSH);

	mi.setText("Help");

	mi.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
		    System.out.println(e.toString());
		}
	    });

	sh.setMenuBar(m);
    }

    private void createHoverBox(final Composite composite) {
	class HoverBoxCreator extends HoverBox {
	    public HoverBoxCreator(Composite composite) {
		super(composite);
	    }

	    protected void dropped(File f) {
		convertFile(f);
	    }
	}
	
	HoverBoxCreator hbc	= new HoverBoxCreator(composite);
	Label label		= hbc.label();
	Label box		= hbc.box();

	label.setText("Drag and drop the target in the box bellow");
    }

    private void createSelectFormatGroup(final Composite composite) {
	class FormatButtonCreator extends SelectFormatButton {
	    public FormatButtonCreator(Composite c) {
		super(c);
	    }

	    protected void selected(FileFormat f) {
		onSelect(f);
	    }
	}
	
	FormatButtonCreator fbc	= new FormatButtonCreator(composite);

	fbc.button("None",	 FileFormat.NONE).setSelection(true);
	fbc.button("Anki",	 FileFormat.ANKI);
	fbc.button("LiveMocha",	 FileFormat.LIVEMOCHA).setEnabled(false);
    }

    private void createStatusLine(final Composite sh) {
	statusLine = new Label(sh, SWT.BORDER);
    }    
    
    // Implementation of mozg.gui.creators.AllContent

    /**
     * Creates the two areas. One for the hover box and one for the
     * group to select a format.
     */
    protected final void main(final Composite composite) {
	createHoverBox(composite);
	createSelectFormatGroup(composite);
    }

    protected final void menu(final Shell shell) {
	createMenu(shell);
    }

    protected final void statusLine(final Composite composite) {
	createStatusLine(composite);
    }

    /**
     * Creates a new <code>GuiCreator</code> instance.
     */
    public GuiCreator(Shell sh) {
	super(sh);	
    }

}
