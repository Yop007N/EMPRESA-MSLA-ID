param(
    [ValidateSet("unit", "all")]
    [string]$Mode = "unit",

    [string]$JavaHome = "C:\Program Files\Java\jdk-25.0.2",

    [switch]$StartPostgres
)

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $projectRoot

if (-not (Test-Path ".\mvnw.cmd")) {
    Write-Error "No se encontro .\mvnw.cmd. Ejecuta este script desde la raiz del proyecto."
    exit 1
}

if (-not (Test-Path $JavaHome)) {
    Write-Error "JAVA_HOME no existe: $JavaHome"
    exit 1
}

$env:JAVA_HOME = $JavaHome
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

Write-Host "JAVA_HOME: $env:JAVA_HOME"
& .\mvnw.cmd -version

if ($Mode -eq "all" -and $StartPostgres) {
    Write-Host "Levantando PostgreSQL con Docker Compose..."
    docker compose up -d postgres | Out-Host
}

if ($Mode -eq "unit") {
    $mavenArgs = @("-Dtest=ExchangeServiceTest", "test")
    Write-Host "Ejecutando tests unitarios de componente: ExchangeServiceTest"
} else {
    $mavenArgs = @("test")
    Write-Host "Ejecutando suite completa de tests"
}

& .\mvnw.cmd @mavenArgs
$exitCode = $LASTEXITCODE

Write-Host ""
Write-Host "Reportes surefire en: $projectRoot\target\surefire-reports"

if ($exitCode -ne 0) {
    Write-Error "Fallo la ejecucion de tests (exit code: $exitCode)."
    exit $exitCode
}

Write-Host "Tests finalizados correctamente."
exit 0

