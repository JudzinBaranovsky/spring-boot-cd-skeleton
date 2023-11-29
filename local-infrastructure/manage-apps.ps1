param ($action)
Write-Host $action

if ($action -eq "start")
{
    docker compose --project-directory app/docker up -d
}

if ($action -eq "stop")
{
    docker compose --project-directory app/docker stop
}

if ($action -eq "destroy")
{
    docker compose --project-directory app/docker down -v
}
