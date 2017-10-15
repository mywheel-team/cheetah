package org.togethwy.cheetah.entity;

import org.togethwy.cheetah.downloader.RequestMethod;

import java.util.Map;

/**
 * @author wangtonghe
 * @date 2017/10/15 18:52
 */
public class JsonConfigEntity {
    private String jsonUrl;
    private RequestMethod requestMethod;
    private Map<String,String> paramMap;

}
