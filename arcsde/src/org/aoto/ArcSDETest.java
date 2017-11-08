package org.aoto;

import java.util.Vector;

import org.aoto.spatialdatahandle.ArcSDEFactory;
import org.aoto.spatialdatahandle.ArcSDETools;

import com.esri.sde.sdk.client.SeConnection;
import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.client.SeLayer;

public class ArcSDETest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArcSDEFactory factory = new ArcSDEFactory();
		factory.setServer("192.168.20.240");
		factory.setInstance("5151");
		factory.setDatabase("");
		factory.setUsername("sde");
		factory.setPassword("sde");
		
		ArcSDETools tools=new ArcSDETools();
		tools.setArcsde(factory);
		try {
			String sql="select t.objectid from FEATURE_POINT where 1=1";
			tools.searchData(sql,"point");
			//tools.getArcSDEInfo();
			//tools.getVersion();
		} catch (SeException e1) {
			e1.printStackTrace();
		}
		
	}
}
