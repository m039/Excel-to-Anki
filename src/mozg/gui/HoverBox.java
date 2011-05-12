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

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * This is the custom widget and is used as a drag and drop box.
 * <br><br>
 * There is only one method {@link #dropped(File)} have to be
 * overrided. It is called when the target is dropped into the box.
 * <br><br>
 * There are two methods that return widget: {@link box()}, {@link label()}.
 * <br><br>
 * {@link box()} is a hover area and {@link label()} is a label above
 * this area.
 * <br><br>
 * Created: Wed May 11 14:43:05 2011
 *
 * @author m039
 * @version 1.0
 */
abstract public class HoverBox {
    private final Composite parent;

    class HoverBoxListener
	extends mozg.logic.HoverBox
	implements PaintListener, DropTargetListener {
	private GC gc;
	private int color = SWT.COLOR_BLACK;
	private Control control;

	public HoverBoxListener(Control control, float coeff) {
	    super(coeff);
	    this.control = control;
	}

	private void activate() {
	    color = SWT.COLOR_GREEN;
	}

	private void deactivate() {
	    color = SWT.COLOR_BLACK;
	}

	protected void line(int x1, int y1, int x2, int y2) {
	    gc.drawLine(x1, y1, x2, y2);
	}

	// Implementation of org.eclipse.swt.events.PaintListener

	public final void paintControl(final PaintEvent e) {
	    gc = e.gc;
	    gc.setForeground(e.display.getSystemColor(color));
	    gc.setLineWidth(10);

	    Point p = control.getSize();
	    hoverBox(p.x, p.y);
	}

	// Implementation of org.eclipse.swt.dnd.DropTargetListener

	public final void dragEnter(final DropTargetEvent dropTargetEvent) {
	    activate();
	    control.redraw();
	}

	public final void dragLeave(final DropTargetEvent dropTargetEvent) {
	    deactivate();
	    control.redraw();
	}

	public final void dragOperationChanged(final DropTargetEvent dropTargetEvent) {
	}

	public final void dragOver(final DropTargetEvent dropTargetEvent) {
	}

	public final void drop(final DropTargetEvent e) {
	    if (e.data == null) {
		e.detail = DND.DROP_NONE;
		return;
	    }

	    dropped(new File(((String[]) e.data)[0]));
	}

	public final void dropAccept(final DropTargetEvent dropTargetEvent) {
	}
    }

    /**
     * Creates a new <code>HoverBoxCreatorBase</code> instance.
     */
    public HoverBox(Composite composite) {
	parent		= new Composite(composite, SWT.NONE);
	GridLayout gl	= new GridLayout(1, false);

	parent.setLayout(gl);
    }

    /**
     * @return a text <code>Label</code> above the hover area.
     */
    public Label label() {
	Label l = new Label(parent, SWT.CENTER);
	l.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	return l;
    }

    /**
     * @return a <code>Label</code> value as the hover area
     */
    public Label box() {
	Label l				= new Label(parent, SWT.CENTER);
	HoverBoxListener listener	= new HoverBoxListener(l, 0.3f);

	l.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	l.addPaintListener(listener);
	addDropTargetListenerToLabel(l, listener);
	
	return l;
    }

    private void addDropTargetListenerToLabel(Label box, DropTargetListener dtl) {
	int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
	Transfer[] types = new Transfer[] {FileTransfer.getInstance()};
	DropTarget target = new DropTarget(box, operations);
	target.setTransfer(types);
	target.addDropListener(dtl);
    }

    /**
     * Called when a target is dropped into the area.
     *
     * @param f the target
     */
    abstract protected void dropped(File f);
}
