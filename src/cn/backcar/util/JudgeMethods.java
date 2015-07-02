package cn.backcar.util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class JudgeMethods {

	public static final int TYPE_Plate = 0;
	public static final int TYPE_Money = 2;

	/**
	 * 判断邮箱是否有效
	 * 
	 * @param inputEmail
	 * @return true or false
	 */
	public boolean isEmail(String inputEmail) {
		if (inputEmail
				.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"))
			return (true);
		else
			return (false);
	}

	/**
	 * 字串s是否为英�??
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglish(String s) {
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		Matcher m = pattern.matcher(s);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字串str是否为英文和中文
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndChinese(String str) {
		int j = 0;
		int k = 0;
		int i = str.length();
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			j++;
		}
		for (int idx = 0; idx < i; idx++) {
			char c = str.charAt(idx);
			int tmp = (int) c;
			if ((tmp >= 'a' && tmp <= 'z') || (tmp >= 'A' && tmp <= 'Z')) {
				k++;
			}
		}
		if (i == j + k) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 字串str是否为英文和数字
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndDigit(String str) {
		int j = 0;
		int k = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
				k++;
			}
		}
		if (k + j == str.length()) {
			return true;
		}

		return false;
	}

	/**
	 * 字串str是否为英�??,数字和中文，不能有特殊符�??
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndDigitAndChinese(String str) {
		int j = 0;
		int k = 0;
		int q = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
				k++;
			}
		}
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			q++;
		}
		if (k + j + q == str.length()) {
			return true;
		}

		return false;
	}

	/**
	 * 字串s是否仅为汉字
	 * 
	 * @param s
	 * @return true or false
	 */
	public boolean isChinese(String name) {
		int idx = 0;
		// name = name.trim();
		int len = name.length();
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(name);
		while (m.find()) {
			idx++;
		}
		if (len == idx) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字串s是否仅为数字
	 * 
	 * @param s
	 * @return true or false
	 */
	public boolean isAllDigit(String s) {
		if (!"".equals(s) && s != null) {
			int len = 0;
			for (int idx = 0; idx < s.length(); idx++) {
				if (Character.isDigit(s.charAt(idx))) {
					len++;
				}
			}
			if (len == s.length()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字串s是否由英文和空格构成
	 * 
	 * @param s
	 * @return true or false
	 */
	public boolean isEnglishAndBackSpace(String s) {
		int len = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || c == ' ') {
				len++;
			}
		}
		if (len == s.length()) {
			return true;
		}
		return false;
	}

	/**
	 * 字串s是否超过maxLen(规则：全角字�??2位，半角1位；汉字2位，字母和数�??1位；)
	 * 
	 * @param s
	 *            and maxLen
	 * @return s是否超过�??大长度maxLen,是返回false，否则true
	 */
	public boolean allowMaxLenthOfString(String s, int maxLen) {

		if (s.length() <= maxLen) {
			return true;
		}
		return false;
	}

	/**
	 * 字串s是否超过maxLen(规则：全角字�??2位，半角1位；汉字2位，字母和数�??1位；)
	 * 
	 * @param s
	 *            and maxLen
	 * @return s是否超过�??大长度maxLen,是返回false，否则true
	 */
	public boolean allowMaxLenthOfString1(String s, int maxLen) {
		int num = 0;
		for (int i = 0; i < s.length(); i++) {
			String tmp = s.substring(i, i + 1);
			if (tmp.getBytes().length == 3) {
				num += 2;
			} else if (tmp.getBytes().length == 1) {
				num += 1;
			}
		}
		if (num <= maxLen) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为手机号码（规则�??11位数字，首位�??1�??
	 * 
	 * @param phone
	 * @return true or false
	 */
	public boolean isPhoneNum(String phone) {

		if (isAllDigit(phone)) {
			if (phone.length() != 0) {
				if (phone.length() != 11 || !phone.substring(0, 1).equals("1")) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断字串是否由数字和点构�??
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isDigitAndDot(String str) {

		int len = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx)) || str.charAt(idx) == '.') {
				len++;
			}
		}
		if (len == str.length()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字串 是否只有数字�?? -
	 * 
	 * @param str
	 * @return true or false
	 */
	public static boolean isg(String str) {

		int len = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx)) || str.charAt(idx) == '-') {
				len++;
			}
		}
		if (len == str.length()) {
			return true;
		}
		return false;
	}

	/**
	 * 根据出生日期判断是否大于18�??
	 * 
	 * @param birthDate
	 * @return 大于18岁，返回true,否则false
	 */
	public boolean isValidateBirth(String birthDate) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		String strBegin[] = birthDate.split("-");

		int birthYear = Integer.parseInt(strBegin[0]);

		if ((year - birthYear) < 18 || (year - birthYear) > 65) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 移除数字前面为零的字�??
	 * 
	 * @param digit
	 * @return �??串由数字组成的字�??
	 */
	public String removeFontZero(String digit) {
		if (digit == null || digit.equals("")) {
			return "";
		}
		if (isJustDigitStar(digit) && !isExistStar(digit)) {

			String[] str = digit.split("\\.", 2);
			if (str.length > 1) {
				if (str[1].contains(".")) {
					return "1";
				}
			}
			DecimalFormat d = new DecimalFormat("0.00");

			digit = d.format(Double.parseDouble(digit));// .split("\\.")[0];
		} else {

			return "1";
		}
		return digit;
	}

	// 判断是否是数字和"."号组成的
	public boolean isJustDigitStar(String str) {

		int len = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx)) || str.charAt(idx) == '.') {
				len++;
			}
		}

		if (len == str.length()) {
			return true;
		}

		return false;
	}

	/**
	 * 判断仅有"."号组成的
	 * 
	 * @param str
	 * @return
	 */
	public boolean isExistStar(String str) {

		int len = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (str.charAt(idx) == '.') {
				len++;
			}
		}

		if (len == str.length()) {
			return true;
		}

		return false;
	}

	/**
	 * 将字符串�??4位隐�??
	 * 
	 * @param str
	 *            传入要隐藏的字符�??
	 * @return
	 */
	public String[] hiddenInformationString(String str) {
		String[] mobileStr = new String[2];
		int in = str.length();
		mobileStr[0] = str.substring(0, (in - 4)) + "****";
		mobileStr[1] = str;
		return mobileStr;
	}

	public int getLenOfStr(String s) {
		int num = 0;
		for (int i = 0; i < s.length(); i++) {
			String tmp = s.substring(i, i + 1);
			if (tmp.getBytes().length == 3) {
				num += 2;
			} else if (tmp.getBytes().length == 1) {
				num += 1;
			}
		}
		return num;

	}

	public EditText setEdit(final EditText et_account, final int type) {
		et_account.addTextChangedListener(new TextWatcher() {
			private boolean isChanged = false;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (type == TYPE_Money) {

					if (s.toString().contains(".")) {
						if (s.length() - 1 - s.toString().indexOf(".") > 2) {
							s = s.toString().subSequence(0,
									s.toString().indexOf(".") + 3);
							et_account.setText(s);
							et_account.setSelection(s.length());
						}
					}
					if (s.toString().trim().substring(0).equals(".")) {
						s = "0" + s;
						et_account.setText(s);
						et_account.setSelection(2);
					}

					if (s.toString().startsWith("0")
							&& s.toString().trim().length() > 1) {
						if (!s.toString().substring(1, 2).equals(".")) {
							et_account.setText(s.subSequence(0, 1));
							et_account.setSelection(1);
							return;
						}
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@SuppressLint("NewApi")
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (isChanged) {// ----->如果字符未改变则返回
					return;
				}
				String str = s.toString();
				isChanged = true;
				switch (type) {
				case TYPE_Plate:
					str = str.toUpperCase();
					et_account.setText(str);
					et_account.setSelection(et_account.length());
					isChanged = false;
					break;

				case TYPE_Money:

					// if (str.contains(".")) {
					//
					// int position = str.length() - str.indexOf(".") + 1;
					// if (position > 2) {
					// BigDecimal b = new BigDecimal(str);
					// float f1 = b.setScale(2, BigDecimal.ROUND_HALF_EVEN)
					// .floatValue();
					// //
					//
					// // DecimalFormat formater = new DecimalFormat("#0.##");
					// // formater.setRoundingMode(RoundingMode.FLOOR);
					// //String f1=formater.format(str);
					// et_account.setText(f1 + "");
					//
					// et_account.setSelection(et_account.getText()
					// .toString().length());
					//
					//
					// }
					// }
					// else{
					//
					// }
					// isChanged = false;
					// String cuttedStr = str;
					// /* 删除字符串中的dot */
					// for (int i = str.length() - 1; i >= 0; i--) {
					// char c = str.charAt(i);
					// if ('.' == c) {
					// cuttedStr = str.substring(0, i) + str.substring(i + 1);
					// break;
					// }
					// }
					// /* 删除前面多余的0 */
					// int NUM = cuttedStr.length();
					// int zeroIndex = -1;
					// for (int i = 0; i < NUM - 2; i++) {
					// char c = cuttedStr.charAt(i);
					// if (c != '0') {
					// zeroIndex = i;
					// break;
					// } else if (i == NUM - 3) {
					// zeroIndex = i;
					// break;
					// }
					// }
					// if (zeroIndex != -1) {
					// cuttedStr = cuttedStr.substring(zeroIndex);
					// }
					// /* 不足3位补0 */
					// if (cuttedStr.length() < 3) {
					// cuttedStr = "0" + cuttedStr;
					// }
					// /* 加上dot，以显示小数点后两位 */
					// cuttedStr = cuttedStr.substring(0, cuttedStr.length() -
					// 2)
					// + "." + cuttedStr.substring(cuttedStr.length() - 2);
					//

					break;

				}

			}
		});
		return et_account;
	}
/**
 *  4位隔开字符串
 * @param str
 * @return
 */
	public String splitByEmpty(String str) {
		String input = str;
		String regex = "(.{4})";
		input = input.replaceAll(regex, "$1   ");
		return input;
	}
	/**
	 * 保留2位小数
	 * @param d
	 * @return
	 */
	public String get2Point(double d ){
		
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
		return df.format(d);
	}

	
}
