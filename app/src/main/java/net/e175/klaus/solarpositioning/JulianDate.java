package net.e175.klaus.solarpositioning;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Calculate Julian date for a given point in time. This follows the algorithm described in Reda, I.; Andreas, A.
 * (2003): Solar Position Algorithm for Solar Radiation Applications. NREL Report No. TP-560-34302, Revised January
 * 2008.
 * 
 * @author Klaus Brunner
 * 
 */
public final class JulianDate {
	private final GregorianCalendar calendar;
	private final double julianDate;
	private final double deltaT;

	/**
	 * Construct a Julian date, assuming deltaT to be 0.
	 * 
	 * @param date
	 */
	public JulianDate(final GregorianCalendar date) {
		this.calendar = createGmtCalendar(date);
		this.julianDate = calcJulianDate();
		this.deltaT = 0.0;
	}

	/**
	 * Construct a Julian date, observing deltaT.
	 * 
	 * @param date
	 * @param deltaT
	 *            Difference between earth rotation time and terrestrial time (or Universal Time and Terrestrial Time),
	 *            in seconds. See <a href ="http://maia.usno.navy.mil/ser7/deltat.preds">http://maia.
	 *            usno.navy.mil/ser7/deltat.preds</a> for values. For the year 2013, a reasonably accurate default would
	 *            be 67.
	 */
	public JulianDate(final GregorianCalendar date, final double deltaT) {
		this.calendar = createGmtCalendar(date);
		this.julianDate = calcJulianDate();
		this.deltaT = deltaT;
	}

	private GregorianCalendar createGmtCalendar(final GregorianCalendar fromCalendar) {
		final GregorianCalendar utcCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		utcCalendar.setTimeInMillis(fromCalendar.getTimeInMillis());
		utcCalendar.set(Calendar.ERA, fromCalendar.get(Calendar.ERA));
		return utcCalendar;
	}

	private double calcJulianDate() {
		int y = (calendar.get(Calendar.ERA) == GregorianCalendar.AD) ? calendar.get(Calendar.YEAR) : -calendar
				.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH) + 1;

		if (m < 3) {
			y = y - 1;
			m = m + 12;
		}

		final double d = calendar.get(Calendar.DAY_OF_MONTH)
				+ (calendar.get(Calendar.HOUR_OF_DAY) + (calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND) / 60.0) / 60.0)
				/ 24.0;
		final double jd = Math.floor(365.25 * (y + 4716.0)) + Math.floor(30.6001 * (m + 1)) + d - 1524.5;
		final double a = Math.floor(y / 100.0);
		final double b = jd > 2299160.0 ? (2.0 - a + Math.floor(a / 4.0)) : 0.0;

		return jd + b;
	}

	public double getJulianDate() {
		return julianDate;
	}

	public double getJulianEphemerisDay() {
		return julianDate + deltaT / 86400.0;
	}

	public double getJulianCentury() {
		return (julianDate - 2451545.0) / 36525.0;
	}

	public double getJulianEphemerisCentury() {
		return (getJulianEphemerisDay() - 2451545.0) / 36525.0;
	}

	public double getJulianEphemerisMillennium() {
		return getJulianEphemerisCentury() / 10.0;
	}

	@Override
	public String toString() {
		return String.format("%.5f", julianDate);
	}

}
