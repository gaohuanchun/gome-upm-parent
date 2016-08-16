package com.gome.upm.domain;

import java.util.Date;

public class ServerInfo {
    private String id;

    /* 项目名/应用名**/
    private String xmm;

    private String bdx;

    private String bmd5;

    /* 包日期**/
    private String brq;

    private String xmlj;

    /* 所属组、部门**/
    private String ssz;
    
    private Integer sszid;

    private String yypid;

    /* 应用总内存**/  /*分配*/
    private String yyznc;

    /* 应用内存使用**/  /*内存使用/内存分配总数*/
    private String yyncsy;

    /* 应用内存使用率**/  /*使用率*/
    private Integer yyncb;

    private String yyqdr;

    /* 应用启动时间**/
    private String yyqdsj;

    private String yyzt;

    /* 服务器名称	**/
    private String xmip;

    private String xmdk;

    /* 是否运行   Y：运行中	N:不在运行**/
    private String sfyx;

    private String yyxc;

    private String yycpu;

    /* 容器**/
    private String xmrq;

    private String gxsj;

    private String xmwjm;

    private String yyjdk;

    /* 健康页反馈值**/
    private String ymz;

    private String jkyz;

    private String jkcj;

    private String jkcja;

    private Integer nginxhit;

    private Integer nginxhita;

    private String nginxgxsj;

    private String yylogjk;

    private String nginxzt;

    private Integer yynets;

    /* 内存分配总数**/
    private Float ncfpzs;

    private String yylogwj;

    private String yyloggjc;

    private Integer yyloggjcs;

    private String yylogzt;

    private Integer yylogcz;

    private String yylogdx;

    /* 项目IP端口组合**/
    private String ida;

    private String yylogwjm;

    private String yybz;

    private String yylogcssj;

    private Integer yyloggjcsz;

    private String yyfbzt;

    private String yycjsj;
    
    private PcInfo pcInfo;
    
    private String ipLast;
    
    /** 报警初始时间  */
    private String bjsj;
    
    /** 报警状态  */
    private String bjzt;
    
    /** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getXmm() {
        return xmm;
    }

    public void setXmm(String xmm) {
        this.xmm = xmm == null ? null : xmm.trim();
    }

    public String getBdx() {
        return bdx;
    }

    public void setBdx(String bdx) {
        this.bdx = bdx == null ? null : bdx.trim();
    }

    public String getBmd5() {
        return bmd5;
    }

    public void setBmd5(String bmd5) {
        this.bmd5 = bmd5 == null ? null : bmd5.trim();
    }

    public String getBrq() {
        return brq;
    }

    public void setBrq(String brq) {
        this.brq = brq == null ? null : brq.trim();
    }

    public String getXmlj() {
        return xmlj;
    }

    public void setXmlj(String xmlj) {
        this.xmlj = xmlj == null ? null : xmlj.trim();
    }

    public String getSsz() {
        return ssz;
    }

    public void setSsz(String ssz) {
        this.ssz = ssz == null ? null : ssz.trim();
    }
    
    public Integer getSszid() {
		return sszid;
	}
    
    public void setSszid(Integer sszid) {
		this.sszid = sszid;
	}

    public String getYypid() {
        return yypid;
    }

    public void setYypid(String yypid) {
        this.yypid = yypid == null ? null : yypid.trim();
    }

    public String getYyznc() {
        return yyznc;
    }

    public void setYyznc(String yyznc) {
        this.yyznc = yyznc == null ? null : yyznc.trim();
    }

    public String getYyncsy() {
        return yyncsy;
    }

    public void setYyncsy(String yyncsy) {
        this.yyncsy = yyncsy == null ? null : yyncsy.trim();
    }

    public Integer getYyncb() {
        return yyncb;
    }

    public void setYyncb(Integer yyncb) {
        this.yyncb = yyncb;
    }

    public String getYyqdr() {
        return yyqdr;
    }

    public void setYyqdr(String yyqdr) {
        this.yyqdr = yyqdr == null ? null : yyqdr.trim();
    }

    public String getYyqdsj() {
        return yyqdsj;
    }

    public void setYyqdsj(String yyqdsj) {
        this.yyqdsj = yyqdsj == null ? null : yyqdsj.trim();
    }

    public String getYyzt() {
        return yyzt;
    }

    public void setYyzt(String yyzt) {
        this.yyzt = yyzt == null ? null : yyzt.trim();
    }

    public String getXmip() {
        return xmip;
    }

    public void setXmip(String xmip) {
        this.xmip = xmip == null ? null : xmip.trim();
    }

    public String getXmdk() {
        return xmdk;
    }

    public void setXmdk(String xmdk) {
        this.xmdk = xmdk == null ? null : xmdk.trim();
    }

    public String getSfyx() {
        return sfyx;
    }

    public void setSfyx(String sfyx) {
        this.sfyx = sfyx == null ? null : sfyx.trim();
    }

    public String getYyxc() {
        return yyxc;
    }

    public void setYyxc(String yyxc) {
        this.yyxc = yyxc == null ? null : yyxc.trim();
    }

    public String getYycpu() {
        return yycpu;
    }

    public void setYycpu(String yycpu) {
        this.yycpu = yycpu == null ? null : yycpu.trim();
    }

    public String getXmrq() {
        return xmrq;
    }

    public void setXmrq(String xmrq) {
        this.xmrq = xmrq == null ? null : xmrq.trim();
    }

    public String getGxsj() {
        return gxsj;
    }

    public void setGxsj(String gxsj) {
        this.gxsj = gxsj == null ? null : gxsj.trim();
    }

    public String getXmwjm() {
        return xmwjm;
    }

    public void setXmwjm(String xmwjm) {
        this.xmwjm = xmwjm == null ? null : xmwjm.trim();
    }

    public String getYyjdk() {
        return yyjdk;
    }

    public void setYyjdk(String yyjdk) {
        this.yyjdk = yyjdk == null ? null : yyjdk.trim();
    }

    public String getYmz() {
        return ymz;
    }

    public void setYmz(String ymz) {
        this.ymz = ymz == null ? null : ymz.trim();
    }

    public String getJkyz() {
        return jkyz;
    }

    public void setJkyz(String jkyz) {
        this.jkyz = jkyz == null ? null : jkyz.trim();
    }

    public String getJkcj() {
        return jkcj;
    }

    public void setJkcj(String jkcj) {
        this.jkcj = jkcj == null ? null : jkcj.trim();
    }

    public String getJkcja() {
        return jkcja;
    }

    public void setJkcja(String jkcja) {
        this.jkcja = jkcja == null ? null : jkcja.trim();
    }

    public Integer getNginxhit() {
        return nginxhit;
    }

    public void setNginxhit(Integer nginxhit) {
        this.nginxhit = nginxhit;
    }

    public Integer getNginxhita() {
        return nginxhita;
    }

    public void setNginxhita(Integer nginxhita) {
        this.nginxhita = nginxhita;
    }

    public String getNginxgxsj() {
        return nginxgxsj;
    }

    public void setNginxgxsj(String nginxgxsj) {
        this.nginxgxsj = nginxgxsj == null ? null : nginxgxsj.trim();
    }

    public String getYylogjk() {
        return yylogjk;
    }

    public void setYylogjk(String yylogjk) {
        this.yylogjk = yylogjk == null ? null : yylogjk.trim();
    }

    public String getNginxzt() {
        return nginxzt;
    }

    public void setNginxzt(String nginxzt) {
        this.nginxzt = nginxzt == null ? null : nginxzt.trim();
    }

    public Integer getYynets() {
        return yynets;
    }

    public void setYynets(Integer yynets) {
        this.yynets = yynets;
    }

    public Float getNcfpzs() {
        return ncfpzs;
    }

    public void setNcfpzs(Float ncfpzs) {
        this.ncfpzs = ncfpzs;
    }

    public String getYylogwj() {
        return yylogwj;
    }

    public void setYylogwj(String yylogwj) {
        this.yylogwj = yylogwj == null ? null : yylogwj.trim();
    }

    public String getYyloggjc() {
        return yyloggjc;
    }

    public void setYyloggjc(String yyloggjc) {
        this.yyloggjc = yyloggjc == null ? null : yyloggjc.trim();
    }

    public Integer getYyloggjcs() {
        return yyloggjcs;
    }

    public void setYyloggjcs(Integer yyloggjcs) {
        this.yyloggjcs = yyloggjcs;
    }

    public String getYylogzt() {
        return yylogzt;
    }

    public void setYylogzt(String yylogzt) {
        this.yylogzt = yylogzt == null ? null : yylogzt.trim();
    }

    public Integer getYylogcz() {
        return yylogcz;
    }

    public void setYylogcz(Integer yylogcz) {
        this.yylogcz = yylogcz;
    }

    public String getYylogdx() {
        return yylogdx;
    }

    public void setYylogdx(String yylogdx) {
        this.yylogdx = yylogdx == null ? null : yylogdx.trim();
    }

    public String getIda() {
        return ida;
    }

    public void setIda(String ida) {
        this.ida = ida == null ? null : ida.trim();
        //http://10.58.11.16:8080/serverInfo.jsp?1469792878
        
        if(this.ida != null && this.ida.length() > 0 && this.jkyz != null && !"no".equals(this.jkyz)){
        	
			this.ipLast = "http://"+this.ida+"/"+this.jkyz+"?"+System.currentTimeMillis()/ 1000;
		}else{
			this.ipLast = "";
		}
    }

    public String getYylogwjm() {
        return yylogwjm;
    }

    public void setYylogwjm(String yylogwjm) {
        this.yylogwjm = yylogwjm == null ? null : yylogwjm.trim();
    }

    public String getYybz() {
        return yybz;
    }

    public void setYybz(String yybz) {
        this.yybz = yybz == null ? null : yybz.trim();
    }

    public String getYylogcssj() {
        return yylogcssj;
    }

    public void setYylogcssj(String yylogcssj) {
        this.yylogcssj = yylogcssj == null ? null : yylogcssj.trim();
    }

    public Integer getYyloggjcsz() {
        return yyloggjcsz;
    }

    public void setYyloggjcsz(Integer yyloggjcsz) {
        this.yyloggjcsz = yyloggjcsz;
    }

    public String getYyfbzt() {
        return yyfbzt;
    }

    public void setYyfbzt(String yyfbzt) {
        this.yyfbzt = yyfbzt == null ? null : yyfbzt.trim();
    }

    public String getYycjsj() {
        return yycjsj;
    }

    public void setYycjsj(String yycjsj) {
        this.yycjsj = yycjsj == null ? null : yycjsj.trim();
    }
    
    public void setPcInfo(PcInfo pcInfo) {
		this.pcInfo = pcInfo;
	}
    
    public PcInfo getPcInfo() {
		return pcInfo;
	}
    
    public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getIpLast() {
		return ipLast;
	}
	
	public void setIpLast(String ipLast) {
		this.ipLast = ipLast;
	}
	
	public String getBjsj() {
		return bjsj;
	}
	
	public void setBjsj(String bjsj) {
		this.bjsj = bjsj;
	}
	
	public String getBjzt() {
		return bjzt;
	}
	
	public void setBjzt(String bjzt) {
		this.bjzt = bjzt;
	}
	
}