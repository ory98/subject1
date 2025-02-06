# 과제1. 객체지향

## 은행 계좌 관리 시스템
### 내용

- 계좌(일반,적금)를 관리하며, 각 계좌에서는 기본적으로 입금, 잔액 조회, 계좌 번호를 조회할 수 있습니다.
- 일반 계좌는 출금 가능하고, 적금 계좌는 출금 불가능합니다.
- 일반 계좌와 적금 계좌의 각 특성에 맞는 동작을 구현합니다.

### 테스트 방법 
- initDb로 데이터 미리 넣어둠 
- test.http를 사용하여 테스트 가능

### 구조
- controller
- service
- repository
- domain
- dto
- exception

### api 

- 입금 API
  - 경로 : /deposit
  - PUT 방식
  - 파라미터 : 
    - userName(사용자 이름)
    - memo(입금 메모)
    - accountNumber(계좌번호)
    - balance(금액)


- 출금 API 
  - 경로 : /withdrawal
  - PUT 방식
  - 파라미터 :
    - userName(사용자 이름)
    - memo(출금 메모)
    - accountNumber(계좌번호)
    - balance(금액)


- 계좌 상세 조회 API
  - 경로 : /user/account
  - GET 방식
  - 파라미터 :
    - userName(사용자 이름)
    - accountNumber(계좌번호)


- 사용자 계좌 리스트 조회 API
  - 경로 : /user/accounts
  - POST 방식
  - 파라미터 :
    - userName(사용자 이름)

