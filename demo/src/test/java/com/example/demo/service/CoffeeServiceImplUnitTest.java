package com.example.demo.service;


import com.example.demo.entity.Coffee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * What is the test for?
 * Inner
 * Inter
 * Outer
 */

@DisplayName("CoffeeService 를 실행한 상황에서")
class CoffeeServiceImplUnitTest {

    /* 커피의 종류
    1. 브라질 산토스
    2. 콜롬비아 수프리모
    3. 자메이카 블루마운틴
    4. 에티오피아 예가체프
    5. 케냐 AA
    6. 코스타리카 따라주
    7. 탄자니아 AA (킬리만자로)
    8. 예멘 모카 마타리
    9. 하와이 코나
    10. 과테말라 안티구아
    11. 파나마 게이샤
    12. 엘살바도르
     */


    @DisplayName("findOneById() 에 argument 1을 넘겼을 때, '브라질 산토스'가 나와야 한다.")
    @Test
    void testFindOneById1() throws Exception {

        // Given: 커피가 주어진 상황에서
        CoffeeService CoffeeService = new CoffeeServiceImpl();

        // When: id(1)과 함께 findById 메소드를 호출 할 때
        Coffee coffee = CoffeeService.findOneById(1);

        // Then: 이름이 "브라질 산토스"인 커피를 받아야 한다.
        Assertions.assertEquals(1, coffee.getId());
        Assertions.assertEquals("브라질 산토스", coffee.getName());
    }

    @DisplayName("findOneById() 에 argument 2을 넘겼을 때, '콜롬비아 수프리모'가 나와야 한다.")
    @Test
    void testFindOneById2() throws Exception {

        // Given: 커피가 주어진 상황에서
        CoffeeService CoffeeService = new CoffeeServiceImpl();

        // When: id(1)과 함께 findById 메소드를 호출 할 때
        Coffee coffee = CoffeeService.findOneById(2);

        // Then: 이름이 "브라질 산토스"인 커피를 받아야 한다.
        Assertions.assertEquals(2, coffee.getId());
        Assertions.assertEquals("콜롬비아 수프리모", coffee.getName());

    }
}