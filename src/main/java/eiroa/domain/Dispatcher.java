package eiroa.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Dispatcher {
	private static Integer MAX_EMPLOYEE_HIERARCHY_VALUE = 3;
	private static Integer MIN_EMPLOYEE_HIERARCHY_VALUE = 1;
	private Map<Role, List<Employee>> employees = new HashMap<Role, List<Employee>>() {{
		put(Role.OPERATOR, Arrays.asList());
		put(Role.SUPERVISOR, Arrays.asList());
		put(Role.DIRECTOR, Arrays.asList());
	}};

	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	private Map<Integer, Future<Call>> callsInProgress = new HashMap<>();
	private List<Call> onHoldCalls;
	private List<Call> finishedCalls = new LinkedList<>();

	public void dispatchCall(Call call) {
		Optional<Employee> availablePicker = searchAvailableEmployee(MIN_EMPLOYEE_HIERARCHY_VALUE);
		availablePicker.flatMap(callPicker -> {
			initiateCall(callPicker, call);
			return Optional.of(callPicker);
		});
	}

	private void initiateCall(Employee callPicker, Call call) {
		callPicker.pickupCall(call);
		call.setCallPicker(callPicker);
		this.callsInProgress.put(call.getId(), (Future<Call>) executorService.submit(call));
	}

	private Optional<Employee> searchAvailableEmployee(Integer hierarchy) {
		Optional<Employee> result = employees.get(Role.getRoleByHierarchy(hierarchy)).stream().filter(employee -> !employee.getOnCall()).findFirst();
		if (result.isPresent() || hierarchy > MAX_EMPLOYEE_HIERARCHY_VALUE) {
			return result;
		} else {
			return searchAvailableEmployee(hierarchy + 1);
		}
	}

	public void endCall(Call finishedCall) {
		this.callsInProgress.remove(finishedCall.getId());
		this.finishedCalls.add(finishedCall);
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
