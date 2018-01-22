package eiroa.domain;

public enum Role {
	OPERATOR,SUPERVISOR,DIRECTOR;

	public static Role getRoleByHierarchy(Integer priority){
		switch (priority){
			case 1: return OPERATOR;
			case 2: return SUPERVISOR;
			case 3: return DIRECTOR;
			default: return OPERATOR;
		}
	}
}
