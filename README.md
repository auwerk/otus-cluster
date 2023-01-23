# Кластер с ДЗ для OTUS arch

Каталоги:
- demo-service, исходный код сервиса и Dockerfile
- helm-charts, чарты helm

Установка ingress-nginx средствами helm:
```
helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx --create-namespace
```
Настройка сервис-монитора ingress-контроллера:
```
helm upgrade ingress-nginx ingress-nginx/ingress-nginx \
--namespace ingress-nginx \
--set controller.metrics.enabled=true \
--set controller.metrics.serviceMonitor.enabled=true
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

Нагрузка сервиса средствами K6:
```
k6 run ./k6-scripts/users.js --vus=10 --duration=1m
```
