package org.emed.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import org.emed.classes.ArticleMeta;
import org.emed.classes.Bibitem;
import org.emed.classes.LaTeX;
import org.emed.classes.Section;
import org.emed.latex.standard.BackBib;
import org.emed.latex.standard.BodyStandard;
import org.emed.latex.standard.MetaStandard;

public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, IllegalArgumentException, IllegalAccessException, org.emed.main.CustomExceptions {
		String inputFile = args[0];
		if (!(inputFile.endsWith(".xml"))) {
    		throw new CustomExceptions("Input file extension must be .xml");
    	}
		
		// path to LaTeX in Stadard format
		String outputLatexStandard = args[1];
		
		// path to bibtex
		String outputBib = args[2];
		
		writerToFile(inputFile, outputLatexStandard, outputBib);
		
	}

	private static void writerToFile(String inputFile, String outputLatexStandard, String outputBib) throws IOException,
			ParserConfigurationException, SAXException, XPathExpressionException, DOMException, NumberFormatException {
		
		// writing LaTeX in World Standard
		Path latexStandard = Paths.get(outputLatexStandard);
		BufferedWriter wrlatex = Files.newBufferedWriter(latexStandard, StandardCharsets.UTF_8);
		
		// writing bibtex
		Path bibtexStandard = Paths.get(outputBib);
		BufferedWriter bib = Files.newBufferedWriter(bibtexStandard, StandardCharsets.UTF_8);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(inputFile);
		XPath xPath =  XPathFactory.newInstance().newXPath();
		
		/* parsing JATS XML */
		LaTeX latex = jatsParser(document, xPath);
		
		/* creating reference to a bib with regex */
		String referenceLink = outputBib.trim().replaceAll(".bib$", "");
		if (referenceLink.contains("\\") || referenceLink.contains("/")) {
			Pattern p = Pattern.compile("(\\w+)$");
			Matcher m = p.matcher(referenceLink);
			if (m.find()) {
				referenceLink = m.group();
			}
		} 
		
		/* writing to LaTeX (GOST, standard, bib) */
		latexStandardWriter(wrlatex, latex, referenceLink);
		bibWriter(bib, latex);
	}

	
	private static void latexStandardWriter(BufferedWriter wrlatex, LaTeX latex, String referenceLink) throws IOException {
		
		/* writing meta to the latex (standard) */
		MetaStandard.meta(wrlatex, latex.getArticleMeta());
		
		/* writing sections to the latex (gost) */
		BodyStandard.body(wrlatex, latex.getSection(), referenceLink);
		
	}
	
	private static void bibWriter(BufferedWriter bib, LaTeX latex) throws IOException {
		
		/* writing references to the bib file */
		BackBib.back(bib, latex.getBibitem());	
	}


	private static LaTeX jatsParser(Document document, XPath xPath)
			throws XPathExpressionException, DOMException, NumberFormatException {
		
		LaTeX latex = new LaTeX ();	
		 /* parsing metadata from xml */
        ArticleMeta articleMeta = Meta.meta(document, xPath);
        
       
		/* parsing sections from xml  */
		ArrayList<Section> listSections = Body.body(document, xPath);
		
		/* parsing references from xml */
		ArrayList<Bibitem> bibitems = Back.back(document, xPath);
		
		
		latex.setArticleMeta(articleMeta);
		latex.setSection(listSections);
		latex.setBibitem(bibitems);
		return latex;
	}

	

}