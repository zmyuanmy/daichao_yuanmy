package com.jbb.domain;

import java.util.Arrays;

public class SelfGroupsPolicy {
	private int[] orgs;

	public int[] getOrgs() {
		return orgs;
	}

	public void setOrgs(int[] orgs) {
		this.orgs = orgs;
	}

	@Override
	public String toString() {
		return "SelfGroupsPolicy [orgs=" + Arrays.toString(orgs) + "]";
	}

}
