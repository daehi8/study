package study;

/*
� ������ �� ���ĺ��� ������ �Ÿ���ŭ �о �ٸ� ���ĺ����� �ٲٴ� ��ȣȭ ����� ���� ��ȣ��� �մϴ�. 
���� ��� "AB"�� 1��ŭ �и� "BC"�� �ǰ�, 3��ŭ �и� "DE"�� �˴ϴ�. 
"z"�� 1��ŭ �и� "a"�� �˴ϴ�. 
���ڿ� s�� �Ÿ� n�� �Է¹޾� s�� n��ŭ �� ��ȣ���� ����� �Լ�, solution�� �ϼ��� ������.

���� ����
������ �ƹ��� �о �����Դϴ�.
s�� ���ĺ� �ҹ���, �빮��, �������θ� �̷���� �ֽ��ϴ�.
s�� ���̴� 8000�����Դϴ�.
n�� 1 �̻�, 25������ �ڿ����Դϴ�.
*/

class Solution {
    public String solution(String s, int n) {
        char[] array = s.toCharArray();
        String result = "";
        
        for(int i : array){
        	
        	// ���� ����
            if(i != 32){
                int in = (i+n);
                
                //�ҹ��� �ʰ� ����
                if(in >= 123 && i >= 97){
                    in = in-26;
                    result += (char)in;
                    
                // �빮�� �ʰ� ����
                }else if(in >= 91 && i <= 90){
                    in = in-26;
                    result += (char)in;
                    
                }else{
                    result += (char)in;    
                }
                
            }else if(i == 32){
                result += (char)i;
            }
        }
        
        return result;
    }
}
