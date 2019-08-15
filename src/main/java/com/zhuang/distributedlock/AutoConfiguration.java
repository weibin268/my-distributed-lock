package com.zhuang.distributedlock;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;


@Configurable
@ComponentScan
public class AutoConfiguration {
    //此配置不可删除需要保留，否则集成到其它工程中，不会扫描spring的bean
}
