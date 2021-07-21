/*******************************************************************************************************
 *
 * StartingLegMessage.java, in plugin irit.gama.jdeqsim, is part of the source code of the GAMA modeling and simulation
 * platform (v. 1.8.1)
 *
 * (c) 2007-2021 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/

package irit.gama.core.scheduler.message;

import irit.gama.common.IConst;
import irit.gama.core.scheduler.Scheduler;
import irit.gama.core.sim_unit.Road;
import irit.gama.core.sim_unit.Vehicle;
import msi.gama.util.GamaDate;

/**
 * The micro-simulation internal handler for starting a leg.
 * 
 * @author rashid_waraich
 */
public class StartingLegMessage extends Message {

	public StartingLegMessage(Scheduler scheduler, Vehicle vehicle) {
		super(scheduler, vehicle);
		priority = IConst.PRIORITY_DEPARTUARE_MESSAGE;
	}

	@Override
	public void handleMessage() {
		// if current leg is in car mode, then enter request in first road
		// if (vehicle.getCurrentLeg().getMode().equals(TransportMode.car)) {

		// if empty leg, then end leg, else simulate leg
		if (vehicle.getCurrentLinkRouteLength() == 0) {
			// move to first link in next leg and schedule an end leg
			// message
			// duration of leg = 0 (departure and arrival time is the same)
			scheduleEndLegMessage(getMessageArrivalTime());

		} else {
			// start the new leg
			Road road = vehicle.getCurrentRoad();
			road.enterRequest(vehicle, getMessageArrivalTime());
		}

		// } else {
		// scheduleEndLegMessage(getMessageArrivalTime() +
		// vehicle.getCurrentLeg().getTravelTime().orElse(0));
		// }
	}

	private void scheduleEndLegMessage(GamaDate time) {
		// move to first link in next leg and schedule an end leg message
		vehicle.moveToFirstLinkInNextLeg();
		Road road = vehicle.getCurrentRoad();
		vehicle.scheduleEndLegMessage(time, road);
	}
}
