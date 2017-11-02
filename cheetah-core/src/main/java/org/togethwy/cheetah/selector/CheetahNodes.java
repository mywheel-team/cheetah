package org.togethwy.cheetah.selector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.togethwy.cheetah.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 元素节点集合
 * TODO 频繁创建对象导致的性能开销
 *
 * @author wangtonghe
 * @date 2017/10/26 16:08
 */
public class CheetahNodes implements Selectable {

    private Elements elements;

    public CheetahNodes() {

    }

    public CheetahNodes(String html){
        this.elements = Jsoup.parse(html).getAllElements();
    }

    public CheetahNodes(Elements elements) {
        this.elements = elements;
    }

    @Override
    public Selectable $(String selectorText) {
        if (elements != null) {
            return new CheetahNodes(elements.select(selectorText));
        }
        return new CheetahNodes();
    }


    @Override
    public List<String> getLinks(String regex) {

        return getUrls("a", "href", regex);
    }

    @Override
    public List<String> getLinks() {
        return getLinks(null);
    }

    @Override
    public String getValue() {
        if (elements == null || elements.size() == 0) {
            return "";
        }
        return elements.get(0).text();
    }

    @Override
    public List<String> getImageUrls() {
        return getUrls("img", "src", null);
    }

    @Override
    public List<String> getImageUrls(String regex) {
        return getUrls("img","src",regex);
    }

    public List<String> getUrls(String type, String attr, String regex) {
        if (elements == null) {
            return null;
        }
        elements = elements.select(type);
        return elements.stream()
                .filter(ele -> ele.hasAttr(attr))
                .map(ele->ele.attr(attr))
                .filter(text -> StringUtils.isEmpty(regex) || !StringUtils.isEmpty(regex) && text.matches(regex))
                .collect(Collectors.toList());

    }


    @Override
    public Selectable get(int index) {
        if (elements == null || elements.size() == 0) {
            return new CheetahNode();
        }
        return new CheetahNode(elements.get(index));
    }

    @Override
    public List<String> getAll() {
        if (elements == null) {
            return new ArrayList<>();
        }
        return elements.stream().map(Element::text).collect(Collectors.toList());
    }

    @Override
    public String prevNodeText() {
        if(elements==null||elements.size()==0){
            return "";
        }
        return elements.get(0).previousSibling().toString();
    }

    @Override
    public String nextNodeText() {
        if(elements==null||elements.size()==0){
            return "";
        }
        return elements.get(0).nextSibling().toString();
    }


}
