package getLatestVersionNum;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

public class GetSenscapeVersionNum {

	private RestAdapter restAdapter = null;
	private SenscapeV2Service service = null;
	private VersionResponse repos = null;

	private interface SenscapeV2Service {
		@GET("/get_latest_version")
		VersionResponse getLatestVersionNum(@Query("platform") Integer versionNum);
	}

	public GetSenscapeVersionNum() {
		try {
			System.out.println("GetSenscapeVersionNum");
			restAdapter = new RestAdapter.Builder()
				.setEndpoint("http://asia.senscape.com.cn/v2/api")
				.build();

			service = restAdapter.create(SenscapeV2Service.class);
		}
		catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
	}

	public VersionResponse getLatestVersionNum()
	{
		if (service != null)
		{
			try {
				repos = service.getLatestVersionNum(0);
			}
			catch (Exception e) {

				// TODO Auto-generated catch block

				e.printStackTrace();			
			}
		}
		return repos;
	}

	public void requestVersionNum() {
		if (service != null) {
			repos = service.getLatestVersionNum(0);	
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("GetLatestVersionNum");

		GetSenscapeVersionNum getSenscapeVersionNum = new GetSenscapeVersionNum();
		VersionResponse repos = getSenscapeVersionNum.getLatestVersionNum();
		if (repos != null) {
			System.out.println(repos.getStatus());
			System.out.println(repos.getNotice());
			System.out.println(repos.getData());
		}
		else {
			System.out.println("repos is null!");
		}

	}

}

