package org.emed.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.emed.classes.ArticleMeta;
import org.emed.classes.Bibitem;
import org.emed.classes.Section;
import org.emed.latex.gost.*;

public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, IllegalArgumentException, IllegalAccessException {
		
		BufferedWriter wrobj = new BufferedWriter (new FileWriter("output2.tex"));
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("cira_revised.xml");
		XPath xPath =  XPathFactory.newInstance().newXPath();
		
		/* parsing data from JATS XML document 
		 * 
		 * parsing metadata from xml */
		ArticleMeta articleMeta = Meta.meta(document, xPath);
		
		/* parsing sections from xml  */
		List<Section> listSections = Body.body(document, xPath);
		
		/* parsing references from xml */
		List<Bibitem> bibitems = Back.back(document, xPath);
		
		
		/* writing to LaTeX (GOST standard)
		 * 
		 * writing metadata
		 * */
		MetaGost.meta(wrobj, articleMeta);
		
		/* writing sections to the latex (gost) */
		BodyGost.body(wrobj, listSections);
		
		/* writing bibliography to the latex (gost)*/
		BackGost.back(wrobj, bibitems);
		
	}	

}