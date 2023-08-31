package com.busher.repoinspector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepo {
    private String name;
    private Owner owner;
    private boolean fork;
    private List<GitHubBranch> branches;
    private String url;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GitHubBranch> getBranches() {
        return branches;
    }

    public void setBranches(List<GitHubBranch> branches) {
        this.branches = branches;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "GitHubRepo{" +
                "name='" + name + '\'' +
                ", owner=" + owner +
                '}';
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Owner {
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
