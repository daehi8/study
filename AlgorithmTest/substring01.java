package study;

public class substring01 {
	
//  ��� ���� ��������	
//	�ܾ� s�� ��� ���ڸ� ��ȯ�ϴ� �Լ�, solution�� ����� ������. 
//	�ܾ��� ���̰� ¦����� ��� �α��ڸ� ��ȯ�ϸ� �˴ϴ�.
//	���ѻ���
//	s�� ���̰� 1 �̻�, 100������ ��Ʈ���Դϴ�.
	
    public String solution(String s) {
        String answer = "";
        int length = s.length();
        int subLength = (length-1)/2;
        
        if(length%2 == 1){
            answer = s.substring(subLength,subLength+1);
        }else if(length%2 == 0){
            answer = s.substring(subLength,subLength+2);
        }
        return answer;
    }
}
