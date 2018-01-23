package eiroa;

import java.util.Arrays;

import eiroa.domain.Call;
import eiroa.domain.Dispatcher;
import eiroa.domain.Employee;
import eiroa.domain.Role;

public class App {

	public static void main(String[] args) {

		Dispatcher dispatcher = new Dispatcher(10);
		dispatcher.MAX_EMPLOYEE_HIERARCHY_VALUE = 3;
		dispatcher.MIN_EMPLOYEE_HIERARCHY_VALUE = 1;

		//define employees
		Employee  employee1 = new Employee(1, Role.OPERATOR,dispatcher);
		Employee  employee2 = new Employee(2, Role.OPERATOR,dispatcher);
		Employee  employee3 = new Employee(3, Role.OPERATOR,dispatcher);
		Employee  employee4 = new Employee(10, Role.OPERATOR,dispatcher);
		Employee  employee5 = new Employee(20, Role.OPERATOR,dispatcher);
		Employee  employee6 = new Employee(30, Role.OPERATOR,dispatcher);
		Employee  supervisor1 = new Employee(4, Role.SUPERVISOR,dispatcher);
		Employee  supervisor2 = new Employee(6, Role.SUPERVISOR,dispatcher);
		Employee  director = new Employee(0, Role.DIRECTOR,dispatcher);


		/*employee1.setOnCall();
		employee2.setOnCall();
		employee3.setOnCall();
		supervisor.setOnCall();
		director.setOnCall();*/

		// register them in dispatcher
		dispatcher.getEmployees().put(Role.OPERATOR, Arrays.asList(employee1,employee2,employee3,employee4,employee5,employee6));
		dispatcher.getEmployees().put(Role.SUPERVISOR,Arrays.asList(supervisor1,supervisor2));
		dispatcher.getEmployees().put(Role.DIRECTOR,Arrays.asList(director));

		//Define calls and dispatch them
		dispatcher.dispatchCall(new Call(1));
		dispatcher.dispatchCall(new Call(2));
		dispatcher.dispatchCall(new Call(3));
		dispatcher.dispatchCall(new Call(4));
		dispatcher.dispatchCall(new Call(5));
		dispatcher.dispatchCall(new Call(10));
		dispatcher.dispatchCall(new Call(20));

		dispatcher.dispatchCall(new Call(40));
		dispatcher.dispatchCall(new Call(50));
		dispatcher.dispatchCall(new Call(100));
		dispatcher.dispatchCall(new Call(200));

		dispatcher.dispatchCall(new Call(9));
		dispatcher.dispatchCall(new Call(99));
		dispatcher.dispatchCall(new Call(999));
		dispatcher.dispatchCall(new Call(9999));
	}

}


