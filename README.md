# github-repo-inspector

API service that allows you to inspect and analyze GitHub repositories.

## Acceptance Criteria

### List User Repositories (Excluding Forks)

- **Request Header**: `Accept: application/json`
- **Response Data**:
    - Repository Name
    - Owner Login
    - For each branch: Name and Last Commit SHA

### Handling Non-Existing GitHub User

- **Scenario**: When a non-existing GitHub user is provided.
- **Response**: 404 status with a JSON format response:
    ```json
    {
        "status": 404,
        "message": "User not found."
    }
    ```

### Handling Invalid Request Header

- **Scenario**: When an invalid request header like `Accept: application/xml` is used.
- **Response**: 406 status with a JSON format response:
    ```json
    {
        "status": 406,
        "message": "Not Acceptable. Please use 'Accept: application/json'."
    }
    ```

## Getting Started

### Prerequisites

- Java 17+
- Spring Boot 3+
- GitHub API access token

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/wolskimarcin/github-repo-inspector.git
   cd github-repo-inspector
   ```

2. Build and run the application:

   ```bash
   mvn clean install
   java -jar target/github-repo-inspector.jar
   ```

### Configuration

Set your GitHub API access token inside application.properties:

```
github.api.token=YOUR_TOKEN
```

## Example usage

```bash
curl -H "Accept: application/json" http://localhost:8080/repos/wolskimarcin | jq

[
  {
    "name": "github-repo-inspector",
    "owner": {
      "login": "wolskimarcin"
    },
    "fork": false,
    "branches": [
      {
        "name": "develop",
        "commit": {
          "sha": "6e8ed15c80b5e53896a82dc06c1cce68e759683f"
        }
      },
      {
        "name": "master",
        "commit": {
          "sha": "d1821ff1e61170d97050bfb8386f4150ab6c3e22"
        }
      }
    ],
    "url": "https://api.github.com/repos/wolskimarcin/github-repo-inspector"
  },
  {
    "name": "Python_2022-23",
    "owner": {
      "login": "wolskimarcin"
    },
    "fork": false,
    "branches": [
      {
        "name": "master",
        "commit": {
          "sha": "8f43da2b0c27a12187fc1983a7401c2f2940018c"
        }
      }
    ],
    "url": "https://api.github.com/repos/wolskimarcin/Python_2022-23"
  },
  {
    "name": "TicTacToe",
    "owner": {
      "login": "wolskimarcin"
    },
    "fork": false,
    "branches": [
      {
        "name": "master",
        "commit": {
          "sha": "977cc139999562cc7c3feb0edec623dd9723a2e6"
        }
      }
    ],
    "url": "https://api.github.com/repos/wolskimarcin/TicTacToe"
  },
  {
    "name": "UInterpolation",
    "owner": {
      "login": "wolskimarcin"
    },
    "fork": false,
    "branches": [
      {
        "name": "master",
        "commit": {
          "sha": "d88183b75937b9015adf644bb1f1292b1f585a8e"
        }
      }
    ],
    "url": "https://api.github.com/repos/wolskimarcin/UInterpolation"
  }
]


```
---
