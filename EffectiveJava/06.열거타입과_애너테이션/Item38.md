## 38. 확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라

### 타입 안전 열거 패턴

```java
public class Operation {
  public static final Operation PLUS = new Operation("+", (x, y) -> x + y);
  public static final Operation MINUS = new Operation("-", (x, y) -> x - y);
  public static final Operation TIMES = new Operation("*", (x, y) -> x * y);
  public static final Operation DIVIDE = new Operation("/", (x, y) -> x / y);

  private final String symbol;
  private final BiFunction<Double, Double, Double> func;

  private Operation(String symbol, BiFunction<Double, Double, Double> func) {
    this.symbol = symbol;
    this.func = func;
  }

  public double apply(double x, double y) {
    return this.func.apply(x, y);
  }
}

```

- 열거한 값들을 그대로 가져온 다음 값을 더 추가하여 다른 목적으로 쓸 수 있다.
- 확장성을 높이는 대신 고려할 요소가 늘어나 설계와 구현이 더 복잡해진다.



### 연산 코드

```java
// 열거 타입
public enum Operation {
  PLUS("+", (x, y) -> x + y),
  MINUS("-", (x, y) -> x - y),
  TIMES("*", (x, y) -> x * y),
  DIVIDE("/", (x, y) -> x / y);

  private final String symbol;
  private final BiFunction<Double, Double, Double> func;

  Operation(String symbol, BiFunction<Double, Double, Double> func) {
    this.symbol = symbol;
    this.func = func;
  }

  public double apply(double x, double y) {
    return this.func.apply(x, y);
  }
}

//타입 안전 열거 패턴은 확장가능 (단, 부모 클래스인 Operation의 생성자가 protected 여야 함)
public class ExtendedOperation extends Operation {

  public static final ExtendedOperation EXP = new ExtendedOperation("^", (x, y) -> Math.pow(x, y));
  public static final ExtendedOperation REMAINDER = new ExtendedOperation("%", (x, y) -> x % y);

  protected ExtendedOperation(String symbol, BiFunction<Double, Double, Double> func) {
    super(symbol, func);
  }
}

//열거 타입은 확장 불가능 (컴파일 에러)
public enum ExtendedOperation extends Operation {
  ...
}
```

- 타입 안전 열거 패턴이 주로 쓰이는 경우
- 연산 코드의 각 원소는 특정 기계가 수행하는 연산을 뜻한다.
- API가 제공하는 기본 연산 외에 사용자 확장 연산을 추가할 수 있도록 열어줘야 할 때가 있다.



### 열거타입과 인터페이스 활용

```java
public interface Operation {
  double apply(double x, double y);
}

public enum BasicOperation implements Operation {
  PLUS("+", (x, y) -> x + y),
  MINUS("-", (x, y) -> x - y),
  TIMES("*", (x, y) -> x * y),
  DIVIDE("/", (x, y) -> x / y);

  private final String symbol;
  private final BiFunction<Double, Double, Double> func;

  BasicOperation(String symbol, BiFunction<Double, Double, Double> func) {
    this.symbol = symbol;
    this.func = func;
  }

  @Override
  public double apply(double x, double y) {
    return this.func.apply(x, y);
  }

  @Override
  public String toString() {
    return symbol;
  }
}

public enum ExtendedOperation implements Operation {
  EXP("^", (x, y) -> Math.pow(x, y)), 
  REMAINDER("%", (x, y) -> x % y);

  private final String symbol;
  private final BiFunction<Double, Double, Double> func;

  ExtendedOperation(String symbol, BiFunction<Double, Double, Double> func) {
    this.symbol = symbol;
    this.func = func;
  }

  @Override
  public double apply(double x, double y) {
    return this.func.apply(x, y);
  }

  @Override
  public String toString() {
    return symbol;
  }
}
```

- 열거 타입이 임의의 인터페이스를 구현할 수 있다는 사실을 이용하는 것이다.

- 연산 코드용 인터페이스를 정의하고 열거 타입이 이 인터페이스를 구현하게 하면 된다.

- 열거 타입이 그 인터페이스의 표준 구현체 역할을 한다.

  

### 핵심

- 열거 타입 자체는 확장할 수 없지만 인터페이스와 그 인터페이스를 구현하는 기본 열거 타입을 함께 사용해 같은 효과를 낼 수 있다.
- 이렇게 하면 클라이언트는 이 인터페이스를 구현해 자신만의 열거타입을 만들 수 있다.
- API가 인터페이스 기반으로 작성되었다면 기본 열거 타입의 인스턴스가 쓰이는 모든 곳을 새로 확장한 열거 타입의 인스턴스로 대체해 사용할 수 있다.

