#!groovy

pipeline {
    agent any
    stages {
        
        stage('Compile The Code') {
            steps {
                echo '######## -JJ- Compiling ########'
                build job:'Compile Code'
            }
        }
        
            stage('Test The Code') {
            steps {
                echo '######## -JJ- Testing ########'
                build job:'Test'
            }
        }
        
            stage('Deploy The Code') {
            steps {
                echo '######## -JJ- Deploying ########'
                build job:'Deploy'
            }
        }
        
    }
}