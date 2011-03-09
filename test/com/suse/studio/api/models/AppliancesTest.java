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

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Test;

import com.suse.studio.api.lib.HttpClient;
import com.suse.studio.api.models.lib.ModelTest;

/**
 * 
 * @author James Tan <james@jam.sg>
 */
public class AppliancesTest extends ModelTest {

	private Appliances appliances;
	
	@Before public void setUp() {
		new NonStrictExpectations() {
			@Mocked HttpClient httpClient;
			{ httpClient.get("user/appliances"); result = getMockResponse(); }
		};
		appliances = new Appliances(new HttpClient(userName, apiKey));
	}

	@Test public void testReload() {
		System.out.println(appliances);
//		assertEquals("Test User", account.getDisplayName());
//		assertEquals("test_user", account.getUserName());
//		assertEquals("test_user@test.com", account.getEmail());
//		assertEquals(Utils.toDate("2008-09-02 15:28:57 UTC"), account.getCreatedAt());
//		String[] openIds = { "http://test.user.myid.net/", "http://test.user.google.com/" };
//		for (int i=0; i<openIds.length; i++) {
//			assertEquals(openIds[i], account.getOpenIdUrls()[i]);	
//		}
//		assertEquals("150GB", account.getTotalQuota());
//		assertEquals("5%", account.getUsedQuotaPercent());
	}	
	
}
