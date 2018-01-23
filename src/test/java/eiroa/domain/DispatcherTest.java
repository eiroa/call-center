package eiroa.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
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
	private Call call3;
	private Call call4;
	private Call call5;
	private Call call6;
	private Call call7;
	private Call call8;
	private Call call9;
	private Call call10;

	private Integer availableEmployees;
	private Integer totalCalls;

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
		givenXCallsAndYAvailableEmployees(10,3);
		whenCallsAreDispatched(Arrays.asList(call1,call2,call3,call4,call5,call6,call7,call8,call9,call10));
		thenValidateThatDispatcherHasAssignedCallsToAvailableEmployeesAndQueedTheRest();
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

	private void whenCallsAreDispatched(List<Call> calls) {
		calls.forEach(call-> dispatcher.dispatchCall(call));
	}

	private void givenXCallsAndYAvailableEmployees(Integer calls, Integer employees) {
		call1 = new Call(callId1);
		call2 = new Call(callId2);
		call3 = new Call(callId2+1);
		call4 = new Call(callId1+2);
		call5 = new Call(callId1+3);
		call6 = new Call(callId1+4);
		call7 = new Call(callId1+5);
		call8 = new Call(callId1+6);
		call9 = new Call(callId1+7);
		call10 = new Call(callId1+8);
		totalCalls=calls;
		availableEmployees=employees;
	}

	private void thenValidateThatDispatcherHasAssignedCallsToAvailableEmployeesAndQueedTheRest() {
		assertEquals(dispatcher.getOnHoldCalls().size(),totalCalls-availableEmployees);
		assertEquals(dispatcher.getCallsInProgress().size(),availableEmployees.intValue());
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
