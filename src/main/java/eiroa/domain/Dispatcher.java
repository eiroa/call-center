package eiroa.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher {
	private  static Integer MAX_EMPLOYEE_HIERARCHY_VALUE = 3;
	private  static Integer MIN_EMPLOYEE_HIERARCHY_VALUE = 1;
	private Map<Role,List<Employee>> employees = new HashMap<>();
	private ExecutorService executorService = Executors.newFixedThreadPool(10);
	private List<Call> onHoldCalls;

	public Map<Role, List<Employee>> getEmployees() {
		return employees;
	}

	public void setEmployees(Map<Role, List<Employee>> employees) {
		this.employees = employees;
	}

	public void dispatchCall(Call call){
		Optional<Employee> availableOperator = searchAvailableEmployee(MIN_EMPLOYEE_HIERARCHY_VALUE);
		if(availableOperator.isPresent()){
			availableOperator.get().pickupCall(call);
		}
	}

	private Optional<Employee> searchAvailableEmployee(Integer hierarchy){
		if(hierarchy > MAX_EMPLOYEE_HIERARCHY_VALUE) return Optional.empty();
		Optional<Employee> result = employees.get(Role.getRoleByHierarchy(hierarchy)).stream().filter(employee -> !employee.getOnCall()).findFirst();
		if(result.isPresent()){
			return result;
		}else {
			return searchAvailableEmployee(hierarchy+1);
		}
	}


	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
}
