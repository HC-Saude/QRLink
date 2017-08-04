package com.happycode.qrlink;

/**
 * Created by lucas on 03/08/17.
 */

//classe criada para representar os itens da db

public class dbEntry {

    String _id;
    String _local;
    String _url;

    public dbEntry(String id, String local, String url){
        this._id=id;
        this._local=local;
        this._url=url;
    }

    public String getId(){
        return this._id;
    }
    public String getLocal(){
        return this._local;
    }
    public String getUrl(){
        return this._url;
    }

    public void setId(String id){
        this._id=id;
    }
    public void setLocal(String local){
        this._local=local;
    }
    public void setUrl(String url){
        this._url=url;
    }

}
