package eiroa.domain;


public class Call implements Runnable {
	private Integer id;
	private Integer duration;
	private Employee callPicker;
	private Boolean isOnHold;

	@Override
	public void run() {
		startCall();
		try {
			Thread.currentThread().sleep(this.duration*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Call "+this.id+" has ended after lasting " + this.duration + " seconds");
		callPicker.endCall();
	}

	/**
	 * Comunication is expected to ocurr here
	 */
	public void startCall() {
		duration = this.callPicker.defineDuration();
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Employee getCallPicker() {
		return callPicker;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Call(Integer id) {
		this.id = id;
	}

	public void setCallPicker(Employee callPicker) {
		this.callPicker = callPicker;
	}

	public Boolean getOnHold() {
		return isOnHold;
	}

	public void setOnHold(Boolean onHold) {
		isOnHold = onHold;
	}
}
