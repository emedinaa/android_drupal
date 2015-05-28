package com.isil.am2.examplerest.model.entity.response;

import java.io.Serializable;

/**
 * Created by emedinaa on 19/05/15.
 */
public class LoginResponse implements Serializable {

    /*
    response {"sessid":"PSBNJimD3IAdWrXRXQE4cRJzovpdnkdxKz_0drzi_6o","session_name":"SESS48b6dad5828ad504d2cb7726169211ef","token":"5I-AY3iynvqBdVKh282QdqSi5TSE2jz6myU-m38A-kY","user":{"uid":"1","name":"admin","mail":"mauro1891@live.com","theme":"","signature":"","signature_format":null,"created":"1431881923","access":"1432788217","login":1432788669,"status":"1","timezone":"America/New_York","language":"","picture":null,"init":"mauro1891@live.com","data":false,"roles":{"2":"authenticated user","3":"administrator"},"rdf_mapping":{"rdftype":["sioc:UserAccount"],"name":{"predicates":["foaf:name"]},"homepage":{"predicates":["foaf:page"],"type":"rel"}}}}
    */

    private  String sessid;
    private  String session_name;
    private  String token;

    private String cookie;

    public String getSessid() {
        return sessid;
    }

    public void setSessid(String sessid) {
        this.sessid = sessid;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCookie() {
        return this.sessid+"="+this.session_name;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
