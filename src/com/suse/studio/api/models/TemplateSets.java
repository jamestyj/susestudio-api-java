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

package com.suse.studio.api.models;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.suse.studio.api.lib.HttpClient;
import com.suse.studio.api.lib.Xml;
import com.suse.studio.api.lib.XmlModel;

/**
 * 
 * @author James Tan <james@jam.sg>
 */
public class TemplateSets extends XmlModel {

	private ArrayList<TemplateSet> templateSets = new ArrayList<TemplateSet>();
	
	public TemplateSets(HttpClient httpClient) {
		super(httpClient);
		reload();
	}

	@Override
	public void reload() {
		xml = new Xml(httpClient.get("user/template_sets"));
		NodeList tsNodes = xml.getNodes("//template_sets/template_set");
		for (int i=0; i<tsNodes.getLength(); i++) {
			NodeList childNodes = tsNodes.item(i).getChildNodes();
			TemplateSet ts = new TemplateSet();
			templateSets.add(ts);
			for (int j=0; j<childNodes.getLength(); j++) {
				Node node = childNodes.item(j);
				if (node.getNodeName() == "name") {
					ts.setName(node.getTextContent());
				}
				else if (node.getNodeName() == "description") {
					ts.setDescription(node.getTextContent());
				}
				else if (node.getNodeName() == "template") {
					NodeList appNodes = node.getChildNodes();
					int id = -1;
					String name = null;
					String baseSystem = null;
					String description = null; 
					for (int k=0; k<appNodes.getLength(); k++) {
						Node appNode = appNodes.item(k);
						if (appNode.getNodeName() == "appliance_id") {
							id = Integer.parseInt(appNode.getTextContent());
						}
						else if (appNode.getNodeName() == "name") {
							name = appNode.getTextContent();
						}
						else if (appNode.getNodeName() == "description") {
							description = appNode.getTextContent();
						}
						else if (appNode.getNodeName() == "basesystem") {
							baseSystem = appNode.getTextContent();
						}
					}
					ts.addAppliance(new Appliance(id, name, baseSystem, description));
				}
			}
		}
	}

	@Override
	public String toString() {
		String str = "[";
		boolean first = true;
		String tsStr;
		for (TemplateSet ts : templateSets) {
			tsStr = ts.toString(2);
			if (first) {
				tsStr = tsStr.substring(1);
			}
			str += tsStr + ",\n";
			first = false;
		}
		return str.substring(0, str.length()-2) + "\n]";
	}
}