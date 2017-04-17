package org.emed.latex.gost;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map.Entry;

import org.emed.classes.AffilNumbers;
import org.emed.classes.Affiliation;
import org.emed.classes.ArticleMeta;
import org.emed.classes.Author;
import org.emed.classes.Par;

public class MetaGost {
	public static void meta(BufferedWriter wrobj, ArticleMeta articleMeta) throws IOException {
		wrobj.write("\\documentclass[twocolumn]{article}");
		wrobj.newLine();
		wrobj.write("\\usepackage[left=48pt,right=46pt]{geometry}");
		wrobj.newLine();
		wrobj.write("\\date{}");
		wrobj.newLine();
		wrobj.write("\\usepackage{polyglossia}");
		wrobj.newLine();
		wrobj.write("\\setmainfont{Cambria}");
		wrobj.newLine();
		wrobj.write("\\setmonofont{Liberation Mono}");
		wrobj.newLine();
		wrobj.write("\\setmainlanguage{ukrainian}");
		wrobj.newLine();
		wrobj.write("\\setotherlanguage{english}");
		wrobj.newLine();
		wrobj.write("\\usepackage{rotating}");
		wrobj.newLine();
		wrobj.write("\\usepackage{caption}");
		wrobj.newLine();
		wrobj.write("\\usepackage{authblk}");
		wrobj.newLine();
		wrobj.write("\\usepackage{indentfirst}");
		wrobj.newLine();
		wrobj.write("\\usepackage{threeparttable}");
		wrobj.newLine();
		wrobj.write("\\usepackage{tablefootnote}");
		wrobj.newLine();
		wrobj.write("\\usepackage{graphicx}");
		wrobj.newLine();
		wrobj.write("\\usepackage{multirow}");
		wrobj.newLine();
		wrobj.write("\\usepackage{longtable}");
		wrobj.newLine();
		wrobj.write("\\usepackage{wrapfig}");
		wrobj.newLine();
		wrobj.write("\\usepackage{array}");
		wrobj.newLine();
		wrobj.write("\\usepackage{booktabs,siunitx}");
		wrobj.newLine();
		wrobj.write("\\usepackage{mathtext}");
		wrobj.newLine();
		wrobj.write("\\usepackage{lineno,hyperref}");
		wrobj.newLine();
		wrobj.write("\\usepackage{tabulary}");
		wrobj.newLine();
		wrobj.write("\\modulolinenumbers[5]");
		wrobj.newLine();
		wrobj.write("\\usepackage{fancyhdr}");
		wrobj.newLine();
		wrobj.write("\\pagestyle{fancy}");
		wrobj.newLine();
		wrobj.write("\\fancyhead{}");
		wrobj.newLine();
		wrobj.write("\\fancyfoot[C]{\\thepage}");
		wrobj.newLine();
		if (articleMeta.getId() != null) {
			wrobj.write("\\fancyhead[C]{Психосоматична медицина та загальна практика" + " $\\bullet$ " + articleMeta.getMonthUkr() + " " + articleMeta.getYear() +  " $\\bullet$ " + " Т. " + articleMeta.getVolume() + ", №. " + articleMeta.getIssue() + " $\\bullet$ " + articleMeta.getId() + "}");
		} else {
			wrobj.write("\\fancyhead[C]{" + articleMeta.getJournal() + " $\\bullet$ " + articleMeta.getMonth() + " " + articleMeta.getYear() +  " $\\bullet$ " + " V. " + articleMeta.getVolume() + ", I. " + articleMeta.getIssue() + "}");
		}
		wrobj.newLine();
		wrobj.write("\\fancyfoot[RO,LE]{}");
		wrobj.newLine();
		wrobj.write("\\setlength\\parindent{20pt}");
		wrobj.newLine();
		wrobj.write("\\setlength{\\parskip}{2pt}");
		wrobj.newLine();
		wrobj.write("\\begin{document}");
		wrobj.newLine();
		
		wrobj.write("\\title{");
		wrobj.newLine();
		if (articleMeta.getJournal().contains("Psychosomatic Medicine and General Practice")) {
			wrobj.write("\\smash{\\includegraphics[scale=0.15]{logo}}\\\\[-1.5cm]");
			wrobj.newLine();
		} else {
			wrobj.write("\\smash\\\\");
			wrobj.newLine();
		}
		wrobj.write("\\bfseries \\qquad " + "Психосоматична медицина та загальна практика" + "\\\\[0.5cm]{");
		wrobj.newLine();
		wrobj.write("\\flushleft\\small{УДК: " + articleMeta.getUdc() + "\\hfill }\\\\[0.4cm]}{");
		wrobj.newLine();
		wrobj.write("\\LARGE \\bfseries " + articleMeta.getTitleUkr());
		wrobj.newLine();
		wrobj.write("}}");
		wrobj.newLine();
		
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
			
			wrobj.write("\\author[" + affilNumbers + "]{" + entry.getValue().getSurname() + " " + entry.getValue().getGiven().replaceAll("[a-z]", "") + ".}");
			wrobj.newLine();
			
		}
		
		for (Entry<Integer, Affiliation> entry : articleMeta.getInstitution().entrySet()) {
			wrobj.write("\\affil[" + entry.getKey() + "]" + "{" + entry.getValue().getInstitution() + "}");
			wrobj.newLine();
		}
		
		wrobj.write("\\twocolumn[");
		wrobj.newLine();
		wrobj.write("\\begin{@twocolumnfalse}");
		wrobj.newLine();
		wrobj.write("\\maketitle");
		wrobj.newLine();
		wrobj.write("\\begin{center}");
		wrobj.newLine();
		wrobj.write("{\\Large \\textbf{Анотація}} \\vspace{1ex}\\\\");
		wrobj.newLine();
		wrobj.write("\\end{center}");
		wrobj.newLine();
		
		// Abstract
		
		for(Entry<String, String> entry: articleMeta.getAbstractUkr().entrySet()) {
			if (articleMeta.getAbstractUkr().size() > 1) {
				wrobj.write("\\textbf{" + entry.getKey().trim() + ". }" + Par.replacement(entry.getValue()) + " \\vspace{1ex}\\\\");
				wrobj.newLine();
			} else if (articleMeta.getAbstractUkr().size() == 1) {
				wrobj.write(entry.getValue() + " \\vspace{1ex}\\\\");
				wrobj.newLine();
			}
		}
		// Keywords
		if (articleMeta.getKeywords() != null) {
			wrobj.write("{\\textbf{Ключові слова:}} ");
			for(int i = 0; i < articleMeta.getKeywords().size(); i++) {
				if (i == (articleMeta.getKeywords().size() - 1)) {
					wrobj.write("\\texttt{" + articleMeta.getKeywords().get(i) + "}");
				} else {
					wrobj.write("\\texttt{" + articleMeta.getKeywords().get(i) + "}, ");
					wrobj.newLine();
				}
			}
		}
		wrobj.write("\\vspace{5ex}");
		wrobj.newLine();
		wrobj.write("\\end{@twocolumnfalse}]");
		wrobj.newLine();
		   
	}
	
	public static void metaSecond(BufferedWriter wrobj, ArticleMeta articleMeta) throws IOException {
		wrobj.write("\\begin{center}");
		wrobj.newLine();
		wrobj.write("\\onecolumn");
		wrobj.newLine();
		wrobj.write("{\\LARGE \\bfseries " + articleMeta.getTitleEng() + "}\\\\[0.5cm]");
		wrobj.newLine();
		for (Entry<AffilNumbers, Author> entry: articleMeta.getName().entrySet()) {
			String affilNumbers = "";
			for (String affil : entry.getKey().getAffilnumbers()) {
				affilNumbers = "\\textsuperscript{" + affil + "}" + affilNumbers;
			}
			wrobj.write("\\large " + entry.getValue().getSurname() + " " + entry.getValue().getGiven().replaceAll("[a-z]", "") + ". " + affilNumbers);
		}
		wrobj.write("\\\\[0.3cm]");
		for (Entry<Integer, Affiliation> entry : articleMeta.getInstitution().entrySet()) {
			wrobj.write("\\textsuperscript{" + entry.getKey() + "}" + entry.getValue().getInstitution() + "\\\\");
			wrobj.newLine();
		}
		wrobj.write("\\vspace{2ex}");
		wrobj.newLine();
		wrobj.write("{\\Large \\textbf{Abstract}}\\vspace{3ex}\\");
		wrobj.newLine();
		wrobj.write("\\end{center}");
		wrobj.newLine();
		// Abstract
		
				for(Entry<String, String> entry: articleMeta.getAbstractEng().entrySet()) {
					if (articleMeta.getAbstractEng().size() > 1) {
						wrobj.write("\\textbf{" + entry.getKey().trim() + ". }" + Par.replacement(entry.getValue()) + " \\vspace{1ex}\\\\");
						wrobj.newLine();
					} else if (articleMeta.getAbstractEng().size() == 1) {
						wrobj.write(entry.getValue() + " \\vspace{1ex}\\\\");
						wrobj.newLine();
					}
				}
				// Keywords
				if (articleMeta.getKeywords() != null) {
					wrobj.write("\\textbf{Keywords:} ");
					for(int i = 0; i < articleMeta.getKeywords().size(); i++) {
						if (i == (articleMeta.getKeywords().size() - 1)) {
							wrobj.write("\\texttt{" + articleMeta.getKeywords().get(i) + "}");
						} else {
							wrobj.write("\\texttt{" + articleMeta.getKeywords().get(i) + "}, ");
							wrobj.newLine();
						}
					}
				}
		
		wrobj.newLine();
		wrobj.write("\\end{document}");
		wrobj.close();
	}

}
