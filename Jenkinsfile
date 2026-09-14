pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out ShopEase source code...'
            }
        }

        stage('Maven Build') {
            steps {
                echo 'Building ShopEase with Maven...'
                bat 'mvnw.cmd clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running unit tests...'
               bat 'mvnw.cmd test -Dtest=!ShopEaseSeleniumTest'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging ShopEase application...'
                bat 'mvnw.cmd package -DskipTests'
            }
        }

        stage('Start ShopEase') {
            steps {
                echo 'Starting ShopEase application...'

                bat '''
                    start "ShopEase" /B cmd /c "mvnw.cmd spring-boot:run > shopease.log 2>&1"
                '''

                echo 'Waiting for ShopEase to start...'

                bat '''
                    powershell -NoProfile -Command ^
                    "$ready=$false; for($i=0;$i -lt 30;$i++){ try { $r=Invoke-WebRequest -Uri 'http://localhost:8084' -UseBasicParsing -TimeoutSec 2; if($r.StatusCode -eq 200){$ready=$true; break} } catch {}; Start-Sleep -Seconds 2 }; if(-not $ready){ Get-Content shopease.log -ErrorAction SilentlyContinue; exit 1 }"
                '''
            }
        }

        stage('Selenium Tests') {
            steps {
                echo 'Running Selenium browser tests...'
                bat 'mvnw.cmd test -Dtest=ShopEaseSeleniumTest'
            }
        }
    }

    post {
        always {
            echo 'Stopping ShopEase application...'

            bat '''
                powershell -NoProfile -Command ^
                "$pids=(Get-NetTCPConnection -LocalPort 8084 -State Listen -ErrorAction SilentlyContinue).OwningProcess; if($pids){$pids | ForEach-Object { Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue }}"
            '''
        }

        success {
            echo 'ShopEase CI pipeline completed successfully!'
        }

        failure {
            echo 'ShopEase CI pipeline failed!'
        }
    }
}