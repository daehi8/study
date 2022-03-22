## 16. public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라

### 접근자와 변경자 메서드를 활용한 캡슐화

```java
class Point {
  private double x;
  private double y;
  
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public double getX() { return x; }
  public double getY() { return y; }
  
  public void setX(double x) { this.x = x; }
  public void setY(double y) { this.y = y; }
}
```

- 패키지 바깥에서 접근할 수 있는 클래스라면 접근자를 제공함으로써 클래스 내부 표현 방식을 언제든 바꿀수 있는 유연성을 얻을 수 있다.
- 필드를 공개하면 이를 사용하는 클라이언트가 생길 것이므로 내부 표현 방식을 마음대로 바꿀 수 없게 된다.



### 데이터 필드 노출

- package-private 클래스 혹은 private 중첩 클래스라면 데이터 필드를 노출한다 해도 문제가 없다.
- 그 클래스가 표현하려는 추상 개념만 올바르게 표현해주면 된다.
- 클래스 선언 면에서나 클라이언트 코드 면에서나 훨씬 깔끔하다.
- 클라이언트 코드가 이 클래스 내부 표현에 묶이지만 클라이언트도 이 클래스를 포함하는 패키지 안에서만 동작하는 코드일 뿐이다.
- 패키지 바깥 코드는 전혀 손대지 않고도 데이터 표현 방식을 바꿀 수 있다.
- private 중첩 클래스의 경우 수정 범위가 더 좁아져서 이 클래스를 포함하는 외부 클래스까지로 제한된다.



### public 클래스의 불변 필드

- API를 변경하지 않고는 표현 방식을 바꿀 수 없고 필드를 읽을때 부수 작업을 수행할 수 없다.
- 불변식은 보장할 수 있게 된다.

