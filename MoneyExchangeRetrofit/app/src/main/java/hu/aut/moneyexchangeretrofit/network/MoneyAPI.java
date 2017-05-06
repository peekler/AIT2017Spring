package hu.aut.moneyexchangeretrofit.network;

import hu.aut.moneyexchangeretrofit.data.MoneyResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoneyAPI {
    @GET("latest")
    Call<MoneyResult> getRatesToUSD(@Query("base") String base);
}
