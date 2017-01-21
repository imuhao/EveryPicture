package com.ylian.common.oss;

/**
 * Created by dafan on 2016/2/19 0019.
 */
public class OssConfig {

	/**
	 * AccessKeyId : STS.E7NhjZdGXrN8UUkdgmeNUB9QE
	 * AccessKeySecret : ECRzEyzuvW34umhvBfrkCNrLBvqZFWhZq7qxTaJAEdLz
	 * endpoint : http://oss-cn-hangzhou.aliyuncs.com
	 * bucketName : wsxx
	 * uploadFilePath : tmp/
	 * SecurityToken : CAES7AMIARKAAZOkqyqjSpYDeLzpTV0EjxMR52e+kdy8imamq5JoLwc6XcJb3N9OuTdzNHB+JHQ3z9jtfaBRyIfIUlAO/FRlESmmJpNLmsxNSZj1N1gVfBgqZP/xWQEaJrjZMD6O7n/0ByUxsCQ57RjxzRO8ZVm/hnXCCIKdAaIM/9FzX8htJtarGh1TVFMuRTdOaGpaZEdYck44VVVrZGdtZU5VQjlRRSISMzY3ODkyNjUzOTg3NjAzNTkyKgtjbGllbnRfbmFtZTCSo4CE6yo6BlJzYU1ENUK9AQoBMRq3AQoFQWxsb3cSYgoMQWN0aW9uRXF1YWxzEgZBY3Rpb24aSgoQb3NzOkRlbGV0ZU9iamVjdAoNb3NzOkxpc3RQYXJ0cwoYb3NzOkFib3J0TXVsdGlwYXJ0VXBsb2FkCg1vc3M6UHV0T2JqZWN0EkoKDlJlc291cmNlRXF1YWxzEghSZXNvdXJjZRouChRhY3M6b3NzOio6Kjp3c3h4L3RtcAoWYWNzOm9zczoqOio6d3N4eC90bXAvKkoQMTA5MTIyODcxOTg3MTY5MlIFMjY4NDJaD0Fzc3VtZWRSb2xlVXNlcmAAahIzNjc4OTI2NTM5ODc2MDM1OTJyDW9zcy10bXAtd3JpdGV4zO3d4feO+AE=
	 */

	private String AccessKeyId;
	private String AccessKeySecret;
	private String endpoint;
	private String bucketName;
	private String uploadFilePath;
	private String SecurityToken;
	private long time;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getAccessKeyId() {
		return AccessKeyId;
	}

	public void setAccessKeyId(String AccessKeyId) {
		this.AccessKeyId = AccessKeyId;
	}

	public String getAccessKeySecret() {
		return AccessKeySecret;
	}

	public void setAccessKeySecret(String AccessKeySecret) {
		this.AccessKeySecret = AccessKeySecret;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	public String getSecurityToken() {
		return SecurityToken;
	}

	public void setSecurityToken(String SecurityToken) {
		this.SecurityToken = SecurityToken;
	}
}
