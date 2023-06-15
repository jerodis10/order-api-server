# 29cm 상품 주문 프로그램

## 개발 환경
- Java 17
- Gradle 7.6.1

## 외부 라이브러리 및 오픈소스

- Lombok
  - 어노테이션 기반의 코드 자동 생성을 통한 생산성 향상

- AssertJ
  - 메소드 체이닝을 통한 좀 더 깔끔하고 읽기 쉬운 테스트 코드를 작성

- Fixture Monkey
  - 복잡한 객체를 자동으로 만들어 테스트를 편하게 만들어주는 라이브러리 


## 프로젝트 구조
- Application: 객체들을 생성하고 의존성을 주입하여 main 메서드를 통해 실행
- package
  - domain: 상품 주문 도메인 로직과 책임
  - service: 상품 주문 비즈니스 로직
  - repository: 상품 주문 데이터 저장소
  - event: 주문/종료 이벤트에 대한 발행 및 구독
  - exception: 로직에서 발생할 수 있는 예외
  - common: 데이터 입력 및 출력, 숫자 핸들링 등 유틸성 클래스


## 구현 방향
- 재고 차감에 따른 동시성 이슈
  - @Synchronized 를 활용해 multi-thread로 동시 접근 차단
    - sychronized 키워드는 객체 레벨에서 락이 걸려서 여러가지 동기화 관련 문제 발생 여지 존재. @Synchronized 는 가상의 필드레벨에서 좀더 안전하게 락을 걸어줌
  - FixedThreadPool 을 통한 여러 쓰레드에서 같은 상품의 재고 차감할 경우 테스트

- 재고수량 유효성 체크
  - 구매수량이 재고수량을 초과할 경우 재고가 차감되지 않고 SoldOutException 발생

- 추상화를 통한 책임 분리
  - 파일 입력을 통한 데이터 삽입이 아닌 다른 방식으로 바뀔 가능성을 고려한 repository 추상화
  - System.out.print 는 synchronized 로 동기화 처리가 되어있어, 오버헤드가 발생할 수 있어 log 로의 기능 변경 용이

- 적절한 의존성 관리
  - OrderEventPublisher 를 통해 런타임에 주문/종료 이벤트를 발생시키기 해당 로직 실행

- 상품 검색 효율성
  - 시간복잡도가 O(1)인 HashMap 을 통해 구현
