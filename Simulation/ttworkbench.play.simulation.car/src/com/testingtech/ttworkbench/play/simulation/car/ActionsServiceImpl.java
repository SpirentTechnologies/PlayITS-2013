package com.testingtech.ttworkbench.play.simulation.car;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;
import com.testingtech.ttworkbench.play.generated.PROTO_SUTAPI.Void;
import com.testingtech.ttworkbench.play.generated.PROTO_SUTAPI.ACTIONS.BlockingInterface;

public class ActionsServiceImpl implements BlockingInterface {

	@Override
	public Void aPICarStatusType(RpcController controller, carStatusType request)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
