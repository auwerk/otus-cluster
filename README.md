# Кластер с ДЗ для OTUS arch

Каталоги:
- db, ресурсы БД
- grafana, дашборды
- helm-charts, чарты helm
- k6-scripts, скрипты нагрузки

Установка ingress-nginx средствами helm:
```
helm upgrade --install ingress-nginx ingress-nginx --repo https://kubernetes.github.io/ingress-nginx --namespace ingress-nginx --create-namespace
```
Настройка метрик ingress-контроллера:
```
helm upgrade ingress-nginx ingress-nginx --repo https://kubernetes.github.io/ingress-nginx --namespace ingress-nginx --set controller.metrics.enabled=true --set-string controller.podAnnotations."prometheus\.io/scrape"="true" --set-string controller.podAnnotations."prometheus\.io/port"="10254"
```

Для разворачивания монторинга выполнить:
```
helm install prometheus prometheus-community/kube-prometheus-stack -f ./helm-charts/kube-prometheus-stack/values.yaml --atomic --namespace monitoring --create-namespace
```

Для разворачивания БД после выполнить:
```
kubectl create ns db
kubectl apply -f ./db/manifests --namespace db
helm install postgresql-dev -f ./db/values.yaml bitnami/postgresql --namespace db
```

Для разворачивания сервиса выполнить:
```
helm install demo-service ./helm-charts/demo-service --namespace otus --create-namespace
```

Для тестирования запросов к сервису можно использовать Postman. Файлы коллекций:
- OtusHealth.postman_collection.json
- OtusUser.postman_collection.json

Нагрузка сервиса средствами K6:
```
k6 run ./k6-scripts/users.js --vus=10 --duration=1m
```
