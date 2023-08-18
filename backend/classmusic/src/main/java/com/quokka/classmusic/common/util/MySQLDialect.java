package com.quokka.classmusic.common.util;

import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Component;

@Component
public class MySQLDialect extends MySQL8Dialect {
    public MySQLDialect(){
        super();
        registerFunction("bitand" ,new SQLFunctionTemplate(IntegerType.INSTANCE,"(?1 & ?2)"));
    }
}
