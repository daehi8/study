## 61. 박싱된 기본 타입보다는 기본 타입을 사용하라

### 데이터 타입

- 자바의 데이터 타입은 기본형, 참조형이 있다.
- 그리고 각각의 기본형에 대응하는 참조타입이 이쓴데 이를 박싱된 기본 타입이라고 한다.
- 예를 들어 Int, double, boolean 이 기본타입이라면 Integer, Double, Boolean 을 박싱된 기본타입이라고 한다.
- 오토박싱과 오토언박싱 덕분에 크게 구분하지 않고 사용할 수는 있지만 둘의 차이점은 분명히 알고 사용하여야 한다.



### 기본 타입과 박싱된 기본 타입의 차이

1. 기본 타입은 값만 가지고, 박싱된 기본 타입은 값에 더해 식별성 (identity)라는 속성을 가진다. 즉, 박싱된 기본 타입의 두 인스턴스는 값이 같아도 서로 다르다고 식별될 수 있다는 뜻이다.
2. 기본 타입의 값은 언제나 유효하나, 박싱된 기본타입의 경우 null 을 가질 수 있다.
3. 기본 타입이 박싱된 기본 타입보다 시간과 메모리면에서 더 효율 적이다.
4. 박싱된 기본 타입에 == 연산자를 사용하면 오류가 일어난다.



### 박싱된 기본 타입 문제점 예시

```java
// 1
Comparator<Integer> naturalOrder =
        (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);

// 2
Comparator<Integer> naturalOrderMod = (iBoxed, jBoxed) -> {
    int i = iBoxed;
    int j = jBoxed;
    return i < j ? -1 : (i == j ? 0 : 1);
};

// 3
Comparator<Integer> naturalOrder2 =
        (i, j) -> i.compareTo(j);

// 4
Comparator<Integer> naturalOrder3 =
        Integer::compareTo;
```

- 1번의 경우 new Integer(42), new Integer(42) 를 넣으면 0을 기대하지만 1이 나온다. 왜냐면 식별성을 갖는 박싱 타입을 == 연산자로 비교하기 때문이다.
- 박싱된 기본 타입은 == 연산자가 아니라 equals 를 사용해야 한다.

- 2, 3, 4 번은 같은 값을 넣어도 제대로 작동한다.
- 2번의 경우 안에서 언박싱해주고 연산을 수행하는 방식이다.
- 3번의 경우 Integer.compare() 를 람다식으로 사용한다.
- 4번의 경우 Integer.compare() 를 메서드 참조 방식으로 사용한다.



### NPE 주의

- 거의 대부분의 경우 기본 타입과 박싱된 기본 타입을 혼용한 연산에서는 박싱된 기본 타입의 박싱이 자동으로 풀린다. 그리고 null 참조를 언박싱하면 NPE가 발생한다.
- 박싱된 기본 타입의 경우 null이 가능하다.

```java
Integer i = null;
```



### 연산속도

```java
public static void main(String[] args) {
    // 1
  long startTime = System.nanoTime();
  Long sum = 0L;
  for (long i = 0; i < Integer.MAX_VALUE; i++) {
    sum += i;
  }
  long endTime = System.nanoTime();
  System.out.println("sum: " + sum);
  System.out.println("time: " + (endTime - startTime));

    // 2
  long startTime2 = System.nanoTime();
  long sum2 = 0L;
  for (long i = 0; i < Integer.MAX_VALUE; i++) {
    sum2 += i;
  }
  long endTime2 = System.nanoTime();
  System.out.println("sum: " + sum2);
  System.out.println("time: " + (endTime2 - startTime2));
}

/*
sum: 2305843005992468481
time: 3651689500
sum: 2305843005992468481
time: 714225875
*/
```

- 1번 코드의 경우 박싱된 기본 타입 Long 을, 2번 코드의 경우 기본타입 long 을 사용하였다.
- 확연히 시간 차이가 나는 것을 볼 수 있다.
- 이유는 1번의 경우 for 문에서 합산할때마다 Long sum이 박싱과 언박싱을 반복해서 느려지는 것이다.



### 박싱된 기본타입을 사용하는 경우

1. 컬렉션의 원소, 키, 값으로 쓴다.
   - 기본 타입은 쓸 수없으니 당연하다.
2. 매개변수화 타입이나 매개변수화 메서드의 타입 매개변수로 쓴다.
   - 마찬가지로 기본 타입은 사용할 수 없다.
3. 리플렉션 (Item65) 을 통해 메서드를 호출할 때도 쓴다.



### 핵심

- 기본 타입과 박싱된 기본 타입 중 하나를 선택해야 한다면 가능하면 기본 타입을 사용하라.
- 박싱된 기본 타입은 == 연산 시 동일성이 계산된다.
- 박싱된 기본 타입을 써야 한다면 주의를 기울이자.
- 박싱된 기본 타입의 연산이 느리다.
- 박싱된 기본 타입과 기본 타입을 섞어쓰면 NPE 위험이 있다.