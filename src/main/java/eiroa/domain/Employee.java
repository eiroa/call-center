package eiroa.domain;

import java.util.Random;

public class Employee {
	private Boolean onCall = false;
	private Role role;
	private Integer id;
	private Call callInProgress;
	private Dispatcher dispatcher;

	public void pickupCall(Call call) {
		System.out.println("call "+ call.getId() + " picked up by "+ this.id + " with role: "+this.role);
		this.onCall = true;
		callInProgress = call;
	}

	public Integer defineDuration(){
		return new Random().nextInt(6) +5;
	}

	public void endCall(){
		onCall = false;
		dispatcher.endCall(callInProgress);
		callInProgress = null;
	}

	public Boolean getOnCall() {
		return onCall;
	}

	public void setOnCall() {
		this.onCall = true;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Employee(Integer id, Role role, Dispatcher dispatcher){
		this.role = role;
		this.id = id;
		this.dispatcher = dispatcher;
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

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
