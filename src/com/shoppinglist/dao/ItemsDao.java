package com.shoppinglist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.shoppinglist.entity.Items;
import com.shoppinglist.util.DbHelper;

//商品业务逻辑类
public class ItemsDao {
	//获取所有商品信息
	public ArrayList<Items> getAllItems() {
		ArrayList<Items> list = new ArrayList<Items>();
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			conn = DbHelper.getConnection();
			String sql = "SELECT * FROM items;";
			ptmt = conn.prepareStatement(sql);
			rs =ptmt.executeQuery();
			while(rs.next()){
				Items item = new Items();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setCity(rs.getString("city"));
				item.setNumber(rs.getInt("number"));
				item.setPrice(rs.getInt("price"));
				item.setPicture(rs.getString("picture"));
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			//关闭资源
			if (rs!=null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ptmt!=null) {
				try {
					ptmt.close();
					ptmt=null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	//根据商品编号显示商品详细信息
	public Items getItemsById(int id) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			conn = DbHelper.getConnection();
			String sql = "SELECT * FROM items WHERE id=?;";
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, id);
			rs =ptmt.executeQuery();
			if(rs.next()){
				Items item = new Items();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setCity(rs.getString("city"));
				item.setNumber(rs.getInt("number"));
				item.setPrice(rs.getInt("price"));
				item.setPicture(rs.getString("picture"));
				return item;
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			//关闭资源
			if (rs!=null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ptmt!=null) {
				try {
					ptmt.close();
					ptmt=null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//获取浏览过的前5条记录
	public ArrayList<Items> getListView(String list) {
		ArrayList<Items> itemslist = new ArrayList<Items>();
		if (list!=null&&list.length()>0) {
			int iCount = 5;
			//传过来的list是以逗号隔开的id集合
			String[] arr = list.split(",");
			//如果商品浏览记录大于5，从后面（倒序）输出5条记录
			if (arr.length>5) {
				for (int i = arr.length-1; i >arr.length-iCount-1; i--) {
					int id = Integer.parseInt(arr[i]);
					itemslist.add(getItemsById(id));
				}
			} else {
				for (int i = arr.length-1; i >= 0; i--) {
					int id = Integer.parseInt(arr[i]);
					itemslist.add(getItemsById(id));
				}
			}
			return itemslist;
		} else {
			return null;
		}
	}
}
