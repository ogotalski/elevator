package by.epam.lab.utils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
public class AppLogger {
	static{
		DOMConfigurator.configure("log4j.xml");
	}
	public final static Logger LOG = Logger.getRootLogger();

}
