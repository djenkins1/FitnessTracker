
class TimeUtil {
	static convertMinutes(totalMinutes) {
		//minutes to hour (and days) converter
		const MINUTES_PER_HOUR = 60;
		const HOURS_PER_DAY = 24;
		const MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;
		let d = Math.floor(totalMinutes / MINUTES_PER_DAY);
		let h = Math.floor((totalMinutes - (d * MINUTES_PER_DAY)) / MINUTES_PER_HOUR);
		let m = Math.round(totalMinutes % MINUTES_PER_HOUR);
		let dayUnitStr = (d == 1 ? " Day " : " Days ");
		let hourUnitStr = (h == 1 ? " Hour " : " Hours ");
		let minUnitStr = (m == 1 ? " Minute" : " Minutes");

		return (d + dayUnitStr + h + hourUnitStr + m + minUnitStr);
	}
}

export default TimeUtil;