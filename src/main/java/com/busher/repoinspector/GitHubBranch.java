package com.busher.repoinspector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubBranch {
    private String name;
    private GitHubCommit commit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GitHubCommit getCommit() {
        return commit;
    }

    public void setCommit(GitHubCommit commit) {
        this.commit = commit;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class GitHubCommit {
    private String sha;
    private String url;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}