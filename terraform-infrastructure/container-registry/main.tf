provider "azurerm" {
  alias           = "app"
  version         = "~>2.0"
  subscription_id = var.app_subscription_id
  client_id       = var.app_client_id
  client_secret   = var.app_client_secret
  tenant_id       = var.app_tenant_id
  features {}
}

data "azurerm_client_config" "app" {
  provider = azurerm.app
}

resource "azurerm_resource_group" "registry_rg" {
  provider = azurerm.app
  name     = var.app_resource_group_name
  location = var.app_location
}

resource "azurerm_container_registry" "registry" {
  provider                 = azurerm.app
  name                     = var.app_resource_name
  resource_group_name      = azurerm_resource_group.registry_rg.name
  location                 = azurerm_resource_group.registry_rg.location
  sku                      = "Basic"
  admin_enabled            = true
}

terraform {
  backend "azurerm" {
    
  }
}
