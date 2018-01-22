package eiroa;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import eiroa.domain.Call;
import eiroa.domain.Dispatcher;
import eiroa.domain.Employee;
import eiroa.domain.Role;

public class App {

	public static void main(String[] args) {

		Dispatcher dispatcher = new Dispatcher();

		//define employees
		Employee  employee1 = new Employee(1, Role.OPERATOR);
		Employee  employee2 = new Employee(2, Role.OPERATOR);
		Employee  employee3 = new Employee(3, Role.OPERATOR);
		Employee  supervisor = new Employee(4, Role.SUPERVISOR);
		Employee  director = new Employee(5, Role.DIRECTOR);


		// register them in dispatcher
		dispatcher.getEmployees().put(Role.OPERATOR, Arrays.asList(employee1,employee2,employee3));
		dispatcher.getEmployees().put(Role.SUPERVISOR,Arrays.asList(supervisor));
		dispatcher.getEmployees().put(Role.DIRECTOR,Arrays.asList(director));

		//
		dispatcher.dispatchCall(new Call(1));

	}

	public static class ThreadLauncher {

		public void main() {
			ExecutorService service = Executors.newFixedThreadPool(10);
			IntStream.range(0, 100).forEach(i -> service.submit(new Call(i)));
			service.shutdown();
		}
	}

}


