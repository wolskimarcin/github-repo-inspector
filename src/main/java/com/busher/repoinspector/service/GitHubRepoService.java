package com.busher.repoinspector.service;

import com.busher.repoinspector.exception.GitHubApiException;
import com.busher.repoinspector.exception.UserNotFoundException;
import com.busher.repoinspector.model.GitHubBranch;
import com.busher.repoinspector.model.GitHubRepo;
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

    private static final String GITHUB_API_URL = "https://api.github.com/users/";

    @Value("${github.api.token}")
    private String githubApiToken;

    @Autowired
    private RestTemplate restTemplate;

    public List<GitHubRepo> listRepos(String username) {
        HttpHeaders headers = createHeadersWithToken();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GitHubRepo[]> response;
        try {
            response = restTemplate.exchange(
                    GITHUB_API_URL + username + "/repos",
                    HttpMethod.GET,
                    entity,
                    GitHubRepo[].class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not found.");
        }

        if (response.getStatusCode() == HttpStatus.OK) {
            GitHubRepo[] repos = response.getBody();
            if (repos != null) {
                return filterOutForks(repos);
            } else {
                throw new GitHubApiException("GitHub API returned null for repositories.");
            }
        } else {
            throw new GitHubApiException("Failed to fetch GitHub repositories.");
        }
    }

    public GitHubBranch[] listBranches(String url) {
        HttpHeaders headers = createHeadersWithToken();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GitHubBranch[]> response;
        try {
            response = restTemplate.exchange(
                    url + "/branches",
                    HttpMethod.GET,
                    entity,
                    GitHubBranch[].class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Branch not found.");
        }

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new GitHubApiException("Failed to fetch GitHub branches.");
        }
    }

    private HttpHeaders createHeadersWithToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(githubApiToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
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
}
