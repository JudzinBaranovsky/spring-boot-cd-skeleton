#!/bin/bash

if [ -z $API_TOKEN ]
then
  echo 'API_TOKEN required'
  exit 1
fi

if [ -z $PACKAGE_TYPE ]
then
  echo 'PACKAGE_TYPE required'
  exit 1
fi

if [ -z $PACKAGE_NAME ]
then
  echo 'PACKAGE_NAME required'
  exit 1
fi

response=$(curl -i --ssl-no-revoke --silent \
            -X DELETE \
            -H "Accept: application/vnd.github+json" \
            -H "Authorization: Bearer $API_TOKEN" \
            -H "X-GitHub-Api-Version: 2022-11-28" \
            https://api.github.com/user/packages/$PACKAGE_TYPE/$PACKAGE_NAME)

status=$(echo $response | awk '{print $2}')
echo $status > output.txt
status=$(echo $status | xargs)

if [ -z "$status" ]
then
  echo "couldn't make a call"
  exit 1
fi

if [ "$status" != "204" ] && [ "$status" != '404' ]
then
  echo "bad status code: $status"
  exit 1
fi
