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
    }
}