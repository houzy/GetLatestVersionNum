package msgSender;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "CSubmitState")
public class MsgResponse {
	
	@Element(name = "State")
	private String state = null;

	@Element(name = "MsgID")
	private String msgID = null;

	@Element(name = "MsgState")
	private String msgState = null;

	@Element(name = "Reserve")
	private String reserve = null;

	public MsgResponse() {
	}

	public String getState() {
		return state;
	};

	public String getMsgID() {
		return msgID;
	};

	public String getMsgState() {
		return msgState;
	};

	public String getReserve() {
		return reserve;
	};
}
