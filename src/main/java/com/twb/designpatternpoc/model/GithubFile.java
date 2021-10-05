package com.twb.designpatternpoc.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GithubFile {
    public final String patternName;
    public final String fileName;
    public final InputStream content;
    public final long size;

    public GithubFile(String patternName, String fileName, byte[] bytes, long size) {
        this.patternName = patternName;
        this.fileName = fileName;
        this.content = new ByteArrayInputStream(bytes);
        this.size = size;
    }

    public String getPatternName() {
        return patternName;
    }

    public String getFileName() {
        return fileName;
    }

    public InputStream getContent() {
        return content;
    }

    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "GithubFile{" +
                "patternName='" + patternName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", content=" + content +
                ", size=" + size +
                '}';
    }
}
