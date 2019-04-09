# 지자체 협약 지원 API

*중소기업은행 지자체 협약 지원 정보 서비스 개발

# Getting Start

## Prerequisites

### Configuration
- Java 1.8.x
- IDE: Spring Tool Suite version 3.9.7
- Framework: Spring Boot 2.1.3.RELEASE
             Spring Boot WebFlux
- Lombok Plugin
- In-Memory Database H2
- FreeMarker for Vier


## Build

```
> git clone https://github.com/basquiat78/municipality-convention-support.git
> cd municipality-convention-support
> gradlew bootRun

```

## API Specification

1. CSV File Upload And DB

어플리케이션이 실행될때 특정 폴더에 파일을 넣고 DB에 넣는 방식보다는 뷰를 통해서 파일을 업로드하고 그것을 DB에 넣는 방식을 취함

1.1 CSV파일로부터 Entity 생성

조건에 의해 지자체 코드와 명을 갖는 Municipality와 나머지 정보를 갖는 MunicipalitySupport로 나눈다.    
사실 데이터를 보면 OneToOne으로 구성되어 있어서 심플하게 OneToOne구성을 함. (이것은 변경될 수 있다고 봐야한다. 왜냐하면 하나의 지자체가 여러개의 MunicipalitySupport을 가질 수 있음을 언제나 고려해야한다.)     

양방향 vs 단방향?


