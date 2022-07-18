## 60. 정확한 답이 필요하다면 float와 double은 피하라

### float과 double

- float과 double 타입은 과학과 공학 계산용으로 설계되었다
- 이진 부동소수점 연산에 쓰이며, 넓은 범위의 수를 빠르게 정밀한 '근사치'로 계산하도록 세심하게 설계되었다.
- 따라서 정확한 결과가 필요할 때는 사용하면 안 된다.

```java
// 근사치로 계산하기에 정확한 값이 안나옴
System.out.println(1.03 - 0.42);
System.out.println(1.00 - 9 * 0.10);

// 0.6100000000000001
// 0.09999999999999998
// 60-1. 부동소수 타입을 사용하여 잘못된 결과가 발생함 
public static void main(String[] args) {
  double funds = 1.00;
  int itemsBought = 0;
  for (double price = 0.10; funds >= price; price += 0.10) {
    funds -= price;
    itemsBought++;
  }
  System.out.println(itemsBought + "개 구입");
  System.out.println("잔돈: " + funds);
}

// 3개 구입
// 잔돈: 0.3999999999999999
```



### 해결법1

- BigDecimal, int, long을 사용하자.

```java
// 60-2. BigDecimal 을 사용한 해법. 속도가 느리고 쓰기 불편하다.
public static void main(String[] args) {
  final BigDecimal TEN_CENTS = new BigDecimal(".10");

  int itemsBought = 0;
  BigDecimal funds = new BigDecimal("1.00");
  for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
    funds = funds.subtract(price);
    itemsBought++;
  }

  System.out.println(itemsBought + "개 구입");
  System.out.println("잔돈: " + funds);
}

// 4개 구입
// 잔돈: 0.00
```

- 올바른 답이 나오지만 기본 타입보다 쓰기가 훨씬 불편하고, 느리다 라는 단점이 있다.
- 짧은 계산이라면 느리다는 문제는 무시할 수 있지만, 쓰기 불편한 점은 아쉬울 것이다.



### 해결법2

- int 혹은 long 타입을 사용하자.

```java
// 60-3. 정수 타입을 사용한 해법 
public static void main(String[] args) {
  int itemsBought = 0;
  int funds = 100;

  for (int price = 10; funds >= price; price += 10) {
    funds -= price;
    itemsBought++;
  }

  System.out.println(itemsBought + "개 구입");
  System.out.println("잔돈: " + funds);
}

// 4개 구입
// 잔돈: 0
```



### 핵심

- 정확한 답이 필요한 계산에는 float, double 사용을 피하라.
- 코딩시 불편함이나 성능 저하를 신경 쓰지 않겠다면 BigDecimal을 사용하라.
- 성능이 중요하고 소수점을 직접 추적할 수 있고 숫자가 너무 크지 않다면 int, long 을 사용하라.

