package com.twb.designpatternpoc.rest;

import com.twb.designpatternpoc.model.GithubFile;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GithubService {
    private static final String GITHUB_ENDPOINT = "https://github.com/";

    private final GitHubAPI gitHubAPI;

    public GithubService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHUB_ENDPOINT)
                .build();
        gitHubAPI = retrofit.create(GitHubAPI.class);
    }

    public Map<String, List<GithubFile>> getGithubFiles() throws IOException {
        Response<ResponseBody> response = gitHubAPI.downloadLatestZip().execute();

        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body == null) {
                throw new IOException("Successful but body is null");
            }

            Map<String, List<GithubFile>> patternMap = new HashMap<>();
            try (ZipInputStream zis = new ZipInputStream(body.byteStream())) {
                ZipEntry entry;
                int offset = 0;
                while ((entry = zis.getNextEntry()) != null) {
                    int entrySize = (int) entry.getSize();


                    //todo read the files from ZipInputStream correctly, this throws an error
                    byte[] bytes = new byte[entrySize];
                    zis.read(bytes, offset, entrySize);
                    offset = entrySize + 1;


                    if (shouldSkipEntry(entry)) {
                        continue;
                    }

                    String entryName = entry.getName();
                    String name = entryName.replace("java-design-patterns-master/", "");
                    String[] nameSplit = name.split("/");
                    String patternName = nameSplit[0];
                    String fileName = nameSplit[nameSplit.length - 1];

                    GithubFile githubFile = new GithubFile(patternName, fileName, bytes, entrySize);
                    if (patternMap.containsKey(patternName)) {
                        patternMap.get(patternName).add(githubFile);
                    } else {
                        List<GithubFile> githubFileList = new ArrayList<>();
                        githubFileList.add(githubFile);
                        patternMap.put(patternName, githubFileList);
                    }
                }
            }
            return patternMap;
        }
        throw new IOException("Error occurred fetching zip file: " + response.toString());
    }

    private static boolean shouldSkipEntry(ZipEntry entry) {
        Pattern pattern = Pattern.compile("java-design-patterns-master\\/.*\\/src\\/main\\/java\\/com\\/iluwatar\\/.*\\.java", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(entry.getName());
        return entry.isDirectory() || !matcher.matches();
    }
}
