package gov.state.irm.goss.osbextensions.udf.transport.http.xforwardedfor;

import java.util.ArrayList;

/**
 * 
 * Utility class for processing HTTP X-Forwarded-For headers.
 * 
 * This source code's highest classification is unclassified.
 * 
 * @author Jason Pyeron <jpyeron@pdinc.us>
 *
 */
public class XForwardedFor
{
	public static final String HEADER="X-Forwarded-For";
	public static final String HEADER_PREFIX=HEADER+":";
	public static final int HEADER_PREFIX_LEN = HEADER_PREFIX.length();
	public static final String NAMESPACE="http://goss.irm.state.gov/xquery/xquery-functions";
	
	public static String getValueFromHeader(String header)
	{
		if (header==null) return null;
		if (!header.startsWith(HEADER_PREFIX)) return null;
		return header.substring(HEADER_PREFIX_LEN).trim();
	}
	
/*	public static boolean isValidHeader(String header)
	{
		String val = getValueFromHeader(header);
		if (val==null) return false;
		return isValidValue(val);
	}
	
	public static boolean isValidValue(String value)
	{
		return false;
	}
*/
	
	public static String getLastClientFromHeader(String header)
	{
		return getLastClientFromHeaders(new String[]{header});
	}
	
	public static String getLastClientFromHeaders(String[] headers)
	{
		String[] vals = getClientsFromHeaders(headers);
		if (vals==null || vals.length==0) return null;
		return vals[vals.length-1];
	}
	
	public static String getFirstClientFromHeader(String header)
	{
		return getFirstClientFromHeaders(new String[]{header});
	}
	
	public static String getFirstClientFromHeaders(String[] headers)
	{
		String[] vals = getClientsFromHeaders(headers);
		if (vals==null || vals.length==0) return null;
		return vals[0];
	}
	
	/**
	 * Returns all the host / proxies with client first from an array of headers.
	 * @param headers
	 * @return
	 */
	public static String[] getClientsFromHeaders(String[] headers)
	{
		ArrayList<String> res=new ArrayList<String>();

		for (String header:headers)
		{
			String[] values = getClientsFromHeader(header);
			for (String value:values) res.add(value);
		}
		
		return res.toArray(new String[res.size()]);
	}
	
	/**
	 * Returns all the host / proxies with client first from a single header.
	 * @param header
	 * @return
	 */
	public static String[] getClientsFromHeader(String header)
	{
		ArrayList<String> res=new ArrayList<String>();

		String value=getValueFromHeader(header);
		
		if (value!=null)
		{
			String[] values = value.split(",");
			for (String hop:values)
			{
				if (hop==null || (hop=hop.trim()).isEmpty()) continue;
				res.add(hop);
			}
		}

		return res.toArray(new String[res.size()]);
	}
}
