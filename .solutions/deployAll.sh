#/bin/sh

kubectl create configmap postgres-config --from-literal=postgres.db.name=mydb
kubectl create secret generic db-security --from-literal=db.user.name=matthias --from-literal=db.user.password=password

kubectl apply -f postgres.yml
kubectl apply -f postgres-service.yml
kubectl apply -f todobackend.yml
kubectl apply -f todobackend-service.yml
kubectl apply -f todoui.yml
kubectl apply -f todoui-service.yml
