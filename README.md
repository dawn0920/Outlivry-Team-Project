# Outlivry-Team-Project
# 1. Project Overview (프로젝트 개요)

- 프로젝트 이름: Outlivry-Team-Project
- 프로젝트 설명: Spring 프레임워크 와 JPA 활용, Token을 이용하여 배달 웹 만들어보기

- 팀 9조

|   역할   |                       팀장                       |                      팀원                       |                     팀원                     |                    팀원                     |
|:------:|:----------------------------------------------:|:---------------------------------------------:|:------------------------------------------:|:-----------------------------------------:|
|   이름   |                      김채원                       |                      전현진                      |                    박주형                     |                    백가희                    |
| 담당 기능  |              리뷰, 주문, 장바구니 <br> 구현              |            가게 구현, <br> 시연영상 제작, 발표            |             메뉴, 이미지 저장 <br> 구현             |         회원가입, 유저, <br> 소셜 로그인 구현          
| GitHub | [chaewon9999](https://github.com/chaewon9999)  | [kickthemoon](https://github.com/kickthemoon) |  [janghwal](https://github.com/janghwal)   |  [dawn0920](https://github.com/dawn0920)  |
| 기술블로그  | [채원velog](https://velog.io/@w0729/posts) |  [현진velog](https://velog.io/@soonch6/posts)   | [주형tistory](https://janghwal.tistory.com/) | [가희tistory](https://dawns2.tistory.com/) |

## API 명세서

[API 명세서](https://www.notion.so/teamsparta/14-1d62dc3ef514806a81c4e975dd72a873?p=1dd2dc3ef514809fb933c1a2d0bb024c&pm=s)

## User API
| Method | Endpoint                | Description          | Parameters                          | Request Body                                                                 | Response                                                                                         | Status Code   |
|--------|-------------------------|----------------------|-------------------------------------|------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|--------------|
| POST   | `/auth/user/signup`     | 유저 회원가입        | 없음                                | `{ "email": string, "password": string, "name": string, "phone": string, "birth": string, "userRole": UserRole }` | `{ "message": "유저 회원 가입 완료", "contents": {} }`                                           | 201 Created  |
| POST   | `/auth/owner/signup`    | 사장님 회원가입      | 없음                                | `{ "email": string, "password": string, "name": string, "phone": string, "birth": string, "userRole": UserRole }` | `{ "message": "사장님 회원가입 완료", "contents": {} }`                                         | 201 Created  |
| POST   | `/auth/login`           | 로그인               | 없음                                | `{ "email": string, "password": string }`                                    | `{ "Authorization": "Bearer {token}" }`                                                         | 200 OK       |
| POST   | `/auth/naver/callback`  | 네이버 소셜 로그인   | **Query:** `code` (String)          | 없음                                                                         | `{ "Authorization": "Bearer {token}" }`                                                         | 200 OK       |
| GET    | `/users`                | 유저 조회            | **Header:** `Authorization: Bearer {token}` | 없음                                                                         | `{ "message": "유저 정보 조회 완료", "contents": { "email": string, ... } }`                     | 200 OK       |
| PUT    | `/users`                | 유저 정보 수정       | **Header:** `Authorization: Bearer {token}` | `{ "nickname": string, "name": string, "phone": string, "address": string }` | `{ "message": "유저 정보 수정 완료", "contents": {} }`                                           | 200 OK       |
| PUT    | `/users/changePW`       | 패스워드 변경        | **Header:** `Authorization: Bearer {token}` | `{ "oldPassword": string, "newPassword": string }`                          | `{ "message": "패스워드 변경 완료", "contents": {} }`                                            | 200 OK       |
| DELETE | `/users`                | 회원 탈퇴            | **Header:** `Authorization: Bearer {token}` | `{ "password": string }`                                                    | `{ "message": "회원 탈퇴 완료", "contents": {} }`                                                | 200 OK       |
## Store API
| Method | Endpoint                     | Description       | Parameters                                                                 | Request Body                                                                                                                                                                                                 | Response                                                                                                                                                                                                                                                                                                                                 | Status Code   |
|--------|------------------------------|-------------------|----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| POST   | `/owners/stores`             | 가게 등록하기     | **Header:** `Authorization: Bearer {token}`                               | `{ "storeName": string, "storePicture": MultipartFile, "phone": string, "address": string, "content": string, "category": string, "minDeliveryPrice": long, "deliveryTip": long, "openTime": LocalTime, "closeTime": LocalTime }` | `{ "message": "가게 생성 성공", "contents": { "storeId": long, "userId": long, "storeName": string, "storePictureUrl": string, "phone": string, "address": string, "content": string, "category": string, "minDeliveryPrice": long, "deliveryTip": long, "openTime": LocalTime, "closeTime": LocalTime, "createdDate": LocalDateTime, "modifiedDate": LocalDateTime, "isDeleted": boolean } }` | 201 Created  |
| GET    | `/stores`                    | 가게 리스트 조회  | **Query:**<br>`page`: int<br>`size`: int<br>`storeName`: string (optional) | 없음                                                                                                                                                                                                         | `{ "message": "가게 조회 성공", "contents": { "stores": [ { store object } ], "pageInfo": { "page": int, "size": int, "totalPages": int, "totalElements": int, "isLast": boolean } } }`                                                                                                                                                  | 200 OK       |
| GET    | `/stores/{storeId}`          | 가게 단건 조회    | **Path:** `storeId`: long                                                 | 없음                                                                                                                                                                                                         | `{ "message": "가게 조회 성공", "contents": { "stores": [ { store object } ], "pageInfo": { "page": int, "size": int, "totalPages": int, "totalElements": int, "isLast": boolean } } }`                                                                                                                                                  | 200 OK       |
| PATCH  | `/owners/stores/{storeId}`   | 가게 수정하기     | **Header:** `Authorization: Bearer {token}`<br>**Path:** `storeId`: long  | `{ "newStoreName": string, "newStorePicture": MultipartFile, "newPhone": string, "newAddress": string, "newContent": string, "newCategory": string, "newMinDeliveryPrice": long, "newDeliveryTip": long, "newOpenTime": LocalTime, "newCloseTime": LocalTime }` | `{ "message": "가게 수정 성공", "contents": { store object } }`                                                                                                                                                                                                                                                                          | 200 OK       |
| DELETE | `/owners/stores/{storeId}`   | 가게 삭제하기     | **Header:** `Authorization: Bearer {token}`<br>**Path:** `storeId`: long  | 없음                                                                                                                                                                                                         | `{ "message": "가게 삭제 성공", "contents": {} }`                                                                                                                                                                                                                                                                                        | 200 OK       |
## Order API
| Method | Endpoint                          | Description       | Parameters                                      | Request Body                     | Response                                                                                                                                                                                                 | Status Code   |
|--------|-----------------------------------|-------------------|------------------------------------------------|----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| POST   | `/orders/stores/{storeId}`        | 주문하기         | **Header:** `Authorization: Bearer {token}`<br>**Path:** `storeId`: long | `{ "request": string }`          | `{ "message": "주문 완료", "contents": { "storeId": long, "orderId": long, "totalPrice": int, "orderItems": List<OrderItem>, "isReceived": boolean, "isDelivery": boolean, "creatTime": LocalDateTime } }` | 201 Created  |
| GET    | `/orders/{orderId}`               | 주문 조회        | **Header:** `Authorization: Bearer {token}`<br>**Path:** `orderId`: long | 없음                             | `{ "message": "주문 조회", "contents": { same as POST response } }`                                                                                                                                      | 200 OK       |
| PATCH  | `/orders/{orderId}/received`      | 주문 수락        | **Header:** `Authorization: Bearer {token}`<br>**Path:** `orderId`: long | 없음                             | `{ "message": "주문 수락 완료", "content": {} }`                                                                                                                                                         | 200 OK       |
| PATCH  | `/orders/{orderId}/delivery`      | 배달 여부        | **Header:** `Authorization: Bearer {token}`<br>**Path:** `orderId`: long | 없음                             | `{ "message": "배달 완료", "content": {} }`                                                                                                                                                              | 200 OK       |
| DELETE | `/orders/{orderId}`               | 주문 취소        | **Header:** `Authorization: Bearer {token}`<br>**Path:** `orderId`: long | 없음                             | `{ "message": "주문 취소", "content": {} }`                                                                                                                                                              | 204 No Content |
## Cart API
| Method | Endpoint                | Description            | Parameters                                      | Request Body | Response                                                                                                                                                     | Status Code   |
|--------|-------------------------|------------------------|------------------------------------------------|--------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| POST   | `/carts/menus/{menuId}` | 장바구니 생성         | **Header:** `Authorization: Bearer {token}`<br>**Path:** `menuId`: long | 없음         | `{ "message": "장바구니에 추가했습니다.", "contents": { "cartId": long, "menuName": string, "price": int, "quantity": int } }`                                | 201 Created  |
| GET    | `/carts`                | 장바구니 조회         | **Header:** `Authorization: Bearer {token}`    | 없음         | `{ "message": "조회 성공", "contents": { "cartId": long, "menuId": long, "menuName": string, "price": int, "quantity": int } }`                              | 200 OK       |
| DELETE | `/carts/{cartId}`       | 특정 물건 삭제        | **Header:** `Authorization: Bearer {token}`<br>**Path:** `cartId`: long | 없음         | `{ "message": "삭제 완료", "content": {} }`                                                                                                                  | 200 OK       |
| DELETE | `/carts`                | 전체 삭제             | **Header:** `Authorization: Bearer {token}`    | 없음         | `{ "message": "장바구니를 비웠습니다.", "content": {} }`                                                                                                      | 200 OK       |
## Review API
| Method | Endpoint                                      | Description            | Parameters                                                                                     | Request Body                     | Response                                                                                                                                                                                                 | Status Code   |
|--------|-----------------------------------------------|------------------------|------------------------------------------------------------------------------------------------|----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| POST   | `/reviews/stores/{storeId}/orders/{orderId}`  | 리뷰 작성             | **Header:** `Authorization: Bearer {token}`<br>**Path:** `storeId`: long, `orderId`: long      | `{ "contents": string, "stars": int }` | `{ "message": "리뷰 작성", "contents": { "reviewId": long, "userNickname": string, "contents": string, "stars": int, "createTime": LocalDateTime } }`                                                    | 201 Created  |
| GET    | `/reviews/stores/{storeId}`                   | 전체 리뷰 조회        | **Header:** `Authorization: Bearer {token}`<br>**Path:** `storeId`: long<br>**Query:** `page`: int | 없음                             | `{ "message": "리뷰 조회", "contents": { "reviewId": long, "userNickname": string, "contents": string, "stars": int, "createTime": LocalDateTime, "modifiedTime": LocalDateTime } }`                     | 200 OK       |
| GET    | `/reviews/stores/{storeId}/stars`             | 별점으로 조회         | **Header:** `Authorization: Bearer {token}`<br>**Path:** `storeId`: long<br>**Query:** `page`: int, `start`: int, `end`: int | 없음                             | `{ "message": "별점으로 리뷰 조회", "contents": { same as 전체 리뷰 조회 response } }`                                                                                                                   | 200 OK       |
| PATCH  | `/reviews/{reviewId}`                         | 리뷰 수정             | **Header:** `Authorization: Bearer {token}`<br>**Path:** `reviewId`: long<br>**Query:** `page`: int | `{ "contents": string, "stars": int }` | `{ "message": "리뷰 수정", "contents": { "reviewId": long, "userNickname": string, "contents": string, "stars": int, "createTime": LocalDateTime, "modifiedTime": LocalDateTime } }`                     | 200 OK       |
| DELETE | `/reviews/{reviewId}`                         | 리뷰 삭제             | **Header:** `Authorization: Bearer {token}`<br>**Path:** `reviewId`: long                      | 없음                             | `{ "message": "리뷰 삭제", "contents": {} }`                                                                                                                                                            | 200 OK       |





## ERD

![ERD](https://github.com/user-attachments/assets/ada30bc3-48e8-4ebb-8dbc-de66a397b4c9)

# 2. Key Features (주요 기능)

<h3>사용자</h3>

- **사용자 등록**
    - 사용자는 사장님, 혹은 유저로 역할에 맞게 회원가입을 진행 가능


- **사용자 로그인**
    - 사용자는 등록된 이메일과 비밀번호를 통해 로그인 가능
    - 네이버 소셜 로그인 기능을 활용해 별도의 회원가입 없이 로그인 진행 가능


- **사용자 정보 조회**
    - 사용자는 발급받은 토큰을 사용해 개인정보 조회 가능


- **사용자 정보 수정**
    - 사용자는 발급받은 토큰을 사용해 개인정보 수정 가능


- **사용자 비밀번호 변경**
  - 사용자는 발급받은 토큰을 사용해 비밀번호 수정 가능


- **사용자 삭제**
    - 사용자는 자신의 계정을 삭제할 수 있으며, 토큰과 비밀번호를 입력하여 삭제 가능

<h3>가게</h3>

- **가게 생성**
    - 사장님으로 로그인된 유저에 한해 자신의 가게를 3개까지 생성 가능


- **가게 이름으로 조회**
    - 모든 사용자는 가게의 이름을 통해 가게 조회 가능


- **가게 아이디로 조회**
    - 모든 사용자는 가게의 아이디를 통해 가게 조회 가능


- **가게 수정**
    - 로그인한 사용자가 해당 가게의 사장이라면 가게 정보 수정 가능


- **가게 삭제**
    - 로그인 한 사용자가 해당 가게의 사장아라면 가게 삭제 가능

<h3>메뉴</h3>

- **메뉴 생성**
    - 로그인 한 사용자가 해당 가게의 사장님이라면 메뉴 등록 가능


- **메뉴 단건 조회**
    - 모든 사용자는 메뉴 아이디를 통해 메뉴 단건 조회 가능


- **메뉴 전체 조회**
    - 모든 사용자는 가게 아이디를 통해 해당 가게의 전체 메뉴 조회 가능


- **메뉴 수정**
    - 로그인 한 사용자가 해당 가게의 사장이라면 메뉴 수정 가능


- **메뉴 삭제**
    - 로그인 한 사용자가 해당 가게의 사장이라면 메뉴 삭제 가능


<h3>주문</h3>

- **주문 생성**
    - 로그인 한 사용자의 장바구니가 존재할 때 해당 가게로 주문 가능


- **주문 조회**
    - 로그인한 사용자가 해당 주문을 했거나 주문받은 가게에 사장님이라면,<br> 주문 아이디를 통해 주문 조회 가능


- **주문 수락**
    - 주문한 가게의 사장님이라면 주문 수락 가능


- **배달 완료**
    - 주문한 가게의 사장님이라면 배달 완료 가


- **주문 취소**
    - 로그인한 사용자가 주문을 한게 맞다면 주문 취소 가능 

<h3>장바구니</h3>

- **장바구니 생성**
    - 모든 사용자는 자신의 장바구니 생성 가능
    - 단, 장바구니에는 같은 가게의 메뉴만 담을 수 있음


- **장바구니 조회**
    - 장바구니에 들어있는 메뉴를 리스트 형태로 조회 가능


- **장바구니 특정 메뉴 삭제**
    - 장바구니 내 특정 물건을 삭제 가능


- **장바구니 전체 삭제**
    - 이용자의 장바구니 내 전체 메뉴 삭제

<h3>리뷰</h3>

- **리뷰 생성**
    - 로그인 한 사용자는 자신의 주문이 완료되었다면 가게에 리뷰 생성 가능


- **전체 리뷰 조회**
    - 모든 사용자는 특정 가게의 리뷰를 조회 가능 


- **별점으로 조회**
    - 모든 사용자는 특정 가게의 리뷰를 별점을 기준으로 조회 가능


- **리뷰 수정**
    - 로그인 한 사용자와 리뷰를 작성한 사용자가 같다면 리뷰 수정 가능


- **리뷰 삭제**
    - 로그인 한 사용자와 리뷰를 작성한 사용자가 같다면 리뷰 삭제 가능


# 3. Technology Stack (기술 스택)

## Language

<img src="https://img.shields.io/badge/java-007396?style=flat-square&logo=java&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/> 

## Version Control

<img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/> <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/>

## JDK Version

Java 17 (OpenJDK 17)


<br/>

# 4. Project Structure (프로젝트 구조)

```
── build
│   ├── classes
│   │   └── java
│   │       └── main
│   │           └── org
│   │               └── example
│   │                   └── outlivryteamproject
│   │                       ├── OutlivryTeamProjectApplication.class
│   │                       ├── common
│   │                       │   ├── BaseEntity.class
│   │                       │   ├── S3ImageUploader.class
│   │                       │   ├── SoftDelete.class
│   │                       │   ├── TokenUserId.class
│   │                       │   ├── UpdateUtils.class
│   │                       │   ├── exception
│   │                       │   │   └── ServerException.class
│   │                       │   └── response
│   │                       │       └── ApiResponse.class
│   │                       ├── config
│   │                       │   ├── FilterConfig.class
│   │                       │   ├── JwtFilter.class
│   │                       │   ├── JwtUtil.class
│   │                       │   ├── PasswordEncoder.class
│   │                       │   ├── RestTemplateConfig.class
│   │                       │   ├── S3Config.class
│   │                       │   ├── WebConfig.class
│   │                       │   └── aop
│   │                       │       ├── OrderStatusLoggingAspect.class
│   │                       │       └── annotaion
│   │                       │           └── OrderStatusLogger.class
│   │                       ├── domain
│   │                       │   ├── auth
│   │                       │   │   ├── controller
│   │                       │   │   │   └── AuthController.class
│   │                       │   │   ├── dto
│   │                       │   │   │   ├── request
│   │                       │   │   │   │   ├── SigninRequest.class
│   │                       │   │   │   │   └── SignupRequest.class
│   │                       │   │   │   └── response
│   │                       │   │   │       ├── NaverUserInfoReponse$NaverUserInfoReponseBuilder.class
│   │                       │   │   │       ├── NaverUserInfoReponse.class
│   │                       │   │   │       ├── SigninResponse.class
│   │                       │   │   │       └── SignupResponse.class
│   │                       │   │   ├── service
│   │                       │   │   │   └── AuthService.class
│   │                       │   │   └── weblogin
│   │                       │   │       └── naver
│   │                       │   │           └── NaverOAuthClient.class
│   │                       │   ├── cart
│   │                       │   │   ├── controller
│   │                       │   │   │   └── CartController.class
│   │                       │   │   ├── dto
│   │                       │   │   │   └── responseDto
│   │                       │   │   │       ├── FindCartResponseDto.class
│   │                       │   │   │       └── SaveCartResponseDto.class
│   │                       │   │   ├── entity
│   │                       │   │   │   └── Cart.class
│   │                       │   │   ├── repository
│   │                       │   │   │   └── CartRepository.class
│   │                       │   │   └── service
│   │                       │   │       ├── CartService.class
│   │                       │   │       └── CartServiceImpl.class
│   │                       │   ├── menu
│   │                       │   │   ├── controller
│   │                       │   │   │   ├── MenuController.class
│   │                       │   │   │   └── OwnerMenuController.class
│   │                       │   │   ├── dto
│   │                       │   │   │   ├── requestDto
│   │                       │   │   │   │   ├── MenuRequestDto.class
│   │                       │   │   │   │   └── ModifiedMenuRequestDto.class
│   │                       │   │   │   └── responseDto
│   │                       │   │   │       └── MenuResponseDto.class
│   │                       │   │   ├── entity
│   │                       │   │   │   └── Menu.class
│   │                       │   │   ├── repository
│   │                       │   │   │   └── MenuRepository.class
│   │                       │   │   └── service
│   │                       │   │       ├── MenuService.class
│   │                       │   │       └── MenuServiceImpl.class
│   │                       │   ├── order
│   │                       │   │   ├── controller
│   │                       │   │   │   └── OrderController.class
│   │                       │   │   ├── dto
│   │                       │   │   │   ├── OrderItemConverter.class
│   │                       │   │   │   ├── requestDto
│   │                       │   │   │   │   └── OrderRequestDto.class
│   │                       │   │   │   └── responseDto
│   │                       │   │   │       └── OrderResponseDto.class
│   │                       │   │   ├── entity
│   │                       │   │   │   ├── Order.class
│   │                       │   │   │   └── OrderItem.class
│   │                       │   │   ├── repository
│   │                       │   │   │   └── OrderRepository.class
│   │                       │   │   └── service
│   │                       │   │       ├── OrderService.class
│   │                       │   │       └── OrderServiceImpl.class
│   │                       │   ├── review
│   │                       │   │   ├── controller
│   │                       │   │   │   └── ReviewController.class
│   │                       │   │   ├── dto
│   │                       │   │   │   ├── requestDto
│   │                       │   │   │   │   ├── CreateReviewRequestDto.class
│   │                       │   │   │   │   ├── FindByStarsRequestDto.class
│   │                       │   │   │   │   └── UpdateReviewRequestDto.class
│   │                       │   │   │   └── responseDto
│   │                       │   │   │       ├── CreateReviewResponseDto.class
│   │                       │   │   │       ├── FindReviewResponseDto.class
│   │                       │   │   │       └── UpdateReviewResponseDto.class
│   │                       │   │   ├── entity
│   │                       │   │   │   └── Review.class
│   │                       │   │   ├── repository
│   │                       │   │   │   └── ReviewRepository.class
│   │                       │   │   └── service
│   │                       │   │       ├── ReviewService.class
│   │                       │   │       └── ReviewServiceImpl.class
│   │                       │   ├── store
│   │                       │   │   ├── controller
│   │                       │   │   │   ├── StoreController.class
│   │                       │   │   │   └── StoreOwnerController.class
│   │                       │   │   ├── dto
│   │                       │   │   │   ├── request
│   │                       │   │   │   │   ├── StoreRequestDto.class
│   │                       │   │   │   │   └── updateStoreRequestDto.class
│   │                       │   │   │   └── response
│   │                       │   │   │       ├── StoreResponseDto.class
│   │                       │   │   │       └── findOneStoreResponseDto.class
│   │                       │   │   ├── entity
│   │                       │   │   │   └── Store.class
│   │                       │   │   ├── repository
│   │                       │   │   │   └── StoreRepository.class
│   │                       │   │   └── service
│   │                       │   │       ├── StoreOwnerService.class
│   │                       │   │       ├── StoreOwnerServiceImpl.class
│   │                       │   │       ├── StoreService.class
│   │                       │   │       └── StoreServiceImpl.class
│   │                       │   └── user
│   │                       │       ├── controller
│   │                       │       │   └── UserController.class
│   │                       │       ├── dto
│   │                       │       │   ├── request
│   │                       │       │   │   ├── UserDeleteRequest.class
│   │                       │       │   │   ├── UserPasswordRequest.class
│   │                       │       │   │   └── UserRequest.class
│   │                       │       │   └── response
│   │                       │       │       └── UserReponse.class
│   │                       │       ├── entity
│   │                       │       │   └── User.class
│   │                       │       ├── enums
│   │                       │       │   ├── LoginType.class
│   │                       │       │   └── UserRole.class
│   │                       │       ├── repository
│   │                       │       │   └── UserRepository.class
│   │                       │       └── service
│   │                       │           └── UserService.class
│   │                       └── exception
│   │                           ├── CustomException.class
│   │                           ├── CustomExceptionHandler.class
│   │                           ├── CustomExceptionResponse$CustomExceptionResponseBuilder.class
│   │                           ├── CustomExceptionResponse.class
│   │                           ├── ErrorCode.class
│   │                           └── ExceptionCode.class
```

<br/>

# 5. Development Workflow (개발 워크플로우)

## 브랜치 전략 (Branch Strategy)

- 공동 작업을 위해 `dev` 브랜치를 생성하여 기능 개발의 기준 브랜치(origin) 로 사용함
- 개인 작업은 `feature/기능명` 형식의 브랜치를 dev 브랜치에서 분리하여 개발
- 개발 완료시 pull request로 `dev` 브랜치에 병합

## 블록 구문

한 줄짜리 블록일 경우라도 {}를 생략하지 않고, 명확히 줄 바꿈 하여 사용

```
if (session != null) {
     session.invalidate();
}
```

<br/>
<br/>   
카멜 표기법을 이용하여 가독성을 향상

```
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;

```

<br/>

## 예외처리

예외 발생시 커스텀 예외를 설정해 처리
```
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
```

## 연관관계
@ManyToOne, @OneToMany를 활용해 연관관계 구현
```
@ManyToOne
@JoinColumn(name = "user_id")
private User user;
    
@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
private List<Comment> comments = new ArrayList<>();
```

## AOP
어노테이션을 만들어 특정 상황 발생 시 로그 남기기
```
    @AfterReturning(
            pointcut = "@annotation(org.example.outlivryteamproject.config.aop.annotaion.OrderStatusLogger)",
            returning = "result"
    )
    public void LogOrderStatus(Object result) throws Throwable {

        if (result instanceof OrderResponseDto responseDto) {
            Long storeId = responseDto.getStoreId();
            Long orderId = responseDto.getOrderId();
            LocalDateTime time = LocalDateTime.now();

            log.info("주문 상태가 변경되었습니다 - 가게 ID: {}, 주문 ID: {}, 요청 시각: {}", storeId, orderId, time);

        } else {
            log.warn("");
        }
    }
```

## BaseEntity 구현
모든 엔티티에서 사용하는 공통 부분은 따로 구현
```
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Setter
public class BaseEntity extends SoftDelete {

    @CreatedDate // 생성시 자동입력
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createTime;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedTime;
}
```

## SoftDelete
삭제 시 SoftDelete 클래스를 구현
```
    @Column(name = "is_deleted")
    private boolean isDeleted;

    public void isDelete() {
        if(this.isDeleted) {
            throw new CustomException(ExceptionCode.ALREADY_DELETE);
        } this.isDeleted = true;
    }
```

# 6. 수행 결과



## 1.회원가입

```
POST auth/user/signup || /auth/owner/signup

message : “유저 회원 가입 완료” 
or ”사장님 회원가입 완료”
```

<br/>

## 2.로그인

```
POST /auth/login

”Authorization”: Bearer {token}
```

<br/>

## 3. 네이버 소셜 로그인

```
POST /auth/naver/callback
```

<br/>

<br/>

## 4.유저 조회

```
GET /users

message : “유저 정보 조회 완료”
body :
{
    “email” = "test@test.com",
    “password” = "비밀번호",
    “name” = "이름",
    “nickname” = "닉네임",
    “phone” = "010-0000-0000",
    “birth” = "2000-07-29",
    “address” = "주소입니다"
}
```

<br/>

## 5.유저 정보 수정

```
PUT /users

message : “유저 정보 수정 완료”
```

<br/>

## 6.유저 패스워드 변경

```
PUT /users/changePW

message : “패스워드 변경 완료”
```

<br/>

## 7.유저 탈퇴

```
DELETE /users

message : “회원 탈퇴 완료”
```

<br/>

## 8. 가게 생성

```
POST /owners/stores


 message : "가게 생성 성공",
 body : 
{
      “storeId” = 1,
      “userId” = 1,
      “storeName” = "가게 이름",
      “storePictureUrl” = "url",
      “phone” = "010-0000-0000",
      “address” = "주소",
      “content” = "가게 설명",
      “category” = "한식",
      “minDeliveryPrice” = 10000,
      “deliveryTip” = 1000,
      “openTime” = 09:00,
      “closeTime” = 21:00,
      “createdDate” = LocalDateTime,
      “modifiedDate” = LocalDateTime,
      “isDeleted” = true
}
```

<br/>

## 9. 가게 리스트 조회

```
GET /stores

 message : "가게 조회 성공",
 body : 
 stores[
{
      “storeId” = 1,
      “userId” = 1,
      “storeName” = "가게 이름",
      “storePictureUrl” = "url",
      “phone” = "010-0000-0000",
      “address” = "주소",
      “content” = "가게 설명",
      “category” = "한식",
      “minDeliveryPrice” = 10000,
      “deliveryTip” = 1000,
      “openTime” = 09:00,
      “closeTime” = 21:00,
      “createdDate” = LocalDateTime,
      “modifiedDate” = LocalDateTime,
      “isDeleted” = true
}
]
```

<br/>

## 10.가게 단건 조회

```
GET /stores/{storeId}

 message : "가게 조회 성공",
 body : 
{
      “storeId” = 1,
      “userId” = 1,
      “storeName” = "가게 이름",
      “storePictureUrl” = "url",
      “phone” = "010-0000-0000",
      “address” = "주소",
      “content” = "가게 설명",
      “category” = "한식",
      “minDeliveryPrice” = 10000,
      “deliveryTip” = 1000,
      “openTime” = 09:00,
      “closeTime” = 21:00,
      “createdDate” = LocalDateTime,
      “modifiedDate” = LocalDateTime,
      “isDeleted” = true
}
```

<br/>

## 11.가게 수정

```
PATCH /owners/stores/{storeId}

 message : "가게 수정 성공",
 body : 
{
      “storeId” = 1,
      “userId” = 1,
      “storeName” = "가게 이름",
      “storePictureUrl” = "url",
      “phone” = "010-0000-0000",
      “address” = "주소",
      “content” = "가게 설명",
      “category” = "한식",
      “minDeliveryPrice” = 10000,
      “deliveryTip” = 1000,
      “openTime” = 09:00,
      “closeTime” = 21:00,
      “createdDate” = LocalDateTime,
      “modifiedDate” = LocalDateTime,
      “isDeleted” = false
}
```

<br/>

## 12. 가게 삭제

```
DELETE /owners/stores/{storeId}

  message : "가게 삭제 성공"
```

<br/>

## 13. 메뉴 등록

```
POST /owner/stores/{storeId}/menus

  message : “등록 성공”
  body : 
{
    “menuId” = 1,
    “storeId” = 1,
    “menuName” = "메뉴 이름",
    “price” = 15000,
    “imageUrl” = "url",
    “createdDate” = LocalDateTime,
    “modifiedDate” = LocalDateTime,
    “soldOut” = false,
    “isDeleted” = false
}
```

<br/>

## 14. 메뉴 단건 조회

```
GET /stores/menus/{menuId}

  message : “조회 성공”
  body : 
{
    “menuId” = 1,
    “storeId” = 1,
    “menuName” = "메뉴 이름",
    “price” = 15000,
    “imageUrl” = "url",
    “createdDate” = LocalDateTime,
    “modifiedDate” = LocalDateTime,
    “soldOut” = false,
    “isDeleted” = false
}
```

## 15. 메뉴 전체 조회

```
GET /stores/{storeId}/menus

  message : “조회 성공”
  body : 
  menus
  [
{
    “menuId” = 1,
    “storeId” = 1,
    “menuName” = "메뉴 이름",
    “price” = 15000,
    “imageUrl” = "url",
    “createdDate” = LocalDateTime,
    “modifiedDate” = LocalDateTime,
    “soldOut” = false,
    “isDeleted” = false
}
]
```
## 16. 메뉴 수정 

```
PATCH /owner/stores/{storeId}/menus/{menuId}

  message : “수정 성공”
  body : 
{
    “menuId” = 1,
    “storeId” = 1,
    “menuName” = "메뉴 이름",
    “price” = 15000,
    “imageUrl” = "url",
    “createdDate” = LocalDateTime,
    “modifiedDate” = LocalDateTime,
    “soldOut” = false,
    “isDeleted” = false
}
```

## 17. 메뉴 상태 변경
```
PATCH /owner/stores/{storeId}/menus/{menuId}/status

  message : “재고 상태 변경”
```
## 18. 메뉴 삭제
```
DELETE /owner/stores/{storeId}/menus/{menuId}

  message : “삭제 완료”
```
## 19. 주문 생성
```
POST /orders/stores/{storeId}

  message : “주문 완료”
  body : 
{
    “storeId” = 1,
    “orderId” = 1,
    “totalPrice” = 42000,
    “orderItems” = List<OrderItem>,
    “isReceived” = false,
    “isDelivery” = false,
    “creatTime” = LocalDateTime
}
```
## 20. 주문 조회
```
GET /orders/{orderId}

  message : “주문 조회”
  body : 
{
    “storeId” = 1,
    “orderId” = 1,
    “totalPrice” = 42000,
    “orderItems” = List<OrderItem>,
    “isReceived” = false,
    “isDelivery” = false,
    “creatTime” = LocalDateTime
}
```
## 21. 주문 수락
```
PATCH /orders/{orderId}/received

message:”주문 수락 완료”
```
## 22. 배달 수락
```
PATCH /orders/{orderId}/delivery

message:”배달 완료”
```
## 23. 주문 취소
```
DELETE /orders/{orderId}

message:”주문 취소.”
```
## 24. 장바구니 생성
```
POST /carts/menus/{menuId}

  message : “장바구니에 추가했습니다.”
  body : 
{
    “cartId” = 1,
    “menuName” = "메뉴 이름",
    “price” = 15000,
    “quantity” = 1
}
```
## 25. 장바구니 조회
```
GET /carts

  message : “조회 성공”
  body : 
{
    “cartId” = 1,
    “menuName” = "메뉴 이름",
    “price” = 15000,
    “quantity” = 1
}
```
## 26. 특정 물건 삭제
```
DELETE /carts/{cartId}

message : ”삭제 완료”
```
## 27. 장바구니 비우기
```
DELETE /carts

message : ”장바구니를 비웠습니다.”
```
## 28. 리뷰 생성
```
POST /reviews/stores/{storeId}/orders/{orderId}

  message : “리뷰 작성”
  body : 
{
    “reviewId” = 1,
    “userNickname” = "닉네임",
    “contents” = "리뷰 내용",
    “stars” = 3,
    “createTime” = LocalDateTime
}
```
## 29. 전체 리뷰 조회
```
GET /reviews/stores/{storeId}

  message : “전체 리뷰 조회”
  body : 
  reviews : [
{
    “reviewId” = 1,
    “userNickname” = "닉네임",
    “contents” = "리뷰 내용",
    “stars” = 3,
    “createTime” = LocalDateTime
}
]
```
## 30. 별점으로 리뷰 조회
```
GET /reviews/stores/{storeId}/stars

  message : “별점으로 리뷰 조회”
  body : 
  reviews : [
{
    “reviewId” = 1,
    “userNickname” = "닉네임",
    “contents” = "리뷰 내용",
    “stars” = 3,
    “createTime” = LocalDateTime
}
]
```
## 31. 리뷰 수정
```
PATCH /reviews/{reviewId}

  message : “리뷰 수정”
  contnents : 
{
    “reviewId” = 1,
    “userNickname” = "닉네임",
    “contents” = "수정된 리뷰 내용",
    “stars” = 5,
    “createTime” = LocalDateTime,
    “modifiedTime” = LocalDateTime
}
```

## 32. 리뷰 삭제
```
DELETE /reviews/{reviewId}

  message : “리뷰 삭제”
```