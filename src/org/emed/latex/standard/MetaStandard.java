package org.emed.latex.standard;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map.Entry;

import org.emed.classes.AffilNumbers;
import org.emed.classes.Affiliation;
import org.emed.classes.ArticleMeta;
import org.emed.classes.Author;
import org.emed.classes.Par;

public class MetaStandard {
	public static void meta(BufferedWriter wrlatex, ArticleMeta articleMeta) throws IOException {
		wrlatex.write("\\documentclass[twocolumn]{article}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage[left=48pt,right=46pt]{geometry}");
		wrlatex.newLine();
		wrlatex.write("\\date{}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{polyglossia}");
		wrlatex.newLine();
		wrlatex.write("\\setmainfont{Cambria}");
		wrlatex.newLine();
		wrlatex.write("\\setmonofont{Liberation Mono}");
		wrlatex.newLine();
		if (articleMeta.getJournal().contains("Psychosomatic Medicine and General Practice")) {
			wrlatex.write("\\setmainlanguage{ukrainian} % can be changed to desired language");
			wrlatex.newLine();
			wrlatex.write("\\setotherlanguage{english}");
			wrlatex.newLine();
		} else {
			wrlatex.write("\\setmainlanguage{english}");
			wrlatex.newLine();
		}
		wrlatex.write("\\bibliographystyle{vancouver}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{microtype}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{rotating}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{caption}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{authblk}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{indentfirst}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{threeparttable}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{tablefootnote}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{graphicx}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{multirow}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{longtable}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{wrapfig}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{array}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{booktabs,siunitx}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{mathtext}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage[hyphens]{url}");
		wrlatex.newLine();
		wrlatex.write("\\urlstyle{same}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{lineno,hyperref}");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{tabulary}");
		wrlatex.newLine();
		wrlatex.write("\\modulolinenumbers[5]");
		wrlatex.newLine();
		wrlatex.write("\\usepackage{fancyhdr}");
		wrlatex.newLine();
		wrlatex.write("\\pagestyle{fancy}");
		wrlatex.newLine();
		wrlatex.write("\\fancyhead{}");
		wrlatex.newLine();
		wrlatex.write("\\fancyfoot[C]{\\thepage}");
		wrlatex.newLine();
		wrlatex.write("\\fancyhead[C]{");
		if (articleMeta.getJournal() != null) {
			wrlatex.write(articleMeta.getJournal() + " $\\bullet$ ");
		}
		if (articleMeta.getMonth() != null) {
			wrlatex.write(articleMeta.getMonth() + " ");
		}
		if (articleMeta.getYear() != 0) {
			wrlatex.write(articleMeta.getYear() + " $\\bullet$ ");
		}
		if (articleMeta.getVolume() != 0) {
			wrlatex.write(" V. " + articleMeta.getVolume());
		}
		if (articleMeta.getIssue() != 0) {
			wrlatex.write(", I. " + articleMeta.getIssue());
		}
		if (articleMeta.getId() != null) {
			wrlatex.write(" $\\bullet$ " + articleMeta.getId());
		}
		wrlatex.write("}");
		wrlatex.newLine();
		wrlatex.write("\\fancyfoot[RO,LE]{}");
		wrlatex.newLine();
		wrlatex.write("\\setlength\\parindent{20pt}");
		wrlatex.newLine();
		wrlatex.write("\\setlength{\\parskip}{2pt}");
		wrlatex.newLine();
		wrlatex.write("\\begin{document}");
		wrlatex.newLine();
		
		wrlatex.write("\\title{");
		wrlatex.newLine();
		if (articleMeta.getJournal().contains("Psychosomatic Medicine and General Practice")) {
			wrlatex.write("\\smash{\\includegraphics[scale=0.15]{logo}}\\\\[-1.5cm]");
			wrlatex.newLine();
		} else {
			wrlatex.write("\\\\[-1.5cm]");
			wrlatex.newLine();
		}
		wrlatex.write("\\bfseries \\qquad ");
		if (articleMeta.getJournal() != null) {
			wrlatex.write(articleMeta.getJournal());
		}
		wrlatex.write("\\\\[0.5cm]{");
		wrlatex.newLine();
		if (articleMeta.getDoi() != null && articleMeta.getUdc() == null) {
			wrlatex.write("\\flushleft\\small{DOI: " + articleMeta.getDoi() + "\\hfill }\\\\[0.4cm]}{");
		} else if (articleMeta.getDoi() == null && articleMeta.getUdc() != null) {
			wrlatex.write("\\flushleft\\small{UDC: " + articleMeta.getUdc() + "\\hfill }\\\\[0.4cm]}{");
		} else if (articleMeta.getDoi() != null && articleMeta.getUdc() != null) {
			wrlatex.write("\\flushleft\\small{DOI: " + articleMeta.getDoi() + "\\hfill }\\\\[0.4cm]");
			wrlatex.newLine();
			wrlatex.write("\\flushleft\\small{UDC: " + articleMeta.getUdc() + "\\hfill }\\\\[0.4cm]}{");
		} else {
			wrlatex.write("}{");
		}
		wrlatex.newLine();
		wrlatex.write("\\LARGE \\bfseries " + articleMeta.getTitleEng());
		wrlatex.newLine();
		wrlatex.write("}}");
		wrlatex.newLine();
		
		for (Entry<AffilNumbers, Author> entry : articleMeta.getName().entrySet()) {
			String affilNumbers = null;
			if (entry.getKey().getAffilnumbers().size() == 1) {
				for (String affil : entry.getKey().getAffilnumbers()) {
					affilNumbers = affil;
					
				}
			} else if (entry.getKey().getAffilnumbers().size() > 1) {
				for (int j = 0; j < entry.getKey().getAffilnumbers().size(); j++) {
					if (j == 0) {
						affilNumbers = entry.getKey().getAffilnumbers().get(j) + ",";
					} else if (j != 0 && j < (entry.getKey().getAffilnumbers().size() - 1)) {
						affilNumbers += entry.getKey().getAffilnumbers().get(j) + ",";
					} else {
						affilNumbers += entry.getKey().getAffilnumbers().get(j);
					}
				}
			}
			
			wrlatex.write("\\author[" + affilNumbers + "]{" + entry.getValue().getSurname() + " " + entry.getValue().getGiven().replaceAll("[a-z]", "") + ".}");
			wrlatex.newLine();
			
		}
		
		for (Entry<Integer, Affiliation> entry : articleMeta.getInstitution().entrySet()) {
			wrlatex.write("\\affil[" + entry.getKey() + "]" + "{" + entry.getValue().getInstitution() + "}");
			wrlatex.newLine();
		}
		
		wrlatex.write("\\twocolumn[");
		wrlatex.newLine();
		wrlatex.write("\\begin{@twocolumnfalse}");
		wrlatex.newLine();
		wrlatex.write("\\maketitle");
		wrlatex.newLine();
		wrlatex.write("\\begin{center}");
		wrlatex.newLine();
		wrlatex.write("{\\Large \\textbf{Анотація}} \\vspace{1ex}\\\\");
		wrlatex.newLine();
		wrlatex.write("\\end{center}");
		wrlatex.newLine();
		
		// Abstract
		
		for(Entry<String, String> entry: articleMeta.getAbstractEng().entrySet()) {
			if (articleMeta.getAbstractEng().size() > 1) {
				wrlatex.write("\\textbf{" + entry.getKey().trim() + ". }" + Par.replacement(entry.getValue()) + " \\vspace{1ex}\\\\");
				wrlatex.newLine();
			} else if (articleMeta.getAbstractEng().size() == 1) {
				wrlatex.write(entry.getValue() + " \\vspace{1ex}\\\\");
				wrlatex.newLine();
			}
		}
		// Keywords
		if (articleMeta.getKeywords() != null) {
			wrlatex.write("{\\textbf{Ключові слова:}} ");
			for(int i = 0; i < articleMeta.getKeywords().size(); i++) {
				if (i == (articleMeta.getKeywords().size() - 1)) {
					wrlatex.write("\\texttt{" + articleMeta.getKeywords().get(i) + "}");
				} else {
					wrlatex.write("\\texttt{" + articleMeta.getKeywords().get(i) + "}, ");
					wrlatex.newLine();
				}
			}
		}
		wrlatex.write("\\vspace{5ex}");
		wrlatex.newLine();
		wrlatex.write("\\end{@twocolumnfalse}]");
		wrlatex.newLine();
		   
	}
}
