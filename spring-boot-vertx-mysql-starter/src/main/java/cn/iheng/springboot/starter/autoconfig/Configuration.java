package cn.iheng.springboot.starter.autoconfig;

import cn.iheng.springboot.starter.mapping.ResultMapRegistry;
import lombok.Data;

/**
 * @author iheng_scau@hotmail.com
 * @date 7/9/2018
 */
@Data
public class Configuration {
    private final ResultMapRegistry resultMapRegistry=new ResultMapRegistry();
}
