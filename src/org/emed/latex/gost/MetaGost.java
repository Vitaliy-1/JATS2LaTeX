package org.emed.latex.gost;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map.Entry;

import org.emed.classes.Affiliation;
import org.emed.classes.ArticleMeta;

public class MetaGost {
	public static void meta(BufferedWriter wrobj, ArticleMeta articleMeta) throws IOException {
		wrobj.write("\\documentclass[twocolumn]{article}");
		wrobj.newLine();
		wrobj.write("\\usepackage[left=48pt,right=46pt]{geometry}");
		wrobj.newLine();
		wrobj.write("\\date{}");
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
		wrobj.write("\\usepackage{wrapfig}");
		wrobj.newLine();
		wrobj.write("\\usepackage{array}");
		wrobj.newLine();
		wrobj.write("\\usepackage{booktabs,siunitx}");
		wrobj.newLine();
		wrobj.write("\\usepackage{mathtext}");
		wrobj.newLine();
		wrobj.write("\\usepackage[T2A]{fontenc}");
		wrobj.newLine();
		wrobj.write("\\usepackage[utf8]{inputenc}");
		wrobj.newLine();
		wrobj.write("\\usepackage[ukrainian]{babel}");
		wrobj.newLine();
		wrobj.write("\\usepackage{lineno,hyperref}");
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
		wrobj.write("\\fancyhead[C]{Психосоматична медицина та загальна практика $\\bullet$ Листопад 2016 $\\bullet$ Т. 1, № 1 $\\bullet$ e010113}");
		wrobj.newLine();
		wrobj.write("\\fancyfoot[RO,LE]{}");
		wrobj.newLine();
		wrobj.write("\\tolerance=10000");
		wrobj.newLine();
		wrobj.write("\\begin{document}");
		wrobj.newLine();
		
		for (Entry<Integer, Affiliation> entry : articleMeta.getInstitution().entrySet()) {
		    Integer key = entry.getKey();
		    Affiliation affiliation = entry.getValue();
		    System.out.println(key + ": " + affiliation.getInstitution() + ", " + affiliation.getCity() + ", " + affiliation.getCountry());
		}
	}

}
