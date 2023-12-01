def bugs = null
def codeSmells = null
def vulnerabilities = null

pipeline {
    agent any // Пайплайн может запускаться на любом доступном агенте

    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'Branch to build')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'stage', 'prod'], description: 'Environment to deploy')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests?')
    }

    environment {
        // Определение переменных среды, используемых во всем пайплайне
        MAVEN_HOME = '/usr/share/apache-maven'
        JAVA_HOME = '/usr/lib/jvm/java-11-amazon-corretto.x86_64'
        PROFILE_NAME = 'AdvancedCalculatorV2Test' // Значение по умолчанию
        SERVER_IP = "107.21.90.96"
        SSH_USER = "ubuntu"
    }

    stages {

        stage('Инициализация') {
            steps {
                echo 'Проверка версии Java...'
                sh '${JAVA_HOME}/bin/java -version'

                echo 'Очистка рабочего пространства...'
                cleanWs() // Очистка workspace перед началом работы

                //echo 'Подготовка конфигурационных файлов...'
                //sh 'cp -r ./config $WORKSPACE/config'

                //echo 'Установка зависимостей...'
                //sh '${MAVEN_HOME}/bin/mvn -Pprofile-name clean dependency:resolve'

                echo 'Информация о ветке и коммите...'
                sh 'echo "Сборка запущена с коммита: ${GIT_COMMIT}"'
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Сборка') {
            steps {
                echo 'Сборка приложения...'
                sh 'pwd'
                sh 'ls -l'
                sh 'mvn clean install'
            }
        }

        stage('Тестирование') {
            steps {
                echo 'Запуск тестов...'

                script {
                    if (env.BRANCH_NAME != 'main') {
                        PROFILE_NAME = 'AdvancedCalculatorJUnitTest'
                    }
                    sh '${MAVEN_HOME}/bin/mvn test -P${PROFILE_NAME}'
                    //sh "mvn clean test -P${PROFILE_NAME}"
                }
            }
        }

        stage('Анализ кода') {
                    steps {
                        echo 'Анализ качества кода...'
                        sh 'mvn sonar:sonar \
                              -Dsonar.projectKey=AdvancedCalculator \
                              -Dsonar.host.url=http://3.82.161.17:9000 \
                              -Dsonar.login=42c4b797b4d3139192c0f73db24c36a70b8a11aa'
                    }
        }

        stage('Get SonarQube Data') {
            steps {
                script {
                    def sonarData = sh(script: "curl -u 42c4b797b4d3139192c0f73db24c36a70b8a11aa: 'http://3.82.161.17:9000/api/measures/component?component=AdvancedCalculator&metricKeys=bugs,vulnerabilities,code_smells'", returnStdout: true).trim()
                    def jsonResponse = new groovy.json.JsonSlurper().parseText(sonarData)
                    def measures = jsonResponse.component.measures

                    bugs = measures.find { it.metric == 'bugs' }?.value ?: 'Нет данных'
                    codeSmells = measures.find { it.metric == 'code_smells' }?.value ?: 'Нет данных'
                    vulnerabilities = measures.find { it.metric == 'vulnerabilities' }?.value ?: 'Нет данных'
                }
            }
        }

        stage('Deploy') {
            steps {
                // Deploying to the server
                echo 'Deploying..'
                sshagent(['ssh-key']) { // SSH Key credential ID in Jenkins
                    // Copy files to the server
                    sh "scp -o StrictHostKeyChecking=no /var/lib/jenkins/workspace/Calc ${SSH_USER}@${SERVER_IP}:~/."
                }
            }
        }
    }

    post {

            //always {
            //    echo 'Очистка после сборки...'
            //    sh 'pwd'
            //    sh 'ls -l'
            //    sh 'ls -la test-output/' // Проверьте папку target для Maven
            //    sh 'ls -la target/surefire-reports/' // Проверьте папку target для Maven
            //    sh 'ls -la' // Проверьте корневой каталог проекта
            //    junit '**/target/surefire-reports/testng-results.xml' // для Maven
            //    junit '/test-output/emailable-report.html' // для TestNG
            //    //cleanWs() // Очистка workspace после завершения работы
            //}

            success {
                script {
                    echo 'Сборка успешно завершена.'
                    def message = "Успешно: ${env.JOB_NAME} [${env.BUILD_NUMBER}] \n" +
                                  "Ссылка: ${env.BUILD_URL} \n" +
                                  "Длительность: ${currentBuild.durationString} \n" +
                                  "Результаты сборки: ${currentBuild.currentResult} \n" +
                                  "Bugs: ${bugs}\n" +
                                  "Code Smells: ${codeSmells}\n" +
                                  "Vulnerabilities: ${vulnerabilities}"
                    sh "curl -s -X POST https://api.telegram.org/bot6791948017:AAE9Thrt41vGXMglNFmR9WZbJ2O9SNX-1dE/sendMessage -d chat_id=671562924 -d text='${message}'"
                }
            }
            failure {
                script {
                    echo 'Сборка не удалась.'
                    sh 'mvn dependency:purge-local-repository -DreResolve=false'
                    def message = "Ошибка: ${env.JOB_NAME} [${env.BUILD_NUMBER}] \n" +
                                                  "Причина: ${currentBuild.currentResult} \n" +
                                                  "Ссылка: ${env.BUILD_URL}"
                    sh "curl -s -X POST https://api.telegram.org/bot6791948017:AAE9Thrt41vGXMglNFmR9WZbJ2O9SNX-1dE/sendMessage -d chat_id=671562924 -d text='${message}'"
                }
            }
            unstable {
                script {
                    echo 'Сборка завершена, но с предупреждениями.'
                    sh "curl -s -X POST https://api.telegram.org/bot6791948017:AAE9Thrt41vGXMglNFmR9WZbJ2O9SNX-1dE/sendMessage -d chat_id=671562924 -d text='Сборка завершена, но с предупреждениями: ${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
                }
            }
        }
}