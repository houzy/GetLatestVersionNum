package getWeixinFriends;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GetWeixinFriends {

	private RestAdapter mRestAdapter = null;
	private SenscapeV2Service mService = null;
	private WeixinFriendsResponse mRepos = null;
	private ArrayList<WeixinFriendsItem> mList = null;

	private interface SenscapeV2Service {
		//获取微信好友列表
        @GET("/get_friends")
        WeixinFriendsResponse getWeixinFriends(@Query("access_token") String access_token, @Query("type") int type);
	}

	public GetWeixinFriends() {
		try {
			System.out.println("getWeixinFriendsAdatper");
			mRestAdapter = new RestAdapter.Builder()
				.setEndpoint("http://asia.senscape.com.cn/v2/api")
				.build();

			mService = mRestAdapter.create(SenscapeV2Service.class);
		}
		catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
	}

	private Observable<WeixinFriendsResponse> getWeixinFriendsList(final String access_token, final int type) {
		return Observable.create(new Observable.OnSubscribe<WeixinFriendsResponse>() {
			public void call(Subscriber<? super WeixinFriendsResponse> subscriber) {
				subscriber.onNext(mService.getWeixinFriends(access_token, type));
				subscriber.onCompleted();
				// or
				/*try {
				  subscriber.onNext(mService.getLatestVersionNum(versionNum));
				  subscriber.onCompleted();
				  } catch (Exception e) {
				  e.printStackTrace();
				  subscriber.onError(e);
				  }*/
			}
		});
	}

	private void subonObervable(String access_token, int type) {
		Observable<WeixinFriendsResponse> versionResponseObservable = getWeixinFriendsList(access_token, type);
		System.out.println("subonObervable");
		
		versionResponseObservable
			.subscribeOn(Schedulers.newThread()) //运行.subscribe返回的handle
			.observeOn(Schedulers.newThread()) //用于运行OnNext, OnCompleted & OnError等。
			.subscribe(
					new Action1<WeixinFriendsResponse>() {
						@Override
						public void call(WeixinFriendsResponse resp) {
							System.out.println("--------Response.getStatus()-----" + resp.getStatus());
							System.out.println("--------Response.getNotice()-----" + resp.getNotice());
							System.out.println("--------Response.getData()-----" + resp.getData());
							System.out.println("Running on: " + Thread.currentThread().getId());
							mList = resp.getData();
							printData();
						}
					},
					new Action1<Throwable>() {
						@Override
						public void call(Throwable e) {
							System.out.println("onError");
							e.printStackTrace();
							System.out.println("Running on: " + Thread.currentThread().getId());
						}
					});
	}
	
	private void printData() {
		for(WeixinFriendsItem l:mList) {
			System.out.println("name: " + l.getNickname());
			System.out.println("url: " + l.getHeadimgurl());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Main entry");

		System.out.println("Running on: " + Thread.currentThread().getId());
				
		GetWeixinFriends getWeixinFriend = new GetWeixinFriends();
		getWeixinFriend.subonObervable("5ae9d988ffb3e0372e953d9262e98109", 0);
		
		// 当此处没有添加延时时，使用.subscribeOn(Schedulers.newThread())
		// 会造成订阅者没有执行，原因是主线程已经退出了，其他线程也一起终结了。
		System.out.println("main before sleep");
		try {
			Thread.currentThread().sleep(3000); // 3000ms
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("main finished");
	}

}