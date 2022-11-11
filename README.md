# Кластер с ДЗ для OTUS arch

Каталоги:
- demo-service, исходный код сервиса и Dockerfile
- kube-manifests, манифесты для Kubernetes

Для функционрования ingress в minikube нужно включить аддон:
```
minikube addons enable ingress
```
Хост arch.homework должен быть добавлен в /etc/hosts c IP адресом minikube

Для тестирования запросов к сервису можно использовать Postman с файлом коллекции из репозитория (OtusHealth.postman_collection.json)
