#/bin/sh

kubectl create configmap postgres-config --from-literal=postgres.db.name=mydb
kubectl create secret generic db-security --from-literal=db.user.name=matthias --from-literal=db.user.password=password

kubectl apply -f postgres.yaml
kubectl apply -f postgres-service.yaml
kubectl apply -f todobackend.yaml
kubectl apply -f todobackend-service.yaml
kubectl apply -f todoui.yaml
kubectl apply -f todoui-service.yaml
