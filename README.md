# Кластер с ДЗ для OTUS arch

Каталоги:
- demo-service, исходный код сервиса и Dockerfile
- helm-charts, чарты helm

Для функционрования ingress в minikube нужно включить аддон:
```
minikube addons enable ingress
```
Хост arch.homework должен быть добавлен в /etc/hosts c IP адресом minikube

Для разворачивания монторинга выполнить:
```
helm install prometheus prometheus-community/kube-prometheus-stack -f ./monitoring/prometheus.yaml --atomic
```

Для разворачивания БД после выполнить:
```
kubectl apply -f ./db/manifests
helm install postgresql-dev -f ./db/values.yaml bitnami/postgresql
```

Для разворачивания сервиса выполнить:
```
helm install demo-service ./helm-charts/demo-service
```

Для тестирования запросов к сервису можно использовать Postman. Файлы коллекций:
- OtusHealth.postman_collection.json
- OtusUser.postman_collection.json
