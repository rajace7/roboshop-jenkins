def call() {
    pipeline {

        agent {
            node {
                label 'rajesh_workstation'
            }
        }

        options {
            ansiColor('xterm')
        }

        stages {

            stage('Code Compile') {
                steps {
                    sh 'mvn compile'
                }
            }
            stage('Code Quality') {
                steps {
                    sh 'echo Code Quality'
                   // sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.83.235:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true -Dsonar.java.binaries=./target'
                }
            }

            stage('Unit Test Cases') {
                steps {
                    sh 'echo Unit tests'

                }
            }

            stage('CheckMarx SAST Scan') {
                steps {
                    sh 'echo Checkmarx Scan'
                }
            }

            stage('CheckMarx SCA Scan') {
                steps {
                    sh 'echo Checkmarx SCA Scan'
                }
            }


        }

        post {
            always {
                cleanWs()
            }
        }

    }


}