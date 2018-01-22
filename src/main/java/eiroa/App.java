package eiroa;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import eiroa.domain.Call;
import eiroa.domain.Dispatcher;
import eiroa.domain.Employee;
import eiroa.domain.Role;

public class App {

	public static void main(String[] args) {

		Dispatcher dispatcher = new Dispatcher();

		//define employees
		Employee  employee1 = new Employee(1, Role.OPERATOR,dispatcher);
		Employee  employee2 = new Employee(2, Role.OPERATOR,dispatcher);
		Employee  employee3 = new Employee(3, Role.OPERATOR,dispatcher);
		Employee  supervisor = new Employee(4, Role.SUPERVISOR,dispatcher);
		Employee  director = new Employee(5, Role.DIRECTOR,dispatcher);
		// register them in dispatcher
		dispatcher.getEmployees().put(Role.OPERATOR, Arrays.asList(employee1,employee2,employee3));
		dispatcher.getEmployees().put(Role.SUPERVISOR,Arrays.asList(supervisor));
		dispatcher.getEmployees().put(Role.DIRECTOR,Arrays.asList(director));

		//Define calls
		dispatcher.dispatchCall(new Call(1));
		dispatcher.dispatchCall(new Call(2));
		dispatcher.dispatchCall(new Call(3));
		dispatcher.dispatchCall(new Call(4));
		dispatcher.dispatchCall(new Call(5));
		dispatcher.dispatchCall(new Call(10));


		//
		try {
			Thread.sleep(10500);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}


		System.out.println("Finished calls: " + dispatcher.getFinishedCalls().size() );
		System.out.println("InProgress Calls calls: " + dispatcher.getCallsInProgress().size());

	}

}


