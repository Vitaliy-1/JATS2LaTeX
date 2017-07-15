package org.emed.latex.standard;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.emed.classes.Bold;
import org.emed.classes.Cell;
import org.emed.classes.Duplicate;
import org.emed.classes.Figure;
import org.emed.classes.Italic;
import org.emed.classes.Markup;
import org.emed.classes.Par;
import org.emed.classes.ParContent;
import org.emed.classes.Row;
import org.emed.classes.Section;
import org.emed.classes.Table;
import org.emed.classes.Xref;

public class BodyStandard {
	public static void body(BufferedWriter wrlatex, List<Section> listSections, String referenceLink) throws IOException {
		for (int j = 0; j < listSections.size(); j++) {
			Section section = listSections.get(j);
			sectionWriting(wrlatex, section);
		} //end of Sections
		wrlatex.newLine();
		wrlatex.write("\\bibliography{");
		wrlatex.write(referenceLink);
		wrlatex.write("}");
		wrlatex.newLine();
		wrlatex.write("\\end{document}");
		wrlatex.close();

	}

	private static void sectionWriting(BufferedWriter wrlatex, Section section)
			throws IOException, MalformedURLException, FileNotFoundException {
		wrlatex.write("\\" + section.getType() + "section{" + section.getTitle() + "}");
		wrlatex.newLine();
		List<Section> secContents = section.getSecContent();
		for (Section secContent : secContents) {
			if (secContent.getClass().getName() == "org.emed.classes.ParContent") {
				ParContent parContent = (ParContent) secContent; 
				if (parContent.getType() == "par") {
					List<ParContent> parContents = parContent.getParContentList();
					wrlatex.write("\\par ");
					writingParContent(wrlatex, parContents);
				} else if (parContent.getType() == "unordered") {
					List<ParContent> parContents = parContent.getParContentList();
					wrlatex.write("\\item ");
					writingParContent(wrlatex, parContents);
				} else if (parContent.getType() == "ordered") {
					List<ParContent> parContents = parContent.getParContentList();
					wrlatex.write("\\item ");
					writingParContent(wrlatex, parContents);
				}
				wrlatex.newLine();
			} else if (secContent.getClass().getName() == "org.emed.classes.Markup") {
				Markup markup = (Markup) secContent;
				wrlatex.write(markup.getContent());
				wrlatex.newLine();
			} else if (secContent.getClass().getName() == "org.emed.classes.Figure") {
				Figure figure = (Figure) secContent;
				wrlatex.write("\\begin{figure}");
				wrlatex.newLine();
				List<ParContent> tabContents = figure.getParContent();
				for (ParContent parContent : tabContents) {
					if (parContent.getType() == "figureTitle") {
						List<ParContent> parContents = parContent.getParContentList();
						wrlatex.write("\\caption{");
						writingParContent(wrlatex, parContents);
						wrlatex.write("}");
						wrlatex.newLine();
					}
				}
				wrlatex.write("\\label{" + figure.getId() + "}");
				wrlatex.newLine();
				
				// downloading figures files if it is possible
				String fileName = downloadFigures(figure);
				if (fileName == null) {
					wrlatex.write("\\includegraphics[width=\\linewidth ]{" + figure.getLink() + "}");
				} else {
					wrlatex.write("\\includegraphics[width=\\linewidth ]{" + fileName + "}");
				}
				
				wrlatex.newLine();
				List<ParContent> tabCaption = figure.getParContent();
				for (ParContent parContent : tabCaption) {
					if (parContent.getType() == "figureCaption") {
						List<ParContent> parContents = parContent.getParContentList();
						wrlatex.write("\\caption*{");
						writingParContent(wrlatex, parContents);
						wrlatex.write("}");
						wrlatex.newLine();
					}
				}
				wrlatex.write("\\end{figure}");
				wrlatex.newLine();
			} else if (secContent.getClass().getName() == "org.emed.classes.Table") {
				Table table = (Table) secContent;
				wrlatex.write("\\begin{table*}[!b]");
				wrlatex.newLine();
				List<ParContent> tabContents = table.getParContent();
				for (ParContent parContent : tabContents) {
					if (parContent.getType() == "tableTitle") {
						List<ParContent> parContents = parContent.getParContentList();
						wrlatex.write("\\caption{");
						writingParContent(wrlatex, parContents);
						wrlatex.write("}");
						wrlatex.newLine();
					}
				}
				wrlatex.write("\\label{" + table.getId() + "}\\centering");
				wrlatex.newLine();
				
				String tabulary = "";
				for (int i = 0; i < table.getColumnNumber(); i++) {
					tabulary = tabulary + "L";	
				}
				wrlatex.write("\\begin{tabulary}{1.0\\textwidth}{" + tabulary + "}");
				wrlatex.newLine();
				wrlatex.write("\\toprule");
				wrlatex.newLine();
				ArrayList<Row> rows = table.getRow();
				ArrayList<Duplicate> duplicateList = new ArrayList<Duplicate>();
				for (int x = 0; x < rows.size(); x++) {
					Row row = rows.get(x);
					
					ArrayList<Cell> cells = row.getCell();
					for (int i = 0; i < cells.size(); i++) {
						Cell cell = cells.get(i);
						if (cell.getRowspan() > 1) {
							Duplicate duplicate = new Duplicate();
							// TODO cell position is not correct when in one row multicolumn goes previous to the multirow
							duplicate.setCell(i);
							duplicate.setRow(x);
							duplicateList.add(duplicate);
							int difference = (cell.getRowspan() - 1);
							duplicate.setDifference(difference);
						}		
						// Check whether there is already another multirow in a row. If yes - correct cellNumber (duplicate.getCell) for inserting "&"
						// int prevNumber tells how many times the multirow occurs in the same row
						int prevNumber = 0;
						for (Duplicate duplicate: duplicateList) {
							if (duplicate.getRow() == prevNumber) {
								int changeNumber = duplicate.getCell() - prevNumber;
								duplicate.setCell(changeNumber);
							}
					
							if (x > duplicate.getRow() && x <= (duplicate.getRow() + duplicate.getDifference()) && i == duplicate.getCell()) {
								// writing additional "&" in LaTeX table where we need it
								prevNumber = duplicate.getRow();
								wrlatex.write("& ");
							}
						}
						
						if (cell.getColspan() == 1 && cell.getRowspan() == 1) {
							ArrayList<ParContent> cellContents = cell.getParContent();
							for (ParContent cellContent : cellContents) {
								List<ParContent> parContents = cellContent.getParContentList();
								writingParContent(wrlatex, parContents);
							}
							
						} else if (cell.getColspan() > 1 && cell.getRowspan() == 1) {
							ArrayList<ParContent> cellContents = cell.getParContent();
							for (ParContent cellContent : cellContents) {
								List<ParContent> parContents = cellContent.getParContentList();
								wrlatex.write("\\multicolumn{" + cell.getColspan() + "}{c}{");
								writingParContent(wrlatex, parContents);
								wrlatex.write("}");
							}
						} else if (cell.getColspan() == 1 && cell.getRowspan() > 1) {
							ArrayList<ParContent> cellContents = cell.getParContent();
							for (ParContent cellContent : cellContents) {
								List<ParContent> parContents = cellContent.getParContentList();
								
								wrlatex.write("\\multirow{" + cell.getRowspan() + "}*{");
								writingParContent(wrlatex, parContents);
								wrlatex.write("}");
							}
						}
						if (i < (cells.size() - 1)) {
							wrlatex.write(" & ");
						} else {
							wrlatex.write(" \\\\ ");
							wrlatex.newLine();
						}
					}
					if (row.getType() == "head") {
						wrlatex.write("\\midrule");
						wrlatex.newLine();
					}
				}
				wrlatex.write("\\bottomrule");
				wrlatex.newLine();
				wrlatex.write("\\end{tabulary}");
				wrlatex.newLine();
				wrlatex.write("\\\\");
				wrlatex.newLine();
				List<ParContent> tabCaption = table.getParContent();
				for (ParContent parContent : tabCaption) {
					if (parContent.getType() == "tableCaption") {
						List<ParContent> parContents = parContent.getParContentList();
						wrlatex.write("\\caption*{");
						writingParContent(wrlatex, parContents);
						wrlatex.write("}");
						wrlatex.newLine();
					}
				}
				
				wrlatex.write("\\end{table*}");
				wrlatex.newLine();
				
		    } else if (secContent.getClass().getName() == "org.emed.classes.Section") {
				sectionWriting(wrlatex, secContent);
			}
		}
	}

	private static String downloadFigures(Figure figure)
			throws MalformedURLException, IOException, FileNotFoundException {
		String fileName = null;
		Pattern p = Pattern.compile("^((http[s]?|ftp):\\/)?\\/?([^:\\/\\s]+)((\\/\\w+)*\\/)([\\w\\-\\.]+[^#?\\s]+)(.*)?(#[\\w\\-]+)?$");
		Matcher m = p.matcher(figure.getLink().trim());
		if (m.find()) {
			fileName = m.group(6);
			URL website = new URL(m.group());
			System.out.println("----------------------------------------------------");
			System.out.println("Trying download file: " + m.group() + " from the web");
			try {
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream(fileName);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
			} catch (Exception e) {
				System.err.println("Figure was not downloaded");
				e.printStackTrace();
			}
			System.out.println("file was downloaded successfully");
		}
		return fileName;
	}
	
	private static void writingParContent(BufferedWriter wrlatex, List<ParContent> parContents) throws IOException {
		for (ParContent parContentPar : parContents) {
			
			if (parContentPar.getClass().getName() == "org.emed.classes.Par") {
				Par par = (Par) parContentPar;
				wrlatex.write(par.getContent());
			} else if (parContentPar.getClass().getName() == "org.emed.classes.Italic") {
				Italic italic = (Italic) parContentPar;
				wrlatex.write("\\textit{" + italic.getContent() + "}");
			} else if (parContentPar.getClass().getName() == "org.emed.classes.Bold") {
				Bold bold = (Bold) parContentPar;
				wrlatex.write("\\textbf{" + bold.getContent() + "}");
		    } else if (parContentPar.getClass().getName() == "org.emed.classes.Xref") {
				Xref xref = (Xref) parContentPar;
				
				if (xref.getBibContent() != null) {
					wrlatex.write("\\cite{bib" + xref.getBibContent() + "}");
				} else if (xref.getFigContent() != null) {   
					wrlatex.write(xref.getFigDescription() + " " + xref.getFigContent());
				} else if (xref.getTableContent() != null) {
					wrlatex.write(xref.getTableDescription() + " " + xref.getTableContent());
				}
				
				
			} 
				
		} // end of Paragraph content
	}

}
