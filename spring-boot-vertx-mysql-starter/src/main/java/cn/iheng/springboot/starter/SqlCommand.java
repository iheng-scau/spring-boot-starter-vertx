package cn.iheng.springboot.starter;

import cn.iheng.springboot.starter.enums.SqlCommandType;
import lombok.Builder;
import lombok.Data;

/**
 * @author iheng_scau@hotmail.com
 * @date 7/16/2018
 */
@Data
@Builder
public class SqlCommand {
    private SqlCommandType sqlCommandType;
    private String sql;
}
