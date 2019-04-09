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

테스트 코드를 위해서 관련 파일은 프로젝트 내부의 testfiles에 저장함

## 2. 지원하는 지자체 목록 검색 API

특별히 paging과 관련된 조건이 없지만 paging처리된 api와 처리되지 않은 api를 작성함     

### 2.1 Usage

with paging [http://localhost:8080/api/v1/municipalitys/support/withpage?page=2&size=10](http://localhost:8080/api/v1/municipalitys/support/withpage?page=2&size=10)

queryParam을 아무것도 주지 않으면 기본적으로 page=1&size=10로 보여준다.

without pagin [http://localhost:8080/api/v1/municipalitys/support](http://localhost:8080/api/v1/municipalitys/support)


