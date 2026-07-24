package com.endpoint.home.to;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cr
 * @date 2022/11/22
 * @description
 */
@Data
@AllArgsConstructor
@ToString
public class RedisTo {
    private Long id;
    private LocalDateTime time;

    public static void main(String[] args) throws JsonProcessingException {
        List<RedisTo> list = new ArrayList<>();

        RedisTo redisTo = new RedisTo(1L, LocalDateTime.now());
        RedisTo redisTo1 = new RedisTo(2L, LocalDateTime.now());
        list.add(redisTo);
        list.add(redisTo1);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String jsonStr = objectMapper.writeValueAsString(list);
        System.out.println(jsonStr);


        String sql  = "[{\"id\":1,\"time\":[2022,11,22,11,34,5,992000000]},{\"id\":2,\"time\":[2022,11,22,11,34,5,992000000]}]";
        ObjectMapper objectMapper2 = new ObjectMapper();
        objectMapper2.findAndRegisterModules();
        List<RedisTo> list1 = objectMapper2.readValue(jsonStr, List.class);
        System.out.println(list1);



//        RedisTo redisTo = new RedisTo(1L, LocalDateTime.now());
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();
//        String jsonStr = objectMapper.writeValueAsString(redisTo);
//        System.out.println(jsonStr);
//
//        String sql  = "{\"id\":1,\"time\":[2022,11,22,11,36,27,415000000]}";
//        ObjectMapper objectMapper2 = new ObjectMapper();
//        objectMapper2.findAndRegisterModules();
//        RedisTo redis= objectMapper2.readValue(jsonStr, RedisTo.class);
//        System.out.println(redis);
    }
}
