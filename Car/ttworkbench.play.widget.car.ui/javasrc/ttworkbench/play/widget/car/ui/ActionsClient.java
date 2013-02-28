
package ttworkbench.play.widget.car.ui;

import com.google.protobuf.BlockingRpcChannel;
import com.testingtech.ttworkbench.play.dashboard.widget.AbstractActionsClient;
import com.testingtech.ttworkbench.play.generated.PROTO_API;

public class ActionsClient extends AbstractActionsClient<PROTO_API.ACTIONS.BlockingInterface> {

  protected PROTO_API.ACTIONS.BlockingInterface createActionsService(BlockingRpcChannel blockingChannel) {
    return PROTO_API.ACTIONS.newBlockingStub(blockingChannel);
  }


}
