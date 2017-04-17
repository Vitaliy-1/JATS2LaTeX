package org.emed.latex.gost;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.emed.classes.Bold;
import org.emed.classes.Cell;
import org.emed.classes.Figure;
import org.emed.classes.Italic;
import org.emed.classes.Markup;
import org.emed.classes.Par;
import org.emed.classes.ParContent;
import org.emed.classes.Row;
import org.emed.classes.SecContent;
import org.emed.classes.Section;
import org.emed.classes.SubSec;
import org.emed.classes.Table;
import org.emed.classes.Xref;

public class BodyGost {
	
	public static void body(BufferedWriter wrobj, List<Section> listSections) throws IOException {
		for (int j = 0; j < listSections.size(); j++) {
			Section section = listSections.get(j);
			wrobj.write(section.getTitle());
			wrobj.newLine();
			List<SecContent> secContents = section.getSecContent();
			for (SecContent secContent : secContents) {
				//System.out.println(secContent.getClass().getName());
				/*
				if (secContent.getTitle() != null) {
					System.out.println(secContent.getTitle());
					wrobj.write(secContent.getTitle());
				}
				*/
				if (secContent.getClass().getName() == "org.emed.classes.ParContent") {
					ParContent parContent = (ParContent) secContent; 
					if (parContent.getType() == "par") {
						List<ParContent> parContents = parContent.getParContentList();
						wrobj.write("\\par ");
						writingParContent(wrobj, parContents);
					} else if (parContent.getType() == "unordered") {
						List<ParContent> parContents = parContent.getParContentList();
						wrobj.write("\\item ");
						writingParContent(wrobj, parContents);
					} else if (parContent.getType() == "ordered") {
						List<ParContent> parContents = parContent.getParContentList();
						wrobj.write("\\item ");
						writingParContent(wrobj, parContents);
					}
				    wrobj.newLine();
					//System.out.println(par.getParContent());
				} else if (secContent.getClass().getName() == "org.emed.classes.Markup") {
					Markup markup = (Markup) secContent;
					wrobj.write(markup.getContent());
					wrobj.newLine();
				} else if (secContent.getClass().getName() == "org.emed.classes.Figure") {
					Figure figure = (Figure) secContent;
					wrobj.write("\\begin{figure}");
					wrobj.newLine();
					List<ParContent> tabContents = figure.getParContent();
					for (ParContent parContent : tabContents) {
						if (parContent.getType() == "figureTitle") {
							List<ParContent> parContents = parContent.getParContentList();
							wrobj.write("\\caption{");
							writingParContent(wrobj, parContents);
							wrobj.write("}");
							wrobj.newLine();
						}
					}
					wrobj.write("\\label{" + figure.getId() + "}");
					wrobj.newLine();
					wrobj.write("\\includegraphics[width=\\linewidth ]{" + figure.getLink() + "}");
					wrobj.newLine();
					List<ParContent> tabCaption = figure.getParContent();
					for (ParContent parContent : tabCaption) {
						if (parContent.getType() == "figureCaption") {
							List<ParContent> parContents = parContent.getParContentList();
							wrobj.write("\\caption*{");
							writingParContent(wrobj, parContents);
							wrobj.write("}");
							wrobj.newLine();
						}
					}
					wrobj.write("\\end{figure}");
					wrobj.newLine();
				} else if (secContent.getClass().getName() == "org.emed.classes.Table") {
					Table table = (Table) secContent;
					wrobj.write("\\begin{table*}");
					wrobj.newLine();
					List<ParContent> tabContents = table.getParContent();
					for (ParContent parContent : tabContents) {
						if (parContent.getType() == "tableTitle") {
							List<ParContent> parContents = parContent.getParContentList();
							wrobj.write("\\caption{");
							writingParContent(wrobj, parContents);
							wrobj.write("}");
							wrobj.newLine();
						}
					}
					wrobj.write("\\label{" + table.getId() + "}\\centering");
					wrobj.newLine();
					
					String tabulary = "";
					for (int i = 0; i < table.getColumnNumber(); i++) {
						tabulary = tabulary + "C";	
					}
					wrobj.write("\\begin{tabulary}{1.0\\textwidth}{" + tabulary + "}");
					wrobj.newLine();
					ArrayList<Row> rows = table.getRow();
					for (Row row : rows) {
						if (row.getType() == "head") {
							wrobj.write("\\toprule");
							wrobj.newLine();
							ArrayList<Cell> cells = row.getCell();
							for (int i = 0; i < cells.size(); i++) {
								Cell cell = cells.get(i);
								if (cell.getColspan() == 1 && cell.getRowspan() == 1) {
									ArrayList<ParContent> cellContents = cell.getParContent();
									for (ParContent cellContent : cellContents) {
										List<ParContent> parContents = cellContent.getParContentList();
										wrobj.write("\\textbf{");
										writingParContent(wrobj, parContents);
										wrobj.write("}");
									}
									
								} else if (cell.getColspan() > 1 && cell.getRowspan() == 1) {
									ArrayList<ParContent> cellContents = cell.getParContent();
									for (ParContent cellContent : cellContents) {
										List<ParContent> parContents = cellContent.getParContentList();
										wrobj.write("\\multicolumn{" + cell.getColspan() + "}{c}{\\textbf{");
										writingParContent(wrobj, parContents);
										wrobj.write("}}");
									}
								} else if (cell.getColspan() == 1 && cell.getRowspan() > 1) {
									ArrayList<ParContent> cellContents = cell.getParContent();
									for (ParContent cellContent : cellContents) {
										List<ParContent> parContents = cellContent.getParContentList();
										wrobj.write("\\multirow{" + cell.getRowspan() + "}*{\\textbf{");
										writingParContent(wrobj, parContents);
										wrobj.write("}}");
									}
								}
								if (i < (cells.size() - 1)) {
									wrobj.write(" & ");
								} else {
									wrobj.write(" \\\\ ");
									wrobj.newLine();
								}
							}
							wrobj.write("\\midrule");
						} else if (row.getType() == "body") {
							
							wrobj.newLine();
							ArrayList<Cell> cells = row.getCell();
							String missedCells = "";
							if (cells.size() < table.getColumnNumber()) {
								int cellNumber = table.getColumnNumber() - cells.size();
								for (int i = 0; i < cellNumber; i++) {
									missedCells = missedCells + "& ";
								}	
							}
							wrobj.write(missedCells);
							for (int i = 0; i < cells.size(); i++) {
								Cell cell = cells.get(i);
								
								if (cell.getColspan() == 1 && cell.getRowspan() == 1) {
									ArrayList<ParContent> cellContents = cell.getParContent();
									for (ParContent cellContent : cellContents) {
										List<ParContent> parContents = cellContent.getParContentList();
										writingParContent(wrobj, parContents);
									}
									
								} else if (cell.getColspan() > 1 && cell.getRowspan() == 1) {
									ArrayList<ParContent> cellContents = cell.getParContent();
									for (ParContent cellContent : cellContents) {
										List<ParContent> parContents = cellContent.getParContentList();
										
										wrobj.write("\\multicolumn{" + cell.getColspan() + "}{c}{");
										writingParContent(wrobj, parContents);
										wrobj.write("}");
									}
								} else if (cell.getColspan() == 1 && cell.getRowspan() > 1) {
									ArrayList<ParContent> cellContents = cell.getParContent();
									for (ParContent cellContent : cellContents) {
										List<ParContent> parContents = cellContent.getParContentList();
										
										wrobj.write("\\multirow{" + cell.getRowspan() + "}*{");
										writingParContent(wrobj, parContents);
										wrobj.write("}");
									}
								}
								if (i < (cells.size() - 1)) {
									wrobj.write(" & ");
								} else {
									wrobj.write(" \\\\ ");
									wrobj.newLine();
								}
							}
						}
					}
					wrobj.write("\\bottomrule");
					wrobj.newLine();
					wrobj.write("\\end{tabulary}");
					wrobj.newLine();
					wrobj.write("\\\\");
					wrobj.newLine();
					List<ParContent> tabCaption = table.getParContent();
					for (ParContent parContent : tabCaption) {
						if (parContent.getType() == "tableCaption") {
							List<ParContent> parContents = parContent.getParContentList();
							wrobj.write("\\caption*{");
							writingParContent(wrobj, parContents);
							wrobj.write("}");
							wrobj.newLine();
						}
					}
					wrobj.newLine();
					wrobj.write("\\end{table*}");
					wrobj.newLine();
					
			    } else if (secContent.getClass().getName() == "org.emed.classes.SubSec") {
					subSection(wrobj, secContent);
				}
			}
		} //end of Sections
	}

	private static void writingParContent(BufferedWriter wrobj, List<ParContent> parContents) throws IOException {
		for (ParContent parContentPar : parContents) {
			
			if (parContentPar.getClass().getName() == "org.emed.classes.Par") {
				Par par = (Par) parContentPar;
				wrobj.write(par.getContent());
			} else if (parContentPar.getClass().getName() == "org.emed.classes.Italic") {
				Italic italic = (Italic) parContentPar;
				wrobj.write("\\textit{" + italic.getContent() + "}");
			} else if (parContentPar.getClass().getName() == "org.emed.classes.Bold") {
				Bold bold = (Bold) parContentPar;
				wrobj.write("\\textbf{" + bold.getContent() + "}");
		    } else if (parContentPar.getClass().getName() == "org.emed.classes.Xref") {
				Xref xref = (Xref) parContentPar;
				
				if (xref.getBibContent() != null) {
				    wrobj.write("\\cite{bib" + xref.getBibContent() + "}");
				} else if (xref.getFigContent() != null) {   
					wrobj.write(xref.getFigDescription() + " " + xref.getFigContent());
				} else if (xref.getTableContent() != null) {
					wrobj.write(xref.getTableDescription() + " " + xref.getTableContent());
				}
				
				
			} 
				
		} // end of Paragraph content
	}

	private static void subSection(BufferedWriter wrobj, SecContent secContent) throws IOException {
		SubSec subSec = (SubSec) secContent;
		wrobj.write(subSec.getTitle());
		wrobj.newLine();
		List<SecContent> subSecContents = subSec.getSecContent();
		for (SecContent subSecContent : subSecContents) {
			if (subSecContent.getClass().getName() == "org.emed.classes.ParContent") {
				ParContent subParContent = (ParContent) subSecContent;
				if (subParContent.getType() == "par") {
					wrobj.write("\\par ");
					List<ParContent> subPars = subParContent.getParContentList();
					writingParContent(wrobj, subPars);
				} else if (subParContent.getType() == "unordered") {
					wrobj.write("\\item ");
					List<ParContent> subPars = subParContent.getParContentList();
					writingParContent(wrobj, subPars);
				} else if (subParContent.getType() == "ordered") {
					wrobj.write("\\item ");
					List<ParContent> subPars = subParContent.getParContentList();
					writingParContent(wrobj, subPars);
				}
			wrobj.newLine();
			} else if (subSecContent.getClass().getName() == "org.emed.classes.Markup") {
				Markup markup = (Markup) subSecContent;
				wrobj.write(markup.getContent());
				wrobj.newLine();
			} else if (subSecContent.getClass().getName() == "org.emed.classes.Figure") {
				Figure figure = (Figure) subSecContent;
				wrobj.write("\\begin{figure}");
				wrobj.newLine();
				List<ParContent> tabContents = figure.getParContent();
				for (ParContent parContent : tabContents) {
					if (parContent.getType() == "figureTitle") {
						List<ParContent> parContents = parContent.getParContentList();
						wrobj.write("\\caption{");
						writingParContent(wrobj, parContents);
						wrobj.write("}");
						wrobj.newLine();
					}
				}
				wrobj.write("\\label{" + figure.getId() + "}");
				wrobj.newLine();
				wrobj.write("\\includegraphics[width=\\linewidth ]{" + figure.getLink() + "}");
				wrobj.newLine();
				List<ParContent> tabCaption = figure.getParContent();
				for (ParContent parContent : tabCaption) {
					if (parContent.getType() == "figureCaption") {
						List<ParContent> parContents = parContent.getParContentList();
						wrobj.write("\\caption*{");
						writingParContent(wrobj, parContents);
						wrobj.write("}");
						wrobj.newLine();
					}
				}
				wrobj.write("\\end{figure}");
				wrobj.newLine();
		    } else if (subSecContent.getClass().getName() == "org.emed.classes.Table") {
				Table table = (Table) subSecContent;
				wrobj.write("\\begin{table*}");
				wrobj.newLine();
				List<ParContent> tabContents = table.getParContent();
				for (ParContent parContent : tabContents) {
					if (parContent.getType() == "tableTitle") {
						List<ParContent> parContents = parContent.getParContentList();
						wrobj.write("\\caption{");
						writingParContent(wrobj, parContents);
						wrobj.write("}");
						wrobj.newLine();
					}
				}
				wrobj.write("\\label{" + table.getId() + "}\\centering");
				wrobj.newLine();
				wrobj.write("\\\\");
				wrobj.newLine();
				List<ParContent> tabCaption = table.getParContent();
				for (ParContent parContent : tabCaption) {
					if (parContent.getType() == "tableCaption") {
						List<ParContent> parContents = parContent.getParContentList();
						wrobj.write("\\caption*{");
						writingParContent(wrobj, parContents);
						wrobj.write("}");
						wrobj.newLine();
					}
				}
				wrobj.newLine();
				wrobj.write("\\end{table*}");
				wrobj.newLine();
			
		    } else if (subSecContent.getClass().getName() == "org.emed.classes.SubSec") {
				subSection(wrobj, subSecContent);
			}
		}
	}
}
