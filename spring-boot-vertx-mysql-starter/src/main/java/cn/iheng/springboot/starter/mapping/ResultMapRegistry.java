package cn.iheng.springboot.starter.mapping;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * used to register result mappers defined in mapper interfaces
 * @author iheng_scau@hotmail.com
 * @date 7/9/2018
 */
public class ResultMapRegistry {
    final Map<String, ResultMapper> registry=new ConcurrentHashMap<>();

    public void addResultMap(String id, ResultMapper mapper){
        this.registry.put(id, mapper);
    }

    /**
     * class to hold result map definition
     */
    @Data
    @Builder
    public static class ResultMapper {
        Map<String,String> mapper;

        public Map<String, String> getMapper() {
            return mapper;
        }

        public void setMapper(Map<String, String> mapper) {
            this.mapper = mapper;
        }
    }
}
