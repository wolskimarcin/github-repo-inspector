package com.busher.repoinspector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GitHubRepoController {

    @Autowired
    private GitHubRepoService repoService;

    @GetMapping("/repos/{username}")
    public ResponseEntity<?> listRepos(
            @PathVariable String username,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader
    ) {
        if ("application/xml".equals(acceptHeader)) {
            // Handle XML response
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("XML format not supported");
        } else if ("application/json".equals(acceptHeader)) {
            // Handle JSON response
            List<GitHubRepo> repos = repoService.listRepos(username);

            // TODO: Get branches info.
            List<String> response = new ArrayList<>();
            for (GitHubRepo repo : repos) {
                response.add("%s\n%s\n".formatted(repo.getName(), repo.getOwner().getLogin()));
            }
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Unsupported 'Accept' header");
        }
    }
}
