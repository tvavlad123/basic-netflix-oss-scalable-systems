variable "app_subscription_id" {
  description = "Type the azure subscription id of your app subscription. Example: 00000000-0000-0000-0000-000000000000"
}

variable "app_client_id" {
  description = "Type the azure client id of your app subscription. Example: 00000000-0000-0000-0000-000000000000"
}

variable "app_client_secret" {
  description = "Type the azure client secret of your app subscription. Example: 00000000-0000-0000-0000-000000000000"
}

variable "app_tenant_id" {
  description = "Type the azure tenant id of your app subscription. Example: 00000000-0000-0000-0000-000000000000"
}

variable "app_location" {
  description = "Which location? Example: northeurope, westeurope, westus..."
  default = "westeurope"
}

variable "app_resource_group_name" {
  description = "Resource group name."
}

variable "app_resource_name" {
  description = "Resource name."
}