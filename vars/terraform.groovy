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

        parameters {
            choice(name: 'env', choices: ['dev', 'prod'], description: 'Pick environment')
        }

        stages {

            stage('Terraform INIT') {
                steps {
                    sh 'terraform init -backend-config=env-${env}/state.tfvars'
                }
            }

            stage('Terraform Apply') {
                steps {
                    sh 'terraform apply -auto-approve -var-file=env-${env}/main.tfvars'
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