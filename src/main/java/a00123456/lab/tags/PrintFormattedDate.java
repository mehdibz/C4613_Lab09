package a00123456.lab.tags;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrintFormattedDate {

	public static String getFooterDate( String format)
	{
		return DateTimeFormatter.ofPattern(format).format(LocalDate.now());
	}
}
