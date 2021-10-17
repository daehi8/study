package test.algorithm;

/*
1 + 2 + 3 + 4 + 5 = 15
4 + 5 + 6 = 15
7 + 8 = 15
15 = 15

자연수 n이 매개변수로 주어질 때, 
연속된 자연수들로 n을 표현하는 방법의 수를 return하는 
solution를 완성해주세요.

제한사항
n은 10,000 이하의 자연수 입니다.

입출력 예
n	result
15	4
*/

public class Exhaustive01 {
	public int solution(int n) {
	    int answer = 0;
	    for(int i = 1; i <= n; i++) {
	        int sum = 0;
	        for(int j = i; j <= n; j++) {
	            sum += j;
	            if(sum >= n) {
	                if(sum == n) {
	                    answer++;
	                }
	                break;
	            }
	        }
	    }
	    return answer;
	}
}