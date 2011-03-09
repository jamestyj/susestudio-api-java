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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.Arrays;

import com.suse.studio.api.annotations.ToString;

/**
 * Auto-magically  
 * 
 * @author James Tan <james@jam.sg>
 */
public class AutoToString {

	// FIXME
	private static boolean skipNulls = true;
	
	public String toString() {
		return toString(0);
	}

	public String toString(int indent) {
		return toString(indent, false);
	}
	
	public String toString(int indent, boolean subIndent) {
		String padding = repeat(' ', indent);
		String out = "{ ";
		if (subIndent) {
			out = "\n  " + padding;
		}
		boolean first = true;
		String field = "";
		for (Method m : this.getClass().getDeclaredMethods()) {
			if (!m.getName().startsWith("get"))
				continue;
			for (Annotation annot : m.getAnnotations()) {
				if (annot instanceof ToString) {
					field = formatField(indent, m);
					if (field != "" && !first) {
						out += padding + "  ";
					}
					out += field;
					first = false;
				}
			}
		}
		if (first)
			return "No getters marked with @ToString!";
		return out.substring(0, out.length()-2) + "\n" + padding + "}";
		
	}

	private String repeat(char character, int times) {
		char[] leftPadChars = new char[times];
		Arrays.fill(leftPadChars, ' ');
		return new String(leftPadChars);
	}
	
	private String formatMethod(Method m) {
		String field = m.getName().substring("get".length());
		return ":" + field.substring(0, 1).toLowerCase() + field.substring(1);
	}
	
	private String formatArray(Object object) {
		String str = " [\n";
		for (Object obj : (Object[]) object) {
			str += "    \"" + obj + "\",\n";
		}
		return str.substring(0, str.length()-2) + "\n  ]";
	}
	
	private String formatValue(Method m, int indent) {
		String str = null;
		try {
			Object obj = m.invoke(this, new Object[0]);
			if (obj != null) {
				if (obj.getClass().isArray()) {
					str = formatArray(obj);
				} else {
					if (obj instanceof String) {
						str = "\"" + obj + "\"";
					}
					else if (obj instanceof AbstractList) {
						@SuppressWarnings("unchecked")
						AbstractList<AutoToString> arrayList = (AbstractList<AutoToString>) obj;
						if (arrayList.size() == 0) {
							str = "[]";
						} else {
							str = "[\n";
							for (AutoToString o : arrayList) {
								str += o.toString(6) + ",\n";
							}
							str += "    ]";
						}
					}
					else if (obj instanceof AutoToString) {
						str = ((AutoToString)obj).toString(indent+2, true);
					} else {
						str = obj.toString();
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return str;
	}

	private String formatField(int indent, Method m) {
		String value = formatValue(m, indent);
		if (skipNulls && value == null) {
			return "";
		}
		return formatMethod(m) + " => " + value + ",\n"; 
	}
}
