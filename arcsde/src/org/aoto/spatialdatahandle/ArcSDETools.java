package org.aoto.spatialdatahandle;

import com.esri.sde.sdk.client.SeColumnDefinition;
import com.esri.sde.sdk.client.SeConnection;
import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.client.SeInstance;
import com.esri.sde.sdk.client.SeLayer;
import com.esri.sde.sdk.client.SeQuery;
import com.esri.sde.sdk.client.SeRelease;
import com.esri.sde.sdk.client.SeRow;
import com.esri.sde.sdk.client.SeSqlConstruct;

public class ArcSDETools {
	private ArcSDEFactory arcsde;
	
	/**
	 *  获得ArcSDE版本信息
	 */
	public  void getVersion() {
		SeConnection conn = arcsde.create();
		SeRelease release = conn.getRelease();
		System.out.println(release.getBugFix());
		System.out.println(release.getDesc());
		System.out.println(release.getRelease());
		System.out.println(release.getMajor());
		System.out.println(release.getMinor());
	}
	
	/**
	 * ArcSDE管理
	 */
	public  void getArcSDEInfo() {
		try {
			SeInstance instance = new SeInstance(arcsde.getServer(), arcsde.getInstance());
			SeInstance.SeInstanceStatus status = instance.getStatus();

			System.out.println("连接数：" + status.getNumConnections());
			System.out.println("可以连接：" + status.isAccepting());
			SeInstance.SeInstanceConfiguration config = instance.getConfiguration();
			System.out.println("最大连接数：" + config.getMaxConnections());

			SeInstance.SeInstanceStats[] stats = instance.getStats();
			for (int i = 0; i < stats.length; i++) {
				System.out.println("操作数：" + stats[i].getOperationCount());
			}
			
			SeInstance.SeInstanceUsers[] users = instance.getUsers();
			for (int j = 0; j < users.length; j++) {
				System.out.println("用户名：" + users[j].getUserName());
				System.out.println("系统名：" + users[j].getSysName());
				System.out.println("服务器开始时间：" + users[j].getServerStartTime());
				System.out.println("服务器PID：" + users[j].getServerPid());
			}
			
			System.out.println("系统名：" + instance.getServerName());

			SeInstance.SeInstanceTableLocks[] tablelocks = instance.getTableLocks();
			if(tablelocks!=null){
				for (int i = 0; i < tablelocks.length; i++) {
					System.out.println("表级别锁类型：" + tablelocks[i].getLockType());
					System.out.println("表级别锁PID：" + tablelocks[i].getPid());
					System.out.println("表级别锁注册ID：" + tablelocks[i].getRegistrationId());
				}
			}
		} catch (SeException e) {
			e.printStackTrace();
		}
	}


	public SeQuery searchData (String sql,String geometryTpye) throws SeException { 
		SeConnection conn = arcsde.create();
		String where=null;
		String tableName=null;
		String[] cols=null;
		if(sql!=null){
			sql = sql.trim().toUpperCase();
			if(!sql.equals("null")&&!sql.equals("")){
				String headSql = sql.substring(6,sql.indexOf("FROM")).trim();
				if(headSql.contains(",")){
					String[] colstemp=headSql.split(",");
					cols=new String[colstemp.length+1];
					for(int i=0;i<colstemp.length;i++){
						String col=colstemp[i].trim();
						if(col.contains(".")){
							col=col.substring(col.indexOf(".")+1);
							cols[i]=col;
						}
					}
				}else{
					if(headSql.contains(".")){
						cols=new String[2];
						String col = headSql.substring(headSql.indexOf(".")+1).trim();
						cols[0]=col;
					}
				}
				String tailSql=sql.substring(sql.indexOf("FROM")+4).trim();
				if(tailSql.contains("WHERE")){
					tableName=tailSql.substring(0,tailSql.indexOf("WHERE")).trim();
					where=tailSql.substring(tailSql.indexOf("WHERE")+5).trim();
				}else{
					tableName=tailSql;
				}
			}
		}
		if(tableName!=null){
			SeLayer layer = new SeLayer( conn,  tableName, "SHAPE"); 
			cols[cols.length-1]=layer.getSpatialColumn();
			if(layer!=null){
				SeSqlConstruct sqlConstruct = new SeSqlConstruct(layer.getName()); 
				if(where!=null&&!where.equals("1=1")){
					sqlConstruct.setWhere(where); 
				}
				SeQuery query = new SeQuery(conn, cols, sqlConstruct ); 
				query.prepareQuery(); 
				query.execute();
				SeRow row = query.fetch();
				
				while (row != null)  
	            {  
	                System.out.println("------featureclass feature count -----------------");  
	                System.out.println(row.getObject(0).toString());  
	                System.out.println("--------------------------------------------------");  
	                SeColumnDefinition[] columns = row.getColumns();
	                for(int i=0;i<columns.length;i++){
	                	SeColumnDefinition col=columns[i];
	                	
	                	if(col.getName().equals("SHAPE")){
	                		System.out.println("---"+col.getName());
	                	}
	                }
	                row = query.fetch();  
	                
	            }  
				
			}
		}

		return null;
    }

	public ArcSDEFactory getArcsde() {
		return arcsde;
	}

	public void setArcsde(ArcSDEFactory arcsde) {
		this.arcsde = arcsde;
	}

}
