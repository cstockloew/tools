In order to work with the makro recorder you have to alter the source code of the universaal middleware. You have to change the method "assessContentSerialization" of the class "ServiceBusImpl.java" to the appended code.
After this change the ServiceBus will create a new textfile "test.txt" which will contain the serialized Service Requests and Service Responses.

 public static synchronized void assessContentSerialization(Resource content) {
	if (Constants.debugMode()) {
	    if (contentSerializer == null) {
		contentSerializer = (MessageContentSerializer) moduleContext
			.getContainer().fetchSharedObject(moduleContext,
				contentSerializerParams);
		if (contentSerializer == null)
		    return;
	    }

	    LogUtils
		    .logDebug(
			    moduleContext,
			    ServiceBusImpl.class,
			    "assessContentSerialization",
			    new Object[] { "Assessing message content serialization:" },
			    null);
	    // System.out.println(new RuntimeException().getStackTrace()[1]);

	    String str = contentSerializer.serialize(content);
	    if (content instanceof ServiceRequest) {
			try {
				if (file.exists()) {
					out = new BufferedWriter(new FileWriter("test.txt",true));
				}
				else {
					out = new BufferedWriter(new FileWriter("test.txt"));
				}
				out.write("\nNew Message:" + (new java.util.Date().getTime()) +"\n");
				out.write(str);
				out.close();
			} catch (IOException e) {
				LogUtils.logDebug(moduleContext, ServiceBusImpl.class, "assessContentSerialization", new Object[] {"could not write to test.txt"}, null);
				e.printStackTrace();
			}
		}
	    LogUtils
		    .logDebug(
			    moduleContext,
			    ServiceBusImpl.class,
			    "assessContentSerialization",
			    new Object[] { "\n      1. serialization dump\n",
				    str,
				    "\n      2. deserialize & compare with the original resource\n" },
			    null);
	    new ResourceComparator().printDiffs(content,
		    (Resource) contentSerializer.deserialize(str));
	}
    }