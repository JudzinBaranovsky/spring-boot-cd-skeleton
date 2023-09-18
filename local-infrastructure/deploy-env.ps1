docker-compose --project-directory env/docker up -d
kubectl apply -f env/k8s
helm repo add kiwigrid https://kiwigrid.github.io
helm install fluentd-logging kiwigrid/fluentd-elasticsearch -f env/helm/fluentd-daemonset-values.yaml

function Initialize-Logstash-Index-Pattern
{
    try
    {
        $Response = Invoke-WebRequest -Uri 'http://localhost:5601/api/saved_objects/index-pattern' `
        -Method POST `
        -ContentType 'application/json; charset=utf-8' `
        -InFile 'env/config/logstash-index-pattern.json' `
        -Headers @{ 'kbn-xsrf' = 'reporting' }

        if ($Response.StatusCode -eq '200')
        {
            return $true
        }
        else
        {
            return $false
        }
    }
    catch
    {
        Write-Host $_
        return $false
    }
}

$attempts = 5
$indexPatternCreated = $false
Write-Host 'Creating a Logstash index pattern'
while (($attempts -gt 0) -and -not $indexPatternCreated)
{
    Start-Sleep 30

    $attempts--
    $indexPatternCreated = Initialize-Logstash-Index-Pattern
    Write-Host "Attempts to create Logstash index pattern left $attempts"
}

if ($indexPatternCreated)
{
    Write-Host 'Logstash index pattern created'
}
else
{
    Write-Host 'Failed to create Logstash index pattern'
}
