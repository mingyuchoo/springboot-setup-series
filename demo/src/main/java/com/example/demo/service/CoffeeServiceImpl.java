package com.example.demo.service;


import com.example.demo.entity.Coffee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class CoffeeServiceImpl implements CoffeeService {

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

    private List<Coffee> coffeeList;

    public CoffeeServiceImpl() throws IOException {
        File file = new File(this.getClass().getClassLoader().getResource("data.json").getFile());
        ObjectMapper mapper = new ObjectMapper();
        coffeeList = mapper.readValue(file, new TypeReference<List<Coffee>>() {});
    }

    @Override
    public Coffee findOneById(int id) throws Exception {
        return coffeeList.get(id - 1);
    }
}
