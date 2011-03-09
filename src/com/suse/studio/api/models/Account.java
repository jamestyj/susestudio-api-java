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

import org.w3c.dom.NodeList;

import com.suse.studio.api.Studio;
import com.suse.studio.api.annotations.ToString;
import com.suse.studio.api.lib.HttpClient;
import com.suse.studio.api.lib.Xml;
import com.suse.studio.api.lib.XmlModel;

/**
 * 
 * @author James Tan <james@jam.sg>
 */
public class Account extends XmlModel {
	
	private String userName;
	private String displayName;
	private String email;
	private Date createdAt;
	private String[] openIdUrls;
	private String totalQuota;
	private String usedQuotaPercent;

	public Account(HttpClient httpClient) {
		super(httpClient);
		reload();
	}

	@ToString public String getUserName() { return userName; }
	@ToString public String getDisplayName() { return displayName; }
	@ToString public String getEmail() { return email; }
	@ToString public Date getCreatedAt() { return createdAt; }
	@ToString public String[] getOpenIdUrls() { return openIdUrls; }
	@ToString public String getTotalQuota() { return totalQuota; }
	@ToString public String getUsedQuotaPercent() { return usedQuotaPercent; }

	@Override public void reload() {
		xml = new Xml(httpClient.get("user/account"));
		Studio.getLog().debug(xml);

		userName    = xml.getString("//account/username/text()");
		displayName = xml.getString("//account/displayname/text()");
		email       = xml.getString("//account/email/text()");
		createdAt   = xml.getDate("//account/created_at/text()");

		NodeList openIds = xml.getNodes("//account/openid_urls/openid_url");
		openIdUrls = new String[openIds.getLength()];
		for (int i=0; i<openIds.getLength(); i++) {
			openIdUrls[i] = openIds.item(i).getTextContent();
		}

		totalQuota       = xml.getString("//account/disk_quota/available/text()");
		usedQuotaPercent = xml.getString("//account/disk_quota/used/text()");
	}
}