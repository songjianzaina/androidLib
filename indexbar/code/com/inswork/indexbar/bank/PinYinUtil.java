package com.inswork.indexbar.bank;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
	public static String getPinYin(String hanzi){
		String pinyin = "";
		if(TextUtils.isEmpty(hanzi))return pinyin;
		HanyuPinyinOutputFormat pFormat = new HanyuPinyinOutputFormat();
		pFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		pFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		char[] arr = hanzi.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if(Character.isWhitespace(arr[i])) continue;
				try {
					String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(arr[i], pFormat);
					if(hanyuPinyinStringArray == null){
						pinyin += arr[i];
					}else{
						pinyin += hanyuPinyinStringArray[0];
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
					pinyin += arr[i];
				}
			if(arr[i]>127){

			}else{
				pinyin += arr[i];
			}
		}
		return pinyin;
	}
}
