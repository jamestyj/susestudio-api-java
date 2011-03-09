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

package com.suse.studio.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.suse.studio.api.exceptions.StudioException;
import com.suse.studio.api.lib.HttpClient;
import com.suse.studio.api.models.Account;
import com.suse.studio.api.models.Appliances;
import com.suse.studio.api.models.TemplateSets;

/**
 * 
 * @author James Tan <james@jam.sg>
 */
public class Studio {
	
	private HttpClient httpClient;
	private static Log log = LogFactory.getLog(Studio.class);
	private Properties config;
	
	// Known property keys for the config Studio.properties file.
	private static String[] KNOWN_KEYS = { "USER_NAME", "API_KEY", "LOG_LEVEL" };
	
	/**
	 * Creates a new Studio API instance. Reads the user name and API key from the 
	 * 'Studio.properties' configuration file.
	 * @throws StudioException if user name or API key cannot be read from the config file. 
	 */
	public Studio() throws StudioException {
		config = getConfig();
		initalize(getProperty("USER_NAME"), getProperty("API_KEY"));
	}
	
	/**
	 * @param userName
	 * @param apiKey
	 */
	public Studio(String userName, String apiKey) {
		initalize(userName, apiKey);
	}

	private void initalize(String userName, String apiKey) {
		httpClient = new HttpClient(userName, apiKey);
	}
	
	private String getProperty(String propertyName) throws StudioException {
		String value = config.getProperty(propertyName);
		if (value == null) {
			throw new StudioException(String.format(
					"%s not specified in config file.", propertyName));
		}
		return value;
	}
	
	private Properties getConfig() throws StudioException {
		// Read in Studio.properties
		String configFile = this.getClass().getSimpleName() + ".properties";
		InputStream configFileIn = this.getClass().getClassLoader().getResourceAsStream(configFile);
		if (configFileIn == null) {
			throw new StudioException(String.format("Cannot find config file '%s'.", configFile));
		}
		Properties config = new Properties();
		try {
			config.load(configFileIn);
		} catch (IOException e) {
			throw new StudioException(String.format("Cannot parse config file '%s'.", configFile));
		}
		
		// Check that all property names are valid
		for (@SuppressWarnings("unchecked") Enumeration<String> e = (Enumeration<String>)
				config.propertyNames(); e.hasMoreElements();) {
			String propertyName = e.nextElement();
			boolean found = false;
			for (String key : KNOWN_KEYS) {
				if (propertyName.equals(key)) {
					found = true;
					break;
				}
			}
			if (!found) {
				throw new StudioException(String.format(
						"Error reading config file '%s': unknown property '%s'.",
						configFile, propertyName));
			}
		}
		
		return config;
	}
	
	public static Log getLog() {
		return log;
	}
	
	public Account getAccount() {
		return new Account(httpClient);
	}
	
	public TemplateSets getTemplateSets() {
		return new TemplateSets(httpClient);
	}
	
	public Appliances getAppliances() {
		return new Appliances(httpClient);
	}
}