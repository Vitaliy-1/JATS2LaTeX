package org.emed.main;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.emed.classes.Cell;
import org.emed.classes.ParContent;
import org.emed.classes.Row;
import org.emed.classes.Table;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TableFunctions {
	protected static void countColumns(XPath xPath, Table table, Node tableHead)
			throws XPathExpressionException, DOMException, NumberFormatException {
		if (tableHead != null) {
			Node firstRow = (Node) xPath.compile("*[1]").evaluate(tableHead, XPathConstants.NODE);
			NodeList childFirstRows = firstRow.getChildNodes();
			int columnNumber = 0;
			for (int w = 0; w < childFirstRows.getLength(); w++) {
				Node childFirstRow = childFirstRows.item(w);
				if (childFirstRow.getNodeValue() == null && (childFirstRow.getNodeName() == "th" || childFirstRow.getNodeName() == "td")) {
					int number = Integer.parseInt(childFirstRow.getAttributes().getNamedItem("colspan").getNodeValue());
					columnNumber = columnNumber + number;
				}
			}
			table.setColumnNumber(columnNumber);
		}
	}
	
	protected static void cellParsing(XPath xPath, Table table, Node tableHead, String rowType)
			throws XPathExpressionException, NumberFormatException, DOMException {
		if (tableHead != null) {
			NodeList trHeadNodes = (NodeList) xPath.compile("tr").evaluate(tableHead, XPathConstants.NODESET);
			for (int y = 0; y < trHeadNodes.getLength(); y++) {
				Row row = new Row();
				row.setType(rowType);
				table.getRow().add(row);
				NodeList cellNodes = (NodeList) xPath.compile("th|td").evaluate(trHeadNodes.item(y), XPathConstants.NODESET);
				for (int w = 0; w < cellNodes.getLength(); w++) {
					Cell cell = new Cell();
					row.getCell().add(cell);
					Node cellNode = cellNodes.item(w);
					if (cellNode != null && cellNode.getAttributes() != null && cellNode.getAttributes().getNamedItem("colspan") != null) {
						cell.setColspan(Integer.parseInt(cellNode.getAttributes().getNamedItem("colspan").getNodeValue()));
						cell.setRowspan(Integer.parseInt(cellNode.getAttributes().getNamedItem("rowspan").getNodeValue()));
						ParContent parContent = new ParContent();
						parContent.setType("tableCell");
						cell.getParContent().add(parContent);
						Body.parsingParContent(cellNode, parContent);
					}
					
				}
				
			}
		}
	}
}
