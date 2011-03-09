/*
 * Copyright (c) 2010 Novell Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.suse.studio.api.lib;

import java.io.IOException;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author James Tan <james@jam.sg>
 */
public class Xml {

	private Document xmlDoc = null;
	private XPath    xPath  = null;
	
	/*
	 * Constructors.
	 */
	public Xml(InputStream stream) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			xmlDoc = builder.parse(stream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		xPath = XPathFactory.newInstance().newXPath();
	}
	
	public Xml(Node node) {
		TransformerFactory factory = TransformerFactory.newInstance();
		DOMResult domResult = new DOMResult();		
		try {
			Transformer tf = factory.newTransformer();
			tf.transform(new DOMSource(node), domResult);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		xmlDoc = (Document) domResult.getNode();		
		xPath  = XPathFactory.newInstance().newXPath();
	}

	public int getInteger(String xpath) {
		return Integer.parseInt((String)get(xpath, XPathConstants.STRING)); 
	}
	
	public double getDouble(String xpath) {
		return (Double) get(xpath, XPathConstants.NUMBER);
	}
	
	public boolean getBoolean(String xpath) {
		return (Boolean) get(xpath, XPathConstants.BOOLEAN);
	}
	
	public String getString(String xpath) {
		return (String) get(xpath, XPathConstants.STRING);
	}
	
	public Date getDate(String xpath) {
		return Utils.toDate(getString(xpath));
	}
	
	public Node getNode(String xpath) {
		return (Node) get(xpath, XPathConstants.NODE);
	}
	
	public NodeList getNodes(String xpath) {
		return (NodeList) get(xpath, XPathConstants.NODESET);
	}
	
	public String toString() {
		try {
			StreamResult result = new StreamResult(new StringWriter());
			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(xmlDoc), result);
			return result.getWriter().toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return "";
	}

	/*
	 * Private methods.
	 */

	private Object get(String xpath, QName returnType) {
		try {
			return xPath.evaluate(xpath, xmlDoc, returnType);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}
}