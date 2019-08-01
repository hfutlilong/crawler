package com.netease.music.service;

import java.util.List;

public interface NLPService {
    void testHanNlp();

    List<String> getKeywords(String sentence);
}
