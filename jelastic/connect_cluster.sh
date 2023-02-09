#! /bin/sh

CLUSTER_USERNAME=${KUBERNETES_CLUSTER_NAME}

kubectl config set-cluster "${KUBERNETES_CLUSTER_NAME}" --server="${KUBERNETES_API_URL}"
kubectl config set-context "${KUBERNETES_CLUSTER_NAME}" --cluster="${KUBERNETES_CLUSTER_NAME}"
kubectl config set-credentials "${CLUSTER_USERNAME}" --token="${KUBERNETES_API_TOKEN}"
kubectl config set-context "${KUBERNETES_CLUSTER_NAME}" --user="${CLUSTER_USERNAME}"
kubectl config use-context "${KUBERNETES_CLUSTER_NAME}"