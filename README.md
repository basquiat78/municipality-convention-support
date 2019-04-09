# 지자체 협약 지원 API

*중소기업은행 지자체 협약 지원 정보 서비스 개발

# Getting Start

## Prerequisites

### Configuration
- OS Window10
- Java 1.8.x
- IDE: Spring Tool Suite version 3.9.7
- Framework: Spring Boot 2.1.3.RELEASE
             Spring Boot WebFlux
- Lombok Plugin
- In-Memory Database H2
- FreeMarker for View


## Build

```
> git clone https://github.com/basquiat78/municipality-convention-support.git
> cd municipality-convention-support
> gradlew bootRun

```

# API Specification

## 1. CSV File Upload And DB

어플리케이션이 실행될때 특정 폴더에 파일을 넣고 DB에 넣는 방식보다는 뷰를 통해서 파일을 업로드하고 그것을 DB에 넣는 방식을 취함

### 1.1 CSV파일로부터 Entity 생성

조건에 의해 지자체 코드와 명을 갖는 Municipality와 나머지 정보를 갖는 MunicipalitySupport로 나눈다.    
사실 데이터를 보면 OneToOne으로 구성되어 있어서 심플하게 OneToOne구성을 함. (이것은 변경될 수 있다고 봐야한다. 왜냐하면 하나의 지자체가 여러개의 MunicipalitySupport을 가질 수 있음을 언제나 고려해야한다.)     

### 1.2 지자체 코드 생성

딱히 주어진 것이 없으니 개발자 임의대로 만드는 듯 하여 반복을 하면서 preFix로 'region'를 붙이고 index와 중복될 수 있는 3자리 난수를 조합해서 사용한다.     
> e.g regino1215    

create, update (과제엔 주어지지 않았지만)인해 MunicipalitySupport이 Municipality와의 연관관계에서 주인이 되어야 하니 OneToOne은 MunicipalitySupport쪽에 걸어두고  MunicipalitySupport정보에 Municipality정보를 담아서 List로 반환하고 saveAll를 통해 전체 데이터를 저장한다.    

### 1.3 Usage

[http://localhost:8080/view/upload](http://localhost:8080/view/upload)

뷰에서 해당 파일을 선택하고 업로드하는 형식을 취하고 있다.

테스트 코드를 위해서 관련 파일은 프로젝트 내부의 testfiles에 저장함

## 2. 지원하는 지자체 목록 검색 API

특별히 paging과 관련된 조건이 없지만 paging처리된 api와 처리되지 않은 api를 작성함     

### 2.1 Usage

with paging [http://localhost:8080/api/v1/municipalitys/support/withpage?page=2&size=10](http://localhost:8080/api/v1/municipalitys/support/withpage?page=2&size=10)

queryParam을 아무것도 주지 않으면 기본적으로 page=1&size=10로 보여준다.

without pagin [http://localhost:8080/api/v1/municipalitys/support](http://localhost:8080/api/v1/municipalitys/support)

## 3. 지원하는 지자체명을 입력 받아 해당 지자체의 지원정보를 출력하는 API 

입출력은 json으로 하기 때문에 일반적인 get방식으로 테스트하기 힘들다.

RESTful API url을 예를 들면 http://localhost:8080/api/v1/municipalitys/{name} 처럼 날리면 좋겠지만 조건에 의해 get방식이지만 json을 입력으로 할려면 Postman같은 툴을 사용하는 방법으로 테스트함.

### 3.1 Usage

[http://localhost:8080/api/v1/municipalitys/support/name](http://localhost:8080/api/v1/municipalitys/support/name)

![실행이미지](https://github.com/basquiat78/municipality-convention-support/blob/master/img/2.2.PNG)

Like검색 조건을 두어서 리스트로 나올 수 있음

## 4. 지원하는 지자체 정보 수정 기능 API

PATCH로 설정해서 Update를 수행한다. 예제대로라면 id가 출력화면에 없기 때문에 일일이 비교를 해서 해당 row를 정확하게 업데이트하는게 맞을 것이다.
하지만 가장 정확한건 유니크한 id를 통해서 업데이트하는것이 가장 좋은 방법이라 생각한다.

### 4.1 Usage

[http://localhost:8080/api/v1/municipalitys/support](http://localhost:8080/api/v1/municipalitys/support)

![실행이미지](https://github.com/basquiat78/municipality-convention-support/blob/master/img/4.PNG)
 
업데이트가 수행되고 난 이후에 해당 아이디로 다시 조회를 해서 정보를 내려주는 방식을 취한다.

## 5. 지원한도 컬럼에서 지원금액으로 내림차순 정렬(지원금액이 동일하면 이차보전 평균 비율이 적은 순서)하여 특정 개수만 출력하는 API 

비교해야하는 컬럼들이 정규화된 데이터가 아니기 때문에 조건에 부합하는 방식으로 지원 한도와 이차 보전의 정보를 유의미하게 변환한다.
JAVA8 스트림 API의 sort를 통해서 비교를 한다.     

처음에는 지원 한도로 sort를 한다. 기본적으로 내림차순이기 때문에 API로 제공하는 방식으로 오름차순으로 정렬하고 체인 메소드롤 통해서 지원 한도가 같은 경우 이차 보전 정보로 내림차순해서 정렬을 하고 limit를 통해 입력받은 수만 municiplityName들을 조합해서 화면에 보여준다.

### 5.1 Usage

3개만 추출하도록 호출

[http://localhost:8080/api/v1/municipalitys/support/limits/3](http://localhost:8080/api/v1/municipalitys/support/limits/3)

이것은 pathVariable로 조회하는 방식으로 작성함

## 6. 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력하는 API

5번과 마찬가지라고 생각했지만 생각해보면 minRate가 같은 녀석이 존재할 수 있다는 것이다.    
따라서 전체 목록을 가져와서 Stream Api를 이용해서 groupingBy를 이용해 minRate를 갖는 TreeMap을 생성하고 list로 반환한다. 오름 차순이니 limit를 걸어서 맨 처음것만 가져온다.    
리스트를 돌면서 해당 추천 기관명을 joining해서 Dto에 담아서 내보낸다.

### 6. Usage

[http://localhost:8080/api/v1/municipalitys/support/rate/min](http://localhost:8080/api/v1/municipalitys/support/rate/min)

# At A Glance

이전에 Type Script와 nodeJs로 되어 있는 서버를 요청에 의해 자바로 바꾸는 작업을 한 적이 있다.
비동기 서버에 대한 정보를 찾다가 WebFlux의 존재를 알게 되었다.
이 때가 대충 2018년 6월인가 7월 중순으로 기억한다. 리액티브 프로그래밍에 대한 관심과 간지나는 방식이 맘에 들어 간혹 필요하면 webFlux을 사용했다.

컨트롤러에 대해 두가지 방식을 제공하는데 기존의 @RestController 방식으로 하는 것과 Functional Endpoints방식을 지원한다.


아마도 nodeJs를 해봤다면 Functional Endponts방식이 많이 비슷하다는 것을 알 수 있는데 사실 이 방식을 쓰다보면 기존의 방식과는 좀 달라서 애를 먹는다.

일단 기존 방식의 컨트롤러 방식에서 사용할 수 있는 막강하고 다양한 어노테이션을 사용하기 모호해진다.   

또한 webFlux를 사용하기 위해서는 전체적인 로직의 흐름이 비동기여야한다. 하지만 지금같은 프로젝트에서 H2를 사용하게 되면 인 메모리 디비라고 해도 I/O가 발생하는데 이게 기본적으로 Block I/O라는 것이다.

스프링 진형에서는 WebFlux와 연계된 MongoDB관련 라이브러리를 제공하니 과제를 검토할 분에게 '나는 이런 걸 썼으니 검토하고 테스트하기 위해서는 본인 컴터에 noSql같은 MongoDB를 까세요'라고 하는 것도 예의에 어긋나기도 하고...

물론 com.github.davidmoten:rxjava2-jdbc 이런 넘이 존재해서 논블로킹 방식을 제공한다는데...(어떻게???)

이 방법을 쓰게 되면 jpa는 쓸 수 없다. native SQL를 써야하니깐...

하지만 그럼에도 언젠가는 기존의 전통적인 RDBMS 벤더들이 이와 관련된 방식을 제공하지 않을까 라는 기대감을 가지고 써본다.

아마도 과제로 주어진 모든 테스트를 view를 제공해서 할 수도 있겠다...이것은 Next Episode로...

핑계지만 개인적인 사정으로 시간 부족으로 jwt연계를 하지 못한 것은 좀 아쉬움에 엄청 남는다....

마지막 옵션 문제는 감을 못잡겠다...enum이든 map이든 관련 정보들을 검색할 수 있는 녀석들을 노가다로 일단 작성하고 해당 기사를 일일이 하나씩 잘라서 비교하면서 dto에 세팅하는 것은 아마도 할 수 있을 거 같다...

문제는 위치 기반 관련 문제가 걸린다.

처음 생각했던 것은 데이터의 row를 기준으로 만일 사는 사람이 '충청 뭐시기'라고 하면 충청 뭐시기가 있는 row를 중심으로 위 아래 거리로 계산하는 방법을 처음 생각했다.

기사로부터 유의미한 비교 값들을 dto에 담아서 전체 데이터를 불러오고 돌면서 그에 해당하는 지자체 정보를 리스트를 중심으로 충청 (id가 50)이라면 50을 기준으로 위 아래로 가장 가까운 녀석을 선택하면 되지 않을까 생각도 해봤다....