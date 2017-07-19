package org.togethwy.cheetah.selector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/7/8 20:36
 */
public class Html implements Selector{

    private String document;

    private Elements elements;

    private Element element;

    private Node node;


    public Html() {


    }

    public Html(String document) {
        this.document = document;
        this.elements = Jsoup.parse(this.document).getAllElements();
    }


    public Html(String document, Elements elements) {
        this.document = document;
        this.elements = elements;
    }
    private Html(String document,Element element){
        this.document = document;
        this.element = element;
    }

    public Html(String document, Elements elements, Element element, Node node) {
        this.document = document;
        this.elements = elements;
        this.element = element;
        this.node = node;
    }


    @Override
    public Html $(String selector) {
        if(this.document!=null){
            if(elements!=null) {
                return new Html(this.document, this.elements.select(selector));
            }else if(element!=null){
                return new Html(this.document,element.select(selector));
            }else{
                return new Html();
            }
        }
        return new Html();

    }

    //TODO 待实现
    @Override
    public Selector regex(String regex) {
        return null;

    }

    @Override
    public Selector get(int index) {
        if(elements!=null&&elements.size()>=index+1){
            return new Html(document,elements.get(index));
        }
        return new Html();

    }



    public List<String> getLinks(String selector) {
        this.elements = Jsoup.parse(document).select(selector).select("a");
        List<String> list = new ArrayList<>();
        for (Element element : elements) {
            list.add(element.attr("href"));
        }
        return list;
    }

    @Override
    public Selector select(String selector) {
       return  $(selector);
    }

    @Override
    public List<String> getLinks() {

        if(elements==null&&element==null){
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();

        if(elements==null){
            elements = element.select("a");
            for (Element element : elements) {
                list.add(element.attr("href"));
            }
        }else{
            elements = elements.select("a");
            for (Element element : elements) {
                list.add(element.attr("href"));
            }
        }

        return list;
    }

    @Override
    public List<String> getImgUrls() {
        return getUrls("img");
    }

    @Override
    public List<String> getVideoUrls() {
        return getUrls("video");
    }


    private List<String> getUrls(String type) {
        if(elements==null&&element==null){
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();

        if(elements==null){
            elements = element.select(type);
            for (Element element : elements) {
                list.add(element.attr("src"));
            }
        }else{
            elements = elements.select(type);
            for (Element element : elements) {
                list.add(element.attr("src"));
            }
        }

        return list;
    }


    @Override
    public String getValue() {
        if (element == null&&elements==null) {
            return null;
        }
        if(element==null){
            return elements.size()>0?elements.get(0).text():null;
        }
        return element.text();
    }

    @Override
    public List<String> getAll() {
        if (elements == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        elements.forEach((e) -> list.add(e.text()));
        return list;
    }

    @Override
    public String nextNodeText(){
        if(element==null){
            return null;
        }
        return element.nextSibling().toString();
    }

    @Override
    public String prevNodeText(){
        if(element==null){
            return null;
        }
        return element.previousSibling().toString();
    }

}
