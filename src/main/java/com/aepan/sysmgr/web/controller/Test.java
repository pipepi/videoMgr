/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.aepan.sysmgr.model.Logistics;
import com.aepan.sysmgr.model.PartnerProduct;
import com.aepan.sysmgr.model.PartnerProductArray;
import com.aepan.sysmgr.model.PartnerProductSt;
import com.aepan.sysmgr.model.SKUArray;
import com.aepan.sysmgr.model.SkusArray;
import com.google.gson.Gson;

/**
 * @author rakika
 * 2015年8月28日下午10:37:30
 */
public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
	/*	
		String ret = "{\"data\":{\"json\":[{\"Name\":\"Color\",\"SpecId\":1,\"Values\":[{\"Id\":\"618\",\"isPlatform\":true,\"Name\":\"白色\",\"Selected\":false},{\"Id\":\"619\",\"isPlatform\":true,\"Name\":\"黑色\",\"Selected\":false},{\"Id\":\"620\",\"isPlatform\":true,\"Name\":\"红色\",\"Selected\":false}]},{\"Name\":\"Size\",\"SpecId\":2,\"Values\":[{\"Id\":\"73\",\"isPlatform\":true,\"Name\":\"10.1寸\",\"Selected\":false},{\"Id\":\"74\",\"isPlatform\":true,\"Name\":\"12寸\",\"Selected\":false},{\"Id\":\"75\",\"isPlatform\":true,\"Name\":\"14寸\",\"Selected\":false},{\"Id\":\"610\",\"isPlatform\":true,\"Name\":\"15寸\",\"Selected\":false},{\"Id\":\"611\",\"isPlatform\":true,\"Name\":\"17寸\",\"Selected\":false},{\"Id\":\"612\",\"isPlatform\":true,\"Name\":\"18寸\",\"Selected\":false},{\"Id\":\"613\",\"isPlatform\":true,\"Name\":\"19寸\",\"Selected\":false},{\"Id\":\"614\",\"isPlatform\":true,\"Name\":\"哒哒哒大的寸\",\"Selected\":false}]},{\"Name\":\"Version\",\"SpecId\":3,\"Values\":[{\"Id\":\"76\",\"isPlatform\":true,\"Name\":\"i3 CPU\",\"Selected\":false},{\"Id\":\"77\",\"isPlatform\":true,\"Name\":\"i5 CPU/独显\",\"Selected\":false},{\"Id\":\"78\",\"isPlatform\":true,\"Name\":\"i7 CPU/独显\",\"Selected\":false}]}],\"tableData\":{\"cost\":[],\"mallPrice\":[],\"productId\":127,\"sku\":[],\"stock\":[]}}}";
		System.out.println(ret);
		
		
        	Gson gson = new Gson();
    		SKUArray hp = gson.fromJson(ret, SKUArray.class);
    		
    		System.out.println(hp.getData());
    		
    		PartnerProductSt hps = hp.getData();
    		
    		List<PartnerProductJson> lppj =  hps.getJson();
    		
    		System.out.println(hps.getJson().size());
//    		System.out.println(hps.getTableData().getProductId());
    		
    		for(int i = 0; i < lppj.size(); i++){
    			PartnerProductJson hj = lppj.get(i);
    			System.out.println(hj.getName());
    			System.out.println(hj.getSpecId());
    			System.out.println(hj.getValues());
    			List<PartnerProductValue> lppv = hj.getValues();
    			for(int j = 0; j < lppv.size(); j++){
    				PartnerProductValue ppv = lppv.get(j);
    				System.out.println(ppv.getId());
    				System.out.println(ppv.getIsPlatform());
    				System.out.println(ppv.getName());
    				System.out.println(ppv.getSelected());
    				System.out.println(ppv.getClass());
    			}
    			System.out.println("***************");
    		}*/
		
		
    		/*PartnerProductTableData pptd = hps.getTableData();
    		System.out.println("####################");
    		System.out.println(pptd.getCost());
    		System.out.println(pptd.getMallPrice());
    		System.out.println(pptd.getProductId());
    		System.out.println(pptd.getSku());
    		System.out.println(pptd.getStock());*/
		
		
//		String shopId = "1";
//		
//		String sign = EncryptUtil.getMd5Str(shopId + "azt");;
//		
//		String partnerId = "1";
//		
//		String url = "http://192.168.1.250/selleradmin/manage/product";
//		
//		PostMethod method = new PostMethod(url);
//	    HttpClient client = new HttpClient();
//	    method.addParameter("shop_id", shopId);//采用MD5加密
//		method.addParameter("sign", sign);//采用MD5加密
//		method.addParameter("partner_id", partnerId);//采用MD5加密
//		System.out.println(method.toString());
//		client.executeMethod(method);
//		String ret = method.getResponseBodyAsString();
//		System.out.println(method.getStatusCode());
//		System.out.println(ret);
//		method.getStatusCode();
//		//
//		JsonElement jsonElement = new JsonParser().parse(ret);
//		JsonObject obj = jsonElement.getAsJsonObject();
		
//		System.out.println("result json :::::::" + getProductJson());
		
//		System.out.println(getSkuList("64", "127").size());
		
//		getTestProductJson();
//		getProductJson();
		
//		String[][] test ={{"131_0_75_78","132_0_0_0","129_0_613_76","318_58_0_63","318_62_0_63"},{"1","2","1","1","1"}};
//		System.out.println(test[0].length);
//		System.out.println(test[1].length);
//		String[] row1 = test[0];
//		String[] row2 = test[1];
//		for(int i = 0 ; i < row1.length; i++){
//			System.out.println(row1[i]);
//		}
//		System.out.println("######################");
//		for(int i = 0 ; i < row2.length; i++){
//			System.out.println(row2[i]);
//		}
		
//		String test = "131_0_75_78,132_0_0_0";
//		String[] tt = test.split(",");
//		System.out.println(tt.length);
//		System.out.println(tt.toString());
		
//		getTestLogitics("shentong", "229340756417");
		
		
//		String url="http://192.168.1.250:8080/VideoInterface/ListProductDetail?saleStatus=1&auditStatus=2&page=1&rows=100&ids=318,319,265";
		
		String url="http://192.168.1.183:8080/api/member/videoauth";
		
		PostMethod method = new PostMethod(url);
		method.setRequestBody("{id:279}");
		method.setRequestHeader("Content-Type", "application/json");
        
		HttpClient client = new HttpClient();

		System.out.println(method.toString());
		String ret = "";
		try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			System.out.println("-------------"+ret);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static String getTestLogitics(String logisticsCompany, String logisticsNum){
		String url = "http://www.kuaidi100.com/query?type=" + logisticsCompany 
				+ "&postid=" + logisticsNum;
		
        GetMethod method = new GetMethod(url);
        
		HttpClient client = new HttpClient();

		System.out.println(method.toString());
		String ret = "";
		try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			System.out.println("-------------"+ret);
			
	        if(method.getStatusCode() == 200 ){
	        	Gson gson = new Gson();
	        	Logistics ls = gson.fromJson(ret, Logistics.class);
	        	System.out.println(ls.getCodenumber());
	        	System.out.println(ls.getStatus());
	        	System.out.println(ls.getData().size());
	        	
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public static String getTestProductJson(){
//		String img = "http://mall-azt.chinacloudapp.cn";
		
//      String url = "http://mall-azt.chinacloudapp.cn/SellerAdmin/product/list?auditStatus=2&page=1&row=100";
//		String url = "http://192.168.1.250:8080/SellerAdmin/product/ListProduct?auditStatus=2&page=1&rows=20&saleStatus=1&ids=318,319,265";
        
//	    String url = "http://192.168.1.250:8080/SellerAdmin/Product/GetSpecifications?categoryId=64&productId=319";
//	    String url = "http://192.168.1.250:8080/SellerAdmin/Product/GetSpecifications?categoryId=119&productId=318";
//		String url="http://192.168.1.250:8080/SellerAdmin/product/GetSpecifications?productId=232&categoryId=204";
//		String url = "http://192.168.1.250:8080/Product/GetSKUsInfo?skus=131_0_75_78,132_0_0_0,129_0_613_76,318_58_0_63,318_62_0_63";
//		String url = "http://192.168.1.250:8080/Product/updateStock?skus=131_0_75_78,132_0_0_0,129_0_613_76,318_58_0_63,318_62_0_63&quantitys=1,2,3,4,5";
		
//		String url="http://192.168.1.250:8080/SellerAdmin/product/GetSpecifications?productId=232&categoryId=204";
		
		String url = "http://192.168.1.250:8080/Product/GetSKUByProductId?pId=319";
		
		
//        String url = "http://mall-9cooo-01.chinacloudapp.cn/SellerAdmin/Product/GetSpecifications?categoryId=120&productId=311";
   
        GetMethod method = new GetMethod(url);

		HttpClient client = new HttpClient();

		System.out.println(method.toString());
		String ret = "";
		try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			System.out.println("-------------"+ret);
			
	        if(method.getStatusCode() == 200 ){
	        	Gson gson = new Gson();
	        	SkusArray sa = gson.fromJson(ret, SkusArray.class);
//	        	PartnerProductPage hp = gson.fromJson(ret, PartnerProductPage.class);
	        	System.out.println(sa.getSkus().size());
	        	
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	
	
	/*public static String getTestProductJson(){
//		String img = "http://mall-azt.chinacloudapp.cn";
		
//      String url = "http://mall-azt.chinacloudapp.cn/SellerAdmin/product/list?auditStatus=2&page=1&row=100";
		String url = "http://192.168.1.250:8080/SellerAdmin/product/ListProduct?auditStatus=2&page=1&rows=9&saleStatus=1&ids=316";
					
        
//        String url = "http://mall-9cooo-01.chinacloudapp.cn/SellerAdmin/Product/GetSpecifications?categoryId=120&productId=311";
//	    String url = "http://192.168.1.250:8080/SellerAdmin/Product/GetSpecifications?categoryId=64&productId=127";
   
        GetMethod method = new GetMethod(url);
        
        
        
        
		HttpClient client = new HttpClient();

		System.out.println(method.toString());
		String ret = "";
		try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			System.out.println("-------------"+ret);
			
			
	        if(method.getStatusCode() == 200 ){
	        	Gson gson = new Gson();
	    		SKUArray hp = gson.fromJson(ret, SKUArray.class);
	    		
	    		System.out.println(hp.getData());
	    		
	    		PartnerProductSt hps = hp.getData();
	    		
	    		List<PartnerProductJson> lppj =  hps.getJson();
	    		
	    		System.out.println(hps.getJson().size());
//	    		System.out.println(hps.getTableData().getProductId());
	    		
	    		for(int i = 0; i < lppj.size(); i++){
	    			PartnerProductJson hj = lppj.get(i);
	    			System.out.println(hj.getName());
	    			System.out.println(hj.getSpecId());
	    			System.out.println(hj.getValues());
	    			List<PartnerProductValue> lppv = hj.getValues();
	    			for(int j = 0; j < lppv.size(); j++){
	    				PartnerProductValue ppv = lppv.get(j);
	    				System.out.println(ppv.getId());
	    				System.out.println(ppv.getIsPlatform());
	    				System.out.println(ppv.getName());
	    				System.out.println(ppv.getSelected());
	    				System.out.println(ppv.getClass());
	    			}
	    			System.out.println("***************");
	    		}
//	    		PartnerProductTableData pptd = hps.getTableData();
//	    		System.out.println("####################");
//	    		System.out.println(pptd.getCost());
//	    		System.out.println(pptd.getMallPrice());
//	    		System.out.println(pptd.getProductId());
//	    		System.out.println(pptd.getSku());
//	    		System.out.println(pptd.getStock());
//	    		return list;
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}*/
	
	/**
	 * 
	 * @return
	 */
	public static String getProductJson(){
		String img = "http://mall-azt.chinacloudapp.cn";
		
        String inFilename = "C://test/jsonList.txt";
        
        String resultRet = "";
        
        List<PartnerProduct> rhp = new ArrayList<PartnerProduct>();
        
        Gson gson = new Gson();
        
        try {
			// in url
			BufferedReader br = new BufferedReader(new FileReader(inFilename));

			String s = br.readLine();
			
			System.out.println("start");
			while (s != null) {
				
				System.out.println(s.toString());
				PartnerProductArray hpa = gson.fromJson(s.toString(),
						PartnerProductArray.class);
				System.out.println("return PartnerProductArray size : " + hpa.getRows().size());
				List<PartnerProduct> hp = hpa.getRows();
				System.out.println("hp list:" + hp.size());
				for (int i = 0; i < hp.size(); i++) {
					PartnerProduct hh = hp.get(i);
					String imageurl = hh.getImage();
					hh.setImage(img + imageurl);
//					System.out.println(hh.getId());
					if(hh.getCategoryId() == 0){
						hh.setSku(getSkuList("1",hh.getId() + ""));	
					}else{
						hh.setSku(getSkuList(hh.getCategoryId() + "",hh.getId() + ""));	
					}
					
//					System.out.println(hh.getImage());
//					System.out.println(hh.getBrandName());
//					System.out.println(hh.getAuditState());
					rhp.add(hh);
				}
				s = br.readLine();
			}
			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        resultRet = gson.toJson(rhp);
        return resultRet;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static PartnerProductSt getSkuList(String categoryId, String productId){
//		String url = "http://mall-azt.chinacloudapp.cn/SellerAdmin/product/list?categoryId="
//	             + categoryId + "&productId="+ productId;
//
//		GetMethod method = new GetMethod(url);
//		HttpClient client = new HttpClient();
//
//		System.out.println(method.toString());
//		String ret = "";
//		try {
//			client.executeMethod(method);
//			ret = method.getResponseBodyAsString();
//		
//			System.out.println(method.getStatusCode());
//			System.out.println(ret);
//	        if(method.getStatusCode() == 200 
//	        		&& method.getResponseBodyAsString().indexOf("skuArray") > 0){
//	        	Gson gson = new Gson();
//	    		SKUArray hp = gson.fromJson(ret, SKUArray.class);
//	    		
//	    		List<PartnerProductSt> list = hp.getData();
//	    		for (int i = 0; i < list.size(); i++) {
//	    			PartnerProductSt hs = list.get(i);
////	    			System.out.println(hs.getPrice());
////	    			System.out.println(hs.getSKUId());
////	    			System.out.println(hs.getStock());
//	    		}
//	    		return list;
//	        }
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		
//      String img = "http://mall-azt.chinacloudapp.cn";
		
//      String url = "http://mall-azt.chinacloudapp.cn/SellerAdmin/product/list?auditStatus=2&page=1&row=100";
//		String url = "http://192.168.1.250:8080/SellerAdmin/product/list?auditStatus=2&page=1&row=100";
        
        String url = "http://mall-azt.chinacloudapp.cn/SellerAdmin/Product/GetSpecifications?categoryId=" + categoryId +"&productId=" + productId;
//	    String url = "http://192.168.1.250:8080/SellerAdmin/Product/GetSpecifications?categoryId=64&productId=127";
   
        GetMethod method = new GetMethod(url);
        method.addRequestHeader("Cookie", "Partner-User=ZENYQm1ueHg2d3hXMXVDUnRVallndz09;Partner-SellerManager=WUh5WVRjTUNiMm5IQ3ZuY0R3TFA0dz09;Partner-DefaultUserName=SellerAdmin");
        
		HttpClient client = new HttpClient();

		System.out.println(method.toString());
		String ret = "";
		PartnerProductSt hps = new PartnerProductSt();
		try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			
			System.out.println(ret);
	        if(method.getStatusCode() == 200 ){
	        	Gson gson = new Gson();
	    		SKUArray hp = gson.fromJson(ret, SKUArray.class);
	    		
	    		System.out.println(hp.getData());
	    		
	    		hps = hp.getData();
	    		
//	    		List<PartnerProductJson> lppj =  hps.getJson();
//	    		
//	    		System.out.println(hps.getJson().size());
//	    		System.out.println(hps.getTableData().getProductId());
//	    		
//	    		for(int i = 0; i < lppj.size(); i++){
//	    			PartnerProductJson hj = lppj.get(i);
//	    			System.out.println(hj.getName());
//	    			System.out.println(hj.getSpecId());
//	    			System.out.println(hj.getValues());
//	    			List<PartnerProductValue> lppv = hj.getValues();
//	    			for(int j = 0; j < lppv.size(); j++){
//	    				PartnerProductValue ppv = lppv.get(j);
//	    				System.out.println(ppv.getId());
//	    				System.out.println(ppv.getIsPlatform());
//	    				System.out.println(ppv.getName());
//	    				System.out.println(ppv.getSelected());
//	    				System.out.println(ppv.getClass());
//	    			}
//	    			System.out.println("***************");
//	    		}
//	    		PartnerProductTableData pptd = hps.getTableData();
//	    		System.out.println("####################");
//	    		System.out.println(pptd.getCost());
//	    		System.out.println(pptd.getMallPrice());
//	    		System.out.println(pptd.getProductId());
//	    		System.out.println(pptd.getSku());
//	    		System.out.println(pptd.getStock());
	    		
//	    		return list;
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hps;
	}

}
