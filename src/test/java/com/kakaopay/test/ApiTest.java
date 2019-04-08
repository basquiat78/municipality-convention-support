package com.kakaopay.test;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 1. testfiles의 파일을 읽어서 업로드하고 db에 인서트한다.
 *    - file upload라 컨트롤러 테스트 진행
 *	
 * 2. paging처리한 리스트를 가져온다.
 * 
 * 3. region으로 검색한 정보를 가져온다.
 * 
 * 4. 특정 아이디 정보를 업데이트하고 업데이트된 정보를 가져온다.
 * 
 * 5. 특정 아이디 정보를 삭제하고 기본적인 page = 1, size= 10인 리스트를 반환한다. 
 *    e.g id가 1인 넘을 삭제해보자
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class ApiTest {

}