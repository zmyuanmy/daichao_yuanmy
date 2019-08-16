package com.jbb.domain;

import java.util.Arrays;

public class SpecialEntryOrgsPolicy {

	private SpecialOrg[] specialOrgs;

	public SpecialOrg[] getSpecialOrgs() {
		return specialOrgs;
	}

	public void setSpecialOrgs(SpecialOrg[] specialOrgs) {
		this.specialOrgs = specialOrgs;
	}

	@Override
	public String toString() {
		return "SpecialGroupsPolicy [specialOrgs=" + Arrays.toString(specialOrgs) + "]";
	}

	public static class SpecialOrg {
		private int orgId;
		private Integer num;
		private String jexlScript;

		public int getOrgId() {
			return orgId;
		}

		public void setOrgId(int orgId) {
			this.orgId = orgId;
		}

		public Integer getNum() {
			return num;
		}

		public void setNum(Integer num) {
			this.num = num;
		}

		public String getJexlScript() {
			return jexlScript;
		}

		public void setJexlScript(String jexlScript) {
			this.jexlScript = jexlScript;
		}

		@Override
		public String toString() {
			return "SpecialOrg [orgId=" + orgId + ", num=" + num + ", jexlScript=" + jexlScript + "]";
		}

	}

}
