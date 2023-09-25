Feature: Hello World endpoint should be available
  Scenario: client calls GET /hello
    When the client calls /api/v1/sample/hello
    Then the client receives status code of 200
    And the client receives json body {"message": "Hello, World"}
