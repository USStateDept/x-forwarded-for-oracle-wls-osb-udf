package gov.state.irm.goss.osbextensions.udf.transport.http.xforwardedfor;

/**
 * Test cases.
 * 
 * This source code's highest classification is unclassified.
 * 
 * @author Jason Pyeron <jpyeron@pdinc.us>
 *
 */
public class XForwardedForTest
{

	String[] headers;
	Boolean valueExtractionTest;
	Boolean valueExtractionResult;
	
	public XForwardedForTest(String header, boolean valueExtraction)
	{
		XForwardedForTest(new String[]{header},valueExtraction);
	}

	public void XForwardedForTest(String[] headers, boolean valueExtraction)
	{
		this.headers=headers;
		this.valueExtractionResult=valueExtraction;		
	}

	public static void main(String[] args)
	{
//		testValueExtraction();
	
		String[][] tests={{"X-Forwarded-For: 127.0.0.1"},
				{"X-Forwarded-For: 127.0.0.1, localhost, bob.com"},
				{"X-Forwarded-For: "},
				{"X-Forwarded-For:"},
				{"X-Forwarded-For"},
				{"X-Forwarded-For:127.0.0.1"},
				{"X-Forwarded-For:127.0.0.1","X-Forwarded-For:foo.bar","X-Forwarded-For: proxy"},
};

		for (String[] test:tests)
		{
			for (int i=0; i<test.length; ++i) System.out.println((i!=0?" ":"")+test[i]);
			String[] r = XForwardedFor.getClientsFromHeaders(test);
			for (String v:r)
			{
				System.out.println("\t"+v);
			}
		}
		
//		System.out.println(XForwardedFor.getValueFromHeader("X-Forwarded-For: 127.0.0.1"));
//		System.out.println(XForwardedFor.getValueFromHeader("X-Forwarded-For: 127.0.0.1, localhost, bob.com"));
//		System.out.println(XForwardedFor.getValueFromHeader("X-Forwarded-For: "));
//		System.out.println(XForwardedFor.getValueFromHeader("X-Forwarded-For:"));
//		System.out.println(XForwardedFor.getValueFromHeader("X-Forwarded-For"));
//		System.out.println(XForwardedFor.getValueFromHeader("X-Forwarded-For:127.0.0.1"));
////		System.out.println(XForwardedFor.getValueFromHeader("X-Forwarded-For: 127.0.0.1"));
////		System.out.println(XForwardedFor.getValueFromHeader("X-Forwarded-For: 127.0.0.1"));
		
	}

	
	static XForwardedForTest[] tests=
	{
		new XForwardedForTest("X-Forwarded-For: 127.0.0.1",true)
	};
	
	private static void testValueExtraction()
	{
		for (XForwardedForTest test:tests)
		{
			if (test.valueExtractionTest==null) continue;
			for (String header:test.headers)
			{
				String r = XForwardedFor.getValueFromHeader(header);
//				if (!test.valueExtractionResult.equals(r))
			}
		}
		
	}
	
}
