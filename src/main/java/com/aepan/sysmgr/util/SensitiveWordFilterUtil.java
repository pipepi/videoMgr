/**
 * 
 */
package com.aepan.sysmgr.util;

/**
 * @author Administrator
 * 2015年10月26日下午2:34:13
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SensitiveWordFilterUtil {
	private static HashMap keysMap = new HashMap();

	private int matchType = 1;

	public static SensitiveWordFilterUtil get() {
		return InstanceHolder.instance;
	}

	public void addKeywords(String[] sensitiveWords) {
		for (String key : sensitiveWords) {
			HashMap nowhash = null;
			nowhash = keysMap;
			for (int j = 0; j < key.length(); ++j) {
				char word = key.charAt(j);
				Object wordMap = nowhash.get(Character.valueOf(word));
				if (wordMap != null) {
					nowhash = (HashMap) wordMap;
				} else {
					HashMap newWordHash = new HashMap();
					newWordHash.put("isEnd", "0");
					nowhash.put(Character.valueOf(word), newWordHash);
					nowhash = newWordHash;
				}
				if (j == key.length() - 1)
					nowhash.put("isEnd", "1");
			}
		}
	}

	public void clearKeywords() {
		keysMap.clear();
	}

	private int checkSensitiveWords(String txt, int begin, int flag) {
		HashMap nowhash = null;
		nowhash = keysMap;
		int maxMatchRes = 0;
		int res = 0;
		int l = txt.length();
		char word = '\0';
		for (int i = begin; i < l; ++i) {
			word = txt.charAt(i);
			Object wordMap = nowhash.get(Character.valueOf(word));
			if (wordMap != null) {
				++res;
				nowhash = (HashMap) wordMap;
				if (((String) nowhash.get("isEnd")).equals("1")) {
					if (flag == 1) {
						wordMap = null;
						nowhash = null;
						txt = null;
						return res;
					}
					maxMatchRes = res;
				}
			} else {
				txt = null;
				nowhash = null;
				return maxMatchRes;
			}
		}
		txt = null;
		nowhash = null;
		return maxMatchRes;
	}

	public Set<String> getSensitiveWord(String txt) {
		Set set = new HashSet();
		int l = txt.length();
		for (int i = 0; i < l;) {
			int len = checkSensitiveWords(txt, i, this.matchType);
			if (len > 0) {
				set.add(txt.substring(i, i + len));
				i += len;
			} else {
				++i;
			}
		}
		txt = null;
		return set;
	}

	public boolean containSensitiveWord(String txt) {
		for (int i = 0; i < txt.length(); ++i) {
			int len = checkSensitiveWords(txt, i, 1);
			if (len > 0) {
				return true;
			}
		}
		txt = null;
		return false;
	}

	public int getMatchType() {
		return this.matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}

	public static void main(String[] args) {
		SensitiveWordFilterUtil filter = get();
		filter.addKeywords(new String[] { "中国人", "中国男人" });
		String txt = "大法";
		boolean boo = filter.containSensitiveWord(txt);
		System.out.println(boo);
		Set set = filter.getSensitiveWord(txt);
		System.out.println(set);
	}

	private static class InstanceHolder {
		static SensitiveWordFilterUtil instance = new SensitiveWordFilterUtil();
	}
}