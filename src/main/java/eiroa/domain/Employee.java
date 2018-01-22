package eiroa.domain;

/**
 * Defines common action across all type of employees within the call center
 */
public class Employee {
	private Boolean onCall = false;
	private Role role;
	private Integer id;
	private Call callInProgress;

	public void pickupCall(Call call) {
		System.out.println("call "+ call.getId() + " picked up by "+ this.id + " with role: "+this.role);
		this.onCall = true;
		call.startCall();
		endCall();
	}

	public void endCall(){
		onCall = false;
		callInProgress = null;
	}

	public Boolean getOnCall() {
		return onCall;
	}

	public void setOnCall(Boolean onCall) {
		this.onCall = onCall;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Employee(Integer id, Role role){
		this.role = role;
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Call getCallInProgress() {
		return callInProgress;
	}

	public void setCallInProgress(Call callInProgress) {
		this.callInProgress = callInProgress;
	}
}
