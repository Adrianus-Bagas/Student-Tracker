package com.tracker.student.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

	private String welcomeText;
	private String timezone;
	private String currency;
	private String secretKey;
	private int expiredMs;
	private int expiredRefreshToken;
	private int expiredForgotPassword;

	public String getWelcomeText() {
		return welcomeText;
	}

	public void setWelcomeText(String welcomeText) {
		this.welcomeText = welcomeText;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public int getExpiredMs() {
		return expiredMs;
	}

	public void setExpiredMs(int expiredMs) {
		this.expiredMs = expiredMs;
	}

	public int getExpiredRefreshToken() {
		return expiredRefreshToken;
	}

	public void setExpiredRefreshToken(int expiredRefreshToken) {
		this.expiredRefreshToken = expiredRefreshToken;
	}

	public int getExpiredForgotPassword() {
		return expiredForgotPassword;
	}

	public void setExpiredForgotPassword(int expiredForgotPassword) {
		this.expiredForgotPassword = expiredForgotPassword;
	}

}
