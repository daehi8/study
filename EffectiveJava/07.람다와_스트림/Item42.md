## 42. 익명 클래스보다는 람다를 사용하라

### 함수 객체

- 함수 타입을 표현할 때 추상 메서드를 하나만 담은 인터페이스의 인스턴스
- 특정 함수나 동작을 나타내는데 사용



### 익명 클래스

```java
Collections.sort(words, new Comparator<String>() {
    public int compare(String s1, String s2) {
        return Integer.compare(s1.length(), s2.length());
    }
});
```

- 함수 객체를 만드는 주요 수단
- 코드가 너무 길기 때문에 적합하지 않다.



### 람다식

```java
Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
Collections.sort(words, comparingInt(String::length));
words.sort(comparingInt(String::length));
```

- 함수형 인터페이스라 부르는 이 인터페이스들의 인스턴스
- 익명 클래스와 개념은 비슷하지만 코드는 훨씬 간결
- 타입을 명시해야 코드가 더 명확할 때만 제외하고는 람다의 모든 매개변수 타입은 생략
- 컴파일러가 오류를 낼 때만 해당 타입을 명시



### 열거 타입의 동작을 표현한 람다

```java
public enum Operation {
    PLUS("+", (x, y) -> x+y);
    MINUS("-", (x,y) -> x-y);
    TIMES("*", (x,y) -> x*y);
    DIVIDE("/", (x,y) -> x / y);
    
    private final String symbol;
    private final DoubleBinaryOperator op;
    
    Operation(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }
    
    @Override
    public String toString() {
        return symbol;
    }
    
    public double apply(double x, double y) {
        return op.applyAsDouble(x, y);
    }
}
```

- 람다를 이용하면 열거 타입의 인스턴스 필드를 이용하는 방식으로 상수별로 다르게 동작하는 코드를 쉽게 구현할 수 있다.
- 각 열거 타입 상수의 동작을 람다로 구현해 생성자에 넘기고 생성자는 이 람다를 인스턴스 필드로 저장해둔다.
- apply 메서드에서 필드에 저장된 람다를 호출하기만 하면 된다.



### 람다의 특징

- 람다는 이름이 없고 문서화도 못한다.
- 코드 자체로 동작이 명확히 설명되지 않거나 코드 줄 수가 많아지면 쓰지 말아야 한다.
- 한줄일 때 가장 좋고 길어야 세 줄안에 끝내는 것이 좋다.
- 람다를 직렬화하는 일은 하지 말아야 한다.



### 람다로 대체할 수 없는 것

- 추상 클래스의 인스턴스를 만들 때는 익명 클래스를 써야 한다.
- 추상 메서드가 여러 개인 인터페이스의 인스턴스를 만들 때도 익명 클래스를 쓸 수 있다.
- 람다는 자신을 참조할 수 없다.



### 핵심

- 익명 클래스는 함수형 인터페이스가 아닌 타입의 인스턴스를 만들 때만 사용해야 한다.
- 람다는 작은 함수 객체를 아주 쉽게 표현할 수 있다.