package com.twb.designpatternpoc;

import com.twb.designpatternpoc.model.GithubFile;
import com.twb.designpatternpoc.rest.GithubService;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class DesignPatternMain {

    public static void main(String[] args) throws IOException {

        Instant now = Instant.now();

        GithubService githubService = new GithubService();
        Map<String, List<GithubFile>> githubFiles = githubService.getGithubFiles();

        int size = githubFiles.size();
        System.out.println("size = " + size);

        Duration timeTakenDuration = Duration.between(now, Instant.now());
        System.out.println("timeTakenDuration = " + timeTakenDuration);
    }
}
