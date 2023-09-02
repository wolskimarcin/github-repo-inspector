package com.busher.repoinspector;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GitHubRepoController {
    private final GitHubRepoService repoService;

    public GitHubRepoController(GitHubRepoService repoService) {
        this.repoService = repoService;
    }

    @GetMapping("/repos/{username}")
    public ResponseEntity<?> listRepos(
            @PathVariable String username,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader
    ) {
        if ("application/xml".equals(acceptHeader)) {
            throw new NotAcceptableException("XML format not supported");
        } else if ("application/json".equals(acceptHeader)) {
            try {
                List<GitHubRepo> repos = repoService.listRepos(username);

                for (GitHubRepo repo : repos) {
                    repo.setBranches(List.of(repoService.listBranches(repo.getUrl())));
                }
                return ResponseEntity.ok(repos);
            } catch (UserNotFoundException e) {
                throw new UserNotFoundException("User not found.");
            } catch (Exception e) {
                throw new RuntimeException("Internal Server Error");
            }
        } else {
            throw new UnsupportedAcceptHeaderException("Unsupported 'Accept' header");
        }
    }
}
