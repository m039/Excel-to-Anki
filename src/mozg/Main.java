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

import mozg.gui.AllContent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @todo
 *
 * - Посмотреть другой вариант statusLine
 *
 * - Нужно всё таки как-то это всё прокомментировать
 *
 * - Добавить help
 */

/**
 * This class contains the main function. The function creates
 * <code>GuiCreatior</code> that creates all content of the program.
 *
 * Created: Thu May 12 14:15:35 2011
 *
 * @author Mozgin Dmitry
 * @version 1.0
 */
public class Main {
    /**
     * This is a simple <a href="http://www.eclipse.org/swt/">SWT</a>
     * wrapper.
     */
    public static void main(final String [] args) {
	final Display d = new Display();
	final Shell sh = new Shell(d);

	AllContent content = new GuiCreator(sh);
	content.createAll();

	sh.setText("Simple words from excel convertor");
	sh.setSize(500, 200);
	sh.open();

	while (!sh.isDisposed()) {
	    if (!d.readAndDispatch()) {
		d.sleep();
	    }
	}

	d.dispose();
    }
}
