## 09. try-finally 보다는 try-with-resources를 사용하라

### try-finally

```java
static String firstLineOfFile(String src, String dst) throws IOExeption {
  InputStream in = new FileInputStream(src);
  try{
    OutputStream out = new FileOutputStream(dst);
    try{
      byer[] buf = ne byte[BUFFER_SIZE];
      int n;
      while ((n = un,read(buf)) >= 0)
        out.write(buf, o, n);
    }finally {
    	out.close();
    }
  }finally{
    in.close();
  }
}
```

- 예외는 try 블록과 finally 블록 모두에서 발생 할수 있는데 기기에 물리적인 문제가 생긴다면 firstLinOfFIle 메서드 안의 readLine 메서드가 예외를 던지고, 같은 이유로 close 메서드도 실패할 것이다.
- 이런 상황이면 두 번째 예외가 첫 번쨰 예외를 완전히 집어 삼킨다.
- 스택 추적 내역에 첫 번째 예외에 관한 정보는 남지 않게 되어, 실제 시스템에서의 디버깅을 몹시 어렵게 한다.
- 두 번째 예외 대신 첫 번째 예외를 기록하도록 코드를 수정할 수는 있지만, 코드가 너무 지저분해져서 실제로 그렇게까지 하는 경우는 거의 없다.

### try-finally-resources

1. AutoCloseable

   - try-finally-resources 구조를 사용하려면 해당 자원이 AutoCloseable 인터페이스를 구현해야 한다.
   - 단순히 void를 반환하는 close 메서드 하나만 정의한 인터페이스다.

2. try-finally을 바꾼 예제

   ```java
   static String firstLineOfFile(String src, String dst) throws IOExeption {
     try(InputStream in = new FileInputStream(src);
        		OutputStream out = new FileOutputStream(dst)){
   			byer[] buf = ne byte[BUFFER_SIZE];
         int n;
         while ((n = un,read(buf)) >= 0)
           out.write(buf, o, n);
     }
   }
   ```

   - 코드가 짧고 읽기 수월하며 문제를 진단하기도 좋다.
   - readLine과 close 호출 양쪽에서 예외가 발생하면 close에서 발생한 예외는 숨겨지고 readLine에서 발생한 예외가 기록된다.
   - 숨겨진 예외들은 그냥 버려지지 않고 스택 추적 내역에 suppressed 꼬리푤르 달고 출력된다.

3. catch 절과 함께 쓰는 예제

   ```java
   static String firstLineOfFile(String path, String defaultVal) {
     try (BufferedReader br = new BufferedReader(new FileReader(path))) {
       return br.readLine();
     } catch (IOExceotion e) {
       return defaultVal;
     }
   }
   ```

   - catch 절 덕분에 try 문을 중첩하지 않고도 다수의 예외를 처리할 수 있다.
   - 꼭 회수해야 하는 자원을 다룰 때는 반드시 사용해야 한다.
   - 정확하고 쉽게 자원을 회수할 수 있으며 코드는 더 짧고 분명해지며 만들어지는 예외 정보도 훨씬 유용하다.