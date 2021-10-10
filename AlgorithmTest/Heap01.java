package test.algorithm;
import java.util.PriorityQueue;

/*
모든 음식의 스코빌 지수가 K 이상이 될 때까지 반복하여 섞습니다.
가진 음식의 스코빌 지수를 담은 배열 scoville과 원하는 스코빌 지수 K가 주어질 때, 
모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 섞어야 하는 최소 횟수를 return 하도록 solution 함수를 작성해주세요.

제한 사항
scoville의 길이는 2 이상 1,000,000 이하입니다.
K는 0 이상 1,000,000,000 이하입니다.
scoville의 원소는 각각 0 이상 1,000,000 이하입니다.
모든 음식의 스코빌 지수를 K 이상으로 만들 수 없는 경우에는 -1을 return 합니다.

섞은 음식의 스코빌 지수 = 가장 맵지 않은 음식의 스코빌 지수 + (두 번째로 맵지 않은 음식의 스코빌 지수 * 2)
 */

public class Heap01 {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
        for(int s:scoville){
            heap.offer(s);
        }
        while(heap.peek() < K){
            if(heap.size() < 2) return -1;
            int x = heap.poll();
            int y = heap.poll();
            int z = x + y * 2;
            heap.offer(z);
            answer++;
        }
        return answer;
    }
}

/*
PriorityQueue 우선순위 큐
내부 요소는 힙으로 구성되어 있는 이진트리 구
시간 복잡도는 0
우선순위를 중요시 하는 상황에 사용

.offer() - 값 추가
.poll() - 첫번째 값 반환하고 제거
.remove() - 첫번쨰 값 제거
.clear() - 초기화
.peek() - 우선순위 가장 높은 값 참조
*/
