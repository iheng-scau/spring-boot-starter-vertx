package cn.iheng.springboot.starter.mapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * used to register result mappers defined in mapper interfaces
 * @author zhangdh@jpush.cn
 * @date 7/9/2018
 */
public class ResultMapRegistry {
    final Map<String,ResultMap> registry=new ConcurrentHashMap<>();

    /**
     * class to hold result map definition
     */
    public static class ResultMap{
        Map<String,String> mapper;

        public Map<String, String> getMapper() {
            return mapper;
        }

        public void setMapper(Map<String, String> mapper) {
            this.mapper = mapper;
        }
    }
}
