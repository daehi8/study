package study;

/*
���� ����
������ ������ �������� �����濡 �����Ͽ����ϴ�. 
�� �� ���� ������ �����ϰ�� ��� ������ �������� �����Ͽ����ϴ�.
�����濡 ������ �������� �̸��� ��� �迭 participant�� ������ �������� �̸��� ��� �迭 completion�� �־��� ��, 
�������� ���� ������ �̸��� return �ϵ��� solution �Լ��� �ۼ����ּ���.

���ѻ���
������ ��⿡ ������ ������ ���� 1�� �̻� 100,000�� �����Դϴ�.
completion�� ���̴� participant�� ���̺��� 1 �۽��ϴ�.
�������� �̸��� 1�� �̻� 20�� ������ ���ĺ� �ҹ��ڷ� �̷���� �ֽ��ϴ�.
������ �߿��� ���������� ���� �� �ֽ��ϴ�.
*/

import java.util.Arrays;

class Hash01 {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        String temp = "";
        
        // �迭 �������� ����
        Arrays.sort(participant);
        Arrays.sort(completion);
        
        int i = 0;
        
        while(i < completion.length){
            if(!completion[i].equals(participant[i])){
                temp = participant[i];
                break;
            }else{
                i++;
            }
        }
        if(!temp.equals("")){
            answer = temp;
        }else{
            // �������� ���� ������ ���� ���� �ִ� ���
            answer = participant[participant.length-1];
        }
        return answer;
    }
}

// hash�� ����� �ٽ��ѹ� Ǯ� ����