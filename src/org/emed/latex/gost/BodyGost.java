package org.emed.latex.gost;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import org.emed.classes.Italic;
import org.emed.classes.ItemList;
import org.emed.classes.Markup;
import org.emed.classes.Par;
import org.emed.classes.ParContent;
import org.emed.classes.SecContent;
import org.emed.classes.Section;
import org.emed.classes.SubSec;
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
					List<ParContent> parContents = parContent.getParContentList();
					wrobj.write("\\par ");
					for (ParContent parContentPar : parContents) {
						
						if (parContentPar.getClass().getName() == "org.emed.classes.Par") {
							Par par = (Par) parContentPar;
							wrobj.write(par.getContent().trim());
						} else if (parContentPar.getClass().getName() == "org.emed.classes.Italic") {
							Italic italic = (Italic) parContentPar;
							wrobj.write("\\textit{" + italic.getContent() + "}");
						} else if (parContentPar.getClass().getName() == "org.emed.classes.Xref") {
							Xref xref = (Xref) parContentPar;
							
							if (xref.getBibContent() != null) {
							    wrobj.write(xref.getBibContent());
							} else if (xref.getFigContent() != null) {   
								wrobj.write(xref.getFigDescription() + " " + xref.getFigContent());
							} else if (xref.getTableContent() != null) {
								wrobj.write(xref.getTableDescription() + " " + xref.getTableContent());
							}
							
							
						} 
							
					} // end of Paragraph content
					
				wrobj.newLine();
					//System.out.println(par.getParContent());
				} else if (secContent.getClass().getName() == "org.emed.classes.ItemList") {
					ItemList subList = (ItemList) secContent;
					wrobj.write(subList.getListContent());
					wrobj.newLine();
				} else if (secContent.getClass().getName() == "org.emed.classes.Markup") {
					Markup markup = (Markup) secContent;
					wrobj.write(markup.getContent());
					wrobj.newLine();
				}
				
				if (secContent.getClass().getName() == "org.emed.classes.SubSec") {
					subSection(wrobj, secContent);
				}
			}
		} //end of Sections
	}

	private static void subSection(BufferedWriter wrobj, SecContent secContent) throws IOException {
		SubSec subSec = (SubSec) secContent;
		wrobj.write(subSec.getTitle());
		wrobj.newLine();
		List<SecContent> subSecContents = subSec.getSecContent();
		for (SecContent subSecContent : subSecContents) {
			//System.out.println(subSecContent.getClass().getName());	
			if (subSecContent.getClass().getName() == "org.emed.classes.ParContent") {
				ParContent subParContent = (ParContent) subSecContent;
				wrobj.write("\\par ");
				List<ParContent> subPars = subParContent.getParContentList();
				for (ParContent subPar : subPars) {
					if (subPar.getClass().getName() == "org.emed.classes.Par") {
						wrobj.write(subPar.getContent());
					} else if (subPar.getClass().getName() == "org.emed.classes.Italic") {
						Italic italic = (Italic) subPar;
						wrobj.write("\\textit{" + italic.getContent() + "}");
					} else if (subPar.getClass().getName() == "org.emed.classes.Xref") {
						Xref xref = (Xref) subPar;
						if (xref.getBibContent() != null) {
							wrobj.write(xref.getBibContent());
						} else if (xref.getFigContent() != null) {
							wrobj.write(" " + xref.getFigDescription() + " " + xref.getFigContent());
						} else if (xref.getTableContent() != null) {
							wrobj.write(" " + xref.getTableDescription() + " " + xref.getTableContent());
						}
					}
				} // end of Paragraph content
			wrobj.newLine();
			} else if (subSecContent.getClass().getName() == "org.emed.classes.ItemList"){
				ItemList subList = (ItemList) subSecContent;
				wrobj.write(subList.getListContent());
				wrobj.newLine();
			} else if (subSecContent.getClass().getName() == "org.emed.classes.Markup") {
				Markup markup = (Markup) subSecContent;
				wrobj.write(markup.getContent());
				wrobj.newLine();
			} else if (subSecContent.getClass().getName() == "org.emed.classes.SubSec") {
				subSection(wrobj, subSecContent);
			}
		}
	}
}
