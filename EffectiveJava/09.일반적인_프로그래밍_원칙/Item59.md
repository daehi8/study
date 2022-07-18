## 59. 라이브러리를 익히고 사용하라

### 랜덤값을 구해오는 라이브러리

- java.util.Random: nextInt() % bound; `java1.0`
- java.util.Random: nextInt(int bount); `java1.2`
- java.util.concurrent.ThreadLocalRandom `java1.7`
- java.util.SplittableRandom `java1.8`
- java.security.SecureRandom `java1.1`

```java
// 59-1. Random.nextInt() % bound 방식의 문제점
public class Item59 {
  static Random random = new Random();

  static int random(int n) {
    return Math.abs(random.nextInt()) % n;
  }

  public static void main(String[] args) {
    int n = 2 * (Integer.MAX_VALUE / 3);
    int low = 0;
    for (int i = 0; i < 1_000_000; i++) {
      if (random(n) < n/2) {
        low++;
      }
    }
    System.out.println(low);
  }
}
```

- n이 그리 크지 않은 2의 제곱수라면 얼마 지나지 않아 같은 수열이 반복 된다.

- n이 2의 제곱수가 아니라면 몇몇 숫자가 평균적으로 더 자주 반환된다.

- 지정한 범위 '바깥'의 수가 튀어나올 수 있다.

  

```java
// 랜덤값 구하기 
int bound = 1_000_000;
int r0 = new Random().nextInt(bound); // ThreadLocalRandom 으로 대체 
int r1 = ThreadLocalRandom.current().nextInt();
int r2 = new SplittableRandom().nextInt(bound); // 포크-조인 풀이나 병렬 스트림에서 사용 
int r3 = new SecureRandom().nextInt(bound); // 좀 더 보안적인 난수가 필요할때 
```



### 표준 라이브러리를 쓰는 이점

- 핵심적인 일과 크게 관련 없는 문제를 해결하느라 시간을 허비하지 않아도 된다.
- 따로 노력하지 않아도 성능이 지속해서 개선된다.
- 기능이 점점 많아진다.
- 작성한 코드가 많은 사람에게 낯익은 코드가 된다.



### 자바 프로그래머라면 적어도 알아야 할 패키지와 라이브러리

- 근데도 많은 프로그래머들은 라이브러리에 그런 기능이 있는지 모르기 때문에 직접 구현해서 쓰고있다.
- 메이저 릴리스 마다 많은 기능이 추가 되는데 한 번쯤 읽어볼 만하다.

- java.lang
- java.util
- java.io
- 컬렉션 프레임워크
- 스트림 라이브러리
- java.util.concurrent 의 동시성 기능



### 기능 구현 전에 라이브러리를 찾아보자.

- 구현 전에 먼저 라이브러리를 사용하려고 해보자.
- 표준 라이브러리에서 제공하는지 찾아보자.
- 서브파티 라이브러리에서 제공하는지 찾아보자. (구글 구아바, 아파치 커먼즈)
- 그럼에도 없다면 직접 구현하자.



### 핵심

- 바퀴를 다시 발명하려 하지 말자.
- 아주 특별한 나만의 기능이 아니라면 이미 누군가가 구현해 놓았을 가능성이 크다.