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

package mozg.converters;

import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.io.Writer;

import au.com.bytecode.opencsv.CSVWriter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Converts file from the Excel(.xls) format to the
 * <a href="http://ankisrs.net/">Anki</a>(.csv) format.
 *
 * Created: Wed May 11 17:38:19 2011
 *
 * @author m039
 * @version 1.0
 */
public class AnkiConverter implements Convert {
    private String status = "";
    
    /**
     * This function find a sheet in the W with the specified CONTENT
     * at cell(0, 0).
     *
     * @tag helping function
     */
    final private Sheet getSheet(Workbook w, String contain)
    {
	final int num = w.getNumberOfSheets();


	for (int i = 0; i < num; i++) {
	    Sheet s = w.getSheet(i);

	    if ((s.getRows() == 0) || (s.getColumns() == 0)) {
		continue;
	    }
  
	    Cell c = s.getCell(0, 0);
	    
	    if (contain.contentEquals(c.getContents())) {
		return s;
	    }
	}

	setStatus("There are no spread sheets with the appropriate format!");

	return null;
    }

    /**
     * @param F File to read from a XSL content.
     * @param CONTAIN A phrase to look for in XSL file. If a sheet
     * contains CONTAIN then it is valid.
     * @return Return that sheet.
     */
    final private Sheet getSheetFromFile(File f, String contain)
	throws java.io.IOException, jxl.read.biff.BiffException
    {
	Workbook w	= Workbook.getWorkbook(f);
	Sheet s		= getSheet(w, contain);
	
	return s;
    }

    /**
     * @param F The file that suffix should be changed.
     * @param SUFFIX The new suffix for the FILE.
     * @return New file with constructed from FILE and SUFFIX.
     */
    final private File createFile(File f, String suffix)
    {
	String filename = f.getName();
	final int index	= filename.indexOf('.');
	return new File(f.getParent(), filename.substring(0, index) + "." + suffix);
    }

    /**
     * This function is needed for the Windows where do not know what
     * does UTF-8 encoding mean.
     * 
     * @param F The file path to wrap.
     * @return A wrapper for F that can be used for writing
     */
    final private Writer createUTFWriter(File f)
	throws java.io.FileNotFoundException, java.io.UnsupportedEncodingException
    {
	FileOutputStream fos	= new FileOutputStream(f);
	return new OutputStreamWriter(fos, "UTF8");
    }

    final private CSVWriter getWriterFromFile(File f)
	throws java.io.IOException, jxl.read.biff.BiffException
    {
	File file = createFile(f, "csv");
	Writer writer = createUTFWriter(file);

	setStatus("File '" + file.getAbsolutePath() + "' created. ");

	return new CSVWriter(writer, '\t');	
    }

    /**
     * Parses the S for pairs and adds they to the W.
     */
    final private void createCSVFromXSL(CSVWriter w, Sheet s)
    {
	final int rows = s.getRows();
	final int columns = s.getColumns();

	if ((rows <= 1) || (columns == 0)) {
	    setStatus("The input file does not has a content!");
	    return;
	}

	/**
	 * Create a content for the CSV file for the Anki.
	 */
	for (int i = 1; i < rows; i++) {
	    String[] entries = new String[2];

	    Cell c = s.getCell(0, i);
	
	    entries[0] = c.getContents();

	    c = s.getCell(1, i);

	    entries[1] = c.getContents();
	    
	    w.writeNext(entries);
	}

	addStatus("Converted " + (rows - 1) + " pairs." );
    }

    
    /**
     * Converts Excel to Anki
     *
     * @param f an Excel file
     */
    public void convert(File f) {
	try {
	    Sheet s	= getSheetFromFile(f, "Word");
	    CSVWriter w	= getWriterFromFile(f);

	    createCSVFromXSL(w, s);

	    w.close();
	} catch (Exception e) {
	    setStatus("Error");
	    System.out.println(e.getMessage());
	}
    }

    private void addStatus(String status) {
	this.status += status;
    }
    
    private void setStatus(String status) {
	this.status = status;
    }
    
    /**
     * Returns the status of the convert operation. This status will
     * showen to the user.
     */
    public String getStatus() {
	return new String(status);
    }
}
