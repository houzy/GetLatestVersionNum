package getLatestVersionNum;

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

public class GetSenscapeVersionNum {

	private RestAdapter mRestAdapter = null;
	private SenscapeV2Service mService = null;
	private VersionResponse mRepos = null;

	private interface SenscapeV2Service {
		@GET("/get_latest_version")
		VersionResponse getLatestVersionNum(@Query("platform") Integer versionNum);
	}

	public GetSenscapeVersionNum() {
		try {
			System.out.println("GetSenscapeVersionNum");
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

	private Observable<VersionResponse> getLatestVersionNum(final Integer versionNum) {
		return Observable.create(new Observable.OnSubscribe<VersionResponse>() {
			public void call(Subscriber<? super VersionResponse> subscriber) {
				subscriber.onNext(mService.getLatestVersionNum(versionNum));
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

	private void getLatestVersion() {
		Observable<VersionResponse> versionResponseObservable = getLatestVersionNum(0);
		System.out.println("GetLatestVersionNum");
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		Scheduler scheduler     = Schedulers.from(service);
		
		// http://stackoverflow.com/questions/20451939/observeon-and-subscribeon-where-the-work-is-being-done
		// http://stackoverflow.com/questions/7579237/whats-the-difference-between-subscribeon-and-observeon
		versionResponseObservable //注意观察main before sleep的打印时间，如果都不指定，所有执行完了才运行main
			//.subscribeOn(Schedulers.newThread()) //运行.subscribe返回的handle
			//.subscribeOn(scheduler) // 此方法用于指定一个特定的线程，newSingleThreadExecutor得到的线程main线程退出后还不退出
			//.observeOn(Schedulers.newThread()) //用于运行OnNext, OnCompleted & OnError等。
			.subscribe(
					new Action1<VersionResponse>() {
						@Override
						public void call(VersionResponse versionResponse) {
							System.out.println("--------versionResponse.getStatus()-----" + versionResponse.getStatus());
							System.out.println("--------versionResponse.getNotice()-----" + versionResponse.getNotice());
							System.out.println("--------versionResponse.getData()-----" + versionResponse.getData());
							System.out.println("Running on: " + Thread.currentThread().getId());
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Main entry");

		System.out.println("Running on: " + Thread.currentThread().getId());
				
		GetSenscapeVersionNum getSenscapeVersionNum = new GetSenscapeVersionNum();
		getSenscapeVersionNum.getLatestVersion();
		
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

