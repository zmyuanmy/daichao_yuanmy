package com.jbb.domain;

import java.util.Arrays;

public class SpecialGroupsPolicy {

	private SpecialGroup[] specialGroups;

	public SpecialGroup[] getSpecialGroups() {
		return specialGroups;
	}

	public void setSpecialGroups(SpecialGroup[] specialGroups) {
		this.specialGroups = specialGroups;
	}

	@Override
	public String toString() {
		return "SpecialGroupsPolicy [specialGroups=" + Arrays.toString(specialGroups) + "]";
	}

	public static class SpecialGroup {
		private int accountId;
		private int num;

		public int getOrgId() {
			return accountId;
		}

		public int getAccountId() {
			return accountId;
		}

		public void setAccountId(int accountId) {
			this.accountId = accountId;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		@Override
		public String toString() {
			return "SpecialGroup [accountId=" + accountId + ", num=" + num + "]";
		}

		

	}

}
