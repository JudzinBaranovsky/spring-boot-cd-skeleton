param ($mode)
Write-Host $mode

if (($mode -eq "all") -or ($mode -eq "fluentd"))
{
    helm uninstall fluentd-logging
    kubectl delete -f env/k8s
}

if (($mode -eq "all") -or ($mode -eq "elk"))
{
    docker-compose --project-directory env/docker down
}
