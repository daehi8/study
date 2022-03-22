## 13. clone 재정의는 주의해서 진행하라

### Cloneable

- 복제해도 되는 클래스임을 명시하는 욛오의 믹스인 인터페이스 
- clone 메서드가 선언된 곳이 Cloneable이 아닌 Object이고 그마저도 protected이다.
- Cloneable을 구현하는 것만으로는 외부 객체에서 clone 메서드를 호출할 수 없다.
- Object의 protected 메서드인 clone의 동작 방식을 결정한다.
- Cloneable을 구현한 클래스의 인스턴스에서 clone을 호출하면 그 객체의 필드들을 하나하나 복사한 객체를 반환하며, 그렇지 않은 클래스와 인스턴스에서 호출하면 CloneNotSuppertedException을 던진다.



### 실무에서의 구현

- 실무에서 Cloneable을 구현한 클래스는 clone 메서드를 public으로 제공하며 사용자는 당연히 복제가 제대로 이뤄지리라 기대한다.

- 클래스와 모든 상위 클래스는 복잡하고, 강제할 수 없고, 허술하게 기술된 프로토콜을 지켜야 하는데 그 결과 깨지기 쉽고 위험하고 모순적인 메커니즘이 탄생한다.

- 생성자를 호출하지 않고도 객체를 생성할 수 있게 되는 것이다.

  

### clone 메서드의 일반 규약

```java
/*
이 객체의 복사본을 생성해 반환한다.
복사의 정확한 뜻은 그 객체를 구현한 클래스에 따라 다를 수 있다.
어떠한 객체 x에 대해 다음 식들은 참이다.
*/
x.clone() != x
  
x.clone().getClass() == x.getClass()

/*
일반적으로 참이지만 필수는 아니다.
*/  
x.clone.equals(x) 
  
/*
이 메서드가 반환한느 객체는 super.clone을 호출해 얻어야 한다. 이 클래스와 Object를 제외한 모든 상위 클래스가 이 관례를 따른다면 다음 식은 참이다.
*/  
x.clone().getClass == x.getClass()    
  
/*
반환한 객체와 원본 객체는 독립적이어야 한다. 이를 만족하려면 super.clone으로 얻은 객체의 필드 중 하나 이상을 반환 전에 수정해야 할 수도 있다.
*/
```

- 강제성이 없다는 점만 빼면 생성자 연쇄와 비슷한 메커니즘이다.

- clone 메서드가 super.clone이 아닌 생성자를 호출해 얻은 인스턴스를 반환해도 컴파일러는 불평하지 않을 것이다.

- 이 클래스의 하위클래스에서 super.clone을 호출한다면 잘못된 클래스의 객체가 만들어져 결국 하위 클래스의 clone 메서드가 제대로 동작하지 않게 된다.

- clone을 재정의한 클래스가 final 이라면 걱정해야 할 하위 클래스가 없으니 이 관례는 무시해도 안전하다.

- final 클래스의 clone  메서드가 super.clone을 호출하지 않는다면 Cloneable을 구현할 이유도 없다.

- Object의 clone 구현의 동작 방식에 기댈 필요가 없기 때문이다.

  

### 가변 객체를 참조하지 않는 클래스용 clone 메서드

```java
@Override public PhonNumber clone() {
  try {
    return (PhonNumber) super.clone();
  } catch (CloneNotSupportedException e) {
    throw new AssertionError();
  }
}
```

- 클래스에 정의된 모든 필드는 원본 필드와 똑같은 값을 갖는다.

- 모든 필드가 기본 타입이거나 불변 객체를 참조한다면 이 객체는 완벽한 상태이다.

- 쓸데없는 복사를 지양한다는 관점에서 보면 불변 클래스는 굳이 clone 메서드를 제공하지 않는 게 좋다.

  

### 가변 객체를 참조하는 클래스용 clone 메서드

```java
@Override public Stack clone() {
  try {
    Stack result = (Stack) super.clone();
    result.elements = elements.clone();
    return result;
  } catch (CloneNotSupportedException e) {
    throw new AssertionError();
  }
}
```

- 원본이나 복제본 중 하나를 수정하면 다른 하나도 수정되어 불변식을 헤치게 되어 프로그램이 이상하게 작동되거나 NullpointerException을 던질 것이다.

- clone 메서드는 사실상 생성자와 같은 효과를 낸다. 즉 clone은 원본 객체에 아무런 해를 끼치지 않는 동시에 복제된 객체의 불변식을 보장해야 한다.

- Cloneable 아키텍처는 '가변 객체를 참조하는 필드는 final로 선언하라'는 일반 용법과 충돌하기 때문에 일부 필드에서 final 한정자를 제거해야 할 수도 있다.

  

### 복잡한 가변 상태를 갖는 클래스용 재귀적 clone 메서드

1. 단순히 버킷 배열의 clone을 재귀적으로 호출

```java
publc class HashTable implements Cloneable {
  private Entry[] buckets = ...;
  
  private static class Entry {
    final Object key;
    Object value;
    Entry next;
    
    Entry(Object key, Object value, Entry next){
      this.key = key;
      this.value = value;
      this.next = next;
    }
    
    Entry deepCopy(){
      return new Entry(key, value, next == null ? null : next.deepCopy());
    }
  }
  
  @Overrid public HashTable clone() {
    try {
      HashTable result = (HashTable) super.clone();
      result.buckets = new Entry[buckets.length];
      for (int i = 0; i < buckets.length; i++)
        if (buckets[i] != null)
          result.buckets[i] = buckets[i].deepCopy();
      return result;
    } catch (CloneNotSupperortedException e) {
      throw new AssertionError();
    }
  }
  ...
}
```

  - 단순히 버킷 배열의 clone을 재귀적으로 호출활 경우 복제본은 자신만의 버킷 배열을 갖지만 원본과 같은 연결 리스트를 참조하여 원본과 복제본 모두 예기치 않게 동작할 가능성이 생긴다.

  - 이를 해결하려면 각 버킷을 구성하는 연결 리스트를 복사해야 한다.

  - private 클래스인 HashTable.Entry는 깊은 복사를 지원하도록 보장되었다.

  - HashTable의 clone 메서드는 먼저 적절한 크기의 새로운 버킷 배열을 할당한 다음 원래의 버킷 배열을 순회하며 비지 않은 각 버킷에 대해 깊은 복사를 수행한다.

  - 이때 Entry의 deepCopy 메서드는 자신이 가리키는 연결 리스트 전체를 복사기 위해 자신을 재귀적으로 호출한다.

  - 재귀 호출 때문에 리스트의 원소 수 만큼 스택 프레임을 소비하여 리스트가 길면 스택 오버플로를 일으킬 위험이 있다.

    


2. 반복자를 사용한 deepCopy 메서드

```java
Entry deepCopy(){
  Entry result = new Entry(key, value, next);
  for (Entry p = result; p.next != null; p = p.next)
    p.next = new Entry(p.next.key, p.next.value, p.next.next);
  return result;
}
```

  - 이를 해결하려면 deepCopy를 재귀 호출 대신 반복자를 써서 순회하는 방향으로 수정해야 한다.

    

3. 고수준 API 활용

- 고수준 API를 활용해 복제하면 간단한 코드를 얻게 되지만 저수준에서 처리할 때보다 느리다.

- Cloneable 아키텍처의 기초가 되는 필드 단위 객체 복사를  우회하기 때문에 전체 CLoneable 아키텍처와는 어울리지 않는 방식이다.

  

### 생성자에서 재정의될 수 있는 메서드

- 생성자에서는 재정의될 수 있는 메서드를 호출하지 않아야 한다.
- clone이 하위 클래스에서 재정의한 메서드를 호출하면 하위 클래스는 복제 과정에서 자신의 상태를 교정할 기회를 잃게 되어 원본과 복제본의 상태가 달라질 가능성이 크다.
- public인 clone 메서드에서는 throws 절을 없애야 한다. 검사 예외를 던지지 않아야 그 메서드를 사용하기 편하기 때문이다.



### 상속용 클래스

- 상속해서 쓰기 위한 클래스는 cloneable을 구현해서는 안된다.
- Object의 방식을 모방해 Cloneable 구현 여부를 하위 클래스에서 선탠할 수 있게 하는 방법이 있다.
- clone을 동작하지 않게 구현해놓고 하위 클래스에서 재정의하지 못하게 할 수도 있다.



### 스레드 안전 클래스

- Object의 clone 메서드는 동기화를 고려하지 않았기 때문에 재정의하고 동기화 해야 한다.



### 복사 생성자와 복사 팩터리

- 언어 모순적이고 위험한 객체 생성 메커니즘을 사용하지 않는다.
- 엉성하게 문서화된 규약에 기대지 않는다.
- 정상적인 final 필드 용법과 충돌하지 않는다.
- 불필요한 검사 예외를 던지지 않는다.
- 형변환이 필요하지 않다.
- 해당 클래스가 구현한 인터페이스 타입의 인스턴스를 인수로 받아 원본의 구현타입에 얽매이지 않고 복제본의 타입을 직접 선택할 수 있다.



### 핵심

- 새로운 인터페이스를 만들 때는 절대 Clonable을 확장해서는 안되며 새로운 클래스도 이를 구현해서는 안된다.
- final 클래스라면 성능 최적화 관점에서 검토한 후 드물게 허용해야 한다.
- 기본 원칙은 복제기능은 생성자와 팩터리를 이용하는것이 좋다.
- 배열은 clone 메서드 방식이 가장 깔끔하고 합당한 예외이다.

