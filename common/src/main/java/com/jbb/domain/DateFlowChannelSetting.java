package com.jbb.domain;

public class DateFlowChannelSetting {

	private String channelCode;
	private boolean enabled;
	private int percent;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	@Override
	public String toString() {
		return "DateFolwChannelSetting [channelCode=" + channelCode + ", enabled=" + enabled + ", percent=" + percent
				+ "]";
	}

}
