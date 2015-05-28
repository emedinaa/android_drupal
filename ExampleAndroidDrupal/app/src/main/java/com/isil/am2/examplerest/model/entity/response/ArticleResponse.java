package com.isil.am2.examplerest.model.entity.response;

import java.io.Serializable;

/**
 * Created by emedinaa on 28/05/2015.
 */
public class ArticleResponse implements Serializable {
    private  int nid;
    private  String uri;

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    /*
    {
    "nid": "8",
    "uri": "http://prueba.sotosoft.co/rest/node/8"
}
     */

    @Override
    public String toString() {
        return "ArticleResponse{" +
                "nid=" + nid +
                ", uri='" + uri + '\'' +
                '}';
    }
}
