/*
 * ----------------------------------------------------------------------------
 *  (C) Copyright Testing Technologies, 2012.  All Rights Reserved.
 *
 *  All copies of this program, whether in whole or in part, and whether
 *  modified or not, must display this and all other embedded copyright
 *  and ownership notices in full.
 *
 *  See the file COPYRIGHT for details of redistribution and use.
 *
 *  You should have received a copy of the COPYRIGHT file along with
 *  this file; if not, write to the Testing Technologies,
 *  Michaelkirchstr. 17/18, 10179 Berlin, Germany.
 *
 *  TESTING TECHNOLOGIES DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS
 *  SOFTWARE. IN NO EVENT SHALL TESTING TECHNOLOGIES BE LIABLE FOR ANY
 *  SPECIAL, DIRECT, INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN
 *  AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION,
 *  ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF
 *  THIS SOFTWARE.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND,
 *  EITHER EXPRESSED OR IMPLIED, INCLUDING ANY KIND OF IMPLIED OR
 *  EXPRESSED WARRANTY OF NON-INFRINGEMENT OR THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.
 *
 * -----------------------------------------------------------------------------
 *
 *  AUTHOR:    Bogdan Stanca
 *  DATE:      Feb 6, 2013
 *
 *  REVISION INFO:
 *    $Revision: 1.5 $ $Date: 2013/02/12 12:57:34 $
 *    $Source: /usr/local/cvs_root/TTplugins/Play/widgets/com.testingtech.ttworkbench.play.widget.pop3.ui/javasrc/com/testingtech/ttworkbench/play/widget/pop3/ui/EventsServiceImpl.java,v $
 * 
 * -----------------------------------------------------------------------------
 */
package ttworkbench.play.widget.car.ui;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.GPSposition;
import ttworkbench.play.widget.car.ui.model.Warnings;
import ttworkbench.play.widget.car.ui.model.WarningType;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.EVENTS.BlockingInterface;
import com.testingtech.ttworkbench.play.generated.PROTO_API.Void;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;



public class EventsServiceImpl implements BlockingInterface {

  private final CarModel model;

  public EventsServiceImpl(CarModel widget) {
    this.model = widget;
  }

@Override
public Void aPICarStatusType(RpcController controller, carStatusType request)
		throws ServiceException {
	if(request != null){
		warningType reqWarning = request.getWarning();
		if(reqWarning != null){
			WarningType warning = new WarningType();
			warning.setWarning(Warnings.getWarning((reqWarning.getWarningName().getEnumValue().getNumber())));
			warning.setGpsPosition(new GPSposition(reqWarning.getGpsPos().getLatitude(),reqWarning.getGpsPos().getLongitude()));
			warning.setPriority(reqWarning.getPriority());
			model.addWarning(warning);
		}
		
		CarStatusModel status = model.getStatus();
		status.setABSenabled(request.getAbsSensor());
		status.setActualSpeed(request.getSpeed());
		status.setEngineStarted(request.getEngineStatus());
		status.setESPenabled(request.getEspSensor());
		status.setLightenabled(request.getLightSensor());
		
		model.notifyListener();
	}
	return nil();
}

protected Void nil() {
    return Void.newBuilder().build();
  }

}
