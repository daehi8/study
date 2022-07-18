## 54. null이 아닌 빈 컬렉션이나 배열을 반환하라



### 1. null을 반환하는 경우

### 컬렉션이 비었을 때 null을 반환하는 코드 - 따라 하지 말 것

```
private final List<Cheese> cheesesInStock = ... ;

/**
 * @return 매장 안의 모든 치즈 목록을 반환한다.
 * 단, 재고가 하나도 없다면 null을 반환한다.
 */
public List<Cheese> getCheeses() {
    return cheesesInStock.isEmpty() ? null : new ArrayList<>(cheesesInStock); 
}
```

- 클라이언트측에서 null 처리를 해주기 위해 추가적인 노력이 필요하다

 

### 클라이언트측에서 null 상황을 처리하는 코드

```
List<Cheese> cheeses = shop.getCheeses();
if (cheeses != null && cheeses.contains(Cheese.STILTON))
    System.out.println("좋았어, 바로 그거야.");
```

- 컬렉션이나 배열 같은 컨테이너가 비었을 때 null을 반환하는 메서드를 사용할 때면 항상 이와 같은 방어 코드를 넣어줘야 한다
- 방어 코드를 누락하면 오류가 발생할 수 있다
- 반환하는 쪽에서도 이러한 상황을 특별히 취급해줘야 해서 코트가 더 복잡해진다

 

### 2. 빈 컬렉션 반환하기

- 일반적인 상황에서 빈 컬렉션을 할당하고 반환하는 것은 null을 반환하는 것과 성능적인 면에서 큰 차이가 없다. 따라서 다음과 같이 코드를 작성하면 된다.

```java
public List<Cheese> getCheeses() {
    return new ArrayList<>(cheesesInStock);
}
```

 

- 빈 컬렉션 할당이 성능을 크게 떨어뜨린다고 판단되는 경우에 매번 똑같은 빈 불변컬렉션을 반환하는 방식을 선택할 수 있다

```java
public List<Cheese> getCheeses() {
    return cheesesInStock.isEmpty() ? Collections.emptyList() : new ArrayList<>(cheesesInStock);
}
```

 

- 배열도 마찬가지이다. null 대신 길이가 0인 배열(또는 정확한 길이의 배열)을 반환하게끔 한다.

```java
public Cheese[] getCheeses() {
    return cheesesInStock.toArray(new Cheese[0]);
}
```

 

- 새로운 배열을 계속 할당하는 것이 우려된다면, 배열을 미리 선언하고 매번 그 배열을 반환하는 방식을 취할 수 있다. 그러나 이는 권장하지 않는다.

```java
private static final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];

public Cheese[] getCheeses() {
    return cheesesInStock.toArray(EMPTY_CHEESE_ARRAY);
}
```

- 이렇게 toArray에 넘기는 배열을 미리 할당하는 것이 오히려 성능을 떨어진다는 연구 결과가 있기 때문에 단순한 성능 개선을 목적으로 위와 같이 코드를 작성하는 것은 지양하라



### 핵심

- null이 아닌 빈 배열이나 컬렉션을 반환하라
- null을 반환하는 API는 사용하기 어렵고 오류 처리 코드도 늘어난다. 또한, 성능이 좋지도 않다

 