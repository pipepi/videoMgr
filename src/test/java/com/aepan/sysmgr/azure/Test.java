package com.aepan.sysmgr.azure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author AZT
 * 2015年8月9日下午4:40:54
 */
public class Test {
	public static void main(String[] args) throws FileNotFoundException, AzureMediaSDKException {
		/*MediaServiceSDK.init("aztsaymediaservice", "ftjscy5977rr0dRopAfUWC5nAd+loZ6MgE+AcSIesTk=");
		StorageServiceSDK.init("picazt",
 				"xLiYUD1/HyBTsnLNu1qNUgLiJmId/c/nYnIVQQw5i2iGF06Asr0pKLqCVlI42f6hrqdMNo4VCW052kSlU0rOCQ==");*/
		File file = new File("D:\\ffmpeg\\input\\Desert.jpg");
		FileInputStream inputStream = new FileInputStream(file );
		StorageServiceSDK.uploadFile("tttdd", file.getName(), inputStream , file.length());
		
	}
}
