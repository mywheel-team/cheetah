package org.togethwy.cheetah.selector;

import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/10/24 10:31
 */
public interface Selectable {


    /**
     * css selector
     *
     * @param selector css selector expression
     * @return new selector
     */
    Selectable $(String selector);

    /**
     * 根据正则表达式获取所有a标签
     *
     * @param regex
     * @return
     */
    List<String> getLinks(String regex);

    /**
     * 获取所有a标签
     *
     * @return
     */
    List<String> getLinks();

    /**
     * 获取元素文本
     *
     * @return  元素字符串
     */
    String getValue();

    /**
     * 获取图片地址
     * @return
     */
    List<String> getImageUrls();

    /**
     * 根据正则获取图片地址
     * @param regex 正则式
     * @return
     */
    List<String> getImageUrls(String regex);

    /**
     * 获取索引处元素
     * @param index
     * @return
     */
    Selectable get(int index);

    /**
     * 获取所有文本
     * @return
     */
    List<String> getAll();

    /**
     * 获取前一节点文本
     * @return
     */
    String prevNodeText();

    /**
     * 获取后一节点文本
     * @return
     */
    String nextNodeText();










}
