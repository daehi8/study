## 58. 전통적인 for 문보다는 for-each 문을 사용하라 

### 전통적인 for문 사용을 지양하라

- for문으로 컬렉션을 순회하는 데 필요한 반복자와 인덱스 변수는 코드를 지저분하게 만든다
- 더 많은 변수를 생성 및 사용하게끔 함으로써 오류가 생길 가능성이 높아진다
- 잘못된 변수를 사용했을 때 컴파일러가 잡아주리라는 보장이 없다
- 다루는 컨테이너의 종류(컬렉션 또는 배열)에 따라 코드 형태가 달라진다

 

### for-each문 사용을 지향하라

- 반복자와 인덱스를 사용하지 않으므로 코드가 깔끔해지고 오류가 날 일도 적다
- 하나의 관용구로 컬렉션과 배열을 모두 처리할 수 있어서 다루는 컨테이너의 종류를 고려하지 않아도 된다
- 컬렉션과 배열뿐만 아니라 Iterable 인터페이스를 구현한 객체는 무엇이든지 순회할 수 있다
- 속도적인 측면에서 전통적인 for문과 차이가 없다



### for-each문을 사용할 수 없는 경우

1. 파괴적인 필터링(destructive filtering)

- 컬렉션을 순회하면서 선택된 원소를 제거해야 한다면 반복자의 remove를 호출해야 한다

```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

Iterator<String> i = list.iterator();
while (i.hasNext()) {
    if ("A".equals(i.next())) {
        i.remove();
    }
}

// 출력결과: ["B", "C"]
```



- 자바 8부터는 Collction의 removeIf 메서드를 사용해 컬렉션을 명시적으로 순회하는 일을 피할 수 있다

```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

list.removeIf("A"::equals);

// 출력결과: ["B", "C"]
```

 

2. 변형(transforming)

- 리스트나 배열을 순회하면서 그 원소의 값 일부 혹은 전체를 교체해야 한다면 리스트의 반복자나 배열의 인덱스를 사용해야 한다

```java
int[] arr = new int[]{1, 2, 3, 4, 5};

for (int i = 0; i < 5; i++) {
    arr[i] = arr[i]+1;
}

// 출력결과: [2, 3, 4, 5, 6]
```

 

3. 병렬 반복(parallel iteration)

- 여러 컬렉션을 병렬로 순회해야 한다면 각각의 반복자와 인덱스 변수를 사용해 엄격하고 명시적으로 제어해야 한다



### 핵심

- 가능한 한 모든 곳에서 전통적인 for문보다는 for-each문을 사용하라
- for-each문은 전통적인 for문보다 명료하고, 유연하고, 버그를 예방해주며 성능 저하도 없다