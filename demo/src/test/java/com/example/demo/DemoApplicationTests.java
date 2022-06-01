package com.example.demo;

import com.example.demo.entity.Coffee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("서버가 실행된 상황에서")
@SpringBootTest
class DemoApplicationTests {

	@DisplayName("컨텍스트를 로딩할 때, data.json 파일에 있는 id(1) 인 커피 정보를 읽어 들여야 한다.")
	@Test
	void contextLoads() throws IOException {
		File file = new File(this.getClass().getClassLoader().getResource("data.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		List<Coffee> coffeeList = mapper.readValue(file, new TypeReference<List<Coffee>>() {});

		Assertions.assertEquals(1, coffeeList.get(0).getId());
	}

}

