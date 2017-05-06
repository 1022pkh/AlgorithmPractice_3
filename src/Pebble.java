import java.util.Scanner;

/*
 * 조약돌 놓기 알고리즘
 * 가로나 세로로 인접한 두 칸에 동시에 조약돌이 놓일 수 없다.
 * 각 열에는 하나 이상의 조약돌을 놓는다.
 */

/*
 * 패턴 1		패턴 2	패턴3		패턴4
 * 1 +		1		1		1 +
 * 2		2 +		2		2
 * 3		3		3 +		3 +
 * 
 * 패턴 1 -> 2,3
 * 패턴 2 -> 1,3,4
 * 패턴 3 -> 1,2
 * 패턴 4 -> 2
 */
public class Pebble {

	public static int pebble[][] = { { 6, 7, 12, -5, 5, 3, 11, 3 }, { -8, 10, 14, 9, 7, 13, 8, 5 },
			{ 11, 12, 7, 4, 8, -2, 9, 4 } };

	public static int patternMatch[][] = { { 2, 3, 0 }, { 1, 3, 4 }, { 1, 2, 0 }, { 2, 0, 0 } };

	public static int resultPebble[][] = new int[8][4];

	public static int patternMax(int i, int p) {

		if (p == 1)
			return pebble[0][i];
		else if (p == 2)
			return pebble[1][i];
		else if (p == 3)
			return pebble[2][i];
		else if (p == 4)
			return pebble[0][i] + pebble[2][i];

		return 0;
	}

	/*
	 * i열이 패턴 p로 높일 때의 최고점수 w[i,p] => i열이 패턴 p로 놓일 때 i열에 돌이 높인 곳의 접수합.
	 */
	public static int pebbleMax(int i, int p) {
		int score = 0;
		int temp;
		int max = -99999;

		if (i == 0)
			return patternMax(0, p);
		else {

			for (int q = 0; q < 3; q++) { // 양립하는 패턴에 해당하는 것만 검사

				if (patternMatch[p - 1][q] == 0)
					break;
				else {
					int a = patternMatch[p - 1][q];
					temp = pebbleMax(i - 1, patternMatch[p - 1][q]);

					// System.out.println(">> pebbleMax "+ (i-1) +" "+ a +" " +
					// temp);
					if (temp > max)
						max = temp;
				}
			}

		}

		// System.out.println(">> " + max);
		return max + patternMax(i, p);
	}

	/*
	 * n열까지 조약돌을 놓을 때의 최고점수.
	 */
	public static void improvePebbleMax(int n) {

		for (int p = 1; p < 5; p++) {
			resultPebble[0][p - 1] = patternMax(0, p);
		}

		// System.out.println(resultPebble[0][0]);
		// System.out.println(resultPebble[0][1]);
		// System.out.println(resultPebble[0][2]);
		// System.out.println(resultPebble[0][3]);

		for (int i = 1; i <= n; i++) {
			for (int p = 0; p < 4; p++) {

				resultPebble[i][p] = maxResultPebble(i - 1, p) + patternMax(i, p + 1);

			}
		}

		System.out.println();
		System.out.println("중복 최소화..");

		for (int i = 0; i < 4; i++)
			System.out.println("패턴 " + (i + 1) + " - 결과 : " + resultPebble[n][i]);

	}

	public static int maxResultPebble(int i, int p) {

		int max = -99999;
		int temp;

		for (int q = 0; q < 3; q++) { // 양립하는 패턴에 해당하는 것만 검사

			int index = patternMatch[p][q];

			if (index == 0)
				break;
			else {

				temp = resultPebble[i][index - 1];

				if (temp > max)
					max = temp;
			}
		}

		return max;
	}

	public static void main(String[] args) {

		System.out.println("\t\t-------- 조약돌 상태 --------");
		for (int i = 0; i < pebble.length * pebble[0].length; i++) {
			int row = i / pebble[0].length; // 행
			int column = i % pebble[0].length; // 열
			System.out.print(pebble[row][column] + "\t");

			if (column == pebble[0].length - 1) {
				System.out.println();
			}

		}

		System.out.println();

		// i열이 패턴 p로 높일 때의 최고점수 w[i,p] => i열이 패턴 p로 놓일 때 i열에 돌이 높인 곳의 접수합.
		// 재귀함수의 문제는 중복호출이 많다는 점.....
		// 해결방법으로는 이미 계산한 부분에 대해서는 기억하고 있는다.
		// 결과를 [n][4]로 저장하고있는다.

		int column = 4;

		int result = pebbleMax(column, 1);
		System.out.println("패턴 1 - 결과 : " + result);
		result = pebbleMax(column, 2);
		System.out.println("패턴 2 - 결과 : " + result);
		result = pebbleMax(column, 3);
		System.out.println("패턴 3 - 결과 : " + result);
		result = pebbleMax(column, 4);
		System.out.println("패턴 4 - 결과 : " + result);

		improvePebbleMax(column);

	}
}
