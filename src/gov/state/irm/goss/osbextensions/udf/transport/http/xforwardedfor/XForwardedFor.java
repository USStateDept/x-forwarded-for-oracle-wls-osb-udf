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
	
	/**
	 * Extracts a header's value.
	 * @param header
	 * @return
	 */
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
	
	/**
	 * Extracts the most recent client from a single header.
	 * @param header
	 * @return null on failure
	 */
	public static String getLastClientFromHeader(String header)
	{
		return getLastClientFromHeaders(new String[]{header});
	}
	
	/**
	 * Extracts the most recent client from a single header value.
	 * @param header
	 * @return null on failure
	 */
	public static String getLastClientFromHeaderValue(String value)
	{
		return getLastClientFromHeadersValues(new String[]{value});
	}
	
	/**
	 * Extracts the most recent client from a single header value or returns the default value.
	 * @param header
	 * @return null on failure
	 */
	public static String getLastClientFromHeaderValueDefault(String value, String def)
	{
		String res = getLastClientFromHeadersValues(new String[]{value});
		if (res==null || res.isEmpty()) return def;
		return res;
	}
	
	/**
	 * Extracts the most recent client from multiple headers.
	 * @return null on failure
	 * @param headers
	 */
	public static String getLastClientFromHeaders(String[] headers)
	{
		String[] vals = getClientsFromHeaders(headers);
		if (vals==null || vals.length==0) return null;
		return vals[vals.length-1];
	}
	
	/**
	 * Extracts the most recent client from multiple headers' values.
	 * @return null on failure
	 * @param headers
	 */
	public static String getLastClientFromHeadersValues(String[] values)
	{
		String[] vals = getClientsFromHeadersValues(values);
		if (vals==null || vals.length==0) return null;
		return vals[vals.length-1];
	}
	
	/**
	 * Extracts the originating client from a single header.
	 * @param header
	 * @return null on failure
	 */
	public static String getFirstClientFromHeader(String header)
	{
		return getFirstClientFromHeaders(new String[]{header});
	}
	
	/**
	 * Extracts the originating client from a single header's value.
	 * @param header
	 * @return null on failure
	 */
	public static String getFirstClientFromHeaderValue(String value)
	{
		return getFirstClientFromHeadersValues(new String[]{value});
	}
	
	/**
	 * Extracts the originating client from a single header's value or returns the default value.
	 * @param header
	 * @return null on failure
	 */
	public static String getFirstClientFromHeaderValueDefault(String value, String def)
	{
		String res = getFirstClientFromHeadersValues(new String[]{value});
		if (res==null || res.isEmpty()) return def;
		return res;
	}
	
	/**
	 * Extracts the originating client from multiple headers.
	 * @param headers
	 * @return null on failure
	 */
	public static String getFirstClientFromHeaders(String[] headers)
	{
		String[] vals = getClientsFromHeaders(headers);
		if (vals==null || vals.length==0) return null;
		return vals[0];
	}
	
	/**
	 * Extracts the originating client from multiple headers' values.
	 * @param headers
	 * @return null on failure
	 */
	public static String getFirstClientFromHeadersValues(String[] values)
	{
		String[] vals = getClientsFromHeadersValues(values);
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
	 * Returns all the host / proxies with client first from an array of headers' values.
	 * @param headers
	 * @return
	 */
	public static String[] getClientsFromHeadersValues(String[] values)
	{
		ArrayList<String> res=new ArrayList<String>();

		for (String value:values)
		{
			String[] clients = getClientsFromHeaderValue(value);
			for (String client:clients) res.add(client);
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
		String value=getValueFromHeader(header);
		
		return getClientsFromHeaderValue(value);
	}

	/**
	 * Returns all the host / proxies with client first from a single header's value.
	 * @param header
	 * @return
	 */
	public static String[] getClientsFromHeaderValue(String value)
	{
		ArrayList<String> res=new ArrayList<String>();

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
