package org.togethwy.cheetah.selector;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.togethwy.cheetah.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 单个元素节点
 *
 * @author wangtonghe
 * @date 2017/10/26 18:05
 */
public class CheetahNode implements Selectable {

    private Element element;

    public CheetahNode(Element element) {
        this.element = element;
    }

    @Override
    public Selectable $(String selector) {
        if(element==null){
            return null;
        }
        return new CheetahNodes(element.select(selector));
    }

    @Override
    public List<String> getLinks(String regex) {
        return getUrls("a", "href", regex);
    }


    public List<String> getUrls(String type, String attr, String regex) {
        if (element == null) {
            return null;
        }
        Elements elements = element.select(type);
        return elements.stream()
                .filter(ele -> ele.hasAttr(attr))
                .map(ele->ele.attr(attr))
                .filter(text -> StringUtils.isEmpty(regex) || !StringUtils.isEmpty(regex) && text.matches(regex))
                .collect(Collectors.toList());

    }

    @Override
    public List<String> getLinks() {
        return getLinks(null);
    }

    public String getValue() {
        return element==null?"":element.text();
    }

    @Override
    public List<String> getImageUrls() {
        return getUrls("img", "src", null);
    }

    @Override
    public List<String> getImageUrls(String regex) {
        return getUrls("img", "src", regex);
    }

    @Override
    public Selectable get(int index) {
        if (element == null || element.children() == null) {
            return null;
        }
        return new CheetahNode(element.child(0));
    }

    @Override
    public List<String> getAll() {
        if (element == null || element.children() == null) {
            return null;
        }
        return element.children().stream().map(Element::text).collect(Collectors.toList());
    }

    @Override
    public String nextNodeText() {
        if (element == null) {
            return null;
        }
        return element.nextSibling().toString();
    }

    @Override
    public String prevNodeText() {
        if (element == null) {
            return null;
        }
        return element.previousSibling().toString();
    }


}
