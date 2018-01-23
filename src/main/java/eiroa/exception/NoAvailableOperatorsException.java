package eiroa.exception;

import eiroa.domain.Call;

public class NoAvailableOperatorsException extends RuntimeException {
	private static final long serialVersionUID = 5154085338366722721L;

	public NoAvailableOperatorsException(){
		super();
		System.out.println("Nobody can pickup call, adding to on Hold Calls");
	}

}
