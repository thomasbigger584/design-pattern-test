package com.twb.designpatternpoc;

import com.twb.designpatternpoc.model.GithubFile;
import com.twb.designpatternpoc.rest.GithubService;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class DesignPatternMain {

    public static void main(String[] args) throws IOException {
        GithubService githubService = new GithubService();
        Map<String, List<GithubFile>> githubFiles = githubService.getGithubFiles();


        for (Map.Entry<String, List<GithubFile>> githubFilesEntry : githubFiles.entrySet()) {
            System.out.println("githubFilesEntry = " + githubFilesEntry);

            for (GithubFile githubFile : githubFilesEntry.getValue()) {
                try (InputStream content = githubFile.getContent()) {
                    String fileAsString = IOUtils.toString(content, StandardCharsets.UTF_8);
                    System.out.println("fileAsString = " + fileAsString);
                }
            }
        }
    }
}
