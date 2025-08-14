# config
$projectName = "WebApplication1"
$pemKeyPath  = "../../../Matcha.pem"
$ec2User     = "ec2-user"
$ec2Host     = "51.21.228.104"

# paths
$projectPath   = Join-Path $PSScriptRoot $projectName
$publishFolder = Join-Path $projectPath "bin\Release\net8.0\linux-x64\publish"
$zipFilePath   = Join-Path $PSScriptRoot "app.zip"

# deploy logic
try {
    Write-Output "Incepe procesul de deploy pentru '$projectName'..."

    Write-Output "[1/3] Se publica aplicatia..."
    dotnet publish $projectPath --configuration Release --runtime linux-x64 --self-contained false
    if ($LASTEXITCODE -ne 0) {
        throw "Eroare la publicarea proiectului .NET."
    }
    Write-Output "Publicare finalizata."

    Write-Output "[2/3] Se arhiveaza fisierele..."
    Compress-Archive -Path "$publishFolder\*" -DestinationPath $zipFilePath -Force
    Write-Output "Arhivare finalizata: $zipFilePath"

    Write-Output "[3/3] Se transfera arhiva catre $ec2Host..."
    scp -i $pemKeyPath $zipFilePath "${ec2User}@${ec2Host}:~/"
    if ($LASTEXITCODE -ne 0) {
        throw "Eroare la transferul fisierului prin SCP."
    }
    Write-Output "Transfer finalizat."

    Write-Output "Deploy finalizat cu succes."
}
catch {
    Write-Error "EROARE: A apărut o problemă în timpul procesului de deploy."
    Write-Error $_.Exception.Message
}
