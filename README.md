# Fashion Shopping

- [Fashion Shopping](#fashion-shopping)
   * [Implementation](#implementation)
      + [Back-End](#back-end)
      + [Front-End](#front-end)
   * [Run Guide](#run-guide)
      + [Code Build](#code-build)
      + [Test](#test)
      + [Run](#run)
   * [API](#api)
   * [Front Web Page](#front-web-page)
   * [Project Directory](#project-directory)
   * [Tech Skills](#tech-skills)
      + [Spring](#spring)
      + [Docs](#docs)
      + [DB](#db)
      + [Test](#test-1)
      + [Web](#web)


## Implementation
### Back-End
#### Brand
1. **브랜드 조회 API (`/brands` - GET):**
    - 클라이언트에서 모든 브랜드를 조회할 수 있습니다.
2. **브랜드 생성 API (`/brands` - POST):**
    - 클라이언트에서 새로운 브랜드를 생성할 수 있습니다. 
3. **브랜드명 수정 API (`/brands/{id}` - PATCH):**
    - 클라이언트에서 브랜드명을 수정할 수 있습니다. 
4. **브랜드 삭제 API (`/brands/{id}` - DELETE):**
    - 클라이언트에서 특정 브랜드를 삭제할 수 있습니다.

#### Product
1. **상품 조회 API (`/products` - GET):**
    - 클라이언트에서 모든 상품을 조회할 수 있습니다.
2. **상품 추가 API (`/brands/{id}/products` - POST):**
    - 특정 브랜드에 상품을 추가할 수 있는 API입니다. 
3. **가격 수정 API (`/products/{id}/price` - PATCH):**
    - 특정 상품의 가격을 수정할 수 있는 API입니다. 
4. **카테고리 수정 API (`/products/{id}/category` - PATCH):**
    - 특정 상품의 카테고리를 수정할 수 있는 API입니다. 
5. **상품 삭제 API (`/products/{id}` - DELETE):**
    - 특정 상품을 삭제할 수 있는 API입니다. 

#### 기타 조회
1. **최저 가격인 단일 브랜드의 카테고리 상품 조회 API (`/brands/min-price-category` - GET):**
    - 최저 가격인 단일 브랜드의 카테고리 상품 조회하는 API입니다.
2. **카테고리 별 최저가격 브랜드 조회 API (`/categories/price-brand` - GET):**
    - 특정 카테고리에서 최저가격인 브랜드와 가격을 조회하는 API입니다. 카테고리를 쿼리 파라미터로 카테고리를 전달합니다.
3. **전체 카테고리 별 최저가격 브랜드 조회 API (`/categories/min-prices` - GET):**
    - 전체 카테고리에서 최저가격인 브랜드와 가격을 조회하는 API입니다. `CategoryMinPriceResponse`를 반환합니다.

> API 실패 시, 실패 사유 메시지와 실패값이 Body로 반환됩니다.

### Front-End
#### 조회
<img width="1465" alt="Get" src="https://github.com/Chocochip101/FashionShopping/assets/73146678/a82af126-65eb-4d3e-ae43-93594d6c6f35">

#### 조회 결과
<img width="1473" alt="GetResult" src="https://github.com/Chocochip101/FashionShopping/assets/73146678/9aeb7225-6d22-4143-ad60-33ad7db2a0f5">

#### 조회 결과 닫기
<img width="1473" alt="CloseResult" src="https://github.com/Chocochip101/FashionShopping/assets/73146678/575e01cf-f87d-44fb-b694-5df75ef2f24d">

#### 등록
<img width="1163" alt="Post" src="https://github.com/Chocochip101/FashionShopping/assets/73146678/ac3475f6-67a8-456f-b1a7-dd2e62ee51a0">

#### 등록 성공
<img width="1480" alt="PostOk" src="https://github.com/Chocochip101/FashionShopping/assets/73146678/f59d5574-195a-45f9-b058-b96b538916c0">


#### 실패 사유, 실패값
<img width="1163" alt="ErrorMessage" src="https://github.com/Chocochip101/FashionShopping/assets/73146678/1e3d60b3-a6d4-4a47-a365-caf499f5dad8">

## Run Guide
### Code Build
```bash
./gradlew build
```
### Test
```bash
./gradlew test
```
### Run
```bash
java -jar build/libs/FashionShopping-0.0.1-SNAPSHOT.jar
```


## API
> http://localhost:8080/docs/index.html

## Front Web Page
> http://localhost:8080/home.html

## Project Directory
```bash
/src
  /main
    /java.com.musinsa.fashionshopping
      /advice
      /brand
      /global
      /product
      DataLoader.java
      FashionShoppingApplication.java
      
    /resources
      /static
        /css/style.css
        /docs/index.html
        /js
        /home.html
      application.yml
      
  /test
    /java.com.musinsa.fashionshopping
      /brand
      /fixture
      /product
```
## Tech Skills
### Spring
- **Java** 17
- **Spring Boot** 2.7.2
- **Spring Data JPA**
- **Validation**
- **Lombok**

### Docs
- **RestDocs**
- **Asciidoctor** 

### DB
- **H2 Database**

### Test
- **JUnit 5**
- **RestAssured**
- **Mockito**
- **Jacoco** 

### Web
- **Html**
- **css**
- **JavaScript**
