## 14. Comparable을 구현할지 고려하라

### compareTo

- Object의 메서드가 아니며 성격은 Object의 equals와 같다.

- 단순 동치성 비교에 더해 순서까지 비교할 수 있으며 제네릭하다.

- Comparable을 구현했다는 것은 그 클래스의 인스턴스들에는 자연적인 순서가 있음을 뜻한다.

- Comparable을 구현한 객체들의 배열은 손쉽게 정렬할 수 있다.

  ```java
  Arrays.sort(a);
  ```

- 검색, 극단값 계산, 자동 정렬되는 컬렉션 관리도 쉽게 가능하다.

- 알파벳, 숫자, 연대 같이 순서가 명확한 값 클래스를 작성한다면 반드시 Comparable 인터페이스를 구현해야 한다.



### compareTo 메서드의 일반 규약

- 이 객체와 주어진 객체의 순서를 비교한다.
- 이 객체가 주어진 객체보다 작으면 음의 정수를, 같으면 0을, 크면 양의 정수를 반환한다.
- 이 객체와 비교할 수 없는 타입의 객체가 주어지면 ClassCastException을 던진다.
- 다음 설명에서 sgn 표기는 수학에서 말하는 부호 함수를 뜻하며 표현식의 값이 음수, 0, 양수일 때 -1, - 1을 반환하도록 정의했다.
  1. Comparable을 구현한 클래스는 모든 x, y에 대해 sgn(x.compareTo(y)) == -sgn(y.compareTo(x))여야 한다(따라서 x.compareTo(y)는 y.compareTo(x) 가 예외를 던질 때에 한해 예외를 던져야 한다.)
  2. Comparable을 구현한 클래스는 추이성을 보장해야 한다. 즉, (x.compareTo(y) > 0 && y.compareTo(z) > 0)이면 x.compareTo(z) > 0이다.
  3. Comparable을 구현한 클래스는 모든 z에 대해 x.compareTo(y) == 0이면 sgn(x.equals(y))여야 한다. Comparable을 구현하고 이 권고를 지키지 않는 모든 클래스는 그 사실을 명시해야 한다.
- compareTo 규약을 지키지 못하면 비교를 활용하는 클래스와 어울리지 못한다. (TreeSet, TreeMap, Collections, Arrays)



### compareTo 규약 설명

1. 첫 번째 규약
   - 두 객체 참조의 순서를 바꿔 비교해도 예상한 결과가 나와야 한다.
   - 첫 번째 객체가 두 번째 객체보다 작으면 두 번째가 첫 번째보다 커야 한다.
   - 첫 번째가 두 번째보다 크면 두 번째는 첫 번째보다 작아야 한다.
2. 두 번째 규약
   - 첫 번째가 두 번째보다 크고 두 번째보다 크고 두 번째가 세 번째보다 크면, 첫 번째는 세 번째보다 커야 한다.
3. 세 번째 규약
   - 크기가 같은 객체들끼리는 어떤 객체와 비교하더라도 항상 같아야 한다.
   - compareTo 메서드로 수행한 동치성 테스트 결과가 equals와 같아야 한다.
   - compareTo로 줄지은 순서와 equals의 결과가 일관되어야 한다.
   - 컬렉션이 구현한 인터페이스들은 동치성을 비교할 때 equals 대신 compareTo를 사용한다.

- 이상의 세 규약은 compareTo 메서드로 수행하는 동치성 검사도 반사성, 대칭성, 추이성을 충족해야 함을 뜻한다.
- 기존 클래스를 확장한 구체 클래스에서 새로운 값 컴포넌트를 추가했다면 compareTo 규약을 지킬 방법이 없다.
- Comparable을 구현한 클래스를 확장해 값 컴포넌트를 추가하고 싶다면 확장하는 대신 독립된 클래스를 만들고 이 클래스에 원래 클래스의 인스턴스를 가리키는 필드를 둔 후 내부 인스턴스를 반환하는 뷰 메서드를 제공하면 된다.



### equals와의 차이

- 타입이 다른 객체를 신경쓰지 않아도 된다.
- 타입이 다른 객체가 주어지면 간단히 ClassCastException을 던져도 되며 대부분 그렇게 한다.
- 규약에서는 다른 타입 사이의 비교도 허용하지만 보통은 비교할 객체들이 구현한 공통 인터페이스를 매개로 이뤄진다.



### compareTo 메서드 작성 요령

- Comparable은 타입을 인수로 받는 제네릭 인터페이스이므로 compareTo 메서드의 인수 타입은 컴파일타임에 정해지므로 입력 인수의 타입을 확인하거나 형변환할 필요가 없다.
- 인수타입이 잘못됐다면 컴파일 자체가 되지 않으며 null을 인수로 넣어 호출하면 NullPointerException을 던져야 한다.
- compareTo 메서드는 각 필드가 동치인지를 비교하는 게 아니라 그 순서를 비교한다.
- 객체 참조 필드를 비교하려면 compareTo 메서드를 재귀적으로 호출한다.
- Comparable을 구현하지 않은 필드나 표준이 아닌 순서로 비교해야 한다면 비교자(Comparator)를 대신 사용한다.



### 객체 참조 필드가 하나뿐인 비교자

```java
public final class CaseInsensitiveString implements Comparable<CaseInsensitiveString> {
	public int compareTo(CaseInsensitiveString cis) {
    return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s)
  }
  ...
}
```

- CaseInsensitiveString이 Comparable을 구현한 것은 참조끼리만 비교할 수 있다는 뜻으로 일반적으로 따르는 패턴이다.



### 기본 타입 필드가 여럿일 때의 비교자

```java
public int compareTo(PhoneNumber pn) {
  int result = Short.compare(areaCode, pn.areaCode); // 가장 중요한 필드
  if (result == 0) {
    result = Short.compare(prefix, pn.prefix); // 두 번째로 중요한 필드
    if (result == 0) {
      result = Short.compare(linenum, pn.lineNum);
    }
  }
  return result;
}
```

- compareTo메서드에서 관계연산자 <와 >를 사용하는 방식은 오류를 유발한다.
- 클래스에 핵심 필드가 여러 개라면 어느 것을 먼저 비교하느냐가 중요하다.
- 가장 핵심 필드부터 비교하고 비교 결과가 0이 아니라면 그 결과를 곧장 반환해야 한다.
- 가장 핵심이 되는 필드가 똑같다면 똑같지 않은 필드를 찾을 때까지 차례대로 비교해나간다.



### 비교자 생성 메서드를 활용한 비교자

```java
private static final Comparator<PhoneNumber> COMPARATOR =
  comparingInt((PhoneNumber pn) -> pn.areaCode)
  	.thenComparingInt(pn -> pn.prefix)
  	.thenComparingInt(pn -> pn.lineNum);

public int compareTo(PhoneNumber pn) {
  return COMPARATOR.compare(this, pn);
}
```

- 클래스를 초기화할 때 비교자 생성 메서드 2개를 이용해 비교자를 생성한다.
  1. comparingInt
     - 객체 참조를 int 타입 키에 매핑하는 키 추출 함수를 인수로 받아 그 키를 기준으로 순서를 정하는 비교자를 반환하는 정적 메서드다.
     - 예제에서는 람다를 인수로 받으며 추출한 지역코드를 기준으로 순서를 정하는 Comparator<PhoneNumber>를 반환한다.
     - 자바의 타입 추론 능력이 이상황에서 타입을 알아낼 만큼 강력하지 않기 때문에 람다에서 입력 인수 타입을 명시한다.
  2. thenComparingInt
     - Comparator의 인스턴스 메서드로 int 키 추출자 함수를 입력받아 다시 비교자를 반환한다.
     - 이 비교자는 첫 번재 비교자를 적용한 다음 새로 추출한 키로 추가 비교를 수행한다.
     - 원하는 만큼 연달아 호출할 수 있다.
     - 자바의 타입 추론 능력이 추론해 낼수 있을 정도였기 때문에 타입을 명시하지 않았다.

- 자바 8에서 Comparator 인터페이스가 일련의 비교자 생성 메서드와 팀을 꾸려 메서드 연쇄 방식으로 비교자를 생성할 수 있게 되었다.
- 약간의 성능 저하가 생긴다.
- 자바의 정적 임포트 기능을 이용하면 정적 비교자 생성 메서드들을 그 이름만으로 사용할 수 있어 코드가 훨씬 깔끔해진다.



### 객체 참조용 비교자 생성 메서드

1. Comparing 이라는 정적 메서드 2개가 다중정의되어 있다.
2. 첫 번째는 키 추출자를 받아서 그 키의 자연적 순서를 사용한다.
3. 두 번째는 키 추출자 하나와 추출된 키를 비교할 비교자까지 총 2개의 인수를 받는다.
4. thenComparing이란 인스턴스 메서드가 3개 다중정의되어 있다.
5. 첫 번째는 비교자 하나만 인수로 받아 그 비교자로 부차 순서를 정한다.
6. 두 번째는 키 추출자를 인수로 받아 그 키의 자연적 순서로 보조 순서를 정한다.
7. 세 번째는 키 추출자 하나와 추출된 키를 비교할 비교자까지 총 2개의 인수를 받는다.

- 객체 참조용 비교자 생성 메서드를 활용한 비교자

```java
static Comparator<Object> hashCodeOrder = 
  Comparator.comparingInt(o -> o.hashCode());
```

- 정적 compare 메서드를 활용한 비교자

```java
 	static Comparator<Object> hashCodeOder = new Comparator<>() {
 	  public int compare(Object o1, Object o2) {
 	    return Integer.compare(o1.hashCode(), o2.hashCode());
 	  }
 	}
```



