## 25. 톱레벨 클래스는 한 파일에 하나만 담으라

### 컴파일 순서

- 소스 파일 하나에 톱레벨 클래스를 여러개 선언하더라도 자바 컴파일러는 불평하지 않는다.
- 한 클래스를 여러 가지로 정의할 수 있으며 그중 어느 것을 사용할지는 어느 소스 파일을 먼저 컴파일하냐에 따라 달라지기 때문이다.



### 해결책

- 톱 레벨 클래스들을 서로 다른 소스 파일로 분리하면 된다.
- 여러 톱레벨 클래스를 한 파일에 담고 싶다면 정적 멤버 클래스를 사용하는 방법이 있다.
- 다른 클래스에 딸린 부차적인 클래스라면 정적 멤버 클래스로 만드는 쪽이 일반적으로 더 나을 것이다.
- 가독성이 좋고 private으로 선언하면 접근 범위도 최소로 관리할 수 있기 때문이다.

```java
public class Test{
  public static void main(String[] args) {
    System.out.println(Utensil.NAME + Dessert.NAME);
  }
  
  private static class Utensil {
    static final String NAME = "pan";
  }
  
  private static class Dessert {
    static final String NAME = "cake";
  }
}
```



### 핵심

- 소스 파일 하나에는 반드시 톱레벨 클래스를 하나만 담아야 한다.
- 소스 파일을 어떤 순서로 컴파일 하든 파일이나 프로그램의 동작이 달라지는 일이 사라질 것이다.