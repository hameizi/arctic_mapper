package com.netease.arctic.ams.server.model;

import java.util.Map;

public class CatalogMeta {
  private int catalogId;
  private String catalogName;
  private String catalogMetastore;
  private Map<String, String> storageConfigs;
  private Map<String, String> authConfigs;
  private Map<String, String> catalogProperties;

  public int getCatalogId() {
    return catalogId;
  }

  public void setCatalogId(int catalogId) {
    this.catalogId = catalogId;
  }

  public String getCatalogName() {
    return catalogName;
  }

  public void setCatalogName(String catalogName) {
    this.catalogName = catalogName;
  }

  public String getCatalogMetastore() {
    return catalogMetastore;
  }

  public void setCatalogMetastore(String catalogMetastore) {
    this.catalogMetastore = catalogMetastore;
  }

  public Map<String, String> getStorageConfigs() {
    return storageConfigs;
  }

  public void setStorageConfigs(Map<String, String> storageConfigs) {
    this.storageConfigs = storageConfigs;
  }

  public Map<String, String> getAuthConfigs() {
    return authConfigs;
  }

  public void setAuthConfigs(Map<String, String> authConfigs) {
    this.authConfigs = authConfigs;
  }

  public Map<String, String> getCatalogProperties() {
    return catalogProperties;
  }

  public void setCatalogProperties(Map<String, String> catalogProperties) {
    this.catalogProperties = catalogProperties;
  }
}
