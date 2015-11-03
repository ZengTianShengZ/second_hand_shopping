package com.dz4.ishop.domain;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 
 * UserµÄJavaBean
 * @author MZone
 *
 */
public class User extends BmobUser {
	private String username;
	private BmobFile icon;
	private BmobRelation favorite;
	private String sex;
	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public BmobFile getIcon() {
		return this.icon;
	}
	public void setIcon(BmobFile icon) {
		this.icon = icon;
	}
	public BmobRelation getFavorite() {
		return this.favorite;
	}
	public void setFavorite(BmobRelation favorite) {
		this.favorite = favorite;
	}
	public String getSex() {
		return this.sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "User [username=" + this.username + ", icon="
				+ this.icon + ", favorite=" + this.favorite
				+ ", sex=" + this.sex + "]";
	}
	
	
	
	
}
