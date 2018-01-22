package eiroa.domain;

import java.util.Random;

public class Call implements Runnable {
	private Integer id;
	private Integer duration;
	private Employee callPicker;
	private Boolean isOnHold;

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

	@Override
	public void run() {
		try {
			Thread.currentThread().sleep(this.duration*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Call has ended after lasting " + this.duration + " seconds");
	}

	public void startCall() {
		this.duration = new Random().nextInt(6) +5;
		this.run();
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
