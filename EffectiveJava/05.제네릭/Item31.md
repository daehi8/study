## 31. 한정적 와일드카드를 사용해 API 유연성을 높이라

### 한정적 와일드카드 타입

```java
public void pushAll(Iterable<? extends E> src) {
	for (E e : src)
		push(e);
}
```

- pushAll의 입력 매개변수 타입은 E의 Iterable이 아니라 E의 하위 타입의 Iterable 이어야 한다.
- 유연성을 극대화하려면 원소의 생산자나 소비자용 입력 매개변수에 와일드카드 타입을 사용해야 한다.
- 입력 매개변수가 생산자와 소비자 역할을 동시에 한다면 와일드카드 타입을 써도 좋을 게 없다.



### 와일드카드 타입을 써야하는 상황

- 펙스(PECS) : producer-extends, consumer-super
- 매개변수화 타입 T가 생산자라면 <? extends T>를 사용하고 소비자라면 <? super T>를 사용해야 한다.

```java
public Chooser(Collection<T> choices)
->  
public Chooser(Cillection<? extends T> choices)
```

- Chooser<Number>의 생성자에 List<Integer>를 넘기고 싶을 경우 수정 전 생성자로는 컴파일조차 되지 않겠지만 한정적 와일드카드 타입으로 선언한 수정 후 생성자에서는 문제가 사라진다.
- 반환 타입에는 한정적 와일드 카드 타입을 사용하면 안 된다.
- 제대로 사용한다면 클래스 사용자는 와일드카드 타입이 쓰였다는 사실조차 의식하지 못할 것이다.
- 클래스 사용자가 와일드카드 타입을 신경 써야 한다면 API에 무슨 문제가 생겼을 가능성이 크다.



### 타입 매개변수와 와일드카드 방식

```java
public static <E> void swap(List<E> list, int i, int j); // 비한정적 타입 매개변수
public static void swap(List<?> list, int i, int j); // 비한정적 와일드카드
```

- public API라면 비한정적 와일드카드가 좋다.
- 메서드 선언에 타입 매개변수가 한 번만 나오면 와일드 카드로 대체하는 것이 좋다.
- 비한정적 타입 매개변수라면 비한정적 와일드카드로 바꾸고 한정적 타입 매개변수라면 한정적 와일드카드로 바꾸면된다.



### 비한정적 와일드카드 도우미 제네릭 메서드

- List<?>에는 null 외에는 어떤 값도 넣을 수 없다.
- 와일드카드 타입의 실제 타입을 알려주는 메서드를 private 도우미 메서드로 따로 작성해 활용해야 한다.

```java
private static <E> void swapHelper(List<E> list, int i, int j) {
  list.set(i, list.set(j, list.get(i)));
}
```

- swapHelper 메서드는 리스트가 List<E>임을 알고 있다.
- 리스트에서 꺼낸 값의 타입은 항상 E이고 E 타입의 값이라면 이 리스트에 넣어도 안전함을 알고 있다.
- swap 메서드를 호출하는 클라이언트는 복잡한 swapHelper의 존재는 모른채 사용이 가능하다.



### 핵심

- 조금 복잡해도 와일드카드 타입을 적용하면 API가 훨씬 유연해진다.
- 널리 쓰일 라이브러리를 작성한다면 반드시 와일드카드 타입을 적절히 사용해줘야 한다.
- PECS 공식을 잘 활용해 생산자는 extends를 소비자는 super를 사용한다.
- Comparable과 Comparator도 모두 소비자이다.