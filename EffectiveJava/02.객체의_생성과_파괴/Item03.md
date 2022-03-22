## 03. private 생성자나 열거 타입으로 싱글턴임을 보증하라

### 싱글턴(singleton)

인스턴스를 오직 하나만 생성할 수 있는 클래스

싱글턴 인스턴스를 mock 구현으로 대체할 수 없기 때문에 클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워 질 수 있다.

### 생성 방식

* 생성자를 private로 감추고 유일한 인스턴스에 접근할 수 있는 수단으로 public static 멤버를 하나 생성

### public static final 필드 방식의 싱글턴

```java
public class Elvis {
  public static final Elvis INSTANCE = new Elvis();
  private Elvis() {...}
  
  public void leaveTheBuilding() {...}
}
```

- private 생성자는 public static final 필드를 초기화할 때 딱 한 번만 호출한다.
- public이나 protected 생성자가 없으므로 클래스가 초기화될 때 만들어진 인스턴스가 전체 시스템에서 하나뿐임을 보장한다.
- 해당 클래스가 싱글턴임이 명백히 드러나며 코드가 간결하다.

### 정적 팩터리 방식의 싱글턴

```java
public class Elvis {
  private static final Elvis INSTANCE = new Elvis();
  private Elvis() {...}
  public static Elvis getInstance() { return INSTANCE; }
  
  public void leaveTheBuilding() {...}
}
```

- API를 바꾸지 않고도 싱글턴이 아니게 변경할 수 있다.
- 정적 팩터리를 제네릭 싱글턴 팩터리로 만들 수 있다.
- 정적 팩터리의 메서드 참조를 공급자로 사용할 수 있다.
- 이러한 장점들이 굳이 필요하지 않다면 public 필드 방식의 싱글턴이 좋다.

### 열거 타입 방식의 싱글턴

```java
puvlic enum Elvis {
  INSTANCE;
  
  public void leaveTheBuilding() {...}
}
```

- Public 필드 방식과 비슷하지만 더 간결하고 추가적인 노력 없이 직렬화 할 수 있다.
- 아주 복잡한 직렬화 상황이나 리플렉션 공격에서도 제2의 인스턴스가 생기는 일을 완벽히 막아준다.
- 대부분의 상황에서는 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이다.
- 단, 만들려는 싱글턴이 Enum 외의 클래스를 상속해야 한다면 이 방법은 사용할 수 없다.