package rxJavaTimeout;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class RxJavaTimeout {
	
	private String printFromObservable()
	{
		String str = "printFromObservable";
		System.out.println(str);
		try {
			Thread.currentThread().sleep(3000); // 3000ms
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	private Observable<String> createStringObservable() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			public void call(Subscriber<? super String> subscriber) {
				subscriber.onNext(printFromObservable());
				subscriber.onCompleted();
				// or
				/*try {
				  subscriber.onNext(printFromObservable());
				  subscriber.onCompleted();
				  } catch (Exception e) {
				  e.printStackTrace();
				  subscriber.onError(e);
				  }*/
			}
		});
	}
	
	public void stringSubscribe() {
		Observable<String> stringObservable = createStringObservable();

		stringObservable
			//.subscribeOn(Schedulers.newThread())
			//.observeOn(Schedulers.mainThread())
			//.timeout(1)
			.timeout(1000, TimeUnit.MILLISECONDS)
			.subscribe(
					new Action1<String>() {
						@Override
						public void call(String printFromObservable) {
							System.out.println(printFromObservable);
							System.out.println("fromSubscribe");
						}
					},
					new Action1<Throwable>() {
						@Override
						public void call(Throwable e) {
							System.out.println("onError");
							e.printStackTrace();
						}
					});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RxJavaTimeout rxJavaTimeout = new RxJavaTimeout();
		rxJavaTimeout.stringSubscribe();

	}

}
