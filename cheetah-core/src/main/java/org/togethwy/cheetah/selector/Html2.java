package org.togethwy.cheetah.selector;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/10/24 13:19
 */
public class Html2 implements Selectable {

    private String document;

    private Elements elements;

    private Element element;

    private Node node;


    @Override
    public Selectable $(String selector) {
        CssSelector cssSelector = new CssSelector();
        cssSelector.select(selector);
//        return selectElements(cssSelector);
        return null;
    }

    @Override
    public Selectable css(String selector) {
        return null;
    }

    @Override
    public Selectable regex(String regex) {
        return null;
    }

    @Override
    public Selectable links(String selector) {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public Selectable get(int index) {
        return null;
    }

    @Override
    public List<String> getAll() {
        return null;
    }
}
