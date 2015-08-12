package msgSender;

import getLatestVersionNum.GetSenscapeVersionNum;

import java.util.ArrayList;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class MsgUserManager {

    public class MsgUser {
        public String displayName;
        public String phoneNumber;
    }

    private final static String TAG = MsgUserManager.class.getName();
    private ArrayList<MsgUser> msgUsersList = null;
    private RestAdapter mRestAdapter = null;
    private MsgService mService = null;

    private interface MsgService {
        @GET("/send.xml")
        MsgResponse sendMsg(@Query("phone") String phone, @Query("content") String content);
    }

    public MsgUserManager() {
        msgUsersList = new ArrayList<MsgUser>();

        try {
            mRestAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://mei.senscape.cn:8080/senscapeSMS")
                    .setConverter(new SimpleXMLConverter())
                    .build();

            mService = mRestAdapter.create(MsgService.class);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG + "Create mRestAdapter failed" + e);
        }
    }

    public MsgUser createUser(String displayName, String phoneNumber) {
        MsgUser msgUser = new MsgUser();
        msgUser.displayName = displayName;
        msgUser.phoneNumber = phoneNumber;
        return msgUser;
    }

    public void addUser(MsgUser msgUser) {
        synchronized (msgUsersList) {
            if (!msgUsersList.contains(msgUser)) {
                msgUsersList.add(msgUser);
            }
        }
    }

    public void removeUser(MsgUser msgUser) {
        synchronized (msgUsersList) {
            if (msgUsersList.contains(msgUser)) {
                msgUsersList.remove(msgUser);
            }
        }
    }

    public void clearUser() {
        synchronized (msgUsersList) {
            msgUsersList.clear();
        }
    }

    public void commit() {
        synchronized (msgUsersList) {
        	String sendPhoneNums = new String();
        	String sendContent = "您的验证码是：954163。请不要把验证码泄露给其他人。【微网通联】";
            for (MsgUser l : msgUsersList) {
            	System.out.println(TAG + l.displayName + ":" + l.phoneNumber);
            	if (sendPhoneNums.length() == 0) {
                    sendPhoneNums = l.phoneNumber;
                } else {
                    sendPhoneNums = sendPhoneNums + "," + l.phoneNumber;
                }
            }
            clearUser();
            sendMsg(sendPhoneNums, sendContent);
        }
    }

    // 创建被观察者
    private Observable<MsgResponse> createMsgSender(final String phone, final String content) {
        return Observable.create(new Observable.OnSubscribe<MsgResponse>() {
            public void call(Subscriber<? super MsgResponse> subscriber) {
                try {
                    subscriber.onNext(mService.sendMsg(phone, content));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendMsg(String phoneNumList, String content) {
        Observable<MsgResponse> versionResponseObservable = createMsgSender(phoneNumList, content);

        // 订阅被观察者
        versionResponseObservable
                //.subscribeOn(Schedulers.newThread())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MsgResponse>() {
                    @Override
                    public void call(MsgResponse msgResponse) {
                    	System.out.println(TAG + "--------msgResponse.getState()-----" + msgResponse.getState());
                    	System.out.println(TAG + "--------msgResponse.getMsgID()-----" + msgResponse.getMsgID());
                    	System.out.println(TAG + "--------msgResponse.getMsgState()-----" + msgResponse.getMsgState());
                    	System.out.println(TAG + "--------msgResponse.getReserve()-----" + msgResponse.getReserve());

                        try {

                        } catch(Exception e) {
                        	System.out.println(TAG + "Problem while fetching/parsing update response" + e);
                            return;
                        }
                    }
                });
    }
    
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("MsgUserManager");

		MsgUserManager msgUserManager = new MsgUserManager();
		MsgUserManager.MsgUser msgUser = msgUserManager.createUser("阳", "13466389546");
		msgUserManager.addUser(msgUser);
		msgUser = msgUserManager.createUser("飞", "15210954842");
		msgUserManager.addUser(msgUser);
		msgUserManager.commit();

	}
}
