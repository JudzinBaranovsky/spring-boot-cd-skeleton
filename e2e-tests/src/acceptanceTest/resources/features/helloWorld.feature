Feature: Aggregation API
  Scenario: client calls the aggregate endpoint
    When the client calls /api/v1/sports/aggregate
    Then the client receives status code of 200
    And the client receives json body {"mostWin":{"team":"Iran","amount":26.0},"mostScoredPerGame":{"team":"Tajikistan","amount":8.0},"leastReceivedPerGame":{"team":"Catalonia","amount":0.0}}
