package study;

/*
���̰� n�̰�, "���ڼ��ڼ��ڼ�...."�� ���� ������ �����ϴ� ���ڿ��� �����ϴ� �Լ�, solution�� �ϼ��ϼ���. 
������� n�� 4�̸� "���ڼ���"�� �����ϰ� 3�̶�� "���ڼ�"�� �����ϸ� �˴ϴ�.

���� ����
n�� ���� 10,000������ �ڿ����Դϴ�.
 */

public class for01 {
	public String solution(int n) {
		String answer = "";

		for (int i = 0; i < n; i++) {
			if(i%2 == 0) {
				answer += "��";
			}
				
			if(i%2 == 1) {
				answer += "��";
			}
		}
		return answer;
	}
}

