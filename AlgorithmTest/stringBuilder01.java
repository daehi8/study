package study;
/*
124 ���� �ֽ��ϴ�. 124 ���󿡼��� 10������ �ƴ� ������ ���� �ڽŵ鸸�� ��Ģ���� ���� ǥ���մϴ�.

124 ���󿡴� �ڿ����� �����մϴ�.
124 ���󿡴� ��� ���� ǥ���� �� 1, 2, 4�� ����մϴ�.
���� �� 124 ���󿡼� ����ϴ� ���ڴ� ������ ���� ��ȯ�˴ϴ�.

10����	124 ����	10����	124 ����
1	1	6	14
2	2	7	21
3	4	8	22
4	11	9	24
5	12	10	41
�ڿ��� n�� �Ű������� �־��� ��, n�� 124 ���󿡼� ����ϴ� ���ڷ� �ٲ� ���� return �ϵ��� solution �Լ��� �ϼ��� �ּ���.

���ѻ���
n�� 500,000,000������ �ڿ��� �Դϴ�.
*/

class stringBuilder01 {
    public String solution(int n) {
        StringBuilder result = new StringBuilder();
        int three = 0;
        
        while(n > 0){
            three = n%3;
            n = n/3;
            if(three == 0){
                n -= 1;
                three = 4;
            }
            result.insert(0, three);
        }
        return result.toString();
    }
}
