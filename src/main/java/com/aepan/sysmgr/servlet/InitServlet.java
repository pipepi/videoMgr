/**
 * 
 */
package com.aepan.sysmgr.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.aepan.sysmgr.service.ConfigService;

/**
 * 初始化servlet
 * @author rakika
 * 2015年9月09日上午10:08:08
 */
public class InitServlet implements Servlet {
//	private static final Logger logger = LoggerFactory.getLogger(InitServlet.class);

	@Autowired
	private ConfigService configService;
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#getServletConfig()
	 */
	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#getServletInfo()
	 */
	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		//在这里添加init参数
		/*try {
			MediaServiceSDK.init("aztsaymediaservice", "ftjscy5977rr0dRopAfUWC5nAd+loZ6MgE+AcSIesTk=");
			StorageServiceSDK.init("picazt",
	 				"xLiYUD1/HyBTsnLNu1qNUgLiJmId/c/nYnIVQQw5i2iGF06Asr0pKLqCVlI42f6hrqdMNo4VCW052kSlU0rOCQ==");
			ConfigManager.getInstance().getFileConfig(configService);
		} catch (AzureMediaSDKException e) {
			logger.error(e.getMessage(),e);
		}*/
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
