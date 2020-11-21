package com.metis.paas;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lkx.util.ExcelUtil;
import com.metis.entity.Comparator2Note;
import com.metis.entity.excel.*;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: ToolOfExcel
 * @Author: YiHui
 * @Date: 2020-11-10 13:52
 * @Version: ing
 */
public class ToolOfExcel {
    public static final String folderPath = "./repository/";
    public static final String folderPath1 = "./repository/CityLevel/";
    public static final String folderPath11 = "./repository/CityLevel/railStations/";
    public static final String folderPath2 = "./repository/GB2260/";
    public static final String folderPath3 = "H:\\Code\\webdriver-master\\geckodriver.exe";

    private static final String SourceUrl1 = "https://qq.ip138.com/train/";
    private static final String ProvinceAndUrlCSS1 = "tbody>tr>td>a";
    private static final String FinishFlag1 = ".hd";

    private static final String SourceUrl2 = "http://www.chinafilm.org.cn/Item/list.asp?id=1619&areaid=104&C=0000000000";
    //            td下面的所有a元素: <a href ="url"
    private static final String ProvinceAndUrlCSS2 = "#ejlmbox > div.container >a";
    private static final String ProvinceAndUrlClickCSS2 = ".citybtn";
    private static final String CinemaAndUrlCSS2 = "#lefttips > li";
    private static final String FinishFlag2 = ".copyright";
//    这是假设没有 一二三四五六区这样长的区名啊!有的话就是无法解析会放在控制台再人为收集或者一个list收集
    private static final Pattern P2 = Pattern.compile("([省市])([\\u4e00-\\u9fa5]{1,5}(区|州|自治区|县|市))");
    private static final Pattern P21 = Pattern.compile("([\\u4e00-\\u9fa5]{1,5}(区|州|自治区|县|市))");

    private static final String SourceUrl3 = "http://www.icbc.com.cn/icbc/%E5%AE%A2%E6%88%B7%E6%9C%8D%E5%8A%A1/business_offices.htm";
    private static final String ProvinceAndUrlCSS3 = "#dumaScrollAreaId_89Area > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > span:nth-child(1) > a:nth-child(1)";
    private static final String ProvinceAndUrlClickCSS3 = "#icbccity";
    private static final String FinishFlag3 = ".n-blue";

    private static final String SourceUrl4 = "http://map.cmbchina.com/";
    private static final String FinishFlag42 = "#citySelect";
    private static final String FinishFlag43 = "#branchdiv";

    private static final Pattern P = Pattern.compile("(\\d+)");

    private static final String js1 = "document.getElementById('hideSerarchBtn').style.display='block'";
    private static final String js2 = "var a = document.getElementsByTagName(\"div\");" +
            "for( var i =0;i<a.length;i++){if (a[i].style.display === \"none\") a[i].style.display='block';}" +
            "var a = document.getElementsByClassName(\"address\");" +
            "for( var i =0;i<a.length;i++){a[i].style.display='block';}";



    @Test
    void json2ExcelGB2260() throws Exception{
        int startYear = 1980;
        for( int i = 0; i < 2020 - startYear+1;i++){
            List<CodeOfRegion> codeOfRegionList = new ArrayList<>(1024);

            String curYear = String.valueOf (startYear + i);
            File jsonFile = ResourceUtils.getFile( folderPath2 + curYear+ ".json" );
            String json = FileUtils.readFileToString(jsonFile);
            // json 类型的String字符串转换为json数组
            JSONArray jsonArray = JSON.parseArray(json);
            // 遍历json数组中的json对象并解析填充
            for( Object obj : jsonArray ){
                JSONObject jObj = (JSONObject) obj;
                String code = jObj.getString("code");
                String name = jObj.getString("name");
                CodeOfRegion codeOfRegion = new CodeOfRegion();
                codeOfRegion.setRegionCode( code );
                codeOfRegion.setCounty( name );
                codeOfRegionList.add( codeOfRegion );
            }
            ExcelUtil.exportExcel( folderPath2 + "res/" + curYear+"codeOfRegion.xlsx",codeOfRegionList,CodeOfRegion.class);

        }
    }
    @Test
    List<List<RegionOfChina>> getAllTypeList() throws Exception{
        List<RegionOfChina> regionOfChinaList = ExcelUtil.readXls( folderPath1+"中国省市区.xlsx",RegionOfChina.class);
        HashMap<String, String> map = new HashMap<>(1024);
//        县级市归属地级市,还有部分归属省
        List<CountyLevelCity> countyLevelCityList = ExcelUtil.readXls( folderPath1 + "县级市.xlsx",CountyLevelCity.class);
        String city;String cityName;
        for( CountyLevelCity c : countyLevelCityList ){
            city = c.getCity();
//            如果只是indexOf("市"),有可能出现"市中市"这种情况
            cityName = city.substring(0,city.lastIndexOf("市"));
            map.put( cityName , c.getProvince());
        }
        List<RegionOfChina> countyLevelCityMoreList = new ArrayList<>();
        for( int i = regionOfChinaList.size() -1;i>=0;i-- ){
            String checkCounty = regionOfChinaList.get(i).getCounty();
            String checkCountyName = checkCounty.substring(0,checkCounty.length()-1);
            if( map.containsKey(checkCountyName )){
                regionOfChinaList.get(i).setCounty( checkCountyName+"市" );
                countyLevelCityMoreList.add( regionOfChinaList.get(i));
//                为了防止"东港市"和"东港区"同时存在于regionOfChinaList中的情况
                map.remove(checkCountyName);
                regionOfChinaList.remove(i);
            }
        }

//        市辖区归属市
        List<CountyOfCity> countyOfCityList = ExcelUtil.readXls( folderPath1 + "市辖区.xlsx",CountyOfCity.class);
        String county;
        for( CountyOfCity c : countyOfCityList ){
            county = c.getCounty();
            String countyName = county.substring(0,county.lastIndexOf("区") );
            map.put( countyName ,c.getCity());
        }

        List<RegionOfChina> countyOfCityMoreList = new ArrayList<>();
        for( int i =regionOfChinaList.size() -1;i>=0;i-- ){
            String checkCounty = regionOfChinaList.get(i).getCounty();
//            多亏了这个数据是干净一点的(尾部没有空格),要不然该多判断截到尾部了
            String checkCountyName = checkCounty.substring(0,checkCounty.length()-1);
            if( map.containsKey(checkCountyName )){
                regionOfChinaList.get(i).setCounty( checkCountyName+"区" );
                countyOfCityMoreList.add( regionOfChinaList.get(i));

                map.remove( checkCountyName);
                regionOfChinaList.remove(i);
            }
        }
        if( countyOfCityMoreList.size() < countyOfCityList.size() ){
            System.out.println( countyOfCityList.size()  - countyOfCityMoreList.size() );
        }
//        ExcelUtil.exportExcel(folderPath1+"纯县.xlsx",regionOfChinaList,RegionOfChina.class);
//        来源的中国省市区应当全面!!
//        否则以芜湖县改名湾沚区为例,清洗之后的countyOfCityMoreList中湾沚区将不会加入
//        同时芜湖县将保存在regionOfChinaList中
        List<List<RegionOfChina>> joinList = new ArrayList<>();
        joinList.add( countyLevelCityMoreList);
        joinList.add( countyOfCityMoreList);
        joinList.add( regionOfChinaList );
        return joinList;
    }

    @Test
    void getTypeOfRegion() throws Exception{
        List<List<RegionOfChina>> allTypeList = getAllTypeList();
         List<TypeOfRegion> typeOfRegionList = new ArrayList<>(1024);
         for( int i =0;i<3;i++){
            for( RegionOfChina r : allTypeList.get(i) ){
                TypeOfRegion typeOfRegion = new TypeOfRegion();
                typeOfRegion.setRegionCode( r.getRegionCode() );
                typeOfRegion.setProvince( r.getProvince() );
                typeOfRegion.setCity( r.getCity() );
                String county = r.getCounty();
                typeOfRegion.setCounty( county );
//  如果只是用county.contains("市")判断,像"市北区"这种就会被误判成"县级市"
                if(county.contains("市") && county.lastIndexOf("市") == county.length()-1 ){
                    typeOfRegion.setTypeOfRegion( "县级市");
                }else if(county.contains("区") && county.lastIndexOf("区") == county.length()-1){
                    typeOfRegion.setTypeOfRegion( "市辖区" );
                }else{
                    typeOfRegion.setTypeOfRegion("县");
                }
                typeOfRegionList.add( typeOfRegion);
            }
         }
//        输出结果并没有排序,不过排序的事,excel很擅长!
        ExcelUtil.exportExcel(folderPath1+"type.xlsx",typeOfRegionList,TypeOfRegion.class);
    }

    @Test
    void comparator2Note() throws Exception {
        List<Comparator2Note> comparator2NoteList = ExcelUtil.readXls( folderPath + "Comparator2Note.xlsx",Comparator2Note.class);
        List<Comparator2Note> resList = new ArrayList<>(3000);
        for( Comparator2Note c : comparator2NoteList ){
            String k1 = c.getK1();
            String k2 = c.getK2();
            if( !k1.equals( k2 )){
                c.setNote( k2 );
            }
            resList.add( c );
        }
        ExcelUtil.exportExcel(folderPath + "C2N.xlsx",resList,Comparator2Note.class);

    }

    /**
     * 直到timeOut时间到了之前,一直寻找findElement()中的内容
     *  等待网页加载完成:等待js网页跳转
     */
    private static void waitPageLoad(WebDriver webDriver,String finishFlag) {
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
//          有八种元素定位方法: d, name, class name, tag name, link text,部分link text, xpath, css选择器
                return d.findElement(By.cssSelector(finishFlag));
            }
        });
    }

    @Test
    void getRailStationList() throws Exception{
        List<List<RailStationNum>> allStationList = new ArrayList<>(64);
        Map<String, String> provinceAndUrl = getProvinceAndUrl(SourceUrl1,ProvinceAndUrlCSS1,ProvinceAndUrlClickCSS2 , FinishFlag1, false);
//        boolean flag = false;
        for (Map.Entry<String, String> e1 : provinceAndUrl.entrySet()) {
//              记下服务器错误使强制停止的部分并跳过
//            if( e1.getKey().equals("河北")){ flag = true; }
//            if( !flag ) continue;
            WebDriver webDriver = getFirefoxWebDriver();
            Map<String, String> citiesAndUrl = getCitiesAndUrl(e1,ProvinceAndUrlCSS1,FinishFlag1,false );
            List<RailStationNum> stationList = new ArrayList<>(256);
            for(Map.Entry<String, String> e2 : citiesAndUrl.entrySet() ){
                System.out.println("======"+e2.getKey()+"=====");
                webDriver.get(e2.getValue());
                waitPageLoad( webDriver,FinishFlag1);
//                这个line依然是全局定位的
                String line = webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div[1]/p[1]")).getText();
                Matcher m = P.matcher(line);
                RailStationNum railStationNum = new RailStationNum();
                if( m.find() ){
                    railStationNum.setProvince( e1.getKey() );
                    railStationNum.setStation( e2.getKey());
                    railStationNum.setRailwayNum( m.group() );
                }
                stationList.add(railStationNum);
            }
            ExcelUtil.exportExcel(folderPath11+e1.getKey()+"railStation.xlsx",stationList, RailStationNum.class);
            allStationList.add( stationList );
            webDriver.close();
        }
        List<RailStationNum> longAllStationList = new ArrayList<>(4096);
        for( List<RailStationNum> l : allStationList) {
            longAllStationList.addAll(l);
        }
        ExcelUtil.exportExcel( folderPath1+"railStation.xlsx",longAllStationList, RailStationNum.class);

    }

    @Test
    void getCinema() throws Exception{
        List<List<CinemaNum>> allCinemaList = new ArrayList<>(64);
        Map<String, String> provinceAndUrl = getProvinceAndUrl(SourceUrl2,ProvinceAndUrlCSS2,ProvinceAndUrlClickCSS2 , FinishFlag2, true);
        for (Map.Entry<String, String> e1 : provinceAndUrl.entrySet()) {
            Map<String, Integer> citiesAndCinemaNum= getCitiesAndCinemaNumByJsoup(e1);
            List<CinemaNum> cinemaNumList = new ArrayList<>(256);
            for(Map.Entry<String, Integer> e2 : citiesAndCinemaNum.entrySet() ){
                System.out.println( e2.getKey() + e2.getValue());
                CinemaNum cinemaNum = new CinemaNum();
                cinemaNum.setProvince( e1.getKey() );
                cinemaNum.setCounty( e2.getKey() );
                cinemaNum.setCinemaNum( e2.getValue() );
                cinemaNumList.add(cinemaNum);
            }
            allCinemaList.add(cinemaNumList);
        }
        List<CinemaNum> longAllCinemaList = new ArrayList<>(4096);
        for( List<CinemaNum> l : allCinemaList) {
            longAllCinemaList.addAll(l);
        }

        ExcelUtil.exportExcel(folderPath1+"cinemaList.xlsx",longAllCinemaList,CinemaNum.class);

    }

    @Test
    void getICBCStation() {
        WebDriver webDriver = getFirefoxWebDriver();

        Map<String, String> provinceAndUrl = getProvinceAndUrl( SourceUrl3,ProvinceAndUrlCSS3,ProvinceAndUrlClickCSS3, FinishFlag3, true);
        for( Map.Entry<String, String> e1 : provinceAndUrl.entrySet()){
            System.out.println( e1.getKey() + e1.getValue() );
        }

        webDriver.close();
    }

    @Test
    void getCMBStation() throws  Exception{
        List<BankStaionNum> allStationNumList = new ArrayList<>(4096);

        WebDriver webDriver = getFirefoxWebDriver();
        webDriver.get( SourceUrl4 );
        waitPageLoad(webDriver,FinishFlag2 );

//        -1是因为会多一个"选择省份"的options
        int provinceListSize = webDriver.findElements(By.cssSelector("select[name='provSelect'] > option")).size()-1;
//        在 WebDriver 中专门提供了一个 Select 类来处理下拉菜单
        Select provSelect = new Select(webDriver.findElement(By.cssSelector("select[name='provSelect']")));

        for( int i = 0;i<provinceListSize;i++ ){
            provSelect.selectByValue(String.valueOf(i));
            waitPageLoad(webDriver,FinishFlag42 );

            Select citySelect = new Select( webDriver.findElement( By.cssSelector("select[name='citySelect")));
            List<WebElement> cityEle =  webDriver.findElements(By.cssSelector("select[name='citySelect'] > option"));

            for( WebElement e : cityEle ){
                String text  = e.getText();
                if( !text.equals("选择城市")){
                    citySelect.selectByVisibleText(text);
                    waitPageLoad(webDriver,FinishFlag42 );
                    System.out.println( text );
                    webDriver.findElement(By.id("address")).sendKeys(text);
//                     一套本来应该是有用的操作:https://stackoverflow.com/questions/12371228/selenium-webdriver-hidden-select-and-anchor
//                    WebElement parentButton =  webDriver.findElement(By.id("imgSearch"));
//                    WebElement hideSearchButton = webDriver.findElement(By.id("hideSerarchBtn"));
//                    parentButton.click();
//                    hideSearchButton.click();
//                    Actions builder = new Actions(webDriver);
//                    builder.moveToElement(parentButton).perform();
//                    Thread.sleep(3000L);
//                    hideSearchButton.click();
                    WebElement hideSearchButton = webDriver.findElement(By.id("hideSerarchBtn"));
                    ((JavascriptExecutor)webDriver).executeScript(js1);
//                    Thread.sleep(3000L);
                    hideSearchButton.click();
                    waitPageLoad(webDriver,FinishFlag43 );

                    ((JavascriptExecutor)webDriver).executeScript(js2);
//                    Thread.sleep(3000L);

                    Map<String, Integer> countyAndNum  = getCountyAndCMBNum(webDriver);
                    for( Map.Entry<String,Integer> m : countyAndNum.entrySet() ){
                        BankStaionNum bankStaionNum = new BankStaionNum();
                        bankStaionNum.setProvince(String.valueOf(i));
                        bankStaionNum.setCounty(m.getKey());
                        bankStaionNum.setStaionNum( m.getValue() );
                        allStationNumList.add(bankStaionNum);
                    }
                }
            }
        }
        ExcelUtil.exportExcel( folderPath1+"CMBStaionNumList.xlsx",allStationNumList, BankStaionNum.class);
    }

    @Test
    void Ask(){
        WebDriver webDriver = getFirefoxWebDriver();
        webDriver.get( SourceUrl4 );
        waitPageLoad(webDriver,FinishFlag2 );
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((JavascriptExecutor)webDriver).executeScript(js2);
//        System.out.println( webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div/select[1]/option[2]")).getText());
        Select provSelect = new Select(webDriver.findElement(By.cssSelector("select[name='provSelect']")));
        List<WebElement> provinceEle = webDriver.findElements(By.cssSelector("select[name='provSelect'] > option"));

        System.out.println( webDriver.findElement(By.cssSelector("#provSelect > option:nth-child(2)")).getText());
        for( WebElement p : provinceEle ){
//            provSelect.selectByValue(String.valueOf(i));
            String t = p.getText();
            System.out.println("====="+ t +"=====");
            provSelect.selectByVisibleText(t);
            waitPageLoad(webDriver,FinishFlag42 );
        }
    }

    private WebDriver getFirefoxWebDriver(){
//            环境变量
        System.setProperty("webdriver.gecko.driver", folderPath3 );
        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        FireFox支持无头浏览器模式
//        firefoxOptions.addArguments("-headless");
//            打开火狐浏览器的一些设置
        WebDriver webDriver = new FirefoxDriver(firefoxOptions);
        webDriver.manage().window().setSize(new Dimension(600,500));
//            左上角的位置
        webDriver.manage().window().setPosition(new Point(326,40));
        return webDriver;
    }

    private Map<String, String> getProvinceAndUrl(String sourceUrl, String css, String clickCss, String finishFlag, Boolean isClick){
        WebDriver webDriver = getFirefoxWebDriver();
        webDriver.get(sourceUrl);
        waitPageLoad( webDriver,finishFlag);
        if( isClick ){
//            WebElement popButtonParent = webDriver.findElement(By.xpath("/html/body/form/div[2]/table/tbody/tr/td[2]/div/table/tbody/tr/td[1]/div"));
//           popButtonParent.click();
//           WebElement popButton = webDriver.findElement( By.xpath("//*[@id=\"icbccity\"]"));
            WebElement popButton = webDriver.findElement(By.cssSelector(clickCss));
            popButton.click();

        }
        List<WebElement> provinces = webDriver.findElements(By.cssSelector(css));
        Map<String, String> provinceAndUrl = new LinkedHashMap<>(128);
        for (WebElement province : provinces) {
            provinceAndUrl.put(province.getText(),province.getAttribute("href"));
        }
        webDriver.close();
        return provinceAndUrl;
    }

    private Map<String, String> getCitiesAndUrl(Map.Entry<String, String> e, String css,String finishFlag,Boolean isClick){
        WebDriver webDriver = getFirefoxWebDriver();
        System.out.println("======" + e.getKey() + "=====");
        webDriver.get(e.getValue());
        waitPageLoad(webDriver,finishFlag);
        List<WebElement> cities = webDriver.findElements(By.cssSelector(css));
        Map<String, String> citiesAndUrl = new LinkedHashMap<>(128);
        for (WebElement city : cities) {
            citiesAndUrl.put(city.getText(), city.getAttribute("href"));
        }
//        if(isClick){
//
//        }
        webDriver.close();
        return citiesAndUrl;
    }

    private Map<String, Integer> getCountyAndCMBNum(WebDriver webDriver) {
        List<WebElement> divs =  webDriver.findElements(By.cssSelector("#branchdiv > div "));
        Map<String,Integer> countyAndCMBNum = new HashMap<>(256);
        for( WebElement a : divs ){
            a.findElement(By.cssSelector("p.address"));
            String county = washRegionData( a.getText() );
            countyAndCMBNum.put( county, countyAndCMBNum.getOrDefault( county,0)+1 );
        }
        return  countyAndCMBNum;
    }

    private Map<String, Integer> getCitiesAndCinemaNum(Map.Entry<String, String> e, String css,String finishFlag,Boolean isClick)  {
        WebDriver webDriver = getFirefoxWebDriver();
        System.out.println("======" + e.getKey() + "=====");
        webDriver.get(e.getValue());
        waitPageLoad(webDriver,finishFlag);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        List<WebElement> cities = webDriver.findElements(By.cssSelector(css));
        Map<String,Integer> countyAnmCinemaNum = new HashMap<>(256);
        for( WebElement city : cities ){
//            目前看来,让WebElement对象先find()是聚焦的过程;如果没有这一步,WebElement的范围仍然是整个页面
//            而如果将聚焦的结果当作是遍历得到的结果,聚焦点就始终会在模糊匹配第一个满足的地方,取到的也就始终是第一个<li>的内容
            city.findElement(By.xpath("//*[contains(@id,'lab')]"));
//            只能说经验是: 非直接取Text可以是以每一个<li>作为对象
            String text = city.getText();
            Matcher m = P2.matcher(text);
            if( m.find() ){
                countyAnmCinemaNum.put(m.group(0), countyAnmCinemaNum.getOrDefault( m.group(0),0) +1);
            }
        }

        webDriver.close();
        return countyAnmCinemaNum;
    }

    @Test
    Map<String, Integer> getCitiesAndCinemaNumByJsoup(Map.Entry<String, String> e) throws Exception{
        WebDriver webDriver = getFirefoxWebDriver();
        System.out.println("======" + e.getKey() + "=====");
        Document doc = Jsoup.connect(e.getValue() ).get();
        Map<String,Integer> countyAnmCinemaNum = new HashMap<>(256);
        Elements links = doc.getElementsByTag("lable");
        for (Element idText : links) {
            //<lable id="lab_1765">
            if (idText.hasAttr("id")) {
                String text = idText.text();
                String county = washRegionData(text);
                countyAnmCinemaNum.put(county, countyAnmCinemaNum.getOrDefault( county,0) +1);
            }
        }
        webDriver.close();
        return countyAnmCinemaNum;
    }

    private String washRegionData( String text){
        Matcher m = P2.matcher(text);
        Matcher mm = P21.matcher(text);

        String county = null;
        if( m.find() ){
            county = m.group(0);
            if( county.indexOf("市") == 0 || county.indexOf("省") == 0 ){
                county = county.substring(1);
            }
        }else if( mm.find()){
            county = mm.group(0);
        }else{
            System.out.println( "Text出错:"+ text);
        }
        return county;
    }
}
