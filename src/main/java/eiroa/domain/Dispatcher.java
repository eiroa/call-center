package eiroa.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import eiroa.exception.MaxConcurrentActiveCallsCapacityException;
import eiroa.exception.NoAvailableOperatorsException;

public class Dispatcher {
	public Integer MAX_CONCURRENT_ACTIVE_CALLS;
	public Integer MAX_EMPLOYEE_HIERARCHY_VALUE;
	public Integer MIN_EMPLOYEE_HIERARCHY_VALUE;
	private Map<Role, List<Employee>> employees = new HashMap<Role, List<Employee>>() {{
		put(Role.OPERATOR, Arrays.asList());
		put(Role.SUPERVISOR, Arrays.asList());
		put(Role.DIRECTOR, Arrays.asList());
	}};

	private ExecutorService executorService = Executors.newFixedThreadPool(10);
	private Map<Integer, Future<Call>> callsInProgress = new HashMap<>();
	private List<Call> onHoldCalls = new LinkedList<>();
	private List<Call> finishedCalls = new LinkedList<>();

	public synchronized void dispatchCall(Call call) {
		try {
			processDispatchment(call);
		} catch (NoAvailableOperatorsException | MaxConcurrentActiveCallsCapacityException e) {
			onHoldCalls.add(call);
		}
	}

	public void processDispatchment(Call call) {
		validateMaxConcurrentActiveCalls(call);
		searchAvailableEmployee(MIN_EMPLOYEE_HIERARCHY_VALUE).flatMap(callPicker -> {
			initiateCall(callPicker, call);
			return Optional.of(callPicker);
		}).orElseThrow(NoAvailableOperatorsException::new);
	}

	public void validateMaxConcurrentActiveCalls(Call call) {
		if (callsInProgress.size() >= MAX_CONCURRENT_ACTIVE_CALLS) {
			System.out.println("Dispatcher cannot process more active calls , adding call " + call.getId() + " to on Hold Calls");
			throw new MaxConcurrentActiveCallsCapacityException();
		}
	}

	public void initiateCall(Employee callPicker, Call call) {
		callPicker.pickupCall(call);
		call.setCallPicker(callPicker);
		this.callsInProgress.put(call.getId(), (Future<Call>) executorService.submit(call));
	}

	public Optional<Employee> searchAvailableEmployee(Integer hierarchy) {
		Optional<Employee> result = employees.get(Role.getRoleByHierarchy(hierarchy)).stream().filter(employee -> !employee.isOnCall()).findFirst();
		if (result.isPresent() || hierarchy > MAX_EMPLOYEE_HIERARCHY_VALUE) {
			return result;
		} else {
			return searchAvailableEmployee(hierarchy + 1);
		}
	}

	public synchronized void endCall(Call finishedCall) {
		this.callsInProgress.remove(finishedCall.getId());
		this.finishedCalls.add(finishedCall);
		finishedCall.getCallPicker().setAvailable();
		checkForOnHoldCalls();
		System.out.println("Finished calls: " + finishedCalls.size());
		System.out.println("InProgress Calls calls: " + callsInProgress.size());
		System.out.println("OnHold Calls calls: " + onHoldCalls.size());
	}

	/**
	 * Selects a random paused call and dispatch it, this method runs inmediatly after
	 */
	public void checkForOnHoldCalls() {
		if (!this.onHoldCalls.isEmpty()) {
			dispatchCall(this.onHoldCalls.remove(new Random().nextInt(this.onHoldCalls.size())));
		}
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public Map<Role, List<Employee>> getEmployees() {
		return employees;
	}

	public void setEmployees(Map<Role, List<Employee>> employees) {
		this.employees = employees;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public List<Call> getOnHoldCalls() {
		return onHoldCalls;
	}

	public void setOnHoldCalls(List<Call> onHoldCalls) {
		this.onHoldCalls = onHoldCalls;
	}

	public List<Call> getFinishedCalls() {
		return finishedCalls;
	}

	public void setFinishedCalls(List<Call> finishedCalls) {
		this.finishedCalls = finishedCalls;
	}

	public Map<Integer, Future<Call>> getCallsInProgress() {
		return this.callsInProgress;
	}
}
