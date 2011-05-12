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

import java.io.File;

import mozg.converters.AnkiConverter;
import mozg.converters.Convert;
import mozg.converters.Status;

/**
 * This class passes filename to the classes from mozg.converts.
 *
 * Created: Tue May 10 23:03:44 2011
 *
 * @author m039
 * @version 1.0
 */
public class Converter implements Status {
    /**
     * Selected format
     */
    private FileFormat format;

    /**
     * Selected file
     */
    private File file;

    /**
     * Current status of the class
     */
    private String status;

    /**
     * Creates a new <code>Converter</code> instance.
     *
     * @param format an initial format to use 
     */
    public Converter(FileFormat format) {
	setFileFormat(format);
    }

    /**
     * Starts an operation.
     */
    public void convert() {
	if ((format != null) && (file != null)) {
	    switch (format) {
	    case ANKI:
		AnkiConverter anki = new AnkiConverter();
		anki.convert(file);
		setStatus(anki.getStatus());
		break;
	    case NONE:
		setStatus("None format selected");
	    default:
		break;
	    }
	}
    }

    /**
     * Get the <code>FileFormat</code> value.
     *
     * @return a <code>FileFormat</code> value
     */
    public final FileFormat getFileFormat() {
	return format;
    }

    /**
     * Set the <code>FileFormat</code> value.
     *
     * @param newFileFormat The new FileFormat value.
     */
    public final void setFileFormat(final FileFormat newFileFormat) {
	this.format = newFileFormat;
    }

    /**
     * Get the <code>File</code> value.
     *
     * @return a <code>File</code> value
     */
    public final File getFile() {
	return file;
    }

    /**
     * Set the <code>File</code> value.
     *
     * @param newFile The new File value.
     */
    public final void setFile(final File newFile) {
	this.file = newFile;
    }

    /**
     * Get the <code>Status</code> value.
     *
     * @return a <code>String</code> value
     */
    public final String getStatus() {
	return new String(status);
    }

    /**
     * Set the <code>Status</code> value.
     *
     * @param newStatus The new Status value.
     */
    private final void setStatus(final String newStatus) {
	this.status = newStatus;
    }
}
