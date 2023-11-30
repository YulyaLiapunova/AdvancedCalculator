pipeline {
    agent any // Пайплайн может запускаться на любом доступном агенте

    environment {
        // Определение переменных среды, используемых во всем пайплайне
        MAVEN_HOME = '/usr/share/apache-maven'
        JAVA_HOME = '/usr/lib/jvm/java-11-amazon-corretto.x86_64'
        PROFILE_NAME = 'AdvancedCalculatorV2Test' // Значение по умолчанию
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

        stage('Сборка') {
            steps {
                echo 'Сборка приложения...'
                sh '${MAVEN_HOME}/bin/mvn -f /var/lib/jenkins/workspace/Project@2/pom.xml clean install'
            }
        }

        stage('Тестирование') {
            steps {
                echo 'Запуск тестов...'
                //sh '${MAVEN_HOME}/bin/mvn -f /var/lib/jenkins/workspace/Project@2/pom.xml test'

                script {
                    if (env.BRANCH_NAME != 'main') {
                        PROFILE_NAME = 'AdvancedCalculatorJUnitTest'
                    }
                    sh '${MAVEN_HOME}/bin/mvn -f /var/lib/jenkins/workspace/Project@2/pom.xml test -P${PROFILE_NAME}'
                    //sh "mvn clean test -P${PROFILE_NAME}"
                }
            }
        }


        stage('Анализ кода') {
            steps {
                echo 'Анализ качества кода...'
                sh '${MAVEN_HOME}/bin/mvn sonar:sonar'
            }
        }

        stage('Деплой') {
            steps {
                echo 'Развертывание приложения...'
                // Здесь может быть скрипт для деплоя или использование maven
                sh '${MAVEN_HOME}/bin/mvn deploy'
            }
        }
    }

    post {
        always {
            echo 'Очистка после сборки...'
            cleanWs() // Очистка workspace после завершения работы
        }

        success {
            echo 'Сборка успешно завершена.'
        }

        failure {
            echo 'Сборка не удалась.'
            // Здесь можно добавить уведомления или действия в случае неудачной сборки
        }

        unstable {
            echo 'Сборка завершена, но с предупреждениями.'
            // Действия для сборок, которые завершились, но имеют предупреждения (например, неудачные тесты)
        }
    }
}