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

package com.suse.studio.api.models.lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/**
 * 
 * @author James Tan <james@jam.sg>
 */
public abstract class ModelTest {

	protected String userName = "test_user";
	protected String apiKey   = "test_api_key";
	
	public InputStream getMockResponse() {
		String mockXmlFilePath = String.format("mocks/%s.xml", this.getClass().getSimpleName()
				.replaceFirst("Test$", ""));
		URL mockXmlFileUrl = this.getClass().getResource(mockXmlFilePath);
		if (mockXmlFileUrl == null) {
			throw fileNotFound(mockXmlFilePath);
		}
		try {
			return new FileInputStream(mockXmlFileUrl.getPath());
		} catch (FileNotFoundException e) {
			throw fileNotFound(mockXmlFilePath);
		}
	}
	
	private RuntimeException fileNotFound(String mockXmlFilePath) {
		return new RuntimeException(String.format("Cannot open %s.", mockXmlFilePath));
	}
	
}