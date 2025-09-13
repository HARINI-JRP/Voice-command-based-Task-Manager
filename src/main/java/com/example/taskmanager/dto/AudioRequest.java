package com.example.taskmanager.dto;

public class AudioRequest {
  // Either: "base64" with audio bytes encoded as Base64, OR "gcsUri" pointing to a GCS file.
  private String base64;
  private String gcsUri;
  private String languageCode = "en-US";

  public String getBase64() { return base64; }
  public void setBase64(String base64) { this.base64 = base64; }
  public String getGcsUri() { return gcsUri; }
  public void setGcsUri(String gcsUri) { this.gcsUri = gcsUri; }
  public String getLanguageCode() { return languageCode; }
  public void setLanguageCode(String languageCode) { this.languageCode = languageCode; }
}
