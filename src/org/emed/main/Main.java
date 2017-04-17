package org.emed.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
import org.emed.latex.gost.*;

public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, IllegalArgumentException, IllegalAccessException {
		String inputFile = "cira_revised.xml";
		String outputFile = "cira_revised.tex";
		
		writerToFile(inputFile, outputFile);
		
	}

	private static void writerToFile(String inputFile, String outputFile) throws IOException,
			ParserConfigurationException, SAXException, XPathExpressionException, DOMException, NumberFormatException {
		//BufferedWriter wrobj = new BufferedWriter (new FileWriter("output2.tex"));
		Path logFile = Paths.get(outputFile);
		BufferedWriter wrobj = Files.newBufferedWriter(logFile, StandardCharsets.UTF_8);
		//BufferedWriter wrobj = new BufferedWriter (new FileWriter(outputFile));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		//Document document = builder.parse("cira_revised.xml");
		Document document = builder.parse(inputFile);
		XPath xPath =  XPathFactory.newInstance().newXPath();
		
		/* parsing JATS XML */
		LaTeX latex = jatsParser(document, xPath);
		
		/* writing to LaTeX (GOST standard) */
		latexGostWriter(wrobj, latex);
	}

	private static void latexGostWriter(BufferedWriter wrobj, LaTeX latex) throws IOException {
		
		/* writing meta to the latex (gost) */
		MetaGost.meta(wrobj, latex.getArticleMeta());
		
		/* writing sections to the latex (gost) */
		BodyGost.body(wrobj, latex.getSection());
		
		/* writing bibliography to the latex (gost)*/
		BackGost.back(wrobj, latex.getBibitem());
		
		/* writing second meta section */
		MetaGost.metaSecond(wrobj, latex.getArticleMeta());
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