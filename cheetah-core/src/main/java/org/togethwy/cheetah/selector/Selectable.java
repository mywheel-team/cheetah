package org.togethwy.cheetah.selector;

import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/10/24 10:31
 */
public interface Selectable {

    /**
     * css selector
     * @param selector css selector expression
     * @return new selector
     */
    Selectable $(String selector);

    Selectable css(String selector);

    /**
     * 正则选择器
     * @param regex 正则表达式
     * @return 选取后的选择器
     */
    Selectable regex(String regex);

    /**
     * 获取所有a标签
     * @param selector
     * @return
     */
    Selectable links(String selector);


    /**
     * 获取单个结果
     * @return 单个结果
     */
    String getValue();

    /**
     * 获取列表中索引处元素
     * @param index 索引
     * @return 元素
     */
    Selectable get(int index);


    /**
     * 获取多个结果
     * @return 多个结果
     */
    List<String> getAll();



}
