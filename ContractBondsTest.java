package api.contracts;

import static config.PlutoConfig.COLLATERAL_BONDS;

import common.AnnotationUtils;
import common.HashMapUtils;
import common.parameterized.BaseParameterizedTest;
import common.rest.TCBSSerenityRest;
import config.PlutoConfig;
import java.util.HashMap;
import lombok.Data;
import net.thucydides.core.annotations.Title;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;

@Data
@UseTestDataFrom(value = "test-data/contracts/bonds/BondsTestCase.csv", separator = '|')
@AnnotationUtils.DebugTestIndex(4)
public class ContractBondsTest extends BaseParameterizedTest {

  private String testCaseName;
  private String prepareFileName;
  private String tokenId;
  private String expectedBody;
  private String headerParam;
  private String gaId;
  private Integer expectedHttpCode;

  private HashMap<String, Object> getHeaderParam() {
    String token = testDataManager.getUserToken(tokenId);
    return HashMapUtils.getHashMapFromJson(headerParam.replace("{token}", token));
  }

  @Title("Verify get list bonds of the companies")
  @Test
  @Override
  public void performTest() throws Exception {
    TCBSSerenityRest.given()
        .baseUri(PlutoConfig.PLUTO_DOMAIN)
        .basePath(COLLATERAL_BONDS)
        .headers(getHeaderParam())
        .contentType("application/json; charset=UTF-8")
        .queryParam("gaIds", gaId)
        .whenGet()
        .thenVerify(expectedHttpCode, expectedBody);
  }
}
