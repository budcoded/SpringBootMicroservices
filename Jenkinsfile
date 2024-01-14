pipeline {
    agent any
    stages {
        stage ('Git Pull') {
            steps {
                git url: 'https://github.com/budcoded/SpringBootMicroservices.git',
                branch: 'main'
            }
        }

        stage ('Maven Build API Gateway') {
            steps {
                sh 'cd api-gateway'
                sh 'mvn clean install'
            }
        }

        stage ('Maven Build Discovery Server') {
            steps {
                sh 'cd discovery-server'
                sh 'mvn clean install'
            }
        }

        stage ('Maven Product Service') {
            steps {
                sh 'cd product-service'
                sh 'mvn clean install'
            }
        }

        stage ('Maven Order Service') {
            steps {
                sh 'cd order-service'
                sh 'mvn clean install'
            }
        }

        stage ('Maven Inventory Service') {
            steps {
                sh 'cd inventory-service'
                sh 'mvn clean install'
            }
        }
        stage ('Build docker image') {
            steps {
                sh 'docker build -t budcoded/apigateway:latest ./api-gateway/'
                sh 'docker build -t budcoded/discoveryserver:latest ./discovery-server/'
                sh 'docker build -t budcoded/productservice:latest ./product-service/'
                sh 'docker build -t budcoded/orderservice:latest ./order-service/'
                sh 'docker build -t budcoded/inventoryservice:latest ./inventory-service/'
            }
        }
        stage ('Push docker images') {
            steps {
                sh 'docker login -u budcoded -p budcodedbudcoded'
                sh 'docker push budcoded/apigateway:latest'
                sh 'docker push budcoded/discoveryserver:latest'
                sh 'docker push budcoded/productservice:latest'
                sh 'docker push budcoded/orderservice:latest'
                sh 'docker push budcoded/inventoryservice:latest'
            }
        }
//         stage ('Maven Build') {
//             steps {
//                 sh 'mvn clean install -DskipTests'
//             }
//         }
//         stage ('Build Docker Image') {
//             steps {
// //                 sh 'docker build -t kshitijashah/productmanagementsystem:latest .'
// //                 sh 'docker build -t kshitijashah/productmanagementsystemui:latest ./frontend'
//                 sh 'docker build -t budcoded/productmanagementsystem:latest .'
//                 sh 'docker build -t budcoded/productmanagementsystemui:latest ./frontend'
//             }
//         }
//         stage ('Push Docker Image') {
//             steps {
// //                 sh 'docker login -u kshitijashah -p kshitija@9991'
// //                 sh 'docker push kshitijashah/productmanagementsystem:latest'
// //                 sh 'docker push kshitijashah/productmanagementsystemui:latest'
//                 sh 'docker login -u budcoded -p budcodedbudcoded'
//                 sh 'docker push budcoded/productmanagementsystem:latest'
//                 sh 'docker push budcoded/productmanagementsystemui:latest'
//             }
//         }
//         stage ('Ansible Copy Docker-Compose File') {
//             steps {
//                 sh 'ansible-playbook -i inventory playbook.yml'
//             }
//         }
    }
}