## 44. 표준 함수형 인터페이스를 사용하라

### 람다 API 작성 방법

- 상위 클래스의 기본 메서드를 재정의해 원하는 동작을 구현하는 템플릿 메서드 패턴의 매력이 줄었다.
- 이를 대체하는 해법은 같은 효과의 함수 객체를 받는 정적 팩터리나 생성자를 제공하는 것이다.
- 필요한 용도에 맞는 게 있다면 직접 구현하지 말고 표준 함수형 인터페이스를 활용하는 것이 좋다.



### 표준 함수형 인터페이스

- Operator: 인수가 하나인 `UnaryOperator`와, 2개인 `BinaryOperator`. 반환값과 인수의 타입이 같다.
- Predicate: 인수를 받아 boolean 반환.
- Function: 인수와 반환 타입이 다른 함수
- Supplier: 인수를 받지 않고 값을 반환.
- Consumer: 인수를 하나 받고 반환값은 없는 함수



### 표준 함수형 인터페이스의 특징

- 대부분 기본 타입만 지원한다. 그렇다고 기본 함수형 인터페이스에 박싱된기본 타입을 넣어 사용하지는 말자. 성능이 느려질 수 있다.



### 표준 함수형 인터페이스를 직접 작성해야 하는 경우

- 이름 자체가 용도를 잘 설명한다.
- 구현하는 쪽에서 지켜야할 규칙을 담고 있다.
- 디폴트 메서드를 담고 있다.
- 이 중 하나 이상을 만족한다면 전용 함수형 인터페이스를 구현하는 것을 고민해야 한다.



### @FunctionalInterface 애너테이션

- 전용 함수형 인터페이스에는 항상 @FunctionalInterface 애너테이션을 사용하자.
- 인터페이스가 람다용으로 설계된 것임을 알려준다.
- 인터페이스가 추상 메서드를 하나만 갖고 있어야 컴파일 되게 해준다.
- 누군가 실수로 메서드를 추가하지 못하게 해준다.



### 핵심

- API를 설계할 때 람다도 염두에 두어야 한다.
- 입력값과 반환값에 함수형 인터페이스 타입을 활용해야 한다.
- 보통은 표준 함수형 인터페이스를 사용하는 것이 가장 좋은 선택이다.
- 흔치 않은 경우에 직접 새로운 함수형 인터페이스를 만들어 쓰는 편이 나을 수도 있다.