package org.bananalaba.springcdtemplate.service;

public interface MessageStore {

    void put(String key, String value);

    String get(String key);

}
