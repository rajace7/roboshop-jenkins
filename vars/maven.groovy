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

        environment {
            NEXUS = credentials('NEXUS')
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
            stage('Release Application') {
                when {
                    expression {
                        env.TAG_NAME ==~ ".*"
                    }
                }
                steps {
                    sh 'mvn package ; cp target/${component}-1.0.jar ${component}.jar'
                    sh 'echo $TAG_NAME >VERSION'
                    sh 'zip -r ${component}-${TAG_NAME}.zip ${component}.jar VERSION'
                    sh 'curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${component}-${TAG_NAME}.zip http://172.31.95.142:8081/repository/${component}/${component}-${TAG_NAME}.zip'

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