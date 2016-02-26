/**
 * 
 */
package com.aepan.sysmgr.string;

/**
 * @author Administrator
 * 2015年11月18日下午5:03:01
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String productAttrs = "41_139_126@40_132";
		if(productAttrs!=null&&!productAttrs.trim().isEmpty()){
			String[] attrs = productAttrs.split("@");
			if(attrs.length>0){
				for (String attr : attrs) {
					String[] aItems = attr.split("_");
					if(aItems!=null&&aItems.length>=2){
						String attrId = aItems[0];
						System.out.println("attrId="+attrId);
						for(int i=1;i<aItems.length;i++){
							String attrValue = aItems[i];
							System.out.println("attrValue="+attrValue);
						}
					}
					
				}
			}
		}
		System.out.println(getValue(2));
	}
	public static int getValue(int i) {
        int result = 0;
        switch (i) {
        case 1:
            result = result + i;
        case 2:
            result = result + i * 2;
        case 3:
            result = result + i * 3;
        }
        return result;
    }
}
