## 55. 옵셔널 반환은 신중히 하라

### 자바 8 이전

- 자바 8전에는 메서드가 특정 조건에서 값을 반환할 수 없을 때 두가지 선택지가 있었다.

1. Exception Throw
   - 예외는 반드시 예외적인 상황에서만 사용해야 한다.
   - 예외는 실행 스택을 추적 캡처하기 때문에 비용이 비싸다.

2. Null Return
   - null을 리턴하는 경우에는 Null Pointer Exception을 항상 조심해야한다.

 

### Optional 도입

- 자바 8에서 Optional<T>가 도입되면서 선택지가 하나 늘었다. 
- Optionl이란 null이 아닌 T타입 참조를 하나 담거나 아무것도 담지 않은 일종의 래퍼 클래스이다.
- Optional은 원소를 최대 1개 가질 수 있는 불변 Collection이다.
- 자바 8이전의 코드보다 null-safe한 로직을 처리할 수 있게 해준다.
- Optional을 반환하여 좀 더 유연하게 작성할 수 있게 해준다.
- 반환값이 Optional인 경우는 반환값이 없을 수도 있음을 클라이언트에게 알려준다.

 

### Optional 메서드

- Optional.empty()
  - 내부 값이 비어있는 Optional 객체 반환
- Optional.of(T value)
  - 내부 값이 value인 Optional 객체 반환
  - 만약 value가 null인 경우 NullPointerException 발생
- Optional.ofNullable(T value)
  - 가장 자주 쓰이는 Optional 생성 방법
  - value가 null이면, empty Optional을 반환하고, 값이 있으면 Optional.of로 생성
- T get()
  - Optional 내의 값을 반환
  - 만약 Optional 내부 값이 null인 경우 NoSuchElementException 발생
- boolean isPresent()
  - Optional 내부 값이 null이면 false, 있으면 true
  - Optional 내부에서만 사용해야하는 메서드라고 생각
- boolean isEmpty()
  - Optional 내부의 값이 null이면 true, 있으면 false
  - isPresent() 메서드의 반대되는 메서드
- void ifPresent(Consumer<? super T> consumer)
  - Optional 내부의 값이 있는 경우 consumer 함수를 실행
- Optional<T> filter(Predicate<T> predicate)
  - Optional에 filter 조건을 걸어 조건에 맞을 때만 Optional 내부 값이 있음
  - 조건이 맞지 않으면 Optional.empty를 리턴
- Optional<U> map(Funtion<? super T, ? extends U> f)
  - Optional 내부의 값을 Function을 통해 가공
- T orElse(T other)
  - Optional 내부의 값이 있는 경우 그 값을 반환
  - Optional 내부의 값이 null인 경우 other을 반환
- T orElseGet(Supplier<? extends T> supplier)
  - Optional 내부의 값이 있는 경우 그 값을 반환
  - Optional 내부의 값이 null인 경우 supplier을 실행한 값을 반환
- T orElseThrow()
  - Optional 내부의 값이 있는 경우 그 값을 반환
  - Optional 내부의 값이 null인 경우 NoSuchElementException 발생
- T orElseThrow(Supplier<? extends X> exceptionSupplier)
  - Optional 내부의 값이 있는 경우 그 값을 반환
  - Optional 내부의 값이 null인 경우 exceptionSupplier을 실행하여 Exception 발생

 

### Optional를 이용한 최댓값 구하는 메서드

```java
public static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
    if(c.isEmpty())
        return Optional.empty();
    
    E result = null;
    for(E e : c){
        if(result == null || e.compareTo(result) > 0){
            result = Objects.requireNonNull(e);
        }
    }
    
    return Optional.of(result);
}
```

 

### 기본값 세팅

```java
String lastWord = max(words).orElse("단어없음");
```

####  

### 원하는 예외 던짐

```java
Toy myToy = max(toys).orElseThrow(TemperTantrumException::new);
```

 

### 항상 값이 있을 경우

```java
Element lastNobleGas = max(Elements.NOBLE_GASES).get();
```

 

### isPresent() 메서드를 이용한 Optional 핸들링

```java
// null을 처리하는 로직과 유사
Optional<ProcessHandle> parentProcess = ph.parent();
System.out.println("Parent PID: " + (parentProcess.isPresent() ? String.valueOf(parentProcess.get().pid()) : "N/A"));

// Optional의 map() 메서드를 이용
System.out.println("Parent PID: " + ph.parent().map(h -> String.valueOf(h.pid())).orElse("N/A"));

// Stream으로 present의 value를 추출
streamOfOptionals
        .filter(Optional::isPresent)
        .map(Optional::get)
```

 

### 주의사항

- Optional.of(value)에 null을 넣으면 NPE를 발생하므로 주의해야한다.
- null 값도 허용하는 옵셔널을 만들려면 Optional.ofNullalble(value)를 사용하면 된다.
- Optional을 반환하는 메서드에서는 절대 null을 반환하면 안된다. 옵셔널을 도입한 취지를 완전히 무시하는 행위이다.
- 컬렉션, 스트림, 배열, 옵셔널과 같은 컨테이너는 또 Optional로 감싸지 말아야한다.
- 반환결과가 없을 수 있을 때, 클라이언트가 특별하게 처리해야 한다면 Optional을 반환한다.
- Optional로 wrap하고 다시 꺼내는 비용, 객체 생성 비용 등이 있으니 오버헤드가 발생한다. 유저가 실수할 여지를 줄이느냐, 성능을 중시하느냐 잘 고려해야한다.
- 박싱된 기본 타입을 Optional로 반환하지 말자.
- 기본 타입을 감싸서 Optional로 또 감싼다면 2중으로 감싸게 된다. 성능상에 문제가 있다. 자바 API에는 int, double, long 전용 Optional 클래스를 만들었다.
- Optional을 컬렉션의 Key, Value, 혹은 배열의 Element로 사용하지 말자
- 필드를 Optional로 선언하는건 대부분의 상황에서 좋지 않다. 
- 대부분의 상황에서는 Optional을 사용하는 대신에 클래스를 나누어 필수 필드만을 담고 있는 클래스를 만들고 이를 확장해 선택 필드를 추가해야 한다는 일종의 신호이기도 하다.

 

### 핵심

- 값을 반환하지 못할 가능성이 있고, 호출할 때 마다 반환값이 없을 가능성도 염두에 둬야하는 메서드라면 옵셔널을 반환해야할 상황일 수 있다.
- 옵셔널 반환에는 성능 저하가 뒤따르니, 성능에 민감한 메서드라면 null을 반환하거나 예외를 던지는 편이 나을 수 있다. 
- 옵셔널을 반환값 이외의 용도로 쓰는 경우는 매우 드물다.