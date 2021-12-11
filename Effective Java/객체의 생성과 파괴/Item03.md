## 03. private 생성자나 열거 타입으로 싱글턴임을 보증하라

### 싱글턴(singleton)

인스턴스를 오직 하나만 생성할 수 있는 클래스

싱글턴 인스턴스를 mock 구현으로 대체할 수 없기 때문에 클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워 질 수 있다.

1. 생성 방식

생성자를 private로 감추고 유일한 인스턴스에 접근할 수 있는 수단으로 public static 멤버를 하나 생성

1. public static final 필드 방식의 싱글턴

```java
public class Elvis {
  public static final Elvis INSTANCE = new Elvis();
  private Elvis() {...}
  
  public void leaveTheBuilding() {...}
}
```

private 생성자는 public static final 필드를 초기화할 때 딱 한 번만 호출

public이나 protected 생성자가 없으므로 클래스가 초기화될 때 만들어진 인스턴스가 전체 시스템에서 하나뿐임이 보장

2. 정적 팩터리 방식의 싱글턴

```java

```