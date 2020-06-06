function check_vars() {
  _N=0
  while (( "$#" )); do
    _VAR=$1
    echo "checking: $_VAR"
    if [[ -z "${!_VAR}" ]]
    then
      echo "ERROR $_VAR variable not defined."
      _N=$(($_N + 1))
      echo "$_N"
    fi
    shift
  done

  if [[ $_N -gt 0 ]]
  then
    echo "Undefined variables found. Exiting."
    exit 1
  fi
}

check_vars APP_SUBSCRIPTION_ID APP_TENANT_ID APP_CLIENT_ID APP_CLIENT_SECRET SOURCE APP_RESOURCE_GROUP_NAME APP_RESOURCE_NAME \
APP_STORAGE_NAME APP_STORAGE_CONTAINER_NAME APP_NAME TF_STATE_RESOURCE_GROUP

cd ${SYSTEM_DEFAULTWORKINGDIRECTORY}/${SOURCE}

az login \
--service-principal \
--tenant ${APP_TENANT_ID} -u ${APP_CLIENT_ID} -p ${APP_CLIENT_SECRET} \
--output table

if [[ $? -ne 0 ]]
then
  echo "ERROR Azure login failed."
  exit 2
fi

az account set \
--subscription ${APP_ACCOUNT_NAME}

APP_STORAGE_ENVIRONMENT=public

# Create storage account for terraform state if not exists
az group create \
--subscription ${APP_SUBSCRIPTION_ID} \
--location ${APP_LOCATION} \
--name ${TF_STATE_RESOURCE_GROUP}

az storage account create \
--subscription ${APP_SUBSCRIPTION_ID} \
--name ${APP_STORAGE_NAME} \
--resource-group ${TF_STATE_RESOURCE_GROUP} \
--location ${APP_LOCATION} \
--kind BlobStorage \
--access-tier Hot \
--sku Standard_GRS

APP_STORAGE_KEY=$(az storage account keys list \
--subscription ${APP_SUBSCRIPTION_ID} \
--resource-group ${TF_STATE_RESOURCE_GROUP} \
--account-name ${APP_STORAGE_NAME} \
--query [0].value -o tsv)
if [[ $? -ne 0 ]]
then
  echo "ERROR Failed to read Azure storage access key."
  echo "Create a storage account in your subscription in:"
  echo "resource-group: ${TF_STATE_RESOURCE_GROUP}"
  echo "name: ${APP_STORAGE_NAME}"
  exit 3
fi

az storage container create \
--subscription ${APP_SUBSCRIPTION_ID} \
--account-name ${APP_STORAGE_NAME} \
--account-key ${APP_STORAGE_KEY} \
--name ${APP_STORAGE_CONTAINER_NAME}

export ARM_ACCESS_KEY=${APP_STORAGE_KEY}

export TF_VAR_app_subscription_id=${APP_SUBSCRIPTION_ID}
export TF_VAR_app_client_id=${APP_CLIENT_ID}
export TF_VAR_app_tenant_id=${APP_TENANT_ID}
export TF_VAR_app_client_secret=${APP_CLIENT_SECRET}
export TF_VAR_app_resource_group_name=${APP_RESOURCE_GROUP_NAME}
export TF_VAR_app_resource_name=${APP_RESOURCE_NAME}
export TF_VAR_app_name=${APP_NAME}

export TF_VERSION=0.12.23
curl -LO https://releases.hashicorp.com/terraform/${TF_VERSION}/terraform_${TF_VERSION}_linux_amd64.zip
unzip terraform_${TF_VERSION}_linux_amd64.zip -d .
rm -f terraform_${TF_VERSION}_linux_amd64.zip
chmod +x terraform

./terraform --version

./terraform init -no-color \
-backend-config="environment=${APP_STORAGE_ENVIRONMENT}" \
-backend-config="storage_account_name=${APP_STORAGE_NAME}" \
-backend-config="container_name=${APP_STORAGE_CONTAINER_NAME}" \
-backend-config="key=${APP_NAME}.tfstate" 

if [[ $? -ne 0 ]]
then
  echo "ERROR Terraform init failed."
  exit 4
fi

echo "Init successfull."
./terraform plan -input=false -no-color
./terraform apply -input=false -auto-approve -no-color
if [[ $? -ne 0 ]]
then
  echo "ERROR Terraform apply failed."
  exit 5
fi

az logout