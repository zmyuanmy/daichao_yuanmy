package com.jbb.domain;

import java.util.Arrays;

public class XorGroupsPolicy {

	private XorGroup[] xorGroups;

	public XorGroup[] getXorGroups() {
		return xorGroups;
	}

	public void setXorGroups(XorGroup[] xorGroups) {
		this.xorGroups = xorGroups;
	}

	@Override
	public String toString() {
		return "XorGroupsPolicy [xorGroups=" + Arrays.toString(xorGroups) + "]";
	}

	public static class XorGroup {
		String groupName;
		int orgs[];

		
		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}

		public int[] getOrgs() {
			return orgs;
		}

		public void setOrgs(int[] orgs) {
			this.orgs = orgs;
		}

		@Override
		public String toString() {
			return "XorGroup [groupName=" + groupName + ", orgs=" + Arrays.toString(orgs) + "]";
		}

	}
}
