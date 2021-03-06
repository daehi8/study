## 37. ordinal 인덱싱 대신 EnumMap을 사용하라

### ordinal 값을 배열의 인덱스로 사용

```java
/**
 * 식물을 나타내는 클래스
 */
public class Plant {
    // 식물의 생애 주기를 관리하는 열거 타입
    enum LifeCycle {
        ANNUAL, // 한해살이
        PERENNIAL, // 여러해살이
        BIENNIAL // 두해살이
    }

    final String name;
    final LifeCycle lifeCycle;

    Plant(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Client {
    public void addPlant(List<Plant> garden) {
        // 생애주기 3가지로 만들어지는 Set
        Set<Plant>[] plantsByLifeCycle = new Set[Plant.LifeCycle.values().length];

        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            plantsByLifeCycle[i] = new HashSet<>();
        }

        for (Plant p : garden) {
            plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
        }

        // 인덱스의 의미를 알 수 없어 직접 레이블을 달아 데이터 확인 작업 필요
        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
        }
    }
}
```

- 배열은 제네릭과 호환되지 않으니 비검사 형변환을 수행해야 하고 깔끔히 컴파일되지 않을 것이다.
- 배열은 각 인덱스의 의미를 모르니 출력 결과에 직접 레이블을 달아야 한다.
- 정확한 정숫값을 사용한다는 것을 직접 보증해야한다.
- 정수는 열거 타입과 달리 타입 안전하지 않기 때문이다.



### EnumMap

```java
class Client {
    public void addPlantTypeEnumMap(List<Plant> garden) {
        Map<Plant.LifeCycle, Set<Plant>> plantByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);

        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
            plantByLifeCycle.put(lc, new HashSet<>());
        }

        for (Plant p : garden) {
            plantByLifeCycle.get(p.lifeCycle).add(p);
        }

        System.out.println(plantByLifeCycle);
    }
}
```

- 열거 타입을 키로 사용하도록 설계한 아주 빠른 Map 구현체
- 안전하지 않은 형변환을 쓰지 않는다.
- 맵의 키인 열거 타입이 그 자체로 출력용 문자열을 제공한다.
- 배열 인덱스를 계산하는 과정에서 오류가 날 가능성이 없다.
- 내부 구현 방식으로 배열을 안으로 숨겨서 Map의 타입 안전성과 배열의 성능을 모두 얻었다.
- EnumMap의 생성자가 받는 키 타입의 Class 객체는 한정적 타입 토큰으로 런타임 제네릭 타입 정보를 제공한다.



### 핵심

- 배열의 인덱스를 얻기 위해 ordinal을 쓰는 것은 일반적으로 좋지 않으니 대신 EnumMap을 사용해야 한다.
- 다차원 관계는 EnumMap<..., EnumMap<...>>으로 표현해야 하며 애플리케이션 프로그래머는 Enum,ordinal을 사용하지 말아야 한다는 일반 원칙의 특수한 사례다.