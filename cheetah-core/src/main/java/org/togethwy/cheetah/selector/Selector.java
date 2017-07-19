package org.togethwy.cheetah.selector;

import java.util.List;

/**
 * 选择器
 * @author wangtonghe
 * @date 2017/7/11 09:50
 */
public interface Selector {

    /**
     * jquery格式选择器,$
     * @param selector 用来查找的字符串
     * @return
     */
    Selector $(String selector);

    /**
     * 获取所有a标签链接地址
     * @param selector 用来查找的字符串
     * @return
     */
    List<String> getLinks(String selector);

    /**
     * jquery格式选择器,selector
     * @param selector 用来查找的字符串
     * @return
     */
    Selector select(String selector);



    /**
     * 正则选择器
     *
     * @param regex
     * @return
     */
    Selector regex(String regex);

    /**
     * 获取指定索引位置元素
     *
     * @param index
     * @return
     */
    Selector get(int index);

    /**
     * 获取元素值
     *
     * @return 元素值
     */
    String getValue();


    /**
     * 获取选择元素所有值
     *
     * @return 元素列表值
     */
    List<String> getAll();


    /**
     * 获取所有a链接
     * @return
     */
    List<String> getLinks();

    /**
     * 获取所有图片url
     * @return
     */
    List<String> getImgUrls();

    /**
     * 获取所有视频url
     * @return
     */
    List<String> getVideoUrls();



    String nextNodeText();

    String prevNodeText();

}
