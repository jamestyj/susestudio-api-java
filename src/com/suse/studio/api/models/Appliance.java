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

import java.util.Date;

import org.w3c.dom.Node;

import com.suse.studio.api.annotations.ToString;
import com.suse.studio.api.lib.HttpClient;
import com.suse.studio.api.lib.Xml;
import com.suse.studio.api.lib.XmlModel;

/**
 * 
 * @author James Tan <james@jam.sg>
 */
public class Appliance extends XmlModel {

	private int id             = -1;
	private String name        = null;
	private String arch        = null;
	private String type        = null;
	private Date lastEdited    = null;
	private String editUrl     = null;
	private String iconUrl     = null;
	private String baseSystem  = null;
	private String uuid        = null;
	private String description = null;
	private Appliance parentAppliance = null;
	private String estimatedSize      = null;
	private String estimatedCompressedSize = null;
//	private Builds = null;

	public Appliance(HttpClient httpClient) {
		super(httpClient);
		reload();
	}

	public Appliance(HttpClient httpClient, Node applianceNode) {
		super(httpClient);
		reload(applianceNode);
	}
	
	public Appliance(int id, String name, String baseSystem, String description) {
		super(null);
		this.id = id;
		this.name = name;
		this.baseSystem = baseSystem;
		this.description = description;
	}

	public Appliance(int id, String name) {
		super(null);
		this.id = id;
		this.name = name;
	}

	@ToString public int getId() { return id; }
	@ToString public String getName() { return name; }
	@ToString public String getDescription() { return description; }
	@ToString public String getBaseSystem() { return baseSystem; }
	@ToString public String getArch() { return arch; }
	@ToString public String getType() { return type; }
	@ToString public Date getLastEdited() { return lastEdited; }
	@ToString public String getEditUrl() { return editUrl; }
	@ToString public String getIconUrl() { return iconUrl; }
	@ToString public String getUuid() { return uuid; }
	@ToString public Appliance getParentAppliance() { return parentAppliance; }
	@ToString public String getEstimatedSize() { return estimatedSize; }
	@ToString public String getEstimatedCompressedSize() { return estimatedCompressedSize; }

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void reload() {
		
	}
	
	public void reload(Node applianceNode) {
		xml  = new Xml(applianceNode);
		id   = xml.getInteger("//appliance/id/text()");
		name = xml.getString("//appliance/name/text()");
		arch = xml.getString("//appliance/arch/text()");
		type = xml.getString("//appliance/type/text()");
		lastEdited    = xml.getDate("//appliance/last_edited/text()");
		estimatedSize = xml.getString("//applianceg/estimated_raw_size/text()");
		estimatedCompressedSize = xml.getString("//appliance/estimated_compressed_size/text()");
		editUrl    = xml.getString("//appliance/edit_url/text()");
		iconUrl    = xml.getString("//appliance/icon_url/text()");
		baseSystem = xml.getString("//appliance/basesystem/text()");
		uuid       = xml.getString("//appliance/uuid/text()");
		
		String parentApplianceId = xml.getString("//appliance/parent/id/text()");
		if (parentApplianceId != null) {
			parentAppliance = new Appliance(
					Integer.parseInt(parentApplianceId),
					xml.getString("//appliance/parent/name/text()"));
		}
	}
}