# 대규모 AI 시스템 설계 프로젝트
<br>    

## 👨‍👩‍👧‍👦 Our Team

| 김규준 | 안지연 | 이아린 |
| --- | --- | --- |
| [@mbc2579](https://github.com/mbc2579) | [@jiyeonahn](https://github.com/jiyeonahn) | [@eggnee](https://github.com/eggnee) |
| Leader / BE | BE | BE |
| `Order`, `Delivery`, `DeliveryPath` | `Company`,`Product`,`Hub`,`Slack` | `User`, `Shipper`,`HubManager`,`CompanyManager` |

<br>

## 🔧 Infra Architecture
![image (5)](https://github.com/user-attachments/assets/605c4f6c-4005-4359-a49c-ec1bd84409a7)


<br>

## 서비스 구성 및 실행방법

### ❗서비스 구성

- **MSA 기반 아키텍처**
    - 모든 주요 기능은 독립적인 마이크로서비스로 개발되어, 각각의 서비스가 독립적으로 배포, 확장, 유지보수될 수 있도록 설계하였습니다.
        ![ddd drawio (1)](https://github.com/user-attachments/assets/b627612d-8265-4ab9-a32d-fbe900c6383b)

        
- **서비스 간 통신**
    - **FeignClient**를 이용하여 서비스 간 통신을 합니다.
- **프로젝트 구조**
    - **Layered Architecture:** Controller, Service, Repository 계층으로 구성된 클린 아키텍처이며, DDD 적용을 위한 패키징 개념도 포함되었습니다.
- **데이터 베이스**
    - **테이블 명명 규칙:** 모든 테이블에 p_ 접두사 사용
    - **UUID 사용:** 모든 주요 엔티티의 식별자는 UUID를 사용 (유저는 예외)
    - **분산 데이터베이스:** 마이크로서비스별 독립된 데이터베이스 사용
    - **Audit 필드:** 모든 테이블에 created_at, created_by, updated_at, updated_by, deleted_at, deleted_by 필드를 추가하여 데이터 감사 로그 기록
- **보안**
    - **JWT 인증:** Spring Security와 JWT(Json Web Token)를 이용한 인증 및 권한 관리
    - 권한 확인 : 권한은 요청마다 저장되어 있는 권한 값과 동일한지 체크필요
    - **비밀번호 암호화:** BCrypt 해시 알고리즘을 사용한 비밀번호 암호화
    - **데이터 유효성 검사:** 서버 측 데이터 유효성 검사를 위해 Spring Validator 사용
    
<br> 

### ❗실행 방법

### 👤회원가입(Auth) `"/api/auth/sign-up"`

> 유저의 이름, 패스워드를 전달 받아 회원가입을 진행합니다.
> 
> 
> username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성
> 
> password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자`
> 
> 또한 다음의 권한으로 나누어 집니다.
> 
> - 마스터 관리자
> - 허브 관리자
> - 허브 배송담당자
> - 허브 업체
- 요청예시
    
    ```json
    {
      "username": "master",
      "password": "abc123!!",
      "slackId": "U07D9CF0A8E",
      "master": true,
      "masterToken": "MASTER_TOKEN_AI_B2B_THREE_III"
    }
    ```
<br>    

### ➡️로그인(Auth) `"/api/auth/sign-in"`

> 유저 아이디와 패스워드를 입력받아 로그인을 진행합니다.
로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급합니다.
발급한 토큰을 사용하여 사용자 정보를 조회 합니다.
> 
- 요청예시
    
    ```jsx
    {
      "username": "master",
      "password": "abc123!!"
    }
    ```

<br>    


### 🏢업체생성 (Company) `"/api/companies"`

> 업체를 추가 할 때는 관리 허브 ID가 존재하는 허브인지 확인합니다. 
업체 엔티티에 is_delete 필드를 추가하여 논리적 삭제를 관리합니다. 업체가 삭제될 경우 관련된 서비스에서 연관 데이터를 비활성화할 때 이 필드를 기준으로 처리합니다.
>
> 권한 관리는 아래와 같습니다.
> 
> - **마스터**: 모든 권한
> - **허브 관리자**: 자신의 허브에 소속된 업체만 관리 가능
> - **허브 업체**: 자신의 업체만 수정 가능, 다른 업체의 읽기와 검색만 가능
- 요청예시
    
    ```json
    {
      "hubId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "name": "업체이름",
      "type": "PRODUCTION_COMPANY",
      "address": "용산구"
    }
    ```
<br>    
    

### 🛒상품생성 (Product)`"/api/products"`

> 상품 엔티티에 is_delete 필드를 추가하여 논리적 삭제를 관리합니다. 상품이 삭제될 때 연관된 데이터(주문 등)도 is_delete 필드를 통해 관리합니다.
모든 조회 및 검색에서 is_delete가 false인 데이터만을 대상으로 처리하도록 합니다.
상품 생성시에 상품 업체가 존재하는지, 상품 관리 허브 ID가 존재하는지 확인합니다.
상품 설명은 `"/api/products/ai"` 를 통해 ai를 활용하여 생성이 가능합니다.
> 
- 요청예시
    
    ```json
    {
      "companyId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "hubId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "name": "상품이름",
      "description": "상품설명",
      "quantity": 100
    }
    ```
<br>    
    

### 🧾주문생성(Order) `"/api/orders"`

> 주문이 생성되면 배송도 같이 생성되어야 합니다.
주문이 생성되면 관련된 재고가 감소하며, 주문이 취소되면 해당 수량이 복원 됩니다.
주문 엔티티에 is_delete 필드를 추가하여 논리적 삭제를 관리합니다. 
주문이 취소되거나 삭제될 때 연관된 데이터도 is_delete 필드를 통해 관리합니다.
>
> 권한 관리는 아래와 같습니다.
> 
> - **생성**: 모든 로그인 사용자 (주문자) 가능
> - **수정**: 마스터 관리자와 해당 주문과   허브 관리자만 가능
> - **조회 및 검색**: 모든 로그인 사용자가 가능, 단, 주문자 본인은 자신의 주문만 조회 가능
- 요청예시
    
    ```jsx
    {
      "productionCompanyId": "생산업체Id",
      "receiptCompanyId": "수령업체 Id",
      "userName": "string",
      "deliveryAddress": "string",
      "recipientName": "string",
      "slackId": "string",
      "orderItemRequestDto": [
        {
          "productId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "productQuantity": 0
        }
      ],
      "status": "COMPLETED"
    }
    ```
<br>    
    

### 🚚배송생성(delivery)

> 주문이 생성될때 배송과 배송 경로 기록 데이터가 같이 생성되어야 합니다.
배송 및 배송 경로 기록 엔티티에 is_delete 필드를 추가하여 논리적 삭제를 관리합니다. 
배송과 관련된 모든 데이터에서 is_delete 필드를 기준으로 비활성화된 데이터를 관리합니다.
**배송과 배송 경로 기록 엔티티를 생성해주세요. 필수 정보는 다음과 같습니다.**
**배송** : 주문 ID, 현재상태(허브 대기중, 허브 이동중, 목적지 허브 도착, 배송중 등), 출발 허브 ID, 목적지 허브 ID, 배송지 주소, 수령인, 수령인 슬랙 ID(전화번호 대체)
**배송 경로 기록** : 배송 ID, 시퀀스(배송 경로 상 허브의 순번), 출발 허브 ID, 도착 허브 ID, 예상거리, 예상 소요시간, 실제 거리, 실제 소요 시간, 현재상태(허브 이동 대기중, 허브 이동중, 목적지 허브 도착, 배송중 등)
>
> 권한 관리는 아래와 같습니다.
> 
> - **생성**: 주문 생성 시 자동으로 생성
> - **수정**: 마스터 관리자, 해당 허브 관리자, 그리고 해당 배송 담당자만 가능
> - **조회 및 검색**: 모든 로그인 사용자가 가능, 단 배송 담당자는 자신이 담당하는 배송만 조회 가능

<br>    

## **프로젝트 목적 및 상세**

이번 프로젝트의 주된 목표는 **MSA(Microservices Architecture)** 기반의 물류 관리 및 배송 시스템을 개발하는 것입니다. 이를 통해 실무에서의 협업과 데이터 연동, API 관리 및 서비스 간 통신의 신뢰성을 확보하는 방법을 경험하며, 복잡한 MSA 시스템을 효과적으로 설계하고 구현하는 능력을 기르는 것이 목표입니다.

### **프로젝트 개요**

- **주제**: MSA 기반 국내 물류 관리 및 배송 시스템 개발 (스파르타 물류)
- **형식**: B2B (Business to Business) 물류 관리 시스템

### **시스템 구성**

- **허브 센터**: 지역별 허브 센터를 운영하여 각 지역의 주문 및 재고 관리를 담당합니다.
- **업체 역할**: 가공업체와 바구니 제작업체 간의 물품 주문과 배송 요청을 관리합니다.

### **프로세스 설명**

1. **주문 발생**:
    - 예를 들어, 부산시의 바구니 제작 업체가 경기도 일산의 플라스틱 가공업체에 1000개의 플라스틱 가공품을 주문합니다.
    - 플라스틱 가공업체는 주문에 따라 스파르타 물류에 허브 저장 가공품의 배송을 요청합니다.
2. **물류 처리 및 재고 이동**:
    - 경기도 허브에서 부산시 허브로 물품을 이동시키는 과정이 필요합니다. 시스템은 이 이동 경로를 계획하고, 물품이 안전하게 목적지 허브로 전달되도록 합니다.
3. **최종 수령**:
    - 부산시 허브에 도착한 물품은 허브 소속 배송 담당자가 바구니 제작업체로 전달합니다.

### **기술적 과제**

- **API 연동**: 서비스 간 API 연동을 통해 데이터와 기능을 효율적으로 연동합니다.
- **데이터 무결성 유지**: 데이터의 정확성과 일관성을 확보하며, 시스템 간의 데이터 연동을 안정적으로 관리합니다.
- **서비스 간 통신 신뢰성**: 서비스 간의 통신을 신뢰성 있게 유지하기 위한 방법을 모색합니다.
- **AI 활용**: Gemini API를 통해 AI 기술을 간접적으로 활용하여 질문과 답변 처리 기능을 구현합니다.

### **프로젝트 목표**

- MSA의 복잡성을 이해하고, 팀원들과 협력하여 실제 사례를 통해 MSA 시스템을 구축합니다.
- 실무에서 발생할 수 있는 다양한 문제를 경험하고 해결하며, 향후 시스템 설계 및 개발에 대한 인사이트를 얻습니다.


<br>    

## 📋ERD

![image (3)](https://github.com/user-attachments/assets/15aa22e9-7b3e-4882-a5dd-8d18ae43cb7a)

<br>

## **⚙** 기술 스택
- **백엔드:** Spring Boot 3.3
- **데이터베이스:** PostgreSQL
- **빌드 툴:**  Gradle
- **API 문서화:** Swagger
- **API 게이트웨이:** Spring Cloud Gateway - 모든 외부 요청을 각 마이크로서비스로 라우팅
- **서비스 디스커버리:** Spring Cloud Eureka - 각 마이크로서비스의 위치를 자동으로 발견하고 관리
- **버전 관리:** Git, GitHub
- **그 외 기술:** `java 17`, `springsecurity`, `jwt`, `Redis`, `docker`, `intellijidea`, `notion`, `slack` 

<br>

## **🚨** 트러블 슈팅

<details>
<summary><b> RestTemplate에서 Http Interface로의 전환을 통한 API 통신 개선 </b></summary>

1. **문제 상황**: 프로젝트에서 외부 API와 통신을 하기 위해 RestTemplate을 사용하였으나, 복잡한 설정과 요청 응답 시 코드의 가독성이 떨어지는 문제를 발견하였고 유지보수에 어려움을 느꼈다.<br>

2. **해결 방법**: RestTemplate 대신 Spring 6에서 제공하는 Http Interface를 사용해보았다. Http Interface는 인터페이스 기반으로 API 호출을 할 수 있도록 지원하여, 코드를 단순화하고 가독성을 높였다. @GetExchange, @PostExchange와 같은 어노테이션을 사용하여 HTTP 요청을 명시적으로 처리할 수 있었다.<br>

3. **장점**: 인터페이스 기반으로 API 호출 구조가 깔끔해졌고 가독성이 높아지고 유지보수성이 개선되었다. 더 효율적으로 API와 상호 작용이 가능해졌다.
<br>
</details>

    
<details>
<summary><b> FeignClient 연동 문제 해결 </b></summary>

1. **문제 상황**: 프로젝트에서 주문 생성을 진행할 때 FeignClient를 사용하여 company 애플리케이션과 연동 후 companyId 값을 제대로 불러오지 못해 업체 조회 여부가 되지 않아 어려움을 느꼈던 적이 있다.<br>

2. **해결 방법**: company 애플리케이션의 데이터 구조를 파악하여 데이터의 값을 동일하게 매핑 후 진행하여 문제를 해결하였다.
<br>
</details>
