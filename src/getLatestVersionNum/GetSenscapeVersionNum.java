package getLatestVersionNum;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

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
                try {
                    subscriber.onNext(mService.getLatestVersionNum(versionNum));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	private void getLatestVersion() {
        Observable<VersionResponse> versionResponseObservable = getLatestVersionNum(0);

        versionResponseObservable
                //.subscribeOn(Schedulers.newThread())
                //.observeOn(Schedulers.mainThread())
                .subscribe(new Action1<VersionResponse>() {
                    @Override
                    public void call(VersionResponse versionResponse) {
                    	System.out.println("--------versionResponse.getStatus()-----" + versionResponse.getStatus());
                    	System.out.println("--------versionResponse.getNotice()-----" + versionResponse.getNotice());
                    	System.out.println("--------versionResponse.getData()-----" + versionResponse.getData());

                    }
                });
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("GetLatestVersionNum");

		GetSenscapeVersionNum getSenscapeVersionNum = new GetSenscapeVersionNum();
		getSenscapeVersionNum.getLatestVersion();
		
	}

}

