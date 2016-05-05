package mjava.abc.array;

import java.util.Scanner;

public class NumToRMB {

	private String[] hanArr = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	// private String[] unitArr = {"拾","佰","仟","万","亿"};

	private String[] smallUnitArr = { "", "拾", "佰", "仟" };

	private String[] bigUnitArr = { "", "万", "亿" };

	private String[] decimalUnitArr = { "角", "分" };

	private final double MAX = 999999999999.99;

	public void test(String numStr) {

		double intNumber = Double.parseDouble(numStr);

		long zheng = (long) intNumber;

		long xiao = Math.round((intNumber - zheng) * 100);

		toHanStr(new String[] { zheng + "", String.valueOf(xiao) });
	}

	public void test(double number) {

		long zheng = (long) number;

		long xiao = Math.round((number - zheng) * 100);

		toHanStr(new String[] { zheng + "", String.valueOf(xiao) });
	}

	public void toHanStr(String[] parts) {
		// 整数部分
		String integral;
		// 小数部分
		String decimal;
		// 当前位的数字
		int d;
		// 含有0的个数
		int zeroCount;
		// 商
		int quotient;
		// 系数
		int modulus;
		// 辅助 商，系数运算
		int p;
		// 结果
		String result = "";

		// ---- Start ----
		integral = parts[0];
		decimal = parts[1];

		if (Double.parseDouble(integral) > MAX) {
			System.out.println("错误！本程序支持最大数为 : " + MAX);
			return;
		}

		// 如果 整数部分 > 0 处理整数部分
		if (Long.parseLong(integral) > 0) {

			zeroCount = 0;

			int numLen = integral.length();
			// 根据当前位数长度 由 高位 到 低位 依次遍历
			for (int i = 0; i < numLen; i++) {
				// 当前位的数字
				d = integral.charAt(i) - 48;

				p = numLen - i - 1;
				quotient = p / 4; // 万 亿
				modulus = p % 4; // 十百千

				if (d == 0) {
					zeroCount++;
				} else {
					if (zeroCount > 0) {
						result += hanArr[d];
					}
					zeroCount = 0;
					result += hanArr[d] + smallUnitArr[modulus];
				}
				if (modulus == 0 && zeroCount < 4) {
					try {
						result += bigUnitArr[quotient];
					} catch (ArrayIndexOutOfBoundsException aioobe) {
						aioobe.printStackTrace();
					}
				}
			}

			result += "元";
		}

		// 如果小数部分 > 0 处理小数部分
		if (!"0".equals(decimal)) {

			if (decimal.length() > 1) {

				for (int i = 0; i < decimal.length(); i++) {
					d = decimal.charAt(i) - 48;
					if (d != 0) {
						result += hanArr[d] + decimalUnitArr[i];
					}
				}

			} else {

				d = decimal.charAt(0) - 48;
				result += hanArr[d] + decimalUnitArr[1];
			}

		}

		// 如果 整数部分不为0，小数位为零。则是整数
		if ("0".equals(decimal) && !"0".equals(integral)) {
			// 如果没有小数部分 则是整数。即 XX元整
			result += "整";
		}

		// 如果小数，整数都为0 即为0元
		if ("0".equals(decimal) && "0".equals(integral)) {
			result = "零元";
		}

		result = "人民币:" + result;

		System.out.println(result);
		
	}

	// public String toHanArr(String numStr){
	//
	// String result = "";
	//
	// int numLen = numStr.length();
	//
	// for ( int i = 0 ; i < numLen ; i ++ ){
	//
	// int num = numStr.charAt(i) - 48;
	//
	// if ( i != numLen - 1 && num != 0){
	//
	// result += hanArr[num] + unitArr[numLen - 2 - i];
	//
	// }else{
	//
	// result += hanArr[num];
	//
	// }
	//
	// }
	//
	// return result + "元";
	//
	// }

	public static void main(String[] args) {

		NumToRMB ntr = new NumToRMB();
		
		System.out.println(">>~ 欢迎来到德莱联盟   ~<<");
		System.out.println("\t      ——这儿不是你该来的地方");
		Scanner in = new Scanner(System.in);

		String numStr = "";

		while (in.hasNext()) {

			numStr = in.next();
			ntr.test(numStr);

		}
		
		in.close();
	}

}
