param ($action, $caseName)
Write-Host $action

$stackName = "perf-troubleshooting"

if ($action -eq "start")
{
    Write-Host app/$caseName.env
    docker compose --project-directory app/docker -p $stackName --env-file "app/$caseName.env" up -d
}

if ($action -eq "stop")
{
    docker compose --project-directory app/docker -p $stackName stop
}

if ($action -eq "destroy")
{
    docker compose --project-directory app/docker -p $stackName down -v
}
