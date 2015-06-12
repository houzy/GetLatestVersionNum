package getLatestVersionNum;

import java.util.List;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public class GetSenscapeVersionNum {

	private RestAdapter restAdapter = null;
	private SenscapeV2Service service = null;
	private Gson gson = null;

	private interface SenscapeV2Service {
		@GET("/get_latest_version")
		VersionResponse getLatestVersionNum(@Query("platform") String versionNum);
	}

	public GetSenscapeVersionNum() {
		GsonBuilder gb = new GsonBuilder();
		gb.serializeNulls();
		gson = gb.create();
		try {
			System.out.println("GetSenscapeVersionNum");
			restAdapter = new RestAdapter.Builder()
				//.setConverter(new GsonConverter(gson))
				.setEndpoint("http://asia.senscape.com.cn/v2/api")
				.build();

			service = restAdapter.create(SenscapeV2Service.class);
		}
		catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
	}

	public void requestVersionNum() {
		if (service != null) {
			VersionResponse repos = service.getLatestVersionNum("0");
			System.out.println(repos.getStatus());
			System.out.println(repos.getNotice());
			System.out.println(repos.getData());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("GetLatestVersionNum");

		GetSenscapeVersionNum getSenscapeVersionNum = new GetSenscapeVersionNum();
		getSenscapeVersionNum.requestVersionNum();

	}

}

