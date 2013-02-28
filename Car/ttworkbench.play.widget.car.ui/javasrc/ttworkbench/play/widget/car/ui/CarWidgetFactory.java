package ttworkbench.play.widget.car.ui;

import com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidgetFactory;
import com.testingtech.ttworkbench.play.dashboard.widget.IDashboard;
import com.testingtech.ttworkbench.play.dashboard.widget.IDashboardWidget;
import com.testingtech.ttworkbench.play.generated.PROTO_API;

public class CarWidgetFactory extends AbstractDashboardWidgetFactory<ICarModel, PROTO_API.ACTIONS.BlockingInterface> {
	
	public IDashboardWidget<ICarModel> create(IDashboard dashboard) {
		return new TestWidget(this, dashboard);
	}
}
