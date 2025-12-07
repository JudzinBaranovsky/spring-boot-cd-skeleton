param ($action)
Write-Host $action

if (($action -eq "recreate") -or ($action -eq "destroy"))
{
    docker compose --project-directory app/docker down -v
    docker compose --project-directory env/docker down -v
    docker compose --project-directory env/docker --profile setup down -v
}

if (($action -eq "recreate") -or ($action -eq "setup"))
{
    docker compose --project-directory env/docker --env-file env/docker/.env up setup -d
}

if (($action -eq "recreate") -or ($action -eq "start"))
{
    docker compose --project-directory env/docker up -d
}

if (($action -eq "stop"))
{
    docker compose --project-directory app/docker stop
    docker compose --project-directory env/docker stop
}
