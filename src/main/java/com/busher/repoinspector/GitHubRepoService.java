package com.busher.repoinspector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubRepoService {

    private final String githubApiUrl = "https://api.github.com/users/";
    @Value("${github.api.token}")
    private String githubApiToken;
    @Autowired
    private RestTemplate restTemplate;

    public List<GitHubRepo> listRepos(String username) {
        // Create headers with Accept: application/json
        HttpHeaders headers = new HttpHeaders();
        System.out.println("Token:" + githubApiToken);
        headers.setBearerAuth(githubApiToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make a GET request to GitHub API to retrieve user's repositories
        ResponseEntity<GitHubRepo[]> response;
        try {
            response = restTemplate.exchange(
                    githubApiUrl + username + "/repos",
                    HttpMethod.GET,
                    entity,
                    GitHubRepo[].class
            );
        } catch (HttpClientErrorException.NotFound e) {
            // Handle the case where the user doesn't exist
            throw new UserNotFoundException("User not found.");
        }

        if (response.getStatusCode() == HttpStatus.OK) {
            GitHubRepo[] repos = response.getBody();
            return filterOutForks(repos);
        } else {
            throw new GitHubApiException("Failed to fetch GitHub repositories.");
        }
    }

    private List<GitHubRepo> filterOutForks(GitHubRepo[] repos) {
        List<GitHubRepo> filteredRepos = new ArrayList<>();
        for (GitHubRepo repo : repos) {
            if (!repo.isFork()) {
                filteredRepos.add(repo);
            }
        }
        return filteredRepos;
    }

    public GitHubBranch[] listBranches(String url) {
        // Create headers with Accept: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(githubApiToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make a GET request to GitHub API to retrieve repo's branches
        ResponseEntity<GitHubBranch[]> response;
        try {
            response = restTemplate.exchange(
                    url + "/branches",
                    HttpMethod.GET,
                    entity,
                    GitHubBranch[].class
            );
        } catch (HttpClientErrorException.NotFound e) {
            // Handle the case where the branch doesn't exist
            //throw new BranchNotFoundException("Branch not found.");
            throw new RuntimeException("Branch not found.");
        }

        if (response.getStatusCode() == HttpStatus.OK) {
            GitHubBranch[] branches = response.getBody();
            return branches;
        } else {
            throw new GitHubApiException("Failed to fetch GitHub branches.");
        }

    }
}
