#! /bin/sh

CLUSTER_NAME=jelastic-cluster
CLUSTER_USERNAME=${CLUSTER_NAME}

kubectl config set-cluster "${CLUSTER_NAME}" --server="${KUBERNETES_API_URL}"
kubectl config set-context "${CLUSTER_NAME}" --cluster="${CLUSTER_NAME}"
kubectl config set-credentials "${CLUSTER_USERNAME}" --token="${KUBERNETES_API_TOKEN}"
kubectl config set-context "${CLUSTER_NAME}" --user="${CLUSTER_USERNAME}"
kubectl config use-context "${CLUSTER_NAME}"