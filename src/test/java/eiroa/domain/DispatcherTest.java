package eiroa.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Optional;

import com.sun.xml.internal.ws.policy.AssertionSet;

import eiroa.exception.NoAvailableOperatorsException;

public class DispatcherTest {

	private Dispatcher dispatcher;
	private Employee employee;
	private Employee supervisor;
	private Employee director;
	private Integer employeeId1;
	private Integer employeeId2;
	private Call call1;
	private Integer callId1;
	private Call call2;
	private Integer callId2;
	private Optional<Employee> callPicker;

	private Integer minHierarchyValue;
	private Integer maxHierarchyValue;
	private Integer maxConcurrentActiveCalls;
	private Integer employeeId3;

	@Before
	public void setUp() {
		dispatcher = new Dispatcher();
		minHierarchyValue = 1;
		maxConcurrentActiveCalls = 10;
		maxHierarchyValue = 3;
		dispatcher.MIN_EMPLOYEE_HIERARCHY_VALUE = minHierarchyValue;
		dispatcher.MAX_EMPLOYEE_HIERARCHY_VALUE = maxHierarchyValue;
		dispatcher.MAX_CONCURRENT_ACTIVE_CALLS = maxConcurrentActiveCalls;
		employeeId1 = 0;
		employeeId2 = 1;
		employeeId3 = 2;
		callId1 = 1;
		callId2 = 2;
		employee = new Employee(employeeId1, Role.OPERATOR, dispatcher);
		supervisor = new Employee(employeeId2, Role.SUPERVISOR, dispatcher);
		director = new Employee(employeeId3, Role.DIRECTOR, dispatcher);
		dispatcher.getEmployees().put(Role.OPERATOR, Arrays.asList(employee));
		dispatcher.getEmployees().put(Role.SUPERVISOR, Arrays.asList(supervisor));
		dispatcher.getEmployees().put(Role.DIRECTOR, Arrays.asList(director));
	}

	@Test
	public void dispatcherCorrectlyProcesses10Calls(){
	}

	@Test
	public void yieldAnOperatorFirstFromEmployeesAvailable() {
		givenAllAvailableEmployees();
		whenTryingToGetAnAvailableEmployeeToPickUpCall();
		thenVerifyThatObtainedEmployeeIsOperator();
	}

	@Test
	public void raiseExceptionWhenNoAvailableEmployees() {
		givenNoAvailableEmployees();
		thenANoAvailableEmployeesExceptionIsRaised();
	}

	private void thenVerifyThatObtainedEmployeeIsOperator() {
		Assertions.assertTrue(callPicker.isPresent());
		Assertions.assertEquals(callPicker.get().getRole(),Role.OPERATOR);
	}

	private void whenTryingToGetAnAvailableEmployeeToPickUpCall() {
		callPicker = dispatcher.searchAvailableEmployee(minHierarchyValue);
	}

	private void givenAllAvailableEmployees() {
		employee.setAvailable();
		supervisor.setAvailable();
		director.setAvailable();
	}


	private void thenANoAvailableEmployeesExceptionIsRaised() {
		Assertions.assertThrows(NoAvailableOperatorsException.class,()-> whenACallIsDispatch());
	}

	private void whenACallIsDispatch() {
		dispatcher.processDispatchment(call1);
	}

	private void givenNoAvailableEmployees() {
		employee.setOnCall();
		supervisor.setOnCall();
		director.setOnCall();
	}
}
