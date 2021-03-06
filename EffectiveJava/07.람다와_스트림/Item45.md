## 45. 스트림은 주의해서 사용하라

### 스트림 API

- 다량의 데이터 처리 작업을 돕고자 추가
- 스트림은 데이터 원소의 유한 혹은 무한 시퀸스를 의미
- 스트림 파이프라인은 이 원소들로 수행하는 연산 단계를 표현하는 개념



### 스트림 파이프라인

- 소스 스트림에서 시작해 종단 연산으로 끝나며 그사이에 하나 이상의 중간 연산이 있을 수 있다.
- 각 중간 연산은 스트림을 어떠한 방식으로 변환한다.
- 예컨데 각 원소에 함수를 적용하거나 특정 조건을 만족 못하는 원소를 걸러낼 수 있다.
- 중간 연산들은 모두 한 스트림을 다른 스트림으로 변환하는데 변환된 스트림의 원소 타입은 변환 전 스트림의 원소 타입과 같을 수도 있고 다를 수도 있다.
- 종단 연산은 마지막 중간 연산이 내놓은 스트림에 최후의 연산을 가한다.



### 스트림 파이프라인 지연 평가

- 지연 평가되며 평가는 종단 연산이 호출될 때 이루어지고 종단 연산에 쓰이지 않는 데이터 원소는 계산에 쓰이지 않는다.
- 무한 스트림을 다룰 수 있다.
- 종단 연산이 없는 스트림 파이프라인은 아무 일도 하지 않는 명령어와 같다.



### 플루언트 API

- 스트림 API는 메서드 연쇄를 지원하는 플루언트 API다.
- 파이프라인 하나를 구성하는 모든 호출을 연결하여 단 하나의 표현식으로 완성할 수 있다.
- 파이프라인 여러개를 연결해 표현식 하나로 만들 수도 있다.



### 순차적 수행

- 기본적으로 스트림 파이프라인은 순차적으로 수행된다.
- 파이프 라인을 병렬로 실행할 수는 있으나 효과를 볼 수 있는 상황은 많지 않다.



### 스트림을 사용할 때 주의해야 할 점

- 스트림을 과용하면 프로그램이 읽거나 유지보수하기 어려워진다.

- char 값들을 처리할 때는 스트림을 삼가는 편이 낫다.

- 기존 코드는 스트림을 사용하도록 리팩터링하되 새 코드가 더 나아 보일 때만 반영하는 것이 좋다.

  

### 코드 블록으로만 할수 있는 일

- 스트림 파이프라인은 되풀이되는 계산을 함수 객체로 표현하지만 반복 코드에서는 코드 블록을 사용해 표현한다.
- 코드 블록에서는 범위 안의 지역변수를 읽고 수정할 수 있지만 람다에서는 final이거나 사실상 final인 변수만 읽을 수 있고 지역 변수를 수정하는 건 불가능하다.
- 코드 블록에서는 메서드에서 빠져나가거나 블록 바깥의 반복문을 종료하거나 건너 뛸 수 있지만 람다로는 할 수 없다.



### 스트림을 사용하기 좋은 상황

- 원소들의 시퀸스를 일관되게 변환한다.
- 원소들의 시퀸스를 필터링한다.
- 원소들의 시퀸스를 하나의 ㅇ녀산을 사용해 결합한다.
- 원소들의 시퀸스를 컬렉션에 모은다.
- 원소들의 시퀸스에서 특정 조건을 만족하는 원소를 찾는다.



### 스트림을 사용하기 어려운 상황

- 한 데이터가 파이프라인의 여러 단계를 통과할 때 이 데이터의 각 단계에서의 값들에 동시에 접근하기 어려운 경우
- 스트림 파이프라인은 일단 한 값을 다른 값에 매핑하고 나면 원래의 값은 잃는 구조이기 때문이다.



### 핵심

- 스트림과 반복 방식 중에 더 알맞는 일이 있을 것이고 수많은 작업들은 이 둘을 조합했을 때 가장 좋게 해결된다.
- 스트림과 반복 중 어느 쪽이 나은지 확신하기 어렵다면 둘 다 해보고 더 나은 쪽을 택하는 것이 좋다.